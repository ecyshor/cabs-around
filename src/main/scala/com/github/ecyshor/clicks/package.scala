package com.github.ecyshor

import io.circe.Decoder
import io.circe.generic.extras.{Configuration, ConfiguredJsonCodec}

package object clicks {
  private implicit val caseConfig: Configuration = Configuration.default.withSnakeCaseMemberNames.withDefaults

  @ConfiguredJsonCodec case class Meta(name: String, `type`: String)

  @ConfiguredJsonCodec case class Extremes(min: Map[String, String], max: Map[String, String])

  @ConfiguredJsonCodec case class Statistics(elapsed: Double, rowsRead: Long, bytesRead: Long)

  case class JsonClickhouseResult[A](meta: Seq[Meta],
                                     data: Seq[A],
                                     totals: Option[Map[String, String]],
                                     extremes: Option[Extremes], rows: Long,
                                     rowsBeforeLimit: Option[Long],
                                     statistics: Option[Statistics])

  implicit def decoder[A: Decoder]: Decoder[JsonClickhouseResult[A]] = Decoder.forProduct7("meta", "data",
    "totals", "extremes", "rows", "rows_before_limit_at_least", "statistics")(JsonClickhouseResult.apply[A])

}
