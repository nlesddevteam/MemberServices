package com.awsd.travel;

import java.util.*;


public class TravelClaimItems extends AbstractCollection 
{
  private Vector s;
  
  public TravelClaimItems(TravelClaim claim) throws TravelClaimException
  {
    s = (Vector)(TravelClaimItemsDB.getClaimItems(claim)).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof TravelClaimItem)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("TravelClaimItems collections can only contain TravelClaimItem objects.");
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