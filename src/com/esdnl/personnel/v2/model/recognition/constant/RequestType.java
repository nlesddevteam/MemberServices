package com.esdnl.personnel.v2.model.recognition.constant;

public class RequestType 
{
  private int uid;
  private String desc;
  
  public static final RequestType EMPLOYEE 
    = new RequestType(1, "EMPLOYEE");
  
  public static final RequestType EMPLOYEE_GROUP 
    = new RequestType(2, "EMPLOYEE GROUP");
  
  public static final RequestType STUDENT 
    = new RequestType(3, "STUDENT");
  
  public static final RequestType STUDENT_GROUP 
    = new RequestType(4, "STUDENT GROUP");
  
  public static final RequestType[] ALL = new RequestType[]
  {
    EMPLOYEE,
    EMPLOYEE_GROUP,
    STUDENT,
    STUDENT_GROUP
  };
  
  private RequestType(int uid, String desc)
  {
    this.uid = uid;
    this.desc = desc;
  }
  
  public int getId()
  {
    return this.uid;
  }
  
  public String getDescription()
  {
    return this.desc;
  }
    
  public String toString()
  {
    return this.getDescription();
  }
  
  public boolean equals(Object obj)
  {
    boolean check = false;
    
    if(obj instanceof RequestType)
    {
      check = (this.getId() ==((RequestType)obj).getId());
    }
    
    return check;
  }
  
  public static final RequestType get(int uid)
  {
    RequestType tmp = null;
    
    for(int i=0; i < ALL.length; i ++)
    {
      if(ALL[i].getId() == uid)
      { 
        tmp = ALL[i];
        break;
      }
    }
    
    return tmp;
  }
}