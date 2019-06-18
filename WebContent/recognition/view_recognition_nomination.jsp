<%@ page language="java"
         import="com.awsd.security.*,
         				 java.text.*,
         				 java.util.*,
         				 com.esdnl.personnel.recognition.model.bean.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/recognition.tld" prefix="rec" %>

<%
	User usr = (User) session.getAttribute("usr");
	NominationBean n = (NominationBean) request.getAttribute("NOMINATIONBEAN");
%>


<html>
<head>
	<title>ESD Recognition of Excellence Program</title>
 <meta name="description" content="Eastern School District's Recognition of Excellence Program for Students and Employees">
  <meta name="keywords" content="">
<link href="includes/css/awards.css" rel="stylesheet" type="text/css">
<style type="text/css">@import 'includes/css/home.css';</style>
<style>
	fieldset{
		width:500px;
	}
	legend{
		font-weight: bold;
		color="#333333";
		text-decoration:underline;
	}
</style>
</head>

<body class="body">
<!-- Begin Main Table -->
<table width="786" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 4px solid #000000;">
<tr style="background-color: Black;">
	<td colspan="2"><div align="right" class="copyright"><SCRIPT LANGUAGE="JavaScript" src="includes/js/date.js"></script>&nbsp;</div></td>
</tr>
<tr style="background-color: Black;">
	<td colspan="2"><img src="includes/images/header.gif" alt="Employee/Student Recognition Program" width="786" height="113" border="0"></td>
</tr>
<tr><td>
<table width="100%" border="0" cellspacing="2" cellpadding="2" align="center" style="background-color: White; font-size: 11px; font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;">
<tr>
	<td>
		<!--Page Content goes here -->
		<table width="80%" cellpadding="0" cellspacing="0" border="0" align="center">
       <tr>
         <td class="displayPageTitle" >Recognition of Excellence - <%=n.getNominationPeriod().getCategory().getName()%> Nomination</td>
       </tr>
       <tr style="padding-top:8px;">
         <td style="padding-bottom:10px;">
         	<form method="POST" action="addRecognitionNomination.html" ENCTYPE="multipart/form-data">
         		<%if(usr != null){ %>
         			<input type="hidden" name='nominator_id' value="<%=usr.getPersonnel().getPersonnelID()%>" />
         		<%}%>
         		<input type='hidden' name='op' value='ADD'/>
            
              <%if(request.getAttribute("msg")!=null){%>
              	<table width="100%" cellpadding="0" cellspacing="0" border="0">
               	<tr class="messageText" style="padding-top:8px;padding-bottom:8px;">
                 	<td  align="center">
                   	Thank you for submitting your nomination!
                   </td>
                 </tr>
                </table>
              <%}%>
              
              <div width="100%" align="center">
              <fieldset>
	   						<legend>Nomination Information</legend>
	              <table width="100%" cellpadding="0" cellspacing="3" border="0">
	              	<tr>
	                	<td class="displayHeaderTitle" width="75">Nominee</td>
	                 	<td class="displayText" width="*"><%=n.getNomineeFullName()%></td>
	               	</tr>
	               	<tr>
	                 	<td class="displayHeaderTitle">Nominee Location</td>
	                 	<td class="displayText" width="*"><%=n.getNomineeLocation().getSchoolName()%></td>
	               	</tr>
	              	<tr>
	                 	<td class="displayHeaderTitle" width="100">Award Category</td>
	                 	<td class="displayText" width="*"><%=n.getNominationPeriod().getCategory().getName() %></td>
	               	</tr>
	               	<tr>
			             	<td class="displayHeaderTitle">Rationale</td>
			             	<td class="displayText" width="*">
			             		<a href="/MemberServices/recognition/nominations/rationales/<%=n.getRationaleFilename()%>" class="nav" target="_blank">view</a>
			             	</td>
			           	</tr>
			           	<tr>
	                	<td class="displayHeaderTitle" width="75">Nominator</td>
	                 	<td class="displayText" width="*"><%=n.getNominatorFullName()%></td>
	               	</tr>
	               	<tr>
	                 	<td class="displayHeaderTitle">Nominator Location</td>
	                 	<td class="displayText" width="*"><%=n.getNominatorLocation()%></td>
	               	</tr>
	               	<tr>
	                 	<td class="displayHeaderTitle">Nomination Date</td>
	                 	<td class="displayText" width="*"><%=n.getFormattedNominationDate()%></td>
	               	</tr>
	              </table>
              </fieldset>
             	</div>
             
              <p class="displayText">
               	The Selection Committee will consist of one representative or designate of the 
               	programs, human resources, finance and administration and rural education/corporate 
               	services divisions, two SEOs and two programs specialists, and the manager of 
               	communications. 
              </p>
           </form>
         </td>
       </tr>
     </table>
</td></tr></table>
</td></tr>
<tr style="background-color: Black;">
	<td colspan="2"><div align="center" class="copyright">&copy; 2008 Eastern School District</div></td>
</tr>
</table>


</body>
</html>
