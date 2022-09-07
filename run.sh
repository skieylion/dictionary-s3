sudo docker rm s3-app
mvn clean package
sudo docker compose build
sudo docker compose up