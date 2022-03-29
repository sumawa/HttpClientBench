docker-compose stop zhttp-server zhttp-client
docker-compose rm -f zhttp-server zhttp-client
docker-compose up -d zhttp-server
docker-compose up zhttp-client
docker-compose down zhttp-server
