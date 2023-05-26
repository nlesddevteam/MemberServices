<%@ page language="java" 
         	session="true"
         	import="java.sql.*,
                 java.util.*,
                 java.text.*,
                 com.awsd.security.*,
                 com.awsd.school.*,
                 com.awsd.personnel.*"%>

<%
  User usr = null;
  SchoolFamilies families = null;
  SchoolFamily family = null;
  Schools schools = null;
  School school = null;
  Personnel p = null;
  Iterator fam_iter = null;
  Iterator sch_iter = null;
  Iterator p_iter = null;
  Vector admins = null;
  Personnel aps[] = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%
	}

  families = new SchoolFamilies();
  fam_iter = families.iterator();
  admins = PersonnelDB.getDistrictPersonnel();
  p_iter = admins.iterator();
%>

<html>
<head>
<title>DOS School Family Admin</title>
  <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		td {vertical-align:center;}
		</style>
	<script>	
		
	$('document').ready(function(){
		aTable = $(".sortAlphaTable").dataTable({
			"order" : [[0,"asc"]],			
			  "paging":   false,
			  "searching": false,
				responsive: true,
				"lengthChange": false,
				"columnDefs": [
					 {
			                "targets": [0],			               
			                "searchable": false,
			                "visible": true
			            }
			        ]
		});
		
		mTable = $(".schoolAdminTable").dataTable({
			"order" : [[0,"asc"]],			
			  "paging":   false,
				responsive: true,
				"lengthChange": false,
				 "searching": false,
						
							
			 "columnDefs": [
				 {
		                "targets": [3],			               
		                "searchable": false,
		                "orderable": false
		            }
		        ]
		});			
		
		$(".schoolAdminTable tr").not(':first').hover(
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
<div class="siteHeaderGreen">School Family Administration</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 


Below are the list of School Families (Family of Schools) throughout the province. Every school is assigned to a particular school family and administered by a Director of Schools.
<br/><br/>
						<div align="center">						 
						 <a onclick="loadingData();" class="btn btn-sm btn-primary" href="schoolfamilycreate.jsp">Create a New Family</a> &nbsp; 
						  <a onclick="loadingData();" class="btn btn-sm btn-danger" href="/MemberServices/navigate.jsp">Back to Administration</a>
						</div>		
       <br/><br/>
        <%if(!fam_iter.hasNext()){%>
                      
                       No school families configured.
                       
                      <%}else{ 
                      int cnt=0;
                      int schcnt=0;
                      %>
 
 <table class="sortAlphaTable table table-sm responsive" width="100%" style="font-size:11px;background-color:White;">
 <thead class="thead-dark">
<tr>
 <th>FAMILY OF SCHOOL NAME (FOS) / ASSIGNED DIRECTOR OF SCHOOLS (DOS)</th>
 </tr>
 </thead>
 <tbody>     
 <div id="accordion">      
       	 <% while(fam_iter.hasNext()) {
                          family = (SchoolFamily) fam_iter.next();
                          cnt++;
                        %>
    <tr>
    <td>                    
   <div class="card">
   <div class="card-header">
    
   <div style="float:right">
   					<a class="btn btn-xs btn-primary card<%=cnt%>" data-toggle="collapse" href="#collapse<%=cnt%>"><i class="far fa-eye"></i> VIEW</a>
					<a class="btn btn-xs btn-warning"  onclick="loadingData();" href="schoolFamilyAdmin.html?op=mod&family_id=<%=family.getSchoolFamilyID()%>"><i class="fas fa-users-cog"></i> EDIT</a>
                    <a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to remove this family? This cannot be undone.');loadingData();" href="schoolFamilyAdmin.html?op=del&family_id=<%=family.getSchoolFamilyID()%>"><i class="far fa-trash-alt"></i> DEL</a>
    </div>      
   <a class="card-link card<%=cnt%>" data-toggle="collapse" href="#collapse<%=cnt%>"><span class="siteSubHeaderBlue"><span id="icon<%=cnt%>"><i class='fas fa-folder'></i></span> <%=family.getSchoolFamilyName()%>  (<span class="schoolCount<%=cnt%>"></span>)</span> </a><br/> 
   <span class="siteSubHeaderGreen"><span style="text-transform:Capitalize;"><%=(family.getProgramSpecialist()!=null)?family.getProgramSpecialist().getFullNameReverse():"No DOS Currently Assigned"%></span>
   
   </div>  
   <div id="collapse<%=cnt%>" class="collapse" data-parent="#accordion">       
<div class="card-body">     

Below are the list of schools in <b><%=family.getSchoolFamilyName()%></b> assigned to 
<span style="text-transform:Capitalize;font-weight:bold;"><%=(family.getProgramSpecialist()!=null)?family.getProgramSpecialist().getFullNameReverse():"No DOS Currently Assigned"%></span>.
You can removed schools from the list. To add schools or to change the DOS for these schools, use the EDIT link above right.
          
<table class="schoolAdminTable table table-sm table-striped responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr>
					<th>SCHOOL NAME</th>						
					<th>PRINCIPAL</th>			
					<th>ASSISTANT PRINCIPAL(s)</th>
					<th>OPTIONS</th>					
				</tr>
				</thead>
				<tbody>                    
					<%sch_iter = family.getSchoolFamilySchools().iterator();
                          while(sch_iter.hasNext()){
                            school = (School) sch_iter.next();
                            aps = school.getAssistantPrincipals();
                            schcnt++;
                        %>
                        <tr>
                        <td><%=school.getSchoolName()%></td> 
                        <td style="text-transform:capitalize;">
                        <%=(school.getSchoolPrincipal()!= null)?school.getSchoolPrincipal().getFullNameReverse().toLowerCase():"N/A"%>
                       </td>
                        <td style="text-transform:capitalize;">
                              <%
                              	if((aps != null) && (aps.length > 0)){
                              		for(int i=0; i < aps.length; i++) { %>
                              <%=aps[i].getFullNameReverse().toLowerCase() %><br/>
                              	
                             <%}} else { %>
                              <span style="color:Silver;">N/A</span>
                              <% }  %>
                            
                         </td>  
                         <td><a class="btn btn-xs btn-danger" onclick="loadingData();return confirm('Are you sure you want to remove this school from this family?')" title="Remove School from this Family" href="schoolFamilyAdmin.html?op=delsch&family_id=<%=family.getSchoolFamilyID()%>&sch_id=<%=school.getSchoolID()%>"><i class="far fa-trash-alt"></i> REMOVE</a></td>
                         </tr>                          
                        <% }%>
                  </tbody>
</table>	

            </div>	
			</div>
			</div>
			<script>
			 $(".schoolCount<%=cnt%>").text(<%=schcnt%>);  
			
			 $('.card<%=cnt%>').on("click", function(e){             	 
	                if( $("#collapse<%=cnt%>").hasClass("show")) {
	                	$("#icon<%=cnt%>").html("<i class='fas fa-folder'></i>");
	                } else {                	
	                	 $("#icon<%=cnt%>").html("<i class='fas fa-folder-open'></i>");
	                }                	 
	                	 e.preventDefault();                	 
	                	});                 
			 
			</script>
			 <%schcnt=0;%>
			
			</td>			
		    </tr>
		    
		    
		    
		    
                        <%}%>
       	 
</div>   
 </tbody>
 </table>     
 
 
 
                      <%}%>											

						
						<br/><br/>
						<div align="center">						 
						 <a onclick="loadingData();" class="btn btn-sm btn-primary" href="schoolfamilycreate.jsp">Create a New Family</a> &nbsp; 
						  <a onclick="loadingData();" class="btn btn-sm btn-danger" href="/MemberServices/navigate.jsp">Back to Administration</a>
						</div>
						
	</div>		
			
	 										
</body>
</html>
