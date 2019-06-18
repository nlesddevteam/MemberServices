<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.v2.model.sds.constant.LocationConstant,
                  com.esdnl.personnel.v2.utils.*,
                  com.awsd.security.*" 
        isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="personnel" %>

<%
  User usr = (User) session.getAttribute("usr");

  Date v_date = null;
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:a");
  
  if(!com.esdnl.util.StringUtils.isEmpty(request.getParameter("view_date")))
  {
    v_date = sdf.parse(request.getParameter("view_date"));
  }
  else if(!com.esdnl.util.StringUtils.isEmpty((String)request.getAttribute("view_date")))
  {
    v_date = sdf.parse((String)request.getAttribute("view_date"));
  }
  else
  {
    Calendar c = Calendar.getInstance();
    //c.add(Calendar.HOUR, 1);
    v_date = c.getTime();
  }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">@import 'includes/home.css';</style>
<style type="text/css">@import '/MemberServices/Personnel/v2.0/css/home.css';</style>
<script language="JavaScript" src="js/common.js"></script>
<script language="JavaScript" src="js/CalendarPopup.js"></script>
<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript">
	$('document').ready(function() {
		$('a.delete').click(function(){
			if(confirm('Are you sure you want to delete all future bookings?'))
				return true;
			else
				return false;
		});
	});
</script>
</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table width="760" cellpadding="0" cellspacing="0" border="0" style="border-top: solid 1px #FFB700;border-bottom: solid 1px #FFB700;" align="center">
    <tr>
      <td>   
        <jsp:include page="admin_top_nav.jsp" flush="true"/>
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <%if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")){%>                  
                    <jsp:include page="admin_side_nav.jsp" flush="true"/>
                  <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-PRINCIPAL-VIEW")){%>
                    <jsp:include page="admin_principal_side_nav.jsp" flush="true"/>
                  <%}else if(usr.getUserPermissions().containsKey("PERSONNEL-VICEPRINCIPAL-VIEW")){%>
                    <jsp:include page="admin_viceprincipal_side_nav.jsp" flush="true"/>
                  <%}%>
                  <td width="551" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="391" align="left" valign="top" style="padding-top:8px;" class="content">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle"><%=LocationConstant.SUBSTITUTE_EASTERN_REGION%> Availability List</td>
                            </tr>
                            <tr>
                              <td class='displayPageSubTitle' align="center" style='color:#333333;'><br>Substitute Student Availability for <span style='color:#FF0000;'><%=sdf.format(v_date)%></span></td>
                            </tr>
                            <%if(request.getAttribute("msg") != null){%>
                              <tr>
                                <td width="100%"><br>
                                  <div style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
                                    <table cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td style="color:#ff0000;" class="messageText"><%=(String)request.getAttribute("msg")%></td>
                                      </tr>
                                    </table>
                                  </div>
                                </td>
                              </tr>
                            <%}%>
                            <tr>
                              <td width="100%">
                                <form name='availability_viewDateForm' id='availability_viewDateForm' method="post" action="availability_list.jsp">
                                  <br>
                                  <table width="100%" border='0'>
                                    
                                    <tr style="height:18px;">
                                      <td class="displayHeaderTitle" valign="middle" align='right'><span class="requiredStar">*&nbsp;</span>Change Availability View</td>
                                      <td valign="top">
                                        <table cellpadding="0" cellspacing="0">
                                          <tr>
                                            <td>
                                              <input class="requiredinput_date" 
                                                     type="text" 
                                                     name="view_date" 
                                                     style="width:100px;" 
                                                     value="<%=sdf.format(v_date)%>"
                                                     onChange="document.forms[0].submit();"
                                                     readonly>
                                            </td>
                                            <td>
                                              <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                                  onmouseover="this.src='images/cal_popup_02.gif';"
                                                  onmouseout="this.src='images/cal_popup_01.gif';"
                                                  onclick="datepicker.popup();"><br>
                                            </td>
                                            <td align='left' width='*'>
                                              &nbsp;&nbsp;<input type='submit' value="GO" style='color:#FF0000;font-size:11px;font-weight:bold;font-family:arial;'>
                                            </td>
                                          </tr>
                                        </table>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td colspan='2'>
                                        <personnel:EmployeeCards type='<%=com.esdnl.personnel.v2.model.sds.constant.PositionConstant.CASUAL_STUDENT_ASSISTANT%>' 
                                                         schoolYear='<%=StringUtils.getSchoolYear(Calendar.getInstance().getTime())%>'
                                                         location='<%=LocationConstant.SUBSTITUTE_EASTERN_REGION%>'
                                                         byAvailability='true'
                                                         viewDate='<%=v_date%>'
                                                         anyOrderCutOff='1' />
                                      </td>
                                    </tr>
                                  </table>
                                </form>
                              </td>
                            </tr>
                          </table>
                        </td>
                        <td width="160" align="left" valign="top" style="padding:0;">
                          <img src="images/man1.gif"><BR>
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
  <jsp:include page="footer.jsp" flush="true" />
  <script language="JavaScript">
    var datepicker = new CalendarPopup(document.forms['availability_viewDateForm'].elements['view_date']);
    datepicker.year_scroll = true;
    datepicker.time_comp = true;
  </script>
</body>
</html>
