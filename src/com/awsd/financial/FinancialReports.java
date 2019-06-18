package com.awsd.financial;

import java.util.*;


public class FinancialReports extends AbstractCollection 
{
  private Vector reports;
  
  public FinancialReports() throws FinancialException
  {
    reports = (Vector)(ReportDB.getReports());
  }

  public boolean add(Object o)
  {
    if (o instanceof Report)
    {
      reports.add(o);
    }
    else
    {
      throw new ClassCastException("FinancialReports collections can only contain Report objects.");
    }

    return true;
  }

  public Iterator iterator()
  {
    return reports.iterator();
  }

  public int size()
  {
    return reports.size();
  }
}