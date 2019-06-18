package com.awsd.security.tag;

import javax.servlet.jsp.tagext.*;

public class SecurityCheckTagHandlerExtraInfo extends TagExtraInfo 
{
  public VariableInfo[] getVariableInfo(TagData tagData)
  {
    VariableInfo evts = new VariableInfo("usr", 
                                          "com.awsd.security.User", 
                                          false, 
                                          VariableInfo.AT_BEGIN);

    return  (new VariableInfo[]{evts});
  }
}