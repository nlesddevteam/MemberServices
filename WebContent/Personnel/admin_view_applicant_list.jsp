<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.util.*"
         isThreadSafe="false"%>
<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
			<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
			<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%  
  ApplicantProfileBean[] applicants = null;
  String surname_part = request.getParameter("surname_part");  
  if(StringUtils.isEmpty(surname_part))
    surname_part = "A";  
  applicants = ApplicantProfileManager.getApplicantProfileBeanBySurname(surname_part);  
  
  
  
%>
<c:set var="today" value="<%=new java.util.Date()%>" />
<fmt:formatDate value="${today}" pattern="yyyy" var="todayYear"/>
<fmt:formatDate value="${today}" pattern="MM" var="todayMonth"/>

<c:set var="totalApplicantsCount" value="0" />
<c:set var="staleApplicantsCount" value="0" />
<c:set var="outdatedApplicantsCount" value="0" />
<c:set var="recentApplicantsCount" value="0" />
<c:set var="teacherAppCount" value="0" />
<c:set var="supportAppCount" value="0" />
<c:set var="okApplicantsCount" value="0" />

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>

newApplicants = 0;
nominalApplicants = 0;		
staleApplicants = 0;
outdatedApplicants = 0;

$("#loadingSpinner").css("display","none");
</script>
<script>
 $('document').ready(function(){
	  $("#applicant-list").DataTable(
		{
			
			"order": [[ 0, "asc" ]],
			"lengthMenu": [[-1], ["All"]],
			"lengthChange": false
		}	  
	  );
 });
    </script>
 	<script src="includes/js/Chart.min.js"></script>   
    <style>
		input {    
    border:1px solid silver;
		}
		.btn {font-size:11px;}
</style>	

</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
  
  
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading">
	               	<span style="font-size:16px;font-weight:bold;">Applicant Surnames Starting With "<%=surname_part%>" (<span class="totalCountVal"></span>)</span>	               	
	               	
	               	</div>
      			 	<div class="panel-body">
      			 	<span style="float:right;font-size:120px;color:rgba(223,240,216,0.7);font-weight:bold;margin-top:-45px;"><%=surname_part%></span>
      			 	<span style="font-size:12px;">
      			 	Below are the list of <span class="totalCountVal"></span> applicants with lastname starting with <b><%=surname_part%></b>. The rows are color coded to show the status of the applicants profile. 
	               	Any newly modified profile in the last 30 days will be highlighted green. 
	               	If a profile hasnt been updated in 6 months, and has become stale, will be highlighted as yellow. 
	               	Those highlighted red are profiles that have not been modified/updated in the last 3 years. Profiles not highlighted have been updated/modified within the last 6 months and are considered ok. If a email address is outlined in red, means its an invalid/outdated email address from pass school districts. 
	               	<br/><br/>To show/hide a group of profiles, check/clear checkbox(s) below. <b>By default the Outdated Applicants are NOT displayed.</b>
                    </span>
                    <div style="clear:both;"></div>
    
										<%if(request.getAttribute("msg")!=null){%>
	                                      <div class="alert alert-warning" style="text-align:center;">                                       
	                                          <%=(String)request.getAttribute("msg")%>
	                                        </div>
	                                    <%}%>									
					
                    
                    <div class="table-responsive"> 
                   
                
                    <div align="center" style="width:100%;text-align:center;padding-bottom:10px;">
						<div style="float:left;width:50%;background-color:#FFFFFF;font-size:12px;"><canvas id="applicantSummaryChart" height="200" style="width:100%;min-width:200px;"></canvas></div>
						<div style="float:right;width:50%;background-color:#DFF0D8;font-size:12px;height:50px;"><input type="checkbox" name="" class="recentList"><div style="padding-top:2px;">New 1 month (<span class="recentCountVal"></span>)  <span class="percentageNew"></span>%</div></div>
						<div style="float:right;width:50%;background-color:#FFFFFF;font-size:12px;height:50px;"><input type="checkbox" name="" class="nominalList"><div style="padding-top:2px;">Nominal 1-5 months (<span class="okCountVal"></span>) <span class="percentageNominal"></span>%</div></div>
						<div style="float:right;width:50%;background-color:#FCF8E3;font-size:12px;height:50px;"><input type="checkbox" name="" class="staleList"><div style="padding-top:2px;">Stale 6+ months (<span class="staleCountVal"></span>)  <span class="percentageStale"></span>%</div></div>
						<div style="float:right;width:50%;background-color:#F2DEDE;font-size:12px;height:50px;"><input type="checkbox" name="" class="outdatedList"><div style="padding-top:2px;">Outdated 3+ years (<span class="outdatedCountVal"></span>) <span class="percentageOutdated"></span>%</div></div>
					 </div>
					 <div style="clear:both;">&nbsp;</div>
		
 		<div class="alert alert-info" style="text-align:center;font-size:11px;">
 		Check the <b>Applicant Type(s)</b> you would like to display/hide. By default, both <b>Support Staff/Management</b> and <b>Teaching/TLA</b> Applicants are displayed.<br/>
    	<br/>
     		&nbsp;<input type="checkbox" name="teacherListings" class="teacherList form-horizontal"> Teaching/TLA Applicants (<span class="teacherCountVal"></span>) <span class="percentageTeacher"></span>% &nbsp; &nbsp;
			&nbsp;<input type="checkbox" name="supportListings" class="supportList form-horizontal"> Support Staff/Management Applicants (<span class="supportCountVal"></span>) <span class="percentageSupport"></span>%
		</div>
               <div style="clear:both;">&nbsp;</div>        
                    
                    			 <table id='applicant-list' class="jobsappListing table table-condensed" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
								    <thead>
								      <tr style="border-top:1px solid black;">
								        <th width='20%'>NAME (Last, First)</th>								        
								        <th width='20%'>EMAIL</th>								        
								        <th width='12%'>TELEPHONE</th>
								        <th width='12%'>CELL</th>
								        <th width='12%'>MODIFIED</th>
								        <th width='16%'>TYPE</th>
								        <th width='8%'>OPTIONS</th>
								      </tr>
								    </thead>
								    
								    <tbody>
								   <%if(applicants.length > 0){
                                    for(int i=0; i < applicants.length; i++){%>
                                    
                                    <c:set var="modDate" value="<%=applicants[i].getModifiedDate()%>" />
                                    <c:set var="staleProfile" value="<%=applicants[i].isStale() %>"/>
                                    <c:set var="applicantType" value="<%= applicants[i].getProfileType() %>"/>
                                    			<c:choose>
                                    			<c:when test="${applicantType eq 'T'}">
                                      			<c:set var="listTypeDisplay" value="teacherDisplayList"/>
                                      			<c:set var="teacherApplicantsCount" value="${teacherApplicantsCount+1}"/> 
                                      			<c:set var="typeColor" value="#00008B;"/>
                                      			</c:when>
                                      			<c:when test="${applicantType eq 'S'}">
                                      			<c:set var="listTypeDisplay" value="supportDisplayList"/>
                                      			<c:set var="supportApplicantsCount" value="${supportApplicantsCount+1}"/>
                                      			<c:set var="typeColor" value="#A52A2A;"/>
                                      			</c:when>
                                      			<c:otherwise>
                                      			<c:set var="listTypeDisplay" value="unknownDisplayList"/> 
                                      			<c:set var="typeColor" value="#5F9EA0;"/>                                     			
                                      			</c:otherwise>
                                                </c:choose>                      
                                    
                                      <fmt:formatDate value="${modDate}" pattern="yyyy" var="modYear"/>
                                      <fmt:formatDate value="${modDate}" pattern="MM" var="modMonth"/>
                                      <c:choose>
                                       <c:when test="${staleProfile eq 'true'}">
                                      			<c:choose>
                                      			<c:when test="${(modYear le (todayYear -3))}">
                                      			<c:set var="listDisplay" value="outdatedDisplayList"/>
                                      			<c:set var="outdatedApplicantsCount" value="${outdatedApplicantsCount+1}"/> 
                                      			<tr class="danger ${listDisplay} ${listTypeDisplay}">
                                      			</c:when>
                                       			<c:otherwise>
                                       			<c:set var="listDisplay" value="staleDisplayList"/>
                                       			<c:set var="staleApplicantsCount" value="${staleApplicantsCount+1}"/>
                                       			<tr class="warning ${listDisplay} ${listTypeDisplay}">
                                       			</c:otherwise>
                                       			</c:choose>
                                      </c:when>                                      
                                      <c:when test="${staleProfile eq 'false'}"> 
                                        		<c:choose>
                                        		<c:when test="${(modYear eq todayYear) and (modMonth eq todayMonth)}">
                                        		<c:set var="listDisplay" value="recentDisplayList"/>
                                        		<c:set var="recentApplicantsCount" value="${recentApplicantsCount+1}"/>
                                        		<tr class="success ${listDisplay} ${listTypeDisplay}">
                                        		</c:when>
                                        		<c:otherwise>
                                        		<c:set var="listDisplay" value="okDisplayList"/>
                                        		<c:set var="okApplicantsCount" value="${okApplicantsCount+1}"/>
                                        		<tr class="default ${listDisplay} ${listTypeDisplay}">
                                        		</c:otherwise>
                                        		</c:choose>
                                      </c:when> 
                                      <c:otherwise>
                                      <c:set var="listDisplay" value="okDisplayList"/>
                                       <tr class="default ${listDisplay} ${listTypeDisplay}">
                                      </c:otherwise>
                                    </c:choose>
                                      
                                      <td style="text-transform:capitalize;"><%=applicants[i].getSurname().toLowerCase()%>, <%=applicants[i].getFirstname().toLowerCase()%></td>
                                      <!-- Check to see if email is old esdnl, wsdnl, ncsd, or lsb. -->
	                                      <c:set var="emailCheck" value ="<%=applicants[i].getEmail()%>"/>
		                                      <c:choose>
			                                      <c:when test="${fn:endsWith(emailCheck,'@esdnl.ca') or fn:endsWith(emailCheck,'@wnlsd.ca') or fn:endsWith(emailCheck,'@lsb.ca') or fn:endsWith(emailCheck,'@ncsd.ca')}">
			                                       <td title="Email needs updating. Invalid address." style="border:1px solid red;background-color:#fde8ec;"><span>ERROR: <%=applicants[i].getEmail()%></span></td>   
			                                      </c:when>
		                                      <c:otherwise>
		                                      		<td><a href="mailto:<%=applicants[i].getEmail()%>?subject=HR Applicant Profile"><%=applicants[i].getEmail()%></a></td>   
		                                      </c:otherwise>
	                                      </c:choose>
                                      
                                      <td><%=((applicants[i].getHomephone() != null)?applicants[i].getHomephone():"<span style='color:silver;'>N/A</span>")%></td>
                                      <td><%=((applicants[i].getCellphone() != null)?applicants[i].getCellphone():"<span style='color:silver;'>N/A</span>")%></td>  
                                      <td>
                                      
                                      <% if (applicants[i].getModifiedDate() != null) { %>
                                      <fmt:formatDate value='${modDate}' pattern='yyyy-MM-dd'/>                                    	  
                                      <%} else { %>                                    	  
                                    	<span style='color:silver;'>N/A</span>  
                                      <%} %>                                                                    
                                                                       
                                      </td>  
                                      	<td><span style="color:${typeColor}"> <%= applicants[i].getProfileType().equals("S") ? "Support/Management": "Teaching/TLA/Admin" %> </span></td>                                                                        
                                     	<td><a onclick="loadingData()" href="viewApplicantProfile.html?sin=<%=applicants[i].getSIN()%>" title="View this Applicant's Profile" class="btn btn-xs btn-primary">PROFILE</span></td>
                                    	<c:set var="totalApplicantsCount" value="${totalApplicantsCount+1}"/>
                                    </tr>
                                 <%}}%>   
								      
								          
								    </tbody>
						  		</table>
							</div>
    
    </div></div>                        
                             
 <script>
 
 //Do the math to make cool graphs!!
 
 	$(".totalCountVal").html("${totalApplicantsCount}");
	$(".staleCountVal").html("${staleApplicantsCount}");
	$(".outdatedCountVal").html("${outdatedApplicantsCount}");
	$(".teacherCountVal").html("${teacherApplicantsCount}");
	$(".supportCountVal").html("${supportApplicantsCount}");
	$(".recentCountVal").html("${recentApplicantsCount}");
	$(".okCountVal").html("${okApplicantsCount}");	
	
	$(".percentageStale").html(Math.round(${staleApplicantsCount}/${totalApplicantsCount}*100));
	$(".percentageOutdated").html(Math.round(${outdatedApplicantsCount}/${totalApplicantsCount}*100));
	$(".percentageNew").html(Math.round(${recentApplicantsCount}/${totalApplicantsCount}*100));
	$(".percentageNominal").html(Math.round(${okApplicantsCount}/${totalApplicantsCount}*100));
	$(".percentageTeacher").html(Math.round(${teacherApplicantsCount}/${totalApplicantsCount}*100));
	$(".percentageSupport").html(Math.round(${supportApplicantsCount}/${totalApplicantsCount}*100));
	
	$('.staleList,.recentList,.nominalList,.teacherList,.supportList').prop("checked",true);  
	$('.staleDisplayList,.recentDisplayList,.okDisplayList,.teacherDisplayList,.supportDisplayList').show(); 
	
	$('.outdatedList').prop("checked",false);  
	$('.outdatedDisplayList').hide();
	
	$(document).ready(function(){
		
		 $('.teacherList').change(function(){
		        if(this.checked) {
		            $('.teacherDisplayList').show();
		            $('.outdatedDisplayList').hide(); 
		            $('.outdatedList').prop("checked",false);  
		            $('.dataTables_wrapper').show();
		        }
		        else {
		        	$('.teacherDisplayList').hide(); 
		        	 if (!$("input[name='teacherListings']").is(':checked') && !$("input[name='supportListings']").is(':checked')) {  	 
		    			 $('.dataTables_wrapper').hide();
		    			 //add error message div
		    		 }
		        }	

		    });	
		
		 $('.supportList').change(function(){
		        if(this.checked) {
		            $('.supportDisplayList').show();
		            $('.outdatedDisplayList').hide();
		            $('.outdatedList').prop("checked",false);  
		            $('.dataTables_wrapper').show();
		        }
		        else {
		        	$('.supportDisplayList').hide(); 
		        	 if (!$("input[name='teacherListings']").is(':checked') && !$("input[name='supportListings']").is(':checked')) {  	 
		    			 $('.dataTables_wrapper').hide();
		    		 } 
		        }	

		    });	
		
		 
		
		 
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
                             
  <script>
//Graph	Summary Applicants
    newApplicants = ${recentApplicantsCount};
    nominalApplicants=${okApplicantsCount}      			
	staleApplicants=${staleApplicantsCount}
	outdatedApplicants=${outdatedApplicantsCount}   
   
	var ctx = document.getElementById("applicantSummaryChart").getContext('2d');
	var applicantSummaryChart = new Chart(ctx, {
	  type: 'pie',
	  data: {
	    labels: [ "New Applicants","Nominal Applicants","Stale Applicants","Outdated Applicants",],
	    datasets: [{
	      backgroundColor: ["#DFF0D8","#F8F8FF","#FCF8E3","#F2DEDE"],
	      data: [ 	newApplicants,
	    	  		nominalApplicants,      			
	    	  		staleApplicants,
	    	  		outdatedApplicants]
	    }]
	  },
	  
	  options: {
		  	  
	      title: {
	         display: false,
	         fontSize: 12,
	         text: 'Applicants Breakdown'
	     },
	     legend: {
	         display: true,
	         fontSize: 12,
	         position: 'left',

	     },
	     responsive: false 
	 }

	  
	  
	});

	
	
	
	
	
	//End load Graph
  
  </script>                      
                             
                             
                             
                        
</body>
</html>
