FROM openjdk:latest AS builder
LABEL authors="hesamdavarpanah"

RUN apt-get -y update && apt-get upgrade && apt-get clean
RUN apt-get -y install netcat-traditional && apt-get install -y net-tools && apt-get install -y iputils-ping && apt-get install -y zsh


WORKDIR /code

COPY . .

RUN ./mvnw -Dmaven.test.skip=true clean package

FROM openjdk:latest AS final

WORKDIR /code

ENTRYPOINT ["java", "-jar", "/sahmeto.jar"]

COPY --from=builder /code/target/*.jar sahmeto.jar

RUN chmod +x ./run.sh
CMD ["run.sh"]