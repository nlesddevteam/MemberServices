package com.awsd.financial;

public class AccountTransaction 
{
  private String acctdesc;
  private String acctseg;
  private int date;
  private String vendor;
  private String invnum;
  private String ponum;
  private double actual;
  private double encum;
  private String acct;
  private String dept;
  private String proj;
  
  public AccountTransaction(String acctdesc,
                            String acctseg,
                            int date,
                            String vendor,
                            String invnum,
                            String ponum,
                            double actual,
                            double encum,
                            String acct,
                            String dept,
                            String proj)
  {
    this.acctdesc = acctdesc;
    this.acctseg = acctseg;
    this.date = date;
    this.vendor = vendor;
    this.invnum = invnum;
    this.ponum = ponum;
    this.actual = actual;
    this.encum = encum;
    this.acct = acct;
    this.dept = dept;
    this.proj = proj;
  }

  public String getAccountID()
  {
    return this.acct;
  }

  public String getDepartment()
  {
    return this.dept;
  }

  public String getProject()
  {
    return this.proj;
  }
  
  public double getActual()
  {
    return this.actual;
  }

  public double getEncumbrance()
  {
    return this.encum;
  }

  public String getInvoiceNumber()
  {
    return this.invnum;
  }

  public String getPurchaseOrderNumber()
  {
    return this.ponum;
  }

  public String getVendor()
  {
    return this.vendor;
  }
  
  public String getAccountDescription()
  {
    return this.acctdesc;
  }

  public String getAccountSegment()
  {
    return this.acctseg;
  }

  public int getTransactionDate()
  {
    return this.date;
  }
}