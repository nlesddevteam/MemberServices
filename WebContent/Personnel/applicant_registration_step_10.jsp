<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.school.bean.*,
                 com.awsd.school.dao.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false" %>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<job:ApplicantLoggedOn/>

<c:set var='doctypes' value='<%= DocumentType.ALL %>' />

<c:set var='cods' value='<%=ApplicantCriminalOffenceDeclarationManager.getApplicantCriminalOffenceDeclarationBeans((ApplicantProfileBean)session.getAttribute("APPLICANT")) %>' />

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
    $("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold;width:15%;}
.tableResult {font-weight:normal;width:85%;}

.tableTitleL {font-weight:bold;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>
</head>
	
	<body>
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">10</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
SECTION 10: Editing your Teacher/Educator HR Application Profile 
</div>

<br/>Various documents/declarations are required to complete the application process. They can be uploaded here. 
<div class="alert alert-warning" style="padding:1px;text-align:center;margin-top:5px;"><span class="glyphicon glyphicon-info-sign"></span> <b>NOTICE:</b> ONLY PDF DOCUMENTS ARE ACCEPTED <span class="glyphicon glyphicon-info-sign"></span></div>


	
<form id="frmPostJob" action="applicantRegistration.html?step=10" method="post" enctype="multipart/form-data" style='padding:0;margin:0;'>
					
					
					<div class="panel-group">
								    <div class="panel panel-info">
								      <div class="panel-heading">10. DOCUMENTS (Upload)</div>
								      <div class="panel-body">
								      You can upload all documents with exception to Criminal Offence Declaration(s) here. Criminal Offense Declaration can be uploaded in a section at bottom of this page.
								     <br/><br/><div class="table-responsive"> 	
								      <table class="table table-striped table-condensed" style="font-size:12px;">							   
											    <tbody>
				                                <tr>
								      			<td class="tableTitle">Document Type:</td>
								      			<td class="tableResult"><job:DocumentTypeListbox id="lst_DocumentType" cls="form-control" /></td>
								      			</tr>
								      			<tr>
								      			<td class="tableTitle">Document File:</td>
								      			<td class="tableResult"><input type="file" name='applicant_document' class="form-control" /></td>
								      			</tr>
								      			</tbody>
								      </table>			
								      		
					                 			<div align="center"><input class="btn btn-info btn-xs" type="submit" value="Upload Selected" /></div>
								      
								      
								      </div>
								    </div>
								    </div>
				   
					
	               <%int cntr=0; %>
	               <c:forEach items="${doctypes}" var="doctype">
	                     <c:set var='docs' value='<%= ApplicantDocumentManager.getApplicantDocumentBean((ApplicantProfileBean)session.getAttribute("APPLICANT"),(DocumentType)pageContext.getAttribute("doctype")) %>' />
	                     <%cntr++; %>
		                  <div class="panel panel-success" id="doctypes<%=cntr%>">     
			              <div class="panel-heading">${doctype.description}</div>  
			              <div class="panel-body">  
			              <div class="table-responsive"> 	      
			                                                          
			                                  		
			                                      		<c:choose>
			                                      			<c:when test="${fn:length(docs) gt 0}">
			                             <table class="table table-striped table-condensed" style="font-size:12px;">
			                             <thead>
			                             <tr>
			                             <th width="80%">UPLOAD DATE</th>
			                             <th width="20%">OPTIONS</th>
			                             </tr>
			                             </thead> 
			                             <tbody> 	
					                     <c:forEach items="${docs}" var="doc">
                                     		<tr>
                                      		<td><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${doc.createdDate}" /></td>
                                      		<td><a class='btn btn-xs btn-primary' href='viewDocument.html?id=${doc.documentId}' target="_blank">View</a></td>
                                      		</tr>	
                                      	</c:forEach>
						                </tbody>
						                </table>                     	
				                                      		</c:when>
				                                      		<c:otherwise>
				                                      		<span style="color:Grey;">No ${doctype.description} documents uploaded.</span>
					                                      	<script>$("#doctypes<%=cntr%>").removeClass("panel-success").addClass("panel-danger");</script>			
				                                      		</c:otherwise>
			                                      		</c:choose>
			            </div>                
			            </div>
				    	</div>                        	
	                   </c:forEach>
	                                    
	                    <div class="panel panel-success coddocs">                   
			              <div class="panel-heading">Criminal Offence Declaration(s) <span class="no-print" style="float:right;padding-right:5px"><a id='btn-add-cod' class='btn btn-xs btn-primary' href='applicant_registration_step_10_CODF.jsp'>Add Declaration</a></span></div>  
			              <div class="panel-body"> 
			              <div class="table-responsive">                 
		                					<c:choose>
		                                      			<c:when test="${fn:length(cods) gt 0}">
		                                      			<table class="table table-striped table-condensed" style="font-size:12px;">
			                             <thead>
			                             <tr>
			                             <th width="80%">DECLARATION DATE</th>
			                             <th width="20%">OPTIONS</th>
			                             </tr>
			                             </thead> 
			                             <tbody> 	
				                                      		
				                                      		<c:forEach items="${cods}" var="cod">
					                                      	<tr>	
					                                      	<td><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${cod.declarationDate}" /></td>
					                                      	<td><a class='btn btn-xs btn-primary' href='viewCriminalOffenceDeclaration.html?id=${cod.declarationId}'>View</a></td>
					                                      	</tr>	
				                                      		</c:forEach>
				                          </tbody>
						                </table>             		
				                                      		
			                                      		</c:when>
			                                      		<c:otherwise>
			                                      		<span style="color:Grey;">No criminal offence declarations on record.</span>
				                                      	<script>$(".coddocs").removeClass("panel-success").addClass("panel-danger");</script>		
			                                      		</c:otherwise>
		                                     	</c:choose>
		                                    		
		                                      		
		                   </div></div></div>                     			
		                                      			
		                                      	<br/><br/>		
	                                    		
	                                    		 <div align="center">
	                                    		  <a class="btn btn-xs btn-danger" href="view_applicant.jsp">Back to Profile</a>
	                                    		 <!-- <input class="btn btn-sm btn-success" type="button" value="Complete Profile!" onclick="document.location.href='applicant_resume.jsp'" />--></div>
	</div>             
	                                </form>
	                                
	                                
   <%if(request.getAttribute("msg")!=null){%>
	<script>$(".msgok").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$(".msgerr").css("display","block").delay(5000).fadeOut();</script>							
<%}%>                           
	</body>
</html>
