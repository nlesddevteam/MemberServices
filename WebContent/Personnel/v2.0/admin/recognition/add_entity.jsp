<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.util.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="personnel" %>

<%
  String loc = (String)request.getAttribute("LOCATION");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Eastern School District - Member Services - Personnel-Package</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import '../../css/home.css';</style>
  
  <script type="text/javascript">
    function confirmed()
    {
      opener.document.location.href = parent.document.location.href + '&confirmed=true';
      self.close();
    }
  </script>
</head>
<body <%=((request.getAttribute("REFRESH_REQUEST")!= null)?"onload=\"opener.document.forms[0].submit();\"":"")%>>

  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table width="350" cellpadding="0" cellspacing="0" border="0" style="border-top: solid 1px #FFB700;border-bottom: solid 1px #FFB700; ">
    <tr>
      <td>   
        <table width="350" cellpadding="0" cellspacing="0" border="0">
          <tr height="79">
            <td width="350" align="left" valign="top" style="background-color:#FFB700;">
              <table width="350" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="154" align="left" valign="top">
                    <img src="/MemberServices/Personnel/v2.0/images/admin_top_logo.gif"><BR>
                  </td>
                  <td width="150" align="left" valign="middle">		
                    <table cellpadding="0" cellspacing="0">
                      <tr><td align="center" style="padding-bottom:5px;"><span class="displayWhiteDate14px">ADD EMPLOYEE</span></td></tr>
                      <tr><td align="center"><span class="displayWhiteDate"><%=(new SimpleDateFormat("EEEE, MMMM dd, yyyy")).format(Calendar.getInstance().getTime())%> </span></td></tr>
                    </table>
                  </td>
                </tr>
              </table>				
            </td>
          </tr>
        </table>
        <table width="350" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="350" align="left" valign="top">
              <table width="350" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="350" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="350" align="left" valign="top" style="padding-top:8px;">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayBoxTitle" align="center" style="color:#333333;">
                                <form name="addEntityForm" action="addEntity.html">
                                  <input type="HIDDEN" name="op" value="GET_EMPLOYEES">
                                  <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                    <%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){%>
                                      <tr style="padding-top:5px;padding-bottom:5px;">
                                        <td colspan="2" align="center" style='color:#FF0000;font-weight:bold;'><%=(String)request.getAttribute("msg")%></td>
                                      </tr>
                                    <%}%>
                                    <tr>
                                      <td align="right" >Location</td>
                                      <td><personnel:Locations id='location' value='<%=loc%>' onChange="document.forms[0].submit();" /></td>
                                    </tr>
                                    <%if(!StringUtils.isEmpty(loc)){%>
                                      <tr style="padding-top: 5px;">
                                        <td align="right" valign="top">Employees</td>
                                        <td><personnel:EmployeesByLocation id='emps' location='<%=loc%>' style='width:220px;height:150px;' /></td>
                                      </tr>
                                      
                                      <tr style="padding-top: 7px;">
                                        <td colspan="2" align="center"><input type="button" value="Add" onclick="document.forms[0].op.value='ADD_EMPLOYEES';document.forms[0].submit();"></td>
                                      </tr>
                                    <%}%>
                                  </table>
                                </form>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>						
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <jsp:include page="../../../footer.jsp" flush="true" >
    <jsp:param name="width" value="350" />
  </jsp:include>
</body>
</html>
