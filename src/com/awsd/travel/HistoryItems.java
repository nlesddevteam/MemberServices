package com.awsd.travel;

import java.util.*;


public class HistoryItems extends AbstractCollection 
{
  private Vector s;
  
  public HistoryItems(TravelClaim claim) throws TravelClaimException
  {
    s = (Vector)(HistoryItemDB.getClaimHistoryItems(claim)).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof HistoryItem)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("HistoryItems collections can only contain HistoryItem objects.");
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