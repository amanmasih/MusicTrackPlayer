FROM openjdk
WORKDIR usr/src
EXPOSE 8085
ADD ./target/authenticationservice-0.0.1-SNAPSHOT.jar /usr/src/authenticationservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","authenticationservice-0.0.1-SNAPSHOT.jar"]