FROM openjdk:21
ARG JAR_FILE=build/libs/chordium.jar
COPY ${JAR_FILE} app.jar
COPY cacheFiles cacheFiles
ENTRYPOINT ["java","-jar","/app.jar"]