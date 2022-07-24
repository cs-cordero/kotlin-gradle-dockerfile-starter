ARG VERSION=17

FROM amazoncorretto:17 as COMPILE_PROJECT
COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon build


FROM amazoncorretto:17

ARG SHADOW_JAR_FILE_NAME
COPY --from=COMPILE_PROJECT \
    /src/build/libs/$SHADOW_JAR_FILE_NAME \
    /bin/runner/run.jar

WORKDIR /bin/runner

CMD ["java", "-jar", "run.jar"]