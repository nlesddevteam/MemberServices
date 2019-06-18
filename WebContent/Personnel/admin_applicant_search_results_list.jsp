<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.util.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<!-- LOAD JAVA TAG LIBRARIES -->		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<%
  ApplicantProfileBean[] applicants = (ApplicantProfileBean[]) request.getAttribute("SEARCH_RESULTS");
%>

<c:set var="today" value="<%=new java.util.Date()%>" />
<fmt:formatDate value="${today}" pattern="yyyy" var="todayYear"/>
<fmt:formatDate value="${today}" pattern="MM" var="todayMonth"/>

<c:set var="totalApplicantsCount" value="0" />
<c:set var="staleApplicantsCount" value="0" />
<c:set var="outdatedApplicantsCount" value="0" />
<c:set var="recentApplicantsCount" value="0" />
<c:set var="okApplicantsCount" value="0" />


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
	               	<span style="font-size:16px;font-weight:bold;">Applicant Search Results</span>	               	
	               	
	               	</div>
      			 	<div class="panel-body">
                    <span style="font-size:12px;">
      			 	Below are a list of <b><%=applicants.length%></b> applicants found with <b>"<%=(String)request.getAttribute("term")%>"</b>. 
      			 	<br/><br/>The rows are color coded to show the status of the applicants profile. 
	               	Any newly modified profile in the last 30 days will be highlighted green. 
	               	If a profile hasnt been updated in 6 months, and has become stale, will be highlighted as yellow. 
	               	Those highlighted red are profiles that have not been modified/updated in the last 3 years. Profiles not highlighted have been updated/modified within the last 6 months and are considered ok. 
	               	<br/><br/><b>By default the outdated applicant profiles are NOT displayed</b>. To show/hide a group of profiles, check/clear checkbox(s) below.
                    </span>
                    <div style="clear:both;">&nbsp;</div>
										<%if(request.getAttribute("msg")!=null){%>
	                                      <div class="alert alert-warning" style="text-align:center;">                                       
	                                          <%=(String)request.getAttribute("msg")%>
	                                        </div>
	                                    <%}%>
	                                 
	                                 
	            <div class="table-responsive"> 
                    
                    
                    <div align="center" style="width:100%;text-align:center;padding-bottom:10px;">
								    
						<div style="float:left;width:25%;background-color:#DFF0D8;font-size:11px;"><input type="checkbox" name="" class="recentList"><div style="padding-top:-3px;">New 1 month (<span class="recentCountVal"></span>)</div></div>
						<div style="float:left;width:25%;background-color:#FFFFFF;font-size:11px;"><input type="checkbox" name="" class="nominalList"><div style="padding-top:-3px;">Nominal 1-5 months (<span class="okCountVal"></span>)</div></div>
						<div style="float:left;width:25%;background-color:#FCF8E3;font-size:11px;"><input type="checkbox" name="" class="staleList"><div style="padding-top:-3px;">Stale 6+ months (<span class="staleCountVal"></span>)</div></div>
						<div style="float:left;width:25%;background-color:#F2DEDE;font-size:11px;"><input type="checkbox" name="" class="outdatedList"><div style="padding-top:-3px;">Outdated 3+ years (<span class="outdatedCountVal"></span>)</div></div>
								    
                    </div>
                    
                     <div style="clear:both;"></div>        
                    
                    			 <table id='applicant-list' class="table table-condensed" style="font-size:12px;background-color:#FFFFFF;margin-top:10px;">
								    <thead>
								      <tr style="border-top:1px solid black;">
								        <th width='20%'>NAME (Last, First)</th>								        
								        <th width='20%'>EMAIL</th>								        
								        <th width='12%'>TELEPHONE</th>
								        <th width='12%'>CELL</th>
								        <th width='12%'>MODIFIED</th>
								        <th width='12%'>TYPE</th>
								        <th width='12%'>OPTIONS</th>
								      </tr>
								    </thead>
								    
								    <tbody>
								   <%if(applicants.length > 0){
                                    for(int i=0; i < applicants.length; i++){%>
                                    
                                    <c:set var="modDate" value="<%=applicants[i].getModifiedDate()%>" />
                                    <c:set var="staleProfile" value="<%=applicants[i].isStale() %>"/>
                                    
                                      <fmt:formatDate value="${modDate}" pattern="yyyy" var="modYear"/>
                                      <fmt:formatDate value="${modDate}" pattern="MM" var="modMonth"/>
                                      <c:choose>
                                       <c:when test="${staleProfile eq 'true'}">
                                      			<c:choose>
                                      			<c:when test="${(modYear le (todayYear -3))}">
                                      			<c:set var="listDisplay" value="outdatedDisplayList"/>
                                      			<c:set var="outdatedApplicantsCount" value="${outdatedApplicantsCount+1}"/>                                     
                                     			<tr class="danger ${listDisplay}">
                                      			</c:when>
                                       			<c:otherwise>
                                       			<c:set var="listDisplay" value="staleDisplayList"/>
                                       			<c:set var="staleApplicantsCount" value="${staleApplicantsCount+1}"/>
                                       			<tr class="warning ${listDisplay}">
                                       			</c:otherwise>
                                       			</c:choose>
                                      </c:when>                                      
                                      <c:when test="${staleProfile eq 'false'}"> 
                                        		<c:choose>
                                        		<c:when test="${(modYear eq todayYear) and (modMonth eq todayMonth)}">
                                        		<c:set var="listDisplay" value="recentDisplayList"/>
                                        		<c:set var="recentApplicantsCount" value="${recentApplicantsCount+1}"/>
                                        		<tr class="success ${listDisplay}">
                                        		</c:when>
                                        		<c:otherwise>
                                        		<c:set var="listDisplay" value="okDisplayList"/>
                                        		<c:set var="okApplicantsCount" value="${okApplicantsCount+1}"/>
                                        		<tr class="default ${listDisplay}">
                                        		</c:otherwise>
                                        		</c:choose>
                                      </c:when> 
                                      <c:otherwise>
                                      <c:set var="listDisplay" value="okDisplayList"/>
                                       <tr class="default ${listDisplay}">
                                      </c:otherwise>
                                    </c:choose>
                                      
                                      <td style="text-transform:capitalize;"><%=applicants[i].getSurname().toLowerCase()%>, <%=applicants[i].getFirstname().toLowerCase()%></td>
                                      <td><a href="mailto:<%=applicants[i].getEmail()%>?subject=HR Applicant Profile"><%=applicants[i].getEmail()%></a></td>                                      
                                      <td><%=applicants[i].getHomephone() %></td>
                                      <td><%=applicants[i].getCellphone() %></td>  
                                      <td>
                                      <fmt:formatDate value="${modDate}" pattern="yyyy-MM-dd"/>                                    
                                                                       
                                      </td> 
                                      <td>
									<%
										if(applicants[i].getProfileType() ==  null){
											out.println("Teaching");
										}else{
											if(applicants[i].getProfileType().equals("S")){
												out.println("Support");
											}else{
												out.println("Teaching");
											}
										}
									%>
					</td>                                                                         
                                     <td><a onclick="loadingData()" href="viewApplicantProfile.html?sin=<%=applicants[i].getSIN()%>" class="btn btn-xs btn-primary">VIEW</span></td>
                                    
                                    </tr>
                                 <%}}%>   
								      <tr>
								      <td colspan=7 style="border-top:1px solid black;">&nbsp;</td>
								      </tr>
								          
								    </tbody>
						  		</table>
							</div>
    
    </div></div>                        
                             
 <script>
 	
	$(".staleCountVal").html("${staleApplicantsCount}");
	$(".outdatedCountVal").html("${outdatedApplicantsCount}");
	$(".recentCountVal").html("${recentApplicantsCount}");
	$(".okCountVal").html("${okApplicantsCount}");	
	
	$('.staleList,.recentList,.nominalList').prop("checked",true);  
	$('.staleDisplayList,.recentDisplayList,.okDisplayList').show(); 
	
	$('.outdatedList').prop("checked",false);  
	$('.outdatedDisplayList').hide();
	
	$(document).ready(function(){
		
	
    $('.outdatedList').change(function(){
        if(this.checked) {
            $('.outdatedDisplayList').show();              
        }
        else {
        	$('.outdatedDisplayList').hide();  
        	
        }	

    });
    
    $('.staleList').change(function(){
        if(this.checked) {
            $('.staleDisplayList').show();              
        }
        else {
        	$('.staleDisplayList').hide();  
        	
        }	

    });
    
    $('.recentList').change(function(){
        if(this.checked) {
            $('.recentDisplayList').show();              
        }
        else {
        	$('.recentDisplayList').hide();  
        	
        }	

    });
    
    $('.nominalList').change(function(){
        if(this.checked) {
            $('.okDisplayList').show();              
        }
        else {
        	$('.okDisplayList').hide();  
        	
        }	

    });
    
	});
	
 </script>                        
     			                          
</body>
</html>
