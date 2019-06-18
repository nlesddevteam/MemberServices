package com.esdnl.servlet;

import com.esdnl.util.*;

public class RequiredSetValueFormElement extends FormElement
{
  private String[] valid_values = null;
  
  public RequiredSetValueFormElement(String name, String[] valid_values)
  {
    super(name);
    this.valid_values = valid_values;
  }
  
  public boolean validate()
  {
    boolean check = false;
    
    if(!StringUtils.isEmpty((String)getValue()))
      for(int i=0; i < valid_values.length; i++)
      {
        if(StringUtils.isEqual((String)getValue(), valid_values[i]))
        {
          check = true;
          break;
        }
      }
    else
      check = true;
    
    return check;
  }
}