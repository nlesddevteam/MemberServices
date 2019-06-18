<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.esdnl.personnel.v2.model.sds.constant.PositionConstant,
                 com.esdnl.personnel.v2.model.sds.constant.LocationConstant,
                 com.esdnl.personnel.v2.model.sds.bean.*,
                 com.esdnl.personnel.v2.site.constant.*,
                 com.esdnl.personnel.v2.utils.*,
                 com.awsd.security.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  DecimalFormat df = new DecimalFormat("00");
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:a");
   
  EmployeeBean emp = (EmployeeBean)request.getAttribute("EMPLOYEE");
  
  Calendar v_cal = Calendar.getInstance();
  if(!com.esdnl.util.StringUtils.isEmpty(request.getParameter("view_date"))) 
    v_cal.setTime(new SimpleDateFormat("dd/MM/yyyy hh:mm:a").parse(request.getParameter("view_date")));
 
  v_cal.set(Calendar.HOUR, 8);
  v_cal.set(Calendar.MINUTE, 0);
  v_cal.set(Calendar.AM_PM, Calendar.AM);
  
  
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import 'includes/home.css';</style>
  <script language="JavaScript" src="js/CalendarPopup.js"></script>
  <script language="Javascript">
    <!--
    function check_submit()
    {  
      if(document.forms[0].reason_code.selectedIndex == 0)
      {
        alert('Please select a reason code.');
        return false;
      }
      else
        return true;
    }
    -->
  </script>
</head>
<body>
	<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
	
  <%
  	if(request.getAttribute("RELOAD_PARENT") != null)
  	{
	  	out.println("<script type='text/javascript'>");
	  	out.println("opener.document.location.href='availability_list.jsp?view_date=" + sdf.format((Date)request.getAttribute("view_date")) + "';");
	    out.println("self.close();");
	    out.println("</script>");
  	}
  %>
  
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
                    <img src="images/admin_top_logo.gif"><BR>
                  </td>
                  <td width="150" align="left" valign="middle">		
                    <table cellpadding="0" cellspacing="0">
                      <tr><td align="center" style="padding-bottom:5px;"><span class="displayWhiteDate14px">Employee Not Available</span></td></tr>
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
                          <%if(emp != null){%>
                            <form name="book_emp_action" action="empNotAvailable.html" method="POST" onsubmit="return check_submit();">
                              <input type="hidden" name="op" value="EMP_NOT_AVAILABLE">
                              <input type="hidden" name="id" value="<%=emp.getEmpId()%>">
                              <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                <tr style="height:18px;">
                                  <td class="displayHeaderTitle" valign="top"><span class="requiredStar">*&nbsp;</span>Employee Name</td>
                                  <td valign="top" class="displayText">
                                    <%=emp.getFullnameReverse()%>
                                  </td>
                                </tr>
                                <tr style="height:18px;">
                                  <td class="displayHeaderTitle" valign="top"><span class="requiredStar">*&nbsp;</span>Start Date</td>
                                  <td valign="top">
                                    <table cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td><input class="requiredinput_date" type="text" name="start_date" style="width:100px;" value='<%=(new SimpleDateFormat("dd/MM/yyyy")).format(v_cal.getTime())%>' readonly></td>
                                        <td>
                                          <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                               onmouseover="this.src='images/cal_popup_02.gif';"
                                               onmouseout="this.src='images/cal_popup_01.gif';"
                                               onclick="datepicker.popup();"><br>
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                                <tr style="height:18px;">
                                  <td class="displayHeaderTitle" valign="top"><span class="requiredStar">*&nbsp;</span>End Date</td>
                                  <td valign="top">
                                    <table cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td><input class="requiredinput_date" type="text" name="end_date" style="width:100px;" value='<%=(new SimpleDateFormat("dd/MM/yyyy")).format(v_cal.getTime())%>' readonly></td>
                                        <td>
                                          <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                               onmouseover="this.src='images/cal_popup_02.gif';"
                                               onmouseout="this.src='images/cal_popup_01.gif';"
                                               onclick="listingdatepicker.popup();"><br>
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                                <tr style="height:18px;">
                                  <td class="displayHeaderTitle" valign="top"><span class="requiredStar">*&nbsp;</span>Start Time</td>
                                  <td valign="top">
                                    <table align="left">
                                      <tr>
                                        <td>
                                          <select name="shour" class="requiredinput_date">
                                            <%for(int i=1; i <= 11; i++){%>
                                              <option value="<%=df.format(i)%>" <%=(v_cal.get(Calendar.HOUR)==i)?"SELECTED":""%>><%=df.format(i)%></option>
                                            <%}%>
                                            <option value="0" <%=(v_cal.get(Calendar.HOUR)==0)?"SELECTED":""%>>12</option>
                                          </select>
                                        </td>
                                        <td>
                                          <select name="sminute" class="requiredinput_date">
                                            <%for(int i=0; i < 60; i++){%>
                                                <option value='<%=df.format(i)%>' <%=(v_cal.get(Calendar.MINUTE)==i)?"SELECTED":""%>><%=df.format(i)%></option>
                                            <%}%>
                                          </select>
                                        </td>
                                        <td>
                                          <select name="sAMPM"  class="requiredinput_date">
                                            <option value="<%=Calendar.AM%>" <%=(v_cal.get(Calendar.AM_PM)==Calendar.AM)?"SELECTED":""%>>AM</option>
                                            <option value="<%=Calendar.PM%>" <%=(v_cal.get(Calendar.AM_PM)==Calendar.PM)?"SELECTED":""%>>PM</option>
                                          </select>
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                                <tr style="height:18px;">
                                  <td class="displayHeaderTitle" valign="top"><span class="requiredStar">*&nbsp;</span>End Time</td>
                                  <td valign="top" >
                                    <%v_cal.add(Calendar.HOUR, 7);%>
                                    <table align="left">
                                      <tr>
                                        <td>
                                          <select name="fhour" class="requiredinput_date">
                                            <%for(int i=1; i <= 11; i++){%>
                                              <option value="<%=df.format(i)%>" <%=(v_cal.get(Calendar.HOUR)==i)?"SELECTED":""%>><%=df.format(i)%></option>
                                            <%}%>
                                            <option value="0" <%=(v_cal.get(Calendar.HOUR)==0)?"SELECTED":""%>>12</option>
                                          </select>
                                        </td>
                                        <td>
                                          <select name="fminute" class="requiredinput_date">
                                            <%for(int i=0; i < 60; i++){%>
                                                <option value='<%=df.format(i)%>' <%=(v_cal.get(Calendar.MINUTE)==i)?"SELECTED":""%>><%=df.format(i)%></option>
                                            <%}%>
                                          </select>
                                        </td>
                                        <td>
                                          <select name="fAMPM"  class="requiredinput_date">
                                            <option value="<%=Calendar.AM%>" <%=(v_cal.get(Calendar.AM_PM)==Calendar.AM)?"SELECTED":""%>>AM</option>
                                            <option value="<%=Calendar.PM%>" <%=(v_cal.get(Calendar.AM_PM)==Calendar.PM)?"SELECTED":""%>>PM</option>
                                          </select>
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                                <tr style="height:18px;">
                                  <td class="displayHeaderTitle" valign="top"><span class="requiredStar">*&nbsp;</span>Reason Code</td>
                                  <td valign="top" >
                                    <select name="reason_code"  class="requiredinput_date">
                                      <option value="-1">Select Reason for Unavailability.
                                      <option value="1">Could Not Be Reached.</option>
                                      <option value="2">Indicated Unavailable.</option>
                                      <option value="3">Previously Booked.</option>
                                    </select>
                                  </td>
                                </tr>
                                <tr>
                                  <td colspan="2" align="center" style="padding-top:5px; color:#333333;">
                                    <input type="submit" value="Submit">
                                  </td>
                                </tr>
                              </table>
                            </form>
                          <%}else{%>
                            <SPAN style='color:#FF0000;'>Employee not specified.</SPAN>
                          <%}%>
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
  <jsp:include page="footer.jsp" flush="true" >
    <jsp:param name="width" value="350" />
  </jsp:include>
  <script language="JavaScript">
    var datepicker = new CalendarPopup(document.forms['book_emp_action'].elements['start_date']);
    datepicker.year_scroll = true;
    datepicker.time_comp = false;
    
    var listingdatepicker = new CalendarPopup(document.forms['book_emp_action'].elements['end_date']);
    listingdatepicker.year_scroll = true;
    listingdatepicker.time_comp = false;
  </script>
</body>
</html>
