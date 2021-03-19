<!-- MyHRP (C) 2018  -->	
<!-- APPLICATION FOR NLESD STAFF (MEMBER) SERVICES -->
<!-- HTML 5 BOOTSTRAP 3.3.7 JQUERY 3.3.1 -->

<%@ page language ="java" session = "true" isThreadSafe="false"%>
<%@ page import = "java.util.*, 
									java.util.stream.*, 
									com.awsd.security.*, 
									com.esdnl.personnel.jobs.bean.*, 
									com.esdnl.personnel.jobs.dao.*, 
									com.nlesd.school.bean.*, 
									com.nlesd.school.service.*,
									org.apache.commons.lang.*" %>

<!-- LOAD JAVA TAG LIBRARIES -->
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW,RTH-NEW-REQUEST,PERSONNEL-RTH-VIEW-APPROVALS" />	

<%
  User usr = (User) session.getAttribute("usr");
%>

<html>
	<head>		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">	
	
	</head>
    
	<body>
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">					
					
					<div class="pageHeader">Human Resources Administration</div>
					<div class="pageBody">
					Welcome <span style="text-transform:capitalize;"><%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></span> to your 
					NLESD Applicant Profiling System Administration Site. Please use the navigation menu above to continue. Not all menu items are available to all users. You will only see options available for your current job position. 
					
					<br/>&nbsp;<br/>
					<b>NOTICE: </b>Due to the information and data layout this application provides, we advise using a tablet or laptop/desktop computer to use this system.
					
					<br/>&nbsp;<br/>
					<c:if test="${ msg ne null }">  
          		<div class="alert alert-warning" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>WARNING:</b> ${ msg } </div>     
         	</c:if>	
         	<c:if test="${ msgOK ne null }">  
          		<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>SUCCESS:</b> ${ msgOK } </div>     
         	</c:if>	
         	<c:if test="${ msgERR ne null }">  
          		<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>ERROR:</b> ${ msgERR } </div>     
         	</c:if>	
         	<%if(!(request.getParameter("delmess") == null) ){ %>
         		<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b><%=request.getParameter("delmess") %></b></div>
         	<%} %>
         	<%if(!(request.getParameter("delmesserr") == null)){ %>
         		<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b><%=request.getParameter("delmesserr") %></b></div>
         	<%} %>			
                  	
					
					
					<div class="alert alert-warning" style="text-align:center"><b>****** ACCESS TO INFORMATION PRIVACY NOTICE / WARNING ******</b><br/>
					The NLESD is committed to the protection of all personal information collected, used and/or disclosed in the operation and management of its activities.  
					As a  public body, all information collected, used and/or disclosed will be in accordance with Access to Information and Protection of Privacy Act,2015.
					By continuing to use this site, you agree with the above Act.</div>
										
					<div align="center"><a href="admin_stats.jsp" class="btn btn-sm btn-primary"><i class="fas fa-chart-pie"></i> Latest Hiring Statistics <i class="fas fa-chart-pie"></i></a></div>
					
					 <c:if test="${ msg ne null }">  
                   		<div class="alert alert-warning"  style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>WARNING:</b> ${ msg } </div>     
                  	</c:if>	
                  	<c:if test="${ msgOK ne null }">  
                   		<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>SUCCESS:</b> ${ msgOK } </div>     
                  	</c:if>	
                  	<c:if test="${ msgERR ne null }">  
                   		<div class="alert alert-danger"  style="margin-top:10px;margin-bottom:10px;padding:5px;"><b>ERROR:</b> ${ msgERR } </div>     
                  	</c:if>					
					
					</div>	
			</div>			
		</div>	
  
  </body>
</html>
