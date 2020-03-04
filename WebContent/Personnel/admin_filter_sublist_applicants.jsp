<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.school.bean.*,
                  com.awsd.school.dao.*,
                  com.esdnl.school.bean.*,
                  com.esdnl.school.database.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.nlesd.school.bean.*,
                  com.nlesd.school.service.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />

<c:set var='szones' value='<%= SchoolZoneService.getSchoolZoneBeans() %>' />
<c:set var='regions' value='<%= RegionManager.getRegionBeans() %>' />
<c:set var='sgroups' value='<%= SubjectGroupManager.getSubjectGroupBeans() %>' />

<%
SubListBean list = (SubListBean) request.getAttribute("list");
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
			    vertical-align:middle;
			    position: relative;
			    bottom: 0px;
			}
			
		</style>
		
		
		<script type='text/javascript'>
			$(function(){
				
					$('input[name=perm]').click(function() {
							if($('#perm-yes').is(':CHECKED')){
								$('#row-mindays').hide();
							}
							else {
								$('#row-mindays').show();
							}
					});
					
				
					$('#majorsgroup').change(function(){
						$.post('/MemberServices/Personnel/admin/ajax/getSubjectGroupSubjects.html', 
								{
									'groupids[]' : [$('#majorsgroup').val()],
									'ajax' : true
								},
								function(data) {
									if($(data).find('GET-SUBJECT-GROUP-SUBJECTS-RESPONSE').length > 0) {
										$(data).find('SubjectBean').each(function(){
											$("#majors option[value='" + $(this).attr('subject-id') + "']").attr("selected", "selected");
										});
									}
								}, 'xml');
					});
					
					$('#minorsgroup').change(function(){
						$.post('/MemberServices/Personnel/admin/ajax/getSubjectGroupSubjects.html', 
								{
									'groupids[]' : [$('#minorsgroup').val()],
									'ajax' : true
								},
								function(data) {
									if($(data).find('GET-SUBJECT-GROUP-SUBJECTS-RESPONSE').length > 0) {
										$(data).find('SubjectBean').each(function(){
											$("#minors option[value='" + $(this).attr('subject-id') + "']").attr("selected", "selected");
										});
									}
								}, 'xml');
					});
			});
		</script>
	</head>
	
	<body>
<div class="panel panel-success">
  <div class="panel-heading">SubList <%=list.getTitle()%> Filter</div>
  	<div class="panel-body">
  	To filter out applicants for this SubList, select the criteria below. <br/><br/>
	                          <form action="viewFilterSubListApplicantsResults.html" method="post">
	                            
	                            <input type="hidden" id="listid" name="listid" value="<%=list.getId()%>">
	                              
	                              <div class="input-group">
								    <span class="input-group-addon">Region: ${rname}</span>
								     <div class="form-control">${rname}</div>
								  </div>
								   
	                              <div class="input-group">
								    <span class="input-group-addon">Applicant(s) in a Permanent Contract?</span>
								    <div class="form-control">
								     <label class="radio-inline"><input type="radio" name="perm" value="Y" id="perm-yes">Yes</label>
									<label class="radio-inline"><input type="radio" name="perm" value="N" id="perm-no">No</label>
									<label class="radio-inline"><input type="radio" name="perm" value="A" id="perm-any" checked>Any</label>
								    </div>
								  </div> 
	                               
	                              <div class="input-group">
								    <span class="input-group-addon">Permanent Exp (Months)</span>
								    <input type="text" name="perm_exp" id="perm_exp" class="form-control" value="0" >
								  </div> 
								  	                                
	                              <div class="input-group">
								    <span class="input-group-addon">Replacement Exp (Months)</span>
								    <input type="text" name="rep_exp" id="rep_exp" class="form-control" value="0">
								  </div>         
	                                      
	                              <div class="input-group">
								    <span class="input-group-addon">Replacement Exp + Permanent Exp (Months)</span>
								    <input type="text" name="tot_exp" id="tot_exp" class="form-control" value="0">
								  </div>       
	                                     
	                              <div class="input-group">
								    <span class="input-group-addon"># Sub Days</span>
								    <input type="text" name="sub_days" id="sub_days" class="form-control" value="0">
								  </div>   	
	                                     
	                              <div class="input-group">
								    <span class="input-group-addon"># Special Ed. Courses</span>
								    <input type="text" name="num_sped" id="num_sped" class="form-control" value="0">
								  </div> 
								  
	                              <div class="input-group">
								    <span class="input-group-addon"># French Language Courses</span>
								    <input type="text" name="num_french" id="num_french" class="form-control" value="0">
								  </div>   
								       
	                               <div class="input-group">
								    <span class="input-group-addon"># Math Courses</span>
								    <input type="text" name="num_math" id="num_math" class="form-control" value="0">
								  </div> 
								  
								  <div class="input-group">
								    <span class="input-group-addon"># English Courses</span>
								    <input type="text" name="num_english" id="num_english" class="form-control" value="0">
								  </div> 
								  
								  <div class="input-group">
								    <span class="input-group-addon"># Music Courses</span>
								    <input type="text" name="num_music" id="num_music" class="form-control" value="0">
								  </div>   
								      
	                              <div class="input-group">
								    <span class="input-group-addon"># Technology Courses</span>
								    <input type="text" name="num_tech" id="num_tech" class="form-control" value="0">
								  </div> 
								  <div class="input-group">
								    <span class="input-group-addon"># Social Studies Courses</span>
								    <input type="text" name="num_sstudies" id="num_sstudies" class="form-control" value="0">
								  </div> 
								  <div class="input-group">
								    <span class="input-group-addon"># Art Courses</span>
								    <input type="text" name="num_art" id="num_art" class="form-control" value="0">
								  </div> 
								  <div class="input-group">
								    <span class="input-group-addon"># Science Courses</span>
								    <input type="text" name="num_science" id="num_science" class="form-control" value="0">
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
								        <a data-toggle="collapse" id="selMjrsSG" href="#mjrSubGrp"><span class="glyphicon glyphicon-triangle-bottom"></span> Select Major Subject Group(s)</a>
								      </h4>
								    </div>
								    <div id="mjrSubGrp" class="panel-collapse collapse">
								    <div class="input-group" style="padding:3px;">
									<c:forEach items='${sgroups}' var='g'>
								        <div style="float:left;min-width:200px;width:25%;color:DimGrey;font-size:11px;">
											<label class="checkbox-inline">
								 				<input type="checkbox" name="majorsgroup" id="majorsgroup" value="${g.groupId}">${g.groupName}
											</label>
										</div>
								    </c:forEach>
								    </div>
								    </div>
								  </div>
								</div>	                                     
									                                     
								<div class="panel-group">
								  <div class="panel panel-default">
								    <div class="panel-heading">
								      <h4 class="panel-title">
								        <a data-toggle="collapse" id="selMjrs" href="#mjrs"><span class="glyphicon glyphicon-triangle-bottom"></span> Select Major(s)</a>
								      </h4>
								    </div>
								    <div id="mjrs" class="panel-collapse collapse">
								    <div class="input-group" style="padding:3px;">
								      <job:MajorMinor id="majors" cls="form-control" />
								      </div>
								    </div>
								  </div>
								</div>		                                  
										                            
									                  
								<div class="panel-group">
								  <div class="panel panel-default">
								    <div class="panel-heading">
								      <h4 class="panel-title">
								        <a data-toggle="collapse" id="selMnrsSG" href="#mnrSubGrp"><span class="glyphicon glyphicon-triangle-bottom"></span> Select Minor Subject Group(s)</a>
								      </h4>
								    </div>
								    <div id="mnrSubGrp" class="panel-collapse collapse">
									<div class="input-group" style="padding:3px;">
									<c:forEach items='${sgroups}' var='g'>
									   <div style="float:left;min-width:200px;width:25%;color:DimGrey;font-size:11px;">
										<label class="checkbox-inline">
											<input type="checkbox" name="minorsgroup" id="minorsgroup" value="${g.groupId}">${g.groupName}
										</label>
										</div>
									</c:forEach>
								    </div>
								    </div>
								  </div>
								</div>	                                      
									                                      
								<div class="panel-group">
								  <div class="panel panel-default">
								    <div class="panel-heading">
								      <h4 class="panel-title">
								        <a data-toggle="collapse" id="selMnrs" href="#mnrs"><span class="glyphicon glyphicon-triangle-bottom"></span> Select Minor(s)</a>
								      </h4>
								    </div>
								    <div id="mnrs" class="panel-collapse collapse">
								    <div class="input-group" style="padding:3px;">
								       <job:MajorMinor id="minors" cls="form-control"/>
								       </div>
								    </div>
								  </div>
								</div>	                                    	                                      
									                                        
								<div class="panel-group">
								  <div class="panel panel-default">
								    <div class="panel-heading">
								      <h4 class="panel-title">
								        <a data-toggle="collapse" id="selPrefs" href="#rprefs"><span class="glyphicon glyphicon-triangle-bottom"></span> Select Regional Preferences</a>
								      </h4>
								    </div>
								    <div id="rprefs" class="panel-collapse collapse">
								       <c:forEach items='${szones}' var='sz'>
									      <c:set var='regions' value='<%= RegionManager.getRegionBeans((SchoolZoneBean)pageContext.getAttribute("sz", PageContext.PAGE_SCOPE)) %>' />
									         <div style="padding:5px;">
									         <span style="text-transform:Capitalize;">${sz.zoneName} Region</span><br/>   		
									          <c:forEach items='${regions}' var='r'>
									            <div style="float:left;min-width:180px;width:20%;color:DimGrey;font-size:11px;">
													<label class="checkbox-inline">
													<input type="checkbox" name="region_prefs" id="region_prefs" value="${r.id}"><span style="text-transform:Capitalize;">${r.name} Zone</span></label>
												</div>
									         </c:forEach>
									         </div>
									    <br/>
									   </c:forEach>
								    </div>
								  </div>
								</div>		                                     
	                                     	                                    
	                                 
	                                       
<div align="center"><input class="btn btn-xs btn-primary" type="submit" value="Submit Filter"> <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a></div>	                                    
	                                     
	                                 
	                          </form>
</div></div>

<script>
	$('#selPrefs,#selMnrs,#selMjrs,#selDegs,#selMnrsSG,#selMjrsSG').click(function(){
	    $(this).find('span').toggleClass('glyphicon-triangle-bottom').toggleClass('glyphicon-triangle-top');
	});
	
	
	
	
	</script> 	                      
	</body>
</html>
