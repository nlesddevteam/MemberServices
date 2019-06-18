package com.awsd.personnel;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;

import java.util.*;


public class DistrictPersonnel extends AbstractCollection 
{
  private Vector members;
  
  public DistrictPersonnel() throws PersonnelException
  {
    members = (Vector)(PersonnelDB.getDistrictPersonnel()).clone();
  }

  public DistrictPersonnel(String role) throws PersonnelException, RoleException, PermissionException, SecurityException
  {
    members = (Vector)(PersonnelDB.getPersonnelList(RoleDB.getRole(role))).clone();
  }


  public boolean add(Object o)
  {
    if (o instanceof Personnel)
    {
      members.add(o);
    }
    else
    {
      throw new ClassCastException("Membership collections can only contain Personnel objects.");
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