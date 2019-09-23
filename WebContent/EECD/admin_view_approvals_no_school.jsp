<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.school.*,
         		 com.awsd.personnel.*,                 
                 com.awsd.common.*,
		         java.util.*,
		         java.io.*,
		         java.text.*,
		         com.esdnl.util.*"%>  


<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<html>

	<head>
		<title>Education and Early Childhood Development Groups Admin</title>
	<script>
$('document').ready(function(){
$("#loadingSpinner").css("display","none");	
	 
	  $("#groupsApproval").DataTable(
		{"order": [[ 0, "asc" ]],			
		"lengthMenu": [[25, 50, 100, -1], [25, 50, 100, "All"]]
		}	  
	  );
 });
    </script>		
	
	</head>

  <body>
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Group(s) Approval</b></div>
      			 	<div class="panel-body"> 
 
							Please use the Approve and Decline buttons to update the status of the selections.  When the Approve or Decline button is pressed
							a screen will popup where you can enter notes about your decisions.  Once you have pressed OK on the popup screen, an email will be 
							sent back to the staff member with you decision and the notes that you typed.                  		
							
							
							<jsp:include page="eecd_menu.jsp"/>
										
					 		<%if(request.getAttribute("msg")!=null){%>								
								<div id="fadeMessage" class="alert alert-danger">${ msg }</div>  									
							<%} %>                 	
                 	
                 			 <div class="alert alert-success" id="successMsg" style="text-align:center;display:none;" align="center"></div>
							 <div class="alert alert-danger" id="errorMsg" style="text-align:center;display:none;" align="center"></div>
					 		
					 		<div style="padding-left:5px; padding-right:5px; padding-top:10px; border-top:1px solid Silver;">
                 	
							<table id="groupsApproval" class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">								
					  				<thead>
					            		<tr>
					                		<th width="25%">TEACHER</th>
					                		<th width="35%">AREA</th>
					                		<th width="15%">DATE SUBMITTED</th>
					                		<th width="25%">OPTIONS</th>
					            		</tr>
					            	</thead>
					            	<tbody>	
     							
								<c:forEach items='${ areas }' var='area'>
                    					<tr id="div${area.teacherAreaId}"> 
	     									<td>${ area.teacherName}</td>
									        <td>${ area.areaDescription }</td>									        
											<td>${area.dateSubmittedFormatted }</td>    
											<td>
												<a href="#" class="btn btn-success btn-xs" style="color:white;" onclick="openapprovedialog('${area.teacherAreaId}','${fn:replace(area.teacherName,"'","\\'")}','${area.areaDescription}','A')">APPROVE</a>
												<a href="#" class="btn btn-danger btn-xs" style="color:white;" onclick="openapprovedialog('${area.teacherAreaId}','${fn:replace(area.teacherName,"'","\\'")}','${area.areaDescription}','D')">DECLINE</a>
											</td>   
										</tr>
								</c:forEach> 
								</tbody>
								</table>
							</div>
							
</div></div></div>							
<!-- Modal View -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
			<div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled"><span id="maintitlespan" name="maintitlespan"></span></h4>
                </div>
                <div class="modal-body">

                	<p>
                    <div class="modal-body">
                    <p>
                    Teacher
                    <p class="text-warning" id="title1dd"><span id="spantitle1d" name="spantitle1d"></span></p>
                    <p>
                    Area
                    <p class="text-warning" id="title2dd"><span id="spantitle2d" name="spantitle2d"></span></p>
                    <p>
                    Notes
                    <p class="text-warning" id="notes"><textarea id="textnotes" name="textnotes" rows="2" cols=25""></textarea></span></p>
                    
				</div>
                    </p>
                    
				</div>
                <div class="modal-footer">
                        <button type="button" class="btn btn-xs btn-primary" id="btnok" name="btnok">OK</button>
        				<button type="button" class="btn btn-xs btn-default" data-dismiss="modal">CANCEL</button>
        				<input type="hidden" id="hiddenid">
                </div>
            </div>
      
    </div>
  </div>
<!-- Modal View -->     
</body>
</html>	