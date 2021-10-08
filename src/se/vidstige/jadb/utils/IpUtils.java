package se.vidstige.jadb.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtils {
    private static Pattern VALID_IPV4_PATTERN = null;
    private static Pattern VALID_IPV4_PATTERN2 = null;
    private static Pattern VALID_IPV6_PATTERN = null;
    private static final String ipv4Pattern =
            "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
    private static final String ipv4Pattern2 = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

    static {
        try {
            VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
            VALID_IPV4_PATTERN2 = Pattern.compile(ipv4Pattern2, Pattern.CASE_INSENSITIVE);
            VALID_IPV6_PATTERN = Pattern.compile(ipv6Pattern, Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isIpAddress(String ipAddress) {

        Matcher m1 = IpUtils.VALID_IPV4_PATTERN.matcher(ipAddress);
        if (m1.matches()) {
            return true;
        }
        Matcher m2 = IpUtils.VALID_IPV6_PATTERN.matcher(ipAddress);
        return m2.matches();
    }


    public static List<String> getIpv4(String ipinfo) {
        List<String> list = new ArrayList<String>();
        Matcher matcher = VALID_IPV4_PATTERN.matcher(ipinfo);
        if (matcher.groupCount() > 0) {
//            System.out.println("[plan A]get ip. ");
            while (matcher.find()) {
                String ip = matcher.group();
                if (!list.contains(ip)) {
                    list.add(matcher.group());
                }
            }

        } else {
            matcher = VALID_IPV4_PATTERN2.matcher(ipinfo);
            if (matcher.groupCount() > 0) {
//                System.out.println("[plan B]get ip.");
                while (matcher.find()) {
                    String ip = matcher.group();
                    if (!list.contains(ip)) {
                        list.add(matcher.group());
                    }
                }
            }

        }

        return list;
    }

}