package com.awsd.school;

import com.awsd.security.*;import com.awsd.security.SecurityException;

import java.util.*;


public class Grades  extends AbstractCollection 
{
  private Vector s;
  
  public Grades(boolean all) throws GradeException
  {
    if(all)
    {
      s = (Vector)(GradeDB.getGrades()).clone();
    }
    else
    {
      s = new Vector(5, 5);
    }
  }

  public Grades(User usr) throws GradeException
  {
    s = (Vector)(GradeDB.getGrades(usr.getPersonnel())).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof Grade)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("Grades collections can only contain Grade objects.");
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