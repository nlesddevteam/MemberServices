<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*,
         				 com.esdnl.personnel.jobs.constants.*,
         				 com.esdnl.personnel.jobs.dao.*,
         				 com.awsd.security.*,
         				 com.awsd.mail.bean.*,
         				 java.util.*" 
         isThreadSafe="false"%>



		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />

<%
  User usr = (User) session.getAttribute("usr");
  SubListBean list = (SubListBean) session.getAttribute("SUBLIST");
  ApplicantProfileBean[] applicants = (ApplicantProfileBean[]) session.getAttribute("SUBLIST_APPLICANTS");
  HashMap<String, ApplicantProfileBean>  shortlistedMap = (HashMap<String, ApplicantProfileBean>) session.getAttribute("SHORTLISTMAP");
  ApplicantProfileBean[] shortlisted = (ApplicantProfileBean[]) session.getAttribute("SHORTLIST");
  HashMap<String, ApplicantProfileBean> notapprovedMap = (HashMap<String, ApplicantProfileBean>) session.getAttribute("NOTAPPROVEDMAP");
  ApplicantProfileBean[] notapproved = (ApplicantProfileBean[]) session.getAttribute("NOTAPPROVED");
  HashMap<String, ApplicantProfileBean> workingMap = (HashMap<String, ApplicantProfileBean>) session.getAttribute("WORKINGMAP");
  ApplicantProfileBean[] working = (ApplicantProfileBean[]) session.getAttribute("WORKING");
  
  String bg_style = null;

  int cnt = 0;
  
  
//Email Applicants
	
	String msg = "";
	
	if((request.getParameter("sent") != null) && (request.getParameter("sent").equalsIgnoreCase("1"))){
		
    String to[]=null, cc[]=null, bcc[]=null;
    String subject="", message="";
		
		try
    {  
      if(request.getParameter("to") != null)
      {
        to = (usr.getPersonnel().getEmailAddress()+ ";" +request.getParameter("to")).split(";");
        System.err.println("TO: " + request.getParameter("to"));
      }
      else
      	to = new String[]{usr.getPersonnel().getEmailAddress()};

      if((request.getParameter("cc") != null) && (!request.getParameter("cc").equals("")))
      {
        cc = request.getParameter("cc").split(";");
        System.err.println("CC: " + request.getParameter("cc"));
      }
      else
      {
        cc = null;
      }

      if((request.getParameter("bcc") != null) && (!request.getParameter("bcc").equals("")))
      {
        bcc = request.getParameter("bcc").split(";");
        System.err.println("BCC: " + request.getParameter("bcc"));
      }
      else
      {
        bcc = null;
      }

      if(request.getParameter("subject") != null)
      {
        subject = request.getParameter("subject");
      }
    
      if(request.getParameter("message") != null)
      {
        message = request.getParameter("message");
      }
      if(request.getParameter("bcc").length() < 3500){
	      EmailBean email = new EmailBean();
	      email.setTo(to);
	      email.setCC(cc);
	      email.setBCC(bcc);
	      email.setSubject(subject);
	      email.setBody(message);
	      email.setFrom(usr.getPersonnel().getEmailAddress());
	      email.send();
      }
      else{
      	String bcc1[] = new String[bcc.length / 2];
      	String bcc2[] = new String[bcc.length - bcc1.length];
      	
      	System.arraycopy(bcc, 0, bcc1, 0, bcc1.length);
      	System.arraycopy(bcc, bcc1.length, bcc2, 0, bcc2.length);
      	
      	EmailBean email = new EmailBean();
	      email.setTo(to);
	      email.setCC(cc);
	      email.setBCC(bcc1);
	      email.setSubject(subject);
	      email.setBody(message);
	      email.setFrom(usr.getPersonnel().getEmailAddress());
	      email.send();
	      
	      email.setBCC(bcc2);
	      email.send();
      }
      
      msg = "Email sent successfully.";
      
    }
    catch(Exception e)
    {
      System.err.println(e);
      e.printStackTrace(System.err);
      msg = "Could not send email.";
    }
		
	}
  
  
  
  
%>


<html>
<head>
	<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
	<style type="text/css">
		span.view {
			color: #FF0000;
			font-weight:bold;
			text-decoration: none;
			cursor: hand;
			width: 100%;
			text-align: center;
		}
	</style>
	
	<script type="text/javascript">
		$('document').ready(function(){
			$('span.view').parent().css('text-align', 'center');
			
			$('span.view').click(function(){
				document.location.href=$(this).attr('href');
			});

			//$('#new-app-count').html(' (' + ($('.tblNewApplicants tr:not(".noapplicant")').length - 1) + ')');
		});
	</script>
	
	
<script>
 $('document').ready(function(){
	  $("#jobapps1").DataTable({"order": [[ 1, "asc" ]],
		  dom: 'Blfrtip',
	        buttons: [			        	
	        	//'colvis',
	        	'copy', 
	        	'csv', 
	        	'excel', 
	        	{
	                extend: 'pdfHtml5',
	                footer:true,
	                //orientation: 'landscape',
	                messageTop: '',
	                messageBottom: null,
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3 ]
	                }
	            },
	        	{
	                extend: 'print',
	                //orientation: 'landscape',
	                footer:true,
	                messageTop: '',
	                messageBottom: null,
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3]
	                }
	            }
	        ] 
	  
	  
	  });
	  $("#jobapps2").DataTable({"order": [[ 1, "asc" ]],
		  dom: 'Blfrtip',
	        buttons: [			       	
	       'copy', 
	        	'csv', 
	        	'excel', 
	        	{
	                extend: 'pdfHtml5',
	                footer:true,
	                //orientation: 'landscape',
	                messageTop: ' ',
	                messageBottom: null,
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3,4 ]
	                }
	            },
	        	{
	                extend: 'print',
	                //orientation: 'landscape',
	                footer:true,
	                messageTop: '',
	                messageBottom: null,
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3,4]
	                }
	            }
	        ]
	  
	  });
	  $("#jobapps3").DataTable({"order": [[ 1, "asc" ]],
		  dom: 'Blfrtip',
	        buttons: [			        	
	        
	        	'copy', 
	        	'csv', 
	        	'excel', 
	        	{
	                extend: 'pdfHtml5',
	                footer:true,
	                //orientation: 'landscape',
	                messageTop: '',
	                messageBottom: null,
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3,4 ]
	                }
	            },
	        	{
	                extend: 'print',
	                //orientation: 'landscape',
	                footer:true,
	                messageTop: '',
	                messageBottom: null,
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3,4]
	                }
	            }
	        ]
	  });
	  $("#jobapps4").DataTable({"order": [[ 1, "asc" ]],
		  dom: 'Blfrtip',
	        buttons: [			        	
	        	//'colvis',
	        	'copy', 
	        	'csv', 
	        	'excel', 
	        	{
	                extend: 'pdfHtml5',
	                footer:true,
	                //orientation: 'landscape',
	                messageTop: '',
	                messageBottom: null,
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3 ]
	                }
	            },
	        	{
	                extend: 'print',
	                //orientation: 'landscape',
	                footer:true,
	                messageTop: '',
	                messageBottom: null,
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3]
	                }
	            }
	        ]
	  
	  });
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
	               	<div class="panel-heading"><b><%=list.getTitle()%> Substitute List</b> (Total Applicants: <%=applicants.length%>)</div>
      			 	<div class="panel-body">
								<%if(request.getAttribute("msg")!=null){%>
                                    <div class="alert alert-danger">
                                      <%=(String)request.getAttribute("msg")%>
                                    </div>
                                  <%}%>   
   					Click on a tab below to open the list to view the applicants for the <%=list.getTitle()%> Substitute List. The data is initially sorted by Last Name.<br/>
   <div class="panel-group" id="accordion">
  	<div class="panel panel-info">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse1">New Applicants <span class="displayText" style="font-weight:none;">(<span id="newAppNum"></span>)</span></a></h4>
    </div>
    <div id="collapse1" class="panel-collapse collapse">
      <div class="panel-body">
      <div class="table-responsive"> 
      									
                                        <% if(applicants.length > 0){ %>
                                        
                                        <table id='jobapps1' class="tblNewApplicants table table-condensed table-striped" style="font-size:12px;">
                                        <thead>
                                        <tr>
                                           <th width="15%">First Name</th>  
                                           <th width="15%">Last Name</th>                                       
                                           <th width="20%">Telephone</th>
                                           <th width="25%">Email</th>
                                           <th width="15%">Applied</th>
                                           <th width="10%">Options</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        
                                        <% cnt = 0;
                                          for(int i=0; i < applicants.length; i++){
                                            if(shortlistedMap.containsKey(applicants[i].getSIN()) 
                                            		|| notapprovedMap.containsKey(applicants[i].getSIN())
                                            		|| workingMap.containsKey(applicants[i].getSIN()))
                                              continue;
                                        %>  
                                        <tr>
                                              <td><%=applicants[i].getFirstname()%></td>  
                                              <td><%=applicants[i].getSurname()%></td>                                            
                                              <td><%=applicants[i].getHomephone()%></td>
                                              <td><a href="mailto:<%=applicants[i].getEmail()%>"><%=applicants[i].getEmail()%></a></td>
                                              <td><%=((applicants[i].getAppliedDate() != null)?applicants[i].getAppliedDateFormatted():"&nbsp")%></td>
                                              <td><a class='btn btn-xs btn-primary' href="viewApplicantProfile.html?sin=<%=applicants[i].getSIN()%>">View Profile</a></td>
                                         </tr>
                                         <%cnt++; %>
                                        <%} %>
                                        </tbody>
                                        </table>  
                                        <script>$("#newAppNum").html(<%=cnt%>);</script>
                                        <%} else {%>                                        
                                          No new applicants currently.
                                        <%}%>
                                      
      </div>
      </div>
    </div>
  </div>
  <div class="panel panel-info">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse2">Short Listed Applicants <span class="displayText" style="font-weight:none;">(<%=shortlisted.length%>)</span></a></h4>
    </div>
    <div id="collapse2" class="panel-collapse collapse">
      <div class="panel-body">
      								
                                        <%
                                          if(shortlisted.length > 0){ %>
                                        	  
                                        	  <table id='jobapps2' class="table table-condensed table-striped" style="font-size:12px;">
                                              <thead>
                                              <tr>
                                                 <th width="15%">First Name</th>  
                                                 <th width="15%">Last Name</th>                                         
                                                 <th width="20%">Telephone</th>
                                                 <th width="25%">Email</th>
                                                 <th width="15%">Applied</th>
                                                 <th width="10%">Options</th>
                                              </tr>
                                              </thead>
                                              <tbody>
                                        	  
                                        	<%                                       	  
                                            cnt = 0;
                                            for(int i=0; i < shortlisted.length; i++){                                              
                                        %>    <tr>                                                
                                                <td><%=shortlisted[i].getFirstname()%></td>                                                
                                                <td><%=shortlisted[i].getSurname()%></td>                                               
                                                <td><%=shortlisted[i].getHomephone()%></td>
                                                <td><a href="mailto:<%=shortlisted[i].getEmail()%>"><%=shortlisted[i].getEmail()%></a></td>
                                                <td><%=((shortlisted[i].getAppliedDate() != null)?shortlisted[i].getAppliedDateFormatted():"&nbsp;")%></td>
                                                <td><a class='btn btn-xs btn-primary' href="viewApplicantProfile.html?sin=<%=shortlisted[i].getSIN()%>">View Profile</a></td>
                                              </tr>
                                        <%  } %>
                                        </tbody>  
                                      	</table>
                                        
                                        <% }else{%>
                                          No applicants currently short listed.
                                        <%}%>
                                      
      </div>
    </div>
  </div>
  <div class="panel panel-info">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse3">Applicants Not Approved <span class="displayText" style="font-weight:none;">(<%=notapproved.length%>)</span></a></h4>
    </div>
    <div id="collapse3" class="panel-collapse collapse">
      <div class="panel-body">
      								
                                        <%
                                          if(notapproved.length > 0){ %>
                                          
                                          <table id='jobapps3' class="table table-condensed table-striped" style="font-size:12px;">
                                        <thead>
                                        <tr>
                                           <th width="15%">First Name</th>  
                                           <th width="15%">Last Name</th>                                         
                                           <th width="20%">Telephone</th>
                                           <th width="25%">Email</th>
                                           <th width="15%">Applied</th>
                                           <th width="10%">Options</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                          
                                          
                                        <%   cnt = 0;
                                            for(int i=0; i < notapproved.length; i++){                                            	
                                        %>    
                                        <tr>
                                                <td><%=notapproved[i].getFirstname()%></td>                                                
                                                <td><%=notapproved[i].getSurname()%></td>
                                                <td><%=notapproved[i].getHomephone()%></td>
                                                <td><a href="mailto:<%=notapproved[i].getEmail()%>"><%=notapproved[i].getEmail()%></a></td>
                                                <td><%=((notapproved[i].getAppliedDate() != null)?notapproved[i].getAppliedDateFormatted():"&nbsp;")%></td>
                                                <td><a class='btn btn-xs btn-primary' href="viewApplicantProfile.html?sin=<%=notapproved[i].getSIN()%>">View Profile</a></td>
                                        </tr>
                                        <%  } %>
                                        </tbody>  
                                      	</table>
                                        
                                        <% }else{%>
                                           No applicants not approved.
                                        <%}%>
                                      
      </div>
    </div>
  </div>
  
  <div class="panel panel-info">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse4">Applicants Working <span class="displayText" style="font-weight:none;">(<%=working.length%>)</span></a></h4>
    </div>
    <div id="collapse4" class="panel-collapse collapse">
      <div class="panel-body">
      								
                                        <%
                                          if(working.length > 0){ %>
                                          
                                          <table id='jobapps4' class="table table-condensed table-striped" style="font-size:12px;">
                                        <thead>
                                        <tr>
                                           <th width="15%">First Name</th>  
                                           <th width="15%">Last Name</th>                                         
                                           <th width="20%">Telephone</th>
                                           <th width="25%">Email</th>
                                           <th width="15%">Applied</th>
                                           <th width="10%">Options</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                          
                                        <%  cnt = 0;
                                            for(int i=0; i < working.length; i++){                                                
                                        %>    
                                        	<tr>
                                                <td><%=working[i].getFirstname()%></td>
                                                <td><%=working[i].getSurname()%></td>                                                 
                                                <td><%=working[i].getHomephone()%></td>
                                                <td><a href="mailto:<%=working[i].getEmail()%>"><%=working[i].getEmail()%></a></td>
                                                <td><%=((working[i].getAppliedDate() != null)?working[i].getAppliedDateFormatted():"&nbsp;")%></td>
                                                <td><a class='btn btn-xs btn-primary' href="viewApplicantProfile.html?sin=<%=working[i].getSIN()%>">View Profile</a></td>
                                              </tr>
                                              
                                        <%  } %>
                                         </tbody>  
                                      	</table>
                                         <% }else{%>
                                            No applicants working.
                                        <%}%>
                                     
      </div>
    </div>
  </div>
  
  
  
</div>
   
<!-- ADMINISTRATIVE FUNCTIONS -->
		<br/><br/>                             
	<div class="no-print" align="center">
  		
                    <!--<a class="bodyLink"  href='admin_filter_job_applicants.jsp'>Filter Applicants</a>&nbsp;&nbsp;-->
                                      <a class="btn btn-xs btn-info"  href='viewSubListShortList.html?list_id=<%=list.getId()%>'>View Shortlist</a>
                                      <button type="button" class="btn btn-warning btn-xs" data-toggle="modal" data-target="#emailModal">Email Applicants</button>          
	               
	                <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
  			
  	</div> 
                              
  </div></div></div>
  
  
  
   
  
 <!-- Email Modal -->
<div id="emailModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"><span class="glyphicon glyphicon-envelope"></span> Email Applicants</h4>
      </div>
      <div class="modal-body">
       <form name="email" action="" method="post" onsubmit="return confirm_send();">
    
      <input type="hidden" name="sent" value="1">
      
       	<div class="form-group">
       	<label for="from">From:</label>
    	<input class="form-control" type="text" name="from" value="<%=usr.getPersonnel().getEmailAddress()%>" readonly="readonly">
  		</div>
        
        <div class="form-group">
       	<label for="to">To:</label>
       	<input class="form-control" type="text" name="to">    	
  		</div>
        
        <div class="form-group">
       	<label for="cc">Cc:</label>
    	<input class="form-control" type="text" name="cc"> 
  		</div>  
        
        <div class="form-group">
       	<label for="cc">Bcc:</label>
    	<textarea name="bcc" class="form-control" rows="2">
    			<%
            		for(int i=0; i < applicants.length; i++)
                	out.print(applicants[i].getEmail().replaceAll(",", ";") + ";");
            	%>    	
    	</textarea>
  		</div>            
          
        <div class="form-group">
       	<label for="subject">Subject:</label>
    	<input type="text" name="subject" class="form-control" value='NLESD Employment Opportunity System'>
  		</div>    
          
        <div class="form-group">
       	<label for="cc">Message</label>
    	<textarea name="message" class="form-control" rows="5"></textarea>
  		</div> 
          
        <input class="btn btn-xs btn-success" type="submit" value="Send" />
        <button type="button" class="btn btn-xs btn-danger" data-dismiss="modal">Cancel</button>  
    </form>
       </div>
      
    </div>

  </div>
</div> 
  
  
  
  
  
  
  
  
</body>
</html>
