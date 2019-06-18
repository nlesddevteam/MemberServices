package com.awsd.strike;

import com.awsd.school.*;

import java.util.*;


public class DailySchoolStrikeInfoHistory extends AbstractCollection 
{
  private Vector s;
  
  public DailySchoolStrikeInfoHistory(School school) throws StrikeException
  {
    s = (Vector)(DailySchoolStrikeInfoDB.getDailySchoolStrikeInfoHistory(school)).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof DailySchoolStrikeInfo)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("DailySchoolStrikeInfoHistory collections can only contain DailySchoolStrikeInfo objects.");
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