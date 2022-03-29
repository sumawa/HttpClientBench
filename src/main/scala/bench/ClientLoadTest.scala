//package bench
//
//import zhttp.http.{Method, URL}
//import zhttp.service.Client.ClientRequest
//import zhttp.service.client.domain.DefaultClient
//import zhttp.service.{Client, ClientSettings}
//import zio._
//import zio.console.putStrLn
//import zio.stream.{ZStream, ZTransducer}
//
//object ClientLoadTest extends App {
////  val env = ChannelFactory.auto ++ EventLoopGroup.auto()
//  val sleep = "http://localhost:8080/plaintext"
//
//  var count       = 0
//  var initialTime = System.currentTimeMillis()
//  val client      = Client.make(ClientSettings.threads(8))
//  def get(url: URL, defaultClient: DefaultClient): ZIO[Any, Throwable, (String, Int)] = {
//    for {
//      resp <- defaultClient.run(ClientRequest(url, Method.GET))
//      body <- resp.bodyAsString
//      res = (body, resp.status.asJava.code())
//    } yield res
//  }
//
//  def stream(url: URL, batchSize: Int, defaultClient: DefaultClient): ZStream[Any, Throwable, List[(String, Int)]] =
//    ZStream
//      .repeat(())
//      .transduce(
//        ZTransducer.fromEffect(
//          ZIO.collectAllPar(List.fill(batchSize)(get(url, defaultClient))),
//        ),
//      )
//
//  val app = for {
//    cl  <- Client.make(ClientSettings.threads(8))
//    url <- ZIO.fromEither(URL.fromString(sleep))
//    _   <- stream(url, 50, cl).zipWithIndex.foreach { s =>
//      count += 1
//      println(s" COUNT : $count IN  ${(System.currentTimeMillis() - initialTime) / 1000} SECONDS")
//      putStrLn(s"stream: ${s.toString}")
//    }
//  } yield ()
//
//  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
//    app
//      .catchAllCause(c => putStrLn(c.toString))
//      .exitCode
//}
