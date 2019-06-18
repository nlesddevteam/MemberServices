package com.awsd.financial;

import com.awsd.personnel.*;

import java.util.*;


public class ReportPersonnelMap  extends AbstractMap
{
  private Map people;
  private Report r;
  
  public ReportPersonnelMap(Report r) throws FinancialException, PersonnelException
  {
    this.r = r;
    people = ReportDB.getReportPersonnelMap(r);
  }

  public Set entrySet()
  {
    return people.entrySet();
  }

  public Report getReport()
  {
    return r;
  }

  public boolean add(Object obj) throws FinancialException, PersonnelException
  {
    boolean check = false;

    if(obj instanceof Personnel)
    {
      Personnel p = (Personnel) obj;
      check = ReportDB.addReportPersonnel(r, p);  

      if(check)
        people.put(new Integer(p.getPersonnelID()), p);
    }
    else if(obj instanceof Personnel[])
    {
      Personnel tmp[] = (Personnel[])obj;
      
      check = ReportDB.addReportPersonnel(r, tmp);

      if(check)
      {
        for(int i = 0; i < tmp.length; i++)
        {
          people.put(new Integer(tmp[i].getPersonnelID()), tmp[i]);
        }
      }
    }
    else
    {
      throw new ClassCastException("ReportPersonnel collections can only contain Personnel objects.");
    }

    return check;
  }

  public boolean delete(Object obj) throws FinancialException, PersonnelException
  {
    boolean check = false;

    if(obj instanceof Personnel)
    {
      Personnel p = (Personnel) obj;
      check = ReportDB.deleteReportPersonnel(r, p);  

      if(check)
        people.remove(new Integer(p.getPersonnelID()));
    }
    else if(obj instanceof Personnel[])
    {
      Personnel tmp[] = (Personnel[])obj;
      
      check = ReportDB.deleteReportPersonnel(r, tmp);

      if(check)
      {
        for(int i = 0; i < tmp.length; i++)
        {
          people.remove(new Integer(tmp[i].getPersonnelID()));
        }
      }
    }
    else
    {
      throw new ClassCastException("ReportPersonnel collections can only contain Personnel objects.");
    }

    return check;
  }
}