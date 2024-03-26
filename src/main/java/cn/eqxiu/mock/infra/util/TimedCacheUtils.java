package cn.eqxiu.mock.infra.util;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;

public class TimedCacheUtils {

    public static final TimedCache<String, String> timedCache = CacheUtil.newTimedCache(1000 * 60 * 5);
}
