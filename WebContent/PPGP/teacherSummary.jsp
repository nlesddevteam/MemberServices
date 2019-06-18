<%@ page language="java"
        session="true"
        import="com.awsd.ppgp.*, 
                java.util.*,com.awsd.security.*, 
                java.text.*,com.awsd.personnel.*,
                com.awsd.common.*,com.esdnl.util.*,
                com.esdnl.personnel.v2.model.sds.bean.*"
        isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>

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
		
		<link rel="stylesheet" href="css/summary.css">
		<link rel="stylesheet" href="css/smoothness/jquery-ui.custom.css">
		<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
		<script type="text/javascript" src="js/jquery-ui.min.js"></script>
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/ppgp.js"></script>
		<script language="JavaScript">
			function openWindow(id,url,w,h) {
		       window.open(url,id,"toolbar=0,location=no,top=50,left=50,directories=0,status=0,menbar=0,scrollbars=0,resizable=0,width="+w+",height="+h);
		
		       if (navigator.appName == 'Netscape'){ 
		               popUpWin.focus();
		       }
			}
		
			$('document').ready(function() {
				var msgupdate="<%=request.getAttribute("msgupdate") %>";
				if(!(msgupdate == "null")){
					$("#spansuccess").text(msgupdate);
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
	</head>

	<body topmargin="10" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">

		<table cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td valign="top" align="left">
					<img src="images/teacherplpsummary.png"><BR>
					<%if(ppgp != null){%>
						<div style="padding-left: 10px;
												font-family: Arial, Helvetica, sans-serif; 
												font-size: 20px; 
												font-weight: bold; 
												color: #003399; 
												line-height: 22px;" align="left" >
							<%=ppgp.getSchoolYear()%>
						</div><BR>
					<%}%>
				</td>
				<td valign="top" align="left">
				  <table cellpadding="0" cellspacing="0" border="0">
				    <tr>
				      <td>
				        <b>Name:</b>
				      </td>
				      <td>
				        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=p.getFullName()%>
				      </td>
				    </tr>
				    <%if(roles.containsKey("TEACHER") || roles.containsKey("PRINCIPAL") || roles.containsKey("VICE PRINCIPAL")){%>
				      <tr>
				        <td>
				          <b>School:</b>
				        </td>
				        <td>
				          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=p.getSchool().getSchoolName()%>
				        </td>
				      </tr>
				    <%}%>
				    
				    <tr>
				      <td>
				        <b>Date:</b>
				      </td>
				      <td>
				        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=(new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime())%>
				      </td>
				    </tr>
				  </table>
				</td>
			</tr>
		</table>

		<table width="95%" cellpadding="2" cellspacing="0" border="0" align="center" class="infotable">
				<tr>
				    <td colspan="6" valign="middle"  align="middle">
				      	<div class="alert alert-success" role="alert" style="display:none;" id="divsuccess">
  							<font style="font-weight:bold"><span id="spansuccess"></span></font>
						</div>
				    </td>
				 </tr>
				 <tr>
				    <td colspan="6" valign="middle"  align="middle">
				      	<div class="alert alert-danger" role="alert" style="display:none;" id="divdanger">
  							<font style="font-weight:bold">No goals added for this PLP. Please click "Return to Learning Plan" to add a goal. Minimum two goals required.</font>
						</div>
				    </td>
				</tr>
				<tr>
				    <td colspan="6" valign="middle"  align="middle">
				      	<div class="alert alert-warning" role="alert" style="display:none;" id="divwarning">
  							<font style="font-weight:bold">One goal added for this PLP. Please click Return to Learning Plan to add a goal. Minimum two goals required.</font>
						</div>
				    </td>
				</tr>
				<tr>
				    <td colspan="6" valign="middle"  align="middle">
				      	<div class="alert alert-success" role="alert" style="display:none;" id="divcomplete">
  							<font style="font-weight:bold">Two goals(or more) added for this PLP.  Minimum required goals completed.</font>
						</div>
				    </td>
				</tr>
			<%if((ppgp != null)){%>
				<tr>
					<td cospan='6'>
					<input type="hidden" name="pid" id="pid" value="<%=ppgp.getPersonnel().getPersonnelID()%>" />
					<input type="hidden" name="totalgoals" id="totalgoals" value="<%=ppgp.entrySet().size()%>" />
					</td>
				</tr>

				</table>
				
				<%if((ppgp.entrySet().size() >= 1)){
			  		for(Map.Entry<Integer, PPGPGoal> entry : ppgp.entrySet()) {
				    goal = entry.getValue();%>
				    <table width="95%" cellpadding="2" cellspacing="0" border="0" align="center" class="infotable">
				  	<tr>
				      <td colspan="<%=(editable)?6:5%>" bgcolor="#0066CC" valign="top">
				        <table>
				          <tr>
				            <td>
				              <span class="title2">Goal:&nbsp;</span>
				            </td>
				            <td colspan="4">
				              <span class="title2"><%=goal.getPPGPGoalDescription()%></span>
				            </td>
				          </tr>
				        </table>
				      </td>
				    </tr>
				    
				    <%if(editable){%>
					    <tr>
					      <td colspan="6" bgcolor="#F4F4F4" valign="middle">
					        <span class="title">Tasks &amp; Activities</span>&nbsp;&nbsp;[<a href="addGrowthPlanTask.html?gid=<%=goal.getPPGPGoalID()%>&ppgpid=<%=ppgp.getPPGPID()%><%=(bigbrother)?"&pid="+p.getPersonnelID():""%>">ADD TASK</a>
					        &nbsp;|&nbsp;<a href="#" onclick="showdeletegoal('<%=goal.getPPGPGoalID()%>','<%=ppgp.getPPGPID()%>','<%=goal.getPPGPGoalDescription().replaceAll("\\r\\n|\\r|\\n", " ").replaceAll("'"," ")%>',this);">REMOVE GOAL</a>| 
					        <a href="#" onclick="showupdategoal('<%=goal.getPPGPGoalID()%>','<%=goal.getPPGPGoalDescription().replaceAll("\\r\\n|\\r|\\n", " ").replaceAll("'"," ")%>','<%=ppgp.getPPGPID()%>');">EDIT GOAL</a>]<BR>
					      </td>
					    </tr>
				    <%}%>
				    <tr>
				      <td>
				        <table width="100%" cellpadding="1" cellspacing="1" border="0" class="infotable">
				          <tr>
				            <td width="20%" bgcolor="#F4F4F4" valign="middle" align="center" rowspan='2'>
				              <span class="title">Strategy</span>
				            </td>
				            <td width="20%" bgcolor="#F4F4F4" valign="middle" class="title" style='text-align:center;' align="center" colspan='2'>
				              Resources/Support
				            </td>
				            
				            <td width="20%" bgcolor="#F4F4F4" valign="middle" class="title" style='text-align:center;' align="center" colspan='2'>
				              Technology
				            </td>
				            <td width="10%" bgcolor="#F4F4F4" valign="middle" align="center" rowspan='2'>
				              <span class="title">Completion Date</span>
				            </td>
				            <td width="20%" bgcolor="#F4F4F4" valign="middle" align="center" rowspan='2'>
				              <span class="title">Self Evaluation</span>
				            </td>
				            <%if(editable){%>
					            <td bgcolor="#F4F4F4" valign="middle" align="center" rowspan='2'>
					              &nbsp;
					            </td>
				            <%}%>
				          </tr>
				          
				          <tr>
				          	<td width="10%" bgcolor="#F4F4F4" valign="middle" class="title" style='text-align:center;'align="center">
				              School
				            </td>
				          	<td width="10%" bgcolor="#F4F4F4" valign="middle" class="title" style='text-align:center;'align="center">
				              District
				            </td>
				            <td width="10%" bgcolor="#F4F4F4" valign="middle" class="title" style='text-align:center;'align="center">
				              School
				            </td>
				          	<td width="10%" bgcolor="#F4F4F4" valign="middle" class="title" style='text-align:center;'align="center">
				              District
				            </td>
				          </tr>
									<%if(goal.entrySet().size() < 1){%>
				            <tr><td colspan="<%=(editable)?6:5%>" style="padding-left:10px;"><font style="color:#FF0000;font-weight:bold">No tasks added for this goal</font></td></tr>
									<%} else {
				            for(Map.Entry<Integer, PPGPTask> g_entry : goal.entrySet()){
				              task = g_entry.getValue(); %>
				            	<tr>
				                <td width="20%" bgcolor="#E1E1E1" valign="top">
				                  <%=task.getDescription()%><br>
				                </td>
				                <td width="10%" bgcolor="#E1E1E1" valign="top">
				                  <%=task.getSchoolSupport()%>
				                </td>
				                <td width="10%" bgcolor="#E1E1E1" valign="top">
				                  <%=task.getDistrictSupport()%>
				                </td>
				                <td width="10%" bgcolor="#E1E1E1" valign="top">
				                	<%=!StringUtils.isEmpty(task.getTechnologySchoolSupport())?task.getTechnologySchoolSupport():""%>
				                </td>
				                <td width="10%" bgcolor="#E1E1E1" valign="top">
				                	<%=!StringUtils.isEmpty(task.getTechnologyDistrictSupport())?task.getTechnologyDistrictSupport():""%>
				                </td>
				                <td width="10%" bgcolor="#E1E1E1" valign="top">
				                  <%=task.getCompletionDate()%>
				                </td>
				                <td width="20%" bgcolor="#E1E1E1" valign="top">
				                  <%=!StringUtils.isEmpty(task.getSelfEvaluation())?task.getSelfEvaluation():""%>
				                </td>
				                <%if(editable){%>
					                <td width="20%" bgcolor="#E1E1E1" valign="top">
					                  
					                        <a href="editGrowthPlanTask.html?tid=<%=task.getTaskID()%>&gid=<%=goal.getPPGPGoalID()%>&ppgpid=<%=ppgp.getPPGPID()%><%=(bigbrother)?"&pid="+p.getPersonnelID():""%>">EDIT</a>
<br>
					                          <a href="#" onclick="showdeletetask('<%=task.getTaskID()%>','<%=goal.getPPGPGoalID()%>','<%=task.getDescription().replaceAll("'"," ")%>',this);">REMOVE</a>

					                </td>
				                <%}%>
				              </tr>
										<%}%>
				         	<%}%>			        
				        </table>
				      </td>
				    </tr>
				    <tr>
				      <td colspan="<%=(editable)?6:5%>" bgcolor="#F4F4F4" valign="middle">
				        &nbsp;
				      </td>
				    </tr>
				    </table>
				<%} %>	
				<%} %>	
			<%}else{%>
				<table width="95%" cellpadding="2" cellspacing="0" border="0" align="center">
				<tr><td>Choose a PGP from the Growth Plan Archive to view.</td></tr>
				</table>
			<%}%>
		

		<BR /><BR />

		<%if((ppgp != null) && editable && !bigbrother){%>
		  <table cellpadding="0" cellspacing="0" border="0" align="center">
		    <tr>
		      <td align="center" width="100%" valign="bottom">		      	
		        	<img src="images/return-off.png"
			          onclick="self.location.href='viewGrowthPlan.html?sy=<%=ppgp.getSchoolYear()%>'"; /><BR />
		      </td>
		    </tr>
		  </table>
		  <BR /><BR />
		<%}%>

		<table width="90%" cellpadding="0" cellspacing="0" border="0" align="center">
			<tr>
				<td width="100%" align="center" valign="bottom" bgcolor="#FFCC00" style='padding:0px;'>
					<img src="images/spacer.gif" width="1" height="5"><BR />
				</td>
			</tr>
		</table>
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
        					<button type="button" class="btn btn-primary" id="butdelete" name="butdelete">Delete</button>
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
          					<h4 class="modal-title">Edit Goal</h4><input type="hidden"  id="updateid" name="updateid"><input type="hidden"  id="plpid" name="plpid">
          					<br />
				      		<div class="alert alert-danger" role="alert" style="display:none;" id="diverror" name="diverror">
  								<font style="font-weight:bold">Please enter Description</font>
							</div>
        				</div>
        				<div class="modal-body">
          					<p><input type="text" id="txtgoal" name="txtgoal" style="width:300px;max-width:300px"></p>
        				</div>
        				<div class="modal-footer">
        					<button type="button" class="btn btn-primary" id="butdelete" name="butupdate" onclick="updategoaldes();">Submit</button>
                			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            			</div>
      				</div>
      			</div>
  			</div>

  				</body>
</html>