package com.awsd.school;

import com.awsd.security.*;import com.awsd.security.SecurityException;

import java.util.*;


public class Subjects  extends AbstractCollection 
{
  private Vector s;
  
  public Subjects() throws SubjectException
  {
    s = (Vector)(SubjectDB.getSubjects()).clone();
  }

  public Subjects(User usr) throws SubjectException
  {
    s = (Vector)(SubjectDB.getSubjects(usr.getPersonnel())).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof Subject)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("Subjects collections can only contain Subject objects.");
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