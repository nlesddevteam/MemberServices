package com.esdnl.sca.model.bean;

public class Pathway extends BaseType
{
  
  public static final Pathway PATHWAY_1 = new Pathway(1, "Pathway 1");
  public static final Pathway PATHWAY_2 = new Pathway(2, "Pathway 2");
  public static final Pathway PATHWAY_3 = new Pathway(3, "Pathway 3");
  public static final Pathway PATHWAY_4_SUBJECT_RELATED = new Pathway(4, "Pathway 4 - Subject Related");
  public static final Pathway PATHWAY_4_ENABLING = new Pathway(5, "Pathway 4 - Enabling");
  public static final Pathway PATHWAY_4_PREREQUISITE = new Pathway(6, "Pathway 4 - Prerequisite");
  public static final Pathway PATHWAY_5 = new Pathway(7, "Pathway 5");
  
  public static final Pathway[] ALL = new Pathway[]
  {
    PATHWAY_1,
    PATHWAY_2,
    PATHWAY_3,
    PATHWAY_4_SUBJECT_RELATED,
    PATHWAY_4_ENABLING,
    PATHWAY_4_PREREQUISITE,
    PATHWAY_5
  };
  
  private Pathway()
  {
    this(-1, "INVALID");
  }
  
  private Pathway(int id, String desc)
  {
    setId(id);
    setDescription(desc);
  }
  
  public static Pathway get(int id)
  {
    switch( id )
    {
      case 1:
        return PATHWAY_1;
      case 2:
        return PATHWAY_2;
      case 3:
        return PATHWAY_3;
      case 4:
        return PATHWAY_4_SUBJECT_RELATED;
      case 5:
        return PATHWAY_4_ENABLING;
      case 6:
        return PATHWAY_4_PREREQUISITE;
      case 7:
        return PATHWAY_5;
      default:
        return null;
    }
  }
  
  public boolean equals(Object obj)
  {
    boolean check = false;
    
    if(obj instanceof Pathway)
    {
      check = (this.getId() ==((Pathway)obj).getId());
    }
    
    return check;
  }  
  
}