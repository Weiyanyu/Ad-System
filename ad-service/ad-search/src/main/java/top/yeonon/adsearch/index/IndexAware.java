package top.yeonon.adsearch.index;

/**
 * @Author yeonon
 * @date 2019/3/27 0027 20:00
 **/
public interface IndexAware<K, V> {

    V get(K key);

    void add(K key, V value);

    void update(K key, V value);

    void delete(K key, V value);
}
