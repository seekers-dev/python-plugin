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

val seekersVersion = "-SNAPSHOT"
val pf4jVersion = "3.11.0"
val ini4jVersion = "0.5.4"

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.github.seekers-dev:seekers-server:$seekersVersion")
    implementation("org.pf4j:pf4j:${pf4jVersion}")
    implementation("org.ini4j:ini4j:${ini4jVersion}")

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
    description = "Create uber jar"
    group = "build"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Plugin-Class"] = "org.seekers.python.PythonPlugin"
        attributes["Plugin-Id"] = "python-plugin"
        attributes["Plugin-Provider"] = "Seekers Contributors"
        attributes["Plugin-Version"] = version.toString()
    }

    archiveBaseName.set(project.name)
    archiveClassifier.set("uber")

    with(tasks.named<Jar>("jar").get())
    dependsOn(configurations.runtimeClasspath)
    val libs = arrayOf("kotlin-stdlib")

    fun matchesAny(name: String): Boolean {
        for (lib in libs) {
            if (name.contains(lib)) return true
        }
        return false
    }

    from(configurations.runtimeClasspath.get().filter{ matchesAny(it.name) }
        .map { if (it.isDirectory) it else zipTree(it) })
}
