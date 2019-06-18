package com.awsd.efile.equestion;

public class QuestionType 
{
  private int type_id;
  private String type_name;
  
  public QuestionType(int type_id, String type_name)
  {
    this.type_id = type_id;
    this.type_name = type_name;
  }

  public int getTypeID()
  {
    return this.type_id;
  }

  public void setTypeID(int type_id)
  {
    this.type_id = type_id;
  }

  public String getTypeName()
  {
    return this.type_name;
  }

  public void setTypeName(String type_name)
  {
    this.type_name = type_name;
  }
}