package com.awsd.pdreg.tag;

import javax.servlet.jsp.tagext.*;


public class DailyCalendarTagHandlerExtraInfo extends TagExtraInfo 
{
  public VariableInfo[] getVariableInfo(TagData tagData)
  {
    VariableInfo evts = new VariableInfo("MonthlyEvents", 
                                          "com.awsd.pdreg.MonthlyCalendar", 
                                          false, 
                                          VariableInfo.AT_BEGIN);

    return  (new VariableInfo[]{evts});
  }
}