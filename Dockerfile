FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/demojenkins.jar guli_parent.jar
ENTRYPOINT ["java","-jar","/guli_parent.jar", "&"]