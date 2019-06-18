package com.esdnl.personnel.jobs.constants;

public class PostingType {

	private int value;
  private String desc;
  
  public static final PostingType INTERNAL_ONLY = new PostingType(0, "INTERNAL ONLY");
  public static final PostingType EXTERNAL_ONLY = new PostingType(1, "EXTERNAL ONLY");
  public static final PostingType INTERNAL_EXTERNAL = new PostingType(2, "INTERNAL AND EXTERNAL");
  
  public static final PostingType[] ALL = new PostingType[]{
  		
  		INTERNAL_ONLY,
  		EXTERNAL_ONLY,
  		INTERNAL_EXTERNAL
  };
  
  private PostingType(int value, String desc)
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
    if(!(o instanceof PostingType))
      return false;
    else
      return (this.getValue() == ((PostingType)o).getValue());
  }
  
  public static PostingType get(int value)
  {
  	PostingType tmp = null;
    
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
