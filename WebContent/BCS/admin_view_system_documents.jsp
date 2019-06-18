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
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});  
   			});
		</script>
		
		
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
	 <div class="BCSHeaderText">System Documents</div>
	
	          <div align="right"><button type="button" class="btn btn-xs btn-success" onclick="openaddnewdialogsd();">Add New Document</button></div>
			 
			  <br />
	  		  <form>
				<div class="BCSHeaderText">Memos <span id="memoCount">(0)</span></div>
				<div class="alert alert-danger" id="memo_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			<div class="alert alert-success" id="memo_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 				
				<div id="MEMO-Table">	
				<table id="BCS-tableA" width="100%" class="BCSTable">
			  		<thead>
			  			<tr class="listHeader">
			  				<th width="25%" class="listdata" style="padding:2px;">Document Title</th>
			  				<th width="20%" class="listdata" style="padding:2px;">Uploaded</th>
			  				<th width="15%" class="listdata" style="padding:2px;">Uploaded By</th>
			  				<th width="10%" class="listdata" style="padding:2px;">Is Active</th>
			  				<th width="10%" class="listdata" style="padding:2px;">Options</th>
			  			</tr>
			  		</thead>
			  		<tbody>
					<c:choose>
		      			<c:when test="${fn:length(memos) > 0}">
			      			<c:forEach items="${memos}" var="rule">
			      			<c:set var="countMemos" value="${countMemos + 1}" />
			 					<tr style="border-bottom:1px solid silver;">
			      					<td class="field_content"><a href="../${rule.viewPath}" target="_blank">${rule.documentTitle}</a></td>
			      					<td class="field_content">${rule.dateUploadedFormatted}</span></td>
			      					<td class="field_content">${rule.uploadedBy}</td>
			      					<td class="field_content">${rule.isActive}</td>
			      					<td align="right" class="field_content">
			      					
			      					<button type="button" class="btn btn-xs btn-primary" onclick="closeMenu();loadMainDivPage('editSystemDocument.html?vid=${rule.id}');">View</button>
		      					    <button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialogsys('${rule.documentTitle}','${rule.id}');">Del</button>
			      					
			      						</td>
			      				</tr>
		        			</c:forEach>
     							</c:when>
     							<c:otherwise>
     								<tr><td colspan='5' style="color:Red;">No memos found</td></tr>
     							</c:otherwise>
     						</c:choose>
			  		</tbody>
			  	</table>
			  	</div>
			  	 <c:choose>
			      	<c:when test="${countMemos >0 }">
			      		<script>
			      		$('#memo_success_message').html("There are <b>${countMemos}</b> <span style='text-transform:lowercase;'>memos</span> found.").css("display","block").delay(4000).fadeOut();
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
			  			<tr class="listHeader">
			  				<th width="25%" class="listdata" style="padding:2px;">Document Title</th>
			  				<th width="20%" class="listdata" style="padding:2px;">Uploaded</th>
			  				<th width="15%" class="listdata" style="padding:2px;">Uploaded By</th>
			  				<th width="10%" class="listdata" style="padding:2px;">Is Active</th>
			  				<th width="10%" class="listdata" style="padding:2px;">Options</th>
			  			</tr>
			  		</thead>
			  		<tbody>
					<c:choose>
		      			<c:when test="${fn:length(policies) > 0}">
			      			<c:forEach items="${policies}" var="rule">
			      			<c:set var="countPolicies" value="${countPolicies + 1}" />
			 					<tr style="border-bottom:1px solid silver;">
			      					<td class="field_content"><a href="../${rule.viewPath}" target="_blank">${rule.documentTitle}</a></td>
			      					<td class="field_content">${rule.dateUploadedFormatted}</td>
			      					<td class="field_content">${rule.uploadedBy}</td>
			      					<td class="field_content">${rule.isActive}</td>
			      					<td align="right" class="field_content">
			      						<button type="button" class="btn btn-xs btn-primary" onclick="closeMenu();loadMainDivPage('editSystemDocument.html?vid=${rule.id}');">View</button>
		      					    <button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialogsys('${rule.documentTitle}','${rule.id}');">Del</button>
			      					</td>
			      				</tr>
		        			</c:forEach>
     							</c:when>
     							<c:otherwise>
     								<tr><td colspan='5' style="color:Red;">No policies found</td></tr>
     							</c:otherwise>
     						</c:choose>
			  		</tbody>
			  	</table>
			  	</div>
			  	<c:choose>
			      	<c:when test="${countPolicies >0 }">
			      		<script>$('#policy_success_message').html("There are <b>${countPolicies}</b> <span style='text-transform:lowercase;'> policies</span> found.").css("display","block").delay(4000).fadeOut();
			      	    $('#policyCount').html('(${countPolicies})');
			      	  	</script>
			      	</c:when>
			      	<c:otherwise>
			      		<script>$('#POLICY-Table').css("display","none");$('#policy_error_message').html("Sorry, there are no policies posted at this time.").css("display","block");</script>
			      	</c:otherwise>
			     </c:choose>
			  	<br />
				<div class="BCSHeaderText">Procedures <span id="procedureCount">(0)</span></div>
				<div class="alert alert-danger" id="procedure_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			<div class="alert alert-success" id="procedure_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 	
				<div id="PROCEDURE-Table">	
				<table id="BCS-tableC" width="100%" class="BCSTable" >
			  		<thead>
			  			<tr class="listHeader">
			  				<th width="25%" class="listdata" style="padding:2px;">Document Title</th>
			  				<th width="20%" class="listdata" style="padding:2px;">Uploaded</th>
			  				<th width="15%" class="listdata" style="padding:2px;">Uploaded By</th>
			  				<th width="10%" class="listdata" style="padding:2px;">Is Active</th>
			  				<th width="10%" class="listdata" style="padding:2px;">Options</th>
			  			</tr>
			  		</thead>
			  		<tbody>
					<c:choose>
		      			<c:when test="${fn:length(procedures) > 0}">
			      			<c:forEach items="${procedures}" var="rule">
			      			<c:set var="countProcedures" value="${countProcedures + 1}" />
			 					<tr style="border-bottom:1px solid silver;">
			      					<td class="field_content"><a href="../${rule.viewPath}" target="_blank">${rule.documentTitle}</a></td>
			      					<td class="field_content">${rule.dateUploadedFormatted}</td>
			      					<td class="field_content">${rule.uploadedBy}</td>
			      					<td class="field_content">${rule.isActive}</td>
			      					<td align="right" class="field_content">
			      						<button type="button" class="btn btn-xs btn-primary" onclick="closeMenu();loadMainDivPage('editSystemDocument.html?vid=${rule.id}');">View</button>
		      					    <button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialogsys('${rule.documentTitle}','${rule.id}');">Del</button>
			      					</td>
			      				</tr>
		        			</c:forEach>
     							</c:when>
     							<c:otherwise>
     								<tr><td colspan='5' style="color:Red;">No procedures found</td></tr>
     							</c:otherwise>
     						</c:choose>
			  		</tbody>
			  	</table>
			  	</div>
			  	<c:choose>
			      	<c:when test="${countProcedures >0 }">
			      		<script>$('#procedure_success_message').html("There are <b>${countProcedures}</b> <span style='text-transform:lowercase;'>procedures</span> found.").css("display","block").delay(4000).fadeOut();
			      		 $('#procedureCount').html('(${countProcedures})');
			      	  	</script>
			      	</c:when>
			      	<c:otherwise>
			      		<script>$('#PROCEDURE-Table').css("display","none");$('#procedure_error_message').html("Sorry, there are no procedures posted at this time.").css("display","block");</script>
			      	</c:otherwise>
			     </c:choose>
			  	
			  	<br />
				<div class="BCSHeaderText">Forms <span id="formCount">(0)</span></div>
				<div class="alert alert-danger" id="forms_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    			<div class="alert alert-success" id="forms_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 	
				<div id="FORMS-Table">	
				<table id="BCS-tableD" width="100%" class="BCSTable" >
			  		<thead>
			  			<tr class="listHeader">
			  				<th width="25%" class="listdata" style="padding:2px;">Document Title</th>
			  				<th width="20%" class="listdata" style="padding:2px;">Uploaded</th>
			  				<th width="15%" class="listdata" style="padding:2px;">Uploaded By</th>
			  				<th width="10%" class="listdata" style="padding:2px;">Is Active</th>
			  				<th width="10%" class="listdata" style="padding:2px;">View</th>
			  			</tr>
			  		</thead>
			  		<tbody>
					<c:choose>
		      			<c:when test="${fn:length(forms) > 0}">
			      			<c:forEach items="${forms}" var="rule">
			      			<c:set var="countForms" value="${countForms + 1}" />
			 					<tr style="border-bottom:1px solid silver;">
			      					<td class="field_content"><a href="../${rule.viewPath}" target="_blank">${rule.documentTitle}</a></td>
			      					<td class="field_content">${rule.dateUploadedFormatted}</td>
			      					<td class="field_content">${rule.uploadedBy}</td>
			      					<td class="field_content">${rule.isActive}</td>
			      					<td align="right" class="field_content">
			      						<button type="button" class="btn btn-xs btn-primary" onclick="closeMenu();loadMainDivPage('editSystemDocument.html?vid=${rule.id}');">View</button>
		      					    <button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialogsys('${rule.documentTitle}','${rule.id}');">Del</button>
			      					</td>
			      				</tr>
		        			</c:forEach>
     							</c:when>
     							<c:otherwise>
     								<tr><td colspan='5' style="color:Red;">No forms found</td></tr>
     							</c:otherwise>
     						</c:choose>
			  		</tbody>
			  	</table>
			  	</div>	
			  <c:choose>
			      	<c:when test="${countForms >0 }">
			      		<script>$('#forms_success_message').html("There are <b>${countForms}</b> <span style='text-transform:lowercase;'>forms</span> found.").css("display","block").delay(4000).fadeOut();
			      	     $('#formCount').html('(${countForms})');
			      	   </script>
			      	</c:when>
			      	<c:otherwise>
			      		<script>$('#FORMS-Table').css("display","none");$('#forms_error_message').html("Sorry, there are no forms posted at this time.").css("display","block");</script>
			      	</c:otherwise>
			     </c:choose>		  				  				  	
			 </form>
		</div>
	</div>
	<div id="myModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle">Add New Document</h4>
                    	<div class="alert alert-danger" style="display:none;" id="demessage" align="center"></div>
						<div class="alert alert-success" style="display:none;" id="dsmessage" align="center"></div>
                </div>
                <div class="modal-body">
                	<table>
                	<tr>
                		<td>Document Type:</td>
                		<td style="padding-left:10px;"><select id="documenttype" name="documenttype">
                    			<option value="-1">*** Please Select ***</option>
									<c:forEach var="entry" items="${dtypes}">
										<option value='${entry.key}'>${entry.value}</option>
								</c:forEach>
                    		</select>
                    	</td>
                	</tr>
                	<tr><td colspan="2">&nbsp;</td></tr>
                	<tr>
                		<td>Document Title:</td>
                		<td style="padding-left:10px;"><input id="documenttitle" name="documenttitle" placeholder="Title of Document" type="text"></td>
                	</tr>
                	<tr><td colspan="2">&nbsp;</td></tr>
                	<tr>
                		<td>Document:</td>
                		<td style="padding-left:10px;">
                		<input type="file" id="documentname" name="documentname" accept="application/pdf">(PDF file format only)
						</td>
                	</tr>
                	<tr><td colspan="2">&nbsp;</td></tr>
                	<tr valign="top">
                		<td>Accessibility:</td>
                		<td style="padding-left:10px;" valign="top">                		
                		<label class="checkbox-inline"><input id="vinternal" name="vinternal" type="checkbox" value="">Internal</label>
                		<label class="checkbox-inline"><input id="vexternal" name="vexternal" type="checkbox" value="">External</label>
                		</td>
                	</tr>
                	<tr><td colspan="2">&nbsp;</td></tr>
                	<tr>
                		<td>Show Message on Contactor Login:</td>
                		<td style="padding-left:10px;"><input id="showmessage" name="showmessage" type="checkbox"></td>
                	</tr>
                	<tr><td colspan="2">&nbsp;</td></tr>
                	<tr>
                		<td>Number of Days to Show:</td>
                		<td style="padding-left:10px;"><input id="messagedays" name="messagedays" type="text" value='0'></td>
                	</tr>
                	<tr><td colspan="2">&nbsp;</td></tr>
                	<tr>
                		<td>Is Active?</td>
                		<td style="padding-left:10px;"><input id="isactive" name="isactive" type="checkbox"></td>
                	</tr>
                	</table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonleft"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
   		</div>
   	</div>
   	<div id="myModal2" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Delete Document</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1d"><span id="spantitle1" name="spantitle1"></span></p>
                    <p class="text-warning" id="title2d"><span id="spantitle2" name="spantitle2"></span></p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default"  id="buttonleftd"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonrightd"></button>
                </div>
            </div>
   		</div>
   	</div>
	<script src="includes/js/jQuery.print.js"></script>	
