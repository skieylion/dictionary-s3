FROM adoptopenjdk/openjdk11:alpine-jre
COPY /target/dictionary-s3-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8082:8082

ENTRYPOINT ["java","-jar", "/app/app.jar"]