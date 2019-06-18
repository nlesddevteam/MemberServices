<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,com.awsd.personnel.*,com.awsd.school.*,com.awsd.security.crypto.*,
                  com.esdnl.servlet.*,
                  com.esdnl.student.travel.bean.*,
                  com.esdnl.student.travel.constant.*,com.esdnl.util.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
  Form form = (Form) request.getAttribute("FORM");
  TravelRequestBean treq = (TravelRequestBean) request.getAttribute("TRAVELREQUESTBEAN");
  Personnel approver = (Personnel) request.getAttribute("APPROVER");
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
  	#info {
  		border-collapse:collapse;  		
  	}
  	#info tr td {
  		border: solid 1px #333333;
  		border-collapse: collapse;
  	}
  </style>
  <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
  
  <script type="text/javascript">
  	$('document').ready(function() {
			$('#info tr:odd td').css('background-color', '#f0f0f0');
  	});
  </script>
  
</head>
<body style="margin-top:15px;">

  <esd:SecurityCheck permissions="STUDENT-TRAVEL-ADMIN-VIEW,STUDENT-TRAVEL-PRINCIPAL-VIEW" />
  
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
                  <td width="21" align="left" valign="top">
                    <img src="images/spacer.gif" width="21" height="1"><BR>
                  </td>
                  <td width="*" align="left" valign="top" style='border-right:solid 1px #e0e0e0; padding:10px;'>		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="100%" align="left" valign="top">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle"><!--<%=(treq == null)?"Add":"Update"%>--> Student Travel Request</td>
                            </tr>
                            
                            <tr style="padding-top:3px;">
                              <td>
                                <%if(request.getAttribute("msg") != null){%>
                                  <p>
                                    <table cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td class="messageText"><%=(String)request.getAttribute("msg")%></td>
                                      </tr>
                                    </table>
                                  </p>
                                <%}%>
                                
                                <form id="frmAddTravelRequest" action="addRequest.html" method="post">
                                  <%if(treq != null){%>
                                    <input type='hidden' name='op' value='UPDATE'>
                                    <input type='hidden' name='request_id' value='<%=treq.getRequestId()%>'>
                                  <%}else{%>
                                    <input type='hidden' name='op' value='ADD'>
                                  <%}%>
                                  <p>
                                  <table id="info" width="100%" cellpadding="5" cellspacing="0" border="0" style="padding: 5px;">
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Current Status</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?treq.getStatus().getDescription():"&nbsp;"%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Requested By</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?PersonnelDB.getPersonnel(treq.getRequestedBy()).getFullNameReverse():"&nbsp;"%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Requested Date</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?treq.getRequestedDateFormatted():"&nbsp;"%>
                                      </td>
                                    </tr>
                                    <%
                                    	if(treq.getRequestedBy() != treq.getActionedBy()){
                                    %>
                                      <tr>
                                        <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'><%=(treq != null)?treq.getStatus().getDescription():"&nbsp;"%> By</td>
                                        <td class="displayText" >
                                          <%=(treq != null)?PersonnelDB.getPersonnel(treq.getActionedBy()).getFullNameReverse():"&nbsp;"%>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'><%=(treq != null)?treq.getStatus().getDescription():"&nbsp;"%> Date</td>
                                        <td class="displayText" >
                                          <%=(treq != null)?treq.getActionDateFormatted():"&nbsp;"%>
                                        </td>
                                      </tr>
                                    <%}%>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>School</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?SchoolDB.getSchool(treq.getSchoolId()).getSchoolName():"&nbsp;"%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Destination</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?treq.getDestination():"&nbsp;"%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="top" align="right" width='200px'>Departure Date</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?treq.getDepartureDateFormated():""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="top" align="right" width='200px'>Return Date</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?treq.getReturnDateFormatted():""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="top" align="right" width='200px'># School Days Missed</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?Double.toString(treq.getDaysMissed()):""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="top" align="right" width='200px'>Reason for Travel</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?StringUtils.encodeHTML(treq.getRational()):""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Grades Involved</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?treq.getGrades():""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'># Students Involved</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?Integer.toString(treq.getNumStudents()):""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Total # Chaperones</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?Integer.toString(treq.getTotalChaperons()):""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Total # Teacher Chaperones</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?Integer.toString(treq.getTotalTeacherChaperons()):""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Teacher Chaperons</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?treq.getTeacherChaperon():""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Total # Non-Teacher Chaperones</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?Integer.toString(treq.getTotalOtherChaperons()):""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Other Chaperons</td>
                                      <td class="displayText" >
                                        <%=((treq != null)&&!StringUtils.isEmpty(treq.getOtherChaperon()))?treq.getOtherChaperon():""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Has each chaperone been approved by Principal?</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?(treq.isChaperonsApproved()?"YES":"NO"):""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Does this trip involve billeting?</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?(treq.isBilletingInvolved()?"YES":"NO"):""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Is there any school fundraising associated with this trip?</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?(treq.isSchoolFundraising()?"YES":"NO"):""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Emergency Contact</td>
                                      <td class="displayText" >
                                        <%=(treq != null)?treq.getEmergencyContact():""%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td class="displayHeaderTitle"  valign="middle" align="right" width='200px'>Itinerary Document</td>
                                      <td class="displayText" >
                                        <a href="https://www.nlesd.ca/MemberServices/student/travel/itineraries/<%=(treq != null)?treq.getIteneraryFilename():""%>" target="_blank">VIEW</a>
                                      </td>
                                    </tr>
                                    <%if((usr.getPersonnel().getPersonnelID() == approver.getPersonnelID())
                                      && (treq != null) && treq.getStatus().equals(RequestStatus.SUBMITTED)){%>
                                      <tr>
                                        <td colspan="2" class="displayText" style="padding-top:10px;color:#FF0000;font-weight:bold;" >
                                          <%
                                          out.println("<a href='http://www.nlesd.ca/MemberServices/student/travel/travelRequestAdmin.html?u="
                                            + approver.getUserName() + "&p="+PasswordEncryption.encrypt(approver.getPassword())
                                            +"&op=approve&id=" +treq.getRequestId() + "' target='_blank'>APPROVE REQUEST</a>"); 
                                          out.println("&nbsp;|&nbsp;");
                                          out.println("<a href='http://www.nlesd.ca/MemberServices/student/travel/travelRequestAdmin.html?u="
                                            + approver.getUserName() + "&p="+PasswordEncryption.encrypt(approver.getPassword())
                                            +"&op=decline&id=" +treq.getRequestId() + "' target='_blank'>DECLINE REQUEST</a>");  
                                          %>
                                        </td>
                                      </tr>
                                    <%}%>
                                    <!--
                                    <tr>
                                      <td colspan="2">
                                        <input type="submit" value="<%=(treq == null)?"Add":"Update"%>">
                                      </td>
                                    </tr>
                                    -->
                                  </table>
                                  </p>
                                </form>
                                
                                <%if(request.getAttribute("msg") != null){%>
                                  <p>
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
  <img src="images/spacer.gif" width="1" height="4"><BR>
  <jsp:include page="footer.jsp" flush="true" />
</body>
</html>
