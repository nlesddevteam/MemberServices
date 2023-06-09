<%@ page language="java"
         import="java.util.*,
                 java.text.*,com.awsd.security.*,
                 com.esdnl.personnel.v2.model.sds.bean.LocationBean,
                 com.esdnl.personnel.v2.utils.StringUtils,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2" %>
 
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW-ETHICS-DEC" />

<c:set var="permanentVal" value="0" />
<html>
	<head>
		<title>View Latest Ethics and Conduct Declarations Report</title>				
		<script type="text/javascript" src='includes/js/encoder.js'></script>
	<style>
		.allocation-addon-text {background-color: #e6ffe6;color:Black; }
		input {border: 1px solid silver;}
	</style>
	
	
			
	</head>
	
	<body>	
	<form id="frm-add-allocation">
	                                	
	     <input id='hdn-allocation-id' type='hidden' value='' />
	                                	
	     <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading no-print"><b>View Latest Ethics and Conduct Declarations Report</b></div>
      			 	<div class="panel-body">       				            
	                                          
	                       <div class="container-fluid no-print">	                       	   
								<div class="row">
									<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">	                        
		                             	<div class="input-group" id='div_location'>
		    								<span class="input-group-addon formTitleArea">Select Report Period</span>
		    								<select id="selectdays" name="selectdays" class="form-control">
		    								<option value="1">*** Please Select ***</option>
		    								<option value="1">Last Day</option>
		    								<option value="2" >Last 2 Days</option>
		    								<option value="7">Last Week</option>
		    								<option value="14">Last 2 Weeks</option>
		    								<option value="21">Last 3 Weeks</option>
		    								<option value="28">Last Month</option>
		    								<option value="60">Last 2 Months (Slowing Running)</option>
		    								</select>		
		    								
		    												    
		  								</div>
		  							</div>
	  							
									<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4" style="text-align:right;">
									  <a href='#' title='Print this page (pre-formatted)' class="btn btn-sm btn-danger" onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});">Print Page</a></li>
                              </div>	
	                       	    </div>
	                       	    	<br/><br/>     
	  						</div>	

           		
	                             		
<!--PERMANENT POSITIONS TAB ---------------------------------------------------------->	                             		
                           							
	                             								
	<div class="panel-group">
  <div class="panel panel-success">
    <div class="panel-heading">
      <h4 class="panel-title"> <b><span class="theSchoolName"></span> Employees</b>
      </h4>
    </div>
    
      <div class="panel-body">  
    	  <div id='permanent-positions-header-row' style='display:block;'>
    	
    	
					   <table id='permanent-positions-table' class="empTable table table-sm table-striped" style="font-size:11px;background-color:#FFFFFF;border:0px;">
						    
						    <thead class="thead-light">
								      <tr>
								       <th width='20%'>EMPLOYEE</th>
								       <th width='20%'>LOCATION</th>
								       <th width='20%'>EMAIL</th>
								        <th width='20%'>UPLOAD DATE</th>
								        <th width='20%'>OPTIONS</th>
								      </tr>
								    </thead>
								 <tbody>
								
								 </tbody>  
				  		</table>
				
    	

    	
    	
    	</div>
    	
    	
    	
    	
    	
</div>
      
    
  </div>
</div>	



       
</div>	
</div>
</div>
</form>
	                 
	                
<script>
$(function() {
	$('#selectdays').on('change',function(){		
		
		if ( $.fn.DataTable.isDataTable( '.empTable' ) ) {
				$('.empTable').DataTable().destroy();
				} 
		if ( $.fn.DataTable.isDataTable( '.empvTable' ) ) {
			$('.empvTable').DataTable().destroy();
			} 
		
				
		//var optionText = $("#selectdays option:selected").val();
		getEmployeesByDays($("#selectdays").val());
		
	});
});

//get employees list
function getEmployeesByDays(ndays)
{
	var empps=false;
	var empvs=false;
	var datalist=[];
		$.ajax(
     			{
     				type: "POST",  
     				url: "viewEthicsReportByDays.html",
     				data: {
     					numdays: ndays
     				}, 
     				success: function(xml){
     					
     					if($(xml).find('MESSAGE').text() ==  "FOUND"){
     						//$('#reportdata tr:gt(0)').remove()
     						$("#permanent-positions-table").find("tr:gt(0)").remove();
     						
     						//$(".empTable").dataTable().destroy();
     						
     						//$("#reportdata tbody").empty();
     						//refreshdatatable();
     	     					//now populate schools
     	     					
								var noemployees = true;
     							$(xml).find('PEMPLOYEE').each(function(){
     								var showrecord=false;
     								
     								if(datalist.length > 0){
     									if(datalist.indexOf($(this).find("APPEMAIL").text()) >= 0){
     										//do nothing
     										showrecord=false;
     									}else{
     										datalist.push($(this).find("APPEMAIL").text());
     										showrecord=true;
     									}
     								}else{
     									datalist.push($(this).find("APPEMAIL").text());
     									showrecord=true;
     								}
     								if(showrecord){
     									noemployees=false;
         								var newrow ="<tr>";
         								newrow += "<td>" + $(this).find("NAME").text() + "</td>";
         								newrow += "<td>" + $(this).find("LOCATION").text() + "</td>";
         								newrow += "<td>" + $(this).find("APPEMAIL").text() + "</td>";
         								newrow += "<td>" + $(this).find("CDATE").text() + "</td>";
         								//now we see what buttons we need
         								newrow += "<td>";
         								newrow += "<a title='View COE Document' class='viewdoc btn btn-xs btn-info' href='viewApplicantDocument.html?id=" + $(this).find("DOCUMENTID").text() + "' target='_blank'>COE DOC</a>&nbsp;";
         								newrow += "<a title='View User Profile' class='btn btn-xs btn-primary' href='viewApplicantProfile.html?sin=" + $(this).find("SIN").text() + "' target='_blank'>PROFILE</a>";
         								newrow += "</td>";
         								
         								newrow +="</tr>"
         								$('#permanent-positions-table tbody').append(newrow);
    			                      
         								empps=true;
     								}

     							});
     							
     							
     							if(noemployees){
     								var newrow ="<tr><td colspan='4'>No Records Found</td></tr>";
     								$('#permanent-positions-table tbody').append(newrow);
     							}
     							if(empps){
     								$("#permanent-positions-header-row").show();     							
     								 
     							     								
     								$('.empTable').dataTable({
     									
     									"order" : [[0,"asc"]],		
     									//"bPaginate": false,
     									"lengthMenu" : [ [ 25, 50, 100, 200, -1 ], [ 25, 50, 100, 200, "All" ] ],
     									responsive: true,
     									     									
     									
     								});
     	     						
     							}
     	     					
     	     					$(".dataTables_filter").addClass("no-print");
     					}
     				},
     				  error: function(xhr, textStatus, error){
     				      alert(xhr.statusText);
     				      alert(textStatus);
     				      alert(error);
     				  },
     				dataType: "text",
     				async: false
     			}
     		);   			
		

	return true;
	
}


</script>	


		
        
        
        
                  
</body>

</html>