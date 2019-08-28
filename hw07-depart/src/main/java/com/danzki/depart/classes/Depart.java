package com.danzki.depart.classes;

import com.danzki.atm.Atmable;
import com.danzki.atm.classes.Atm;
import com.danzki.depart.Departable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Depart implements Departable {
  @Getter
  private List<Atmable> atmList = new ArrayList<>();
  private Map<Integer, ConfigSaver> defaultAtmConfig;

  @Override
  public void addAtm(Atmable atm) {
    atmList.add(atm);
  }

  public Depart() {
    if (defaultAtmConfig == null) {
      defaultAtmConfig = new HashMap<Integer, ConfigSaver>();
    }
  }

  @Override
  public Integer getAtmStatement(Atmable atm) {
    var atmDoVisitor = new AtmDoVisitor();
    return atmDoVisitor.giveStatement(((Atm) atm));
  }

  @Override
  public void restoreAtm(Atmable atm) {
    var currAtm = (Atm) atm;
    var cfgSaver = defaultAtmConfig.get(currAtm.getAtmId());
    atm.restoreConfig(cfgSaver.getAtmConfig());
  }

  @Override
  public void saveAtm(Atmable atm) {
    var currAtm = (Atm) atm;
    var cfgSaver = new ConfigSaver();
    cfgSaver.setAtmConfig(atm.saveConfig());
    defaultAtmConfig.put(currAtm.getAtmId(), cfgSaver);
  }

  public void  configureAtm(Atmable atm, String config) {
    ((Atm) atm).setConfig(config);
  }

  public String getAtmConfig(Atmable atm) {
    return ((Atm) atm).getConfig();
  }
}
