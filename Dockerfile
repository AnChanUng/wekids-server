FROM openjdk:17-ea-11-jdk-slim AS BUILDER
RUN mkdir /app_source
COPY . /app_source
WORKDIR /app_source
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

FROM openjdk:17-ea-11-jdk-slim AS RUNNER
RUN mkdir /app

ARG RDS_ENDPOINT
ARG RDS_USERNAME
ARG RDS_PASSWORD
ARG JWT_KEY
ARG NAVER_CLIENT_ID
ARG NAVER_CLIENT_SECRET
ARG NAVER_REDIRECT_URI
ARG S3_KEY
ARG S3_SECRET_KEY
ARG BAAS_URL

ENV RDS_ENDPOINT=${RDS_ENDPOINT} \
    RDS_USERNAME=${RDS_USERNAME} \
    RDS_PASSWORD=${RDS_PASSWORD} \
    JWT_KEY=${JWT_KEY} \
    NAVER_CLIENT_ID=${NAVER_CLIENT_ID} \
    NAVER_CLIENT_SECRET=${NAVER_CLIENT_SECRET} \
    NAVER_REDIRECT_URI=${NAVER_REDIRECT_URI} \
    S3_KEY=${S3_KEY} \
    S3_SECRET_KEY=${S3_SECRET_KEY} \
    BAAS_URL=${BAAS_URL} \
    TZ=Asia/Seoul

WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=BUILDER /app_source/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Dspring.profiles.active=release", "-jar", "/app/app.jar"]