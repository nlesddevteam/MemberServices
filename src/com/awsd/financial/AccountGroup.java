package com.awsd.financial;

import java.util.*;


public class AccountGroup extends AbstractCollection 
{
  private Vector accounts;
  
  public AccountGroup(String ACCTSEG) throws FinancialException
  {
    accounts = (Vector)(AccountDB.getAccountsByAcctSeq(ACCTSEG)).clone();
  }

  public AccountGroup(String ACCTSEGS[]) throws FinancialException
  {
    if(ACCTSEGS.length > 0)
    {
      accounts = new Vector(10);
    
      for(int i=0; i < ACCTSEGS.length; i++)
      {
        if(ACCTSEGS[i].indexOf("-") >= 0)
        {
          String st = ACCTSEGS[i].substring(0, ACCTSEGS[i].indexOf("-"));
          String ed = ACCTSEGS[i].substring(ACCTSEGS[i].indexOf("-")+1);
          accounts.addAll((Vector)(AccountDB.getAccountsByAcctSeqRange(st, ed)).clone());
        }
        else
        {
        accounts.addAll((Vector)(AccountDB.getAccountsByAcctSeq(ACCTSEGS[i])).clone());
        }
      }
    }
  }

  public boolean add(Object o)
  {
    if (o instanceof Account)
    {
      accounts.add(o);
    }
    else
    {
      throw new ClassCastException("AccountGroup collections can only contain Account objects.");
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