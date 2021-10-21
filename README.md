# Java Sockets Lab - Basic Chat App

[![Gradle Build](https://github.com/edwfeng/basic-chat-app/actions/workflows/gradle.yml/badge.svg)](https://github.com/edwfeng/basic-chat-app/actions/workflows/gradle.yml)

A Java-based chat application that features:
* Multiple chatrooms
* Private rooms with access control and invites
* Commands like `/me` and `/help`

## Getting Started

### Quick Start

[Download the server JAR](https://github.com/edwfeng/basic-chat-app/suites/4124396306/artifacts/105506688), then connect to it with [the client JAR](https://github.com/edwfeng/basic-chat-app/suites/4124396306/artifacts/105506687).

### Building with Gradle

To build the server and client, use:

```
./gradlew build
```

(Use `gradlew.bat` if you're on Windows.)

To run the server, use:

```
./gradlew server
```

To run the client, use:

```
./gradlew client
```

To generate JAR files, use:

```
./gradlew serverJar
./gradlew clientJar
```