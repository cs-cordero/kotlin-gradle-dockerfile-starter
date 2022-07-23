# A Starter Template for Kotlin-Gradle-Dockerfile setups

## Setup

```shell
$ cd /path/to/Projects
$ git clone --depth 1 https://github.com/cs-cordero/kotlin-gradle-dockerfile-starter.git [your-new-project]
$ cd [your-new-project]
$ rm -r .git
$ git init
$ git add .
$ git commit -m "Bootstrapped from https://github.com/cs-cordero/kotlin-gradle-dockerfile-starter"
```

## Post-Setup
Open the [gradle.properties](./gradle.properties) file and change the following values to what you want:
* **name**.  The project name, used in many places, notably the name of the shadow jar will be `"${project.name}-${project.version}.jar"`
* **version**. The project version, used in many places, notably the name of the shadow jar will be `"${project.name}-${project.version}.jar"`
* **group**.  See [here](https://stackoverflow.com/questions/23354243/what-is-group-property-for-in-gradle).
* **imageRepository**.  A component of the image tag (`repo/name:version`)
* **imageName**.  A component of the image tag (`repo/name:version`).  Defaults to `project.name`.
* **createLatestTag**.  If this is set to any value, `buildImage` will also create a `:latest` image tag.  Remove it entirely if you don't want this.

## Commands

```shell
$ ./gradlew build
$ ./gradlew run
$ ./gradlew buildImage
```

## Features
* Comes equipped with a [build.gradle.kts](./build.gradle.kts) that configures
  a [Shadow Jar](https://imperceptiblethoughts.com/shadow) plugin to include the
  Kotlin stdlib when creating Jar.
* Also configures the `buildImage` task, which will create a Docker image for you.


## Author

[Christopher Sabater Cordero](https://chrisdoescoding.com)