FROM gcr.io/distroless/java  
COPY target/*.jar /usr/app/backend.jar  
EXPOSE 8080 
ENTRYPOINT ["java","-jar","/usr/app/backend.jar","--server.port=8080"]