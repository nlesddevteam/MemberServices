package com.esdnl.nicep.beans;

public class StudentSchoolHistoryBean 
{
  private int HistoryId;
  private int StudentId;
  private int SchoolId;
  private String SchoolYear;
  private int Term;

  public StudentSchoolHistoryBean()
  {
  }

  public int getHistoryId()
  {
    return HistoryId;
  }

  public void setHistoryId(int newHistoryId)
  {
    HistoryId = newHistoryId;
  }

  public int getStudentId()
  {
    return StudentId;
  }

  public void setStudentId(int newStudentId)
  {
    StudentId = newStudentId;
  }

  public int getSchoolId()
  {
    return SchoolId;
  }

  public void setSchoolId(int newSchoolId)
  {
    SchoolId = newSchoolId;
  }

  public String getSchoolYear()
  {
    return SchoolYear;
  }

  public void setSchoolYear(String newSchoolYear)
  {
    SchoolYear = newSchoolYear;
  }

  public int getTerm()
  {
    return Term;
  }

  public void setTerm(int newTerm)
  {
    Term = newTerm;
  }
}