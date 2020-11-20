<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.jobs.bean.*, 
                  com.esdnl.personnel.jobs.dao.*,
                  com.awsd.security.*"
        isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%!
  User usr = null;
  SubListBean list = null;
%>

<%
  usr = (User) session.getAttribute("usr");
  
  if(request.getParameter("list_id") != null)
    list = SubListManager.getSubListBean(Integer.parseInt(request.getParameter("list_id")));
  else
  	list = (SubListBean)request.getAttribute("SUBLIST");
  
  session.setAttribute("JOB", null);
  session.setAttribute("SUBLIST", null);
  ApplicantSubListAuditBean[] alogs = ApplicantSubListAuditManager.getSublistAuditTrail(list.getId());
  pageContext.setAttribute("auditlogs", alogs);
%>


<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>

<script language="Javascript">
  function openWindow(id,url,w,h, scroll) {
  var winl = (screen.width-w)/2;
  var wint = (screen.height - h - 25 )/2;
  window.open(url,id,"titlebar=0,toolbar=0,location=no,top="+wint+",left="+winl+",directories=0,status=1,menbar=0,scrollbars="+scroll+",resizable=0,width="+w+",height="+h);
}
</script>
</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
 
 
	<job:ViewSubList listId='<%=list.getId()%>' />
 	<div class="panel panel-info">
  		<div class="panel-heading">Audit Log:</div>
  				<div class="panel-body" style="font-size:11px;">
 					<ol>                    
						<c:choose>
                        	<c:when test="${not empty auditlogs}">
                            	<c:forEach items="${auditlogs}" var="abean">
                             		<li>${abean.entryNotes} on ${abean.entryDateString}		</li>	                                      				                                      			
                                </c:forEach>
                                </c:when>
                                <c:otherwise>
                                	<li>No Audit Log Entries.</li>
                                </c:otherwise>
                        </c:choose>
                    </ol>
	</div></div>                    
   <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-danger">   
	               	<div class="panel-heading"><b>Administrative Options</b></div>
      			 	<div class="panel-body">
      			 	<div align="center">                            
                                          	<a class="btn btn-xs btn-primary" href='viewSubListApplicants.html?list_id=<%=list.getId()%>'>View Applicants</a>
                                          	<a class="btn btn-xs btn-primary" href='viewFilterSubListApplicants.html?list_id=<%=list.getId()%>'>Filter Applicants</a>                                          	
                                          	<a class="btn btn-xs btn-danger" href='deleteSubList.html?list_id=<%=list.getId()%>'>Delete Sublist</a>
                                          	<%if(list.isActive()){ %>
                                          		<a class="btn btn-xs btn-warning" href='deactivateSubList.html?list_id=<%=list.getId()%>'>Deactivate Sublist</a>
                                          	<%}else{%>
                                          		<a class="btn btn-xs btn-success" href='activateSubList.html?list_id=<%=list.getId()%>'>Activate Sublist</a>
                                          	<%} %>
                                          	<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
                    </div>
                    </div>
                    </div>
   </div>                                       
</body>
</html>
