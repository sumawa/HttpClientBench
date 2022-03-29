package bench

import zio.{ExitCode, URIO, ZIO}

import java.util.concurrent.TimeUnit

object SttpBenchmark extends zio.App {
  import org.asynchttpclient.{AsyncHttpClientConfig, DefaultAsyncHttpClientConfig}
  import sttp.client3.{UriContext, basicRequest}

  val rootContext = scala.util.Properties.envOrElse("ROOT_CONTEXT", "localhost:7777")
  val N = scala.util.Properties.envOrElse("NUM_REQUESTS", "3000").toInt

  import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend

  val config: AsyncHttpClientConfig = new DefaultAsyncHttpClientConfig.Builder().build()
  val grequest                      = basicRequest.get(uri"http://$rootContext/get")
  val prequest                      = basicRequest.get(uri"http://$rootContext/post").body("Sample content")
  val backend                       = AsyncHttpClientZioBackend.usingConfig(config)

  def run(n: Int) = for {
    //    _ <- ZIO(println(s"STTP CLIENT: Warming up the server with 2000 requests $grequest"))
    _ <- backend.flatMap(backend => grequest.send(backend).map(_.statusText).repeatN(2000))

    startTime <- ZIO(System.nanoTime())
    _         <- backend.flatMap(backend => grequest.send(backend).map(_.statusText).repeatN(n))
    duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)
    _        = println(s"\nSTTP : GET $n requests --- ${(n * 1000 / duration)} requests/sec")

    startTime <- ZIO(System.nanoTime())
    _         <- backend.flatMap(backend => prequest.send(backend).map(_.statusText).repeatN(n))
    duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)
    _        = println(s"STTP : POST $n requests --- ${(n * 1000 / duration)} requests/sec \n")

  } yield ()

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    run(N).exitCode
}
