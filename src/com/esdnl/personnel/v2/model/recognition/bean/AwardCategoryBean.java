package com.esdnl.personnel.v2.model.recognition.bean;

public class AwardCategoryBean 
{
  private int categoryId;
  private String categoryName;

  public AwardCategoryBean()
  {
  }

  public int getCategoryId()
  {
    return categoryId;
  }

  public void setCategoryId(int newCategoryId)
  {
    categoryId = newCategoryId;
  }

  public String getCategoryName()
  {
    return categoryName;
  }

  public void setCategoryName(String newCategoryName)
  {
    categoryName = newCategoryName;
  }
  
  public boolean equals(Object o)
  {
    boolean check = false;
    
    if(o instanceof AwardCategoryBean)
    {
      if(((AwardCategoryBean)o).getCategoryId() == this.categoryId)
        check = true;
    }
    
    return check;
  }
}