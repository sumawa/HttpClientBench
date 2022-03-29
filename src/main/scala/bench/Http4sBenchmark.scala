package bench

import java.util.concurrent.{TimeUnit}
import org.http4s.{Method, Uri}
import cats.effect._
import org.http4s.Request
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.Client

object Http4sBenchmark extends cats.effect.IOApp {

  val rootContext = scala.util.Properties.envOrElse("ROOT_CONTEXT", "localhost:7777")

  val gurl = s"http://$rootContext/get"
  val getRequest = Request[IO](Method.GET,Uri.unsafeFromString(gurl))
  val purl = s"http://$rootContext/post"
  val postRequest = Request[IO](Method.POST,Uri.unsafeFromString(purl))

  def run(client: Client[IO], n: Int) =     for {
    //    _ <- ZIO(println(s"STTP CLIENT: Warming up the server with 2000 requests $grequest"))
    _  <- client.status(getRequest).replicateA(2000)

    startTime <- IO.delay(System.nanoTime())
    _  <- client.status(getRequest).replicateA(n)
    duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)
    _        <- IO.delay( println(s"\nHttp4s : GET $n requests --- ${(n * 1000 / duration)} requests/sec "))

    startTime <- IO.delay(System.nanoTime())
    _  <- client.status(postRequest).replicateA(n)
    duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)
    _        <- IO.delay( println(s"Http4s : POST $n requests --- ${(n * 1000 / duration)} requests/sec "))

  } yield ()

  override def run(args: List[String]): IO[ExitCode] =
    BlazeClientBuilder[IO].resource
      .use(run(_, 3000))
      .as(ExitCode.Success)

}
