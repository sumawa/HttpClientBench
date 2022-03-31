package bench

import sttp.client3.HttpURLConnectionBackend

object SttpStandaloneBenchmark extends scala.App {
  import sttp.client3.{UriContext, basicRequest}

  val rootContext = scala.util.Properties.envOrElse("ROOT_CONTEXT", "localhost:7777")
  val N = scala.util.Properties.envOrElse("NUM_REQUESTS", "3000").toInt

//  val config: org.asynchttpclient.AsyncHttpClientConfig = new org.asynchttpclient.DefaultAsyncHttpClientConfig.Builder().build()
  val grequest                      = basicRequest.get(uri"http://$rootContext/get")
  val prequest                      = basicRequest.get(uri"http://$rootContext/post").body("Sample content")

  implicit val backend = HttpURLConnectionBackend()

  1 to N foreach   { _ =>
    grequest.send(backend).statusText
  }

  val startTimeGet = System.currentTimeMillis()
  1 to N foreach  { _ => grequest.send(backend).statusText}
  val durationGet = System.currentTimeMillis() - startTimeGet

  println(s"\n | STTP | GET | $N | ${(N * 1000 / durationGet)} | %0A ")

  val startTimePost = System.currentTimeMillis()
  1 to N foreach { _ => prequest.send(backend).statusText }
  val durationPost = System.currentTimeMillis() - startTimeGet

  println(s"\n | STTP | POST | $N | ${(N * 1000 / durationPost)} | %0A ")

}
