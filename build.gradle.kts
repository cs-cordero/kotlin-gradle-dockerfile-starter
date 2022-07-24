import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

plugins {
    kotlin("jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.bmuschko.docker-remote-api") version "6.7.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("MainKt")
}

tasks.jar {
    manifest {
        attributes(mapOf(
            "Main-Class" to application.mainClass
        ))
    }
}

val shadowJarFileName = "${project.name}-${project.version}.jar"

tasks.shadowJar {
    dependsOn(tasks.jar)

    // See https://imperceptiblethoughts.com/shadow/configuration/#configuring-output-name
    archiveFileName.set(shadowJarFileName)
}

tasks.distTar { dependsOn(tasks.shadowJar) }
tasks.distZip { dependsOn(tasks.shadowJar) }
tasks.startScripts { dependsOn(tasks.shadowJar) }

/**
 * See https://bmuschko.github.io/gradle-docker-plugin/api/com/bmuschko/gradle/docker/tasks/image/DockerBuildImage.html
 */
tasks.create("buildImage", DockerBuildImage::class) {
    dependsOn(tasks.build)

    buildArgs.set(mapOf("SHADOW_JAR_FILE_NAME" to shadowJarFileName))

    // defines the docker build context
    inputDir.set(project.projectDir)

    val imageRepository = project.properties["imageRepository"]!!
    val imageName = project.properties["imageName"] ?: project.name

    val tagVersion = "$imageRepository/$imageName:${project.version}"
    val tagLatest = "$imageRepository/$imageName:latest"

    images.add(tagVersion)
    if (project.hasProperty("createLatestTag")) {
        images.add(tagLatest)
    }
}