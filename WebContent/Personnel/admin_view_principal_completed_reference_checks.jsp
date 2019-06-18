<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*, 
                 com.esdnl.personnel.jobs.constants.*" %>
<%@ taglib uri='http://java.sun.com/jstl/core_rt' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<style type="text/css">@import 'includes/home.css';</style>
	<script language="JavaScript" src="js/CalendarPopup.js"></script> 
	<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
	<script type='text/javascript'>
		$('document').ready(function(){
			$('tr.datalist:odd').css({'background-color':'#e4e4e4'});
		});
	</script>
</head>
<body>

<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table width="760" align="center" cellpadding="0" cellspacing="0" border="0" style="border-top: solid 1px #FFB700;border-bottom: solid 1px #FFB700;">
    <tr>
      <td>   
        <jsp:include page="admin_top_nav.jsp" flush="true"/>
        <table width="760" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="760" align="left" valign="top">
              <table width="760" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <jsp:include page="admin_menu.jsp" flush="true"/>
                  <td width="551" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="391" align="left" valign="top" style="padding-top:8px;">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayPageTitle">Completed Reference Checks</td>
                            </tr>
                            <tr style="padding-top:8px;">
                              <td>
                                <table width='100%' cellpadding='0' cellspacing='0'>
                                <tr>
	                              	<td class='displayHeaderTitle'>Reference Date</td>
	                              	<td class='displayHeaderTitle'>Applicant</td>
	                                <td class='displayHeaderTitle'>&nbsp;</td>
	                              </tr>
	                                  
	                              <c:choose>
	                              	<c:when test="${fn:length(refs) gt 0}">
	                              		<c:forEach items='${refs}' var='ref'>
	                              			<tr class='datalist'>
		                                    <td class='displayText'>
		                                    	${ref.providedDateFormatted}
		                                    </td>
		                                    <td class='displayText'>
		                                    	${ref.applicant.fullName}
		                                    </td>
		                                    <td class='displayText'>
		                                    	<a target='_blank' 
		                                    		 style='color:#FF0000;font-weight:bold;text-decoration:none;' 
		                                    		 href='viewApplicantReference.html?id=${ref.id}'>view</a> |
		                                    	<a target='_blank' 
		                                    		 style='color:#FF0000;font-weight:bold;text-decoration:none;' 
		                                    		 href='editApplicantReference.html?id=${ref.id}'>edit</a>
		                                    </td>
	                                    </tr>
	                              		</c:forEach>
	                              	</c:when>
	                              	<c:otherwise>
	                              		<tr>
	                              			<td style='border: solid 1px #e4e4e4;' colspan='3' class='displayText'>
	                              				No references added with last 6 months.
	                              			</td>
	                              		</tr>
	                              	</c:otherwise>
	                              </c:choose>
                                </table>
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
</body>
</html>
