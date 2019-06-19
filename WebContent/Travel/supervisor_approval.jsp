<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
         		 com.awsd.personnel.profile.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>  
        
       
        
        
        
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
  User usr = null;
  Profile profile = null;
  String op;
  Iterator iter = null;
  PersonnelCategory cat = null;

  usr = (User) session.getAttribute("usr");
  
  profile = (Profile) request.getAttribute("CURRENT_PROFILE");
  op = request.getParameter("op");
  
  
  TravelClaims claims = null;
  TreeMap year_map = null;  
  TreeMap approved = null;
  TreeMap payment_pending = null;
  TreeMap paid_today = null; 
  TravelClaim claim = null;
 
  Iterator y_iter = null;
  Map.Entry item = null;
  DecimalFormat df = null;
  DecimalFormat dollar_f =  null;
  TravelBudget budget = null; 
  
   
  claims = usr.getPersonnel().getTravelClaims();
  budget = usr.getPersonnel().getCurrrentTravelBudget();

  
  df = new DecimalFormat("#,##0");
  dollar_f = new DecimalFormat("$#,##0");
  
  
%>


		
			<script>
			
			
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");   
    			
        		
        		
			      		
			});
			
			
			
			</script>
			
			<script>
				
				var surl="includes/claimsToApprove.jsp";				
				$("#pageContentList").load(surl);			
				
				
				 
				 
				 
				 </script>
	

	
		
					
	
	
    <div class="claimHeaderText">Claims Awaiting Your Approval</div>
    
   
          <br/>    
                    
         <p><div id="pageContentList"><div style="color:white;background-color:Red;padding:3px;text-align:center;"> &nbsp; <b>*** PLEASE WAIT ***</b><br/>Retrieving claims awaiting your approval. (This may take a few minutes.)&nbsp; </div></div>            
           

    
    

