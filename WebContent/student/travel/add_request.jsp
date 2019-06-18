<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,
                  com.esdnl.servlet.*,
                  com.esdnl.student.travel.bean.*,
                  com.esdnl.student.travel.constant.*,com.esdnl.util.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
  Form form = (Form) request.getAttribute("FORM");
  TravelRequestBean treq = (TravelRequestBean) request.getAttribute("TRAVELREQUESTBEAN");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Newfoundland &amp; Labrador English School District - Member Services - STUDENT TRAVEL</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import 'css/home.css';</style>
  <style type="text/css">
  	.displayHeaderTitle { 
  		border-right: solid 1px #333333;
  		padding-right: 5px;
  	}
  	#addreq {
  		border-collapse:collapse;  		
  	}
  	#addreq tr td {
  		border: solid 1px #333333;
  		border-collapse: collapse;
  	}
  </style>
  <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
  <script type="text/javascript">
  	$('document').ready(function() {
			$('#addreq tr:odd td').css('background-color', '#f0f0f0');
  	});
  </script>
</head>
<body style="margin-top:15px;">

  <esd:SecurityCheck permissions="STUDENT-TRAVEL-PRINCIPAL-VIEW" />
  
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <jsp:include page="header.jsp" flush="true" />
  <table width="760" cellpadding="0" cellspacing="0" border="0"  align="center">
    <tr>
      <td>   
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="30%" align="left" valign="top" 
                    style="padding-top:10px;padding-left:5px;border-left:solid 1px #e0e0e0;border-right:solid 1px #FFB700;">
                    <img src="images/spacer.gif" width="1" height="5"><BR>
                    
                    <jsp:include page="side_nav.jsp" flush="true"/>
                    
                    <img src="images/spacer.gif" width="1" height="10"><BR>
                  </td>
                  <td width="*" align="left" valign="top" style='border-right:solid 1px #e0e0e0; padding:10px;'>		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="100%" align="left" valign="top">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle"><%=(treq == null)?"Add":"Update"%> Student Travel Request</td>
                            </tr>
                            
                            <tr style="padding-top:8px;">
                              <td>
                                <form id="frmAddTravelRequest" action="addRequest.html" method="post" ENCTYPE="multipart/form-data">
                                  <%if(treq != null){%>
                                    <input type='hidden' name='op' value='UPDATE'>
                                    <input type='hidden' name='request_id' value='<%=treq.getRequestId()%>'>
                                  <%}else{%>
                                    <input type='hidden' name='op' value='ADD'>
                                  <%}%>
                                  <p>
                                  <table id='addreq' width="100%" cellpadding="5" cellspacing="0" border="0" style="padding:5px;">
                                    <tr>
                                      <td colspan="2" class="displayText" align="center" valign="middle"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar">*&nbsp;</span>Destination</td>
                                      <td>
                                        <input type="text" name="destination" id="destination" style="width:225px;" class="requiredInputBox" value="<%=(form!=null)?form.get("destination"):(treq != null)?treq.getDestination():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="top" align="right" width='200px'><span class="requiredStar">*&nbsp;</span>Departure Date<br>(dd/mm/yyyy)</td>
                                      <td>
                                        <input type="text" name="departure_date" id="departure_date" style="width:225px;" class="requiredInputBox" value="<%=(form!=null)?form.get("departure_date"):(treq != null)?treq.getDepartureDateFormated():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="top" align="right" width='200px'><span class="requiredStar" >*&nbsp;</span>Return Date<br>(dd/MM/yyyy)</td>
                                      <td>
                                        <input type="text" name="return_date" id="return_date" style="width:225px;" class="requiredInputBox" value="<%=(form!=null)?form.get("return_date"):(treq != null)?treq.getReturnDateFormatted():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="top" align="right" width='200px'><span class="requiredStar" >*&nbsp;</span># School Days Missed<br></td>
                                      <td>
                                        <input type="text" name="days_missed" id="days_missed" style="width:225px;" class="requiredInputBox" placeholder="Enter a Number ONLY" value="<%=(form!=null)?form.get("days_missed"):(treq != null)?treq.getDaysMissed():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="top" align="right" width='200px'><span class="requiredStar">*&nbsp;</span>Reason for Travel</td>
                                      <td>
                                        <textarea name="rational" id="rational" style="width:225px;height:125px;" class="requiredInputBox"><%=(form!=null)?form.get("rational"):(treq != null)?treq.getRational():""%></textarea>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar" >*&nbsp;</span>Grades Involved</td>
                                      <td>
                                        <input type="text" name="grades" id="grades" style="width:225px;" class="requiredInputBox" value="<%=(form!=null)?form.get("grades"):(treq != null)?treq.getGrades():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar" >*&nbsp;</span># Students Involved</td>
                                      <td>
                                        <input type="text" name="num_students" id="num_students" style="width:225px;" class="requiredInputBox" placeholder="Enter a Number ONLY" value="<%=(form!=null)?form.get("num_students"):(treq != null)?Integer.toString(treq.getNumStudents()):""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar" >*&nbsp;</span>Total # Chaperones</td>
                                      <td>
                                        <input type="text" name="total_chaperons" id="total_chaperons" style="width:225px;" class="requiredInputBox" placeholder="Enter a Number ONLY" value="<%=(form!=null)?form.get("total_chaperons"):(treq != null)?Integer.toString(treq.getTotalChaperons()):""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar" >*&nbsp;</span>Total # Teacher Chaperones</td>
                                      <td>
                                        <input type="text" name="total_teacher_chaperons" id="total_teacher_chaperons" style="width:225px;" class="requiredInputBox" placeholder="Enter a Number ONLY" value="<%=(form!=null)?form.get("total_teacher_chaperons"):(treq != null)?Integer.toString(treq.getTotalTeacherChaperons()):""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar" >*&nbsp;</span>Teacher Chaperone Name(s)</td>
                                      <td>
                                        <input type="text" name="teacher_chaperons" id="teacher_chaperons" style="width:225px;" class="requiredInputBox" value="<%=(form!=null)?form.get("teacher_chaperons"):(treq != null)?treq.getTeacherChaperon():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar" >*&nbsp;</span>Total # Non-Teacher Chaperones</td>
                                      <td>
                                        <input type="text" name="total_nonteacher_chaperons" id="total_nonteacher_chaperons" style="width:225px;" class="requiredInputBox" placeholder="Enter a Number ONLY" value="<%=(form!=null)?form.get("total_nonteacher_chaperons"):(treq != null)?Integer.toString(treq.getTotalOtherChaperons()):""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar" style="color:#FFFFFF;" >*&nbsp;</span> Non-Teacher Chaperone Name(s)</td>
                                      <td>
                                        <input type="text" name="other_chaperons" id="other_chaperons" style="width:225px;" class="requiredInputBox" value="<%=(form!=null)?form.get("other_chaperons"):(treq != null)?treq.getOtherChaperon():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar">*&nbsp;</span>
                                      	Has each chaperone been approved by the Principal?
                                      </td>
                                      <td class='displayText' valign='middle'> 
                                        <input type="radio" name="chaperons_approved" id="chaperons_approved" value="true" <%=(form!=null && form.getBoolean("chaperons_approved"))?"CHECKED":(treq != null && treq.isChaperonsApproved())?"CHECKED":""%>> Yes
                                        <input type="radio" name="chaperons_approved" id="chaperons_approved" value="false" <%=(form!=null && !form.getBoolean("chaperons_approved"))?"CHECKED":(treq != null && !treq.isChaperonsApproved())?"CHECKED":""%>> No
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar">*&nbsp;</span>
                                      	Does this trip involve billeting?
                                      </td>
                                      <td class='displayText' valign='middle'> 
                                        <input type="radio" name="billeting_involved" id="billeting_involved" value="true" <%=(form!=null && form.getBoolean("billeting_involved"))?"CHECKED":(treq != null && treq.isBilletingInvolved())?"CHECKED":""%>> Yes
                                        <input type="radio" name="billeting_involved" id="billeting_involved" value="false" <%=(form!=null && !form.getBoolean("billeting_involved"))?"CHECKED":(treq != null && !treq.isBilletingInvolved())?"CHECKED":""%>> No
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar">*&nbsp;</span>
                                      	Is there any school fundraising associated with this trip?
                                      </td>
                                      <td class='displayText' valign='middle'> 
                                        <input type="radio" name="school_fundraising" id="school_fundraising" value="true" <%=(form!=null && form.getBoolean("school_fundraising"))?"CHECKED":(treq != null && treq.isSchoolFundraising())?"CHECKED":""%>> Yes
                                        <input type="radio" name="school_fundraising" id="school_fundraising" value="false" <%=(form!=null && !form.getBoolean("school_fundraising"))?"CHECKED":(treq != null && !treq.isSchoolFundraising())?"CHECKED":""%>> No
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar" >*&nbsp;</span>Emergency Contact<br>(xxx-xxx-xxxx)</td>
                                      <td>
                                        <input type="text" name="emergency_contact" id="emergency_contact" style="width:225px;" class="requiredInputBox" value="<%=(form!=null)?form.get("emergency_contact"):(treq != null)?treq.getEmergencyContact():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle" valign="middle" align="right" width='200px'><span class="requiredStar">*&nbsp;</span>Itinerary Document</td>
                                      <td>
                                        <input type="file" name="itinerary_document" id="itinerary_document" style="width:225px;" class="requiredInputBox" value="<%=(form!=null)?form.get("itinerary_document"):(treq != null)?treq.getIteneraryFilename():""%>">
                                      </td>
                                    </tr>
                                    <tr>
                                      <td colspan="2" align='center' style='padding-top:10px;'>
                                        <input type="submit" value="<%=(treq == null)?"Add":"Update"%> Request">
                                      </td>
                                    </tr>
                                  </table>
                                  </p>
                                </form>
                                
                                <%if(request.getAttribute("msg") != null){%>
                                  <p style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
                                    <table cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td class="messageText"><%=(String)request.getAttribute("msg")%></td>
                                      </tr>
                                    </table>
                                  </p>
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
      </td>
    </tr>
  </table>
  <jsp:include page="footer.jsp" flush="true" />
</body>
</html>
