<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
  
  AdRequestBean[] reqs = null;
  RequestStatus status = null;
  AdRequestHistoryBean history = null;  
	  if(StringUtils.isEmpty(request.getParameter("status")))
	    status = RequestStatus.SUBMITTED;
	  else
	    status = RequestStatus.get(Integer.parseInt(request.getParameter("status")));  
	  	reqs = AdRequestManager.getAdRequestBean(status);
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>

<script>
$("#loadingSpinner").css("display","none");
</script>
 <script>
 $('document').ready(function(){
	  $("#adReq").DataTable(
		{"order": [[ 3, "desc" ]]}	  
	  );
 });
    </script>
    
    <style>
input {    
    border:1px solid silver;
		}
</style>
</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADREQUEST-APPROVE,PERSONNEL-ADREQUEST-POST" />
  
  
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b><%=status.getDescription()%> Requests</b> (<%=reqs.length%>)</div>
      			 	<div class="panel-body">   
  					The following list of <%=status.getDescription()%> Requests are sorted by Request Date by default. You can click on any column title to sort by that column.
					<%if(request.getAttribute("msg")!=null){%>
	                           <div class="alert alert-warning" style="text-align:center;">                                       
	                               <%=(String)request.getAttribute("msg")%>
	                             </div>
	                <%}%>		
        <br/><br/>
        <div class="table-responsive">          
						  <table id="adReq" class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">
						    
						    <%if(reqs.length > 0){ %>
						    	
						    	<thead>
							      <tr>
							        <th width="43%">POSITION TITLE</th>
							        <th width="20%">LOCATION</th>
							        <th width="20%">REQUESTED BY</th>
							        <th width="12%">REQUEST DATE</th>
							        <th width="5%" class="no-print">OPTIONS</th>
							      </tr>
							    </thead>
							    <tbody>
						    	
						    <%
                                    for(int i=0; i < reqs.length; i++){
                                      history = reqs[i].getHistory(RequestStatus.SUBMITTED);
                            %>
						      <tr>
						        <td><%=reqs[i].getTitle()%></td>
						        <td><%=reqs[i].getLocation().getLocationDescription()%></td>
						        <td><span style="text-transform:capitalize;"><%=((history != null)?history.getPersonnel().getFullNameReverse():"")%></span></td>
						        <td><%=history.getFormatedHistoryDate()%></td>
                                <td class="no-print"><a onclick="loadingData()" href="viewAdRequest.html?rid=<%=reqs[i].getId()%>" class="btn btn-xs btn-warning">VIEW</a></td>
						        
						      </tr>
						    <% } %>
						       </tbody>
						    <% }else { %>
						        <tbody>
						       <tr>
						        <td><span style="color:DimGrey;">Sorry, no <%=status.getDescription()%> Requests available at this time.</span></td>
						        </tr>
						        </tbody>
                              <% }%>
                                  
						  
						  </table>
						  
		</div><br/>
                     <div align="center" class="no-print">
      			 		<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a>
      			 	</div> 
         
      				</div>
      				</div>
  </div>   
         
                               
</body>
</html>
