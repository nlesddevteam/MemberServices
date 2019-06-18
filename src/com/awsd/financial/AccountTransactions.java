package com.awsd.financial;

import java.util.*;


public class AccountTransactions  extends AbstractCollection 
{
  private Vector trans;
  
  public AccountTransactions(Account acc) throws FinancialException
  {
    trans = (Vector)(AccountTransactionDB.getAccountTransactions(acc));
  }


  public boolean add(Object o)
  {
    if (o instanceof AccountTransaction)
    {
      trans.add(o);
    }
    else
    {
      throw new ClassCastException("AccountTransactions collections can only contain AccountTransaction objects.");
    }

    return true;
  }

  public Iterator iterator()
  {
    return trans.iterator();
  }

  public int size()
  {
    return trans.size();
  }
}