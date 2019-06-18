package com.awsd.financial;

import com.awsd.personnel.*;

import java.util.*;


public class ReportPersonnel extends AbstractCollection 
{
  private Vector people;
  private Report rpt;
  
  public ReportPersonnel(Report rpt) throws FinancialException, PersonnelException
  {
    this.rpt = rpt;
    people = (Vector)(ReportDB.getReportPersonnel(this.rpt)).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof Personnel)
    {
      people.add(o);
    }
    else
    {
      throw new ClassCastException("ReportPersonnel collections can only contain Personnel objects.");
    }

    return true;
  }

  public Iterator iterator()
  {
    return people.iterator();
  }

  public int size()
  {
    return people.size();
  }
}