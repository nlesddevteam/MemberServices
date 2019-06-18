package com.awsd.travel;

import java.util.*;


public class TravelClaimNotes extends AbstractCollection 
{
  private Vector s;
  
  public TravelClaimNotes(TravelClaim claim) throws TravelClaimException
  {
    s = (Vector)(TravelClaimNoteDB.getClaimNotes(claim)).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof TravelClaimNote)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("TravelClaimNotes collections can only contain TravelClaimNote objects.");
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