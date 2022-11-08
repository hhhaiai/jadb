# JADB #

本地adb开启服务AdbServerLauncher， java调用本地adb服务(tcpip)
ADB client implemented in pure Java.

The Android Debug Bridge (ADB) is a client-server architecture used to communicate with Android devices (install APKs, debug apps, etc).

The Android SDK Tools are available for the major platforms (Mac, Windows & Linux) and include the `adb` command line tool which implements the ADB protocol.

This projects aims at providing an up to date implementation of the ADB protocol.

[![Build Status](https://travis-ci.org/vidstige/jadb.svg?branch=master)](https://travis-ci.org/vidstige/jadb)
[![](https://jitpack.io/v/vidstige/jadb.svg)](https://jitpack.io/#vidstige/jadb)
[![codecov](https://codecov.io/gh/vidstige/jadb/branch/master/graph/badge.svg)](https://codecov.io/gh/vidstige/jadb)
[![](http://img.shields.io/badge/first--timers--only-friendly-green.svg?style=flat&colorB=FF69B4)](http://www.firsttimersonly.com/)


## APIs ##
Usage: [Api](./Api.md)


## Protocol Description ##

An overview of the protocol can be found here: [Overview](https://android.googlesource.com/platform/system/adb/+/master/OVERVIEW.TXT)

A list of the available commands that a ADB Server may accept can be found here:
[Services](https://android.googlesource.com/platform/system/adb/+/master/SERVICES.TXT)

The description for the protocol for transferring files can be found here: [SYNC.TXT](https://android.googlesource.com/platform/system/adb/+/master/SYNC.TXT).


## Using JADB in your application ##

Since version v1.1 Jadb support [maven](https://maven.apache.org/) as a build system. Although this project is not presented in official apache maven 
repositories this library can be used as dependencies in your maven/gradle project with the help of [jitpack](https://jitpack.io). 
[Jitpack](https://jitpack.io) is a system which parses github public repositories and make artifacts from them. 
You only need to add [jitpack](https://jitpack.io) as a repository to let maven/gradle to search for artifacts in it, like so

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
 
After that you will need to add actual dependency. [Jitpack](https://jitpack.io) takes groupId, artifactId and version id from repository name, 
project name and tag ignoring actual values from pom.xml. So you need to write:
 
```
<dependency>
    <groupId>com.github.vidstige</groupId>
    <artifactId>jadb</artifactId>
    <version>v1.2.1</version>
</dependency>
```
## Build

``` shell
#!/bin/sh
set -eu

docker run -it --rm               \
    -v "$(pwd)":/opt/maven        \
    -w /opt/maven maven:3.5-jdk-7 \
     mvn clean install
```
## Troubleshooting
If you cannot connect to your device check the following.

- Your adb server is running by issuing `adb start-server`
- You can see the device using adb `adb devices`

If you see the device in `adb` but not in `jadb` please file an issue on https://github.com/vidstige/jadb/.

### Workaround for Unix Sockets Adb Server

Install `socat` and issue the following to forward port 5037 to the unix domain socket.
```bash
socat TCP-LISTEN:5037,reuseaddr,fork UNIX-CONNECT:/tmp/5037
```

## Contributing ##
This project would not be where it is, if it where not for the helpful [contributors](https://github.com/vidstige/jadb/graphs/contributors)
supporting jadb with pull requests, issue reports, and great ideas. If _you_ would like to
contribute, please read through [CONTRIBUTING.md](CONTRIBUTING.md).

* If you fix a bug, try to _first_ create a failing test. Reach out to me for assistance or guidance if needed.

## Authors ##
Samuel Carlsson <samuel.carlsson@gmail.com>

See [contributors](https://github.com/vidstige/jadb/graphs/contributors) for a full list.

## License ##
This project is released under the Apache License Version 2.0, see [LICENSE.md](LICENSE.md) for more information.
