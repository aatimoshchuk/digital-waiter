set container-name=postgres-dw-with-logs
set image-name=digital_waiter_db_database-postgres

docker stop %container-name%
docker rm %container-name%
docker rmi %image-name%
docker compose up -d
timeout /t 5