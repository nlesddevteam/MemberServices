<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.ppgp.*,com.awsd.security.*,com.awsd.school.*,com.awsd.school.bean.*,com.awsd.personnel.*,com.esdnl.util.*,
                java.text.*"
        isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<esd:SecurityCheck permissions="PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST" />

<%
  User usr = (User) session.getAttribute("usr");
  SearchResults results = (SearchResults) session.getAttribute("SEARCH-RESULTS");
  int page_num;
  
  PPGP ppgp = null;
  PPGPGoal goal = null;
  PPGPTask task = null;
  
  RegionBean current_region = null;
 
  if(request.getParameter("page")!= null)
    page_num = Integer.parseInt(request.getParameter("page"));
  else
    page_num = 1;
    
 
%>

<html>

  <head>
    <title>PGP Search</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    	<link rel="stylesheet" href="css/summary.css">
		<link rel="stylesheet" href="css/smoothness/jquery-ui.custom.css">
		<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="js/CalendarPopup.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/ppgp.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript">
			$('document').ready(function() {
				$(document).on('change','#selectdomain',function(){
					selectdomain_change();
				});
				$(document).on('change','#year',function(){
					changeyears();
				});
				$("#divgrade").hide();
				$("#divsubject").hide();
				$("#divtopic").hide();
				$("#divstopic").hide();
				$("#divstopic").hide();
				$("#divstrength").hide();
				var selectedyear = $("#year").val();
				//alert(selectedyear);
				var arr = selectedyear.split("-");
				var test = parseInt(arr[1],10);
				if(test > 2017){
					$("#divnew").show();
					$("#divoriginal").hide();
					$("#spantitle").text("Domains and Strengths");
					
				}else{
					$("#divnew").hide();
					$("#divoriginal").show();
					$("#spantitle").text("Categories");
				}

			});
		
		</script>
  </head>

  <body topmargin="10" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">    
    <form name="searchform" action="searchPGP.html" method="post" onsubmit="return checkrequired()">
	    <table align="center" width="95%" cellpadding="0" cellspacing="0" border="0">
	      <tr>
	        <td width="33%" valign="top" align="left">
	         <a href='viewGrowthPlanProgramSpecialistSummary.html'><img src="images/progspeclpsummary.png" border='0' /></a><BR />
	        </td>
	        <td width="33%" valign="middle" align="left">
	          <table align="center">
	            <tr>
	              <td>
	                <b>Name:</b>
	              </td>
	              <td>
	                <%=usr.getPersonnel().getFullName()%>
	              </td>
	            </tr>
	            <tr>
	              <td>
	                <b>Date:</b>
	              </td>
	              <td>
	                <%=(new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime())%><BR>
	              </td>
	            </tr>
	          </table>
	        </td>
	        <td width="*" align="right">
	          <table align='right'>
	            <tr>
	              <td align="center">
	                <a href="searchPGP.html"><img src="images/search-off.png" border="0" alt="PGP Search"></a>
	              </td>
	            </tr>
	          </table>
	        </td>
	      </tr>
	      <%if(request.getAttribute("msg") != null){%>
	        <tr style="padding-bottom:10px;">
	          <td colspan='3'>
	            <p style='border:solid 1px #FF0000; color:#ff0000;padding:3px;'>
	              <table cellpadding="0" cellspacing="0">
	                <tr>
	                  <td style="color:#ff0000;"><%=(String)request.getAttribute("msg")%></td>
	                </tr>
	              </table>
	            </p>
	          </td>
	        </tr>
	      <%}%>
	      <tr>
	        <td colspan="3" align="center">
	          <table width="40%" cellpadding="0" cellspacing="0" align='center' border='1' class="search">
	            <tr>
	              <td width="100%">
	                <input type="hidden" name="op" value="START_SEARCH">
	                <fieldset>
	                  <legend><font style="color:#00407A;font-weight:bold;font-size:20px;FONT-FAMILY: T, 'Times New Roman', Times, serif;"><i>PLP Search Criteria</i></font></legend>
	                  <table align="center" width="95%" cellpadding="5" cellspacing="0" border="0">
	                  <!-- TASK CATEGORY -->
	                    <tr>
	                      <td width="100%" valign="middle" align="left" style="padding:0px;">
	                        <table align='center' border="0" class="search">
	                        	<tr>
	                            <td width="100"><span class="subtitle"><b>School Year:</b>&nbsp;&nbsp;</span></td>
	                            <td width="*">
	                              <pgp:AllPGPYears id="year" cls="requiredInputBox" value='<%=(request.getAttribute("year")!=null)?(String)request.getAttribute("year"):""%>' />
	                            </td>
	                          </tr>
	                        	<tr>
	                            <td width="100"><span class="subtitle"><b>Region:</b>&nbsp;&nbsp;</span></td>
	                            <td width="*">
	                              <pgp:Regions id="region" style="width:250px;" cls="requiredInputBox" value='<%=(request.getAttribute("region")!=null)?(String)request.getAttribute("region"):""%>' />
	                            </td>
	                          </tr>
	                          <tr>
	                            <td width="100"><span class="subtitle"><b>Keywords:</b>&nbsp;&nbsp;</span></td>
	                            <td width="*">
	                              <input type="text" name="keywords" id="keywords" style="width:250px;" value='<%=(request.getAttribute("keywords")!=null)?(String)request.getAttribute("keywords"):""%>'>
	                            </td>
	                          </tr>
	                          
	                          
							  
							  <!-- TASK CATEGORY -->
							  <tr>
							    
							    <td colspan='2' align="left">
							    	<div id="divnew" style="display:none;">
							    		<table cellpadding="0" cellspacing="0" width="105%" border="0" class="search">
							        		<tr>
							          			<td width="100"><span class="subtitle"><b>Domain:</b>&nbsp;&nbsp;</span></td>
							          			<td width="*">
							            			<select id="selectdomain" name="selectdomain">
							            				<option value="0">SELECT DOMAIN</option>
							            				<c:forEach items="${domains}" var="itemrow">
   															<option value="${ itemrow.domainID}">${ itemrow.domainTitle}</option>
  														</c:forEach> 
							            			</select>
							          			</td>
							        		</tr>
							        		<tr>
							        			<td colspan='2'>
							        			<div id="divstrength">
							        			<table cellpadding="0" cellspacing="0" width="105%" border="0" class="search">
							        				<tr>
							        						<td width="100"><span class="subtitle"><b>Strength:</b>&nbsp;&nbsp;</span></td>
							          						<td width="*">
							          						
							            						<select id="selectstrength" name="selectstrength">
							            							<option value="0">SELECT STRENGTH</option>
							            							<%if (!(request.getAttribute("strengths") == null)){ %>
							            									<c:forEach items="${strengths}" var="itemrow">
   																				<option value="${ itemrow.strengthID}">${ itemrow.strengthTitle}</option>
  																			</c:forEach> 
							            							<%} %>
							            						</select>
							          						</td>
							        				</tr>
							        			</table>
							        			</div>
							          			</td>
							        		</tr>							        		
							        	</table>
							    	</div>
							      <div id="divoriginal" style="display:none;">
							      <table cellpadding="0" cellspacing="0" width="470" border="0" class="search">
							        <tr>
							          <td width="100"><span class="subtitle"><b>Category:</b>&nbsp;&nbsp;</span></td>
							          <td width="*">
							            	<select id='cat_id' name='cat_id' onchange="cat_id_change();">
							            		<option value="0">SELECT CATEGORY</option>
							                	<c:forEach items="${cats}" var="itemrow">
   													<option value="${ itemrow.value.categoryID}">${ itemrow.value.categoryTitle}</option>
  												</c:forEach> 
							                </select>
							          </td>
							        </tr>
							        <tr>
							        	<td colspan='2'>
							        		<div id="divgrade">
							        		<table cellpadding="0" cellspacing="0" width="105%" border="0" class="search">
							        		<tr>
							    				<td width="100" style="border-top: solid 1px #FFFFFF;"><span class="subtitle"><b>Grade:</b>&nbsp;&nbsp;</span></td>
							            		<td width="*" style="border-top: solid 1px #FFFFFF;">
							              		<select id='grade_id' name='grade_id' onchange="grade_id_change();">
							            		<option value="0">SELECT GRADE</option>
							                	<c:forEach items="${grades}" var="itemrow">
   													<option value="${ itemrow.value.gradeID}">${ itemrow.value.gradeTitle}</option>
  												</c:forEach> 
							                </select>
							            		</td>
							        		</tr>
							        		</table>
							        		</div>
										</td>
							          </tr>
							          <tr>
							          	<td colspan='2'>
							          		<div id="divsubject">
							          		<table cellpadding="0" cellspacing="0" width="105%" border="0" class="search">
							          			<tr>
							          				<td width="100" style="border-top: solid 1px #FFFFFF;"><span class="subtitle"><b>Subject Area:</b>&nbsp;&nbsp;</span></td>
							              			<td width="*" style="border-top: solid 1px #FFFFFF;">
							                			<select id='subject_id' name='subject_id' onchange="subject_id_change();">
							                				<%if (!(request.getAttribute("subjects") == null)){ %>
							            						<c:forEach items="${subjects}" var="itemrow">
   																	<option value="${ itemrow.subjectID}">${ itemrow.subjectTitle}</option>
  																</c:forEach> 
							            					<%} %>
							                			
							                			</select>
							              			</td>
							            		</tr>
							          		</table>
							          		</div>
							          	</td>
							          </tr>
							          <tr>
							          	<td colspan='2'>
							          		<div id="divtopic">
							          			<table cellpadding="0" cellspacing="0" width="105%" border="0" class="search">
							          				<tr>
							          					<td width="100" style="border-top: solid 1px #FFFFFF;"><span class="subtitle"><b>Topic Area:</b>&nbsp;&nbsp;</span></td>
							                			<td width="*" style="border-top: solid 1px #FFFFFF;">
							                  				<select id='topic_id' name='topic_id' onchange="topic_id_change();">
							                  					<%if (!(request.getAttribute("topics") == null)){ %>
							                  						<OPTION VALUE="0">SELECT SPECIFIC TOPIC AREA</OPTION>
																	<OPTION VALUE="1" SELECTED>Other</OPTION>
							                  						<c:forEach items="${topics}" var="itemrow">
   																		<option value="${ itemrow.topicID}">${ itemrow.topicTitle}</option>
  																	</c:forEach> 
							            						<%} %>
							                  				</select>
							                			</td>
							          				</tr>
							          			</table>
							          		</div>
							          	</td>
							          </tr>	
							          <tr>
							          	<td colspan='2'>
							          		<div id="divstopic">
							          			<table cellpadding="0" cellspacing="0" width="105%" border="0" class="search">
							          				<tr>
							          					<td width="100" style="border-top: solid 1px #FFFFFF;"><span class="subtitle"><b>Specific Topic:</b>&nbsp;&nbsp;</span></td>
							                  			<td width="*" style="border-top: solid 1px #FFFFFF;">
							                    		<select id='stopic_id' name='stopic_id'>
							                    			<%if (!(request.getAttribute("stopics") == null)){ %>
							                    					<OPTION VALUE="0">SELECT SPECIFIC TOPIC AREA</OPTION>
																	<OPTION VALUE="1" SELECTED>Other</OPTION>
							                    					<c:forEach items="${stopics}" var="itemrow">
   																		<option value="${ itemrow.specificTopicID}">${ itemrow.specificTopicTitle}</option>
  																	</c:forEach> 
							            					<%} %>
							                    		</select>
							                  			</td>
							          				</tr>
							          			</table>
							          		</div>
							          	</td>
							          </tr>
							      </table>
							      </div>
							      
							    </td>
							  
							  </tr>
							<tr>
								<td colspan='2' align="center">
									<div class="alert alert-danger" role="alert" style="display:none;text-align: center;height:50%;" id="diverror" name="diverror">
  										<font style="font-weight:bold"><span id="spanerror" name="spanerror"></span></font>
									</div>
								</td>
							</tr>
							  
	                          
	                          <tr>
	                            <td colspan="2" align="center">
	                              <input type="submit" value="Start Search">
	                            </td>
	                          </tr>
	                        </table>
	                      </td>
	                    </tr>
	                  </table>
	                </fieldset>
	              </td>
	            </tr>
	          </table>
	        </td>
	      </tr>
	      <tr>
	        <td colspan="3">
	          <img src="images/spacer.gif" width="1" height="10" /><BR />
	        </td>
	      </tr>
	      <tr>
	        <td colspan="3" bgcolor="#FFCC00">
	          <img src="images/spacer.gif" width="1" height="5" /><BR />
	        </td>
	      </tr>
	      <%if((results != null) && !results.isEmpty()){%>
	        <tr>
	          <td colspan="3">
	            <table align="center" width="95%" cellpadding="5" cellspacing="1" border="0">
		            <tr>
		              <td colspan="5">
		                <table  width="100%" cellpadding="0" cellspacing="0" border="0">
		                  <tr>
		                    <td valign="top" width="50">
		                      <span color="#FF0000;"><b>Criteria:</b>&nbsp;</span>
		                    </td>
		                    <td width="*" valign="top" align="left">
		                      <font size="1" style="font-weight:bold;">
		                      	<%if(!StringUtils.isEmpty(results.getSchoolYear())){%>
		                      		School Year: <%=results.getSchoolYear()%><BR />
		                      	<%}%>
		                      	<%if(results.getRegion() != null){%>
		                      		Region: <%=results.getRegion().getName()%><BR />
		                      	<%}%>
		                        <%if(!StringUtils.isEmpty(results.getKeywords())){%>
		                          Keywords: <%=results.getKeywords()%><BR />
		                        <%}%>
		                        <%if(results.getCategory() != null){%>
		                          Category: <%=results.getCategory().getCategoryTitle()%><BR />
		                        <%}%>
		                        <%if(results.getGrade() != null){%>
		                          Grade: <%=results.getGrade().getGradeTitle()%><BR />
		                        <%}%>
		                        <%if(results.getSubject() != null){%>
		                          Subject: <%=results.getSubject().getSubjectTitle()%><BR />
		                        <%}%>
		                        <%if(results.getTopic() != null){%>
		                          Topic: <%=results.getTopic().getTopicTitle()%><BR />
		                        <%}%>
		                        <%if(results.getSpecificTopic() != null){%>
		                          Specific Topic: <%=results.getSpecificTopic().getSpecificTopicTitle()%><BR />
		                        <%}%>
		                        <%if(results.getDomain() != null){%>
		                          Domain: <%=results.getDomain().getDomainTitle()%><BR />
		                        <%}%>
		                        <%if(results.getStrength() != null){%>
		                          Strength: <%=results.getStrength().getStrengthTitle()%><BR />
		                        <%}%>
		                      </font>
		                    </td>
		                  </tr>
		                </table>
		              </td>
		            </tr>
		            
		            <tr>
		              <td colspan="5">
		                <span color="#FF0000;"><b>Results:</b></span> <b><%=results.getPGPCount()%></b> PGP(s) found.<br>
		                <font size="1" style="font-weight:bold;">                  
		                  [Page: 
		                  <%
		                    if(page_num > 1)
		                    {
		                  %>  <Font color="#000000" style="font-weight:bold;">
		                        <a href='search.jsp?page=<%=page_num-1%>' class="navigation_small">Prev</a>
		                      </font>&nbsp;
		                  <%} 
		                  
		                    for(int i = 1; i <= results.getPageCount(); i++) 
		                    { 
		                      if(i == page_num)
		                      {
		                  %>    <Font color="#FF0000" style="font-weight:bold;"><%=i%></font>
		                  <%  }
		                      else
		                      {
		                  %>    <Font color="#000000" style="font-weight:bold;">
		                    <a href='search.jsp?page=<%=i%>' class="navigation_small"><%=i%></a>
		                        </font>
		                  <%  }
		                  %>  &nbsp;
		                  <%
		                    }
		                  
		                    if(page_num < results.getPageCount())
		                    {
		                  %>  <Font color="#000000" style="font-weight:bold;">
		                    <a href='search.jsp?page=<%=page_num+1%>' class="navigation_small">Next</a>
		                      </font>
		                  <%}%>
		                  ]
		                </font>
		              </td>
		            </tr>
	            
		            <%for(SearchResult item : results.getPage(page_num)) {
		              	if(!item.getRegion().equals(current_region)) {
		              		current_region = item.getRegion();
		              		out.println("<tr><td colspan='5' style='font-weight:bold;font-size:11px;color:#FF0000;'>" + current_region.getName() + "</td></tr>");
		              	}
		                ppgp = item.getPGP();%>
		              <tr>
		              	<td colspan="5">
		                	<fieldset style='padding:6px;padding-bottom:15px;'>
		                  	<legend><%=ppgp.getPersonnel().getFullName()%> - <%= ppgp.getPersonnel().getSchool().getSchoolName()%> [<%=ppgp.getSchoolYear()%>]</legend>
					              <table class='pgp-list' align="center" width="95%" cellpadding="4" cellspacing="0" border="0">
						              <tr>
						                <th width="25%"  valign="middle" align="center">
						                  <span class="title">PD Requested</span><BR>
						                </th>
						                <th width="20%"  valign="middle" align="center">
						                  <span class="title">School Support</span><BR>
						                </th>
						                <th width="20%"  valign="middle" align="center">
						                  <span class="title">District Support</span><BR>
						                </th>
						                <th width="15%"  valign="middle" align="center">
						                  <span class="title">Completion Date</span><BR>
						                </th>
						                <th width="20%" valign="middle" align="center">
						                  <span class="title">Self Reflection</span><BR>
						                </th>
						              </tr>
						            	<%for(Map.Entry<Integer, PPGPGoal> entry : ppgp.entrySet()) {
						              		goal = entry.getValue(); %>    
						                	<tr class='goal'>
						                    <td class='first last' colspan="5" valign="top">
						                    	<span class="title2">Goal:&nbsp;<%=goal.getPPGPGoalDescription()%></span>
						                    </td>
						                  </tr>
						            			<%for(Map.Entry<Integer, PPGPTask> g_entry : goal.entrySet()) {
							                    task = g_entry.getValue();%>
							                    <tr class='category'>
							                      <td class='first last' colspan="5" valign="top">
							                        <span class="text">
							                          <%=(task.getCategory() != null)?"<b>Category: </b>"+task.getCategory().getCategoryTitle()+"&nbsp;&nbsp;":""%>
							                          <%=((task.getGrade() != null)&&(task.getGrade().getGradeID() > 0))?"<b>Grade: </b>"+task.getGrade().getGradeTitle()+"&nbsp;&nbsp;":""%>
							                          <%=((task.getSubject() != null)&&(task.getSubject().getSubjectID() > 0))?"<b>Subject:</b>"+task.getSubject().getSubjectTitle()+"&nbsp;&nbsp;":""%>
							                          <%=(task.getTopic() != null)?"<b>Topic: </b>"+task.getTopic().getTopicTitle()+"&nbsp;&nbsp;":""%>
							                          <%=(task.getSpecificTopic() != null)?"<b>Specific Topic: </b>"+task.getSpecificTopic().getSpecificTopicTitle():""%>
							                          <%=(task.getDomain() != null)?"<b>Domain: </b>"+task.getDomain().getDomainTitle():""%>
							                          <%=(task.getStrength() != null)?"<b>Strength: </b>"+task.getStrength().getStrengthTitle():""%>
							                        </span>
							                      </td>
							                    </tr>
							                    <tr class='task'>
							                      <td class='first' width="25%" valign="top">
							                        <span class="text"><%=task.getDescription()%></span>
							                      </td>
							                      <td width="20%" valign="top">
							                        <span class="text"><%=task.getSchoolSupport()%></span>
							                      </td>
							                      <td width="20%" valign="top">
							                        <span class="text"><%=task.getDistrictSupport()%></span>
							                      </td>
							                      <td width="15%" valign="top" align="center">
							                        <span class="text"><%=task.getCompletionDate()%></span>
							                      </td>
							                      <td class='last' width="20%" bgcolor="#F4F4F4" valign="top">
							                        <span class="text"><%=!StringUtils.isEmpty(task.getSelfEvaluation())?task.getSelfEvaluation():""%></span>
							                      </td>
							                    </tr>
						            			<%}%>
						              <%}%>
				              	</table>
		                	</fieldset>
		                </td>
		              </tr>
		            <%}%>
	            </table>     
	          </td>
	        </tr>
	      <%} else {%>
	      	<% if(!(request.getAttribute("sreturn") == null)) {%>
	      		<tr>
	      			<td colspan="3" bgcolor="#FFCC00">
	          			<div class="alert alert-warning" role="alert" style="text-align: center;height:50%;" id="divnoresults" name="divnoresults">
  							<font style="font-weight:bold"><span id="spannoresults" name="spannoresults">No Results Found</span></font>
						</div>
	        		</td>
	      		</tr>
	      		<%} %>
	      <%} %>    
	    </table>
    </form>
  </body>
</html>