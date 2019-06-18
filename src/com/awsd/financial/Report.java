package com.awsd.financial;

import com.awsd.personnel.*;


public class Report 
{
  private int report_id;
  private String report_name;
  private ReportAccountsMap accounts = null;
  private ReportPersonnelMap users = null;
  
  public Report(int report_id, String report_name)
  {
    this.report_id = report_id;
    this.report_name = report_name;
    accounts = null;
    users = null;
  }

  public int getReportID()
  {
    return this.report_id;
  }

  public String getReportName()
  {
    return this.report_name;
  }

  public ReportAccountsMap getReportAccounts() throws FinancialException
  {
    if(accounts == null)
    {
      accounts = new ReportAccountsMap(this);
    }

    return accounts;
  }

  public ReportPersonnelMap getReportPersonnel() throws FinancialException, PersonnelException
  {
    if(users == null)
    {
      users = new ReportPersonnelMap(this);
    }

    return users;
  }
}