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
 
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW-COVID19" />

<c:set var="permanentVal" value="0" />
<html>
	<head>
		<title>Employees by School</title>				
		<script type="text/javascript" src='includes/js/encoder.js'></script>
	<style>
		.allocation-addon-text {background-color: #e6ffe6;color:Black; }
		input {border: 1px solid silver;}
	</style>
	
	
			
	</head>
	
	<body>	
	<form id="frm-add-allocation" action="addTeacherAllocation.html" method="post">
	                                	
	     <input id='hdn-allocation-id' type='hidden' value='' />
	                                	
	     <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading no-print"><b>View COVID19 Vaccination Status By School</b></div>
      			 	<div class="panel-body">       				            
	                                          
	                       <div class="container-fluid no-print">	                       	   
								<div class="row">
									<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">	                        
		                             	<div class="input-group" id='div_location'>
		    								<span class="input-group-addon formTitleArea">* Select School:</span>
		    								<jobv2:Locations id="lst_school" cls='form-control' />		
		    								
		    												    
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
								       <th width='35%'>EMPLOYEE</th>
								        <th width='35%'>STATUS</th>
								        <th width='10%'>UPLOAD DATE</th>
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
	$('#lst_school').on('change',function(){		
		
		if ( $.fn.DataTable.isDataTable( '.empTable' ) ) {
				$('.empTable').DataTable().destroy();
				} 
		if ( $.fn.DataTable.isDataTable( '.empvTable' ) ) {
			$('.empvTable').DataTable().destroy();
			} 
		
		var sch = $("#lst_school").val();
		$(".theSchoolName").text(sch);
		
		var optionText = $("#lst_school option:selected").val();
		$('.SchoolName').text(optionText);
		getEmployeesByLocation(optionText);
		
	});
});

//get employees list
function getEmployeesByLocation(locid)
{
	var empps=false;
	var empvs=false;
		$.ajax(
     			{
     				type: "POST",  
     				url: "viewCovid19ReportBySchool.html",
     				data: {
     					locationid: locid
     				}, 
     				success: function(xml){
     					var noemployees = true;
     					if($(xml).find('MESSAGE').text() ==  "FOUND"){
     						//$('#reportdata tr:gt(0)').remove()
     						$("#permanent-positions-table").find("tr:gt(0)").remove();
     						
     						//$(".empTable").dataTable().destroy();
     						
     						//$("#reportdata tbody").empty();
     						//refreshdatatable();
     	     					//now populate schools
							
     							$(xml).find('PEMPLOYEE').each(function(){
     								noemployees=false;
     								var newrow ="<tr>";
     								newrow += "<td>" + $(this).find("NAME").text() + "</td>";
     								//newrow += "<td>" + $(this).find("LOCATION").text() + "</td>";
     								newrow += "<td><span id='" + $(this).find("DOCUMENTID").text()  + "'>" + $(this).find("STATUS").html() + "</span></td>";
     								newrow += "<td>" + $(this).find("CDATE").text() + "</td>";
     								//now we see what buttons we need
     								if($(this).find("STATUSCODE").text() == "1"){
     									//show no buttons
     									newrow += "<td>";  
     									newrow += "<a class='btn btn-xs btn-primary' href='viewApplicantProfile.html?sin=" + $(this).find("SIN").text() + "' target='_blank'>PROFILE</a>";
     									newrow += "</td>";
     								}else if($(this).find("STATUSCODE").text() == "2"){
     									//show view and verify links
     									newrow += "<td>";
     									newrow += "<a class='btn btn-xs btn-primary' href='viewApplicantProfile.html?sin=" + $(this).find("SIN").text() + "' target='_blank'>PROFILE</a>&nbsp;";
     									newrow += "<a class='viewdoc btn btn-xs btn-info' href='viewApplicantDocument.html?id=" + $(this).find("DOCUMENTID").text() + "' target='_blank'>DOC</a>&nbsp; ";
     									newrow += "<a class='viewdoc  btn btn-xs btn-success' onclick=\"verifycovid19list('" + $(this).find("DOCUMENTID").text()+ "',this);\">VERIFY</a>" 
     									newrow += "</td>";
     								}else if($(this).find("STATUSCODE").text() == "3"){
     									newrow += "<td>";
     									newrow += "<a class='btn btn-xs btn-primary' href='viewApplicantProfile.html?sin=" + $(this).find("SIN").text() + "' target='_blank'>PROFILE</a>";
     									newrow += "<a class='viewdoc btn btn-xs btn-info' href='viewApplicantDocument.html?id=" + $(this).find("DOCUMENTID").text() + "' target='_blank'>DOC</a>";
     									newrow += "</td>";
     								}
     								
     								
     								newrow +="</tr>"
     								$('#permanent-positions-table tbody').append(newrow);
			                      
     								empps=true;

     							});
     							;
     							if(noemployees){
     								var newrow ="<tr><td colspan='4'>No Records Found</td></tr>";
     								$('#permanent-positions-table tbody').append(newrow);
     							}
     							if(empps){
     								$("#permanent-positions-header-row").show();     							
     								 
     							     								
     								$('.empTable').dataTable({
     									
     									"order" : [[0,"asc"]],		
     									"bPaginate": false,
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
