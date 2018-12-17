package com.github.ecyshor.clicks

import cats.Monad
import cats.effect.{Bracket, Sync}
import org.http4s.client.Client
import org.http4s.headers.{Accept, MediaRangeAndQValue}
import org.http4s.{EntityDecoder, Request}

object HttpClientExtension {

  implicit class Http4sClientOps[F[?] : Sync](client: Client[F])(implicit F: Bracket[F, Throwable]) {
    /**
      * Submits a request and decodes the response, regardless of the status code.
      * The underlying HTTP connection is closed at the completion of the
      * decoding.
      */
    def fetchAsE[A](req: Request[F])(implicit d: EntityDecoder[F, A]): F[A] = {
      val r = if (d.consumes.nonEmpty) {
        val m = d.consumes.toList
        req.putHeaders(Accept(MediaRangeAndQValue(m.head), m.tail.map(MediaRangeAndQValue(_)): _*))
      } else req
      client.fetch(r) { resp =>
        if (resp.status.isSuccess)
          d.decode(resp, strict = false).fold(throw _, identity)
        else Monad[F].flatMap(EntityDecoder.decodeString(resp))(message => F.raiseError(ClickhouseException(message)))
      }
    }
  }

}
