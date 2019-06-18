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
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
});
		</script>
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		<div class="container">
			  <h2>Approve Contractor Employee</h2>
			  <br />
			  <ul class="nav nav-tabs">
	    		<li class="active"><a data-toggle="tab" href="#details">Employee Details</a></li>
	    		<li><a data-toggle="tab" href="#documents">Documents</a></li>
	  		  </ul>
	  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="" enctype='multipart/form-data'>
	  			<div class="tab-content">
	  	<div id="details" class="tab-pane fade in active">
			<div class="form-group">
		      	<label class="control-label col-sm-2" for="email">Status:</label>
		      	
		      	
		      	      <c:choose>
         				<c:when test = "${employee.status eq 1}">
         				<div class="col-sm-5">
            				<p class="form-control-static">${ employee.statusText}</p>
            				</div>
         				</c:when>
         				<c:when test = "${employee.status eq 2}">
         				<div class="col-sm-5">
            				<p class="form-control-static">${ employee.statusText} [Approved By: ${ employee.approvedBy} on ${employee.dateApprovedFormatted}]</p>
            				</div>
         				</c:when>
                  		<c:when test = "${employee.status eq 3}">
                  		<div class="col-sm-5">
            				<p class="form-control-static">${employee.statusText} [Not Approved By: ${employee.approvedBy} on ${ employee.dateApprovedFormatted} Notes: ${employee.statusNotes}]</p>
         				</div>
         				</c:when>
         				<c:otherwise>
            				<div class="col-sm-5"><p class="form-control-static"></p></div>
         				</c:otherwise>
      				</c:choose>
        		
      		  	
		    </div> 	  	
			<div class="form-group">
		      	<label class="control-label col-sm-2" for="email">Company:</label>
		      	<div class="col-sm-5">
        			<p class="form-control-static">${contractor.company}</p>
      		  	</div>
		    </div> 	  	
		  <div class="form-group">
		  	<label class="control-label col-sm-2" for="email">Employee Position:</label>
                <div class="col-sm-5">
        			<p class="form-control-static">${employee.employeePositionText}</p>
      		  	</div>
	      </div>
		  
		    <div class="form-group">
		      	<label class="control-label col-sm-2" for="email">First Name:</label><input type="hidden" id="cid" name="cid" value="${employee.id}">
		      	<div class="col-sm-5">
        			<p class="form-control-static">${employee.firstName}</p>
      		  	</div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Last Name:</label><input type="hidden" id="hidfullname" name="hidfullname" value="${employee.lastName},${employee.firstName}">
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.lastName}</p>
      		  </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Middle Name:</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.middleName}</p>
      		  </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Email:</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.email}</p>
      		  </div>
		    </div>
		   	<div class="form-group">
		      <label class="control-label col-sm-2" for="email">Address 1:</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.address1}</p>
      		  </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Address 2:</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.address2}</p>
      		  </div>
		    </div>    
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">City:</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.city}</p>
      		  </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Province:</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.province}</p>
      		  </div>
			</div>
			<div class="form-group">
		      <label class="control-label col-sm-2" for="email">Postal Code <br />(xxxxxx):</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.postalCode}</p>
      		  </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Home Phone <br />(xxxxxxxxx):</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.homePhone}</p>
      		  </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Cell Phone <br />(xxxxxxxxx):</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.cellPhone}</p>
      		  </div>
		    </div>
		    
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Start Date With Contractor</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.startDate}</p>
      		  </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="email">Years of Continuous Service</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.continuousService}</p>
      		  </div>
		    </div>
		</div>
		<div id="documents" class="tab-pane fade">
		<br />
			<div class="form-group">
		      <label class="control-label col-sm-2" for="email">Driver Licence:</label>
		      <div class="col-sm-5">
        			<p class="form-control-static">${employee.dlNumber}</p>
      		  </div>
		    </div>
	        <div class="form-group">
	                <label class="control-label col-sm-2" for="email">Driver Licence Expiry Date:</label> 
	                <div class="col-sm-5">
        				<p class="form-control-static">${employee.dlExpiryDateFormatted}</p>
      		  		</div>
	       </div>
	       <div class="form-group">
                <label class="control-label col-sm-2" for="email">Driver License Class:</label>
                <div class="col-sm-5">
        			<p class="form-control-static">${employee.dlClassText}</p>
      		  </div>
		  </div>
		  <div class="form-group">
                <label class="control-label col-sm-2" for="email">Driver Licence Image Front:</label> 
                <div class="col-sm-5">
                	<c:if test = "${employee.dlFront != null}">
                		<a href='${spath}${dpath}${employee.dlFront}' class="mclaims" target="_blank">View Current Document</a> &nbsp;
                	</c:if>
                </div>
	       </div>
	      <div class="form-group">
                <label class="control-label col-sm-2" for="email">Driver Licence Image Back:</label> 
                <div class="col-sm-5">
                	<c:if test = "${employee.dlBack != null}">
                		<a href='${spath}${dpath}${employee.dlBack}' class="mclaims" target="_blank">View Current Document</a> &nbsp;
                	</c:if>
                </div>
	       </div>
       	   <div class="form-group">
                <label class="control-label col-sm-2" for="email">Driver Abstract Run Date:</label> 
                <div class="col-sm-5">
        				<p class="form-control-static">${employee.daRunDate == null ? '' : employee.daRunDateFormatted}</p>
      		  	</div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-2" for="email">Driver Abstract File:</label> 
                <div class="col-sm-5">
                	<c:if test = "${employee.daDocument != null}">
                		<a href='${spath}${dpath}${employee.daDocument}' class="mclaims" target="_blank">View Current Document</a> &nbsp;
                	</c:if>
                </div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-2" for="email">Driver Abstract Convictions:</label>
                <div class="col-sm-5">
        				<p class="form-control-static">${employee.daConvictions}</p>
      		  	</div>
	       </div> 
       	   <div class="form-group">
                <label class="control-label col-sm-2" for="email">First Aid/Epipen Training Expiry Date:</label> 
                <div class="col-sm-5">
        				<p class="form-control-static">${employee.faExpiryDate == null ? '' : employee.faExpiryDateFormatted}</p>
      		  	</div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-2" for="email">First Aid Certificate File:</label> 
                <div class="col-sm-5">
                	<c:if test = "${employee.faDocument != null}">
                		<a href='${spath}${dpath}${employee.faDocument}' class="mclaims" target="_blank">View Current Document</a> &nbsp;
                	</c:if>
                </div>                
	       </div>
       	   <div class="form-group">
                <label class="control-label col-sm-2" for="email">PRC/VSQ Date:</label> 
                <div class="col-sm-5">
        				<p class="form-control-static">${employee.prcvsqDate == null ? '' : employee.prcvsqDateFormatted}</p>
      		  	</div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-2" for="email">PRC/VSQ File:</label> 
                <div class="col-sm-5">
                	<c:if test = "${employee.prcvsqDocument != null}">
                		<a href='${spath}${dpath}${employee.prcvsqDocument}' class="mclaims" target="_blank">View Current Document</a> &nbsp;
                	</c:if>
                </div>                
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-2" for="email">Findings of Guilt:</label>
                <div class="col-sm-5">
        				<p class="form-control-static">${employee.findingsOfGuilt}</p>
      		  	</div>
	       </div>
       	   <div class="form-group">
                <label class="control-label col-sm-2" for="email">Provincial Court Check Date:</label> 
                <div class="col-sm-5">
        				<p class="form-control-static">${employee.pccDate == null ? '' : employee.pccDateFormatted}</p>
      		  	</div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-2" for="email">Provincial Court Check File:</label> 
                <div class="col-sm-5">
                	<c:if test = "${employee.pccDocument != null}">
                		<a href='${spath}${dpath}${employee.pccDocument}' class="mclaims" target="_blank">View Current Document</a> &nbsp;
                	</c:if>
                </div>                
	       </div>
	       <div class="form-group">
                <label class="control-label col-sm-2" for="email">Signed Confidentiality Agreement Date:</label> 
               <div class="col-sm-5">
        				<p class="form-control-static">${employee.scaDate == null ? '' : employee.scaDateFormatted}</p>
      		  	</div>
	       </div>
      	  <div class="form-group">
                <label class="control-label col-sm-2" for="email">Signed Confidentiality Agreement File:</label> 
                <div class="col-sm-5">
                	<c:if test = "${employee.scaDocument != null}">
                		<a href='${spath}${dpath}${employee.scaDocument}' class="mclaims" target="_blank">View Current Document</a> &nbsp;
                	</c:if>
                </div>                
	       </div>	        	       	       	        
		</div>
		</div>
			<div class="form-group">        
		      <div class="col-sm-offset-2 col-sm-10" id="divbuttons">
      			<br />
      			<c:if test = "${contractor.status != 2}">
        			<button type="button" class="btn btn-default" onclick="openApproveEmp();">Approve</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-default" onclick="openRejectEmp();">Reject</button>
      			</c:if>
      			</div>
		    </div>
			 
			  </form>
<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"><span id="modaltitle"></span></h4>
      </div>
      <div class="modal-body">
        <p><span id="modaltext"></span></p>
      </div>
      <div class="modal-body2" style="display:none;text-align:center;" id="modalnotes">
      	<p>Notes:222</p>
      	<br>
        <textarea class = "form-control" rows = "5" style="width:75%;display: block;margin-left: auto;margin-right: auto;" id="rnotes"></textarea>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-default" onclick="approverejectemployee();">Ok</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button><input type="hidden" id="trantype">
      </div>
    </div>

  </div>
</div>			  
		</div>
	</div>
	
