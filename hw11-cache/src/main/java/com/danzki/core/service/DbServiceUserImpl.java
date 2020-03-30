package com.danzki.core.service;

import com.danzki.cache.HwCache;
import com.danzki.cache.HwListenerImpl;
import com.danzki.cache.MyCache;
import com.danzki.core.dao.UserDao;
import com.danzki.core.model.User;
import com.danzki.core.sessionmanager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final UserDao userDao;
  private boolean noCache;

  public DbServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
    noCache = false;
  }

  @Override
  public void setNoCache(boolean noCache) {
    this.noCache = noCache;
  }

  HwCache<String, User> cache = new MyCache<>();

  @Override
  public long create(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = userDao.create(user);
        sessionManager.commitSession();

        if (!noCache) {
          cache.addListener(new HwListenerImpl<String, User>(Long.toString(userId), user));
          cache.put(Long.toString(userId), user);
        }

        logger.info("created user: {}", userId);
        return userId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public Optional<User> load(long id) {
    if (!noCache) {
      User cachedUser = cache.get(Long.toString(id));
      if (cachedUser != null) {
        return Optional.of(cachedUser);
      }
    }

    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.load(id);

        logger.info("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

  @Override
  public void update(User user) {
    if (!noCache) {
      User cachedUser = cache.get(Long.toString(user.getId()));
      if (cachedUser != null) {
        cache.put(Long.toString(user.getId()), user);
      }
    }

    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        userDao.update(user);
        sessionManager.commitSession();

        logger.info("updated user: {}", user.toString());
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }
}
