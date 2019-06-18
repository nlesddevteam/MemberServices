package com.esdnl.personnel.v2.model.recognition.bean;

public class AwardTypeBean 
{
  private int typeId;
  private int categoryId;
  private String awardName;

  public AwardTypeBean()
  {
  }

  public int getTypeId()
  {
    return typeId;
  }

  public void setTypeId(int newTypeId)
  {
    typeId = newTypeId;
  }

  public int getCategoryId()
  {
    return categoryId;
  }

  public void setCategoryId(int newCategoryId)
  {
    categoryId = newCategoryId;
  }

  public String getAwardName()
  {
    return awardName;
  }

  public void setAwardName(String newAwardName)
  {
    awardName = newAwardName;
  }
}