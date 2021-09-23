package se.vidstige.jadb;

import se.vidstige.jadb.managers.Package;
import se.vidstige.jadb.managers.PackageManager;
import se.vidstige.jadb.managers.PropertyManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JAdb {

    private static JadbConnection jadb = new JadbConnection();

    public static List<JadbDevice> getDevices() throws IOException, JadbException {
        return jadb.getDevices();
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
      return   device.executeShell(command, args);
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
