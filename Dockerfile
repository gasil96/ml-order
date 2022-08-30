FROM openjdk:11
ADD target/*.jar orderapplication
ENTRYPOINT ["java", "-jar","orderapplication"]
EXPOSE 8080