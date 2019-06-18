package com.awsd.strike;

import java.util.*;


public class SchoolStrikeGroups extends AbstractCollection 
{
  private Vector s;
  
  public SchoolStrikeGroups() throws StrikeException
  {
    s = (Vector)(SchoolStrikeGroupDB.getAllSchoolStrikeGroups()).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof SchoolStrikeGroup)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("SchoolStrikeGroups collections can only contain SchoolStrikeGroup objects.");
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