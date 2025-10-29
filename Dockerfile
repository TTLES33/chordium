FROM openjdk:21
ARG JAR_FILE=build/libs/chordium.jar
COPY ${JAR_FILE} app.jar
COPY cacheFiles cacheFiles
COPY chordDatabase.db chordDatabase.db
ENTRYPOINT ["java","-jar","/app.jar"]