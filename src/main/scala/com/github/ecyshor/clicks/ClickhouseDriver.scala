package com.github.ecyshor.clicks

import cats.effect._
import fs2.Chunk
import io.circe._
import io.circe.syntax._
import org.http4s._
import org.http4s.client.{Client => HttpClient}
import HttpClientExtension._
case class ClickhouseException(message:String) extends RuntimeException(message)

class ClickhouseDriver(host: Uri, database: String) {


  def query[A](query: String)(implicit client: HttpClient[IO], ev: EntityDecoder[IO, A]): IO[A] = {
    client.fetchAsE[A](
      Request[IO](
        Method.GET,
        host.copy(query = Query(
          "query" -> Some(query)
        ))
      ))
  }

  def create[A](query: String)(implicit client: HttpClient[IO], ev: EntityDecoder[IO, A]): IO[A] = {
    client.fetchAsE[A](
      Request[IO](
        Method.POST,
        host.copy(query = Query(
          "query" -> Some(query)
        ))
      ))
  }

  def insert[A](table: String, entities: Seq[A])(implicit client: HttpClient[IO], ev: Encoder[A]): IO[String] = {
    val req = Request(
      Method.POST,
      host.copy(query = Query(
        "query" -> Some(s"INSERT INTO $database.$table FORMAT JSONEachRow")
      )),
      body =
        fs2.Stream[IO, A](entities: _*)
          .map(el => {
            Printer.noSpaces.pretty(el.asJson)
          }).intersperse("\n").flatMap(el => fs2.Stream.chunk(Chunk.bytes(el.getBytes())))
    )
    client.fetchAsE[String](req)
  }


}
