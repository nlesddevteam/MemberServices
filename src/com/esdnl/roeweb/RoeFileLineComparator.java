package com.esdnl.roeweb;

import java.util.*;

public class RoeFileLineComparator implements Comparator
{
  public int compare(Object o1, Object o2)
  {
    Date d1 = (Date) o1;
    Date d2 = (Date) o2;
    
    return -1 * (d1.compareTo(d2));
  }      
}