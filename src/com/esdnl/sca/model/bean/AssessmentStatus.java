package com.esdnl.sca.model.bean;

public class AssessmentStatus extends BaseType 
{
  public static final AssessmentStatus NOT_YET_BEGUN = new AssessmentStatus(1, "NOT YET BEGUN");
  public static final AssessmentStatus BEGUN = new AssessmentStatus(2, "BEGUN");
  public static final AssessmentStatus COMPLETE = new AssessmentStatus(3, "COMPLETE");
  
  public static final AssessmentStatus[] ALL = new AssessmentStatus[]
  {
    NOT_YET_BEGUN, BEGUN, COMPLETE    
  };
  
  private AssessmentStatus()
  {
    this(-1, "INVALID");
  }
  
  private AssessmentStatus(int id, String desc)
  {
    setId(id);
    setDescription(desc);
  }
  
  public static AssessmentStatus get(int id)
  {
    switch( id )
    {
      case 1:
        return NOT_YET_BEGUN;
      case 2:
        return BEGUN;
      case 3:
        return COMPLETE;
      default:
        return null;
    }
  }
  
  public boolean equals(Object o)
  {
    boolean check = false;
    
    if(o != null)
    {
      if(o instanceof AssessmentStatus)
      {
        if(((AssessmentStatus)o).getId() == this.getId())
          check = true;
      }
    }
    
    return check;
  }
}
