package edu.nciae.order.utils;

import edu.nciae.common.utils.SnowflakeIdWorker;

public class SnowflakeIdUtils {
    private static SnowflakeIdWorker snowFlake = new SnowflakeIdWorker(1, 1);

    public static Long getNextId() {
        return snowFlake.nextId();
    }

}
