package se.vidstige.jadb.api;

import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;
import se.vidstige.jadb.utils.IpUtils;
import se.vidstige.jadb.utils.MacHelper;
import se.vidstige.jadb.utils.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Copyright © 2021 sanbo Inc. All rights reserved.
 * @Description: wrapp adb commond
 * @Version: 1.0
 * @Create: 2021/10/08 11:29:30
 * @author: sanbo
 */
public class Jwrap {
    public static void main(String[] args) throws IOException, JadbException {
        System.out.println("获取IP: " + getIp(getDevices().get(0)));
        System.out.println("获取MAC列表: " + getBaseMac(getDevices().get(0)));
        System.out.println("获取真MAC: " + getRealMac(getDevices().get(0)));
        System.out.println("获取随机MAC: " + getRandomMac(getDevices().get(0)));
    }


    private static JadbConnection conn = new JadbConnection();


    public static List<JadbDevice> getDevices() throws IOException, JadbException {
        return conn.getDevices();
    }

    public static String getRandomMac(JadbDevice device) throws IOException, JadbException {
        Map<String, String> macs = getBaseMac(device);
        if (macs.containsKey("wlan0")) {
            return macs.get("wlan0");
        }
        return "";
    }

    public static String getRealMac(JadbDevice device) throws IOException, JadbException {
        Map<String, String> macs = getBaseMac(device);
        if (macs.containsKey("wlan1")) {
            return macs.get("wlan1");
        }
        return "";
    }

    private static Map<String, String> getBaseMac(JadbDevice device) throws IOException, JadbException {
        InputStream is = device.executeShell("ip link");
        byte[] bs = is.readAllBytes();
        if (bs == null || bs.length == 0) {
            return null;
        }
        String macInfo = new String(bs, "UTF-8");
        if (TextUtils.isEmpty(macInfo)) {
            return null;
        }
        String[] lines = null;
        if (macInfo.contains("\n")) {
            lines = macInfo.split("\n");
        } else if (macInfo.contains("\r")) {
            lines = macInfo.split("\r");
        } else if (macInfo.contains("\r\n")) {
            lines = macInfo.split("\r\n");
        }
        if (lines == null || lines.length == 0) {
            return null;
        }

        Map<String, String> p = MacHelper.getData(lines);
        if (p == null || p.size() == 0) {
            return null;
        }
        return p;
    }

    private static String getIp(JadbDevice device) throws IOException, JadbException {

        InputStream is = device.executeShell("netcfg");
        byte[] bs = is.readAllBytes();
        String result = new String(bs, "UTF-8");
        if (bs == null || bs.length == 0 || result.contains("not found")) {
            is = device.executeShell("ip address|grep wlan0");
            bs = is.readAllBytes();
            result = new String(bs, "UTF-8");
            if (bs == null || bs.length == 0 || !result.contains("wlan0")) {
                return "";
            }
        }

        result = new String(bs, "UTF-8");
        if (!TextUtils.isEmpty(result)) {
            List<String> ips = IpUtils.getIpv4(result);
            if (ips == null || ips.size() == 0) {
                result = "";
            }
            for (String ip : ips) {
                if (!TextUtils.isEmpty(ip) && !ip.endsWith("255")) {
                    return ip;
                }
            }
        }
        return result;
    }

}
