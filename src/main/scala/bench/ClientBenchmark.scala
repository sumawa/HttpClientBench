//package bench
//import zio.{App, ExitCode, URIO, ZIO}
//
//trait BaseBenchmark {
//
//  val rootContext = scala.util.Properties.envOrElse("ROOT_CONTEXT", "localhost:7777")
//}
//
//object ClientBenchmark extends App {
//
//  val clientType = scala.util.Properties.envOrElse("CLIENT_TYPE", "ZHTTP")
//
//  def program(n: Int) = clientType match {
////    case "ZHTTP" => ZHttpBenchmark.run(n)
////    case "ZHTTP_OLD" => ZHttpBenchmarkOld.run(n)
//    case "STTP"  => SttpBenchmark.run(n)
////    case _       => ZHttpBenchmarkOld.run(n)
//  }
//
//  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = program(3000).exitCode
//}
//
////case object ZHttpBenchmark extends BaseBenchmark {
////  val env  = ChannelFactory.auto ++ EventLoopGroup.auto()
////  val gurl = s"http://$rootContext/get"
////  val purl = s"http://$rootContext/post"
////
////  def run(n: Int) = (for {
////    _ <- ZIO(println(s"Warming up the server with 2000 requests $gurl"))
////
////    client <- (
////      ClientSettings.threads(8) ++
////        ClientSettings.maxConnectionsPerRequestKey(10) ++
////        ClientSettings.maxTotalConnections(20)
////    ).make
////
////    _ <- client.run(gurl).map(_.status).repeatN(2000)
////    //    _ <- Client.request(gurl).map(_.status).repeatN(2000)
////
////    _         <- ZIO(println("\nStarting the benchmarking for GET requests with zio-http client!\n"))
////    _         <- ZIO(println(s"Number of GET requests: ${n}"))
////    startTime <- ZIO(System.nanoTime())
////
////    //    _ <- Client.request(gurl).map(_.status).repeatN(n)
////    _ <- client.run(gurl).map(_.status).repeatN(n)
////
////    duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)
////    _        = println(s"\n GET requests/sec for zio-http client: ${(n * 1000 / duration)}\n")
////
////    _         <- ZIO(println("\nStarting the benchmarking for POST requests for zio-http client!\n"))
////    _         <- ZIO(println(s"\nNumber of POST requests: ${n}\n"))
////    startTime <- ZIO(System.nanoTime())
////
////    // _ <- Client.request(purl, Method.POST, Headers.empty, HttpData.fromString("Sample content")).map(_.status).repeatN(n)
////    _ <- client.run(purl, Method.POST, Headers.empty, HttpData.fromString("Sample content")).map(_.status).repeatN(n)
////
////    duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)
////    _        = println(s"\n POST requests/sec for zio-http client: ${(n * 1000 / duration)}\n")
////
////  } yield ()).provideCustomLayer(env)
////}
//
