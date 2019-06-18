package com.awsd.financial;

import java.util.*;


public class ReportAccounts extends AbstractCollection 
{
  private Vector accounts;
  private Report rpt;
  
  public ReportAccounts(Report rpt) throws FinancialException
  {
    this.rpt = rpt;
    accounts = (Vector)(ReportDB.getReportAccounts(this.rpt)).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof Account)
    {
      accounts.add(o);
    }
    else
    {
      throw new ClassCastException("ReportAccount collections can only contain Account objects.");
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