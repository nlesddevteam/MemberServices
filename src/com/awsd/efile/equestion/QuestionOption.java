package com.awsd.efile.equestion;

public class QuestionOption 
{
  private int option_id;
  private String option;
  private boolean iscorrect;
  
  public QuestionOption(int option_id, String option, boolean iscorrect)
  {
    this.option_id = option_id;
    this.option = option;
    this.iscorrect = iscorrect;
  }

  public QuestionOption(String option, boolean iscorrect)
  {
    this(-1, option, iscorrect);
  }

  public int getOptionID()
  {
    return this.option_id;
  }

  public void setOptionID(int option_id)
  {
    this.option_id = option_id;
  }

  public String getOption()
  {
    return this.option;
  }

  public void setOption(String option)
  {
    this.option = option;
  }

  public boolean isCorrect()
  {
    return this.iscorrect;
  }

  public void setIsCorrect(boolean iscorrect)
  {
    this.iscorrect = iscorrect;
  }
}