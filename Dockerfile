FROM openjdk:21
ARG JAR_FILE=build/libs/chordium.jar
COPY ${JAR_FILE} app.jar
COPY cacheFiles cacheFiles
COPY chordDatabase.db chordDatabase.db
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
