rm -rf /log/*client*.log

clientArray=("SttpStandalone" "Zhttp" "Http4s")

for T in ${clientArray[@]} ; do CLIENT_TYPE=$T java -classpath /target/scala-2.13/client-benchmark-assembly-0.1.0-SNAPSHOT.jar bench.${T}Benchmark >> /log/results.out ; done
