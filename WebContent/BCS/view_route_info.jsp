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
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bcs.js"></script>
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");

});
		</script>    		    
	<div id="printJob">	
		
		<div class="BCSHeaderText">View Route Information</div>
	
				<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
   				<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
		
		 <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="">
		  	<div class="form-group">
		      <label class="control-label col-sm-4" for="email">Contract Name:</label>
		      <div class="col-sm-8">
		        <p class="form-control-static">${ contract.contractName}</p><input type="hidden" id="contractid" name="contractid" value="${contract.id }">
		    </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-4" for="email">Name:</label>
		      <div class="col-sm-8">
		        <p class="form-control-static">${ route.routeName }</p><input type="hidden" id="routeid" name="routeid">
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-4" for="email">School:</label>
		      <div class="col-sm-8">
		       <p class="form-control-static">${ route.routeSchoolString}</p>
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-4" for="email">Assigned Driver:</label>
		      <div class="col-sm-8">
		      	<div class="form-inline">
		        	<p class="form-control-static">${ assigneddriver} &nbsp;&nbsp;<button type="button" class="btn btn-xs btn-primary" id="changedriver" name="changedriver" onclick="openchangedriver('${route.id}');">Change</button></p>
		      	</div>
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-4" for="email">Assigned Vehicle:</label>
		      <div class="col-sm-7">
		      	<div class="form-inline">
		        	<p class="form-control-static">${assignedvehicle}&nbsp;&nbsp;<button type="button" class="btn btn-xs btn-primary" id="changevehicle" name="changevehicle" onclick="openchangevehicle('${route.id}');">Change</button></p>
		      	</div>
		      </div>
		    </div>	   
		    </form>
		
	</div>
	
	
	
   	<div id="myModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle">Add Driver</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1"><span id="spantitle1" name="spantitle1">Select driver to assign to route</span></p>
                    <p class="text-warning" id="title2"><span id="spantitle2" name="spantitle2"></span></p>
                    <p>
                    	<select class="form-control" id="drivers" name="drivers"  style="width:auto;">
		                	<option value=" ">Please select driver</option>
							<c:forEach var="entry" items="${drivers}">
								<c:choose>
									<c:when test="${entry.id ne assigneddriverid}">
										<option value='${entry.id}'>${entry.lastName},${entry.firstName}</option>
									</c:when>
								</c:choose>
							</c:forEach>
				  		</select>
				  	</p>
				  	
				  	
				  	
				  	
				  	
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-success"  id="buttonleft"></button>
                    <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
   		</div>
   	</div>
   	<div id="myModalv" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitlev">Add Vehicle</h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1v"><span id="spantitle1v" name="spantitle1v">Select vehicle to assign to route</span></p>
                    <p class="text-warning" id="title2v"><span id="spantitle2v" name="spantitle2v"></span></p>
                    <p>
                    	<select class="form-control" id="vehicles" name="vehicles"  style="width:auto;">
		                	<option value=" ">Please select vehicle</option>
							<c:forEach var="entry" items="${vehicles}">
								<c:choose>
									<c:when test="${entry.id ne assignedvehicleid}">
										<option value='${entry.id}'>${entry.vPlateNumber} [${entry.vSerialNumber}]</option>
									</c:when>
								</c:choose>
							</c:forEach>
				  		</select>
				  	</p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-success"  id="buttonleftv"></button>
                    <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal" id="buttonrightv"></button>
                </div>
            </div>
   		</div>
   	</div>  	
<script src="includes/js/jQuery.print.js"></script>	