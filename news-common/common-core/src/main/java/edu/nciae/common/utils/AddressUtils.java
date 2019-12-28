package edu.nciae.common.utils;

import edu.nciae.common.json.JSONObject;
import edu.nciae.common.json.JSON;
import edu.nciae.common.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 获取地址类
 */
@Slf4j
public class AddressUtils {
    public static final String IP_URL = "http://news.nciae.edu";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
        if (StringUtils.isEmpty(rspStr)) {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }
        JSONObject obj;
        try {
            obj = JSON.unmarshal(rspStr, JSONObject.class);
            JSONObject data = obj.getObj("data");
            address = data.getStr("address");
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return address;
    }

}
