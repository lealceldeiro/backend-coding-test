FROM maven:3.8.2-openjdk-11 as BUILD_STAGE
COPY pom.xml .
RUN mvn -B -f pom.xml dependency:go-offline

COPY src ./src
RUN mvn -B clean package

FROM openjdk:11-jre-slim as RUN_STAGE
# https://spring.io/guides/gs/spring-boot-docker/
RUN mkdir -p "/rp_springbootapp"
RUN adduser --system --group rp_springbootapp
RUN chown -R rp_springbootapp /rp_springbootapp

COPY --from=BUILD_STAGE target/rp_springbootapp.jar /rp_springbootapp/rp_springbootapp.jar

LABEL author="Asiel Leal Celdeiro"
EXPOSE 8443

USER rp_springbootapp:rp_springbootapp

ENTRYPOINT java -jar /rp_springbootapp/rp_springbootapp.jar
