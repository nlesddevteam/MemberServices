package com.awsd.school;

import java.util.*;


public class SchoolFamilies extends AbstractCollection 
{
  private Vector s;
  
  public SchoolFamilies() throws SchoolFamilyException
  {
    s = (Vector)(SchoolFamilyDB.getSchoolFamilies()).clone();
  }
  
  public boolean add(Object o)
  {
    if (o instanceof SchoolFamily)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("SchoolFamilies collections can only contain SchoolFamily objects.");
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