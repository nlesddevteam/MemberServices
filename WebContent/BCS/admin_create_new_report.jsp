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
<link href="includes/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css">
<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
<script src="includes/js/jquery.min.js"></script>
<script src="includes/js/bootstrap.min.js"></script>
<script src="includes/js/bootstrapvalidator.min.js"></script>
<script src="includes/js/bootstrap-datepicker.min.js"></script>
<script src="includes/js/multiselect.js"></script>
<script src="includes/js/bcs.js"></script>
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
		 <div class="BCSHeaderText">Create New Report</div>			 
			
			You can run reports on all the data collected in this system and export to excel, CSV format, and/or print. To begin follow the steps below: 
				
			 
			  <br /> <br />
		  		  <form class="form-horizontal"  id="contact-form-up" name="contact-form-up" method="post" action="runNewReport.html" target="blank" onsubmit="showcompletemessage();" >
		  		  <div id="divstep1">
		  		  <div class="BCSHeaderText">Select Report Table(s):</div>
		  		  1. Select the data table you wish to use to generate a report on from the list below and then goto next step.
		  		  <br/><br/>
		  		  	<div class="form-group">		  		  		
		  		  		<div class="col-md-12">
		                <select class="form-control" id="selecttables" name="selecttables" style="width:auto;">
		                	<option value="-1"> Select table</option>
		                	<c:forEach var="entry" items="${tables}">
		                		<option value='${entry.id}'>${entry.tableTitle}</option>
							</c:forEach>
				  		</select>			  		
			      
					   		      		
			      		</div>
			      		</div>
			      		<div class="form-group">
			      		<div class="col-md-5">
			      		<button type="button" class="btn btn-primary" id="btnstep1next" onclick="openstep2();">Goto Step 2</button>
			      		</div>
	    			</div>
	    			</div>
	    			
	    			
	    			
	    			<div id="divstep2" style="display:none;">
	    			
	    			<div class="BCSHeaderText">Select Report Field(s):</div>
	    			
	    			2. Now select what fields of the data table you wish to include in the report and proceed to next step. You can select any number of fields to include.
	    			
	    			<br/><br/>
		  		  		<div class="form-group">  		  			
		  		  				
		  		  			
		  		  			<div class="col-md-9">
		  		  				<div class="row">
								  <div class="col-xs-4">
								    <select name="from" id="multiselect" class="form-control" size="8" multiple="multiple">
								    
								    </select>
								  </div>
								  <div class="col-xs-1">
								    <button type="button" id="multiselect_rightAll" class="btn btn-block"><i class="glyphicon glyphicon-forward"></i></button>
								    <button type="button" id="multiselect_rightSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-right"></i></button>
								    <button type="button" id="multiselect_leftSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-left"></i></button>
								    <button type="button" id="multiselect_leftAll" class="btn btn-block"><i class="glyphicon glyphicon-backward"></i></button>
								  </div>
								  <div class="col-xs-4">
								    <select name="to" id="multiselect_to" class="form-control" size="8" multiple="multiple">
								    </select>
								  </div>
								</div>
		  		  			</div>
		  		  		</div>
		  		  		
		  		  		<div class="form-group">
			      		<div class="col-md-5">
		  		  		<button type="button" class="btn btn-default" id="btnstep2prev" onclick="openstep1prev();">Back to Step 1</button>
			        	<button type="button" class="btn btn-primary" id="btnstep2next" onclick="openstep3();">Goto Step 3</button>        
			      			 
	    				</div>
	    			</div>
	    			</div>
	    			<div id="divstep3" style="display:none;">
		  		  		<div class="BCSHeaderText">Add Report Filter(s):</div>
		  		  		
		  		  		3. You can now add some report criteria to filter your data for your generated report, if necessary. If you do not select any criteria, all data from the data table and fields selected in steps 2 and 3 will be displayed.
		  		  		
		  		  		<br/><br/>
		  		  		<div class="form-group">
		  		  			<div class="col-md-12">
								<table id="reportcriteria" class="BCSTable" width="100%">
			      					<thead>
			      					<tr style="background-color:#ffd333;">
			      						<th width="40%" class="listdata">Field Name</th>
			      						<th width="20%" class="listdata">Criteria Operator</th>
			      						<th width="30%" class="listdata">Criteria Value</th>
			      						<th width="10%" class="listdata">Options</th>
			      					</tr>
			      					</thead>
			      					<tbody>
			      					</tbody>
			      				</table>
			      			</div>
			      		</div>
			      		<br/>
		  		  		<div class="form-group">          
			      			<div class="col-md-5">
			      					
			      				<button type="button" class="btn btn-default" id="btnstep3prev" onclick="openstep2prev();">Back to Step 2</button>
			      				<button type="button" class="btn btn-success" id="btnstep3prev" onclick="openaddnewcriteria();">Add Criteria</button>
			        			<button type="button" class="btn btn-primary" id="btnstep3next" onclick="openstep4();">Goto Step 4</button>
			      			</div>
	    				</div>
	    			</div>
	    			<div id="divstep4" style="display:none;">
		  		  		<div class="BCSHeaderText">Run Report</div>
		  		  		
		  		  		Select the report function below. If you only <b>Run Report</b>, it will open the data in another browser window or tab for exporting to a spreadsheet or printing. 
		  		  		If you wish to save the report for future exporting/viewing of the data, select <b>Run and Save Report</b> and give it a name. 
		  		  		The report will then be listed under the <b>My Reports</b> menu item to run in the future.
		  		  		
		  		  		<br/><br/>
		  		  		<div class="form-group">
  		  					<div class="col-md-5">
  		  						
              							<input type="radio" name="runreport" id="runreport" value='R' checked> &nbsp; Run Report<br/>
										<input type="radio" name="runreport" id="runreport" value='RS'> &nbsp; Run and Save Report
								
							</div>
						</div>
						<div class="form-group">        
			      			<div class="col-md-10">
			      				<input class="form-control" id="reportname" name="reportname" placeholder="Report name" type="text">
			      			</div>
	    				</div>
		  		  		<div class="form-group">           
			      			
			      			<div class="col-md-5">
								<button type="button" class="btn btn-default" id="btnstep4prev" onclick="openstep3prev();">Back to Step 3</button>
			        			<button type="submit" class="btn btn-success" id="btnstep4next" >Run Report!</button>
			      			</div>
	    				</div>
					</div>
					<div id="divstep5" style="display:none;">
					<div class="alert alert-success" id="details_success_message" style="margin-top:10px;margin-bottom:10px;padding:5px;">REPORT GENERATED SUCCESSFULLY. Report data will show in a new browser tab or window.</div>
		  		  		
		  		  	</div>	    				
				</form>
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
		                	<option value="Y">Add</option>
		                	<option value="N">Cancel</option>
				  		</select>
                    </div>
                    </p>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary"  id="buttonleft" onclick="addnewcriterarow();">Add</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="buttonright">Cancel</button>
                </div>
            </div>
   		</div>
   	</div>



	<script src="includes/js/jQuery.print.js"></script>	
