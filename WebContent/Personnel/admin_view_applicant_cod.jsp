<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.bean.*,
                 com.awsd.school.dao.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-DOCUMENTS-VIEW-ALL" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<title>Newfoundland &amp; Labrador English School District - Criminal Offence Declaration</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import 'includes/home.css';</style>
		<link href="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/smoothness/jquery-ui.css"
        type="text/css" rel="Stylesheet" />
		<style>
			body{
				margin: auto;
				margin-top:10px;
				width:650px;
				border: solid 1px #c4c4c4;
				padding:5px;
			}
			
			.main{
				margin:auto;
			}
			
			.header{
				padding-bottom: 10px;
			}
			
			
			
			.footer {
				border-top:solid 1px #333333;
			 	text-align: center;
			 	font-weight: bold;
			 	font-size: 9pt;
			}
			
			.position-details {
				border-collapse: collapse;
				border: solid 1px #333333;
			}	
			
			.position-details caption {
				border: solid 1px #333333;
				border-bottom: none;
				font-weight: bold;
			}
			
			.position-details td {
				border: solid 1px #333333;
			}
			
			.position-details .label {
				font-weight:bold;
			}
			
			@media print {
				body{
					margin-top:0px;
					margin: auto;
					border: none;
				}
				.content{
					font-family:verdana,sans-serif;
  				font-size:12px;
					min-height: 675px;
				}
			}
			/*
			div.document-box {
				background-color: #e4e4e4; 
				padding:3px 3px 3px 3px;
			}
			
			div.document-box table.document-box-table {
				background-color:white;
				border: solid 1px #c4c4c4;
			}
			
			div.document-box table.document-box-table caption {
				background-color: #d4d4d4; 
				border: solid 1px #c4c4c4;
				border-bottom: none;
				padding: 5px 5px 5px 5px;
				text-align: left;
				font-family:Arial, Helvetica, sans-serif;
				font-size: 11px;
				font-weight: bold;
			}
			
			.small-button {
			   font-size: .7em !important;
			   font-weight:bold;
			   color: red;
			}
			
			.extra-small-button {
			   font-size: .9em !important;
			   font-weight: bold;
			   color: red;
			}
			*/
			ol{margin-top:0;margin-bottom:0;}
			li {padding:2px;}
			
			
		</style>
		
	</head>
	
	<body>
		<div class='main'>
			<div class='header'>
				<img src="images/empltr_header.png" border="0" width='650' />
			</div>
			
			<div class='content'>
				<table width="100%" cellpadding="2" cellspacing="0" border="0" style="padding:10px;display:inline;border-collapse:collapse;">
            
              <tr>
            			<td width="100%" style='padding-top: 15px;padding-bottom: 0px;'>
            				<div width="100%" class="document-box">
             				<table class="document-box-table" width="100%" cellspacing="3" cellpadding="3">
             					<tr>
                  	<td width='50%' class='displayText' style='border: solid 1px #c4c4c4;'>
                  		<span class='displayHeaderTitle'>NAME:</span>&nbsp;&nbsp;${cod.applicant.fullName}
                  	</td>
                  	<td width='*' class='displayText' style='border: solid 1px #c4c4c4;'>
                  		<span class='displayHeaderTitle'>DATE OF BIRTH:</span>&nbsp;&nbsp;${cod.applicant.DOB ne null ? cod.applicant.DOBFormatted : "NOT RECORDED"}
                  	</td>
                  </tr>
                  
                  <tr>
                  	<td class='displayText' style='border: solid 1px #c4c4c4;'>
                  		<span class='displayHeaderTitle'>POSITION:</span>&nbsp;&nbsp;${cod.position}
                  	</td>
                  	<td class='displayText' style='border: solid 1px #c4c4c4;'>
                  		<span class='displayHeaderTitle'>LOCATION/SCHOOL:</span>&nbsp;&nbsp;${cod.location}
                  	</td>
                  </tr>
                  
                  <tr>
                  	<td class='displayText' style='border: solid 1px #c4c4c4;' colspan='2'>
                  		<span class='displayHeaderTitle'>Declaration Date:</span>&nbsp;&nbsp;<fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${cod.declarationDate}" />
                  	</td>
                  </tr>
             				</table>
             			</div>
             		</td>
             	</tr>
              <tr>
           			<td width="100%" style='padding-top: 0px;padding-bottom: 15px;'>
           				<div width="100%" class="document-box">
            					<table class="document-box-table" width="100%" cellspacing="3" cellpadding="3">
				              <tr>
				            			<td width="100%" style='padding-top:10px;padding-bottom:15px;font-size:11pt;' class='displayText'>
				            				<ol>
				            					<li>
				            						<b>I DECLARE</b>, since the last Criminal Reference Check (CRC) provided to Newfoundland and Labrador English School District, 
				            							or since the last Criminal Offence Declaration (COD) given by me to the district, that:
				            							<ol style='padding-top:5px;list-style-type: lower-alpha;'>
				            								<li>
				            									I have <b>no</b> convictions under the <i>Criminal Code of Canada</i> up and including the date of this
				            									declaration for which a pardon has not been issued or granted under the <i>Criminal Records Act (Canada)</i>.
				            								</li>
				            								<li>
				            									I have <b>no</b> charges pending under the <i>Criminal Code of Canada</i> up to and including the date
				            									of this declaration.
				            								</li>
				            							</ol>
				            					</li>
				            				</ol>
				            			</td>
				            		</tr>
				            		
				            		<tr>
				            			<td style='padding-left:15px;padding-bottom:20px;font-size:12pt;' class='displayText'><b>OR</b></td>
				            		</tr>
				            		
				            		<tr>
				            			<td width="100%" style='padding-bottom: 15px;font-size:11pt;' class='displayText'>
				            				<ol start='2'>
				            					<li>
				            						I have the following convictions/charges for offences under the <i>Criminal Code of Canada</i>
				            						for which a pardon under the <i>Criminal Records Act (Canada)</i> has <b>not</b> been
				            						issued or granted.<br>
				            						<b>List of Offences:</b>
				            						<c:choose>
				            							<c:when test="${fn:length(cod.offences) gt 0}">				
				              						<ol id='ol-offences'>
				              							<c:forEach items="${cod.offences}" var='codo'>
				               							<li>
				               								<ol style='list-style-type: lower-alpha;'>
				               									<li><b>Date:</b>&nbsp;&nbsp;<fmt:formatDate type="both" dateStyle="medium" value="${codo.offenceDate}" /></li>
				               									<li><b>Court Location:</b>&nbsp;&nbsp;${codo.courtLocation}</li>
				               									<li><b>Conviction/Charge:</b>&nbsp;&nbsp;${codo.conviction}</li>
				               								</ol>
				               							</li>
				              							</c:forEach>
				              						</ol>
				            							</c:when>
				            							<c:otherwise>
				            								<br /><span style='color:red;padding:0px;'>No offences listed.</span>
				            							</c:otherwise>
				            						</c:choose>
				            					</li>
				            				</ol>
				            			</td>
				            		</tr>
           						</table>
           					</div>
           				</td>
            		</tr>
            </table>
			</div>
			
			<div class='footer'>
				Suite 601, Atlantic Place,  215 Water St., Box 64-66, St. John's, NL  A1C 6C9<br />
				Telephone:  (709)758-2372   Facsimile:  (709)758-2706   Web Site:  www.nlesd.ca<br />
				Follow @ESDNLCA
			</div>
		</div>
	</body>
</html>
