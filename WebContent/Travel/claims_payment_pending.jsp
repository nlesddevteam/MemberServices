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
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
User usr = null;
TravelClaims claims = null;
TreeMap year_map = null;
TreeMap pending_approval = null;
TreeMap approved = null;
LinkedHashMap payment_pending = null;
LinkedHashMap pre_submission=null;
LinkedHashMap rejected=null;
TreeMap paid_today = null; 
TravelClaim claim = null;
Iterator iter = null;
Iterator y_iter = null;
Map.Entry item = null;
DecimalFormat df = null;
DecimalFormat dollar_f =  null;
TravelBudget budget = null;
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
	   
	    payment_pending = usr.getPersonnel().getTravelClaimsPaymentPending();
	   
  }
  else
  {
   
    payment_pending = null;
  
  }

%>








    	<% 
    	//TreeMap<Integer, Vector<TravelClaim>> paid_today = TravelClaimDB.getClaimsPaymentPendingLetterTreeMap("A");
    	//TravelClaim claim = null;
    	%>
    	<script>
    		$( document ).ready(function() {
    			$('#claimMessage').css("display","none");
    			var d = new Date();
    			var n = parseInt(d.getFullYear());   			
    			 $("#secondMessage").html("").css("display","none");
    			var _alphabets = $('.alphabet > a');
			    _alphabets.click(function () {
			        var _letter = $(this);
			        _text = $(this).text();
			        _count = 0;
			        
			        var n = $('input[name="yearSelect"]:checked').val();
			        
			        getpendingtravelclaimsbyletter(_text,n);
			        _alphabets.removeClass("active");
			        _letter.addClass("active");
					
			    });
			    
			    $("input[name='yearSelect']").change(function(){
			    	
				var n = $('input[name="yearSelect"]:checked').val();
			        
			        getpendingtravelclaimsbyletter("A",n);	
			        
			        $("#secondMessage").html("<br/>(May take a few minutes)").css("display","inline");
			        _alphabets.removeClass("active");
			       
			        
			        
			    });
			    $("#secondMessage").html("<br/>(May take a few minutes)").css("display","inline");
			    getpendingtravelclaimsbyletter("A",n);
    		});
    		
		</script>
  		
<c:set var="now" value="<%=new java.util.Date()%>" />
 <fmt:formatDate pattern="yyyy" value="${now}" var="thisYear" /> 		

	
	<div class="claimHeaderText">Travel Claims Payment Pending</div>
	<br/>Below is a list of travel claims that are currently pending payment for the last 10 years. To filter listing by a lastname, please click on a letter. By default, the current years pending claims will display starting with lastnames beginning with A. Select another year to display past pending claims.<br/><br/>
	
	
	
	
	
	<%
					   		int counter=0;
							c_cnt=0;
							if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")){
							if((payment_pending != null)&& (payment_pending.size() > 0)) {
								//get count of payment pending claims
								iter = payment_pending.entrySet().iterator();
						        while(iter.hasNext()){
						          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
						        }
								
						        out.println("<b>Total Number of Claims Pending Payment: <span style='color:Red;''>" + c_cnt +" </span></b>");
								
							}
							}
							%>
	
	
	
	
	<br/><br/>
	
	<form>
	<b>Select Year:</b><br/>
  	<input type="radio" name="yearSelect" id="yearSelect1" value="${thisYear}" checked> ${thisYear} &nbsp; 
   	<input type="radio" name="yearSelect" id="yearSelect2" value="${thisYear-1}"> ${thisYear-1} &nbsp;
  	<input type="radio" name="yearSelect" id="yearSelect3" value="${thisYear-2}"> ${thisYear-2} &nbsp;
  	<input type="radio" name="yearSelect" id="yearSelect4" value="${thisYear-3}"> ${thisYear-3} &nbsp;
  	<input type="radio" name="yearSelect" id="yearSelect5" value="${thisYear-4}"> ${thisYear-4} &nbsp;
  	<input type="radio" name="yearSelect" id="yearSelect4" value="${thisYear-5}"> ${thisYear-5} &nbsp;
  	<input type="radio" name="yearSelect" id="yearSelect4" value="${thisYear-6}"> ${thisYear-6} &nbsp;
  	<input type="radio" name="yearSelect" id="yearSelect4" value="${thisYear-7}"> ${thisYear-7} &nbsp;
  	<input type="radio" name="yearSelect" id="yearSelect4" value="${thisYear-8}"> ${thisYear-8} &nbsp;
  	<input type="radio" name="yearSelect" id="yearSelect4" value="${thisYear-9}"> ${thisYear-9}	<br/>
	</form> 
	
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	<br/><span id="claimMessage" style="display:none;"><span id="numberPending" style="color:Red;font-weight:bold;"></span> Claims Pending Payment for <span id="numberPendingDate" style="color:Red;font-weight:bold;"></span> based on lastname(s) starting with letter <span id="numberPendingSelection" style="color:Red;font-weight:bold;"></span>.</span><p>
		
	
	<br/>
	<b>Select Letter: </b><br/>	
		<div class="alphabet">
	            <a href="#" style="width:20px;">All</a>
	            <a class="first" href="#">A</a>
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
						<th width="20%" class="listdata" style="padding:2px;">Employee</th>
						<th width="10%" class="listdata" style="padding:2px;">Type</th>
						<th width="60%" class="listdata" style="padding:2px;">Title/Month</th>
						<th width="10%" class="listdata" style="padding:2px;">Year</th>
						<th width="10%" class="listdata" style="padding:2px;">Function</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>

		</div>
		
