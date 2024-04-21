FROM openjdk:17

COPY build/libs/announcementApp-0.0.1-SNAPSHOT.jar /app/

CMD ["java","-jar","/app/announcementApp-0.0.1-SNAPSHOT.jar"]


