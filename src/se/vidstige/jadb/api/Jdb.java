package se.vidstige.jadb.api;

import se.vidstige.jadb.*;
import se.vidstige.jadb.managers.Package;
import se.vidstige.jadb.managers.PackageManager;
import se.vidstige.jadb.managers.PropertyManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: wrapping adb commond
 * @Version: 1.0
 * @Create: 2021/10/08 11:28:42
 * @author: sanbo
 */
public class Jdb {

    private static JadbConnection conn = new JadbConnection();

    public static List<JadbDevice> getDevices() throws IOException, JadbException {
        return conn.getDevices();
    }

    public static InetSocketAddress connect(String ip, int port) throws ConnectionToRemoteDeviceException, IOException, JadbException {
        return conn.connectToTcpDevice(new InetSocketAddress(ip, port));
    }

    public static InetSocketAddress disconnect(String ip, int port) throws ConnectionToRemoteDeviceException, IOException, JadbException {
        return conn.disconnectFromTcpDevice(new InetSocketAddress(ip, port));
    }

    public static void install(JadbDevice device, File localFile) throws IOException, JadbException {
        new PackageManager(device).install(localFile);
    }

    public static void uninstall(JadbDevice device, String pkgName) throws IOException, JadbException {
        new PackageManager(device).uninstall(new Package(pkgName));
    }

    public static void launch(JadbDevice device, String pkgName) throws IOException, JadbException {
        new PackageManager(device).launch(new Package(pkgName));
    }

    public static List<Package> getPackages(JadbDevice device) throws IOException, JadbException {
        return new PackageManager(device).getPackages();
    }

    public static void push(JadbDevice device, File localFile, String remotePath) throws IOException, JadbException {
        device.push(localFile, new RemoteFile(remotePath));
    }

    public static void pull(JadbDevice device, String remotePath, File localFile) throws IOException, JadbException {
        device.pull(new RemoteFile(remotePath), localFile);
    }

    public static InputStream executeShell(JadbDevice device, String command, String... args) throws IOException, JadbException {
        return device.executeShell(command, args);
    }

    public static void tcpip(JadbDevice device) throws IOException, JadbException {
        device.enableAdbOverTCP();
    }

    public static void tcpip(JadbDevice device, int port) throws IOException, JadbException {
        device.enableAdbOverTCP(port);
    }

    public static String getSerial(JadbDevice device) throws IOException, JadbException {
        return device.getSerial();
    }

    public static JadbDevice.State getState(JadbDevice device) throws IOException, JadbException {
        return device.getState();
    }

    public static Map<String, String> getprop(JadbDevice device) throws IOException, JadbException {
        return new PropertyManager(device).getprop();
    }

    public static List<RemoteFile> list(JadbDevice device, String remotePath) throws IOException, JadbException {
        return device.list(remotePath);
    }
}
