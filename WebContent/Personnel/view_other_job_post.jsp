<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*, 
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.personnel.jobs.constants.*,
                 com.awsd.security.*"
        isThreadSafe="false"%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
<%
 	OtherJobOpportunityBean opp = null;
	if(request.getParameter("job_id") != null)
    	opp = OtherJobOpportunityManager.getOtherJobOpportunityBeanById(Integer.parseInt(request.getParameter("job_id")));
%>
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />

<html>
<head>
<title>MyHRP Applicant Profiling System</title>

<script>
			$("#loadingSpinner").css("display","none");
</script>

<script type="text/javascript">
  function openWindow(id,url,w,h, scroll) {
  var winl = (screen.width-w)/2;
  var wint = (screen.height - h - 25 )/2;
  window.open(url,id,"titlebar=0,toolbar=0,location=no,top="+wint+",left="+winl+",directories=0,status=1,menbar=0,scrollbars="+scroll+",resizable=0,width="+w+",height="+h);
}
</script>

<style>
		.tableTitle {font-weight:bold;width:20%;}
		.tableResult {font-weight:normal;width:80%;}
</style>

</head>
<body>
<br/>

<%if(request.getAttribute("msg")!=null){%>
<div class="alert alert-warning" style="text-align:center;">                                       
<%=(String)request.getAttribute("msg")%>
</div>
<%}%>

<div class="panel panel-success">
  <div class="panel-heading"><b>Viewing Job Post <%=request.getParameter("job_id")%></b></div>
  <div class="panel-body">
  				<div class="table-responsive">   
  				
                                <job:ViewOtherPost job_id='<%=Integer.parseInt(request.getParameter("job_id"))%>' />
                                <br/><br/>
                                <%if((opp != null) && (opp.isCancelled())){ %>
                                <div class="alert alert-danger" align="center">	
	                                	Position cancelled on <%= opp.getFormatedCancelledDate() %> by <%= opp.getCancelled_by() %><br/>	                                	
	                                	<b>Reason for Cancellation:</b> <%= opp.getCancelled_reason() %>
	                             </div> 
                                <%}%>
                              
                              
    <div class="no-print" align="center"> 
    
   
    
    
    
    
    
    
    
    
    <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-POSTOTHERJOB">
	                                       
	                                          	 <esd:SecurityAccessRequired roles="ADMINISTRATOR">
                                                  <a class="btn btn-danger btn-xs" href="deletePostOther.html?job_id=<%=request.getParameter("job_id")%>&title=<%=opp.getTitle()%>">Delete Post</a>
                                                </esd:SecurityAccessRequired>
                                                  <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-POSTOTHERJOB">
                                                     <%if((opp != null) && !opp.isClosed()){%>
                                                     <a class="btn btn-xs btn-primary" href='admin_post_job_other.jsp?job_id=<%=request.getParameter("job_id")%>'>Edit Post</a>
                                                    <%}%>

                                                    <%if((opp != null) &&  !opp.isCancelled()){%>
                                                      <a class="btn btn-warning btn-xs"  href="cancelPostOther.html?job_id=<%=request.getParameter("job_id")%>&title=<%=opp.getTitle()%>">Cancel Post</a>
                                                    <%}%>
                                                  </esd:SecurityAccessRequired>
                                               	
    </esd:SecurityAccessRequired>
 
 
          <a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
 
 </div>
 </div>				
							
</div>
</div>	        
</body>
</html>
