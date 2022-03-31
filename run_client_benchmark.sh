mkdir -p ~/cbcache
mkdir -p ~/cbsbt
docker-compose stop zhttp-server zhttp-client
#docker-compose rm -f zhttp-server zhttp-client
docker-compose up -d zhttp-server
docker-compose up zhttp-client
docker-compose stop zhttp-server zhttp-client
