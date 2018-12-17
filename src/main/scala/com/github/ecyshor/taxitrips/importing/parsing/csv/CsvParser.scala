package com.github.ecyshor.taxitrips.importing.parsing.csv

import cats.data.Validated
import cats.implicits._

trait CsvParser[T] {

  def parse(values: Map[String, String]): Validated[CsvFieldParsingException, T]

}

object CsvParser {

  def parse1[Target, A0: CsvFieldReader](a0Key: String)(b: A0 => Target): CsvParser[Target] =
    (values: Map[String, String]) => Validated.fromEither[CsvFieldParsingException,Target](CsvFieldReader[A0].read(a0Key, values).map(b))

}

sealed trait CsvFieldParsingException

case class MissingFieldException(message: String) extends CsvFieldParsingException

case class BadFieldValueException(message: String) extends CsvFieldParsingException

trait CsvFieldReader[T] {
  def read(header: String, values: Map[String, String]): Either[CsvFieldParsingException, T]
}

object CsvFieldReader {

  def apply[A: CsvFieldReader]: CsvFieldReader[A] = implicitly[CsvFieldReader[A]]

  implicit def genericFieldReader[T: FieldParser]: CsvFieldReader[T] = (header: String, values: Map[String, String]) => {
    values.get(header).toRight(MissingFieldException(s"Could not find field $header")).flatMap(implicitly[FieldParser[T]].parse _)
  }

  implicit def optionCsvFieldReader[T: FieldParser]: CsvFieldReader[Option[T]] = (header: String, values: Map[String, String]) =>
    values.get(header).traverse[Either[CsvFieldParsingException, ?], T](implicitly[FieldParser[T]].parse _)
}

trait FieldParser[T] {
  def parse(field: String): Either[CsvFieldParsingException, T]
}

object FieldParser {

  implicit val intFieldParser: FieldParser[Int] = (field: String) => Either.catchOnly[NumberFormatException](field.toInt).leftMap(_ => BadFieldValueException(s"Cannot convert $field to int."))
  implicit val doubleFieldParser: FieldParser[Double] = (field: String) => Either.catchOnly[NumberFormatException](field.toDouble).leftMap(_ => BadFieldValueException(s"Cannot convert $field to double."))
  implicit val stringFieldParser: FieldParser[String] = (field: String) => Either.right(field)

}

