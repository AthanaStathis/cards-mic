FROM openjdk:27-ea-trixie
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cards-mic.jar
ENTRYPOINT ["java","-jar","/cards-mic.jar"]