package com.esdnl.sds;

public class SDSSchoolInfo 
{
  private int id;
  private String pd_acct;
  
  public SDSSchoolInfo(int id, String pd_acct)
  {
    this.id = id;
    this.pd_acct = pd_acct;
  }
  
  public int getSchoolId()
  {
    return this.id;
  }
  
  public String getPDAcctCode()
  {
    return this.pd_acct;
  }
}