<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.school.bean.*,
                  com.awsd.school.dao.*,
                  com.esdnl.school.bean.*,
                  com.esdnl.school.database.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.nlesd.school.bean.*,
                  com.nlesd.school.service.*,
                  com.esdnl.personnel.jobs.constants.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-OTHER-MANAGER-VIEW" />

<c:set var='szones' value='<%= SchoolZoneService.getSchoolZoneBeans() %>' />
<c:set var='regions' value='<%= RegionManager.getRegionBeans() %>' />
<c:set var='sgroups' value='<%= SubjectGroupManager.getSubjectGroupBeans() %>' />

<%
  	JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
	TreeMap<Integer,String> hmapc = ApplicantEducationPostSSManager.getDiplomaCertValues(3);
	TreeMap<Integer,String> hmapd = ApplicantEducationPostSSManager.getDiplomaCertValues(2);
%>


<html>

	<head>
		<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
		
		<style type='text/css'>
			optgroup { text-transform: uppercase; color: red; }
			optgroup option { text-transform: capitalize; color: black;} 
			
			input[type=checkbox] {
			    vertical-align:bottom;
			    position: relative;
			    bottom: 0px;
			}
			
		</style>
		
		
		<script type='text/javascript'>
			$(function(){
				var pickerOpts={dateFormat:"mm/dd/yy",changeMonth:true,changeYear:true,yearRange: "-75:+0"};
			    $( "#sdate" ).datepicker(pickerOpts);
			});
		</script>
		
		<script src="includes/js/changepopup.js"></script>
	</head>
	
	<body>
  <br/>
  <div class="panel panel-success">
  <div class="panel-heading">Competition # <%=job.getCompetitionNumber()%> Filter</div>
  	<div class="panel-body">
	
	                          <form action="filterJobApplicantsSS.html" method="post">
	                            
	                           <div class="input-group">
								    <span class="input-group-addon">Applicant(s) Currently Employed By NLESD?</span>
								    <div class="form-control">
								    <label class="radio-inline"><input type="radio" name="perm" value="Y" id="perm-yes">Yes</label>
									<label class="radio-inline"><input type="radio" name="perm" value="N" id="perm-no">No</label>
									<label class="radio-inline"><input type="radio" name="perm" value="A" id="perm-any" checked>Any</label>
								    </div>
							   </div> 	                          
	                           <div class="input-group" style="display:none;">
								    <span class="input-group-addon">Senority Date</span>
								    <div class="form-control">
								    		<label class="radio-inline"><input type="radio" name="sfilter" value="before" >Before</label>
											<label class="radio-inline"><input type="radio" name="sfilter" value="after" >After</label>
											<label class="radio-inline"><input type="radio" name="sfilter" value="" checked>N/A</label><br/>
											
								    </div>
								    <input type="text" name="sdate" id="sdate" class="form-control" value="" placeholder="Select Date">
							   </div>  
	                            
	                           <div class="input-group">
								    <span class="input-group-addon">Current Union</span>
								    <c:set var='unioncodes' value='<%=RequestToHireManager.getUnionCodes()%>'/>
			                    	<SELECT name="union_code" id="union_code" class="form-control" style="width:250px;" onchange="getPositionsFilter();">
										<OPTION VALUE='-1'>N/A</OPTION>
										<%
											TreeMap<String,Integer> ucodes = RequestToHireManager.getUnionCodes();
											for(Map.Entry<String, Integer> entry : ucodes.entrySet()) {
											  String key = entry.getKey();
											  Integer value = entry.getValue();

											 out.println("<option value='" + value + "'>" + key + "</option>");
											}
										%>
									</SELECT>
								</div>  
	                                      
	                           <div class="input-group">
								    <span class="input-group-addon">Current Position</span>
								    
									    <select id="perm_position" name="perm_position" class="form-control">
									    		
	                                	</select>
								    
							   </div>  
	                             
	                             
	                                     
	                            <div class="panel-group">
								  <div class="panel panel-default">
								    <div class="panel-heading">
								      <h4 class="panel-title">
								        <a data-toggle="collapse" id="selDegs" href="#degrees"><span class="glyphicon glyphicon-triangle-bottom"></span> Select Degree(s)</a>
								      </h4>
								    </div>
								    <div id="degrees" class="panel-collapse collapse">
								    <div class="input-group" style="padding:3px;">
								      <job:Degrees id="degrees" cls="form-control"/>
								      </div>
								    </div>
								  </div>
								</div>         
	                             
	                           
								<div class="panel-group">
								  <div class="panel panel-default">
								    <div class="panel-heading">
								      <h4 class="panel-title">
								        <a data-toggle="collapse" id="selDips" href="#diplomas"><span class="glyphicon glyphicon-triangle-bottom"></span> Select Diploma(s)</a>
								      </h4>
								    </div>
								    <div id="diplomas" class="panel-collapse collapse">
								    <div class="input-group" style="padding:3px;">
								      <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;">
								      <label class="checkbox-inline">      
								      <input type='checkbox' name="ddiploma" VALUE='-1'>Not Applicable
								     </label>
								    </div>
								    <% for (Map.Entry<Integer, String> entry : hmapd.entrySet()) { %>
								     <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;">
										<label class="checkbox-inline">
										<input type="checkbox" name="ddiploma" value='<%=entry.getKey()%>'><%=entry.getValue()%>
										</label>
									</div>
								    <%}%> 
								      </div>
								    </div>
								  </div>
								</div> 
								
								 <div class="panel-group">
								  <div class="panel panel-default">
								    <div class="panel-heading">
								      <h4 class="panel-title">
								        <a data-toggle="collapse" id="selCerts" href="#certificates"><span class="glyphicon glyphicon-triangle-bottom"></span> Select Certificate(s)</a>
								      </h4>
								    </div>
								    <div id="certificates" class="panel-collapse collapse">
								    <div class="input-group" style="padding:3px;">
								      <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;">
								      <label class="checkbox-inline">      
								      <input type='checkbox' name="dcert" VALUE='-1'>Not Applicable
								     </label>
								    </div>
								    <% for (Map.Entry<Integer, String> entry : hmapc.entrySet()) { %>
								     <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;">
										<label class="checkbox-inline">
										<input type="checkbox" name="dcert" value='<%=entry.getKey()%>'><%=entry.getValue()%>
										</label>
									</div>
								    <%}%>
								      </div>
								    </div>
								  </div>
								</div> 
								<div class="panel-group">
								  <div class="panel panel-default">
								    <div class="panel-heading">
								      <h4 class="panel-title">
								        <a data-toggle="collapse" id="selDocs" href="#documents"><span class="glyphicon glyphicon-triangle-bottom"></span> Select Document(s)</a>
								      </h4>
								    </div>
								    <div id="documents" class="panel-collapse collapse">
								    <div class="input-group" style="padding:3px;">
								      <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;">
								      <label class="checkbox-inline">      
								      <input type='checkbox' name="dcert" VALUE='-1'>Not Applicable
								     </label>
								    </div>
								    <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="da" id="da" /> Drivers Abstract</label></div> 
								    <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="ohs" id="ohs" /> OHS Training</label></div>
								    <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="cc" id="cc" /> Code of Conduct</label></div>
								    <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="vs" id="vs" /> Vulnerable Sector Check</label></div>								    
								    <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="fa" id="fa" /> First Aid</label></div>
								    <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="whmis" id="whmis" /> WHMIS</label></div> 
								    <div style="float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;"><label class="checkbox-inline"><input type="checkbox" name="dl" id="dl" /> Drivers License</label></div>
									</div>
								    </div>
								  </div>
								</div>								            
	                            
	                                      
											
<div align="center"><input class="btn btn-xs btn-primary" type="submit" value="Submit Filter"> <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a></div>
	                                     
	                          </form>
</div></div>
<script>
	$('#selCerts,#selDegs,#selDips,#selDocs').click(function(){
	    $(this).find('span').toggleClass('glyphicon-triangle-bottom').toggleClass('glyphicon-triangle-top');
	});
</script> 
	                       
	</body>
</html>
