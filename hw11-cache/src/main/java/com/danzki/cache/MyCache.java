package com.danzki.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
  //Надо реализовать эти метод
  private Map<K, V> cacheData = new WeakHashMap<>();
  private List<HwListener> listerners = new ArrayList<>();

  @Override
  public void put(K key, V value) {
    cacheData.put(key, value);
    for (HwListener listener : listerners) {
      listener.notify(key, value, "put");
    }
  }

  @Override
  public void remove(K key) {
    cacheData.remove(key);
    for (HwListener listener : listerners) {
      listener.notify(key, null, "put");
    }
  }

  @Override
  public V get(K key) {
    return cacheData.get(key);
  }

  @Override
  public void addListener(HwListener<K, V> listener) {
    listerners.add(listener);
  }

  @Override
  public void removeListener(HwListener<K, V> listener) {
    listerners.remove(listener);
  }
}
