FROM openjdk:8
RUN mkdir darkspy
COPY target/darkspy-1.0-SNAPSHOT.jar /darkspy
WORKDIR /darkspy
CMD ["java", "-jar", "darkspy-1.0-SNAPSHOT.jar"]