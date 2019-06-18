<%@ page language="java"
         isThreadSafe="false"%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>


	<head>
		<title>NLESD - Member Services - Personnel-Package</title>
	</head>
	
	<body>	  
	<esd:SecurityCheck permissions="PERSONNEL-ADMIN-CREATE-SUBLIST" />
	 <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Create Sub-List</b><br/>Fields marked with * are required.</div>
      			 	<div class="panel-body">  
	
	                <form id="frmCreateSubList" action="createSubList.html" method="post">
	                                  
                        <%if(request.getAttribute("msg")!=null){%>
                          <div class="alert alert-danger">
                            
                              <%=(String)request.getAttribute("msg")%>
                          
                          </div>
                        <%}%>	
	                                    
	<div class="input-group">
    <span class="input-group-addon"> List Type*:</span>
    <job:SubstituteListType id="list_type" cls="form-control" />
  	</div>
	                                    
	<div class="input-group">
    <span class="input-group-addon">List Title*:</span>
	<input type="text" name="list_title" id="list_title" class="form-control">
	</div>
	                                     
	<div class="input-group">
    <span class="input-group-addon">Region*:</span>
	<job:Regions id="list_region" cls="form-control" />
	</div>
	
	<div class="panel-group" id="accordion">
  		<div class="panel panel-default">
    		<div class="panel-heading">
      			<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapse1" style="font-size:14px;color:DimGrey;">Select Grade Levels*:</a>
      			</h4>
   			</div>
    	<div id="collapse1" class="panel-collapse collapse">
      	<div class="panel-body">
      	
      	<job:Grades id="list_grades" cls="form-control"/>
      	</div>
    	</div>
  		</div>
  		<div class="panel panel-default">
    		<div class="panel-heading">
      			<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapse2" style="font-size:14px;color:DimGrey;">Select Subject Areas*:</a>
      			</h4>
   			</div>
    	<div id="collapse2" class="panel-collapse collapse">
      	<div class="panel-body">
      	<job:MajorMinor id="list_subjects" cls="form-control"/>
      	
      	</div>
    	</div>
  		</div>
	</div>	
		
	<div class="input-group">
    	<span class="input-group-addon">Expiry Date*:</span>
		<input class="form-control" type="text" name="expiry_date" id="expiry_date" readonly />
	</div>								                            
    
    <br/>
    <div align="center">
    	<input type="submit" value="Create Sub List" class="btn btn-success btn-xs" /> &nbsp; <a href="admin_index.jsp" class="btn btn-danger btn-xs">Cancel</a>
    </div>          
    <br/><br/>      
          <%if(request.getAttribute("msg")!=null){%>
            <div class="alert alert-danger">	                                        
                <%=(String)request.getAttribute("msg")%>
            </div>  
          <%}%>
	                                 
	      </form>
	      </div></div></div>  
	      
	  	<script language="JavaScript">  
			  $('document').ready(function(){
					$("#expiry_date").datepicker({
				      	changeMonth: true,//this option for allowing user to select month
				      	changeYear: true, //this option for allowing user to select from year range
				      	dateFormat: "dd/mm/yy"
				      
				 	});
			  });  
  		</script>     
	                            
	</body>
</html>
