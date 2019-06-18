package com.awsd.financial;

import java.util.*;


public class ReportAccountsMap extends AbstractMap
{
  private Map accounts;
  private Report r;
  
  public ReportAccountsMap(Report r) throws FinancialException
  {
    this.r = r;
    accounts = ReportDB.getReportAccountsMap(r);
  }

  public Set entrySet()
  {
    return accounts.entrySet();
  }

  public Report getReport()
  {
    return r;
  }

  public boolean add(Object obj) throws FinancialException
  {
    boolean check = false;

    if(obj instanceof Account)
    {
      Account acc = (Account) obj;
      check = ReportDB.addReportAccount(r, acc);  

      if(check)
        accounts.put(acc.getFormatedAccountCode(), acc);
    }
    else if(obj instanceof Account[])
    {
      Account tmp[] = (Account[])obj;
      
      check = ReportDB.addReportAccount(r, tmp);

      if(check)
      {
        for(int i = 0; i < tmp.length; i++)
        {
          accounts.put(tmp[i].getFormatedAccountCode(), tmp[i]);
        }
      }
    }
    else
    {
      throw new ClassCastException("ReportAccount collections can only contain Account objects.");
    }

    return check;
  }

  public boolean delete(Object obj) throws FinancialException
  {
    boolean check = false;

    if(obj instanceof Account)
    {
      Account acc = (Account) obj;
      check = ReportDB.deleteReportAccount(r, acc);  

      if(check)
        accounts.remove(acc.getFormatedAccountCode());
    }
    else if(obj instanceof Account[])
    {
      Account tmp[] = (Account[])obj;
      
      check = ReportDB.deleteReportAccount(r, tmp);

      if(check)
      {
        for(int i = 0; i < tmp.length; i++)
        {
          accounts.remove(tmp[i].getFormatedAccountCode());
        }
      }
    }
    else
    {
      throw new ClassCastException("ReportAccount collections can only contain Account objects.");
    }

    return check;
  }
}