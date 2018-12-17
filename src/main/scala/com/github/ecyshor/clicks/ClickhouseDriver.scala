package com.github.ecyshor.clicks

import cats.effect._
import com.github.ecyshor.clicks.HttpClientExtension._
import fs2.Chunk
import org.http4s._
import org.http4s.client.{Client => HttpClient}
import org.http4s.headers.`Content-Type`

case class ClickhouseException(message: String) extends RuntimeException(message)

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

  def insert[A](table: String, entities: fs2.Stream[IO, A])(implicit client: HttpClient[IO], ev: EntityEncoder[IO, A]): IO[String] = {
    IO.fromEither(ev.contentType.collect {
      case `Content-Type`(MediaType.application.json, _) => "JSONEachRow"
      case `Content-Type`(MediaType.text.csv, _) => "CSV"
    }.toRight(ClickhouseException(s"Cannot determine format from $ev"))).flatMap(format => {
      client.fetchAsE[String](Request(
        Method.POST,
        host.copy(query = Query(
          "query" -> Some(s"INSERT INTO $database.$table FORMAT $format")
        )),
        body =
          entities
            .map(el => {
              ev.toEntity(el).body
            }).intersperse(fs2.Stream.chunk(Chunk.bytes("\n".getBytes))).flatten
      ))
    })

  }


}
