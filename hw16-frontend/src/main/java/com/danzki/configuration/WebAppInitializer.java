package com.danzki.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  private static Logger logger = LoggerFactory.getLogger(WebAppInitializer.class);

  @Override
  protected Class<?>[] getRootConfigClasses() {
    logger.info("getRootConfigClasses");
    return new Class<?>[]{};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    logger.info("getServletConfigClasses");
    return new Class<?>[]{WebConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    logger.info("getServletMappings");
    return new String[]{"/"};
  }

  @Override
  protected Filter[] getServletFilters() {
    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
    encodingFilter.setEncoding("UTF-8");
    encodingFilter.setForceEncoding(true);
    return new Filter[]{encodingFilter};
  }
}
