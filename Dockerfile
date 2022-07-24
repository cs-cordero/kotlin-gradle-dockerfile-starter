ARG VERSION=17

FROM amazoncorretto:17 as COMPILE_PROJECT
COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon build


FROM amazoncorretto:17 as WITH_CHROME_AND_CHROMEDRIVER

RUN touch /etc/yum.repos.d/google-chrome.repo \
    && echo -e "[google-chrome]\nname=google-chrome\nbaseurl=http://dl.google.com/linux/chrome/rpm/stable/\$basearch\nenabled=1\ngpgcheck=1\ngpgkey=https://dl-ssl.google.com/linux/linux_signing_key.pub" >> /etc/yum.repos.d/google-chrome.repo \
    && yum install -y curl unzip wget libxcb google-chrome-stable \
    && DRIVER_VERSION=`curl --silent https://chromedriver.storage.googleapis.com/LATEST_RELEASE` \
    && curl --silent "https://chromedriver.storage.googleapis.com/$DRIVER_VERSION/chromedriver_linux64.zip" -o /tmp/chromedriver_linux64.zip \
    && unzip /tmp/chromedriver_linux64.zip -d /bin \
    && chmod +x /bin/chromedriver


FROM WITH_CHROME_AND_CHROMEDRIVER

ARG SHADOW_JAR_FILE_NAME
COPY --from=COMPILE_PROJECT \
    /src/build/libs/$SHADOW_JAR_FILE_NAME \
    /bin/runner/run.jar

WORKDIR /bin/runner

CMD ["java", "-jar", "run.jar"]