package com.awsd.personnel;

import java.util.*;


public class Supervisors extends AbstractCollection 
{
  private Vector members;
  
  public Supervisors() throws PersonnelException
  {
    members = (Vector)(PersonnelDB.getPotentialSupervisors()).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof Personnel)
    {
      members.add(o);
    }
    else
    {
      throw new ClassCastException("Supervisors collections can only contain Personnel objects.");
    }

    return true;
  }

  public Iterator iterator()
  {
    return members.iterator();
  }

  public int size()
  {
    return members.size();
  }
}