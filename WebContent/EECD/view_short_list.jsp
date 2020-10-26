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
		
		$("#areaApproval").DataTable(
				{"order": [[ 0, "asc" ]],			
				"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
				}	  
			  );
		
		 });
    </script>				
	</head>

  <body>
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Area Approved Shortlist for ${areadescription}</b></div>
      			 	<div class="panel-body"> 
  							Below is a list of all teachers shortlisted for the selected EDU Area.  Please use the Remove from Shortlist button to remove a teacher from the shortlist for this area.  
							To export shortlist  then please use the Export Shortlist button.
							
							<jsp:include page="eecd_menu.jsp"/>
										
					 		<%if(request.getAttribute("msg")!=null){%>								
								<div id="fadeMessage" class="alert alert-danger">${ msg }</div>  									
							<%} %>                 	
                 	
                 			 <div class="alert alert-success" id="successMsg" style="text-align:center;display:none;" align="center"></div>
							 <div class="alert alert-danger" id="errorMsg" style="text-align:center;display:none;" align="center"></div>
					 		
					 		<div style="padding-left:5px; padding-right:5px; padding-top:10px; border-top:1px solid Silver;">
								<c:choose>
								<c:when test="${fn:length(areas) gt 0}">
								<table id="areaApproval" class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">								
					  				<thead>
								<tr>
									<th width="25%">TEACHER</th>	
									<th width="25%">SCHOOL</th>									        
									<th width="25%">AREA</th>
									<th width="25%">OPTIONS</th>
									   
								</tr>
								</thead>
								 <tbody>
								<c:forEach items='${ areas }' var='area'>
                    					<tr id="div${area.id}"> 
	     									<td>${ area.teacherName}</td>	
									        <td>${ area.schoolName}</td>								        
											<td>${ area.areaDescription }</td>
											<td style="text-align:right;">   
											<c:if test = "${iscompleted eq false}">
												<span id="divremove"><a href="#" class="btn btn-danger btn-xs" title='Remove from Shortlist' style="color:white;" onclick="openremovefromshortlistdialog('${area.id}','${fn:replace(area.teacherName,"'","\\'")}','${area.areaDescription}','${fn:replace(area.schoolName,"'","\\'")}');">REMOVE</a></span>
											</c:if>
											<c:if test = "${iscompleted eq true}">
												<span id="divremove" style="display:none;"><a href="#" title='Remove from Shortlist' class="btn btn-danger btn-xs" style="color:white;" onclick="openremovefromshortlistdialog('${area.id}','${fn:replace(area.teacherName,"'","\\'")}','${area.areaDescription}','${fn:replace(area.schoolName,"'","\\'")}');">REMOVE</a></span>
											</c:if>
											<c:if test = "${isquestions eq 'Y'}">										
												<c:choose>
													<c:when test = "${area.hasAnswers eq true}">
														<a href="#" class="btn btn-primary btn-xs" onclick="viewanswers('${listid}','${area.personnelId}');">VIEW ANSWERS</a>
													</c:when>
													<c:otherwise>
														<span class="btn btn-xs btn-danger" title="Not Complete/Not Answered." onclick="viewanswers('${listid}','${area.personnelId}');"><span class="glyphicon glyphicon-info-sign"></span> NOT COMPLETE</span>														
													</c:otherwise>
												</c:choose>
											</c:if> 
											</td>  
										</tr>
								</c:forEach>
								</tbody> 
								</table>
								</c:when>
								<c:otherwise>
								Sorry, no teachers shortlisted for this area.
								</c:otherwise>	
								</c:choose>			

    				<esd:SecurityAccessRequired permissions="EECD-VIEW-ADMIN,EECD-VIEW-SHORTLIST">
    					<div align="center">
    					<c:choose>
    						<c:when test="${iscompleted eq false}">
    							<div id="divcomplete">					 
					 				<a href='#' class="btn btn-xs btn-success" style="color:White;" title="Mark Shortlist As Complete" onclick="markshortlistcomplete('${listid}','C')">MARK SHORTLIST COMPLETE</a> 
					 			</div>
					 			<div id="divunlock" style="display:none;">					 
					 				<a href='#' class="btn btn-xs btn-warning" style="color:White;" title="Reopen/Unlock Shortlist" onclick="markshortlistcomplete('${listid}','U')">REOPEN/UNLOCK SHORTLIST</a>
					 				<a href='exportShortlist.html?aid=${listid}' class="btn btn-xs btn-info" style="color:White;" title="Export Shortlist" target="_blank">EXPORT SHORTLIST</a> 
					 			</div>  
    						</c:when>
    						<c:otherwise>    						
    							<div id="divcomplete" style="display:none;">					 
					 				<a href='#' class="btn btn-xs btn-success" style="color:White;" title="Mark Shortlist As Complete" onclick="markshortlistcomplete('${listid}','C')">MARK SHORTLIST COMPLETE</a> 
					 			</div>
					 			<div id="divunlock">					 
					 				<a href='#' class="btn btn-xs btn-warning" style="color:White;" title="Reopen/Unlock Shortlist" onclick="markshortlistcomplete('${listid}','U')">REOPEN/UNLOCK SHORTLIST</a>
					 				<a href='exportShortlist.html?aid=${listid}' class="btn btn-xs btn-info" style="color:White;" title="Export Shortlist" target="_blank">EXPORT SHORTLIST</a> 
					 			</div>  						
    						</c:otherwise>
    					</c:choose>
    					</div>
					 </esd:SecurityAccessRequired>
	
	
</div></div></div></div>
	
	
	
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
                    School
                    <p class="text-warning" id="title2dd"><span id="spantitle2d" name="spantitle2d"></span></p>
                    <p>
                    Area
                    <p class="text-warning" id="title2dd"><span id="spantitle3d" name="spantitle3d"></span></p>
                    
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
       <div class="modal fade" id="myModalq" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
			<div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitleq"><span id="maintitlespanq"></span></h4>
                </div>
                <div class="modal-body">
					<form id="frmquestions" action="">
                   
                    <table id="tquestions" width="100%" style="font-size:11px;background-color:#FFFFFF;">
                    <tbody>                    
                    </tbody>			
                    </table>			
                    
                    </form>
                    
				</div>
                <div class="modal-footer">
                        <button type="button" class="btn btn-xs btn-primary" id="btnokq" name="btnok">OK</button>
        		</div>
                
            </div>
      
    </div>
  </div> 
<!-- Modal View -->     
</body>
</html>	