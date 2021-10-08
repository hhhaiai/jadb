# API用法

> 确保`adb server`是运行的，可以执行命令行命令`adb devices`
>
> 确定几个事:
> * 标准ADB命令(adb 命令)在Jdb
> * 获取详情命令(获取IP、获取分辨率等)在Jwarp

### 连接

* adb connect

``` java
Jdb.connect(String ip, int port)
// or
new JadbConnection().connectToTcpDevice(InetSocketAddress inetSocketAddress)
```

* adb disconnect

``` java
Jdb.disconnect(String ip, int port)
// or
new JadbConnection().disconnectFromTcpDevice(InetSocketAddress tcpAddressEntity)
```

### 获取设备列表

``` java
// 方式一
JadbConnection Jdb = new JadbConnection()
List<JadbDevice> devices = Jdb.getDevices()
// 方式二
List<JadbDevice> devices=Jdb.getDevices()
```

### 安装列表

* 安装

``` java
// 方式一
Jdb.install(JadbDevice device, File localFile)
//方式二
new PackageManager(device).install(localFile)
```

* 卸载

``` java
// 方式一
Jdb.uninstall(JadbDevice device, String pkgName)
//方式二
 new PackageManager(device).uninstall(new Package(pkgName))
```

* 启动

``` java
// 方式一
Jdb.launch(JadbDevice device, String pkgName)
//方式二
new PackageManager(device).launch(new Package(pkgName))
```

* 查询安装列表

``` java
// 方式一
List<Package> pkgs = Jdb.getPackages(JadbDevice device)
//方式二
new PackageManager(device).getPackages()
```

### shell指令相关

* adb tcpip

``` java
// 方式一
Jdb.tcpip(JadbDevice device) 
// 方式二
JadbDevice().enableAdbOverTCP()
// 方式三
JadbDevice().tcpip()
```

* adb push

``` java
// 方式一
Jdb.push(JadbDevice device, File localFile, String remotePath)
// 方式二
JadbDevice().push(File local, RemoteFile remote)
```

* adb pull

``` java
// 方式一
Jdb.pull(JadbDevice device, String remotePath, File localFile)
// 方式二
JadbDevice().pull(RemoteFile remote, File local)
```

* adb shell

``` java
// 方式一
InputStream is = Jdb.executeShell(JadbDevice device, String command, String... args)
// 方式二
InputStream is = JadbDevice().executeShell(String command, String... args)
```

