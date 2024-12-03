FROM openjdk:17-ea-11-jdk-slim AS BUILDER
RUN mkdir /app_source
COPY . /app_source
WORKDIR /app_source
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

FROM openjdk:17-ea-11-jdk-slim AS RUNNER
RUN mkdir /app
COPY --from=BUILDER /app_source/build/libs/*.jar /app/app.jar
WORKDIR /app
ENV TZ=Asia/Seoul
EXPOSE 8080
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Dspring.profiles.active=release", "-jar", "/app/app.jar"]