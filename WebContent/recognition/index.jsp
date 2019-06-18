<%@ page language="java"
         import="com.awsd.security.*,
         				 java.text.*,
         				 java.util.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/recognition.tld" prefix="rec" %>

<%
	User usr = (User) session.getAttribute("usr");
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
	<td colspan="2"><img src="includes/images/header.gif" alt="Employee/Student Recognition Program" width="786" border="0"></td>
</tr>
<tr><td>
<table width="100%" border="0" cellspacing="2" cellpadding="2" align="center" style="font-size: 11px; font-family: Verdana, Geneva, Arial, Helvetica, sans-serif; background: White url(includes/images/tblbakg.gif);">
<tr>
	<td>
		<!--Page Content goes here -->
		<table width="80%" cellpadding="0" cellspacing="0" border="0" align="center">
       <tr>
         <td class="displayPageTitle" >Recognition of Excellence - Nomination Form</td>
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
                   	<%=(String)request.getAttribute("msg")%>
                   </td>
                 </tr>
                </table>
              <%}%>
              
              <div width="100%" align="center">
              <fieldset>
	   						<legend>NOMINEE</legend>
	              <table width="100%" cellpadding="0" cellspacing="3" border="0">
	               <tr>
	                 <td class="displayHeaderTitle" width="75">First Name</td>
	                 <td width="*"><input type="text" name="nominee_first_name" id="nominee_first_name" style="width:200px;" class="requiredInputBox"></td>
	               </tr>
	               <tr>
	                 <td class="displayHeaderTitle">Surname</td>
	                 <td><input type="text" name="nominee_last_name" id="nominee_last_name" style="width:200px;" class="requiredInputBox"></td>
	               </tr>
	               <tr>
	                 <td class="displayHeaderTitle">Location</td>
	                 <td><rec:Schools id="nominee_location_id" style="width:200px;" cls="requiredInputBox" /></td>
	              </tr>
	              </table>
              </fieldset>
              
              <br/>
              
              <fieldset>
	   						<legend>AWARD</legend>
	              <table width="100%" cellpadding="0" cellspacing="3" border="0">
	               <tr>
	               	<td class="displayText" colspan="2" style="display:block;padding-top:5px;padding-bottom:5px">
	                	Please attach a <b><i>maximum of two pages</i></b> describing why the nominee should be considered for 
	                	a Recognition of Excellence Award.  <!-- Be sure to sign your nomination statement and please keep the 
	                	nomination confidential. -->
	               	</td>
	               </tr>
	               <tr>
	                 <td class="displayHeaderTitle" width="100">Award Category</td>
	                 <td><rec:RecognitionCategories id="period_id" style="width:200px;" cls="requiredInputBox" /></td>
	               </tr>
	               <tr>
			             <td class="displayHeaderTitle">Statement of Support<br><span style="font-size:9px;">(*.pdf, *.doc, *.txt)</span></td>
			             <td><input type="file" name="rationale_file" style="width:275px;" class="requiredInputBox" /></td>
			           </tr>
			          </table>
	          	</fieldset>
          
		          <br/>
		          
		          <fieldset>
	   						<legend>NOMINATOR</legend>
	          		<table width="100%" cellpadding="0" cellspacing="3" border="0">
	               <%if(usr != null){%>
	                <tr>
	                  <td class="displayHeaderTitle" width="75">Name</td>
	                  <td class="displayText"><%=usr.getPersonnel().getFullNameReverse()%></td>
	                </tr>
	               	<%if(usr.getPersonnel().getSchool() != null){%>
	                 <tr>
	                   <td class="displayHeaderTitle" width="75">School</td>
	                   <td class="displayText"><%=usr.getPersonnel().getSchool().getSchoolName()%></td>
	                 </tr>
	               <%}%>
		             <%}else{%>
		              <tr>
		                  <td class="displayHeaderTitle" width="75">First Name</td>
		                  <td><input type="text" name="nominator_first_name" id="nominator_first_name" style="width:200px;" class="requiredInputBox"></td>
		                </tr>
		                <tr>
		                  <td class="displayHeaderTitle" width="75">Surname</td>
		                  <td><input type="text" name="nominator_last_name" id="nominator_last_name" style="width:200px;" class="requiredInputBox"></td>
		                </tr>
		                <tr>
		                  <td class="displayHeaderTitle" width="75">Address</td>
		                  <td><input type="text" name="nominator_location" id="nominator_location" style="width:200px;" class="requiredInputBox"></td>
		                </tr>
		             <%}%>
		             <tr>
		             	<td class="displayHeaderTitle" width="75">Date</td>
		              <td class="displayText"><%=new SimpleDateFormat("MMMM dd, yyyy hh:mm a").format(Calendar.getInstance().getTime())%></td>
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
              
              <p class="displayText" align="center" style="font-weight:bold;">
              	Nominations are due by Friday, March 19, 2010.
              </p>
              
              <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <tr>
              	<td  align="center" style="padding-bottom:8px;">
              		<input type="submit" value="Nominate" />
              	</td>
              </tr>
            	</table>
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
