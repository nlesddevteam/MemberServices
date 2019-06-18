package com.awsd.financial;

import com.awsd.personnel.*;

import java.util.*;


public class PersonnelReports extends AbstractCollection 
{
  private Vector reports;
  private Personnel p;
  
  public PersonnelReports(Personnel p) throws FinancialException, PersonnelException
  {
    this.p = p;
    reports = (Vector)(ReportDB.getPersonnelReports(this.p)).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof Report)
    {
      reports.add(o);
    }
    else
    {
      throw new ClassCastException("PersonnelReports collections can only contain Report objects.");
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