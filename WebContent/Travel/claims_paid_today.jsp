<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />
<%
User usr = null;
TravelClaims claims = null;
TreeMap pending_approval = null;
TreeMap year_map = null;
TreeMap paid_today = null; 
TravelClaim claim = null;
Iterator iter = null;
Iterator y_iter = null;
Map.Entry item = null;
Iterator p_iter = null;

  int c_cnt = 0;
  usr = (User) session.getAttribute("usr");
  
  claims = usr.getPersonnel().getTravelClaims();

	//populate initial objects from database
  if(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")){
    pending_approval = usr.getPersonnel().getTravelClaimsPendingApproval();
  }
  else
  {
    pending_approval = null;
  }
  if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"))
  {
	   
	    paid_today = usr.getPersonnel().getTravelClaimsPaidToday();
	   
  }
  else
  {

    paid_today = null;  
  }

%>

			<script>
    		$( document ).ready(function() {
    			var _alphabets = $('.alphabet > a');
			    _alphabets.click(function () {			    	
			        var _letter = $(this);
			        _text = $(this).text();
			        _count = 0;
			        gettravelclaimsbyletter(_text);
			        _alphabets.removeClass("active");
			        _letter.addClass("active");		        
			        
			    });			    
			    	    
			    
			    gettravelclaimsbyletter("All");
			    
    		});
    		
    	
    		
    		
    		
    		
			</script>
  		
	
		
	<div class="claimHeaderText">Travel Claims Paid Today</div>
	<br/>Below is a list of travel claims that have been paid today. To filter listing by a lastname, please click on a letter.<br/><br/>
	
	
	
	<% 
							int counter=0;
							c_cnt=0;							
							if((paid_today != null)&& (paid_today.size() > 0)) {
								//get count of payment pending claims
								iter = paid_today.entrySet().iterator();
						        while(iter.hasNext()){
						          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
						        }
								
								out.println("<b>Total Number of Claims Paid Today: <span style='color:Green;'>" + c_cnt +" </span></b>");
								
							}
	%>  
	
	
	<br/><br/>
	
	
	
				
		<div class="alphabet">
		 		<a class="first" href="#" style="width:20px;">All</a>
	            <a href="#">A</a>
	            <a href="#">B</a>
	            <a href="#">C</a>
	            <a href="#">D</a>
	            <a href="#">E</a>
	            <a href="#">F</a>
	            <a href="#">G</a>
	            <a href="#">H</a>
	            <a href="#">I</a>
	            <a href="#">J</a>
	            <a href="#">K</a>
	            <a href="#">L</a>
	            <a href="#">M</a>
	            <a href="#">N</a>
	            <a href="#">O</a>
	            <a href="#">P</a>
	            <a href="#">Q</a>
	            <a href="#">R</a>
	            <a href="#">S</a>
	            <a href="#">T</a>
	            <a href="#">U</a>
	            <a href="#">V</a>
	            <a href="#">W</a>
	            <a href="#">X</a>
	            <a href="#">Y</a>
	            <a class="last" href="#">Z</a>
	           
		</div>
		
		
		
		
		
				
		
		
		
		
		
		<div id="claims">
			<table id="claims-table" class="claimsTable">
				<thead>
					<tr class="listHeader">
						<th width="25%" class="listdata" style="padding:2px;">Employee</th>
						<th width="10%" class="listdata" style="padding:2px;">Type</th>
						<th width="55%" class="listdata" style="padding:2px;">Title/Month</th>						
						<th width="10%" class="listdata" style="padding:2px;">Function</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>
		</div>
		
		
