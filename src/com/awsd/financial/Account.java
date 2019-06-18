package com.awsd.financial;

public class Account 
{
  private String acctfrmt;
  private String acctdesc;
  private AccountSummary summary;
  private AccountTransactions transactions;
  
  public Account(String acctfrmt, String acctdesc)
  {
    this.acctfrmt= acctfrmt;
    this.acctdesc = acctdesc;
    summary = null;
    transactions = null;
  }

  public String getFormatedAccountCode()
  {
    return this.acctfrmt;
  }

  public String getAccountDescription()
  {
    return this.acctdesc;
  }

  public AccountSummary getAccountSummary() throws FinancialException
  {
    if(summary == null)
    {
      summary = AccountSummaryDB.getAccountSummary(this.acctfrmt);
    }
    return summary;
  }

  public AccountTransactions getAccountTransactions() throws FinancialException
  { 
    if(transactions == null)
    {
      this.transactions = new AccountTransactions(this);
    }

    return transactions;
  }

  public String getAccountSegment()
  {
    String seg = "";
    
    if(acctfrmt.indexOf("-") >= 0)
      seg = acctfrmt.substring(0, acctfrmt.indexOf("-"));
    else
      seg = acctfrmt;

    return seg;
  }

  public String getDepartment()
  {
    String dept = "";

    if(acctfrmt.indexOf("-") > -1)
    {
      if(acctfrmt.indexOf("-") == acctfrmt.lastIndexOf("-"))
      {
        dept = acctfrmt.substring(acctfrmt.indexOf("-")+1);
      }
      else
      {
        dept = acctfrmt.substring(acctfrmt.indexOf("-")+1, acctfrmt.lastIndexOf("-"));
      }
    }
    return dept;
  }

  public String getProject()
  {
    String proj = "";

    if((acctfrmt.lastIndexOf("-") > -1)&& !getDepartment().equals(acctfrmt.substring(acctfrmt.lastIndexOf("-")+1)))
      proj =  acctfrmt.substring(acctfrmt.lastIndexOf("-")+1);

    return proj;
  }

  public String toString()
  {
    String msg = "";

    try
    {
      msg = "Account[" + this.acctfrmt + ", " + this.getAccountSummary().getAccountActual() + "]";
    }
    catch(Exception e){};

    return msg;
  }
}