FROM gradle:5.4.1-jdk-alpine AS builder
#RUN mkdir /project
WORKDIR /project
COPY . /project
USER root
RUN chown -R gradle /project
USER gradle 
RUN whoami
RUN ls -lah
RUN gradle clean build jacocoTestReport sonarqube --info

FROM openjdk:8-jre-alpine
RUN mkdir /app
COPY --from=builder "/project/build/libs/final-project-0.0.1-SNAPSHOT.jar" "/app/app.jar"
WORKDIR /app
EXPOSE 8080
CMD [ "-jar", "app.jar" ]
ENTRYPOINT [ "java" ]
