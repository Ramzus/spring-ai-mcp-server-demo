FROM gradle:jdk21-corretto AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Import the ADEO certificate into the JVM's truststore
RUN keytool -import -trustcacerts -alias adeo-root-ca \
    -file src/main/resources/certs/ADEO_ROOT_CA1.crt \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit -noprompt

RUN gradle bootJar

FROM openjdk:21-jdk-slim
RUN addgroup --system spring && adduser --system spring --ingroup spring

# Import the ADEO certificate into the JVM's truststore in the runtime image
COPY --from=build /home/gradle/src/src/main/resources/certs/ADEO_ROOT_CA1.crt /tmp/ADEO_ROOT_CA1.crt
RUN apt-get update && apt-get install -y --no-install-recommends curl && \
    keytool -import -trustcacerts -alias adeo-root-ca \
    -file /tmp/ADEO_ROOT_CA1.crt \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit -noprompt && \
    apt-get purge -y --auto-remove curl && \
    rm -rf /var/lib/apt/lists/* && \
    rm /tmp/ADEO_ROOT_CA1.crt

USER spring:spring
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]