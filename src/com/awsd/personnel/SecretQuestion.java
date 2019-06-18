package com.awsd.personnel;

public class SecretQuestion 
{
  private String question;
  private String answer;
  
  public SecretQuestion(String question, String answer)
  {
    this.question = question;
    this.answer = answer;
  }
  
  public String getQuestion()
  {
    return this.question;
  }
  
  public String getAnswer()
  {
    return this.answer;
  }
  
  public void setSecretQuestion(String question, String answer)
  {
    this.question = question;
    this.answer = answer;
  }
  
  public String toString()
  {
    return this.question.toUpperCase() + "?";
  }
}