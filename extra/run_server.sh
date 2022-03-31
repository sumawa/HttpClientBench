rm -rf /log/*.log
rm -rf /log/*.out
echo "BUILDING ASSEMBLY IN SERVER" >> /log/server_out.log
cd /
SECONDS=0
export SBT_OPTS="-XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=2G -Xmx2G -Xms1G"
#sbt assembly >> /log/assembly.log
sbt assembly >> /log/server_out.log
duration=$SECONDS
echo "DONE BUILDING ASSEMBLY $(($duration / 60)) minutes and $(($duration % 60)) seconds elapsed." >> /log/server_out.log

java -classpath /target/scala-2.13/client-benchmark-assembly-0.1.0-SNAPSHOT.jar bench.SimpleServer >> /log/out.log 


