package com.awsd.financial;

import java.util.*;


public class FinancialAccounts extends AbstractCollection 
{
  private Vector accounts;
  
  public FinancialAccounts() throws FinancialException
  {
    accounts = (Vector)(AccountDB.getAccounts()).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof Account)
    {
      accounts.add(o);
    }
    else
    {
      throw new ClassCastException("FinancialAccounts collections can only contain Account objects.");
    }

    return true;
  }

  public Iterator iterator()
  {
    return accounts.iterator();
  }

  public int size()
  {
    return accounts.size();
  }
}