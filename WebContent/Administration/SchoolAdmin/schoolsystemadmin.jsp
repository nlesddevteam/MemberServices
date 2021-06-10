<%@ page language="java" 
         session="true"
         import="java.sql.*, 
         java.util.*,
         java.text.*,
         com.awsd.security.*, 
         com.awsd.weather.*,
         com.awsd.school.*,
         com.awsd.personnel.*"
         isThreadSafe="false" %>
         
<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld"  %>

<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<%
	User usr = null;
  SchoolSystems systems = null;
  SchoolSystem sys = null;
  Schools schools = null;
  School school = null;
  Personnel p = null;
  Iterator<SchoolSystem> sys_iter = null;
  Iterator<School> sch_iter = null;
  Iterator<Personnel> p_iter = null;
  Vector<Personnel> principals = null;
  Personnel ap[] = null;

  systems = new SchoolSystems();
  
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
  
  sys_iter = systems.iterator();
  principals = PersonnelDB.getDistrictPersonnel();
  p_iter = principals.iterator();
%>

<html>
<head>
<title>School System Administration</title>
<style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		</style>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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
			  "searching": false,
				responsive: true,
				"lengthChange": false			
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
<div class="siteHeaderGreen">School System Administration</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Administration Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 

        <form name="modsys" action="schoolSystemAdmin.html" method="post">
                            <input type="hidden" name="op" value="mod">
                            <input type="hidden" name="ss_id" value="">
                            
						<div align="center">				 
					 <a  class="btn btn-sm btn-primary" href="schoolsystemcreate.jsp">Create New School System</a> &nbsp; 
						  <a onclick="loadingData();" class="btn btn-sm btn-danger" href="../index.jsp">Back to Administration</a>
						</div>		
       <br/><br/>


<table class="sortAlphaTable table table-sm responsive" width="100%" style="font-size:11px;background-color:White;">
<thead>
<tr>
<th></th>
</tr>
</thead>
 <tbody>     
 <div id="accordion">     



  <%
  int cnt=0;
  int schcnt=0;
  while(sys_iter.hasNext()) {
                                sys = (SchoolSystem) sys_iter.next();
                                cnt++;
                              %>
                               
   <tr>
    <td>                    
   <div class="card">
   <div class="card-header">    
   <div style="float:right"> 
		   	<a class="btn btn-xs btn-primary card<%=cnt%>" data-toggle="collapse" href="#collapse<%=cnt%>"><i class="far fa-eye"></i> VIEW</a>   
		    <a class="btn btn-xs btn-warning" href="" onclick="document.forms[0].ss_id.value='<%=sys.getSchoolSystemID()%>';document.forms[0].submit();return false;"><i class="fas fa-users-cog"></i> EDIT</a>
		    <a class="btn btn-xs btn-danger" href="" onclick="return confirm('Are you sure you want to remove this system? This cannot be undone.');loadingData();"><i class="far fa-trash-alt"></i> DEL</a>   
   </div>                                      
                          
   <a class="card-link card<%=cnt%>"  data-toggle="collapse" href="#collapse<%=cnt%>"><span class="siteSubHeaderBlue"><span id="icon<%=cnt%>"><i class='fas fa-folder'></i></span>  <%=sys.getSchoolSystemName()%> (<span class="schoolCount<%=cnt%>"></span>)</span> </a><br/>                              
   <span class="siteTextBlack" style="font-size:12px;"><b>System Admin(s):</b> <span style="text-transform:Capitalize;"><%=(sys.getSchoolSystemAdmin()!=null)?sys.getSchoolSystemAdmin().getFullNameReverse() :"N/A"%>
                                 <%
	                                    Personnel[] backup = sys.getSchoolSystemAdminBackup();
	                    								for(int i=0; ((backup != null)&&( i < backup.length));i++)
	                    									if(backup[i] != null) {%>	                    									
	                    									, <%=backup[i].getFullNameReverse()%>
                                   <%}%>
   </span></span>                               
   </div>                      
  <div id="collapse<%=cnt%>" class="collapse" data-parent="#accordion">       
<div class="card-body">                                            
 <table class="schoolAdminTable table table-sm table-bordered responsive" width="100%" style="font-size:12px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">
					<th width="50%">SCHOOL NAME</th>						
					<th width="25%">PRINCIPAL</th>			
					<th width="25%">ASSISTANT PRINCIPAL(s)</th>								
				</tr>
				</thead>
				<tbody>                          
                                <%sch_iter = sys.getSchoolSystemSchools().iterator();
                                  while(sch_iter.hasNext()){
                                    school = (School) sch_iter.next();     
                                    schcnt++;
                                %>   
                                <tr>                             
                 		<td width="50%"><%=school.getSchoolName()%> </td>
                      	<td width="25%"><span style="text-transform:Capitalize;"><%=(school.getSchoolPrincipal()!= null)?school.getSchoolPrincipal().getFullNameReverse():"N/A"%></span></td>
                        <td width="25%"><span style="text-transform:Capitalize;"><%
                                      	ap = school.getAssistantPrincipals();
                                      	if(ap != null && ap.length > 0){
                                      		for(int i=0; i < ap.length; i++) {%>
                                      			<%=ap[i].getFullNameReverse()%><br/>
                                      	<% }} else {%>
                                      		N/A
                                      <% }%></span>
                          </td>
                        </tr>            
                                <%} %>
                </tbody>
                 </table> 
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
   </div> 
</div>
</div>
</td>
</tr>
                        
  <%}%>
   </div>                   
</tbody>
</table>
               
  </form>                              
                         <br/><br/>		
						<div align="center">
										<a  class="btn btn-sm btn-primary" href="schoolsystemcreate.jsp">Create New  School System</a> &nbsp; 
						  <a onclick="loadingData();" class="btn btn-sm btn-danger" href="../index.jsp">Back to Administration</a>
						</div>		
				
</div>               
	
	<script>
	
	
	</script>
										
</body>
</html>
