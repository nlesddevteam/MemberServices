<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<!-- LOAD JAVA TAG LIBRARIES -->		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>



<%
JobOpportunityBean[] opportunitiesList = (JobOpportunityBean[])request.getAttribute("SEARCH_RESULTS");
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
$("#loadingSpinner").css("display","none");
</script>
</head>

<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
 
 
 <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading">
	               	<span style="font-size:16px;font-weight:bold;">Competition Search Results</span>	               	
	               	
	               	</div>
      			 	<div class="panel-body">
                    <span style="font-size:12px;">  
      			 	Below are a list of competitions matching <b><%=request.getAttribute("term").toString()%></b>.       			 	      			 	
                    </span>
                    <div style="clear:both;">&nbsp;</div>
										<%if(request.getAttribute("msg")!=null){%>
	                                      <div class="alert alert-warning" style="text-align:center;">                                       
	                                          <%=(String)request.getAttribute("msg")%>
	                                        </div>
	                                    <%}%>
	                                 
	                                 
	            <div class="table-responsive"> 
 
 <form action='managePosts.html' method="post">
	<input type="hidden" name="op" value="AWARD" />
	<input type="hidden" name="status" value="<%=request.getParameter("status")%>" />
 
   <div style="clear:both;"></div>        
                    
                    			 <table id='applicant-list' class="table table-striped table-condensed" style="font-size:12px;margin-top:10px;">
								    <thead>
								      <tr style="border-top:1px solid black;">								      	
								        <th width='20%'>COMPETITION NUMBER</th>
								        <th width='50%'>POSITION</th>
								        <th width='20%'>END DATE</th>
								        <th width='10%'>OPTIONS</th>								       
								      </tr>
								    </thead>
								    
								    <tbody>				   
								   
								<%if(opportunitiesList.length > 0){
                                    for(int i=0; i < opportunitiesList.length; i++){%>                                  
                                   	
                                   	<tr>
                                   	<%if (!opportunitiesList[i].isCancelled()) { %> 
                                   	                                 	
                                   	  <td><%=opportunitiesList[i].getCompetitionNumber()%></td>                                                                          
                                      <td><%=opportunitiesList[i].getPositionTitle() %></td>
                                      <td><%=opportunitiesList[i].getFormatedCompetitionEndDate() %></td>                                     	
                                   	                                  	
                                   	<%} else {%>
                                   	  
                                   	  <td style="border-bottom:1px solid #FF0000;border-top:1px solid #FF0000;background-color:#ffe6e6;color:Red;"><%=opportunitiesList[i].getCompetitionNumber()%></td>                                                                          
                                      <td style="border-bottom:1px solid #FF0000;border-top:1px solid #FF0000;background-color:#ffe6e6;color:Red;"><%=opportunitiesList[i].getPositionTitle() %></td>
                                      <td style="border-bottom:1px solid #FF0000;border-top:1px solid #FF0000;background-color:#ffe6e6;color:Red;"><%=opportunitiesList[i].getFormatedCompetitionEndDate() %></td> 
                                   	                                 	
                                   	<%} %>                                  	                                   	                                   	
                                     
                                    <%if (!opportunitiesList[i].isCancelled()) { %>                                	
                                   	<td><a href="view_job_post.jsp?comp_num=<%=opportunitiesList[i].getCompetitionNumber()%>" class="btn btn-xs btn-default"'>VIEW JOB</span></td>
                                    <%} else { %>                                   	
                                   	<td style="border-bottom:1px solid #FF0000;border-top:1px solid #FF0000;background-color:#ffe6e6;color:Red;">CANCELLED</td>
                                    <%} %>         
                                    </tr>
                                 <%}}%>   
								      <tr>
								      <td colspan=5 style="border-top:1px solid black;">&nbsp;</td>
								      </tr>
								          
								    </tbody>
						  		</table>
						
						
						<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-AWARD-MULTIPLE">
	    <input type="submit" value="Award Selected" />
    </esd:SecurityAccessRequired>
						
						  		
					</form>	  		
						  		
							</div>
    
    </div></div>                        
 
 
 </div>
 
 
 
 
  
	
  
                     
    
 
  
 
</body>
</html>
