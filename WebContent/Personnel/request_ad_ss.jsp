<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.*,
                 com.esdnl.util.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="personnel" %>

<%
  AdRequestBean req = (AdRequestBean) request.getAttribute("AD_REQUEST");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">@import 'includes/home.css';</style>
<script src = "js/jquery-1.10.2.js"></script>
<script src = "js/bootstrap.min.js"></script>
<script src = "js/jquery-ui.js"></script>
<link rel="stylesheet" href="includes/jquery-ui-1.12.0/jquery-ui.min.css">
<link rel="stylesheet" href="includes/bootstrap.min.css">
<script src="js/applicant_validations.js"></script>
<script language="JavaScript" src="js/CalendarPopup.js"></script> 
</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADREQUEST-REQUEST" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table align="center" width="760" cellpadding="0" cellspacing="0" border="0" style="border-top: solid 1px #FFB700;border-bottom: solid 1px #FFB700;">
    <tr>
      <td>   
        <jsp:include page="admin_top_nav.jsp" flush="true"/>
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <jsp:include page="admin_side_nav.jsp" flush="true"/>
                  <td width="551" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="391" align="left" valign="top">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle">Advertisement Request</td>
                            </tr>
                            <tr style="padding-top:8px;">
                              <td>
                                <form id="frmAdRequest" action="requestAdvertisementSS.html" method="post">
                                  <input type="HIDDEN" name="op" id="op" value="GET_EMPLOYEES">
                                  <table width="100%" cellpadding="0" cellspacing="5" border="0" style="padding:5px;">
                                    <%if(request.getAttribute("msg")!=null){%>
                                      <tr class="messageText" style="padding-top:8px;padding-bottom:8px;">
                                        <td colspan="2" align="center">
                                          <%=(String)request.getAttribute("msg")%>
                                        </td>
                                      </tr>
                                    <%}%>
                                    <tr>
                                      <td colspan="2" class="displayText" align="center" valign="middle"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="top"><span class="requiredStar">*&nbsp;</span>Position Title</td>
                                      <td>
                                        <input type="text" name="ad_title" id="ad_title" style="width:250px;" class="requiredInputBox" value="<%=((req!=null)&&!StringUtils.isEmpty(req.getTitle()))?req.getTitle():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="top"><span class="requiredStar">*&nbsp;</span>Location</td>
                                      <td>
                                        <personnel:Locations id='location' cls="requiredInputBox" value='<%=((req != null) && (req.getLocation() != null)) ? req.getLocation().getLocationDescription():""%>' />
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="top"><span class="requiredStar">*&nbsp;</span>Reason for Vacancy</td>
                                      <td>
                                        <textarea name="vacancy_reason" id="vacancy_reason" rows="5" style="line-height:12px;height:75px;width:250px;" class="requiredInputBox"><%=(req != null)?req.getVacancyReason():""%></textarea>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="top"><span class="requiredStar">*&nbsp;</span>Units </td>
                                      <td>
                                        <personnel:UnitTime id="ad_unit" cls="requiredInputBox" value='<%=(req != null)?req.getUnits():1.0%>' />
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="top">&nbsp;&nbsp;&nbsp;Job Type</td>
                                      <td>
                                      	<SELECT name="posting_type" id="posting_type" class="requiredInputBox" style="width:250px;">
											<OPTION VALUE='-1'>--- SELECT POSTING TYPE ---</OPTION>
											<OPTION VALUE="9">INTERNAL ONLY</OPTION>
											<OPTION VALUE="10">EXTERNAL ONLY</OPTION>
											<OPTION VALUE="11">INTERNAL AND EXTERNAL</OPTION>
										</SELECT>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="top">&nbsp;&nbsp;&nbsp;Ad Text</td>
                                      <td>
                                        <textarea name="ad_text" id="ad_text" rows="5" style="line-height:12px;height:75px;width:250px;" class="requiredInputBox"><%=((req != null)&&!StringUtils.isEmpty(req.getAdText()))?req.getAdText():""%></textarea>
                                      </td>
                                    </tr>
                                    <tr style="height:18px;">
                                      <td class="displayHeaderTitle" valign="top"><span class="requiredStar">*&nbsp;</span>Start Date</td>
                                      <td valign="top">
                                        <table cellpadding="0" cellspacing="0">
                                          <tr>
                                            <td><input class="requiredinput_date" type="text" name="start_date" id="start_date"  style="width:51px;" value="" readonly></td>
                                            <td>
                                              <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                                  onmouseover="this.src='images/cal_popup_02.gif';"
                                                  onmouseout="this.src='images/cal_popup_01.gif';"
                                                  onclick="startdatepicker.popup();"><br>
                                            </td>
                                          </tr>
                                        </table>
                                      </td>
                                    </tr>
                                    <tr style="height:18px;">
                                      <td class="displayHeaderTitle" valign="top">&nbsp;&nbsp;&nbsp;End Date</td>
                                      <td valign="top">
                                        <table cellpadding="0" cellspacing="0">
                                          <tr>
                                            <td><input class="requiredinput_date" type="text" name="end_date" id="end_date" style="width:51px;" value="" readonly></td>
                                            <td>
                                              <img class="requiredinput_popup_cal" src="images/cal_popup_01.gif" alt="choose date"
                                                  onmouseover="this.src='images/cal_popup_02.gif';"
                                                  onmouseout="this.src='images/cal_popup_01.gif';"
                                                  onclick="enddatepicker.popup();"><br>
                                            </td>
                                          </tr>
                                        </table>
                                      </td>
                                    </tr>
                                    
                                    <tr>
                                      <td colspan="2" class="displayHeaderTitle">
                                      &nbsp;&nbsp;&nbsp;Is position unadvertised? <input type="checkbox" id="is_unadvertised" name="is_unadvertised" class='requiredInputBox' /> 
                                      </td>
                                    </tr>
                                    
                                    <tr style="padding-top:8px;">
                                      <td colspan="2" align="right">
                                        <input type="button" value="Submit" onclick="checknewadrequest();">
                                      </td>
                                    </tr>
                                    <tr class="messageText" style="padding-top:8px;">
                                    	<td colspan="2" align="center">
                                    	<div id="divmsg">
                                    	<br />
                                        	<span id="spanmsg">
                                          	</span>
                                        </div>
                                        </td> 
                                    </tr>
                                    <%if(request.getAttribute("msg")!=null){%>
                                      <tr class="messageText" style="padding-top:8px;">
                                        <td colspan="2" align="center">
                                          <%=(String)request.getAttribute("msg")%>
                                        </td>
                                      </tr>
                                    <%}%>
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
    var startdatepicker = new CalendarPopup(document.forms['frmAdRequest'].elements['start_date']);
    startdatepicker.year_scroll = true;
    startdatepicker.time_comp = false;
    
    var enddatepicker = new CalendarPopup(document.forms['frmAdRequest'].elements['end_date']);
    enddatepicker.year_scroll = true;
    enddatepicker.time_comp = false;
  </script>
</body>
</html>
