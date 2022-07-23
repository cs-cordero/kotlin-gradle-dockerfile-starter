ARG VERSION=17

FROM amazoncorretto:17 as BUILD
COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon build


FROM amazoncorretto:17

ARG SHADOW_JAR_FILE_NAME
COPY --from=BUILD \
    /src/build/libs/$SHADOW_JAR_FILE_NAME \
    /bin/runner/run.jar

WORKDIR /bin/runner

CMD ["java", "-jar", "run.jar"]