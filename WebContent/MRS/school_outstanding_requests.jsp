<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.esdnl.mrs.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>  

<%
  User usr = null;
  MaintenanceRequest[] reqs = null;
  
  DecimalFormat df = null;
  SimpleDateFormat sdf = null;
  int num_reqs = 0;
  String color_on;
  String color_off;

  usr = (User) session.getAttribute("usr");
  
  if(session.getAttribute("OUTSTANDING_REQUESTS") != null){
		reqs = ((MaintenanceRequest[])((ArrayList)session.getAttribute("OUTSTANDING_REQUESTS")).get(0));
	  num_reqs = reqs.length;
  }
    
  df = new DecimalFormat("JOB-#000000");
  sdf = new SimpleDateFormat("MM/dd/yyyy");
  color_off = "#FFFFFF";
  color_on = "#D8E7FC";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Maintenance/Repair - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/maint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
    <script language="JavaScript" src="../js/common.js"></script>
	</head>
	<!-- loaded(); if(top != self){resizeIFrame('maincontent_frame', 0);} -->
	<body style="margin:0px;" onload="loaded(); if(top != self){resizeIFrame('maincontent_frame', 0);}">
	
		<esd:SecurityCheck permissions="MAINTENANCE-SCHOOL-VIEW" />
	
		<div id='preloaded' style='display:inline;'>
			<table width="100%" height="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
	        <tr><td align="center" style="padding-bottom:5px;"><img src="images/outstanding_school_requests_title.gif"><br></td></tr>
	        <tr><td align="center" style="padding-bottom:5px;color:#E76B10;font-weight:bold;" >LOADING REQUESTS...</td></tr>
	    </table>
		</div>
		<div id='loaded' style='display:none;'>
    <form id="add_request_form" name="add_request_form" action="addRequest.html" method="post">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <%if(session.getAttribute("OUTSTANDING_REQUESTS") != null){%>
	      <table width="100%" height="100%" cellpadding="0" cellspacing="0" align="center" valign="top">
	        <tr><td align="center" style="padding-bottom:5px;"><img src="images/outstanding_school_requests_title.gif"><br></td></tr>
	        <tr>
	          <td id="maincontent" align="center" width="100%">
	            <table width="95%" cellpadding="0" cellspacing="3" align="center" valign="top">
	              <tr><td align="right" style="padding-bottom:5px;" colspan="3"><a href="" onclick="openWindow('PRINT_REPORT', 'print_report.jsp',600, 300, 0); return false;" class="small_blue"><img src="images/printer.gif" style="cursor:hand;" border="0"></a></td></tr>
	              <tr>
	                <td class="label" width="50%" align="left">PROBLEM DESCRIPTION</td>
	                <td class="label" width="15%" align="center">STATUS</td>
	                <td class="label" width="20%" align="center">PRIORITY</td>
	                <td width="20%" align="center">&nbsp;</td>
	              </tr>
	              <%if(num_reqs < 1){%>
	                <tr>
	                  <td class="label_no_underline" colspan="4" align="left">NO REQUESTS CURRENTLY OUTSTANDING.</td>
	                </tr>
	              <%}else{
	                  for(int i=0; i < reqs.length; i++){%>
	                    <tr id="item_row_<%=reqs[i].getRequestID()%>" style="cursor:hand;background-color:<%=color_off%>;"
	                        onmouseover="toggleTableRowHighlight('item_row_<%=reqs[i].getRequestID()%>', '<%=color_on%>');"
	                        onmouseout="toggleTableRowHighlight('item_row_<%=reqs[i].getRequestID()%>', '<%=color_off%>');"
	                        >
	                      <td class="content applyborder" width="50%" align="left" onclick="document.location.href='viewSchoolRequestDetails.html?req=<%=reqs[i].getRequestID()%>';">
	                        <table width="100%" cellpadding="0" cellspacing="1" align="center" valign="top">
	                          <tr>
	                            <td width="22" valign="top">
	                              <img src="images/view_details_01.gif"
	                                onmouseover="src='images/view_details_02.gif';"
	                                onmouseout="src='images/view_details_01.gif';"><br>
	                            </td>
	                            <td width="*"><%=reqs[i].geProblemDescription()%></td>
	                          </tr>
	                        </table>
	                      </td>
	                      <td class="content applyborder" width="15%" align="center" onclick="document.location.href='viewSchoolRequestDetails.html?req=<%=reqs[i].getRequestID()%>';"><%=reqs[i].getCurentStatus().getStatusCodeID()%></td>
	                      <td class="content applyborder" width="20%" align="center" onclick="">
                          <select id="req_<%=reqs[i].getRequestID()%>" onchange="document.location.href='changeSchoolPriority.html?req=<%=reqs[i].getRequestID()%>&priority='+this.value;">
                            <%for(int p=1; p <= num_reqs; p++){%>
                                <option value="<%=p%>" <%=(reqs[i].getSchoolPriority() == p)?"SELECTED":""%>><%=p%></option>
                             <%}%>
                          </select>
	                      </td>
	                      <td class="content applyborder" width='*'>
	                      	<%if(!reqs[i].getCurentStatus().getStatusCodeID().equals("IN PROGRESS")
                            && !reqs[i].getCurentStatus().getStatusCodeID().equals("COMPLETED")
                            && !reqs[i].getCurentStatus().getStatusCodeID().equals("PARTS ORDERED")){%>
                             <div style='padding-bottom:3px;'><span class="small">&gt;&nbsp;</span><a class="small" onclick="return confirm('Are you sure you want to CANCEL this request?');" href="cancelRequest.html?req=<%=reqs[i].getRequestID()%>">cancel</a></div>
                          <%}%>
                          <%if(!reqs[i].getCurentStatus().getStatusCodeID().equals("COMPLETED")){ %>
                          	<div><span class="small">&gt;&nbsp;</span><a class="small" onclick="return confirm('Are you sure you want to mark this request as COMPLETE?');" href="cancelRequest.html?req=<%=reqs[i].getRequestID()%>&complete=1">complete</a></div>
                          <%}%>
	                      </td>
	                    </tr>
	                  <%}%>
	              <%}%>
	            </table>
	          </td>
	        </tr>
	      </table> 
      <%}else{%>
      		<div align="center">
      			<p style="padding-top:50px;font-weight:bold; color:#FF0000;">
      				Please select your school at the left. Thank you.
      			</p>
      		</div>
      <%}%>
    </form>
    </div>
	</body>
</html>