plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("kapt") version "1.9.23"
}
buildscript {
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.4.21"))
    }
}

group = "org.seekers.python"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

apply(plugin = "org.jetbrains.kotlin.jvm")

val seekersVersion = "47cfdb1"
val pf4jVersion = "3.11.0"

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly("com.github.seekers-dev:seekers-server:$seekersVersion")

    kapt("org.pf4j:pf4j:${pf4jVersion}")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}
kotlin {
    jvmToolchain(11)
}

tasks.test {
    useJUnitPlatform()
}

tasks.named("build") {
    dependsOn(tasks.named("plugin"))
}
tasks.register<Jar>("plugin") {
    description = "Create plugin jar"
    group = "plugin"
    duplicatesStrategy = DuplicatesStrategy.WARN

    manifest {
        attributes["Plugin-Class"] = "org.seekers.python.PythonPlugin"
        attributes["Plugin-Id"] = "python-plugin"
        attributes["Plugin-Provider"] = "Seekers Contributors"
        attributes["Plugin-Version"] = version.toString()
    }

    archiveBaseName.set(project.name)
    archiveClassifier.set("dist")

    //into("classes") {
        with(tasks.named<Jar>("jar").get())
    //}
    dependsOn(configurations.runtimeClasspath)
    into("lib") {
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }
        })
    }
}
