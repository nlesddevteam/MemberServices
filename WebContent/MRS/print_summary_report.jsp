<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,com.awsd.school.*,
                 com.esdnl.mrs.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>  

<%
  MaintenanceRequest[] reqs = null;
  MaintenanceRequest req = null;
  School school = null;
  SimpleDateFormat sdf = null;
  
  ArrayList pages = null; 
  
  pages = (ArrayList) session.getAttribute("OUTSTANDING_REQUESTS");

  sdf = new SimpleDateFormat("dd/MMM/yyyy");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Eastern School District Maintenance/Repair - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "css/maint.css";</style>
    <script language="JavaScript" src="js/common.js"></script>
    <script language="JavaScript" src="../js/common.js"></script>
    <STYLE TYPE="text/css">
      br.pagebreak {page-break-before: always}
    </STYLE> 
	</head>
	<body style="margin:0px;" onload="window.print();self.close();">
	
		<esd:SecurityCheck permissions="MAINTENANCE-WORKORDERS-VIEW,MAINTENANCE-ADMIN-VIEW,MAINTENANCE-SCHOOL-VIEW" />
		
		
      <table width="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
      	<tr>
          <td id="maincontent" width="100%" valign="top">
          	<table width="100%" cellpadding="0" cellspacing="0" align="left" valign="top">
        			
			        <% for(int i=0; i < pages.size(); i++){
			            reqs = (MaintenanceRequest[])pages.get(i);
			            for(int j=0; j < reqs.length; j++){
			              req = reqs[j];
			              
              			if(!reqs[j].getSchool().equals(school)){
              				
                      if(school != null){
                        out.println("<tr><td colspan='4' style='border-top:1px solid #072B61;' align='left'><img src='images/spacer.gif' width='1' height='5'></td></tr>");
                      	out.println("<tr><td width='100%'><br class='pagebreak'></td></tr>");
                      }
                      out.println("<tr><td colspan='4' align='center' valign='top' style='padding-bottom:5px;'><img src='images/outstanding_school_requests_title.gif'><br></td></tr>");
                      school = reqs[j].getSchool();
                      out.println("<tr><td colspan='4' style='padding-bottom:10px;' class='school_name' align='left'>"+school.getSchoolName()+"</td></tr>");
                      
                      out.println("<tr>");
                      out.println("<td class='label' width='30%' align='left'>ROOM/LOCATION</td>");
                      out.println("<td class='label' width='40%' align='left'>PROBLEM DESCRIPTION</td>");
                      out.println("<td class='label' width='20%' align='center'>DATE<br>REQUESTED</td>");
                      out.println("<td class='label' width='10%' align='center'>STATUS</td>");
                      out.println("</tr>");
                    }
              %>
                    <tr>
                    	<td class="content" width="50%" align="left" valign="top" style='padding-top: 5px;border-bottom: solid 1px #e0e0e0;'>
                    		<%=reqs[j].getRoomNameNumber()%>
                    	</td>
                      <td class="content" width="50%" align="left" style='padding-top: 5px;border-bottom: solid 1px #e0e0e0;'>
                      	<%=reqs[j].geProblemDescription()%>
                      </td>
                      <td class="content" width="20%" align="center" valign="top" style='padding-top: 5px;border-bottom: solid 1px #e0e0e0;'>
                      	<%=sdf.format(reqs[j].getRequestedDate())%>
                      </td>
                      <td class="content" width="20%" align="center" valign="top" style='padding-top: 5px;border-bottom: solid 1px #e0e0e0;'>
                      	<%=reqs[j].getCurentStatus().getStatusCodeID()%>
                      </td>
                    </tr>
                  <%}%>
              <%}%>
              <tr><td colspan='4' style='border-top:1px solid #072B61;' align='left'><img src='images/spacer.gif' width='1' height='5'></td></tr>
            </table>
          </td>
        </tr>
      </table> 
	</body>
</html>