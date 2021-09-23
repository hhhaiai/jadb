# API用法

> 确保`adb server`是运行的，可以执行命令行命令`adb devices`

### 获取设备列表

``` java
// 方式一
JadbConnection jadb = new JadbConnection()
List<JadbDevice> devices = jadb.getDevices()
// 方式二
List<JadbDevice> devices=JAdb.getDevices()
```

### 安装列表

* 安装

``` java
// 方式一
JAdb.install(JadbDevice device, File localFile)
//方式二
new PackageManager(device).install(localFile)
```

* 卸载

``` java
// 方式一
JAdb.uninstall(JadbDevice device, String pkgName)
//方式二
 new PackageManager(device).uninstall(new Package(pkgName))
```

* 启动

``` java
// 方式一
JAdb.launch(JadbDevice device, String pkgName)
//方式二
new PackageManager(device).launch(new Package(pkgName))
```

* 查询安装列表

``` java
// 方式一
List<Package> pkgs = JAdb.getPackages(JadbDevice device)
//方式二
new PackageManager(device).getPackages()
```

### shell指令相关

* adb tcpip

``` java
// 方式一
JAdb.tcpip(JadbDevice device) 
// 方式二
JadbDevice().enableAdbOverTCP()
// 方式三
JadbDevice().tcpip()
```

* adb push

``` java
// 方式一
JAdb.push(JadbDevice device, File localFile, String remotePath)
// 方式二
JadbDevice().push(File local, RemoteFile remote)
```

* adb pull

``` java
// 方式一
JAdb.pull(JadbDevice device, String remotePath, File localFile)
// 方式二
JadbDevice().pull(RemoteFile remote, File local)
```

* adb shell

``` java
// 方式一
InputStream is = JAdb.executeShell(JadbDevice device, String command, String... args)
// 方式二
InputStream is = JadbDevice().executeShell(String command, String... args)
```

