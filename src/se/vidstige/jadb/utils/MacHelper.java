package se.vidstige.jadb.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacHelper {

    public static String getMac(String line) {
        if (!TextUtils.isEmpty(line) && line.contains(" ")) {
            String[] ss = line.split(" ");
            if (ss != null && ss.length > 0) {
                for (int i = 0; i < ss.length; i++) {
                    String one = ss[i];
                    if (!TextUtils.isEmpty(one)
                            && one.contains(":")
                            && !"ff:ff:ff:ff:ff:ff".equals(one)
                            && !"00:00:00:00:00:00".equals(one)
                            && !"::".equals(one)
                    ) {
                        return one;
                    }
                }
            }
        }
        return "";
    }


    /**
     * name: lines
     * </p>
     * "lo":["1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000",
     * "link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00" ]
     */
    private static Map<String, List<String>> map = new HashMap();

    private static String proName = null;
    private static List<String> proLines = null;

    /**
     * 获取网络类型,和mac值
     *
     * @param lines
     * @return
     */
    public static Map<String, String> getData(String[] lines) {
        map.clear();
        for (String line : lines) {
            if (isStartWithNumber(line)) {
                // close will
                if (!TextUtils.isEmpty(proName) && proLines != null && proLines.size() > 1) {
                    map.put(proName, proLines);
                }
                //start new protocol
                proLines = new ArrayList<String>();
                proLines.add(line);
                proName = getNetName(line);
//                System.out.println(line);
//                System.out.println(proName);
            } else {
                proLines.add(line);
//                System.out.println("xxxxxxx" + line);
            }

        }
        proName = null;
        proLines.clear();
        proLines = null;

        Map<String, String> protocolMac = new HashMap<String, String>();
//        System.out.println("parser over. map:" + map.toString());
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> pros = entry.getValue();
            if (pros.size() < 2) {
                continue;
            }
            String mac = getMac(pros.get(1));
            if (TextUtils.isEmpty(mac)) {
                continue;
            }
            String protoName = entry.getKey();
            protocolMac.put(protoName, mac);
        }
        map.clear();
        return protocolMac;
    }

    private static String getNetName(String line) {
        if (line.contains(":")) {
            String[] as = line.split(":");
            if (as != null && as.length > 2) {
                return as[1].trim().replaceAll(" ", "");
            }
        }
        return "";
    }

    public static boolean isStartWithNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]:*");
        Matcher isNum = pattern.matcher(str.charAt(0) + "");
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
