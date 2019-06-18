
package com.awsd.common;

import java.util.*;


public class ItemCart extends AbstractMap
{
  private Map items = null;
  
  public ItemCart()
  {
    items = new HashMap();
  }

  public Set entrySet()
  {
    return items.entrySet();
  }
}