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
  
  profile = (Profile) request.getAttribute("CURRENT_PROFILE");
  roer = (ROERequest) request.getAttribute("ROER");
  op = request.getParameter("op");
  sdf = new SimpleDateFormat("dd/MM/yyyy");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>ROE Request System</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/roerequest.css";</style>
    <script language="JavaScript" src="js/CalendarPopup.js"></script>
    <script language="JavaScript" src="js/common.js"></script>
	</head>
	<body style="margin-top:10px;">
    <form name="add_roe_request_form" method="post" action="addROERequest.html">
      <input type="hidden" name="op" value="ADDED">
      <input type="hidden" id="sick_dates" name="sick_dates" value="">
      <input type="hidden" id="unpaid_dates" name="unpaid_dates" value="">
      <table width="600" cellpadding="0" cellspacing="0"  align="center">
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
                      <td colspan="2" align="center" style="padding-right:5px;" class="content"><span class="requiredstar">*</span> Required Fields.</td>
                    </tr>
                    <%if("VIEW".equals(op)){%>
                    <tr>
                      <td colspan="2" align="right" style="padding-right:5px;" class="content">
                        <img src="images/printer.gif" onclick="window.print();" style="cursor:hand;"><br>
                      </td>
                    </tr>
                    <%}%>
                    <tr>
                      <td colspan="2"><h2>Profile</h2></td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="label">First Name:</span></td>
                      <td width="*" align="left" class="content">
                        <%if(profile != null){%>
                          <%=profile.getPersonnel().getFirstName()%>
                        <%}else{%>
                          <%=usr.getPersonnel().getFirstName()%>
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="label">Last Name:</span></td>
                      <td width="*" align="left" class="content">
                        <%if(profile != null){%>
                          <%=profile.getPersonnel().getLastName()%>
                        <%}else{%>
                          <%=usr.getPersonnel().getLastName()%>
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">SIN:</span></td>
                      <td width="*" align="left" class="content">
                        <%if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=profile.getSIN()%>
                        <%}else{%>
                          <input type="text" name="sin" id='sin' value="<%=((profile != null)&&(profile.getSIN()!=null))?profile.getSIN():""%>" style="width:100px;" class="requiredinput">
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Street Address:</span></td>
                      <td width="*" align="left" class="content">
                        <%if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=profile.getStreetAddress()%>
                        <%}else{%>
                          <input type="text" id="cur_street_addr" name="cur_street_addr" value="<%=((profile != null))?profile.getStreetAddress():""%>" style="width:175px;" class="requiredinput">
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">City/Town:</span></td>
                      <td width="*" align="left" class="content">
                        <%if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=profile.getCommunity()%>
                        <%}else{%>
                          <input type="text" id="cur_community" name="cur_community" value="<%=((profile != null))?profile.getCommunity():""%>" style="width:175px;" class="requiredinput">
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Province:</span></td>
                      <td width="*" align="left" class="content">
                        <%if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=profile.getProvince()%>
                        <%}else{%>
                          <select id="cur_province" name="cur_province" class="requiredinput">
                            <option value="NL" SELECTED>Newfoundland</option>
                          </select>
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Postal Code:</span></td>
                      <td width="*" align="left" class="content">
                        <%if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=profile.getPostalCode()%>
                        <%}else{%>
                          <input type="text" id="cur_postal_code" name="cur_postal_code" value="<%=((profile != null))?profile.getPostalCode():""%>" style="width:75px;" class="requiredinput">
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Home Phone #:</span></td>
                      <td width="*" align="left" class="content">
                        <%if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=profile.getPhoneNumber()%>
                        <%}else{%>
                          <input type="text" id="home_phone" name="home_phone" value="<%=((profile != null))?profile.getPhoneNumber():""%>" style="width:100px;" class="requiredinput">
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td class="label" width="40%" align="right" style="padding-right:5px;">Cell Phone #:</td>
                      <td width="*" align="left" class="content">
                        <%if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=(profile.getCellPhoneNumber()!=null)?profile.getCellPhoneNumber():""%>
                        <%}else{%>
                          <input type="text" id="cell_phone" name="cell_phone" value="<%=((profile != null))&&(profile.getCellPhoneNumber()!=null)?profile.getCellPhoneNumber():""%>" style="width:100px;" class="optionalinput">
                        <%}%>                          
                      </td>
                    </tr>
                    <tr>
                      <td class="label" width="40%" align="right" style="padding-right:5px;">Fax Phone #:</td>
                      <td width="*" align="left" class="content">
                        <%if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=(profile.getFaxNumber()!=null)?profile.getFaxNumber():""%>
                        <%}else{%>
                          <input type="text" id="fax" name="fax" value="<%=((profile != null)&&(profile.getFaxNumber()!=null))?profile.getFaxNumber():""%>" style="width:100px;" class="optionalinput">
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Gender:</span></td>
                      <td width="*" align="left" class="content">
                        <%if((profile != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=profile.getGender().equalsIgnoreCase("M")?"Male":"Female"%>
                        <%}else{%>
                          <select id="gender" name="gender" class="requiredinput">
                            <option value="M" <%=((profile != null)&&profile.getGender().equals("M"))?"SELECTED":""%>>Male</option>
                            <option value="F" <%=((profile != null)&&profile.getGender().equals("F"))?"SELECTED":""%>>Female</option>
                          </select>
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2"><h2>Work History</h2></td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">First Day Worked:<br>(since last ROE)</span></td>
                      <td width="*" align="left" valign="middle" class="content">
                        <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){%>
                            <%=sdf.format(roer.getFirstDayWorkedDate())%>
                        <%}else{%>
                            <table width="60" cellpadding="0" cellspacing="0" style="padding:0px;">
                              <tr style="height:18px;">
                                <td width="95%">
                                  <input class="requiredinput_date" type="text" name="first_day_worked" id="first_day_worked" style="width:51px;" 
                                         value="<%=((roer != null)&&op.equals("update"))?sdf.format(roer.getFirstDayWorkedDate()):""%>" readonly>
                                </td>
                                <td width="*" align="center">
                                  <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                      onmouseover="this.src='images/cal_popup_02.gif';"
                                      onmouseout="this.src='images/cal_popup_01.gif';"
                                      onclick="calendarPopup('first_day_worked');"><br>
                                </td>
                              </tr>
                            </table>
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">Last Day Worked:</span></td>
                      <td width="*" align="left" valign="middle" class="content">
                        <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=sdf.format(roer.getLastDayWorkedDate())%>
                        <%}else{%>
                          <table width="60" cellpadding="0" cellspacing="0" style="padding:0px;">
                            <tr style="height:18px;">
                              <td width="95%">
                                <input class="requiredinput_date" type="text" name="last_day_worked" id="last_day_worked" style="width:51px;" value="<%=((roer != null)&&op.equals("update"))?sdf.format(roer.getLastDayWorkedDate()):""%>" readonly>
                              </td>
                              <td width="*" align="center">
                                <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                    onmouseover="this.src='images/cal_popup_02.gif';"
                                    onmouseout="this.src='images/cal_popup_01.gif';"
                                    onclick="calendarPopup('last_day_worked');"><br>
                              </td>
                            </tr>
                          </table>
                        <%}%>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="top"><span class="requiredstar">*</span><span class="label">Hrs Worked During the Week OF:</span></td>
                      <td width="*" align="left" valign="top">
                        <table width="100%" cellspacing="0" cellpadding="0" style="padding:0px;">
                          <tr>
                            <td width="25%" align="center" valign="center" class="small_label_no_underline" style="border-right:solid 1px #c4c4c4;">May 29-Jun 2</td>
                            <td width="25%" align="center" valign="center"  class="small_label_no_underline" style="border-right:solid 1px #c4c4c4;">Jun 5-Jun 9</td>
                            <td width="25%" align="center" valign="center"  class="small_label_no_underline"  style="border-right:solid 1px #c4c4c4;">Jun 12-Jun 16</td>
                            <td width="25%" align="center" valign="center"  class="small_label_no_underline">Jun 19-Jun 23</td>
                          </tr>
                          <tr>
                            <td width="25%" align="center" class="content" style="border-right:solid 1px #c4c4c4;">
                              <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){%>
                               
                                <%=roer.getWeekOneHoursWorked()%>
                              <%}else{%>
                                <input type="text" id="week1" name="week1" value="<%=((roer != null)&&op.equals("update"))?""+roer.getWeekOneHoursWorked():"0"%>" style="width:25px;" class="requiredinput">
                              <%}%>
                            </td>
                            <td width="25%" align="center" class="content" style="border-right:solid 1px #c4c4c4;">
                              <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){%>
                                 
                                <%=roer.getWeekTwoHoursWorked()%>
                              <%}else{%>
                                <input type="text" id="week2" name="week2" value="<%=((roer != null)&&op.equals("update"))?""+roer.getWeekTwoHoursWorked():"0"%>" style="width:25px;" class="requiredinput">
                              <%}%>
                            </td>
                            <td width="25%" align="center" class="content" style="border-right:solid 1px #c4c4c4;">
                              <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){%>
                               
                                <%=roer.getWeekThreeHoursWorked()%>
                              <%}else{%>
                                <input type="text" id="week3" name="week3" value="<%=((roer != null)&&op.equals("update"))?""+roer.getWeekThreeHoursWorked():"0"%>" style="width:25px;" class="requiredinput"></td>
                              <%}%>
                            <td width="25%" align="center" class="content">
                              <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){%>
                            
                                <%=roer.getWeekFourHoursWorked()%>
                              <%}else{%>
                                <input type="text" id="week4" name="week4" value="<%=((roer != null)&&op.equals("update"))?""+roer.getWeekFourHoursWorked():"0"%>" style="width:25px;" class="requiredinput">
                              <%}%>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="top"><span class="requiredstar">*</span><span class="label">Day(s) Worked That <br>you have not been paid:<br>(if applicable)</span></td>
                      <td width="*" align="left" valign="middle" class="content">
                        <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){
                            if(roer.getUnpaidDates() != null){
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
                          }else{%>
                          <table width="185" cellpadding="0" cellspacing="0" style="padding:0px;">
                            <tr style="height:18px;">
                              <td width="57"><input class="requiredinput_date" type="text" name="unpaid_date" id="unpaid_date" style="width:51px;" value="" readonly></td>
                              <td width="18" align="center">
                                <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                    onmouseover="this.src='images/cal_popup_02.gif';"
                                    onmouseout="this.src='images/cal_popup_01.gif';"
                                    onclick="calendarPopup('unpaid_date');"><br>
                              </td>
                              <td width="25" align="right" style="padding-left:5px;padding-right:5px;"><span class="small_label_no_underline">Hrs:</span></td>
                              <td width="40" align="left"><input type="text" id="hrs" name="hrs" value="0" style="width:25px;" class="requiredinput"></td>
                              <td width="*" align="right">
                                <img src="images/btn_add_01.gif" alt="add unpaid day"
                                    style="padding-left:5px;"
                                    onmouseover="this.src='images/btn_add_02.gif';"
                                    onmouseout="this.src='images/btn_add_01.gif';"
                                    onclick="add_unpaid_date('unpaid_dates', 'unpaid_date', 'hrs');"><br>
                              </td>
                            </tr>
                          </table>
                        <%}%>
                      </td>
                    </tr>
                    <tr id="unpaid_dates_row" style="display:none;">
                      <td width="40%" align="right" valign="middle">&nbsp;</td>
                      <td width="*" align="left" valign="middle" class="content" style="padding-left:0px;">
                        <table id="unpaid_dates_tbl" align='left' width='50%' cellpadding='1' cellspacing='0' border='0' style="border: solid 1px #c4c4c4;">
                          <tr><td colspan="2" align="center" style="background-color:#c4c4c4;" class="label_no_underline">unpaid days</td></tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" style="padding-right:5px;"><span class="requiredstar">*</span> <span class="label">Reason for Record:</span></td>
                      <td width="*" align="left" class="content">
                        <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=roer.getReasonForRecordRequest().replaceAll("_", " ")%>
                        <%}else{%>
                          <select id="reason_for_record" name="reason_for_record" class="requiredinput" onchange="request_reason_info(this);">
                            <option value="STUDENT_ASSISTANT" SELECTED>STUDENT ASSISTANT</option>
                            <option value="STUDENT_ASSISTANT_MATERNITY">STUDENT ASSISTANT MATERNITY LEAVE</option>
                            <option value="SUBSTITUTE_STUDENT_ASSISTANT">SUBSTITUTE STUDENT ASSISTANT</option>
                            <option value="PERM_TEACHER_SICK_LEAVE">PERM TEACHER SICK LEAVE</option>
                            <option value="PERM_TEACHER_REDUNDANT">PERM TEACHER REDUNDANT</option>
                            <option value="TEACHER_REPLACEMENT">TEACHER REPLACEMENT</option>
                            <option value="TEACHER_MATERNITY">TEACHER MATERNITY LEAVE</option>
                            <option value="TEACHER_MATERNITY">TEACHER PARENTAL LEAVE</option>
                            <option value="SUBSTITUTE_TEACHER">SUBSTITUTE TEACHER</option>
                          </select>
                        <%}%>
                      </td>
                    </tr>
                    <tr id="MATERNITY" style="<%=((roer != null) && ("VIEW".equalsIgnoreCase(op))&&(roer.getReasonForRecordRequest().indexOf("MATERNITY") >= 0))?"display:inline;":"display:none;"%>">
                      <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">Baby Birth Date:</span></td>
                      <td width="*" align="left" valign="middle" class="content">
                        <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=(roer.getBabyBirthDate()!=null)?sdf.format(roer.getBabyBirthDate()):""%>
                        <%}else{%>
                          <table width="60" cellpadding="0" cellspacing="0" style="padding:0px;">
                            <tr style="height:18px;">
                              <td width="95%"><input class="requiredinput_date" type="text" name="birth_date" id="birth_date" style="width:51px;" value="" readonly></td>
                              <td width="*" align="center">
                                <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                    onmouseover="this.src='images/cal_popup_02.gif';"
                                    onmouseout="this.src='images/cal_popup_01.gif';"
                                    onclick="calendarPopup('birth_date');"><br>
                              </td>
                            </tr>
                          </table>
                        <%}%>
                      </td>
                    </tr>
                    <tr id="REPLACEMENT" style="<%=((roer != null) && ("VIEW".equalsIgnoreCase(op))&&(roer.getReasonForRecordRequest().indexOf("REPLACEMENT") >= 0))?"display:inline;":"display:none;"%>">
                      <td colspan="2">
                        <table width="100%" cellspacing="6" cellpadding="0" style="padding:0px;">
                          <tr>
                            <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">Replacement Start Date:</span></td>
                            <td width="*" align="left" valign="middle" class="content">
                              <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){%>
                                <%=(roer.getReplacementStartDate() != null)?sdf.format(roer.getReplacementStartDate()):""%>
                              <%}else{%>
                                <table width="60" cellpadding="0" cellspacing="0" style="padding:0px;">
                                  <tr style="height:18px;">
                                    <td width="95%"><input class="requiredinput_date" type="text" name="replacement_start_date" id="replacement_start_date" style="width:51px;" value="" readonly></td>
                                    <td width="*" align="center">
                                      <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                          onmouseover="this.src='images/cal_popup_02.gif';"
                                          onmouseout="this.src='images/cal_popup_01.gif';"
                                          onclick="calendarPopup('replacement_start_date');"><br>
                                    </td>
                                  </tr>
                                </table>
                              <%}%>
                            </td>
                          </tr>
                          <tr>
                            <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">Replacement End Date:</span></td>
                            <td width="*" align="left" valign="middle" class="content">
                              <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){%>
                                <%=(roer.getReplacementFinishDate() != null)?sdf.format(roer.getReplacementFinishDate()):""%>
                              <%}else{%>
                                <table width="60" cellpadding="0" cellspacing="0" style="padding:0px;">
                                  <tr style="height:18px;">
                                    <td width="95%"><input class="requiredinput_date" type="text" name="replacement_finish_date" id="replacement_finish_date" style="width:51px;" value="" readonly></td>
                                    <td width="*" align="center">
                                      <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                          onmouseover="this.src='images/cal_popup_02.gif';"
                                          onmouseout="this.src='images/cal_popup_01.gif';"
                                          onclick="calendarPopup('replacement_finish_date');"><br>
                                    </td>
                                  </tr>
                                </table>
                              <%}%>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">Sick Leave Dates:<br>(if applicable)</span></td>
                      <td width="*" align="left" valign="top" class="content">
                        <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){
                            if(roer.getSickLeaveDates() != null){
                              iter = roer.getSickLeaveDates().iterator();
                              if(iter.hasNext()){
                                while(iter.hasNext()){
                        %>        <%= sdf.format((java.util.Date)iter.next())%><br>
                        <%      }
                              }
                            }
                            else
                              out.println("NO SICK LEAVE");
                          }else{%>
                          <table width="120" cellpadding="0" cellspacing="0" style="padding:0px;">
                            <tr style="height:18px;">
                              <td width="57"><input class="requiredinput_date" type="text" name="sick_date" id="sick_date" style="width:51px;" value="" readonly></td>
                              <td width="18" align="center">
                                <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                    onmouseover="this.src='images/cal_popup_02.gif';"
                                    onmouseout="this.src='images/cal_popup_01.gif';"
                                    onclick="calendarPopup('sick_date');"><br>
                              </td>
                              <td width="*" align="right">
                                <img src="images/btn_add_01.gif" alt="add sick leave date"
                                    style="padding-left:5px;"
                                    onmouseover="this.src='images/btn_add_02.gif';"
                                    onmouseout="this.src='images/btn_add_01.gif';"
                                    onclick="add_sick_leave_date('sick_leave_dates', 'sick_date');"><br>
                              </td>
                            </tr>
                          </table>
                        <%}%>
                      </td>
                    </tr>
                    <tr id="sick_leave_dates_row" style="display:none;">
                      <td width="40%" align="right" valign="middle">&nbsp;</td>
                      <td width="*" align="left" valign="middle" class="content" style="padding-left:0px;">
                        <table id="sick_leave_dates" align='left' width='40%' cellpadding='1' cellspacing='0' border='0' style="border: solid 1px #c4c4c4;">
                          <tr><td colspan="2" align="center" style="background-color:#c4c4c4;" class="label_no_underline">Sick Leave Dates</td></tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="40%" align="right" valign="middle"><span class="requiredstar">*</span><span class="label">Date Last Record Issued:</span></td>
                      <td width="*" align="left" valign="middle" class="content">
                        <%if((roer != null) && ("VIEW".equalsIgnoreCase(op))){%>
                          <%=sdf.format(roer.getLastRecordIssuedDate())%>
                        <%}else{%>
                          <table width="60" cellpadding="0" cellspacing="0" style="padding:0px;">
                            <tr style="height:18px;">
                              <td width="95%"><input class="requiredinput_date" type="text" name="last_record_issued_date" id="last_record_issued_date" style="width:51px;" value="" readonly></td>
                              <td width="*" align="center">
                                <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                    onmouseover="this.src='images/cal_popup_02.gif';"
                                    onmouseout="this.src='images/cal_popup_01.gif';"
                                    onclick="calendarPopup('last_record_issued_date');"><br>
                              </td>
                            </tr>
                          </table>
                        <%}%>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td width="100%" height="*" align="right" valign="bottom">
                  <table width="100%" cellpadding="0" cellspacing="6" border="0">
                    <tr>
                      <td id="server_message" width="100%" align="center" valign="middle" class="message_info">
                      <%if(request.getAttribute("msg") != null){%>
                          <%=request.getAttribute("msg")%>
                      <%}else{%>
                          &nbsp;
                      <%}%>
                      </td>
                      
                      <%if((roer == null)){%>
                        <td width="100%" align="center" valign="middle">
                          <img src="images/btn_submit_01.gif"
                              onmouseover="this.src='images/btn_submit_02.gif';"
                              onmouseout="this.src='images/btn_submit_01.gif';"
                              onclick="if(validate_request(document.forms[0])){process_message('server_message', 'Adding ROE Request PLEASE WAIT...'); document.forms[0].op.value='ADDED'; document.forms[0].submit();}"><br>
                        </td>
                      <%}else if(usr.getUserPermissions().containsKey("ROEREQUEST-ADMIN")){%>
                        <td width="100%" align="center" valign="middle">
                          <img src="images/btn_complete_01.gif"
                              onmouseover="this.src='images/btn_complete_02.gif';"
                              onmouseout="this.src='images/btn_complete_01.gif';"
                              onclick="document.location.href='completeRequest.html?req=<%=(roer != null)?roer.getRequestID():-1%>';"><br>
                        </td>
                      <%}%>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form>
	</body>
</html>
