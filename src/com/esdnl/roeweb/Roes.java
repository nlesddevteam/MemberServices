package com.esdnl.roeweb;

import java.util.*;

public class Roes 
{
  private TreeMap roes;
  
  public Roes()
  {
    roes = new TreeMap();
  }
  
  public void addRoeFileLine(RoeFileLine roe_file_line)
  {
    Roe roe_data = null;
    
    if(roes.containsKey(roe_file_line.getSIN()))
    {
      roe_data = (Roe) roes.get(roe_file_line.getSIN());
    }
    else
    {
      roe_data = new Roe();
      roes.put(roe_data.getSIN(), roe_data);
    }
    
    roe_data.addRoeFileLine(roe_file_line);
  }
  
  public void addDemographicFileLine(DemographicFileLine demo_file_line)
  {
    Roe roe_data = null;
    
    if(roes.containsKey(demo_file_line.getSIN()))
    {
      roe_data = (Roe) roes.get(demo_file_line.getSIN());
    }
    else
    {
      roe_data = new Roe();
      roes.put(roe_data.getSIN(), roe_data);
    }
    
    roe_data.setDemographicFileLine(demo_file_line);
  }
  
  public Roe[] getRoes()
  {
    return ((Roe[]) this.roes.values().toArray(new Roe[0]));
  }
}