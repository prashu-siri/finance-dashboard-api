#
# Build stage
#
FROM maven:3.8.6-amazoncorretto-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/finance-dashboard-0.0.1-SNAPSHOT.jar finance-dashboard.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","finance-dashboard.jar"]
