FROM eclipse-temurin:17-jdk-focal as build
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN ./mvnw package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /usr/app
COPY --from=build /usr/src/app/target/campaign-ms-*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]