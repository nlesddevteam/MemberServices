package com.esdnl.personnel.jobs.constants;

public class SublistAuditTypeCostant {
	private int value;
	  private String desc;
	  
	  
	  public static final SublistAuditTypeCostant LISTCREATED = new SublistAuditTypeCostant(1, "List Created");
	  public static final SublistAuditTypeCostant LISTACTIVATED = new SublistAuditTypeCostant(2, "List Activated");
	  public static final SublistAuditTypeCostant LISTDEACTIVATED = new SublistAuditTypeCostant(3, "List Deactivated");
	  public static final SublistAuditTypeCostant LISTDELETED = new SublistAuditTypeCostant(4, "List Deleted");
	  public static final SublistAuditTypeCostant APPLICANTAPPROVED = new SublistAuditTypeCostant(5, "Applicant Approved");
	  public static final SublistAuditTypeCostant APPLICANTEDNOTAPPROVED = new SublistAuditTypeCostant(6, "Applicant Not Approved");
	  public static final SublistAuditTypeCostant APPLICANTRESET = new SublistAuditTypeCostant(6, "Applicant Reset");
	  public static final SublistAuditTypeCostant APPLICANTAPPLIED = new SublistAuditTypeCostant(6, "Applicant Applied");
	  
	  public static final SublistAuditTypeCostant[] ALL = new SublistAuditTypeCostant[]
	  {
		 
		  LISTCREATED,
		  LISTACTIVATED,
		  LISTDEACTIVATED,
		  LISTDELETED,
		  APPLICANTAPPROVED,
		  APPLICANTEDNOTAPPROVED,
		  APPLICANTRESET,
		  APPLICANTAPPLIED
		  
	  };
	  
	  private SublistAuditTypeCostant(int value, String desc)
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
	  
	  public boolean equal(Object o)
	  {
	    if(!(o instanceof SublistAuditTypeCostant))
	      return false;
	    else
	      return (this.getValue() == ((SublistAuditTypeCostant)o).getValue());
	  }
	  
	  public static SublistAuditTypeCostant get(int value)
	  {
		  SublistAuditTypeCostant tmp = null;
	    
	    for(int i=0; i < ALL.length; i ++)
	    {
	      if(ALL[i].getValue() == value)
	      { 
	        tmp = ALL[i];
	        break;
	      }
	    }
	    
	    return tmp;
	  }
	  
	  public String toString()
	  {
	    return this.getDescription();
	  }
	}