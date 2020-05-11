<%@ page language="java"
        session="true"
        import="com.awsd.ppgp.*, 
                java.util.*,com.awsd.security.*, 
                java.text.*,com.awsd.personnel.*,
                com.awsd.common.*,com.esdnl.util.*,
                com.esdnl.personnel.v2.model.sds.bean.*"
        isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>

<%
	response.sendRedirect("/MemberServices/memberServices.html");
%>

<esd:SecurityCheck permissions='PPGP-VIEW,PPGP-VIEW-SUMMARY' />

<%
  User usr = null;
  PPGP ppgp = null;
  PPGPGoal goal = null;
  PPGPTask task = null;
  Personnel p = null;
  int pid;
  UserRoles roles = null;
  boolean bigbrother = false;
  boolean editable = false;
  EmployeeBean ebean = null;
  
  ebean = (EmployeeBean) request.getAttribute("ebean");
  usr = (User) session.getAttribute("usr");
  
  if(usr.checkPermission("PPGP-VIEW-SUMMARY") || usr.checkPermission("PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST"))
  {
    if(request.getAttribute("TEACHER")!= null)
    {
      p = (Personnel) request.getAttribute("TEACHER");
      bigbrother = true;
    }
    else
    {
      p = usr.getPersonnel();
    }
  }
  else
  {
    p = usr.getPersonnel();
  }

  roles = new UserRoles(p);

  ppgp = (PPGP) request.getAttribute("PPGP");

  if((ppgp != null) && ((!ppgp.isSelfReflectionComplete()) || (ppgp.isSelfReflectionComplete() && ppgp.getSchoolYear().equals(PPGP.getPreviousGrowthPlanYear()))
  		|| ppgp.getSchoolYear().equals(PPGP.getCurrentGrowthPlanYear()) 
  		|| ppgp.getSchoolYear().equals(PPGP.getNextGrowthPlanYear())))
    editable = true;
%>

<html>
	<head>
		<title>Professional Growth Plan Summary</title>
		
		<script language="JavaScript">
					
			$('document').ready(function() {
				var msgupdate="<%=request.getAttribute("msgupdate") %>";
				if(!(msgupdate == "null")){
					$("#divsuccess").text(msgupdate);
					$("#divsuccess").show();
				}
  				var totalgoals = $("#totalgoals").val();
  				if(parseInt(totalgoals,10) < 1){
  					$("#divdanger").show();
  				}else if(parseInt(totalgoals,10) == 1){
  					$("#divwarning").show();
  				}else{
  					$("#divcomplete").show();
  				}
				if (${REFRESH_ARCHIVE ne null}) {
					parent.ppgpmenu.document.location.reload();
				}

			
			});
		</script>
<script>
    $("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold; font-size:16px;}
.tableResult {font-weight:normal;}
.tableTitleWide {column-span: all;}
.tableTitleL {font-weight:bold;font-size:16px;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;font-size:16px;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>	
	</head>

	<body>
    <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading">
<%if(ppgp != null){%>
					<b><%=ppgp.getSchoolYear()%> Professional Learning Plan</b> for<br/>
<%}%>
	               	<b><span style="text-transform:Capitalize;"><%=p.getFullNameReverse()%></span></b> 				      
<%if(roles.containsKey("TEACHER") || roles.containsKey("PRINCIPAL") || roles.containsKey("VICE PRINCIPAL")){%>
				     of <b><%=p.getSchool().getSchoolName()%></b>
<%}%>
				    <br/>
				   <b>Date:</b> <%=(new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime())%>
	               	
	               	</div>
      			 	<div class="panel-body"> 
					<div class="alert alert-success" align="center" role="alert" style="display:none;" id="divsuccess"></div>
					<div class="alert alert-danger" align="center" role="alert" style="display:none;" id="diverror"></div>
				    <div class="alert alert-danger" align="center" role="alert" style="display:none;" id="divdanger">
  						No goals added for this Professional Learning Plan. <br/>Please click "ADD A GOAL" below to add a goal. Minimum two goals required.
					</div>
				    <div class="alert alert-warning" align="center" role="alert" style="display:none;" id="divwarning">
  						One goal added for this Professional Learning Plan. <br/>Please click "ADD A GOAL" below to add a goal. Minimum two goals required.
					</div>
				    <div class="alert alert-success no-print" align="center" role="alert" style="display:none;" id="divcomplete">
  						Two goals(or more) added for this Professional Learning Plan.  Minimum required goals completed.
					</div>
				    <div align="center" class="no-print">
					    <%if((ppgp != null) && editable && !bigbrother){%>
			  	      	<a href="viewGrowthPlan.html?sy=<%=ppgp.getSchoolYear()%>" class="no-print btn btn-success btn-xs" onclick="loadingData()">ADD A GOAL</a>
			        	<%}%>
						<a class="no-print btn btn-xs btn-danger" href="/MemberServices/PPGP/policy.jsp" onclick="loadingData()">HOME</a>
				    </div> 
				    <br/>
<%if((ppgp != null)){%>
				
					<input type="hidden" name="pid" id="pid" value="<%=ppgp.getPersonnel().getPersonnelID()%>" />
					<input type="hidden" name="totalgoals" id="totalgoals" value="<%=ppgp.entrySet().size()%>" />
					
				
<%if((ppgp.entrySet().size() >= 1)){
int cntrg=0;
%>
			  		
		<% for(Map.Entry<Integer, PPGPGoal> entry : ppgp.entrySet()) {
				    goal = entry.getValue();
				    cntrg++;
				    %>
				    
				    <table class="table table-condensed" style="font-size:12px;">							   
					<tbody>
					<tr style="background-color:#0066cc;font-size:14px;color:White;">					
				    <td colspan=4><div style="float:right;">
				    <%if(editable){%>
				    <a class="no-print btn-xs btn btn-success" title="Add Task/Strategy to Goal" onclick="loadingData()" href="addGrowthPlanTask.html?gid=<%=goal.getPPGPGoalID()%>&ppgpid=<%=ppgp.getPPGPID()%><%=(bigbrother)?"&pid="+p.getPersonnelID():""%>">ADD A TASK</a>
					<a href="#" class="no-print btn-xs btn btn-primary" title="Edit Goal Name" onclick="showupdategoal('<%=goal.getPPGPGoalID()%>','<%=goal.getPPGPGoalDescription().replaceAll("<[^>]*>", "").replaceAll("\\r\\n|\\r|\\n", "").replace("</p>","").replace("<br>","").replace("<p>","")%>');">EDIT GOAL</a>
				   	<a class="no-print btn-xs btn btn-danger" href="#" title="Delete Goal" onclick="showdeletegoal('<%=goal.getPPGPGoalID()%>','<%=ppgp.getPPGPID()%>',this);">DEL GOAL</a> 
					<%}%>
				    </div>
				    <b>GOAL #<%=cntrg%>:</b><br/><%=goal.getPPGPGoalDescription()%>
				    
				    </td>
				    </tr>
				    
								<%if(goal.entrySet().size() < 1){%>
										   	<tr>
										   	<td colspan=4>
										    <div class="alert alert-danger">No tasks added for this goal.</div>
											</td>
											</tr>
						
								<%} else {
									int cntrt=0;
								%>	
									<tr><td>
								
											
														<%for(Map.Entry<Integer, PPGPTask> g_entry : goal.entrySet()){
														task = g_entry.getValue();	
														cntrt++;
														%>
														<table class="table table-condensed table-bordered" style="font-size:12px;">							   
														<tbody>
														<tr class="warning">
													    <td colspan=4><b>TASK/STRATEGY #<%=cntrt%>: </b><%=task.getDescription()%></td>
													    </tr>
													    <tr class="active">
													    <td colspan=2 style="text-align:center;font-weight:bold;">RESOURCES/SUPPORT</td>
													    <td colspan=2 style="text-align:center;font-weight:bold;">TECHNOLOGY</td>
													    </tr>
													    <tr class="active">
													    <td width="25%" style="text-align:center;font-weight:bold;">School Support(s)</td>
													    <td width="25%" style="text-align:center;font-weight:bold;">District Support(s)</td>
													    <td width="25%" style="text-align:center;font-weight:bold;">School Support(s)</td>
													    <td width="25%" style="text-align:center;font-weight:bold;">District Support(s)</td>
													   	</tr>
													    <tr>
													    <td><%=task.getSchoolSupport()%></td>
													    <td><%=task.getDistrictSupport()%></td>
													    <td><%=!StringUtils.isEmpty(task.getTechnologySchoolSupport())?task.getTechnologySchoolSupport():""%></td>
													    <td><%=!StringUtils.isEmpty(task.getTechnologyDistrictSupport())?task.getTechnologyDistrictSupport():""%></td>
													   	</tr>
													 	<tr>
													 	<td  class="active"><b>COMPLETION DATE:</b></td>
													 	<td colspan=3><%=task.getCompletionDate()%></td>
													 	</tr>
													 	<tr>
													 	<td class="active"><b>SELF EVALUATION:</b></td>
													 	<td colspan=3>
													 	<%=!StringUtils.isEmpty(task.getSelfEvaluation())?task.getSelfEvaluation():"N/A"%>
													 	</td>
													 	</tr>
																	<%if(editable) { %>
																     <tr class="active no-print">
																     <td colspan=4 style="text-align:right;">					   
																		 <a class="no-print btn btn-xs btn-primary" title="Edit this Task/Strategy" onclick="loadingData()" href="editGrowthPlanTask.html?tid=<%=task.getTaskID()%>&gid=<%=goal.getPPGPGoalID()%>&ppgpid=<%=ppgp.getPPGPID()%><%=(bigbrother)?"&pid="+p.getPersonnelID():""%>">EDIT TASK</a>
																		 <a class="no-print btn btn-xs btn-danger" title="Delete this Task/Strategy" href="#" onclick="showdeletetask('<%=task.getTaskID()%>','<%=goal.getPPGPGoalID()%>',this);">DEL TASK</a>
																	 </td>
																     </tr>      
																	<% } %>
														</tbody>
														</table>		
														<%}%>
									</td></tr>
									<%}%>	        
				       
				       </tbody>
				       </table>
				     	<div class="pagebreak"></div>			      
			<%}%>	
			
	<%}%>
			
			<%}else{%>
				<div align="center" class="alert alert-info">Choose a PGP from the Growth Plan Archive to view.</div>
			<%}%>
		

	

		


</div></div></div>

		
<!-- Modal -->
  			<div class="modal fade" id="myModal" role="dialog">
    			<div class="modal-dialog">
    			<!-- Modal content-->
      				<div class="modal-content">
        				<div class="modal-header">
          					<button type="button" class="close" data-dismiss="modal">&times;</button>
          					<h4 class="modal-title">Delete Goal/Task</h4>
        				</div>
        				<div class="modal-body">
          					<p><span id="spantitle" name="spantitle"></span></p><br>
          					<p><span id="spangoal" name="spangoal"></span></p>
        				</div>
        				<div class="modal-footer">
        					<button type="button" class="btn btn-danger" id="butdelete" name="butdelete">Delete</button>
                			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                			<input type='hidden' id='wasdeleted' name='wasdeleted'>
            			</div>
      				</div>
      			</div>
  			</div>

<!-- Modal -->
  			<div class="modal fade" id="myModalUpdate" role="dialog">
    			<div class="modal-dialog">
    			<!-- Modal content-->
      				<div class="modal-content">
        				<div class="modal-header">
          					<button type="button" class="close" data-dismiss="modal">&times;</button>
          					<h4 class="modal-title">Edit this Goal</h4><input type="hidden"  id="updateid" name="updateid"><input type="hidden"  id="plpid" name="plpid">
          					<br />
				      		<div class="alert alert-danger" role="alert" style="display:none;" id="diverror" name="diverror">
  								<font style="font-weight:bold">Please enter Description</font>
							</div>
        				</div>
        				<div class="modal-body">
          					<p><input type="text" id="txtgoal" name="txtgoal" class="form-control"></p>
        				</div>
        				<div class="modal-footer">
        					<button type="button" class="btn btn-success" id="butdelete" name="butupdate" onclick="updategoaldes();">Save/Update</button>
                			<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
            			</div>
      				</div>
      			</div>
  			</div>

  				</body>
</html>