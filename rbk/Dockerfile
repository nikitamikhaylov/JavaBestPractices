FROM openjdk:8
RUN mkdir rbk
COPY target/rbk-1.0-SNAPSHOT.jar /rbk
WORKDIR /rbk
CMD ["java", "-jar", "rbk-1.0-SNAPSHOT.jar"]
