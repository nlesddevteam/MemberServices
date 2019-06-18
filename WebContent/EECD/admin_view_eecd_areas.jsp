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
	 
	  $("#eecdlist").DataTable(
		{"order": [[ 1, "asc" ]],			
		"lengthMenu": [[25, 50, 100, -1], [25, 50, 100, "All"]]
		}	  
	  );
 });
    </script>	
	
	</head>

  <body>
  
 
  
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Area Administration </b></div>
      			 	<div class="panel-body">
      			 	Below is a list of all EECD Areas viewable for teachers to select their choices.  New areas can be added by using the Add New Area button and any areas no longer needed
					 can be removed using the delete link.  If a change in the name is required then please click the View button and make the required change.
                 
                 	         <jsp:include page="eecd_menu.jsp"/>
										
					 		<%if(request.getAttribute("msg")!=null){%>								
								<div id="fadeMessage" class="alert alert-danger">${ msg }</div>  									
							<%} %>  
							
							 <div class="alert alert-success" id="successMsg" style="text-align:center;display:none;" align="center"></div>
							 <div class="alert alert-danger" id="errorMsg" style="text-align:center;display:none;" align="center"></div>
					
						    <div style="padding-left:5px; padding-right:5px; padding-top:10px; border-top:1px solid Silver;">
							
							<c:choose>
							<c:when test="${fn:length(areas) gt 0}">
							
								<table id="eecdlist" class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">
									    <thead>
									      <tr>
									        <th width='85%'>AREA DESCRIPTION</th>
									        <th width='15%'>OPTIONS</th>
									      </tr>
									    </thead>
									    <tbody>
							
								<c:forEach items='${ areas }' var='area'>
                   					<tr id="div${area.id}"> 
     									<td>${ area.areaDescription } </td>   
										
										<td>
											<a href="viewAreaApprovedList.html?aid=${area.id}&adescription=${ area.areaDescription }" class="btn btn-primary btn-xs">VIEW</a>
											<esd:SecurityAccessRequired permissions="EECD-VIEW-ADMIN">
											<a href="#" class="btn btn-danger btn-xs"  onclick="opendeletedialog('${area.id}','${ area.areaDescription }');">DELETE</a>
											</esd:SecurityAccessRequired>
										</td>
									</tr>	   
									
								</c:forEach> 
								
								 </tbody>
                                 </table>	
								
							</c:when>
							<c:otherwise>
							No Areas Available
							</c:otherwise>
							</c:choose>
							</div>
	<div align="center"><a class="btn btn-xs btn-danger" onclick="loadingData()" href="javascript:history.go(-1);">Back</a></div> 
							
</div></div></div>					
<!-- Modal View -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
			<div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Add New Area</h4>
                </div>
                <div class="modal-body">
                	<p class="text-warning" id="title2">Area Description:</p>
                		<div class="alert alert-danger" role="alert" id="errMsg" style="display:none;"></div>
                		<div class="alert alert-success" role="alert" id="scsMsg" style="display:none;"></div>
						
                    <p>
                    <input class="form-control" id="areadescription" name="areadescription" placeholder="Area Description" type="text">
                    </p>
                    
				</div>
                <div class="modal-footer">
                        <button type="button" class="btn btn-xs btn-primary" onclick="addnewarea();">Add</button>
        				<button type="button" class="btn btn-xs btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </div>
      
    </div>
  </div>

<!-- Modal View -->
  <div class="modal fade" id="myModald" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
			<div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitled">Delete Area</h4>
                </div>
                <div class="modal-body">
                	<div class="alert alert-danger" role="alert" id="errMsgd" style="display:none;"></div>
                	<div class="alert alert-success" role="alert" id="scsMsgd" style="display:none;"></div>
                	<p>
                    <div class="modal-body">
                    <p class="text-warning" id="title1dd"><span id="spantitle1d" name="spantitle1d"></span></p>
                    <p class="text-warning" id="title2dd"><span id="spantitle2d" name="spantitle2d"></span></p>
				</div>
                    </p>
                    
				</div>
                <div class="modal-footer">
                        <button type="button" class="btn btn-xs btn-primary" id="btndelete">Delete</button>
        				<button type="button" class="btn btn-xs btn-default" data-dismiss="modal">Cancel</button>
        				<input type="hidden" id="hiddenid">
                </div>
            </div>
      
    </div>
  </div>
<!-- Modal View -->     
</body>
</html>	
			

			