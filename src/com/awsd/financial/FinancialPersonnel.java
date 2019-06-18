package com.awsd.financial;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;

import java.util.*;


public class FinancialPersonnel extends AbstractCollection 
{
  private Vector personnel;
  
  public FinancialPersonnel() throws FinancialException, RoleException, PermissionException, PersonnelException
  {
    personnel = (Vector)(PersonnelDB.getPersonnelList(PermissionDB.getPermission("FINANCIAL-VIEW")));
  }

  public boolean add(Object o)
  {
    if (o instanceof Personnel)
    {
      personnel.add(o);
    }
    else
    {
      throw new ClassCastException("FinancialPersonnel collections can only contain Personnel objects.");
    }

    return true;
  }

  public Iterator iterator()
  {
    return personnel.iterator();
  }

  public int size()
  {
    return personnel.size();
  }
}