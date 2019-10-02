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

<job:ApplicantLoggedOn/>

<%
  ApplicantProfileBean profile = null;
  ApplicantEsdExperienceBean esd_exp = null;
  User usr = null;
  
  profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
  usr = (User) session.getAttribute("usr");
  
  if(profile != null)
  {
    esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(profile.getSIN());
  }
  
  Calendar now = Calendar.getInstance();
  boolean canUpdate  = ((now.get(Calendar.MONTH) < Calendar.JULY || now.get(Calendar.MONTH) >= Calendar.SEPTEMBER)) 
  	|| (usr != null && (usr.checkRole("ADMINISTRATOR") || usr.checkRole("SEO - PERSONNEL") || usr.checkRole("AD HR")));
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
    function toggleRow(rid, state)
    {
      var row = document.getElementById(rid);
      row.style.display=state;
    }
  </script>
</head>
<body>
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">2a</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
<%if(profile != null){%>
	SECTION 2a: Editing your Teacher/Educator HR Application Profile 
<%} else { %>
	SECTION 2a: New Applicant HR Registration for Teachers/Educators
<%} %>
</div>

<br/>Please complete/update your profile below. Fields marked * are required. 

<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>						


<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>2a. NLESD EXPERIENCE</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 
    
    
    <form id="frmPostJob" action="applicantRegistration.html?step=2" method="post">
    
    
    <div class="form-group">
  		<label for="perm_contract">Do you currently hold a permanent contract (probationary or tenured) with the Newfoundland and Labrador English School District?</label><br/>
  		<label class="radio-inline"><input type='radio' name='perm_contract' id='perm_contract' value='Y' onclick='toggleRow("perm_school_yes_row", "block");toggleRow("perm_school_no_row", "none");toggleRow("repl_sub_time_yes_row","none");' >Yes</label>
  		<label class="radio-inline"><input type='radio' name='perm_contract' id='perm_contract' value='N' onclick='toggleRow("perm_school_yes_row", "none");toggleRow("perm_school_no_row", "block");' checked="checked">No</label>					
	</div>
    
   
    
				<div id="perm_school_yes_row" style="display:none;">
								<table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
							    <tr style="border-top:1px solid silver;">
							    <td class="tableTitle">School:</td>
				                <td class="tableResult"><job:JobLocation id='perm_school' cls="form-control" value='<%=(esd_exp != null)?Integer.toString(esd_exp.getPermanentContractSchool()):""%>' /></td>
								</tr>
								<tr style="border-bottom:1px solid silver;">
							    <td class="tableTitle">Position Held:</td>
				                <td class="tableResult"><input type="text" name="perm_position" id="perm_position" class="form-control" value='<%=((esd_exp != null)&&!StringUtils.isEmpty(esd_exp.getPermanentContractPosition()))?esd_exp.getPermanentContractPosition():""%>'></td>
								</tr>         			
         						</tbody>
         						</table>
                </div>
          		
                <div id="perm_school_no_row" >
                           	<div class="form-group">
  							<label for="repl_sub_time">Are you currently in a replacement contract?</label><br/>
  							<label class="radio-inline"><input type="radio" name="repl_sub_time" id="repl_sub_time" value="Y" onclick="toggleRow('repl_sub_time_yes_row', 'block');">Yes</label>
  							<label class="radio-inline"><input type="radio" name="repl_sub_time" id="repl_sub_time" value="N" onclick="toggleRow('repl_sub_time_yes_row', 'none');">No</label>					
							</div>
                </div>

                <div id="repl_sub_time_yes_row" style="display:none;">
                
                <table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
							    <tr style="border-top:1px solid silver;">
							    <td class="tableTitle">School:</td>
                				<td class="tableResult"><job:JobLocation id='contract_school' cls='form-control' value='<%=(esd_exp != null)?Integer.toString(esd_exp.getContractSchool()):""%>' /></td>
                				</tr>
                				<tr style="border-bottom:1px solid silver;">
							    <td class="tableTitle">End Date (mm/yyyy):</td>
                				<td class="tableResult"><input type="text" name="contract_enddate" class="form-control" readonly id="contract_enddate" value='<%=((esd_exp != null)&&(esd_exp.getContractEndDate()!=null))?(new SimpleDateFormat("MM/yyyy")).format(esd_exp.getContractEndDate()):""%>'></td>
                				</tr>
           						</tbody>
           		</table>				
            	
            	</div>
	<% if(!canUpdate) { %>
    <div class="alert alert-danger">Sorry, your contract status cannot be updated at this time.</div>
    <% } %>
	
							
	
	
	<div align="center"><a class="btn btn-sm btn-danger" href="view_applicant.jsp">Back to Profile</a> <input type="submit" class="btn btn-sm btn-success" value="Save/Update" <%= !canUpdate ? " disabled=disabled" : "" %> /></div>
          	
    </form>
 
</div></div></div></div> 
 
 						<%if(request.getAttribute("msg")!=null){%>
							<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>
						<%if(request.getAttribute("errmsg")!=null){%>
							<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>
 
                             
<%if(esd_exp != null){
      		if(esd_exp.getPermanentContractSchool() > 0){%>
        		<script type='text/javascript'>document.forms[0].perm_contract[0].click();</script>
  			<%  }else{%>
        		<script type='text/javascript'>document.forms[0].perm_contract[1].click();</script>
  			<%  }
if(esd_exp.getContractSchool() > 0){%>
        		<script type='text/javascript'>document.forms[0].repl_sub_time[0].click();</script>
  			<% }else{%>
        		<script type='text/javascript'>document.forms[0].repl_sub_time[1].click();</script>
  			<% }%>
<%}%>
  <script>
 $('document').ready(function(){
		$("#contract_enddate").datepicker({
	      	changeMonth: true,
	      	changeYear: true,
	      	dateFormat: "mm/yy",
	      	yearRange: "-75:+0"
	   });
 });
 </script>  	
</body>
</html>
