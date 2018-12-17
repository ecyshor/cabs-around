package com.github.ecyshor.clicks

import cats.effect._
import io.circe.generic.auto._
import org.http4s.Uri
import org.http4s.client.blaze._
import org.scalatest.{CancelAfterFailure, FlatSpec, Matchers}
import org.http4s.circe.CirceEntityEncoder._

import scala.concurrent.ExecutionContext.Implicits.global

class ClickhouseDriverTest extends FlatSpec with Matchers with CancelAfterFailure {

  val client = new ClickhouseDriver(Uri.uri("http://localhost:8123"), "default")
  implicit val contextShift: ContextShift[IO] = IO.contextShift(global)

  "The clickhouse client" should "execute simple select query" in {
    import org.http4s.circe.CirceEntityDecoder._
    BlazeClientBuilder[IO](global).resource.use { implicit c =>
      client.query[Int]("SELECT 1 + 1")
    }.unsafeRunSync() should be(2)
  }

  it should "insert data into clickhouse" in {
    case class TestColumn(firstColumn: Int, secondColumn: String)
    BlazeClientBuilder[IO](global).resource.use { implicit c =>
      for {
        _ <- client.create[String]("CREATE TABLE IF NOT EXISTS test_table(firstColumn Int32, secondColumn String) ENGINE = Memory")
        _ <- client.insert("test_table", fs2.Stream(
          TestColumn(1, "id"),
          TestColumn(2, "id_2"),
          TestColumn(5, "id_2")
        ))
        res <- {
          import org.http4s.circe.CirceEntityDecoder._
          client.query[JsonClickhouseResult[TestColumn]]("SELECT sum(firstColumn) as firstColumn, secondColumn FROM test_table GROUP BY secondColumn FORMAT JSON")
        }
      } yield res
    }.unsafeRunSync().data shouldBe Seq(
      TestColumn(1, "id"),
      TestColumn(7, "id_2")
    )
  }


}
