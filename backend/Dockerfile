# Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
ENV GRADLE_OPTS="-Dorg.gradle.vfs.watch=false -Dorg.gradle.daemon=false"
RUN chmod +x ./gradlew
RUN ./gradlew -x test bootJar --no-daemon
RUN jar="$(ls -S build/libs/*.jar | head -n1)" && mv "$jar" app.jar

# Run
FROM eclipse-temurin:21-jre-alpine AS run
WORKDIR /app
COPY --from=build /app/app.jar app.jar
RUN addgroup -S app && adduser -S app -G app
USER app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]