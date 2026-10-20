FROM gradle:8.10.2-jdk17 AS build
WORKDIR /workspace
COPY settings.gradle.kts build.gradle.kts ./
COPY backend ./backend
RUN gradle :backend:installDist --no-daemon
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/backend/build/install/backend/ ./
ENV PORT=8080
EXPOSE 8080
CMD ["./bin/backend"]
