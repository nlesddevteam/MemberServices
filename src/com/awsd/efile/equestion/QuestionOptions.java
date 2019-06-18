package com.awsd.efile.equestion;

import java.util.*;


public class QuestionOptions extends AbstractCollection 
{
  private Vector s;

  public QuestionOptions()
  {
    s = new Vector();
  }
  
  public QuestionOptions(Question q) throws QuestionException
  {
    s = (Vector)(QuestionDB.getQuestionOptions(q)).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof QuestionOption)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("QuestionOptions collections can only contain QuestionOption objects.");
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