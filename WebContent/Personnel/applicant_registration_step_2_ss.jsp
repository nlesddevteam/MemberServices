<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.security.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.util.*" 
         isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%
  	ApplicantProfileBean profile = null;
	ApplicantNLESDExperienceSSBean esd_exp = null;
  	profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
  	if(profile != null){
   	 esd_exp = ApplicantNLESDExperienceSSManager.getApplicantNLESDExperienceSSBeanBySin(profile.getSIN());
 	}
  
 // Calendar now = Calendar.getInstance();
  //boolean canUpdate  = ((now.get(Calendar.MONTH) < Calendar.JULY || now.get(Calendar.MONTH) >= Calendar.SEPTEMBER)) 
  	//|| (usr != null && (usr.checkRole("ADMINISTRATOR") || usr.checkRole("SEO - PERSONNEL") || usr.checkRole("AD HR")));
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script src="js/applicant_validations.js"></script>
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

				<script>
			$('document').ready(function(){
				$('#frmPostJob').change(function(){
					var selected_value = $("input[name='employed']:checked").val();
					if(selected_value=="Y"){
						$("#divnlesd").show();
					}else{
						$("#divnlesd").hide();
					}
		        });
				var pickerOpts={dateFormat:"mm/dd/yy",changeMonth:true,changeYear:true,yearRange: "-75:+0"};
			    $( "#sdate" ).datepicker(pickerOpts);
			    $( "#edate" ).datepicker(pickerOpts);
			    //now we check to see if this is an edit
			    if($( "#hsstatus" ).length){
			    	$("#sstatus").val($( "#hsstatus" ).val());
			    }
			    var selected_value = $("input[name='employed']:checked").val();
				if(selected_value=="Y"){
					$("#divnlesd").show();
				}else{
					$("#divnlesd").hide();
				}
			});	
		</script>
</head>
<body>
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">2</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
<%if(profile != null){%>
	SECTION 2: Editing your Support Staff/Management HR Application Profile 
<%} else { %>
	SECTION 2a: New Applicant HR Registration for Support Staff/Management
<%} %>
</div>

<br/>Please complete/update your profile below. Fields marked * are required. 

<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>						


<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>2. NLESD EXPERIENCE</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 



<form id="frmPostJob" action="applicantRegistrationSS.html?step=2" method="post" onsubmit="return checknlesdexp()">

			         				<%if(esd_exp != null){%>
                                    	<input type="hidden" name="op" id="op" value="edit" >
                                    	<input type="hidden" name="id" id="id" value="<%= esd_exp.getId()%>">
                                  <%}%> 
			<div class="alert alert-danger" align="center" id="divmsg" style="display:none;"><span id="spanmsg" name="spanmsg"></span></div>
				<div class="form-group">
  					<label for="perm_contract">Are you currently employed with the Newfoundland and Labrador English School District?</label> &nbsp; &nbsp;
	  					<%if(esd_exp == null){ %>
					  		<label class="radio-inline"><input type='radio' name='employed' id='employed' value='Y'> YES </label>
					  		<label class="radio-inline"><input type='radio' name='employed' id='employed' value='N' checked> NO</label>
				  		<%}else{ %>
					  		<label class="radio-inline"><input type='radio' name='employed' id='employed' value='Y' <%= esd_exp.getCurrentlyEmployed().equals("Y")?"checked":""%>> YES</label>
					  		<label class="radio-inline"><input type='radio' name='employed' id='employed' value='N' <%= esd_exp.getCurrentlyEmployed().equals("N")?"checked":""%>> NO</label>
				  		<%}%>					
				</div>
	
			  
			                  
 <div id="divnlesd" style="display:none;">
 				<div style="color:Green;font-size:14px;font-weight:bold;">Current Position(s):</div>
				To add a position, simply fill out the below form and click Add Position to include in your profile.
				<br/><br/>         						
         		<job:ApplicantCurrentPositionsSS showdelete="edit"/>
         		<hr>	
         		<tr>
         					
         		
         						<table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
							    <tr>
 								<td class="tableTitleL">Work Location*:</td>
 								<td class="tableResultL"><job:JobLocation id='perm_school' cls='form-control' /></td>
 								<td class="tableTitleL">Position Union*:</td>
 								<td class="tableResultL">
 									<c:set var='unioncodes' value='<%=RequestToHireManager.getUnionCodes()%>'/>
			                    	<SELECT name="union_code" id="union_code" class="form-control" style="width:250px;" onchange="getPositionsApp()">
										<OPTION VALUE='-1'>--- SELECT UNION---</OPTION>
										<%
											TreeMap<String,Integer> ucodes = RequestToHireManager.getUnionCodes();
											for(Map.Entry<String, Integer> entry : ucodes.entrySet()) {
											  String key = entry.getKey();
											  Integer value = entry.getValue();

											 out.println("<option value='" + value + "'>" + key + "</option>");
											}
										%>
									</SELECT>
 								
 								</td>
                                </tr>
                                <tr>
                                <td class="tableTitleR">Position Held*:</td>
                                <td class="tableResultR">
	                                <select id="perm_position" name="perm_position" class="form-control">	
	                                </select>
                                </td>
 								<td class="tableTitleL">Position Type*:</td>
 								<td class="tableResultL">
	 								<select id="positiontype" name="positiontype" class='form-control' >
	 								    <option value="">--- Please Select ---</option>
		              	  				<option value="C">Casual</option>
		              	  				<option value="P">Permanent</option>
		              	  				<option value="S">Substitute</option>
		              	  				<option value="T">Temporary</option>
		              	  			</select>
 								</td>
                                </tr>
                                <tr>
                                <td class="tableTitleR">Position Hours/Wk*:</td>
                                <td class="tableResultR"><input type="text" name="position_hours" id="position_hours" class="form-control" placeholder="Enter # of Hours (i.e. 35)"></td>
                                </tr>
                                <tr>
                                <td class="tableTitleR">Start Date*:</td>
                                <td class="tableResultR"><input type="text" name="sdate" id="sdate" class="form-control" placeholder="Enter Start Date"></td>
                                <td class="tableTitleR">End Date(if applicable):</td>
                                <td class="tableResultR"><input type="text" name="edate" id="edate" class="form-control" placeholder="Enter End Date"></td>
                                </tr>
                                </tbody>
                                </table>
         		
         		
         		<div align="center"><input type="button" class="btn btn-xs btn-primary" value="Add Position" onclick="submitNlesdExp('C')"></div>
                <input type="hidden" id="hidadd" name="hidadd" value="viewing">
                                       			
                <hr>                      		
 </div>
         					
          
         <div align="center"><a class="btn btn-xs btn-danger" href="view_applicant_ss.jsp">Back to Profile</a> <input type="button" class="btn btn-xs btn-success" value="Save/Update" onclick="submitNlesdExp('N')"></div>
          
          
         
         
      	</form>

 
 </div></div></div></div>
 
 
 						<%if(request.getAttribute("msg")!=null){%>
							<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>
						<%if(request.getAttribute("errmsg")!=null){%>
							<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>
  
</body>
</html>
