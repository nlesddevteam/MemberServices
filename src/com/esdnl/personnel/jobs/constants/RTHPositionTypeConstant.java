package com.esdnl.personnel.jobs.constants;

public class RTHPositionTypeConstant {
	private int value;
	  private String desc;
	  
	  
	  public static final RTHPositionTypeConstant PERMANENT = new RTHPositionTypeConstant(3, "PERMANENT");
	  public static final RTHPositionTypeConstant TEMPORARY = new RTHPositionTypeConstant(4, "TEMPORARY");
	  public static final RTHPositionTypeConstant REPLACEMENT = new RTHPositionTypeConstant(5, "REPLACEMENT");
	  public static final RTHPositionTypeConstant NEW_POSITION = new RTHPositionTypeConstant(6, "NEW POSITION");
	  
	  public static final RTHPositionTypeConstant[] ALL = new RTHPositionTypeConstant[]
	  {
		 
		  PERMANENT,
		  TEMPORARY,
		  REPLACEMENT,
	  	  NEW_POSITION
	  };
	  
	  private RTHPositionTypeConstant(int value, String desc)
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
	    if(!(o instanceof RTHPositionTypeConstant))
	      return false;
	    else
	      return (this.getValue() == ((RTHPositionTypeConstant)o).getValue());
	  }
	  
	  public static RTHPositionTypeConstant get(int value)
	  {
		  RTHPositionTypeConstant tmp = null;
	    
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
