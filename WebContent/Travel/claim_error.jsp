<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
  User usr = null;
  usr = (User) session.getAttribute("usr");  
%>

			<script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");  
			});
			
			</script>
  
  <div class="pageHeader" style="color:Red;">
		Search Submission Error
</div>
<div class="pageBodyText">  
       You searched for <b>${param.txt}</b>.      
       <br/><br/>   
       <div class="alert alert-danger"><b>ERROR:</b> <%=request.getParameter("msg")%></div>
       
       Please contract your supervisor or support if you feel you are receiving this message in error.        
           
</div>