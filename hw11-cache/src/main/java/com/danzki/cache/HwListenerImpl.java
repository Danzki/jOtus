package com.danzki.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HwListenerImpl<K, V> implements HwListener {
  private static Logger logger = LoggerFactory.getLogger(HwListenerImpl.class);

  public HwListenerImpl(K key, V value) {
  }

  @Override
  public void notify(Object key, Object value, String action) {
    logger.info("Cache: action: {}, Key: {}, Value: {}", action, key, value);
  }
}
