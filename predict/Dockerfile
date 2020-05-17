FROM openjdk:8
RUN mkdir predict
COPY target/predict-1.0-SNAPSHOT.jar /predict
WORKDIR /predict
CMD ["java", "-jar", "predict-1.0-SNAPSHOT.jar"]