name := "cabs-around"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies := Seq(
  "com.typesafe.akka" %% "akka-http-core" % "10.1.5",
  "org.scalatest" %% "scalatest" % "3.0.5",
  "org.typelevel" %% "cats-core" % "1.4.0",
  "com.crobox.clickhouse" %% "client" % "0.8.8"
)