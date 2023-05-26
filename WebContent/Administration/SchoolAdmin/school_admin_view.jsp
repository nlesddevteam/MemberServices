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
  	
  	int FOS1Count=0; 
  	int FOS2Count=0; 
  	int FOS3Count=0; 
  	int FOS4Count=0; 
  	int FOS5Count=0; 
  	int FOS6Count=0; 
  	int FOS7Count=0; 
  	int FOS8Count=0; 
  	int FOS9Count=0; 
  	int FOS10Count=0; 
  	int FOS11Count=0; 
  	int FOS12Count=0; 
  	int FOS13Count=0; 
  	int FOS14Count=0; 
  	int FOSNACount=0; 
  	
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
				  "lengthMenu": [[10,25,50,100,200, -1], [10,25,50,100,200, "All"]],	
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
	                         columns: [ 0,1,2,3,4,5,6],	                       
	                     }
	            },
	            { 
	       		 extend: 'excel',	
	       		 exportOptions: {        		       		 

	                    columns: [ 0,1,2,3,4,5,6],
	                 },
	       },
	       { 
	     		 extend: 'csv',	
	     		 exportOptions: {        		            		 

	                    columns: [ 0,1,2,3,4,5,6],
	               },
	     },
	         
		        ],			
								
				 "columnDefs": [
					 {
			                "targets": [7],			               
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
<table class="schoolAdminTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">			
					<th>ID</th>		
					<th>SCHOOL NAME</th>
					<th>TOWN/CITY</th>	
					<th>FAMILY</th>		
					<th>PRINCIPAL</th>
					<th>VICE PRINCIPAL(S)</th>	
					<th>REGION</th>							
					<th>OPTIONS</th>					
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
                    <td style="vertical-align:middle;"><%=school.getSchoolDeptID() %></td>
                    <td style="vertical-align:middle;"><%=school.getSchoolName()%></td>
                    <td style="vertical-align:middle;"><%=school.getTownCity() %></td>                  
                                     
                 <c:catch var='schoolFamilyException'>
<c:set var="theSchool" value="<%=school.getSchoolFamily().getSchoolFamilyName() %>"/>
</c:catch>
<c:if test = "${schoolFamilyException != null}">
<c:set var="theSchool" value="ERROR"/>
</c:if>
<c:choose>
<c:when test="${ theSchool eq 'FOS 01' }">
<td class="familyAlign family1" style="vertical-align:middle;">FOS 01</td>
<%FOS1Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 02' }">
<td class="familyAlign family2" style="vertical-align:middle;">FOS 02</td>
<%FOS2Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 03' }">
<td class="familyAlign family3" style="vertical-align:middle;">FOS 03</td>
<%FOS3Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 04' }">
<td class="familyAlign family4" style="vertical-align:middle;">FOS 04</td>
<%FOS4Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 05' }">
<td class="familyAlign family5" style="vertical-align:middle;">FOS 05</td>
<%FOS5Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 06' }">
<td class="familyAlign family6" style="vertical-align:middle;">FOS 06</td>
<%FOS6Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 07' }">
<td class="familyAlign family7" style="vertical-align:middle;">FOS 07</td>
<%FOS7Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 08' }">
<td class="familyAlign family8" style="vertical-align:middle;">FOS 08</td>
<%FOS8Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 09' }">
<td class="familyAlign family9" style="vertical-align:middle;">FOS 09</td>
<%FOS9Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 10' }">
<td class="familyAlign family10" style="vertical-align:middle;">FOS 10</td>
<%FOS10Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 11 (DSS)' }">
<td class="familyAlign family11" style="vertical-align:middle;">FOS 11 (DSS)</td>
<%FOS11Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 12 (DSS)' }">
<td class="familyAlign family12" style="vertical-align:middle;">FOS 12 (DSS)</td>
<%FOS12Count++;%>
</c:when>
<c:when test="${ theSchool eq 'FOS 13 (DSS)' }">
<td class="familyAlign family13" style="vertical-align:middle;">FOS 13 (DSS)</td>
<%FOS13Count++;%>
</c:when>
<c:otherwise>
<td class="familyAlign familyDefault" style="vertical-align:middle;">N/A</td>
<%FOSNACount++;%>
</c:otherwise>

</c:choose>
                 
                 
                 
                 
                 
                    <td style="text-transform:Capitalize; vertical-align:middle;">
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
                   <td style="text-transform:Capitalize;vertical-align:middle;">      
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
                     <% if (school.getZone()!=null) { %>                   
                   				<% if (school.getZone().getZoneName().equalsIgnoreCase("avalon") || school.getZone().getZoneName().equalsIgnoreCase("eastern")) { %>
                   						<c:set var="regionColor" value="officeAvalon"/>
                   				<%
                   				avalonCount++;
                   				} else if (school.getZone().getZoneName().equalsIgnoreCase("central")) { %>
                   						<c:set var="regionColor" value="officeCentral"/>
                   				<%
                   				centralCount++;
                   				} else if (school.getZone().getZoneName().equalsIgnoreCase("western")) { %>
                   						<c:set var="regionColor" value="officeWestern"/>
                   				<%
                   				westernCount++;
                   				} else if (school.getZone().getZoneName().equalsIgnoreCase("labrador")) { %>
                   						<c:set var="regionColor" value="officeLabrador"/>
                   				<%
                   				labradorCount++;
                   				} else if (school.getZone().getZoneName().equalsIgnoreCase("provincial")) { %>
                   				<c:set var="regionColor" value="officeProvincial"/>                   				
                   				<%
                   				provincialCount++;
                   				} else{ %>
                   				<c:set var="regionColor" value="region6"/>
                   				<%
                   				otherCount++;
                   				} } %>
                   				  
                   <td class="${regionColor}" style="vertical-align:middle;text-align:center;text-transform:Capitalize;">                  
                    			<%=school.getZone()!=null?school.getZone().getZoneName():"N/A" %>
                   </td>
                    <td ><a class="btn btn-warning btn-xs" href='schoolAdminChange.html?sid=<%=school.getSchoolID()%>' onclick="loadingData();"><i class="fas fa-user-edit"></i> EDIT </a></td>
                    </tr>           
                    <%      }   %>
                </tbody>
                </table>
                             
<hr>
<b>REGIONAL SCHOOLS</b><br/>
<b>TOTAL::</b> <span id="membersTotal"></span> &nbsp;
<b>AVALON:</b> <span id="avalonTotal"></span> &nbsp; 
<b>CENTRAL:</b> <span id="centralTotal"></span> &nbsp; 
<b>WESTERN:</b> <span id="westernTotal"></span> &nbsp; 
<b>LABRADOR:</b> <span id="labradorTotal"></span> &nbsp; 
<b>PROVINCIAL:</b> <span id="provincialTotal"></span> &nbsp; 
<b>OTHER:</b> <span id="otherTotal"></span>
<hr>
<b>FAMILY OF SCHOOLS</b><br/>
<b>FOS 01:</b> <span id="FOS1Total"></span> &nbsp; 
<b>FOS 02:</b> <span id="FOS2Total"></span> &nbsp; 
<b>FOS 03:</b> <span id="FOS3Total"></span> &nbsp; 
<b>FOS 04:</b> <span id="FOS4Total"></span> &nbsp; 
<b>FOS 05:</b> <span id="FOS5Total"></span> &nbsp; 
<b>FOS 06:</b> <span id="FOS6Total"></span> &nbsp; 
<b>FOS 07:</b> <span id="FOS7Total"></span> &nbsp;
<b>FOS 08:</b> <span id="FOS8Total"></span> &nbsp; 
<b>FOS 09:</b> <span id="FOS9Total"></span> &nbsp; 
<b>FOS 10:</b> <span id="FOS10Total"></span> &nbsp; 
<b>FOS 11:</b> <span id="FOS11Total"></span> &nbsp; 
<b>FOS 12:</b> <span id="FOS12Total"></span> &nbsp; 
<b>NO FAMILY:</b> <span id="FOSNATotal"></span>
<hr>
<b>PRINCIPALS</b><br/>
<b>ASSIGNED:</b> <span id="principalTotal"></span>  &nbsp;
<b>UNASSIGNED:</b> <span id="uprincipalTotal"></span><br/>
<hr>
<b>ASSISTANT PRINCIPALS</b><br/>
<b>ASSIGNED:</b> <span id="asstprincipalTotal"></span>  &nbsp; 
<b>UNASSIGNED:</b> <span id="uasstprincipalTotal"></span>
          
     <br/><br/>     
           <div align="center"><a class="btn btn-sm btn-danger" href="/MemberServices/navigate.jsp" onclick="loadingData();">Back to StaffRoom</a></div>
                                
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

$("#FOS1Total").text(<%=FOS1Count%>);
$("#FOS2Total").text(<%=FOS2Count%>);
$("#FOS3Total").text(<%=FOS3Count%>);
$("#FOS4Total").text(<%=FOS4Count%>);
$("#FOS5Total").text(<%=FOS5Count%>);
$("#FOS6Total").text(<%=FOS6Count%>);
$("#FOS7Total").text(<%=FOS7Count%>);
$("#FOS8Total").text(<%=FOS8Count%>);
$("#FOS9Total").text(<%=FOS9Count%>);
$("#FOS10Total").text(<%=FOS10Count%>);
$("#FOS11Total").text(<%=FOS11Count%>);
$("#FOS12Total").text(<%=FOS12Count%>);
$("#FOSNATotal").text(<%=FOSNACount%>);


</script>                
                
                
                
  </body>
</html>