<%@ page language="java"
         import="java.util.*,
                 java.text.*,com.awsd.security.*,
                 com.esdnl.personnel.v2.model.sds.bean.LocationBean,
                 com.esdnl.personnel.v2.utils.StringUtils,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2" %>
 
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
<job:MyHRPSettingsSecurityCheck permissions="PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" setting="isPpBlockSchools" expectedValue="<%= false %>" />

<% 
	MyHrpSettingsBean rbean=MyHrpSettingsManager.getMyHrpSettings(); 
%>
<c:set var="permanentVal" value="0" />
<html>
	<head>
		<title>MyHRP Applicant Profiling System</title>				
		<script type="text/javascript" src='includes/js/encoder.js'></script>
		<script type="text/javascript" src='includes/js/position_planning.js?<%= new Date().getTime() %>'></script>
		<script type="text/javascript">
			$('document').ready(function(){
				$.ajaxSetup({ cache: false });
				
				<%
					User usr = (User) session.getAttribute("usr");
					LocationBean loc = (LocationBean) request.getAttribute("location");
				%>
				
				<% if(usr.checkRole("ADMINISTRATOR")) { %>
					isSystemAdministrator = true;
				<% } %>
				
				<% if((usr.checkPermission("PERSONNEL-PRINCIPAL-VIEW") || usr.checkPermission("PERSONNEL-VICEPRINCIPAL-VIEW")) && loc != null) { %>
					isPositionPlanningAdmin = false;
					schoolYear = $.cookie('myhrp-pp-schoolyear') ? $.cookie('myhrp-pp-schoolyear') : '<%= StringUtils.getSchoolYear(Calendar.getInstance().getTime()) %>';
					loadTeacherAllocation(schoolYear, "<%=loc.getLocationDescription() %>");
					$('.SchoolName').text("<%=loc.getLocationDescription() %>");
					$('.SchoolYear').text(schoolYear);
				<% } else { %>
					if($.cookie('myhrp-pp-schoolyear') && $.cookie('myhrp-pp-location')) {
						$('#lst_schoolyear').val($.cookie('myhrp-pp-schoolyear'));
						$('#lst_school').val($.cookie('myhrp-pp-location'));
						loadTeacherAllocation($.cookie('myhrp-pp-schoolyear'), $.cookie('myhrp-pp-location'));
						$('.SchoolName').text($.cookie('myhrp-pp-location'));
						$('.SchoolYear').text($.cookie('myhrp-pp-schoolyear'));
					}
				<% } %>
			});
			
			$("#loadingSpinner").css("display","none");
		</script>

	
	
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0/dist/Chart.min.js"></script>	
	<style>
	.allocation-addon-text {background-color: #e6ffe6;color:Black; }
	</style>
	</head>
	
	<body>	
					<% if(rbean.isPpBlockSchools()) { %> 					
					<div class="alert alert-danger ppStatus" style="text-align:center;margin-top:10px;marging-bottom:10px;">
					<b>NOTICE:</b> Planners open for viewing until May 7, 2023. Editing will be enabled after May 7, 2023.
					</div>  
					<%}else{ %>
					<div class="alert alert-danger ppStatus" style="text-align:center;margin-top:10px;marging-bottom:10px;">
					<b>NOTICE:</b> Planners open for viewing until May 7, 2023. Editing will be enabled after May 7, 2023.
					</div>  
					<%} %>			

	<form id="frm-add-allocation" action="addTeacherAllocation.html" method="post">
	                                	
	     <input id='hdn-allocation-id' type='hidden' value='' />
	                                	
	     <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Position Planning for Teacher Allocations</b></div>
      			 	<div class="panel-body"> 
      			 			<span class="no-print">Select the school (if not already selected) and school year from the fields below to display allocation information data. 
	                        Once selected, wait for the data to load and then select the tab you wish to open. 
	                        Depending on your level of access some data and/or features may or may not be available.	
	                        <br/>Fields marked with * are required.
	                        </span>
	            <br/><br/> 
	            
	          <div id='tbl-teacher-allocations'>	                                 
	                       <div class="container-fluid no-print">
	                       	    <div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									<div class="input-group" id='div_locationOptions' style="width:100%;text-align:right;"></div>
									</div>	
	                       	    </div>
								<div class="row">
									<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	                        
		                             	<div class="input-group" id='div_location'>
		    								<span class="input-group-addon formTitleArea">* School:</span>
		    								<jobv2:Locations id="lst_school" cls='form-control' />							    
		  								</div>
		  							</div>	
		  							<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">	
		  								<div class="input-group" id='div_schoolyear'>
		    								<span class="input-group-addon formTitleArea">* School Year:</span>
		    								<jobv2:SchoolYearListbox id="lst_schoolyear" pastYears="3" futureYears="1" cls='form-control' style='text-transform: uppercase;' />								    
		  							 	</div>
	  								</div>
	  							</div>
	  							<div class="row no-print">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									 <div class="input-group" id='div_schoolyearOptions' style="width:100%;padding-top:5px;padding-bottom:5px;text-align:center;"></div>
									</div>	
	                       	    </div>
	  						</div>	
	            
	            
	            <div class="panel-group" id="topSummaryPanel" style="display:none;">
  					
  					<div class="panel panel-default">
      					<div class="panel-heading">Quick Summary Information for <span class="SchoolName"></span> (<span class="SchoolYear"></span>)</div>
      					<div class="panel-body">
      					<div class="container-fluid">
									 <div class="row">
									 	<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6" style="font-size:11px;">
									 	This is a quick summary of allocations for <span class="SchoolName"></span>:<br/><br/>
									 								 										 	
									 	School Allocation Units: <span class="school-allocation-units-title"></span><br/>
									 	Other Allocation Units: <span class="total-extra-allocation-units-title"></span><br/>								 	
										
									 	<!-- 
									 	Teacher Allocation Units: <span class="total-tchr-allocation-units-title"></span><br/>									 	
									 	Teacher Learning Assistant Units: <span class="total-tla-allocation-units-title"></span><br/>
									 	Student Assistant Units: <span class="total-sa-allocation-units-title"></span><br/>
									 	-->
									 	
									 	<b>Total Allocation Units:</b> <span class="total-staffing-allocation-units-title"></span><br/>
									 	<i>*See Allocations/Other Tabs Below</i><br/><br/>							 	
									 	
									 	
									 	Permanent Position Units: <span class="total-permanent-position-units-title"></span><br/>
									 	Vacant Position Units: <span class="total-vacancy-position-units-title"></span><br/>
									 	Redundant Position Units: <span class="total-redundant-position-units-title"></span><br/>
									 	<i>*See Permanent/Vacant/Redundant Tabs Below</i><br/><br/>
									 	<b>Total Outstanding Units:</b> <span class="total-outstanding-assigned-units-title"></span>									 	
									 	</div>
									 	<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
									 		<canvas id="summaryChart" height="200" style="width:100%;min-width:200px;"></canvas>									 	
									 	</div>
					    			 </div>
					    </div>		
      					</div>
    				</div>
  				</div>
	                            
	                            
	                          
<div id='add-allocation-row' style='display:none;'>	
	                            
<!----ALLOCATIONS PANEL ---------------------------------------------------------->
	                            
<div class="panel-group">
  <div class="panel panel-info">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" href="#collapse1"><b>Allocations</b> (Total Units: <span class="school-allocation-units-title"></span>)</a>
      </h4>
    </div>
    <div id="collapse1" class="panel-collapse collapse in">
      <div class="panel-body">
	
				<div id='add-allocation-operation-table' align="center" style="width:100%;">
						<div class="container-fluid">
						
									 <div class="row">
									 	<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
											 <div class="input-group">
   												<span class="input-group-addon">Reg Units*:</span>
                       							<input id='txt_regUnits' name='txt_regUnits' type='text' class='allocation-input form-control input-sm'/>
                       						 </div>
                       					</div>	 
                       				    <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                       						 <div class="input-group">
    											<span class="input-group-addon">Administration Units*:</span>
                        							<input id='txt_administrative' name='txt_administrative' type='text' class='allocation-input form-control input-sm'/>
                        					 </div>
                        			    </div>
                        			    <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">                  						
                        					 <div class="input-group">
    											<span class="input-group-addon">Guidance Units*:</span>
                        							<input id='txt_guidance' name='txt_guidance' type='text' class='allocation-input form-control input-sm' />
                        					 </div>
                        				</div>                     				
                     				
                        				
                        				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">	 
                        					 <div class="input-group">
    											<span class="input-group-addon">Specialist Units*:</span>
                        							<input id='txt_specialist' name='txt_specialist' type='text' class='allocation-input form-control input-sm' />
                        					 </div> 
                        				</div>
                        				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">		                        					 		
                        					 <div class="input-group">
    											<span class="input-group-addon">LRT Units*:</span>
                        							<input id='txt_lrt' name='txt_lrt' type='text' class='allocation-input form-control input-sm' />
                        					 </div>
                        				</div>
                        				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">			
                        					 <div class="input-group">
    											<span class="input-group-addon">IRT Units*:</span>
                        							<input id='txt_irt1' name='txt_irt1' type='text' class='allocation-input form-control input-sm' />
                        					 </div>	
                        				</div>
                        				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3" style="display:none;">
                        					 <div class="input-group">
    											<span class="input-group-addon">IRT 2 Units*:</span>
                        							<input id='txt_irt2' name='txt_irt2' type='text' class='allocation-input form-control input-sm'/>
                        					 </div>			
                        				</div>
                        				
                        				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                        					 <div class="input-group">
    											<span class="input-group-addon">Reading Specialist Units*:</span>
                        							<input id='txt_reading' name='txt_reading' type='text' class='allocation-input form-control input-sm'/>
                        					 </div>
                        				</div>
                        				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                        				 	 <div class="input-group">
    											<span class="input-group-addon">TLA Units*:</span>
                        							<input id='txt_tla' name='txt_tla' type='text' class='allocation-input form-control input-sm' />
                        					 </div>			                        		
                        				</div>
                        				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                        					 <div class="input-group">
    											<span class="input-group-addon">Adjustment Units*:</span>
                        							<input id='txt_other' name='txt_other' type='text' class='allocation-input form-control input-sm'/>
                        					 </div>	
                        			 	</div>
                        				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">				
                        					 <div class="input-group">
    											<span class="input-group-addon">Stud. Asst. Hours*:</span>
                        							<input id='txt_sa' name='txt_sa' type='text' class='allocation-input form-control input-sm' />
                        					 </div>	
                        				</div>
                        				</div>
                        				
										
							</div>
					</div>
										
										<div style="clear:both;"></div>
										
					                        <div id='school-allocation-header-row' class='displayText' style='display:none;'>
					             				<div id='school-allocation-units' class='alert alert-warning' style="font-weight:bold;text-align:center;text-transform:capitalize;">School Allocation Units: 0.00</div>
											</div>					                             							
					                        <div id='add-allocation-operation-row'>
								                <div id='add-allocation-message' class="alert alert-danger" style="display:none;"></div>
								                <div align="center" class="no-print"><a href='#' id="btn-add-allocation" class='btn btn-xs btn-success'>Add Allocation</a></div>								                
									        </div>
	                             								
	                             								
	</div>
      
    </div>
  </div>
</div>
	                            

<!----OTHER PANEL ---------------------------------------------------------->


<div class="panel-group">
  <div class="panel panel-info">
    <div class="panel-heading">
      <h4 class="panel-title">                         
        <a data-toggle="collapse" href="#collapse2"><b>Other</b> (Total Units: <span class="total-extra-allocation-units-title"></span>)</a>
      </h4>
    </div>
    <div id="collapse2" class="panel-collapse collapse in">
      <div class="panel-body">
	                            
	
	  <div id='extra-allocation-header-row' style='display:none;'>
	 
	 
				<div class="table-responsive"> 
							   <table id='extra-allocation-table' class="table table-condensed table-hover" style="font-size:12px;background-color:#FFFFFF;">
								    <thead>
								      <tr>
								        <th width='20%'>Extra Allocation</th>
								        <th width='10%'>Type</th>
								        <th width='10%'>Date</th>
								        <th width='50%'>Rationale</th>
								        <th width='10%'>Options</th>
								      </tr>
								    </thead>
								    <tbody>
								            
								    </tbody>
						  		</table>
				</div> 
	 			<div id='extra-allocation-addition-table'>
          			<input type='hidden' id='hdn_extraID' name='hdn_extraID' value='' />
         			<div class="container-fluid">
	                       	 <div class="row">
         			             <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">        				
				         				<div class="input-group">				   
				  							<span class="input-group-addon">Extra Allocation*:</span>
				  								<input id='txt_extraUnits' name='txt_extraUnits' class="form-control" type='text' />
										</div>
         						 </div>
         						 <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6"> 
				         				<div class="input-group">
				   						<span class="input-group-addon">Allocation Type*:</span>							 
											  		<select id='lst_extraUnitsType' name='lst_extraUnitsType' class='form-control'>
											  		<option value="">**** Please Select ****</option>
				               								<% for(TeacherAllocationExtraBean.AllocationType at : TeacherAllocationExtraBean.AllocationType.values()) { 
				               											if(at.equals(TeacherAllocationExtraBean.AllocationType.UNKNOWN)) continue;
				               								%>
				               											<option value="<%= at.getValue() %>"><%= at %></option>
				               								<% } %>
				               						</select>
										</div>
         						</div>	
         					</div>
         					<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">         								
				               			<div class="input-group">
				  							<span class="input-group-addon">Rationale*</span>
				  							<textarea id='txt_extraUnitsRationale' name='txt_extraUnitsRationale' class="form-control"></textarea>
										</div>			
               						</div>
               				</div>
               		</div>
               				
               				<div id='add-allocation-extra-message' class="alert alert-danger" align="center" style="display:none;"></div>
                  	
                  			<div align="center" class="no-print" style="text-align:left;padding-top:5px;padding-bottom:5px;">
	                  			<a href="#" id='btn-cancel-allocation-extra' class='btn btn-xs btn-warning' style='display:none;'>Cancel</a>                  			
	                  			<a href="#" id='btn-add-allocation-extra' class='btn btn-xs btn-success'>Add Other Allocation</a> 							
 							</div>						
 							
                </div>
                <div style="clear:both;"></div>
				           <div id='total-extra-allocation-units' class="alert alert-warning" style="text-align:center;font-weight:bold;text-transform:capitalize;">Total Other Units: 0.00</div>
							                             				
	            </div>
	                             								
						<div id='total-allocation-header-row' style='display:none;'>
                  					<div id='total-tchr-allocation-units' class='alert alert-warning' style="text-align:center;font-weight:bold;float:left;width:32%;margin-right:1%;">
               							Total TCHR Allocation Units: 0.00
               						</div>
               						<div id='total-tla-allocation-units' class='alert alert-warning' style="text-align:center;font-weight:bold;float:left;width:34%;">
               							Total TLA Allocation Units: 0.00
               						</div>
               						<div id='total-sa-allocation-units' class='alert alert-warning' style="text-align:center;font-weight:bold;float:left;width:32%;margin-left:1%;">
               							Total SA Allocation Hours: 0.00
               						</div>                  									
                  		</div>
	  
	  
	            
	                            
		</div>
      
    </div>
  </div>
</div>	                            
	                            
	                             					
	                             		
	                             		
<!--PERMANENT POSITIONS TAB ---------------------------------------------------------->	                             		
	                             							
				                         
	                             							
	                             								
	<div class="panel-group">
  <div class="panel panel-success">
    <div class="panel-heading">
      <h4 class="panel-title">                         
        <a data-toggle="collapse" href="#collapse3"><b>Permanent Positions</b> (Total Units: <span class="total-permanent-position-units-title"></span>)</a>
      </h4>
    </div>
    <div id="collapse3" class="panel-collapse collapse in">
      <div class="panel-body">  
    	  <div id='permanent-positions-header-row' style='display:none;'>
    	
    	Please list all teachers permanently assigned to your school and their teaching unit. If an employee will be on leave for all or part of the upcoming school year, 
        please enter <b><i>0.00</i></b> for their teaching unit and list their position in the <b><i>Vacant Position</i></b> section below.
    	
    	<div class="table-responsive"> 
					   <table id='permanent-positions-table' class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">
						    <tbody>
						    </tbody>
				  		</table>
				</div> 
    	
    	<input type='hidden' id='hdn_PermPositionID' name='hdn_PermPositionID' value='' />
        			
        			<div id='permanent-positions-add-edit'>
        			
        			<div class="container-fluid">
	                       	 <div class="row">
         			             <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">        				
				         				<div class="input-group">				   
				  							<span class="input-group-addon">Teacher*:</span>
				  							<jobv2:EmployeesByLocation id='lst_teacher' multiple="false" cls='form-control' />
              							</div>
              					</div>		
              					<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
              							<div class="input-group">
	              							<span class="input-group-addon">Tenure*:</span>              							
	              							<input id='txt_tenure' name='txt_tenure' class='form-control' readonly="readonly" />
              							</div>
              					</div>	
              				</div>
              				<div class="row">
         			             <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">        				
				         				<div class="input-group">				   
				  							<span class="input-group-addon">FTE*:</span>
              								<input id='txt_fte' name='txt_fte' class='form-control'/>
              							</div>
              					</div>		
              					<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
              							<div class="input-group">
	              							<span class="input-group-addon">Assignment*:</span>               							
	              							<input id='txt_assignment' name='txt_assignment' class='form-control' />
              							</div>
              					</div>
              				</div>	
              				<div class="row">
	              				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 	
	              						<div id='add-permanent-position-message' class="alert alert-danger" style="display:none;"></div>	                  						
	                  							
	                  							<div align="center" class="no-print">
	                  							<a href="#" id='btn-cancel-permanent-position' class='btn btn-xs btn-warning' style='display:none;'>Cancel</a>
	                  							<a href="#" id='btn-add-permanent-position' class='btn btn-xs btn-success'>Add Position</a>
	                  							</div>                 						
	                  					<div id='total-permanent-position-units' class='alert alert-warning' style="font-weight:bold;text-align:center;margin-top:5px;">Permanent Position Total Units: 0.00</div>
	              				</div>
              				</div>	
           			</div>
           			
           			</div>
    	
    	
    	
    	</div>
    	
    	
    	
    	
    	
</div>
      
    </div>
  </div>
</div>	       


<!-- VACANT POSITIONS ------------------------------------------------------->
	                             							
<div class="panel-group">
  <div class="panel panel-danger">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" href="#collapse4"><b>Vacant Positions</b> (Total Units: <span class="total-vacancy-position-units-title"></span>)</a>
      </h4>
    </div>
    <div id="collapse4" class="panel-collapse collapse in">
      <div class="panel-body">	                             							
	              
	              	
	          <div id='vacant-positions-header-row' style='display:none;'>    	
	              	
	              	Please enter any vacant positions at your school by indicating the Job Description, Teacher Currently Owning Position, Type, Reason for Vacancy and Teaching Unit.
	              	
	              	<div class="table-responsive"> 
							   <table id='vacant-positions-table' class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">
								    <thead>
								      <tr>
								        <th width='20%'>Job Desc.</th>
								        <th width='10%'>Type</th>
								        <th width='25%'>Owner/Reason</th>
								        <th width='10%'>Term Dates</th>
								        <th width='5%'>Unit</th>
								        <th width='5%'>Ad Request</th>
								        <th width='5%'>Job Ad</th>
								        <th width='5%'>Filled</th>
								        <th width='10%'>Options</th>
								      </tr>
								    </thead>
								    <tbody>
								            
								    </tbody>
						  		</table>
					</div> 
	              
	           
	           
	           
	           <input type='hidden' id='hdn_VacantPositionID' name='hdn_VacantPositionID' value='' />
	                             										
	                             										
    					<div id='vacant-positions-add-edit'>
    					
    					<div class="container-fluid">
	                       	 <div class="row">
         			             <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">        				
				         				<div class="input-group">				   
				  							<span class="input-group-addon">Job Description*:</span>					
    										<textarea id='txt_jobDescription' name='txt_jobDescription' class='form-control'></textarea>
          								</div>
          						</div>	
          					</div>
          					<div class="row">
         			             <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">        				
				         				<div class="input-group">		
          					               	<span class="input-group-addon">Type*:</span>
          									<job:EmploymentStatus id='lst_type' cls='form-control' />
          						 		</div>
          						 </div>
          						 <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">        				
				         				<div class="input-group">		
          					               	<span class="input-group-addon">Owner*:</span>
          									<jobv2:EmployeesByLocation id='lst_owner' includeNA='VACANT - NO OWNER' multiple="false" cls='form-control' />
          								</div>
          						</div>
          					</div>	
          					<div class="row">
         			             <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">        				
				         				<div class="input-group">		
          					               	<span class="input-group-addon">Reason for Vacancy*</span>      							
          									<textarea id='txt_reasonVacancy' name='txt_reasonVacancy' class='form-control'></textarea>
          								</div>
          						 </div>		
          					</div>
          					<div class="row" style="margin-top:5px;">
          					 <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
          					  Terms Date:(If &lt; then full school year)
          					 </div>
          					</div>	
          					<div class="row">          					
         			             <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">         			               		
				         				<div class="input-group">		
          					               	<span class="input-group-addon">Start Date:</span>      							
          									<input id='txt_startTermDate' name='txt_startTermDate' class='form-control'/>          									
          								</div>
          						 </div>	
          						 <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">        			               		
				         				<div class="input-group">		
          					               	<span class="input-group-addon">End Date:</span> 
          					               	<input id='txt_endTermDate' name='txt_endTermDate' class='form-control'/>
          								</div>
          						 </div>			
          					</div>

										<div class="row">
											<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
												<div class="input-group">
													<span class="input-group-addon">Unit:</span> <input
														id='txt_vacancyUnit' name='txt_vacancyUnit'
														class='form-control' />
												</div>
											</div>
											<!--  
											<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
												<div class="checkbox">
													<label><input id='chk_advertised'
														name='chk_advertised' type='checkbox' /> Position
														Advertised?</label>
												</div>
											</div>
											-->
											<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
												<div class="checkbox">
													<label><input id='chk_filled' name='chk_filled'
														type='checkbox' /> Position Filled?</label>
												</div>
											</div>
											<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
												<%
													if (usr.checkRole("ADMINISTRATOR") || usr.checkRole("SENIOR EDUCATION OFFICIER")) {
												%>
												<div style="display: none;" id="divlinks">
													<a id="hrefad" target="_blank">
														<button type="button" class="btn btn-primary">
															<span id="hrefspan"></span>
														</button>
													</a>
												</div>
												<%
													}
												%>
											</div>
										</div>
														<div class="row">
          					<div id='add-vacancy-position-message' class="alert alert-warning" style="display:none;"></div>
             				</div>
             				<div class="row no-print" align="center" style="margin-top:5px;margin-bottom:5px;">			
             							<a href="#" id='btn-cancel-vacancy-position' class='btn btn-xs btn-warning' style='display:none;'>Cancel</a>
             							<a href="#" id='btn-add-vacancy-position' class='btn btn-xs btn-success'>Add Vacancy</a>
             				</div>		
              				<div class="row">		
          						<div id='total-vacancy-position-units' class='alert alert-warning' style="text-align:center;font-weight:bold;">Vacancy Position Total Units: 0.00</div>
          					</div>	
       					</div>
     
	           </div>   
                          							
</div>
      
    </div>
  </div>
</div>	
</div>	
<!-- REDUNDANT POSITIONS ---------------------------------------->	

<div class="panel-group">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" href="#collapse5"><b>Redundant Positions</b> (Total Units: <span class="total-redundant-position-units-title"></span>)</a>
      </h4>
    </div>
    <div id="collapse5" class="panel-collapse collapse in">
      <div class="panel-body">


 				<div id='redundant-positions-header-row' style='display:none;'>    	
	              	
	              	Please enter any redundant positions at your school.
	              	
	              	<div class="table-responsive"> 
							   <table id='redundant-positions-table' class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">
								    <thead>
								      <tr>
								        <th width='20%'>Teacher</th>
								        <th width='50%'>Rationale</th>
								        <th width='10%'>Unit</th>								        
								        <th width='20%'>Options</th>
								      </tr>
								    </thead>
								    <tbody>
								            
								    </tbody>
						  		</table>
					</div>
				<input type='hidden' id='hdn_RedundantPositionID' name='hdn_RedundantPositionID' value='' />
											<div id='redundant-positions-add-edit'>											
							
													<div class="container-fluid">
													<div class="row">
						         			             <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">        				
										         				<div class="input-group">				   
										  							<span class="input-group-addon">Teacher:</span>					
						    										<jobv2:EmployeesByLocation id='lst_redundantTeacher' multiple="false" cls='form-control' />
						          								</div>
						          						</div>	
						          						<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">        				
										         				<div class="input-group">				   
										  							<span class="input-group-addon">Unit:</span>					
						    										<input id='txt_redundancyUnit' name='txt_redundancyUnit' class='form-control' />
						          								</div>
						          						</div>	
						          					</div>
																				
							                       	 <div class="row">
						         			             <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">        				
										         				<div class="input-group">				   
										  							<span class="input-group-addon">Rationale:</span>					
						    										<textarea id='txt_redundancyRationale' name='txt_redundancyRationale' class='form-control'></textarea>
						          								</div>
						          						</div>	
						          					</div>
						          					
						          					 <div class="row">
						          					 		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
						          						 		<div id='add-redundancy-position-message' class="alert alert-warning" style="text-align:center;display:none;"></div>								                                					
						          						 	</div>          						          					 
						          					 </div>
						          					 
						          					 <div class="row no-print" style="margin-top:5px;margin-bottom:5px;">
						          					 		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" align="center"> 
						          					 		 				<a href="#" id='btn-cancel-redundancy-position' class='btn btn-xs btn-warning' style='display:none;'>Cancel</a> 
														                 	<a href="#" id='btn-add-redundancy-position' class='btn btn-xs btn-success'>Add Redundancy</a>
						          						 	</div>          						          					 
						          					 </div>
						          					
						          					 <div class="row">
						          					 		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
						          					 		<div id='total-redundant-position-units' class='alert alert-warning' style="text-align:center;font-weight:bold;">Redundant Position Total Units: 0.00</div>
						          						 	</div>          						          					 
						          					 </div>						          					
						          					
													</div>
											
											</div>
			
				
				</div>
                            									
</div>
      
    </div>
  </div>
</div>	

<!-- OTHER INFO -->
	                             								
	                             				
                       		<div id='total-staffing-allocation-header-row' style='display:none;'>
                       									
                             						<div id='total-staffing-allocation-units' class='alert alert-success' style="text-align:center;font-weight:bold;">
                             							Total Assigned School Staffing Units: 0.00
                             						</div>
                             </div>
                       										
                       		<div id='outstanding-positions-header-row' style='display:none;'>
                       									
                             						<div id='total-outstanding-assigned-units' class="alert alert-danger" style="text-align:center;font-weight:bold;">
                             							Outstanding Assigned Total Units: 0.00
                             						</div>
                             					
                       		</div>
	                             			
                             						
</div>	
	                             			
		
	                            			</div> 
	                            			
	                            			 <div align="center" class="no-print">
      			 		<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
      			 	</div> 
	                            			
	                            			
	                            			</div>
	                            			</div></div>
	                                </form>
	                 
	                
<script>
$(function() {
	$('#lst_school').on('change',function(){
		var optionText = $("#lst_school option:selected").val();
		$('.SchoolName').text(optionText);
	});
	
	$('#lst_schoolyear').on('change',function(){
		var optionText = $("#lst_schoolyear option:selected").val();
		$('.SchoolYear').text(optionText);
	});
});
</script>	
<div id="modalDelete" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"><span id="modaltitle"></span></h4><input type="hidden" id="hidid">
      </div>
      <div class="modal-body">
        <p><span id="deletemessage"></span></p>
        <p><span id="deletead"></span></p>
        <p><span id="deletejob"></span></p>
      </div>

      <div class="modal-footer">
      	<button type="button" class="btn btn-xs btn-primary" id="btnDeleteVac">Delete</button>
        <button type="button" class="btn btn-xs btn-default" data-dismiss="modal" >Close</button>
      </div>
    </div>

  </div>
</div>
                  
</body>

</html>
