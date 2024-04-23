<h1 align="center">Python Plugin</h1>

<p align="center">
    <a href="https://github.com/seekers-dev/python-plugin/actions/workflows/gradle.yml">
        <img alt="Java CI with Gradle" src="https://github.com/seekers-dev/python-plugin/actions/workflows/gradle.yml/badge.svg">
    </a>
    <a href="https://github.com/seekers-dev/python-plugin/actions/workflows/gradle-publish.yml">
        <img alt="Gradle Package" src="https://github.com/seekers-dev/python-plugin/actions/workflows/gradle-publish.yml/badge.svg">
    </a>
    <a href="https://github.com/seekers-dev/python-plugin/actions/workflows/codeql.yml">
        <img alt="CodeQL" src="https://github.com/seekers-dev/python-plugin/actions/workflows/codeql.yml/badge.svg">
    </a>
    <img alt="GitHub License" src="https://img.shields.io/github/license/seekers-dev/python-plugin">
    <img alt="GitHub top language" src="https://img.shields.io/github/languages/top/seekers-dev/python-plugin">
    <img alt="GitHub commit activity" src="https://img.shields.io/github/commit-activity/m/seekers-dev/python-plugin">
</p>
 
The python plugin is a plugin for the seekers server that enables client hosting from python files.

## Get from release

There is currently no release, please wait.

## Build on your own

### Get this project

Download the zip file of this repository or fork and clone it.

### Install java

The build requires java 11.

Linux:
```shell
sudo apt update && apt install default-jre
```

Windows: https://www.java.com/en/download/windows_manual.jsp

You can check your installation with:
```shell
java --version
```

### Create a uber jar

You can now create your custom build of this property with the following command.

Linux:
```shell
bash ./gradlew build
```

Windows:
```bash
./gradlew.bat build
```

That's it! You can find your jar file at `build/libs`. The jar you should use for the server ends with the `dist`
classifier.