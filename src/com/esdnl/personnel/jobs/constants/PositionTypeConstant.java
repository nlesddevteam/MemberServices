package com.esdnl.personnel.jobs.constants;

public class PositionTypeConstant {
	private int value;
  private String desc;
  
  public static final PositionTypeConstant TEACHER = new PositionTypeConstant(1, "TEACHER");
  public static final PositionTypeConstant PRINCIPAL = new PositionTypeConstant(2, "PRINCIPAL");
  public static final PositionTypeConstant ASSISTANT_PRINCIPAL = new PositionTypeConstant(3, "ASSISTANT PRINCIPAL");
  public static final PositionTypeConstant PROGRAM_SPECIALIST = new PositionTypeConstant(4, "PROGRAM SPECIALIST");
  public static final PositionTypeConstant REO = new PositionTypeConstant(5, "REGIONAL EDUCATION OFFICER");
  public static final PositionTypeConstant SEO = new PositionTypeConstant(6, "SENIOR EDUCATION OFFICER");
  public static final PositionTypeConstant OTHER = new PositionTypeConstant(7, "OTHER");
  
  public static final PositionTypeConstant[] ALL = new PositionTypeConstant[]
  {
  	TEACHER,
  	PRINCIPAL,
  	ASSISTANT_PRINCIPAL,
  	PROGRAM_SPECIALIST,
  	REO,
  	SEO,
  	OTHER
  };
  
  private PositionTypeConstant(int value, String desc)
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
    if(!(o instanceof PositionTypeConstant))
      return false;
    else
      return (this.getValue() == ((PositionTypeConstant)o).getValue());
  }
  
  public static PositionTypeConstant get(int value)
  {
  	PositionTypeConstant tmp = null;
    
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
