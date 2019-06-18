<%@ page language="java"
         import="com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
<esd:SecurityCheck permissions="KINDERGARTEN-REGISTRATION-ADMIN-VIEW" />

<html>
  
  <head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <TITLE>Administration</title>
    <style type="text/css">
    	caption{
				font-weight: bold;
				text-align: left;
				color: #BF00BD;
			}
			th{
				vertical-align:bottom;
			}
			
			@media print {
				
				#data-table { width:600px; }
				
				tr.period-data-row td { border-bottom: solid 1px #000000; }
			}
    </style>
    
    <script type="text/javascript">
    	jQuery(function(){
    		
    		$('.opbutton').button();
    		
    		$('tr.period-data-row:odd').css({'background-color':'#f0f0f0'})
    		
    	});
    	
    </script>
  </head>

  <body bgcolor="#BF6200">
		<div align='center' style='font-size:14pt;font-weight:bold;color:#33cc33;padding-bottom:15px;'>
	  	Kindergarten Registrants Summary By School
  	</div>
		<div>
			<table id='data-table' width="100%" border="0" cellspacing="0" cellpadding="5" style='border-bottom: solid 2px grey;'>
				<caption>${sy}</caption>
				<tr>
					<th>School</th>
					<th>Total</th>
					<th>English</th>
					<th>French</th>
				</tr>
				<c:choose>
					<c:when test="${fn:length(summaries) gt 0 }">
						<c:forEach items="${summaries}" var="s">
							<tr class='period-data-row'>
								<td>${s.school.schoolName}</td>
								<td>${s.totalRegistrants}</td>
								<td>${s.englishRegistrants}</td>
								<td>${s.frenchRegistrants}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan='4'>No summary data found for this period.</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td style='font-weight: bold; border-top:solid 2px #000000;'>Totals</td>
					<td style='font-weight: bold; border-top:solid 2px #000000;'>${totalreg}</td>
					<td style='font-weight: bold; border-top:solid 2px #000000;'>${totaleng}</td>
					<td style='font-weight: bold; border-top:solid 2px #000000;'>${totalfr}</td>
				</tr>
			</table>
		</div>
		<br />
		<div align='center' class='noprint'>
			<a class='opbutton' href="<c:url value='/schools/registration/kindergarten/admin/district/index.html' />">Back to Registration Period List</a>
		</div>
		<br />
	</body>
	
</html>