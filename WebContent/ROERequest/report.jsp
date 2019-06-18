<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,com.awsd.personnel.profile.*,
                 com.esdnl.roer.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>

<%!
  User usr = null;
  Profile profile = null;
  ROERequest roer = null;
  UnpaidDay uday = null;
  SimpleDateFormat sdf = null;
  Iterator r_iter = null;
  Iterator iter = null;
  String op;
%>
<%
  usr = (User) session.getAttribute("usr"); 
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("ROEREQUEST-VIEW")
      || usr.getUserPermissions().containsKey("ROEREQUEST-ADMIN")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}

  sdf = new SimpleDateFormat("dd/MM/yyyy");
  
  r_iter = ROERequestDB.getROERequests(sdf.parse(request.getParameter("dt"))).iterator();
  
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>ROE Request System</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/roerequest.css";</style>
    <script language="JavaScript" src="js/CalendarPopup.js"></script>
    <script language="JavaScript" src="js/common.js"></script>
    <STYLE TYPE="text/css">
      br.pagebreak {page-break-before: always}
    </STYLE> 
	</head>
	<body style="margin-top:10px;" onload="window.print(); self.close();">
    <form name="add_roe_request_form" method="post" action="addROERequest.html">
      <input type="hidden" name="op" value="ADDED">
      <input type="hidden" id="sick_dates" name="sick_dates" value="">
      <input type="hidden" id="unpaid_dates" name="unpaid_dates" value="">
      <table width="600" cellpadding="0" cellspacing="0"  align="center">
        <%while(r_iter.hasNext()){
            roer = (ROERequest) r_iter.next();
            profile = roer.getPersonnel().getProfile();
        %>
        <tr>
          <td id="form_header" width="100%" height="75">
            <img src="images/roer_header.gif"><br>
          </td>
        </tr>
        <tr>
          <td id="maincontent" width="100%">
            <table width="100%" cellpadding="0" cellspacing="6" border="0">
              <tr>
                <td width="100%">
                  <table width="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      <td colspan="2"><h2>Profile</h2></td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="label">First Name:</span></td>
                      <td width="*" align="left" class="content">
                          <%=profile.getPersonnel().getFirstName()%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="label">Last Name:</span></td>
                      <td width="*" align="left" class="content">
                          <%=profile.getPersonnel().getLastName()%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">SIN:</span></td>
                      <td width="*" align="left" class="content">
                          <%=profile.getSIN()%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Street Address:</span></td>
                      <td width="*" align="left" class="content">
                          <%=profile.getStreetAddress()%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">City/Town:</span></td>
                      <td width="*" align="left" class="content">
                          <%=profile.getCommunity()%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Province:</span></td>
                      <td width="*" align="left" class="content">
                          <%=profile.getProvince()%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Postal Code:</span></td>
                      <td width="*" align="left" class="content">
                          <%=profile.getPostalCode()%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Home Phone #:</span></td>
                      <td width="*" align="left" class="content">
                          <%=profile.getPhoneNumber()%>
                      </td>
                    </tr>
                    <tr>
                      <td class="label" width="40%" align="right" style="padding-right:5px;">Cell Phone #:</td>
                      <td width="*" align="left" class="content">
                          <%=(profile.getCellPhoneNumber()!=null)?profile.getCellPhoneNumber():""%>
                      </td>
                    </tr>
                    <tr>
                      <td class="label" width="40%" align="right" style="padding-right:5px;">Fax Phone #:</td>
                      <td width="*" align="left" class="content">
                          <%=(profile.getFaxNumber()!=null)?profile.getFaxNumber():""%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Gender:</span></td>
                      <td width="*" align="left" class="content">
                          <%=profile.getGender().equalsIgnoreCase("M")?"Male":"Female"%>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2"><h2>Work History</h2></td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">First Day Worked:<br>(since last ROE)</span></td>
                      <td width="*" align="left" valign="middle" class="content">
                            <%=sdf.format(roer.getFirstDayWorkedDate())%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">Last Day Worked:</span></td>
                      <td width="*" align="left" valign="middle" class="content">
                          <%=sdf.format(roer.getLastDayWorkedDate())%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="top"><span class="requiredstar">*</span><span class="label">Hrs Worked During the Week OF:</span></td>
                      <td width="*" align="left" valign="top">
                        <table width="100%" cellspacing="0" cellpadding="0" style="padding:0px;">
                          <tr>
                            <td width="25%" align="center" valign="center" class="small_label_no_underline" style="border-right:solid 1px #c4c4c4;">May 30-Jun 3</td>
                            <td width="25%" align="center" valign="center"  class="small_label_no_underline" style="border-right:solid 1px #c4c4c4;">Jun 6-Jun 10</td>
                            <td width="25%" align="center" valign="center"  class="small_label_no_underline"  style="border-right:solid 1px #c4c4c4;">Jun 13-Jun 17</td>
                            <td width="25%" align="center" valign="center"  class="small_label_no_underline">Jun 20-Jun 24</td>
                          </tr>
                          <tr>
                            <td width="25%" align="center" class="content" style="border-right:solid 1px #c4c4c4;">
                                <%=roer.getWeekOneHoursWorked()%>
                            </td>
                            <td width="25%" align="center" class="content" style="border-right:solid 1px #c4c4c4;">
                                <%=roer.getWeekTwoHoursWorked()%>
                            </td>
                            <td width="25%" align="center" class="content" style="border-right:solid 1px #c4c4c4;">
                                <%=roer.getWeekThreeHoursWorked()%>
                            <td width="25%" align="center" class="content">
                                <%=roer.getWeekFourHoursWorked()%>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="top"><span class="requiredstar">*</span><span class="label">Day(s) Worked That <br>you have not been paid:<br>(if applicable)</span></td>
                      <td width="*" align="left" valign="middle" class="content">
                        
                        <%    if(roer.getUnpaidDates() != null){
                              iter = roer.getUnpaidDates().iterator();
                              if(iter.hasNext()){
                                while(iter.hasNext()){
                                  uday = (UnpaidDay) iter.next();
                        %>        <%= sdf.format(uday.getUnpaidDate()) + " - " + uday.getHoursWorked() + " HRS"%><br>
                        <%      }
                              }
                            }
                            else
                              out.println("NO UNPAID DATES");
                        %>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Reason for Record:</span></td>
                      <td width="*" align="left" class="content">
                          <%=roer.getReasonForRecordRequest().replaceAll("_", " ")%>
                      </td>
                    </tr>
                    <tr id="MATERNITY" style="<%=((roer != null) &&(roer.getReasonForRecordRequest().indexOf("MATERNITY") >= 0))?"display:inline;":"display:none;"%>">
                      <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">Baby Birth Date:</span></td>
                      <td width="*" align="left" valign="middle" class="content">
                          <%=(roer.getBabyBirthDate()!=null)?sdf.format(roer.getBabyBirthDate()):""%>
                      </td>
                    </tr>
                    <tr id="REPLACEMENT" style="<%=((roer != null) && (roer.getReasonForRecordRequest().indexOf("REPLACEMENT") >= 0))?"display:inline;":"display:none;"%>">
                      <td colspan="2">
                        <table width="100%" cellspacing="6" cellpadding="0" style="padding:0px;">
                          <tr>
                            <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">Replacement Start Date:</span></td>
                            <td width="*" align="left" valign="middle" class="content">
                                <%=(roer.getReplacementStartDate() != null)?sdf.format(roer.getReplacementStartDate()):""%>
                            </td>
                          </tr>
                          <tr>
                            <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">Replacement End Date:</span></td>
                            <td width="*" align="left" valign="middle" class="content">
                                <%=(roer.getReplacementFinishDate() != null)?sdf.format(roer.getReplacementFinishDate()):""%>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="top"><span class="requiredstar">*</span><span class="label">Sick Leave Dates:<br>(if applicable)</span></td>
                      <td width="*" align="left" valign="middle" class="content">
                        <%  if(roer.getSickLeaveDates() != null){
                              iter = roer.getSickLeaveDates().iterator();
                              if(iter.hasNext()){
                                while(iter.hasNext()){
                        %>        <%= sdf.format((java.util.Date)iter.next())%><br>
                        <%      }
                              }
                            }
                            else
                              out.println("NO SICK LEAVE");
                          %>   
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">Date Last Record Issued:</span></td>
                      <td width="*" align="left" valign="middle" class="content">
                          <%=sdf.format(roer.getLastRecordIssuedDate())%>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr><td width="100%"><br class="pagebreak"></td></tr>
        <%}%>
      </table>
    </form>
	</body>
</html>
