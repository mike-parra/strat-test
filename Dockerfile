# Before building the container image run:
# ./mvnw package -Pnative

# Use Quarkus Image
FROM quay.io/quarkus/quarkus-micro-image:2.0
# Set the working directory
WORKDIR /work/

# Copy native executable into the container and raname to application
COPY target/*-runner /work/application

#Giving permissions
RUN chmod 775 /work

#Expose Port
EXPOSE 8080

# Startup command to run the native executable
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]