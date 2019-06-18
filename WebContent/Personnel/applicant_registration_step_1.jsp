<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                 com.awsd.security.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.util.*"  
         isThreadSafe="false"%>
         
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
$("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold;width:20%;}
.tableResult {font-weight:normal;width:80%;}

.tableTitleL {font-weight:bold;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>

<script type="text/javascript">
	function validateStep1Submit()
	{
		check = validateEmail(document.forms[0].email);
		if(!check){
			
			$('#msgerr').css('display','block').html("Email Address is Invalid. Please re-enter a valid email address.").delay(5000).fadeOut();
			$('#email').focus();
		}
			
		return check;
		
	}
	
	$(function() {
		var pickerOpts={dateFormat:"dd/mm/yy",changeMonth:true,changeYear:true,yearRange: "-75:+0"};
	    $( "#dob" ).datepicker(pickerOpts);

	  });
   

</script>



</head>
<body><div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">1</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
<%if(profile != null){%>
	SECTION 1: Editing your Teacher/Educator HR Application Profile 
<%} else { %>
	SECTION 1: New Applicant HR Registration for Teachers/Educators
<%} %>
</div>

<br/>Please complete/update your profile below. Fields marked * are required. 

						


<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}.</div>
<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>

<form id="ApplicantRegistrationStep1" action="applicantRegistration.html?step=1" method="post" onsubmit="return checknewprofile()">
                                  <%if(profile != null){%>
                                    <input type="hidden" name="sin" value="<%=profile.getSIN()%>" >
                                    <input type="hidden" name="op" id="op" value="edit" >
                                  <%}%>     
                                
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>1a. ACCOUNT INFORMATION</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
      			 	 <div class="alert alert-danger" id="divmsgAI" align="center" style="display:none;"></div>      
      			 	       		<table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
							    <tr>
							    <td class="tableTitleL">Email Address*:</td>
							    <td class="tableResultL" id="emailW"><input type="text" name="email" id="email" class="form-control" value='<%=(profile!=null)?profile.getEmail():""%>' onblur="return validateEmail(this);"></td>
							    <td class="tableTitleR">Confirm Email*:</td>
							    <td class="tableResultR" id="cemailW"><input type="text" name="email_confirm" id="email_confirm" class="form-control" value='<%=(profile!=null)?profile.getEmail():""%>'></td>
							    </tr>
							    <tr>
							    <td class="tableTitleL">Password*:</td>
							    <td class="tableResultL" id="passwordW"><input type="password" name="password" id="password" class="form-control" value='<%=(profile!=null)?profile.getPassword():""%>'></td>
							    <td class="tableTitleR">Confirm Password*:</td>
							    <td class="tableResultR" id="cpasswordW"><input type="password" name="password_confirm" class="form-control" id="password_confirm" value='<%=(profile!=null)?profile.getPassword():""%>'></td>
							    </tr>
							    </tbody>
                                </table>
</div></div></div></div>
                        	
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>1b. PROFILE INFORMATION</b></div>
      			 	<div class="panel-body"> 
      			 	
					<div class="table-responsive"> 
      			 	<div class="alert alert-danger" id="divmsgPI" align="center" style="display:none;"></div>      
      			 	       		<table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
							    <tr>                        
                                <td class="tableTitleL">Surname*:</td>
							    <td class="tableResultL" id="surnameW"><input type="text" name="surname" id="surname" class="form-control" value='<%=(profile!=null)?profile.getSurname():""%>'></td>
							    <td class="tableTitleR">First Name*:</td>
							    <td class="tableResultR" id="firstnameW"><input type="text" name="firstname" id="firstname" class="form-control" value='<%=(profile!=null)?profile.getFirstname() : ""%>'></td>
							    </tr>
							    <tr>                        
                                <td class="tableTitleL">Middle Name:</td>
							    <td class="tableResultL" id="middlenameW"><input type="text" name="middlename" id="middlename" class="form-control" value='<%=((profile!=null)&&(profile.getMiddlename()!=null))?profile.getMiddlename() : ""%>'></td>
							    <td class="tableTitleR">Maiden Name:</td>
							    <td class="tableResultR" id="maidennameW"><input type="text" name="maidenname" id="maidenname" class="form-control" value='<%=((profile!=null)&&(profile.getMaidenname()!=null))?profile.getMaidenname() : ""%>'></td>
							    </tr>
							    <tr>
								<td class="tableTitleL">Address*:</td>
							    <td class="tableResultL" id="address1W"><input type="text" name="address1" id="address1" class="form-control" value="<%=(profile!=null)?profile.getAddress1() : ""%>"></td>
							    <td class="tableTitleR">City/Town*:</td>
							    <td class="tableResultR" id="address2W"><input type="text" name="address2" id="address2" class="form-control" value="<%=((profile!=null)&&(profile.getAddress2()!=null))?profile.getAddress2() : ""%>"></td>
							    </tr>
							    <tr>
								<td class="tableTitleL">Province/State*:</td>
							    <td class="tableResultL" id="state_provinceW"><job:StateProvince id="state_province" cls="form-control" value='<%=(profile!=null)?profile.getProvince():""%>' /></td>
							    <td class="tableTitleR">Country*:</td>
							    <td class="tableResultR" id="countryW"><job:Country id="country" cls="form-control" value='<%=(profile!=null)?profile.getCountry():""%>' /></td>
							    </tr>
							    <tr>
							    <td class="tableTitleL">Postal Code/Zip*:</td>
							    <td class="tableResultL" id="postalcodeW"><input type="text" name="postalcode" id="postalcode" class="form-control" value='<%=(profile!=null)?profile.getPostalcode() : ""%>'></td>
							    <td class="tableTitleR"></td>
							    <td class="tableResultR"></td>
							    </tr>
							    <tr>
							    <td class="tableTitleL">Telephone*:</td>
							    <td class="tableResultL" id="homephoneW"><input type="text" name="homephone" id="homephone" class="form-control" value='<%=(profile!=null)?profile.getHomephone():""%>'></td>
							    <td class="tableTitleR">Work Phone:</td>
							    <td class="tableResultR"><input type="text" name="workphone" id="workphone" class="form-control" value='<%=((profile!=null)&&(profile.getWorkphone()!=null))?profile.getWorkphone() : ""%>'></td>
							    </tr>
							    <tr>
							    <td class="tableTitleL">Cell Phone:</td>
							    <td class="tableResultL"><input type="text" name="cellphone" id="cellphone" class="form-control" value='<%=((profile!=null)&&(profile.getCellphone()!=null))?profile.getCellphone() : ""%>'></td>
							    <td class="tableTitleR">DOB:</td>
							    <td class="tableResultR"><input type="text" name="dob" id="dob" class="form-control" value='<%=((profile!=null)&&(profile.getDOB()!=null))?profile.getDOBFormatted() : ""%>'></td>
							    </tr>                                
                             </tbody>
                             </table>
   </div></div></div></div>                          
                             
            			<div class="alert alert-danger" id="divmsg" align="center" style="display:none;"></div> 
            			
 							
 							
 							<div align="center"  class="no-print">
 							
 							<%if(profile != null){%>
 							<a class="btn btn-xs btn-danger" href="view_applicant.jsp">Back to Profile</a>
 							<%} else { %>
 							<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>  
 							<%}%>
 							<input class="btn btn-xs btn-success" type="submit" value="Save/Update" /></div>



                                 
                                  
                                </form>
						<%if(request.getAttribute("msg")!=null){%>
							<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>
						<%if(request.getAttribute("errmsg")!=null){%>
							<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>                   
</body>
</html>
