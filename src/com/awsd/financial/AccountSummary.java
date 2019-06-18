package com.awsd.financial;

public class AccountSummary 
{
  private String acctdesc;
  private String acctfrmt;
  private double budget;
  private double encumbrance;
  private double actual;
  private String acctseg;
  private String dept;
  private String proj;

  public AccountSummary(String acctfrmt) throws FinancialException
  {
    AccountSummary tmp = AccountSummaryDB.getAccountSummary(acctfrmt);
    this.acctdesc = tmp.acctdesc;
    this.acctfrmt = tmp.acctfrmt;
    this.acctseg = tmp.acctseg;
    this.actual = tmp.actual;
    this.budget = tmp.budget;
    this.dept = tmp.dept;
    this.encumbrance = tmp.encumbrance;
    this.proj = tmp.proj;
  }
  
  public AccountSummary(String acctdesc, 
                        String acctfrmt, 
                        double budget, 
                        double encumbrance,
                        double actual,
                        String acctseg,
                        String dept,
                        String proj)
  {
    this.acctdesc = acctdesc;
    this.acctfrmt = acctfrmt;
    this.budget = budget;
    this.encumbrance = encumbrance;
    this.actual = actual;
    this.acctseg = acctseg;
    this.dept = dept;
    this.proj = proj;
  }

  public String getAccountDescription()
  {
    return this.acctdesc;
  }

  public String getFormatedAccountCode()
  {
    return this.acctfrmt;
  }

  public double getAccountBudget()
  {
    return -this.budget;
  }

  public double getAccountEncumbrance()
  {
    return this.encumbrance;
  }

  public double getAccountActual()
  {
    return -this.actual;
  }

  public String getAccountSegement()
  {
    return this.acctseg;
  }

  public String getAccountDepartment()
  {
    return this.dept;
  }

  public String getAccountProject()
  {
    return this.proj;
  }
}