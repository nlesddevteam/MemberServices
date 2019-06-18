package com.awsd.school;

import com.awsd.security.*;import com.awsd.security.SecurityException;

import java.util.*;


public class Courses  extends AbstractCollection 
{
  private Vector s;
  
  public Courses(boolean all) throws CourseException
  {
    if(all)
    {
      s = (Vector)(CourseDB.getCourses()).clone();
    }
    else
    {
      s = new Vector(10, 10);
    }
  }

  public Courses(User usr) throws CourseException
  {
    s = (Vector)(CourseDB.getCourses(usr.getPersonnel())).clone();
  }

  public Courses(Grade g) throws CourseException
  {
    s = (Vector)(CourseDB.getCourses(g)).clone();
  }

  public Courses(Subject[] subs, Grade[] grds) throws CourseException
  {
    s = (Vector)(CourseDB.getCourses(subs, grds)).clone();
  }
  public boolean add(Object o)
  {
    if (o instanceof Course)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("Courses collections can only contain Course objects.");
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