<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />

<%
  SubListBean list = (SubListBean) session.getAttribute("SUBLIST");
  ApplicantProfileBean[] applicants = (ApplicantProfileBean[]) session.getAttribute("SUBLIST_SHORTLIST");
  User usr = (User)session.getAttribute("usr");
  
  TrainingMethodConstant trnlvl = null;
  
  if(!StringUtils.isEmpty(request.getParameter("trnlvl_id"))) {
  	trnlvl = TrainingMethodConstant.get(Integer.parseInt(request.getParameter("trnlvl_id")));
  }
%>





<html>
<head>
<title>MyHRP Applicant Profiling System</title>


<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.print.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.5/jszip.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.40/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.40/vfs_fonts.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css">
<script type="text/javascript">
	$('document').ready(function(){
		
		$('#reportdata').DataTable({
			
			"order": [[ 1, 'asc' ]],
			  dom: 'Bfrtip',			  
			  buttons: [ 'copyHtml5', 'excelHtml5', 
				  {
	                extend: 'pdfHtml5',
	                orientation: 'landscape',
	                pageSize: 'LETTER'
	                
	            }, 'csvHtml5',{
	                extend: 'print',
	                orientation: 'landscape',
	                pageSize: 'LETTER'
	            }
			  ]
			 ,"bAutoWidth": false
			  
			} );
		
		$("#reportdata").css('table-layout', "fixed");
		
	});
	
	
	
	
</script>

    
 <style>
input {    
    border:1px solid silver;
		}
</style>
</head>
<body>

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b><%=trnlvl != null ? trnlvl.getDescription() : list != null ? list.getTitle() : "Substitute List" %> - Short List</b> (Total Applicants: <%=applicants.length%>)</div>
      			 	<div class="panel-body">

      				
                                  <%if(applicants.length > 0){ %>
                                  By default, the list below is sorted by Last Name. Click on the column header to sort by that column and/or use the search to find a particular applicant.
      							 <br/><br/>
                                  <table id="reportdata" class="table table-striped table-condensed dt-responsive" style="font-size:11px;background-color:#FFFFFF;margin-top:5px;">
                                  <thead>
                                  <tr>
                                    <th width="10%">First Name</th>
                                    <th width="10%">Last Name</th>                                     
                                    <th width="35%">Major(s)/Minor(s)</th>                                    
                                    <th width="20%">Email</th>
                                    <th width="15%">Telephone</th>
                                    <th width="10%" class="no-print">Options</th>
                                  </tr>
                                  </thead>
                                  <tbody>
                                  <%                                  
                                    for(int i=0; i < applicants.length; i++){%>
                                    <tr>
                                      <td><%=applicants[i].getFirstname()%></td>
                                      <td><%=applicants[i].getSurname()%></td>
                                      <td>
                                      <%
                                      		String nnc = "";
                                      		if(StringUtils.isEmpty(applicants[i].getMajorsList())){
                                      			nnc="None listed.";
                                      		}else{
                                      			nnc= applicants[i].getMajorsList();
                                      		}
                                      		out.println("<span style='color:#A52A2A;'>Major(s): " + nnc + "</span>");
                                      	
                                      		String nncc = "";
                                      		if(StringUtils.isEmpty(applicants[i].getMinorsList())){
                                      			nncc="None listed.";
                                      		}else{
                                      			nncc= applicants[i].getMinorsList();
                                      		}
                                      		out.println("<br/><span style='color:#008B8B;'>Minor(s): " + nncc + "</span>");
                                      	%>
                                      
                                      </td>
                                      <td><a href="mailto:<%=applicants[i].getEmail()%>"><%=applicants[i].getEmail()%></a></td>
                                      <td>
                                      	<%
                                      		String nc = "";
                                      		if(!StringUtils.isEmpty(applicants[i].getHomephone()))
                                      			nc += ("(h) " + applicants[i].getHomephone());
                                      		if(!StringUtils.isEmpty(applicants[i].getCellphone())){
                                      			nc += ((!StringUtils.isEmpty(nc)? "<br />(c) ":"(c) ") + applicants[i].getCellphone());
                                      		}
                                      		if(StringUtils.isEmpty((nc)))
                                      			nc = "&nbsp;";
                                      		
                                      		out.println(nc);
                                      	%>
                                      </td>
                                      <td class="no-print" style="vertical-align:middle;"><a class='btn btn-xs btn-primary' href="viewApplicantProfile.html?sin=<%=applicants[i].getSIN()%>">View Profile</a></td>
                                    </tr>
                                 <% } %>
                                 
                                  </tbody>
                                	</table>
                                 
                                 <% }else{%>
                                   No applicants currently short listed.
                                  <%}%>
                                  
                                 
                              <br/><br/>
    <!-- ADMINISTRATIVE FUNCTIONS -->
		                             
	<div class="no-print" align="center">
  		
                 <%if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")){%>
                                  <a class="btn btn-xs btn-primary"  href='printable_shortlist.jsp' target="_blank">Print Short List Profiles</a>
                                  <a class="btn btn-xs btn-danger" href='admin_view_sublist_applicants.jsp'>Back to Applicants List</a>
                                <%}%>              
	               
	                <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
  			
  	</div>                           
                              
                              
                                
                              
    </div></div></div>                          
</body>
</html>
