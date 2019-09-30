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
		"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
		}	  
	  );
 });
    </script>	
	
	
	</head>

  <body>
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b> Area Approved List for ${areadescription}</b></div>
      			 	<div class="panel-body"> 
							Below is a list all teachers approved for the selected EECD Area.  Please use the Add To Shortlist button to add a teacher to the shortlist for this area.  
							To view the full shortlist, remove a teacher from the list and/or close the list then please use the View Short List button.
							
                            <jsp:include page="eecd_menu.jsp"/>
 							<esd:SecurityAccessRequired permissions="EECD-VIEW-ADMIN,EECD-VIEW-SHORTLIST">					
									<div align="center"><a href='viewShortlist.html?aid=${listid}&adescription=${areadescription}' class="btn btn-xs btn-warning"  title="View Shortlist">View Shortlist</a>
									<a href='viewAllList.html?aid=${listid}' class="btn btn-xs btn-warning"  title="View All Applicants">View All Applicants</a>
									</div>
									<br />
									<div align="center" class="alert alert-info"><b>${ slisted }</b> teacher(s) has/have been added to the shortlist.</div>
							</esd:SecurityAccessRequired>		
	
	     								
					 		<%if(request.getAttribute("msg")!=null){%>								
								<div id="fadeMessage" class="alert alert-danger">${ msg }</div>  									
							<%} %>                 	
                 	
                 			 <div class="alert alert-success" id="successMsg" style="text-align:center;display:none;" align="center"></div>
							 <div class="alert alert-danger" id="errorMsg" style="text-align:center;display:none;" align="center"></div>
					 		
					 		<div style="padding-left:5px; padding-right:5px; padding-top:10px; border-top:1px solid Silver;">
 								<c:choose>
								<c:when test="${fn:length(areas) gt 0}">  
								<table id="groupsApproval" class="table table-condensed table-striped" style="font-size:11px;background-color:#FFFFFF;">								
					  				<thead>
					            		<tr>
					                		<th width="25%">TEACHER</th>
					                		<th width="25%">SCHOOL</th>
					                		<th width="30%">AREA</th>
					                		<th width="20%">OPTIONS</th>
					            		</tr>
					            	</thead>
					            	<tbody>	
					            	
								<c:forEach items='${ areas }' var='area'>
                    					<tr id="div${area.id}"> 
	     									<td>${ area.teacherName}</td>	
									        <td>${ area.schoolName}</td>
									       	<td>${ area.areaDescription }</td>    
											<td style="text-align:right;">
												<c:if test = "${isquestions eq 'Y'}">										
													<c:choose>
													<c:when test = "${area.hasAnswers eq true}">
													<a href="#" class="btn btn-primary btn-xs" onclick="viewanswers('${listid}','${area.personnelId}');">VIEW ANSWERS</a>
													</c:when>
													<c:otherwise>
													<span class="btn btn-xs btn-danger" title="Not Complete/Not Answered.">NOT ANSWERED</span>													
													</c:otherwise>
													</c:choose>
												</c:if> 
												<c:if test = "${iscompleted eq false}">
													<a href="#" class="btn btn-success btn-xs" title="Add to Shortlist" onclick="openaddtoshortlistdialog('${area.id}','${fn:replace(area.teacherName,"'","\\'")}','${area.areaDescription}','${fn:replace(area.schoolName,"'","\\'")}','${area.areaId}');">+ SHORTLIST</a>
												</c:if>
											</td>   
										</tr>
								</c:forEach>
								     </tbody>
								     </table>
								</c:when>
								<c:otherwise>
									
								Sorry, no teachers approved for this area.
								</c:otherwise>	
								</c:choose>			
                                   <esd:SecurityAccessRequired permissions="EECD-VIEW-ADMIN,EECD-VIEW-SHORTLIST">					
									<div align="center"><a href='viewShortlist.html?aid=${listid}&adescription=${areadescription}' class="btn btn-xs btn-warning"  title="View Shortlist">View Shortlist</a>
									<a href='viewAllList.html?aid=${listid}' class="btn btn-xs btn-warning"  title="View All Applicants">View All Applicants</a>
									</div>
									<br />
									<div align="center"><b>${ slisted }</b> teacher(s) has/have been added to the shortlist.</div>
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
                    <tr><td colspan='2' align="center"><h4 class="modal-title" id="title1q"></h4></td></tr>
                    <tr><td colspan='2' align="center"><h4 class="modal-title" id="title2q"></h4></td></tr>
                    </tbody>			
                    </table>
                    </form>
                    
				</div>
                <div class="modal-footer">
                        <button type="button" class="btn btn-xs btn-primary" id="btnokq" name="btnok">DONE</button>
        		</div>
                
            </div>
      
    </div>
  </div>
<!-- Modal View -->     
</body>
</html>	