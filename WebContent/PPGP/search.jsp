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
<script>
    $("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold; font-size:12px;}
.tableResult {font-weight:normal;}
.tableTitleWide {column-span: all;}
.tableTitleL {font-weight:bold;font-size:12px;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;font-size:12px;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>			
  </head>

  <body>  
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>Professional Learning Plan Search System</b><br/>
	               	<b>Date:</b> <%=(new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime())%></div>
      			 	<div class="panel-body"> 
					
				   <span class="no-print">To search the entire PLP database, please enter your search criteria below and press Start Search. Please be patient as search may take awhile. Search results may not be limited to just your school or school family.</span>
	               	
   <br/><br/>
    <form name="searchform" action="searchPGP.html" method="post" onsubmit="return checkrequired()">
	  	    	            
	      <%if(request.getAttribute("msg") != null){%>
	        <div class="alert alert-danger" align="center"><%=(String)request.getAttribute("msg")%></div>
	      <%}%>
	      
	      <input type="hidden" name="op" value="START_SEARCH">
	      
	      <table class="table table-condensed no-print" style="font-size:11px;">							   
					<thead>
					<tr>
						<th colspan=4 style="background-color:#0066cc;font-size:14px;font-weight:bold;color:White;text-align:center;">PLP SEARCH CRITERIA</th>
					</tr>
	      			</thead>
	      			<tbody>
	      			<tr>
	      			<td class="tableTitleL">SCHOOL YEAR:</td>
	      			<td class="tableResultL"><pgp:AllPGPYears id="year" cls="form-control" value='<%=(request.getAttribute("year")!=null)?(String)request.getAttribute("year"):""%>' /></td>
	      			<td class="tableTitleR">REGION:</td>
	      			<td class="tableResultL">
	      			
	      			<select name="region" id="region" class="form-control" placeholder="Please Select a Region">	
	      			<option style="text-transform:capitalize;" value="5" selected>All Regions</option>      			
					<optgroup style="text-transform:uppercase;" label="Avalon Region">
					<!--<option style="text-transform:capitalize;" value="5">all eastern region</option>-->
					<option style="text-transform:capitalize;" value="1">avalon east</option>
					<option style="text-transform:capitalize;" value="2">avalon west</option>
					</optgroup>
					<optgroup style="text-transform:uppercase;" label="Central Region">
					<!-- <option style="text-transform:capitalize;" value="13">all central region</option>-->
					<option style="text-transform:capitalize;" value="4">burin Zone</option>
					<option style="text-transform:capitalize;" value="15">central Zone</option>
					<option style="text-transform:capitalize;" value="3">vista Zone</option>
					</optgroup>
					<optgroup style="text-transform:uppercase;" label="Western Region">
					<!--<option style="text-transform:capitalize;" value="12">all western region</option>-->
					<option style="text-transform:capitalize;" value="10">central Zone</option>
					<option style="text-transform:capitalize;" value="17">green bay / white bay Zone</option>
					<option style="text-transform:capitalize;" value="9">northern Zone</option>
					<option style="text-transform:capitalize;" value="11">southern Zone</option>
					</optgroup>
					<optgroup style="text-transform:uppercase;" label="Labrador Region">
					<!--<option style="text-transform:capitalize;" value="14">all labrador region</option>-->
					<option style="text-transform:capitalize;" value="8">Coastal Zone</option>
					<option style="text-transform:capitalize;" value="6">Eastern Zone</option>
					<option style="text-transform:capitalize;" value="7">Western Zone</option>
					</optgroup>
					<optgroup style="text-transform:uppercase;" label="Provincial">
					<option style="text-transform:capitalize;" value="16">Provincial</option>
					</optgroup>
					</select>
	      			
	      			</tr>
	      			<tr>
	      			<td class="tableTitleL">KEYWORDS:</td>
	      			<td colspan=3><input type="text" name="keywords" id="keywords" class="form-control" value='<%=(request.getAttribute("keywords")!=null)?(String)request.getAttribute("keywords"):""%>'></td>	      			
	      			</tr>
	      			</tbody>
	      			</table>
	      			
	      	<div id="divnew"  class="no-print" style="display:none;">
			      	<table class="table table-condensed" style="font-size:11px;">
			      	<tbody>
	      			<tr>
	      			<td class="tableTitleL">DOMAIN:</td>
	      			<td colspan="3">
	      								<select id="selectdomain" name="selectdomain" class="form-control">
							            				<option value="0">SELECT DOMAIN</option>
							            				<c:forEach items="${domains}" var="itemrow">
   															<option value="${ itemrow.domainID}">${ itemrow.domainTitle}</option>
  														</c:forEach> 
							            </select>
	      			</td>	      			
	      			</tr>
			      	</tbody>
			      	</table>
	      	
		      		<div id="divstrength">
		      			<table class="table table-condensed no-print" style="font-size:11px;">
		      			<tbody>
		      			<tr>
		      			<td class="tableTitleL">STRENGTH:</td>
		      			<td colspan=3>
		      								<select id="selectstrength" name="selectstrength" class="form-control">
					            							<option value="0">SELECT STRENGTH</option>
					            							<%if (!(request.getAttribute("strengths") == null)){ %>
					            									<c:forEach items="${strengths}" var="itemrow">
																					<option value="${ itemrow.strengthID}">${ itemrow.strengthTitle}</option>
																	</c:forEach> 
					            							<%} %>
								            </select>
		      			
		      			</td>	      			
		      			</tr>
		      			</tbody>
		      			</table>
		      		</div>
	      			
	      	</div>
	      
	      	
	      	<div id="divoriginal" style="display:none;">
	      	 		<table class="table table-condensed no-print" style="font-size:11px;">
			      	<tbody>
	      			<tr>
	      			<td class="tableTitleL">CATEGORY:</td>
	      			<td colspan=3>
	      									<select id='cat_id' name='cat_id' onchange="cat_id_change();" class="form-control">
							            		<option value="0">SELECT CATEGORY</option>
							                	<c:forEach items="${cats}" var="itemrow">
   													<option value="${ itemrow.value.categoryID}">${ itemrow.value.categoryTitle}</option>
  												</c:forEach> 
							                </select>
					</td>	      			
	      			</tr>
	      			</tbody>
	      			</table>
	      			
	      		<div id="divgrade">	   	
	      	        <table class="table table-condensed no-print" style="font-size:11px;">
			      	<tbody>
	      			<tr>
	      			<td class="tableTitleL">GRADE:</td>
	      			<td colspan=3>
		      			<select id='grade_id' name='grade_id' onchange="grade_id_change();" class="form-control">
							<option value="0">SELECT GRADE</option>
							<c:forEach items="${grades}" var="itemrow">
		   					<option value="${ itemrow.value.gradeID}">${ itemrow.value.gradeTitle}</option>
		  					</c:forEach> 
						 </select>
	      			</td>	      			
	      			</tr>
	      			</tbody>
	      			</table>
	      		</div>
	      		
	      		<div id="divsubject">
	      			<table class="table table-condensed no-print" style="font-size:11px;">
			      	<tbody>
	      			<tr>
	      			<td class="tableTitleL">SUBJECT AREA:</td>
	      			<td colspan=3>
	      			<select id='subject_id' name='subject_id' onchange="subject_id_change();" class="form-control">
	      			<%if (!(request.getAttribute("subjects") == null)){ %>
						
						<c:forEach items="${subjects}" var="itemrow">
   						<option value="${ itemrow.subjectID}">${ itemrow.subjectTitle}</option>
  						</c:forEach> 
  						
						<%}%>
					</select>	
					</td>	      			
	      			</tr>
	      			</tbody>
	      			</table>
	      		</div>	
	      			
	      		<div id="divtopic">
	      			<table class="table table-condensed no-print" style="font-size:11px;">
			      	<tbody>
	      			<tr>
	      			<td class="tableTitleL">TOPIC AREA:</td>
	      			<td colspan=3>
	      									<select id='topic_id' name='topic_id' onchange="topic_id_change();" class="form-control">
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
	      			</tbody>
	      			</table>
	      		</div>	
	      		<div id="divstopic">
	      			<table class="table table-condensed no-print" style="font-size:11px;">
			      	<tbody>
	      			<tr>
	      			<td class="tableTitleL">SPECIFIC TOPIC:</td>
	      			<td colspan=3>
	      									<select id='stopic_id' name='stopic_id' class="form-control">
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
	      			</tbody>
	      			</table>
	      		</div>	
	      	</div>		
	 	          	
							          		
							      
							    
									<div class="alert alert-danger" role="alert" style="display:none;text-align: center;" id="diverror" name="diverror">
  									<span id="spanerror" name="spanerror"></span>
									</div>
								
							  
	                          <div align="center"><input type="submit" value="Start Search" class="no-print btn btn-xs btn-primary"> <a href="policy.jsp" class="no-print btn btn-xs btn-danger">Cancel</a></div>
	                           
	       
	       
	       
	       
	      <%if((results != null) && !results.isEmpty()) {%>
	             <br/><br/>     
		       <div class="alert alert-success" align="center">            
		      	<b>***** SEARCH SUCCESSFUL *****</b><br/><%=results.getPGPCount()%> PGP(s) found matching your criteria! Results are displayed below broken down by Region and School.<br/>
		      	
		      					<%if(!StringUtils.isEmpty(results.getSchoolYear())){%>
		                      		<b>School Year:</b> <%=results.getSchoolYear()%>, 
		                      	<%}%>
		                      	<%if(results.getRegion() != null){%>
		                      		<b>Region:</b> <span style="text-transform:capitalize;"><%=results.getRegion().getName()%></span>
		                      	<%}%>	
		                        <%if(!StringUtils.isEmpty(results.getKeywords())){%>
		                          <br/><b>Keyword(s):</b> <%=results.getKeywords()%>
		                        <%}%>
		                        <%if(results.getCategory() != null){%>
		                          <br/><b>Category:</b> <%=results.getCategory().getCategoryTitle()%>, 
		                        <%}%>
		                        <%if(results.getGrade() != null){%>
		                          <b>Grade:</b> <%=results.getGrade().getGradeTitle()%>, 
		                        <%}%>
		                        <%if(results.getSubject() != null){%>
		                          <b>Subject:</b> <%=results.getSubject().getSubjectTitle()%>,
		                        <%}%>
		                        <%if(results.getTopic() != null){%>
		                          <b>Topic:</b> <%=results.getTopic().getTopicTitle()%>,
		                        <%}%>
		                        <%if(results.getSpecificTopic() != null){%>
		                          <b>Specific Topic:</b> <%=results.getSpecificTopic().getSpecificTopicTitle()%>
		                        <%}%>
		                        <%if(results.getDomain() != null){%>
		                         <br/><b>Domain:</b> <%=results.getDomain().getDomainTitle()%>, 
		                        <%}%>
		                        <%if(results.getStrength() != null){%>
		                          <b>Strength:</b> <%=results.getStrength().getStrengthTitle()%>
		                        <%}%>
		      	
		      			</div> 
		      	
		      	<div align="center" class="no-print">
		      	Page: 
		                  <%
		                    if(page_num > 1)
		                    {
		                  %> 
		                   <a class='btn btn-xs btn-default' href='search.jsp?page=<%=page_num-1%>' class="navigation_small">Prev</a>
		                  <%} 		                  
		                    for(int i = 1; i <= results.getPageCount(); i++) 
		                    { 
		                      if(i == page_num)
		                      {
		                  %>    <a href="#" class='btn btn-xs btn-info'><%=i%></a>
		                  <%  }
		                      else
		                      {
		                  %>    <a class='btn btn-xs btn-default' href='search.jsp?page=<%=i%>' class="navigation_small"><%=i%></a>
		                  <% }}
		                    
		                    if(page_num < results.getPageCount())
		                    {
		                  %>  
		                    <a class='btn btn-xs btn-default' href='search.jsp?page=<%=page_num+1%>' class="navigation_small">Next</a>
		                  <%}%>
		                  
		           </div>    
	            
		            <%for(SearchResult item : results.getPage(page_num)) {
		              	if(!item.getRegion().equals(current_region)) {
		              		current_region = item.getRegion(); %>
		              		
		              		<div class="pagebreakb4">&nbsp;</div>
		              		<table class="table table-condensed table-bordered" style="font-size:12px;">
		              		<tbody>
		              		<tr style="background-color:#006400;color:White;font-weight:bold;font-size:16px;">
		              		<td><span style="text-transform:capitalize;">
		              		<% try { %>
		              			<%=item.getPGP().getPersonnel().getSchool().getZone().getZoneName()!=null?item.getPGP().getPersonnel().getSchool().getZone().getZoneName():"N/A" %> Region
		              		<%} catch(Exception e) { %>
		              		 	<span style="color:White;background-color:Red;"><b>ERROR:</b> Region Not Set for some results.</span>
		              		 <%} %>
		              		<% try { %>		              		
		              			(<%=current_region.getName()!=null?current_region.getName():"N/A"%> Zone)
		              		<% } catch(Exception e) { %>
                            	<span style="color:White;background-color:Red;"><b>ERROR:</b> Regional Zone Not Set for some results.</span>
							<%	} %>
		              		</span></td>
		              		</tr>
		              		</tbody>
		              		</table>
		              		
		              	<%}
		                ppgp = item.getPGP();
		                int cntrg=0;
		                %>
		             
		             
		             
		             <table class="table table-condensed table-bordered" style="font-size:12px;">							   
					<tbody>
		            <tr style="background-color:#000000;font-size:14px;color:White;font-weight:bold;">
		            <td colspan=4><%=ppgp.getPersonnel().getFullName()%> - <%= ppgp.getPersonnel().getSchool().getSchoolName()%> (<%=ppgp.getSchoolYear()%>)</td>
		            </tr>
		             
						            	<%for(Map.Entry<Integer, PPGPGoal> entry : ppgp.entrySet()) {
						              		goal = entry.getValue(); 
						              		cntrg++;%>    
						                	
						                	
											<tr style="background-color:#0066cc;font-size:12px;color:White;">					
										    <td colspan=4>
										    <b>GOAL #<%=cntrg%>:</b><br/><%=goal.getPPGPGoalDescription()%>
										    </td>
										    </tr>
						                	
						                	<%int cntrt=0; %>
						                	
						            			<%for(Map.Entry<Integer, PPGPTask> g_entry : goal.entrySet()) {
							                    task = g_entry.getValue();
							                    cntrt++;%>
							                     <tr class="info">
							                     <td colspan=4>
							                          <%=(task.getCategory() != null)?"<b>Category: </b>"+task.getCategory().getCategoryTitle()+"&nbsp;&nbsp;":""%>
							                          <%=((task.getGrade() != null)&&(task.getGrade().getGradeID() > 0))?"<b>Grade: </b>"+task.getGrade().getGradeTitle():""%>
							                          <%=((task.getSubject() != null)&&(task.getSubject().getSubjectID() > 0))?"<br/><b>Subject:</b>"+task.getSubject().getSubjectTitle()+"&nbsp;&nbsp;":""%>
							                          <%=(task.getTopic() != null)?"<b>Topic: </b>"+task.getTopic().getTopicTitle()+"&nbsp;&nbsp;":""%>
							                          <%=(task.getSpecificTopic() != null)?"<b>Specific Topic: </b>"+task.getSpecificTopic().getSpecificTopicTitle():""%>
							                          <%=(task.getDomain() != null)?"<b>Domain: </b>"+task.getDomain().getDomainTitle()+"&nbsp;&nbsp;":""%>
							                          <%=(task.getStrength() != null)?"<b>Strength: </b>"+task.getStrength().getStrengthTitle():""%>
							                    </td>
							                    </tr>
							                   <tr class="warning">
													    <td colspan=4><b>TASK/STRATEGY #<%=cntrt%>:</b><br/><%=task.getDescription()%></td>
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
													 	<%=task.getSelfEvaluation() !=null?task.getSelfEvaluation():"<div class='alert alert-danger' align='center'><span style='text-transform:Capitalize;font-weight:bold;'>"+ppgp.getPersonnel().getFullNameReverse()+"</span> has not yet completed the self evaluation for this Goal. Completion Date may not have yet passed.</div>"%>
													 	</td>
													 	</tr>
																	
							                    
							                    
							                    
							                    
							                    
							                     
							                    
						            			
						            			<%}%>
						            			
						            			
						            			
						              <%}%>
				              
		                </tbody>
				       </table>
				       <div class="pagebreak"></div>
		            <%}%>
	          <div align="center" class="no-print">
		      	Page: 
		                  <%
		                    if(page_num > 1)
		                    {
		                  %> 
		                   <a class='btn btn-xs btn-default' href='search.jsp?page=<%=page_num-1%>' class="navigation_small">Prev</a>
		                  <%} 		                  
		                    for(int i = 1; i <= results.getPageCount(); i++) 
		                    { 
		                      if(i == page_num)
		                      {
		                  %>    <a href="#" class='btn btn-xs btn-info'><%=i%></a>
		                  <%  }
		                      else
		                      {
		                  %>    <a class='btn btn-xs btn-default' href='search.jsp?page=<%=i%>' class="navigation_small"><%=i%></a>
		                  <% }}
		                    
		                    if(page_num < results.getPageCount())
		                    {
		                  %>  
		                    <a class='btn btn-xs btn-default' href='search.jsp?page=<%=page_num+1%>' class="navigation_small">Next</a>
		                  <%}%>
		                  
		           </div>    
	          
	      <%} else {%>
	      	<% if(!(request.getAttribute("sreturn") == null)) {%>
	      				<br/><br/>
	          			<div class="alert alert-danger no-print" role="alert" style="text-align: center;" id="divnoresults" name="divnoresults">
  							Sorry, no results found. Please try again.
						</div>
	        		
	      		<%} %>
	      <%} %>    
	  
    </form>
     </div></div></div>
    
  </body>
</html>