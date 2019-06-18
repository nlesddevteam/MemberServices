package com.awsd.financial;

import java.util.*;


public class AccountGroupSummary 
{
  private AccountGroup grp = null;
  private String group_descrpion = null;

  private double budget;
  private double encumbrance;
  private double actual;

  public AccountGroupSummary(AccountGroup grp, String group_description) throws FinancialException
  {
    this.grp = grp;
    this.group_descrpion = group_description;

    summarize();
  }

  public double getAccountBudget()
  {
    return this.budget;
  }

  public double getAccountEncumbrance()
  {
    return this.encumbrance;
  }

  public double getAccountActual()
  {
    return this.actual;
  }

  public String getDescription()
  {
    return this.group_descrpion;
  }
  
  private void summarize() throws FinancialException
  {
    AccountSummary tmp = null;
    Iterator iter = this.grp.iterator();

    this.budget = 0;
    this.encumbrance = 0;
    this.actual = 0;

    System.err.println("***********************");

    while(iter.hasNext())
    {
      tmp = (AccountSummary) ((Account)iter.next()).getAccountSummary();

      if(tmp != null)
      {
        this.budget += tmp.getAccountBudget();
        this.actual += tmp.getAccountActual();
        this.encumbrance += tmp.getAccountEncumbrance();
        System.err.println(tmp.getFormatedAccountCode() + ": " + tmp.getAccountActual() + " : " + tmp.getAccountBudget());
      }
    }

    System.out.println("SUMMARY: " + this.actual);
    System.err.println("***********************");
  }
}