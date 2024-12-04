#!/bin/bash
REPOSITORY=/home/ubuntu/wekids
CONTAINER_NAME=wekids-spring
ECR_REGISTRY=992382462285.dkr.ecr.ap-northeast-2.amazonaws.com

cd $REPOSITORY

echo "> 🔵 PULL DOCKER IMAGE FROM ECR"
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin $ECR_REGISTRY

echo "> 🔵 RUN APPLICATION CONTAINER"
docker-compose pull spring
docker-compose up -d