<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,                 
                 com.awsd.common.*,com.esdnl.util.*,
                 org.apache.commons.lang.StringUtils,
                 org.apache.commons.lang.StringUtils.*, 
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<c:set var="countMemos" value="0" />
<c:set var="countPolicies" value="0" />
<c:set var="countProcedures" value="0" />
<c:set var="countForms" value="0" />
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/bcs.js"></script>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");    			
    			$("#BCS-tableA tr:even").not(':first').css("background-color", "#FFFFFF");
     		    $("#BCS-tableA tr:odd").css("background-color", "#f2f2f2");
     		    $("#BCS-tableB tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#BCS-tableB tr:odd").css("background-color", "#f2f2f2");
    		    $("#BCS-tableC tr:even").not(':first').css("background-color", "#FFFFFF");
     		    $("#BCS-tableC tr:odd").css("background-color", "#f2f2f2");
     		    $("#BCS-tableD tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#BCS-tableD tr:odd").css("background-color", "#f2f2f2");
				

});
		</script>
		<script>
   			$(document).ready(function () {
    		$('.menuBCSC').click(function () {
    		$("#loadingSpinner").css("display","inline").delay(2000).fadeOut();
    		});  
   			});
		</script> 
		
		
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		
			<br/>
			   <div class="BCSHeaderText">Policies and Procedures</div>
			  	Below are various documents posted for your review. Simply click on the View icon at right to open the document in a new window.		  
			 
	  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="" enctype='multipart/form-data'>
				<div class="BCSHeaderText">Memos <span id="memoCount">(0)</span></div>
				<div class="alert alert-danger" id="memo_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			<div class="alert alert-success" id="memo_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 				
				<div id="MEMO-Table">	
				<table id="BCS-tableA" width="100%" class="BCSTable">
			  		<thead>
			  			<tr style="border-bottom:1px solid grey;" class="listHeader">
		      		<th width="60%" class="listdata">Document Title</th>
		      		<th width="20%" class="listdata">Document Type</th>
		      		<th width="10%" class="listdata">Uploaded</th>		      		
		      		<th width="10%" class="listdata">Options</th>
		      		
		      		
		      		</tr>
		      		</thead>
		      		<tbody>
					<c:choose>
		      			<c:when test="${fn:length(memos) > 0}">
			      			<c:forEach items="${memos}" var="rule">
			      			<c:set var="countMemos" value="${countMemos + 1}" />
			 					<tr style="border-bottom:1px solid silver;">
			 					
			 					<td class="field_content">${rule.documentTitle}</td>
		      					<td class="field_content">${rule.typeString}</td>
		      					<td class="field_content">${rule.dateUploadedFormatted}</td>		      					
		      					<td class="field_content" align="right">
		      					<button type="button" class="btn btn-xs btn-primary" onclick="window.open('../${rule.viewPath}','_blank');">View</button>
		      					</td>
			      				</tr>
		        			</c:forEach>
     							</c:when>
     							<c:otherwise>
     								<tr><td colspan='4' style="color:Red;">No memos found</td></tr>
     							</c:otherwise>
     						</c:choose>
			  		</tbody>
			  	</table>
			  	</div>
			  	<c:choose>
			      	<c:when test="${countMemos >0 }">
			      		<script>
			      		$('#memo_success_message').html("There are <b>${countMemos}</b> memos found.").css("display","block").delay(4000).fadeOut();
			      		$('#memoCount').html('(${countMemos})');
			      		</script>
			      	</c:when>
			      	<c:otherwise>
			      		<script>$('#MEMO-Table').css("display","none");$('#memo_error_message').html("Sorry, there are no memos posted at this time.").css("display","block");</script>
			      	</c:otherwise>
			     </c:choose>
			  	
			  	
			  	
			  	<br />
				<div class="BCSHeaderText">Policies <span id="policyCount">(0)</span></div>	
				<div class="alert alert-danger" id="policy_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			<div class="alert alert-success" id="policy_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 	
				<div id="POLICY-Table">	
				<table id="BCS-tableB" width="100%" class="BCSTable" >
			  		<thead>
			  		<tr style="border-bottom:1px solid grey;" class="listHeader">
		      		<th width="60%" class="listdata">Document Title</th>
		      		<th width="20%" class="listdata">Document Type</th>		      		
		      		<th width="10%" class="listdata">Uploaded</th>		      		
		      		<th width="10%" class="listdata">Options</th>
		      		
		      		
		      		</tr>
		      		</thead>
		      		<tbody>
					<c:choose>
		      			<c:when test="${fn:length(policies) > 0}">
			      			<c:forEach items="${policies}" var="rule">
			      			<c:set var="countPolicies" value="${countPolicies + 1}" />
			 					<tr style="border-bottom:1px solid silver;">
			 					<td class="field_content">${rule.documentTitle}</td>
		      					<td class="field_content">${rule.typeString}</td>		      					
		      					<td class="field_content">${rule.dateUploadedFormatted}</td>		      					
		      					<td class="field_content" align="right">
		      					<button type="button" class="btn btn-xs btn-primary" onclick="window.open('../${rule.viewPath}','_blank');">View</button>      					
		      					
			 					
			 					</td>
			      				</tr>
		        			</c:forEach>
     							</c:when>
     							<c:otherwise>
     								<tr><td colspan='4' style="color:Red;">No policies found</td></tr>
     							</c:otherwise>
     						</c:choose>
			  		</tbody>
			  	</table>
			  	</div>
			  	
			  	<c:choose>
			      	<c:when test="${countPolicies >0 }">
			      		<script>
			      		$('#policy_success_message').html("There are <b>${countPolicies}</b> policies found.").css("display","block").delay(4000).fadeOut();
			      		$('#policyCount').html('(${countPolicies})');
			      		</script>
			      	</c:when>
			      	<c:otherwise>
			      		<script>$('#POLICY-Table').css("display","none");$('#memo_error_message').html("Sorry, there are no policies posted at this time.").css("display","block");</script>
			      	</c:otherwise>
			     </c:choose>
			  	
			  	
			  	<br />
				<div class="BCSHeaderText">Procedures <span id="procedureCount">(0)</span></div>
				<div class="alert alert-danger" id="procedure_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			<div class="alert alert-success" id="procedure_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 	
				<div id="PROCEDURE-Table">	
				<table id="BCS-tableC" width="100%" class="BCSTable" >
			  		<thead>
			  			<tr style="border-bottom:1px solid grey;" class="listHeader">
		      		<th width="60%" class="listdata">Document Title</th>
		      		<th width="20%" class="listdata">Document Type</th>		      		
		      		<th width="10%" class="listdata">Uploaded</th>		      		
		      		<th width="10%" class="listdata">Options</th>
		      		
		      		
		      		</tr>
		      		</thead>
		      		<tbody>
					<c:choose>
		      			<c:when test="${fn:length(procedures) > 0}">
			      			<c:forEach items="${procedures}" var="rule">
			      			<c:set var="countProcedures" value="${countProcedures + 1}" />
			 					<tr style="border-bottom:1px solid silver;">
			 					<td class="field_content">${rule.documentTitle}</td>
		      					<td class="field_content">${rule.typeString}</td>		      					
		      					<td class="field_content">${rule.dateUploadedFormatted}</td>		      					
		      					<td class="field_content" align="right">
		      					<button type="button" class="btn btn-xs btn-primary" onclick="window.open('../${rule.viewPath}','_blank');">View</button>
			 					</td>
			      				</tr>
		        			</c:forEach>
     							</c:when>
     							<c:otherwise>
     								<tr><td colspan='4' style="color:Red;">No procedures found</td></tr>
     							</c:otherwise>
     						</c:choose>
			  		</tbody>
			  	</table>
			  	</div>
			  	<c:choose>
			      	<c:when test="${countProcedures > 0 }">
			      		<script>$('#procedure_success_message').html("There are <b>${countProcedures}</b> procedures found.").css("display","block").delay(4000).fadeOut();
			      		 $('#procedureCount').html('(${countProcedures})');
			      	  	</script>
			      	</c:when>
			      	<c:otherwise>
			      		<script>$('#PROCEDURE-Table').css("display","none");$('#procedure_error_message').html("Sorry, there are no procedure documents posted at this time.").css("display","block");</script>
			      	</c:otherwise>
			     </c:choose>
			  	
			  	
			  	<br />
				<div class="BCSHeaderText">Forms <span id="formCount">(0)</span></div>
				<div class="alert alert-danger" id="forms_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			<div class="alert alert-success" id="forms_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 	
				<div id="FORMS-Table">	
				<table id="BCS-tableD" width="100%" class="BCSTable" >
			  		<thead>
			  		
			  			<tr style="border-bottom:1px solid grey;" class="listHeader">
		      		<th width="60%" class="listdata">Document Title</th>
		      		<th width="20%" class="listdata">Document Type</th>		      		
		      		<th width="10%" class="listdata">Uploaded</th>		      		
		      		<th width="10%" class="listdata">Options</th>
		      		
		      		
		      		</tr>
		      		</thead>
		      		<tbody>
					<c:choose>
		      			<c:when test="${fn:length(forms) > 0}">
			      			<c:forEach items="${forms}" var="rule">
			      			<c:set var="countForms" value="${countForms + 1}" />
			 					<tr style="border-bottom:1px solid silver;">
			 					<td class="field_content">${rule.documentTitle}</td>
		      					<td class="field_content">${rule.typeString}</td>		      					
		      					<td class="field_content">${rule.dateUploadedFormatted}</td>		      					
		      					<td class="field_content" align="right">	      					
		      					<button type="button" class="btn btn-xs btn-primary" onclick="window.open('../${rule.viewPath}','_blank');">View</button>		      					
			 					</td>
			      				</tr>
		        			</c:forEach>
     							</c:when>
     							<c:otherwise>
     								<tr><td colspan='4' style="color:Red;">No forms found</td></tr>
     							</c:otherwise>
     						</c:choose>
			  		</tbody>
			  	</table>	
			  	</div>		
			  	
			  	<c:choose>
			      	<c:when test="${countForms >0 }">
			      		<script>$('#forms_success_message').html("There are <b>${countForms}</b> forms posted.").css("display","block").delay(4000).fadeOut();
			      	     $('#formCount').html('(${countForms})');
			      	   </script>
			      	</c:when>
			      	<c:otherwise>
			      		<script>$('#FORMS-Table').css("display","none");$('#forms_error_message').html("Sorry, there are no forms posted at this time.").css("display","block");</script>
			      	</c:otherwise>
			     </c:choose>	
			  	
			  	  				  				  	
			 </form>
		
	</div>

<script src="includes/js/jQuery.print.js"></script>	