package com.danzki.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  private static Logger logger = LoggerFactory.getLogger(IndexController.class);

  public IndexController() {
    logger.info("Index Controller constructor run.");
  }

  @GetMapping("/")
  public String indexPage() {
    return "index";
  }
}
