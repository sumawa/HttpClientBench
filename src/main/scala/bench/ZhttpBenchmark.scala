package bench

import zhttp.http.{Headers, HttpData, Method}
import zhttp.service.{ChannelFactory, Client, EventLoopGroup}
import zio.{ExitCode, URIO, ZIO}

import java.util.concurrent.TimeUnit

object ZhttpBenchmark extends zio.App {
  val rootContext = scala.util.Properties.envOrElse("ROOT_CONTEXT", "localhost:7777")

  val env  = ChannelFactory.auto ++ EventLoopGroup.auto()
  val gurl = s"http://$rootContext/get"
  val purl = s"http://$rootContext/post"

  def run(n: Int) = (for {
    //    _ <- ZIO(println(s"ZIO-HTTP CLIENT: Warming up the server with 2000 requests $gurl"))
    _ <- Client.request(gurl).map(_.status).repeatN(2000)

    startTime <- ZIO(System.nanoTime())
    _ <- Client.request(gurl).map(_.status).repeatN(n)
    duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)
    _        = println(s"\n | ZIO-HTTP | GET | $n | ${(n * 1000 / duration)} | %0A ")

    startTime <- ZIO(System.nanoTime())
    _ <- Client.request(purl, Method.POST, Headers.empty, HttpData.fromString("Sample content")).map(_.status).repeatN(n)
    duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)
    _        = println(s"\n | ZIO-HTTP | POST | $n | ${(n * 1000 / duration)} | %0A ")

  } yield ()).provideCustomLayer(env)

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = run(3000).exitCode
}
