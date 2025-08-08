FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY /applications/app-service/build/libs/nequi-franchise-api.jar nequi-franchise-api.jar
ENV JAVA_OPTS="-XX:MaxRAMPercentage=70 -XX:+ExitOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom"
# Replace with a non-root user to avoid running the container with excessive privileges
# USER appuser
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar nequi-franchise-api.jar" ]
