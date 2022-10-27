<%@ page language="java"
         import="java.util.*,
         					java.util.stream.*,
                  java.text.*,
                  java.util.concurrent.TimeUnit,
                  java.text.SimpleDateFormat, 
				  java.util.Date,  
                  com.awsd.security.*,
                  com.awsd.school.*,		           
		          com.awsd.school.dao.*,
                  com.awsd.school.bean.*,
                  com.nlesd.school.bean.*,
		          com.nlesd.school.service.*,  
                  com.awsd.mail.bean.AlertBean,
                  com.esdnl.util.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.awsd.security.crypto.*,
                 com.esdnl.personnel.v2.model.sds.bean.*,
                 com.esdnl.personnel.v2.database.sds.*" 
         isThreadSafe="false"%>


<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<!-- LOAD JAVA TAG LIBRARIES -->		
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<esd:SecurityCheck permissions="PERSONNEL-SEARCH-APPLICANTS-NON" />

<%
	User usr = (User)session.getAttribute("usr");
	ApplicantProfileBean profile = (ApplicantProfileBean) request.getAttribute("APPLICANT");
	EmployeeBean empbean = null;
  	if(profile != null){
  		empbean = (EmployeeBean) request.getAttribute("EMPBEAN");
  	}
  	
  	Collection<ApplicantDocumentBean> docs = ApplicantDocumentManager.getApplicantDocumentBean(profile);
  	SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
    SimpleDateFormat sdf_long = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
%>
<!-- Clean up the code and use jstl vars. this should be moved after. -->
<c:set var="nameDisplay" value="<%=profile.getSurname() + \", \" + profile.getFirstname()%>"/>
<c:set var="fullName" value="<%=profile.getFirstname() + \" \" + ((profile.getMiddlename() != null)?profile.getMiddlename() + \" \":\"\") + profile.getSurname() + ((profile.getMaidenname()!= null)?\" (\"+profile.getMaidenname()+\")\":\"\")%>"/>
<c:set var="UUID" value="<%=profile.getSIN()%>"/>
<c:set var="primaryAddress" value="<%=profile.getAddress1()!=null?profile.getAddress1() : \"N/A\" %>"/>
<c:set var="secondaryAddress" value="<%=profile.getAddress2()!=null?\", \"+ profile.getAddress2() : \"\" %>"/>
<c:set var="prov" value="<%=profile.getProvince()%>"/>
<c:set var="country" value="<%=profile.getCountry()%>"/>
<c:set var="postalCode" value="<%=profile.getPostalcode()%>"/>
<c:set var="homePhone" value="<%=profile.getHomephone()!=null?profile.getHomephone() : \"N/A\" %>"/>
<c:set var="cellPhone" value="<%=profile.getCellphone()!=null?profile.getCellphone() : \"N/A\" %>"/>
<c:set var="workPhone" value="<%=profile.getWorkphone()!=null?profile.getWorkphone() : \"N/A\" %>"/>
<c:set var="emailAddress" value="<%=profile.getEmail()%>"/>
<c:set var="password" value="<%=profile.getPassword()%>"/>
<c:set var="userID" value="<%=profile.getUID()%>"/>
<c:set var="SDSID" value="<%=((empbean != null) && org.apache.commons.lang.StringUtils.isNotBlank(empbean.getEmpId()))?empbean.getEmpId():\"N/A\" %>"/>
<c:set var="seniorityTotal" value="<%=empbean != null && empbean.getSeniority(EmployeeSeniorityBean.Union.NLTA) != null ? empbean.getSeniority(EmployeeSeniorityBean.Union.NLTA).getSeniorityTotal() :\"\"  %>"/> 

<html>
<head>
<title>MyHRP Applicant Profiling System</title>

<script>
$("#loadingSpinner").css("display","none");
</script>

<script type="text/javascript">
$('document').ready(function(){

  	
	});
</script>

<style>
.tableTitle {font-weight:bold;width:20%;}
.tableResult {font-weight:normal;width:30%;}

input {    
    border:1px solid silver;
}

</style>
</head>
<body>
                     <!-- DEMOGRAPHICS --------------------------------------------------------------->  
 
<div class="panel-group" style="padding-top:5px;">
                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>DEMOGRAPHICS</b></div>
      			 	<div class="panel-body">      
      			 	<span style="color:grey;">
      			 		<%if(profile.getProfileType().equals("T")){ %>
      			 			TEACHER/TLA/ADMIN PROFILE for:
      			 		<%}else{%>
      			 			SUPPORT STAFF/STUDENT ASSISTANT/MANAGEMENT PROFILE for:
      			 		<%}%>
      			 	</span>			<br/>		 	
      			 				<span style="font-size:20px;padding-top:10px;color:#007d01;font-weight:bold;">${nameDisplay}</span><br/>
      			 				<input type="hidden" id="id" value="<%=profile.getSIN() %>">
      			 				<input type='hidden' id="appname" value="${fullName}">
      			 	 			<c:if test="${APPLICANT.modifiedDate ne null}">
                       				<span style="color:Silver;text-align:right;">Last Modified: <fmt:formatDate pattern='MMMM dd, yyyy' value='${APPLICANT.modifiedDate}'/></span>
                     			</c:if>
      			 			<br/>
      			 		<esd:SecurityAccessRequired permissions="PERSONNEL-NONHR-DEMO">	
                    	<div class="table-responsive">       			 	       
      			 	       <table class="table table-striped table-condensed" style="font-size:12px;">							   
							    <tbody>
				 				<tr>
							    <td class="tableTitle">NAME:</td>
							    <td class="tableResult">${fullName}</td>
							    <td colspan=2></td>
							    </tr>
							     <tr>
							    <td class="tableTitle">ADDRESS:</td>
							    <td colspan=3>${primaryAddress}${secondaryAddress}, ${prov }, ${country } &nbsp; ${postalCode }</td>
							    </tr>
							    <tr>
							    <td class="tableTitle">TEL #:</td>
							    <td>${homePhone}</td>
							    <td class="tableTitle">CELL #:</td>
							    <td>${cellPhone}</td>
							    
							    </tr>
							    <tr>
							    <td class="tableTitle">WORK #:</td>
							    <td>${workPhone}</td>
							    <td class="tableTitle">EMAIL:</td>
							    <!-- Check to see if email is old esdnl, wsdnl, ncsd, or lsb. -->
	                                      <c:set var="emailCheck" value ="${emailAddress}"/>
		                                      <c:choose>
			                                      <c:when test="${fn:endsWith(emailCheck,'@esdnl.ca') or fn:endsWith(emailCheck,'@wnlsd.ca') or fn:endsWith(emailCheck,'@lsb.ca') or fn:endsWith(emailCheck,'@ncsd.ca')}">
			                                       <td title="Email needs updating. Invalid address." style="background-color:#fde8ec;"><span>${emailAddress} (Need update)</span></td>   
			                                      </c:when>
		                                      <c:otherwise>
		                                      		 <td><a href="mailto:${emailAddress}">${emailAddress}</a></td>   
		                                      </c:otherwise>
	                                      </c:choose>							    
							    
							    
							   
							    </tr>
							   
								<tr>
							    	<td class="tableTitle">SDS Employee ID:</td>
								    <td>${SDSID}</td>
							    	<td class="tableTitle"></td>
							    	<td></td>
							    </tr>
							    <%if(profile.getProfileType().equals("T")){ %>
							    <tr>
										<td colspan='1' class='tableTitle'>Self-Reported Contract:</td>
								    <c:choose>
									    <c:when test="${permContractSchool ne 'N/A' && permContractSchool ne null }">
										    <td colspan='3'>${permContractSchool} ending ${permContractPosition} - PERMANENT</td>
									    </c:when>
									    <c:when test="${repContractSchool ne 'N/A' && repContractSchool ne null}">
										    <td colspan='3'>${repContractSchool} ending ${repContractEndDate} - REPLACEMENT</td>
									    </c:when>
									    <c:otherwise>
										    	<td colspan='3'>N/A</td>
									    </c:otherwise>
								    </c:choose>
							    </tr>
							    <%} %>
							    <tr>
							    	<td class='tableTitle'>SDS Current Contract:</td>
							    	<td colspan='3'>
							    		<%
							    			if(empbean != null && empbean.getCurrentPositions() != null) { 
							    				List<EmployeePositionBean> positions = empbean.getCurrentPositions().stream().filter(p -> p.getEndDate().after(new Date())).collect(Collectors.toList());
							    				System.out.println(positions);
							    				if(positions != null && positions.size() > 0) {
							    					String pp = positions.stream()
								    						.filter(p -> p.getFteHours() > 0)
								    						.sorted((EmployeePositionBean p1, EmployeePositionBean p2) -> {
								    							if((p1.getEndDate() != null) && (p2.getEndDate() != null) && (p1.getEndDate().compareTo(p2.getEndDate()) != 0)) {
								    								return -1 * p1.getEndDate().compareTo(p2.getEndDate());
								    							}
								    							else  if((p1.getStartDate() != null) && (p2.getStartDate() != null) && (p1.getStartDate().compareTo(p2.getStartDate()) != 0)) {
								    								return -1 * p1.getStartDate().compareTo(p2.getStartDate());
								    							}
								    							else {
								    								return -1 * p1.getSchoolYear().compareTo(p2.getSchoolYear());
								    							}
								    						})
								    						.map(p -> p.toString())
								    						.collect(Collectors.joining("<br />"));
								    				out.println(!StringUtils.isEmpty(pp) ? pp : "N/A");
							    				}
							    				else {
							    					out.println("N/A");
							    				}
							    			}
							    			else {
							    				out.println("N/A");
							    			}
							    		%>
							    	</td>
							    </tr>
							    
							    </tbody>
							    </table>
      			 	       
						</div>
</esd:SecurityAccessRequired>						
						<!-- DOCUMENTATION --------------------------------------------------------------->
<esd:SecurityAccessRequired permissions="PERSONNEL-NONHR-DOCS">
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success" id="section12">
			<div class="panel-heading">
				<b>Documents</b>
			</div>
			<div class="panel-body">

				<div class="table-responsive">

					<% if ((docs != null) && (docs.size() > 0)) { %>
						<table class="table table-condensed table-striped" style="font-size: 11px; background-color: #FFFFFF; margin-top: 10px;">
							<thead>
	
								<tr style="border-top: 1px solid black;">
									<th width='50%'>TYPE</th>
									<th width='30%'>UPLOADED</th>
									<th width='20%'>STATUS</th>
								</tr>
							</thead>
							<tbody>
								<%
									int i = 0;
									for (ApplicantDocumentBean doc : docs) {
										if(profile.getProfileType().equals("T")){
											//only select roles get docs other then transcripts.
											if ((doc.getType().equal(DocumentType.UNIVERSITY_TRANSSCRIPT))   || (doc.getType().equal(DocumentType.LETTER)
													|| (doc.getType().equal(DocumentType.COVID19_VAX) || (doc.getType().equal(DocumentType.COVID19_VAX_BOOSTER) || doc.getType().equal(DocumentType.CODE_OF_CONDUCT)))
													)) {
												continue;
											}
										}else{
											 //only select roles get docs other then transcripts.
											if ((doc.getTypeSS().equal(DocumentTypeSS.UNIVERSITY_TRANSSCRIPT))   || (doc.getTypeSS().equal(DocumentTypeSS.LETTER)
													|| (doc.getTypeSS().equal(DocumentTypeSS.COVID19_VAX) || (doc.getTypeSS().equal(DocumentTypeSS.COVID19_VAX_BOOSTER) || doc.getTypeSS().equal(DocumentTypeSS.CODE_OF_CONDUCT)))
													)) {
												continue;
											}
										}
										
								%>
								<tr>
									<%if(profile.getProfileType().equals("T")){ %>
										<td><%=doc.getType().getDescription()%></td>
									<%}else{%>
										<td><%=doc.getTypeSS().getDescription()%></td>
									<%} %>
									
									<td><%=sdf_long.format(doc.getCreatedDate())%></td>
									<td><span style='color:Green;'>OK</span></td>
									
								</tr>
								
								<% } %>
							</tbody>
						</table>
					<% } else { %>
						<span style="color: Grey;">No Documentation currently on file.</span>
						<script>$("#section12").removeClass("panel-success").addClass("panel-danger");</script>
					<% } %>

				</div>
			</div>
		</div>
	</div>
</esd:SecurityAccessRequired>						
						
						
	              
	              </div>
  </div>
  </div> 

	</body>
</html>