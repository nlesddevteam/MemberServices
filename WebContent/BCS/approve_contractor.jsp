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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrapvalidator.min.js"></script>
 <title>Approval Bussing Contractor</title>
</head>
<body>
<div class="container">
  <h2>Approval Bussing Contractor</h2>
  <form class="form-horizontal"  id="contact-form" name="contact-form">
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">First Name:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.firstName}</p>
        <input type="hidden" id="hidid" value="${contractor.id}">
        <input type="hidden" id="hidfullname" value="${contractor.lastName},${contractor.firstName}">
      </div>
    </div>    
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Last Name:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.lastName}</p>
      </div>
    </div>    
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Middle Name:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.middleName}</p>
      </div>
    </div>    
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Email:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.email}</p>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Address 1:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.address1}</p>
      </div>
    </div>    
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Address 2:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.address2}</p>
      </div>
    </div>    
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">City:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.city}</p>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Province:</label>
      <div class="col-sm-5">
			<p class="form-control-static">${contractor.province}</p>
		</div>
	</div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Postal Code:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.postalCode}</p>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Home Phone:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.homePhone}</p>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Cell Phone:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.cellPhone}</p>
      </div>
    </div>     
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Work Phone:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.workPhone}</p>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Company Name:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.company}</p>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Business Number:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.businessNumber}</p>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">HST Number:</label>
      <div class="col-sm-5">
        <p class="form-control-static">${contractor.hstNumber}</p>
      </div>
    </div>    
	<div class="alert alert-success" role="alert" id="success_message" style="display:none;"><i class="glyphicon glyphicon-thumbs-up"></i> <span id="successspan"></span></div>                   	      
    <div class="alert alert-danger" role="alert" id="error_message" style="display:none;"><i class="glyphicon glyphicon-thumbs-down"></i> <span id="errorspan"></span></div> 
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
      	<br />
      	<c:if test = "${contractor.status != 2}">
        <button type="button" class="btn btn-default" onclick="openApprove();">Approve</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-default" onclick="openReject();">Reject</button>
      	</c:if>
      </div>
    </div>
  </form>
</div>
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
      	<p>Notes:</p>
      	<br>
        <textarea class = "form-control" rows = "5" style="width:75%;display: block;margin-left: auto;margin-right: auto;" id="rnotes"></textarea>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-default" onclick="approverejectcontractor();">Ok</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button><input type="hidden" id="trantype">
      </div>
    </div>

  </div>
</div>
</body>
</html>