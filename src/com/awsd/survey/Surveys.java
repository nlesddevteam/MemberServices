package com.awsd.survey;

import com.awsd.personnel.*;

import java.util.*;


public class Surveys extends AbstractCollection 
{
  private Vector s;
  
  public Surveys(Personnel p) throws SurveyException, PersonnelException
  {
    s = (Vector) SurveyDB.getUntakenSurveys(p);
  }

  public boolean add(Object o)
  {
    if (o instanceof Survey)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("Surveys collections can only contain Survey objects.");
    }

    return true;
  }

  public Iterator iterator()
  {
    return s.iterator();
  }

  public int size()
  {
    return s.size();
  }
}