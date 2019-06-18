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
    	$(function(){
    		
    		$('#frm-reg-caps').submit(function(){
    			$('#btnSaveCaps').attr("disabled", "disabled");	
    			$('#btnSaveCaps').val("Saving...");	
    			
    			return true;
    		});
    		
    		$('.opbutton').button();
    		
    		$('tr.period-data-row:odd').css({'background-color':'#f0f0f0'})
    		
    		$('input[type=text], select').css({'border' : 'solid 1px #7B9EBD', 'background-color' : '#FFFFCC'});
    	});
    	
    </script>
  </head>

  <body bgcolor="#BF6200">
		<div align='center' style='font-size:14pt;font-weight:bold;color:#33cc33;padding-bottom:15px;'>
	  	School Kindergarten Registrant Cap Summary
  	</div>
		<div>
			<form id='frm-reg-caps' method='post' action="<c:url value='/schools/registration/kindergarten/admin/district/updateSchoolRegistrationCaps.html'/>">
				<input type='hidden' name='school_year' value='${sy}' />
				<table id='data-table' width="100%" border="0" cellspacing="0" cellpadding="5" style='border-bottom: solid 2px grey;'>
					<caption>${sy}</caption>
					<tr>
						<th>School</th>
						<th>Total</th>
						<th>English<br />Registered</th>
						<th>English<br />Accepted</th>
						<th>English<br />Waitlisted</th>
						<th>French<br />Registered</th>
						<th>French<br />Accepted</th>
						<th>French<br />Waitlisted</th>
					</tr>
					<c:choose>
						<c:when test="${fn:length(caps) gt 0 }">
							<c:forEach items="${caps}" var="c">
								<tr class='period-data-row'>
									<td>
										<input type="hidden" name="school_id" value='${c.school.schoolID}' />
										${c.school.schoolName}
									</td>
									<td>${c.summary.totalRegistrants}</td>
									<td>${c.summary.englishRegistrants}</td>
									<td>
										<input type="text" name="english_cap_${c.school.schoolID}" value="${c.englishCap}" style='width:50px;' class='required' />
									</td>
									<td>${((c.summary.englishRegistrants - c.englishCap) < 0) ? 0 : (c.summary.englishRegistrants - c.englishCap) }</td>
									<td>${c.summary.frenchRegistrants}</td>
									<td>
										<input type="text" name="french_cap_${c.school.schoolID}" value="${c.frenchCap}" style='width:50px;' />
									</td>
									<td>${((c.summary.frenchRegistrants - c.frenchCap) < 0) ? 0 : (c.summary.frenchRegistrants - c.frenchCap)}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan='4'>No cap data found for this school year.</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td style='font-weight: bold; border-top:solid 2px #000000;'>Totals</td>
						<td style='font-weight: bold; border-top:solid 2px #000000;'>${totalreg}</td>
						<td style='font-weight: bold; border-top:solid 2px #000000;'>${totaleng}</td>
						<td style='font-weight: bold; border-top:solid 2px #000000;'>&nbsp;</td>
						<td style='font-weight: bold; border-top:solid 2px #000000;'>${englishw}</td>
						<td style='font-weight: bold; border-top:solid 2px #000000;'>${totalfr}</td>
						<td style='font-weight: bold; border-top:solid 2px #000000;'>&nbsp;</td>
						<td style='font-weight: bold; border-top:solid 2px #000000;'>${frenchw}</td>
					</tr>
				</table>
				<br />
				<div align='center' class='noprint'>
					<input id='btnSaveCaps' class='opbutton' type="submit" value='Save Registrant Caps' />
					<a class='opbutton' href="<c:url value='/schools/registration/kindergarten/admin/district/index.html' />">Back to Registration Period List</a>
				</div>
				<br />
			</form>
		</div>

	</body>
	
</html>