val ZIOVersion = "1.0.13"
//val ZIOVersion = "2.0.0-RC2"
val zhttpVersion     = "1.0.0.0-RC25"
//val zhttpVersion = "2.0.0-RC4"

val http4sVersion = "0.23.11"

lazy val zhttp = ProjectRef(uri(s"https://github.com/---COMMIT_SHA---"), "zhttp")
//lazy val zhttp = ProjectRef(uri(s"https://github.com/dream11/zio-http.git#a7b6fe27bb0df2287834773ce0615ee3931e64a7"), "zhttp")

lazy val root = project
  .in(file("."))
  .settings(
    name := "client-benchmark",
    organization := "bench",
    scalaVersion := "2.13.1",
  )
  .dependsOn(zhttp)

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "check",
  "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
)

libraryDependencies ++= Seq(
  // ZIO
  "dev.zio" %% "zio"          % ZIOVersion,
  "dev.zio" %% "zio-streams"  % ZIOVersion,
//  "dev.zio" %% "zio-test"     % ZIOVersion % "test",
//  "dev.zio" %% "zio-test-sbt" % ZIOVersion % "test",

  // zio-http
//  "io.d11"                %% "zhttp"                          % zhttpVersion,

  // sttp
  "com.softwaremill.sttp.client3" %% "core" % "3.5.1",
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio1" % "3.5.1", // for ZIO 1.x,

  // http4s
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,

)

testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))

