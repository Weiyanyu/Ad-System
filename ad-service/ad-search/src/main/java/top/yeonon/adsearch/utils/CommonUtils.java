package top.yeonon.adsearch.utils;


import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author yeonon
 * @date 2019/3/27 0027 20:55
 **/
public class CommonUtils {

    public static <K,V> V getOrCreate(K key, Map<K, V> map,
                                      Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

}
