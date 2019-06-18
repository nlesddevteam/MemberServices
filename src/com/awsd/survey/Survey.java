package com.awsd.survey;

public class Survey 
{
  private int survey_id;
  private int school_id;
  private String survey_name;
  private String survey_url;
  private String logo_image;
  
  public Survey(int survey_id, int school_id, String survey_name, String survey_url, String logo_image)
  {
    this.survey_id = survey_id;
    this.school_id = school_id;
    this.survey_name = survey_name;
    this.survey_url = survey_url;
    this.logo_image = logo_image;
  }

  public Survey(int school_id, String survey_name, String survey_url, String logo_image)
  {
    this.survey_id = -1;
    this.school_id = school_id;
    this.survey_name = survey_name;
    this.survey_url = survey_url;
    this.logo_image = logo_image;
  }

  public int getSurveyID()
  {
    return this.survey_id;
  }

  public void setSurveyID(int id)
  {
    this.survey_id = id;
  }

  public int getSchoolID()
  {
    return this.school_id;
  }

  public void setSchoolID(int school_id)
  {
    this.school_id = school_id;
  }

  public String getSurveyName()
  {
    return this.survey_name;
  }

  public void setSurveyName(String survey_name)
  {
    this.survey_name = survey_name;
  }

  public String getSurveyURL()
  {
    return this.survey_url;
  }

  public void setSurveyURL(String survey_url)
  {
    this.survey_url = survey_url;
  }

  public String getLogoImage()
  {
    return this.logo_image;
  }

  public void setLogoImage(String logo_image)
  {
    this.logo_image = logo_image;
  }
}