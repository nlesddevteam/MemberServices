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
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/multiselect.js"></script>
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(document).ready(function() {
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    			$('#multiselect').multiselect();
        		var date_start=$('#sstartdate');
        		var date_end=$('#senddate');
        	      var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
        	      var options={
        	        format: 'mm/dd/yyyy',
        	        container: container,
        	        todayHighlight: true,
        	        autoclose: true,
        	      };
        	      date_start.datepicker(options);
        	      date_end.datepicker(options);

});
		</script>
	<%pageContext.setAttribute("now", new java.util.Date()); %>   		
	<div id="printJob">	
		<div class="container">
			 <div class="BCSHeaderText">Edit Custom Report</div>
			  <br />
			  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="">
			  		<div class="form-group">
			      		<label class="control-label col-sm-2" for="email">Report Name:</label><input type="hidden" id="reportid" name="reportid" value="${report.id}">
			      		<div class="col-sm-8">
			        		<input class="form-control" id="reportname" name="reportname" type="text" placeholder="Enter report name" value="${report.reportName}">
			      		</div>
			    	</div>
			  		<div class="form-group">
		      			<label class="control-label col-sm-2" for="email">Report Table:</label>
		      			<div class="col-sm-8">
        					<p class="form-control-static">${table.tableName}</p><input type="hidden" id="deletedids" name="deletedids">
        					<input type="hidden" id="selectedfields" name="selectedfields">
      		  			</div>
		    		</div>
		    		<div class="form-group">
	  		  				<label class="control-label col-sm-2" for="email">Select Report Field(s):</label>
	  		  				<div class="row">
							  <div class="col-sm-3">
							    <select name="from" id="multiselect" class="form-control" size="8" multiple="multiple">
							    	<c:forEach var="entry" items="${notselected}">
		                				<option value='${entry.id}'>${entry.fieldTitle}</option>
									</c:forEach>
							    </select>
							  </div>
							  <div class="col-sm-1">
							    <button type="button" id="multiselect_rightAll" class="btn btn-block"><i class="glyphicon glyphicon-forward"></i></button>
							    <button type="button" id="multiselect_rightSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-right"></i></button>
							    <button type="button" id="multiselect_leftSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-left"></i></button>
							    <button type="button" id="multiselect_leftAll" class="btn btn-block"><i class="glyphicon glyphicon-backward"></i></button>
							  </div>
							  <div class="col-sm-3">
							    <select name="to" id="multiselect_to" class="form-control" size="8" multiple="multiple">
							    <c:forEach var="entry" items="${selected}">
		                				<option value='${entry.id}'>${entry.fieldTitle}</option>
									</c:forEach>
							    </select>
							  </div>
							</div>
	  		  			
	  		  		</div>
    				<div class="form-group">
	  		  			<label class="control-label col-sm-2" for="email">Add Report Filter(s):</label>
	  		  			<div class="col-sm-8">
	  		  			<div align="right"><button type="button" class="btn btn-xs btn-success" onclick="openaddnewcriteria();">Add Criteria</button></div>
	  		  			<br/>
							<table id="reportcriteria" width="100%" class="BCSTable">
								  <thead>
								    <tr style="border-bottom:1px solid grey;" class="listHeader">
		      						<td width="45%" class="listdata">Field Name</td>
		      						<td width="15%" class="listdata">Operator</td>
		      						<td width="30%" class="listdata">Criteria Value</td>
		      						<td width="10%" class="listdata">Options</td>
		      						</tr>
		      					</thead>
		      					<tbody>
		      					
											
		      					
		      					
		      					<c:forEach var="entry" items="${conditions}">
		      						<c:choose>
						      			<c:when test="${entry.fieldType eq 'VARCHAR' }">
						      				<tr style="border-bottom:1px solid silver;"><td class="field_content">${entry.fieldName}<input type='hidden' id='fieldid' name ='fieldid' value='${entry.fieldId}'></td>
											<td class="field_content">${entry.operatorString}<input type='hidden' id='operatorid' name ='operatorid' value='${entry.operatorId}'></td>
											<td class="field_content">${entry.cText}<input type='hidden' id='ctext' name ='ctext' value='${entry.cText}'>
											<input type='hidden' id='selectid' name ='selectid' value='-1'><input type='hidden' id='startdate' name ='startdate' value=''>
											<input type='hidden' id='enddate' name ='enddate' value=''></td>
											<td class="field_content">
											<button type="button" class="btn btn-xs btn-danger" onclick="$(this).closest('tr').remove();deletecriteriarow('${entry.id}');">Del</button>
											<input type='hidden' id='cid' name ='cid' value='${entry.id}'></td></tr>
						      			</c:when>
						      			<c:when test="${entry.fieldType eq 'DDLIST' || entry.fieldType eq 'FILE' }">
						      				<tr style="border-bottom:1px solid silver;"><td class="field_content">${entry.fieldName}<input type='hidden' id='fieldid' name ='fieldid' value='${entry.fieldId}'></td>
											<td class="field_content">${entry.operatorString}<input type='hidden' id='operatorid' name ='operatorid' value='${entry.operatorId}'></td>
											<td class="field_content"><input type='hidden' id='ctext' name ='ctext' value=''>${entry.selectText}
											<input type='hidden' id='selectid' name ='selectid' value='${entry.selectId}'><input type='hidden' id='startdate' name ='startdate' value=''>
											<input type='hidden' id='enddate' name ='enddate' value=''></td>
											<td class="field_content">
											<button type="button" class="btn btn-xs btn-danger"  onclick="$(this).closest('tr').remove();deletecriteriarow('${entry.id}');">Del</button>
											<input type='hidden' id='cid' name ='cid' value='${entry.id}'></td></tr>
						      			</c:when>
						      			<c:when test="${entry.fieldType eq 'DATE' }">
						      				<tr style="border-bottom:1px solid silver;"><td class="field_content">${entry.fieldName}<input type='hidden' id='fieldid' name ='fieldid' value='${entry.fieldId}'></td>
											<td class="field_content">${entry.operatorString}<input type='hidden' id='operatorid' name ='operatorid' value='${entry.operatorId}'></td>
											<td class="field_content"><input type='hidden' id='ctext' name ='ctext' value=''>
											<input type='hidden' id='selectid' name ='selectid' value='-1'>
											<c:choose>
												<c:when test="${entry.operatorId eq 'BT'}">
													${entry.startDate} AND ${entry.endDate}
												</c:when>
												<c:otherwise>
													${entry.startDate}
												</c:otherwise>
											</c:choose>
											<input type='hidden' id='startdate' name ='startdate' value='${entry.startDate}'>
											<input type='hidden' id='enddate' name ='enddate' value='${entry.endDate}'></td>
											<td class="field_content">
											<button type="button" class="btn btn-xs btn-danger" onclick="$(this).closest('tr').remove();deletecriteriarow('${entry.id}');">Del</button>
											<input type='hidden' id='cid' name ='cid' value='${entry.id}'></td></tr>						      				
						      			</c:when>
						      			<c:when test="${entry.fieldType eq 'YESNO' }">
						      				<tr style="border-bottom:1px solid silver;"><td class="field_content">${entry.fieldName}<input type='hidden' id='fieldid' name ='fieldid' value='${entry.fieldId}'></td>
											<td class="field_content">${entry.operatorString}<input type='hidden' id='operatorid' name ='operatorid' value='${entry.operatorId}'></td>
											<td class="field_content">${entry.cText}<input type='hidden' id='ctext' name ='ctext' value='${entry.selectId}'>
											<input type='hidden' id='selectid' name ='selectid' value='-1'><input type='hidden' id='startdate' name ='startdate' value=''>
											<input type='hidden' id='enddate' name ='enddate' value=''></td>
											<td class="field_content">
											<button type="button" class="btn btn-xs btn-danger" onclick="$(this).closest('tr').remove();deletecriteriarow('${entry.id}');">Del</button>
											<input type='hidden' id='cid' name ='cid' value='${entry.id}'></td></tr>
						      			</c:when>
						      			<c:otherwise>
						      			</c:otherwise>			      			
						      		</c:choose>
		      					</c:forEach>
		      					</tbody>
		      				</table>
		      			</div>
		      		</div>
	    			<div class="form-group">        
			      		<div class="col-sm-offset-2 col-sm-10">
			      		<br />
			        	<button type="button" class="btn btn-xs btn-primary" onclick="updateCustomReport();">Update Report</button>
			      		</div>
	    			</div>			
				</form>
			</div>
		</div>
	<div id="myModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle">Add New Criteria</h4>
                    	<div class="alert alert-danger" style="display:none;" id="dalert" align="center">
  							<span id="demessage"></span>
						</div>
						<div class="alert alert-success" style="display:none;" id="dalerts" align="center">
  							<span id="dsmessage"></span>
						</div>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title2">Select Field:</p>
                    <p>
                    	<select class="form-control" id="selectfields" style="width:auto;" onchange="getfieldproperties();">
		                	<option value="-1">Please select field</option>
		                	<c:forEach var="entry" items="${allfields}">
		                		<option value='${entry.id}'>${entry.fieldTitle}</option>
							</c:forEach>
				  		</select>
				  		<input type="hidden" id="hidfieldtype" name="hidfieldtype">
                    </p>
                    <p>
                    <div id="divoperator" style="display:none;">
                    	<select class="form-control" id="selectoperator" style="width:auto;" onchange="checkfieldtype();">
		                	<option value="-1">Select operator</option>
				  		</select>
                    </div>
                    </p>
                    <p>
                    <div id="divcriteriatext" style="display:none;">
                    	<input class="form-control" id="criteriatext" name="criteriatext" placeholder="Search text" type="text">
                    </div>
 		    		</p>
 		    		<p>
                    <div id="divdropdown" style="display:none;">
                    	<select class="form-control" id="selectrelated" style="width:auto;">
                    		
				  		</select>
                    </div>
                    </p>
 		    		<p>
                    <div id="divdates" style="display:none;">
                    	<p>
                    	<input class="form-control" id="sstartdate" name="sstartdate" placeholder="MM/DD/YYY" type="text">
                    	</p>
                    	
                    	<p>
                    		<div id="divenddate" style="display:none;">
                    		<p> and </p>
                    			<input class="form-control" id="senddate" name="senddate" placeholder="MM/DD/YYY" type="text">
                    		</div>
                    	</p>
                    </div>
                    </p>
                    <p>
                    <div id="divyesno" style="display:none;">
                    	<select class="form-control" id="selectyesno" style="width:auto;" onchange="checkfieldtype();">
		                	<option value="Y">Yes</option>
		                	<option value="N">No</option>
				  		</select>
                    </div>
                    </p>
				</div>
                <div class="modal-footer">
                     <button type="button" class="btn btn-xs btn-success"  id="buttonleft" onclick="addnewcriterarow();"></button>
                    <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
   		</div>
   	</div>		