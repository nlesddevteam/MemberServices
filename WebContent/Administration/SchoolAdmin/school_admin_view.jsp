<%@ page language="java"
        session="true"
        import="java.util.*,
        com.awsd.security.*,
        com.awsd.school.*,
        com.awsd.personnel.*,
                java.text.*"%>
                
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<%
	User usr = null;	
  	Personnel principal = null;
  	Personnel[] asstprincipals = null;
  	int avalonCount=0; 
  	int centralCount=0;
  	int westernCount=0;
  	int labradorCount=0;
  	int provincialCount=0;
  	int otherCount=0;
  	int principalCount=0;
  	int uprincipalCount=0;
  	int asstprincipalCount=0;
  	int uasstprincipalCount=0;  
  	
    	  Collection<School> schools = null;
    	  schools = SchoolDB.getSchoolsAdminAll();	
    	
%>
<html>
  <head>
     <title>MemberServices Administration</title>
   
   <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
	</style>
	
	<script>		
		$('document').ready(function(){
			mTable = $(".schoolAdminTable").dataTable({
				"order" : [[1,"asc"]],
				  "lengthMenu": [[25,50,100, 250, -1], [25,50,100, 250, "All"]],	
				responsive: true,
				dom: 'Blfrtip',
		        buttons: [			        	
		        	//'colvis',
		        	{
		        	
	                extend: 'print',
	                title: '<div align="center"><img src="/MemberServices/Administration/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
	                messageTop: '<div align="center" style="font-size:18pt;">School Administration List</div>',
	                messageBottom: '<div class="alert alert-danger"><b>Confidentiality Warning:</b> This document and any attachments are intended for the sole use of the intended recipient(s), and contain privileged and/or confidential information. If you are not an intended recipient, any review, retransmission, printing, copying, circulation or other use 	of this message and any attachments is strictly prohibited.</div>',
	                	 exportOptions: {	                			                		 
	                         columns: [ 0,1,2,3,4,5],	                       
	                     }
	            },
	            { 
	       		 extend: 'excel',	
	       		 exportOptions: {        		       		 

	                    columns: [ 0,1,2,3,4,5],
	                 },
	       },
	       { 
	     		 extend: 'csv',	
	     		 exportOptions: {        		            		 

	                    columns: [ 0,1,2,3,4,5],
	               },
	     },
	         
		        ],			
								
				 "columnDefs": [
					 {
			                "targets": [6],			               
			                "searchable": false,
			                "orderable": false
			            }
			        ]
			});			
			
			$("tr").not(':first').hover(
			  function () {
			    $(this).css("background","yellow");
			  }, 
			  function () {
			    $(this).css("background","");
			  }
			);			
		
			$(".loadPage").show();
			$(".loadingTable").css("display","none");
			$("#loadingSpinner").css("display","none");
		});

		</script>
   
  </head>

<body>
<div class="siteHeaderGreen">School Administration Summary</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 

This page displays all current NLESD principals and assistant principal(s) school assignments. This list also includes past schools and buildings.

<br/><br/>
<b>Total Schools:</b> <span id="membersTotal"></span><br/>
<b>Avalon Schools:</b> <span id="avalonTotal"></span> &nbsp; 
<b>Central Schools:</b> <span id="centralTotal"></span> &nbsp; 
<b>Western Schools:</b> <span id="westernTotal"></span> &nbsp; 
<b>Labrador Schools:</b> <span id="labradorTotal"></span> &nbsp; 
<b>Provincial Schools:</b> <span id="provincialTotal"></span> &nbsp; 
<b>Other Buildings::</b> <span id="otherTotal"></span>
<br/>
<b>Principals Assigned:</b> <span id="principalTotal"></span>  &nbsp; 
<b>Schools no Principals Assigned:</b> <span id="uprincipalTotal"></span><br/>
<b>Assistant Principals Assigned:</b> <span id="asstprincipalTotal"></span>  &nbsp; 
<b>Schools no Assistant Principals Assigned:</b> <span id="uasstprincipalTotal"></span>

     <br/>              
                  <% if(request.getAttribute("msg") != null){%>
                     <div class="alert alert-danger" style="text-align:Center;"><%=request.getAttribute("msg")%></div>
                  <%}%>
        <br/><br/>            
   <div align="center"><a class="btn btn-sm btn-danger" href="../index.jsp">Back to Administration</a></div>
  <br/><br/>
<table class="schoolAdminTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">			
					<th width="5%">ID</th>		
					<th width="20%">SCHOOL NAME</th>
					<th width="20%">TOWN/CITY</th>			
					<th width="10%">REGION</th>			
					<th width="15%">PRINCIPAL</th>
					<th width="20%">VICE PRINCIPAL(S)</th>							
					<th width="10%">OPTIONS</th>					
				</tr>
				</thead>
				<tbody>                    
                
                    <% for(School school: schools) {            
                    	
                    
                    	
                        principal = school.getSchoolPrincipal();                      
                        asstprincipals = school.getAssistantPrincipals();
                    
                                      
                    %>                
                    
                    
                    <!-- 475,476,477,478,441,410,216,217,424,309,378,269,735,273,274,275,276,475,476,477,478   
                    
                    
                    --> 
                  
                    
                    
                    <tr>
                    <td width="5%"><%=school.getSchoolDeptID() %></td>
                    <td width="20%"><%=school.getSchoolName()%></td>
                    <td width="20%"><%=school.getTownCity() %></td>                  
                     <% if (school.getZone()!=null) { %>                   
                   				<% if (school.getZone().getZoneName().equalsIgnoreCase("avalon") || school.getZone().getZoneName().equalsIgnoreCase("eastern")) { %>
                   						<c:set var="regionColor" value="region1"/>
                   				<%
                   				avalonCount++;
                   				} else if (school.getZone().getZoneName().equalsIgnoreCase("central")) { %>
                   						<c:set var="regionColor" value="region2"/>
                   				<%
                   				centralCount++;
                   				} else if (school.getZone().getZoneName().equalsIgnoreCase("western")) { %>
                   						<c:set var="regionColor" value="region3"/>
                   				<%
                   				westernCount++;
                   				} else if (school.getZone().getZoneName().equalsIgnoreCase("labrador")) { %>
                   						<c:set var="regionColor" value="region4"/>
                   				<%
                   				labradorCount++;
                   				} else if (school.getZone().getZoneName().equalsIgnoreCase("provincial")) { %>
                   				<c:set var="regionColor" value="region5"/>                   				
                   				<%
                   				provincialCount++;
                   				} else{ %>
                   				<c:set var="regionColor" value="region6"/>
                   				<%
                   				otherCount++;
                   				} } %>
                   				  
                   <td width="10%" class="${regionColor}" style="text-align:center;text-transform:Capitalize;">                  
                    			<%=school.getZone()!=null?school.getZone().getZoneName():"N/A" %>
                   </td>
                    <td width="15%" style="text-transform:Capitalize;">
                            <% if(principal!=null) { %>
                              <a href="#" onclick="top.document.location.href='../../loginAs.html?pid=<%=principal.getPersonnelID()%>'; return false;"><%=principal.getFullNameReverse()%></a>
                            <% 
                            principalCount++;
                            } else { %>
                             N/A
                            <% 
                            uprincipalCount++;
                            } %>
                    </td>     
                   <td width="20%" style="text-transform:Capitalize;">      
                            <% if((asstprincipals!=null) && (asstprincipals.length > 0)) { 
                              		for(int i=0; i < asstprincipals.length; i++){ %>
                              		<a href="#" onclick="top.document.location.href='../../loginAs.html?pid=<%=asstprincipals[i].getPersonnelID()%>'; return false;"><%=asstprincipals[i].getFullNameReverse()%></a><br/>
     								
     								<%  
     								asstprincipalCount++;
                              		}%> 
     								
     								<% } else { %>
                           N/A
                            <% 
                            uasstprincipalCount++;
                           } %>
                    </td>
                    <td width="10%"><a class="btn btn-warning btn-xs" href='schoolAdminChange.html?sid=<%=school.getSchoolID()%>'><i class="fas fa-user-edit"></i> EDIT </a></td>
                    </tr>           
                    <%      }   %>
                </tbody>
                </table>
          
          <br/><br/>
          
           <div align="center"><a class="btn btn-sm btn-danger" href="../index.jsp">Back to Administration</a></div>
                                
</div>
<script>
$("#membersTotal").text(<%=avalonCount+centralCount+westernCount+labradorCount+provincialCount%>);
$("#avalonTotal").text(<%=avalonCount%>);
$("#centralTotal").text(<%=centralCount%>);
$("#westernTotal").text(<%=westernCount%>);
$("#labradorTotal").text(<%=labradorCount%>);
$("#provincialTotal").text(<%=provincialCount%>);
$("#otherTotal").text(<%=otherCount%>);
$("#principalTotal").text(<%=principalCount%>);
$("#uprincipalTotal").text(<%=uprincipalCount%>);
$("#asstprincipalTotal").text(<%=asstprincipalCount%>);
$("#uasstprincipalTotal").text(<%=uasstprincipalCount%>);

</script>                
                
                
                
  </body>
</html>