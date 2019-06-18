package com.esdnl.nicep.constants;

public class AgencyContractType 
{
  public static final AgencyContractType PAYMENT_FLAT_FEE = new AgencyContractType(1, "PER STUDENT FLAT FEE PAID TO AGENCY");
  public static final AgencyContractType PAYMENT_TUTITION_PERCENTAGE = new AgencyContractType(2, "PERCENTAGE STUDENT TUTITION PAID TO AGENCY");
  public static final AgencyContractType INVOICE_STUDENT_TUTITION = new AgencyContractType(3, "AGENCY INVOICED FOR STUDENT TUTITION");
  
  public static final AgencyContractType[] ALL = 
    new AgencyContractType[]{
      PAYMENT_FLAT_FEE, 
      PAYMENT_TUTITION_PERCENTAGE, 
      INVOICE_STUDENT_TUTITION
    };
  
  private int value;
  private String desc;
  
  public AgencyContractType(int value, String desc)
  {
    this.value = value;
    this.desc = desc;
  }
  
  public int getValue()
  {
    return this.value;
  }
  
  public String getDescription()
  {
    return this.desc;
  }
  
  public static AgencyContractType get(int type)
  {
    AgencyContractType obj = null;
    
    for(int i=0; i < ALL.length; i++)
    {
      if(ALL[i].getValue() == type)
      {
        obj = ALL[i];
        break;
      }
    }
    
    return obj;
  }
  
  public boolean equals(Object o)
  {
    boolean check = true;
    
    if(o == null)
      check = false;
    else if(!(o instanceof AgencyContractType))
      check = false;
    else if(((AgencyContractType) o).getValue() != this.getValue())
      check = false;
    else
      check = true;
    
    return check;
  }
}