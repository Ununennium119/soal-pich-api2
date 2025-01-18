#!/bin/sh

./mvnw clean package
docker build -t soal-pich/api .
