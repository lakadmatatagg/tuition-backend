FROM eclipse-temurin:21-jdk-jammy as builder

WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY common-313 ./common-313
COPY mapper-313 ./mapper-313
COPY repository-313 ./repository-313
COPY service-313 ./service-313
COPY web-313 ./web-313

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
RUN ./mvnw clean install -DskipTests


FROM eclipse-temurin:21-jdk-jammy

WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder opt/app/web-313/target/*.jar /opt/app/*.jar

ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]