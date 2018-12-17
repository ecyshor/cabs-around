name := "cabs-around"

version := "0.1"

scalaVersion := "2.12.7"

//scalacOptions += "-Ypartial-unification"


val ClickhouseClientVersion = "0.8.8"
val AkkaVersion = "2.5.18"

resolvers += Resolver.sonatypeRepo("releases")
val Http4sVersion = "0.20.0-M4"
val CirceVersion = "0.10.1"
addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.8")
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "1.0-M1",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.typelevel" %% "cats-core" % "1.5.0",
  "org.typelevel" %% "kittens" % "1.2.0",
  "com.chuusai" %% "shapeless" % "2.3.3",
  "com.crobox.clickhouse" %% "client" % ClickhouseClientVersion,
  "com.crobox.clickhouse" %% "testkit" % ClickhouseClientVersion,
  "com.crobox.clickhouse" %% "dsl" % ClickhouseClientVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe" % "config" % "1.3.3",
  //  http4s
  "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "io.circe" %% "circe-generic" % CirceVersion,
  "io.circe" %% "circe-generic-extras" % CirceVersion

)