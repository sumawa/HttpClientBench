rm -rf /log/*.log
echo "$(pwd)" >> /log/server_out.log
echo "BUILDING ASSEMBLY IN SERVER" >> /log/server_out.log
cd /
# export SBT_OPTS="-XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=2G -Xmx2G -Xms1G"
sbt assembly >> /log/assembly.log
echo "DONE BUILDING ASSEMBLY" >> /log/server_out.log

java -classpath /target/scala-2.12/client-benchmark-assembly-0.1.0-SNAPSHOT.jar bench.SimpleServer >> /log/out.log 


