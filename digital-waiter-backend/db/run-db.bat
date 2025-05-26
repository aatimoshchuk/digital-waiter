set container-name=postgres-dw
set image-name=digital-waiter-db

docker stop %container-name%
docker rm %container-name%
docker rmi %image-name%
docker-compose -f docker-compose.db.yaml up -d
timeout /t 5