package com.esdnl.photocopier.bean;

public class SupplierBean 
{
  private int id;
  private String name;
  
  public SupplierBean()
  {
    this.id = -1;
    this.name = null;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String toString()
  {
    return this.name;
  }
  
  public boolean equals(Object o)
  {
    boolean check = false;
    
    if(o instanceof BrandBean)
    {
      if(this.getId() == ((SupplierBean) o).getId())
        check = true;
    }
    
    return check;
  }
}