<%@ page language="java"
        session="true"
        import="com.awsd.ppgp.*,java.util.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*,
                java.text.*"
        isThreadSafe="false"%>

        
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/ppgp.tld" prefix="pgp" %>

<c:set var="provincialCount" value="0" />
<c:set var="labradorCount" value="0" />
<c:set var="westernCount" value="0" />
<c:set var="centralCount" value="0" />
<c:set var="avalonCount" value="0" />

<esd:SecurityCheck permissions='PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST' />

<%
  User usr = (User) session.getAttribute("usr");
  Schools schools = new Schools();
  Personnel principal = null;
 PPGP ppgp = null;
 
 String syear="CUR";
 if(request.getAttribute("syear")!=null){
	  syear=request.getAttribute("syear").toString();
 }
 
 
%>

<c:set var="query" value="${ param.syear }"/>
<c:if test="${empty param.syear}">
	<c:set var="query" value="CUR"/>
</c:if>

<html>
	<head>
		<title>Program Specialists Summary</title>
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
   <script>   
 $('document').ready(function(){
	  $(".schoolList").DataTable({
		  	"order": [[ 0, "asc" ]],
	  		"lengthMenu": [[-1], ["All"]],
	  		"lengthChange": false,
	  		"bFilter": false
	  });	
	 
 });
 

 
    </script>

	</head>

	<body>
		
	
	<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading">
	               	<div style="float:right;font-size:36px;font-weight:bold;margin-top:-10px;color:rgba(0, 102, 153, 0.3);">
		               	<c:choose>
						<c:when test="${param.syear eq 'PREV'}">
						<%=PPGP.getPreviousGrowthPlanYear()%>
						</c:when>
						<c:otherwise>
						<%=PPGP.getCurrentGrowthPlanYear()%>
						</c:otherwise>
						</c:choose>
	               	</div>
	               	<span style="font-size:16px;">
	               	<span style="text-transform:capitalize;font-weight:bold;"><%=usr.getPersonnel().getFullNameReverse()%></span>               	
	               	<%if(usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryName().equalsIgnoreCase("SENIOR EDUCATION OFFICIER") ||
	 					 usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryName().equalsIgnoreCase("SENIOR EDUCATION OFFICER")) {%>
	               	(Director of Schools)
	               	<%} else { %>
	               	(Program Specialist)
	               	<%}%>
	               	 Learning Plan Summary School List for
	               	 
	               	 <c:choose>
					<c:when test="${param.syear eq 'PREV'}">
					<%=PPGP.getPreviousGrowthPlanYear()%>
					</c:when>
					<c:otherwise>
					<%=PPGP.getCurrentGrowthPlanYear()%>
					</c:otherwise>
					</c:choose>
	               	 
	               	 </span><br/>
	               	<b>Date Today:</b> <%=(new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime())%>
	               	</div>
      			 	<div class="panel-body"> 		            
				     Below is a list of Schools with Learning Plan Summaries. Some changes to the system to note are:<br/>
				     <ul style="font-style: italic;padding-top:10px;">
				     <li>A Director of Schools will only see schools that are a part of their associated Family of Schools.
				     <li>You can now view the current year plans, as well as previous year plans under the Administration Menu.
				     <li>Schools having no summaries and not part of an assigned School Family will NOT be listed. 
				     <li>A Director of Schools without a School Family association will NOT see any schools listed.
				     <li>If you are a Program Specialist, ALL schools will display. 
				     <li>A Program Specialist can only print ONE school summary at a time. However, if you do a search, you can print the results.
				     <li>A Director of Schools can print their entire Family of Schools summaries at once.
				     </ul>
				     If you believe one of your schools is missing from the list, please contact that school and request the Learning Plan Summary to be complete and/or contact support to make sure the school is assigned to your family.<br/>
<br/><br/>




<table class="schoolList table table-striped table-condensed" style="font-size:11px;">							   
<thead>
<tr>
<th width="30%">SCHOOL NAME (DEPT ID)</th>
<th width="15%">REGION</th>
<th width="20%">TOWN/CITY</th>
<th width="15%">PRINCIPAL</th>	
<th width="12%">TELEPHONE</th>			     
<th width="8%" class="no-print">OPTIONS</th>
</tr>
</thead>
<tbody>

<!-- If a Director of School, show just their Family of Schools. If not, show ALL schools to a Program Specialist. -->
<%if(usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryName().equalsIgnoreCase("SENIOR EDUCATION OFFICIER") ||
	 usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryName().equalsIgnoreCase("SENIOR EDUCATION OFFICER") ||
	 usr.getPersonnel().getPersonnelCategory().getPersonnelCategoryName().equalsIgnoreCase("DIRECTOR OF SCHOOLS")) {
	
for(School school : schools) {
			    principal = school.getSchoolPrincipal();			    
			 
			if(principal!=null) { 
				try{                  
            		if(school.getSchoolFamily().getProgramSpecialist().getFullNameReverse().equalsIgnoreCase(usr.getPersonnel().getFullNameReverse())){%>
			            <tr>
							<td><%=school.getSchoolName()%> (<%=school.getSchoolDeptID()%>)</td>		
							<td style="text-transform:Capitalize;"><%=school.getZone()!=null?school.getZone():"<span style='color:Silver'>N/A</span>" %></td>	
							<td><%=school.getTownCity()!=null?school.getTownCity():"<span style='color:Silver'>N/A</span>" %></td>
							<td style="text-transform:Capitalize;"><%=(principal!=null)?principal.getFullNameReverse():"<span style='color:Silver'>N/A</span>" %> </td>
							<td><%=school.getTelephone()!=null?school.getTelephone():"<span style='color:Silver'>N/A</span>"%></td>			
						<td class="no-print">
							<a href="viewGrowthPlanPrincipalSummary.html?pid=<%=principal.getPersonnelID()%>&syear=${query}" class="no-print btn btn-xs btn-primary" onclick="loadingData()" title="Click here to view detailed summary">VIEW</a>
						</td>
						</tr> 
					<%}} catch(Exception e){ %>
            
            	<!-- Hmm. Error because no family is assigned. Catch the error and do nothing but ignore for now. -->            
            	<%}}}%>

          
<%} else { %>
	               	

<% for(School school : schools) {
			    principal = school.getSchoolPrincipal();			    
			
			if(principal!=null) { 
				try{ %>
		            <tr>
					<td><%=school.getSchoolName()%> (<%=school.getSchoolDeptID()%>)</td>		
					<td style="text-transform:Capitalize;"><%=school.getZone()!=null?school.getZone():"<span style='color:Silver'>N/A</span>" %></td>	
					<td><%=school.getTownCity()!=null?school.getTownCity():"<span style='color:Silver'>N/A</span>" %></td>
					<td style="text-transform:Capitalize;"><%=(principal!=null)?principal.getFullNameReverse():"<span style='color:Silver'>N/A</span>" %> </td>
					<td><%=school.getTelephone()!=null?school.getTelephone():"<span style='color:Silver'>N/A</span>"%></td>			
					<td class="no-print">
					 <a href="viewGrowthPlanPrincipalSummary.html?pid=<%=principal.getPersonnelID()%>&syear=${query}" class="no-print btn btn-xs btn-primary" onclick="loadingData()" title="Click here to view detailed summary">VIEW</a>
					</td>
					</tr> 
           		<%} catch(Exception e){ %>
            		<!-- Hmm. Error because no family is assigned. Catch the error and do nothing but ignore for now. -->  
                        
			<%}}}%>


<%}%>

			
	</tbody>	
	</table>
	
	</body>
</html>