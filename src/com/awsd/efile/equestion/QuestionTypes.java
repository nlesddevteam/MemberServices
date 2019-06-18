package com.awsd.efile.equestion;

import java.util.*;


public class QuestionTypes extends AbstractCollection 
{
  private Vector s;
  
  public QuestionTypes() throws QuestionException
  {
    s = (Vector)(QuestionDB.getQuestionTypes()).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof QuestionType)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("QuestionTypes collections can only contain QuestionType objects.");
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