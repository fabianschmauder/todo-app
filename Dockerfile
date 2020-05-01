FROM openjdk:11

ENV ENVIRONMENT=prod

MAINTAINER Fabian Schmauder <fabian.schmauder@gmail.com>

ADD backend/target/app.jar app.jar

RUN bash -c 'touch /app.jar'

CMD [ "sh", "-c", "java -Dserver.port=$PORT -Dspring.data.mongodb.uri=$MONGODB_URI -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
