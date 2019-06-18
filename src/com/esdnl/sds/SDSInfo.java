package com.esdnl.sds;


public class SDSInfo 
{
  private String vendor_number;
  private String acct;
  
  public SDSInfo(String vendor_number, String acct)
  {
    this.vendor_number = vendor_number;
    this.acct = acct;
  }

  public String getVendorNumber()
  {
    return this.vendor_number;
  }
  
  public void setVendorNumber(String vendor_number)
  {
    this.vendor_number = vendor_number;
  }
  
  public String getAccountCode()
  {
    return acct;
  }
  
  public void setAccountCode(String acct)
  {
    if((acct != null)&&(!acct.trim().equals("")))
      this.acct = acct;
  }
}