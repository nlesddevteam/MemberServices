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
    	  budget = usr.getPersonnel().getCurrrentTravelBudget();
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
    		    approved = usr.getPersonnel().getTravelClaimsApproved();
    		    payment_pending = usr.getPersonnel().getTravelClaimsPaymentPending();
    		    paid_today = usr.getPersonnel().getTravelClaimsPaidToday();
    		    pre_submission = usr.getPersonnel().getTravelClaimsPreSubmission();
    		   rejected = usr.getPersonnel().getTravelClaimsRejected();
    	  }
    	  else
    	  {
    	    approved = null;
    	    payment_pending = null;
    	    paid_today = null;
    	    pre_submission = null;
    	    rejected = null;
    	  }
    	   	
    	
    	
    	//TreeMap<Integer, Vector<TravelClaim>> paid_today = TravelClaimDB.getClaimsPaymentPendingLetterTreeMap("A");
    	//TravelClaim claim = null;
    	%>
    	
    	<script>
    		$( document ).ready(function() {
    			$("#spinnerWarning").html("");		
    			var lastletter="A";
    			$(".letterPage").text(lastletter);
    			$.cookie("lastletter", lastletter, {expires: 1 }); //Rest on page load
    			var _alphabets = $('.alphabet > a');
			    _alphabets.click(function () {
			    	 lastletter=$.cookie('lastletter');			    	
			    	var table = $('#presub-table').DataTable();
			    	table.clear();
			    	table.destroy();
			    	
			        var _letter = $(this);
			        
			        $(this).removeClass("btn-primary").addClass("btn-danger");
			        $(this).removeClass("btn-primary").addClass("btn-danger");
			        var _ll  = "#"+$.cookie('lastletter');	
			        $(_ll).removeClass("btn-danger").addClass("btn-primary");
			        
			        _text = $(this).text();
			        
			        if(_text=="All") {
			        	$("#spinnerWarning").html("<br/>BE PATIENT - YOU SELECTED <b>ALL</b>.<br/>THIS WILL TAKE A FEW MINUTES!<br/><br/><a class='btn btn-sm btn-danger' href='index.jsp'>CANCEL</a><br/>");
			        } else {
			        	$("#spinnerWarning").html("");			        	
			        }
			        
			        _count = 0;
			        getpresubmissiontravelclaimsbyletter(_text);
			        $(".letterPage").text(_text);
			        $.cookie("lastletter", _text, {expires: 1 });    	
			    });
			    
    			$('#claimMessage').css("display","none");
    			$.cookie('backurl', 'claimsPreSubmissionLetter.html', {expires: 1 }); 
    			//$.cookie('lettertoload', _text, {expires: 1 });     		
    			getpresubmissiontravelclaimsbyletter("A");	  			
    		});
    		
		</script>
    	
    	
    	
    	
	
<img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/presub_stamp.png" style="max-width:200px;" border=0/> 
		<div class="siteHeaderBlue">Travel Claims in Pre-Submission</div>
	
	<br/>Below is a list of travel claims that are currently in pre-submission (claims yet to be submitted to supervisor) sorted by name (ascending), year (descending). 
	These include claims that were never submitted (from previous years), and those currently being completed for submission.  
	To filter listing by a lastname, please click on a letter. By default, pre-submission claims will display starting with lastnames beginning with A. To sort by any column, click on the title heading.
	Select another letter to display past pre-submission claims.<br/><br/>
<div style="float:right;font-size:72px;color:rgba(65, 105, 225,0.3);" class="letterPage"></div>
			
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	<br/>
	
	<b>Select Letter of Claiment Lastname: </b><br/>	You can select All to load the entire list of ALL claiments in the system with rejected claims and do a search, or just limit to the last name.<br/><br/>
	
		<div class="alphabet">
	          
	            <a id="A" class="first btn btn-sm btn-danger" href="#">A</a>
	            <a id="B" class="btn btn-sm btn-primary" href="#">B</a>
	            <a id="C" class="btn btn-sm btn-primary" href="#">C</a>
	            <a id="D" class="btn btn-sm btn-primary" href="#">D</a>
	            <a id="E" class="btn btn-sm btn-primary" href="#">E</a>
	            <a id="F" class="btn btn-sm btn-primary" href="#">F</a>
	            <a id="G" class="btn btn-sm btn-primary" href="#">G</a>
	            <a id="H" class="btn btn-sm btn-primary" href="#">H</a>
	            <a id="I" class="btn btn-sm btn-primary" href="#">I</a>
	            <a id="J" class="btn btn-sm btn-primary" href="#">J</a>
	            <a id="K" class="btn btn-sm btn-primary" href="#">K</a>
	            <a id="L" class="btn btn-sm btn-primary" href="#">L</a>
	            <a id="M" class="btn btn-sm btn-primary" href="#">M</a>
	            <a id="N" class="btn btn-sm btn-primary" href="#">N</a>
	            <a id="O" class="btn btn-sm btn-primary" href="#">O</a>
	            <a id="P" class="btn btn-sm btn-primary" href="#">P</a>
	            <a id="Q" class="btn btn-sm btn-primary" href="#">Q</a>
	            <a id="R" class="btn btn-sm btn-primary" href="#">R</a>
	            <a id="S" class="btn btn-sm btn-primary" href="#">S</a>
	            <a id="T" class="btn btn-sm btn-primary" href="#">T</a>
	            <a id="U" class="btn btn-sm btn-primary" href="#">U</a>
	            <a id="V" class="btn btn-sm btn-primary" href="#">V</a>
	            <a id="W" class="btn btn-sm btn-primary" href="#">W</a>
	            <a id="X" class="btn btn-sm btn-primary" href="#">X</a>
	            <a id="Y" class="btn btn-sm btn-primary" href="#">Y</a>
	            <a id="Z" class="last btn btn-sm btn-primary" href="#">Z</a>	
	            <a id="All" class="btn btn-sm btn-primary" href="#" >All</a>           
		</div>
	<br/><br/>
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
		
				<table id="presub-table" class="table table-condensed table-striped table-bordered presubClaimsTable" style="font-size:11px;background-color:White;" width="100%">	
				<thead>
					<tr style="text-transform:uppercase;font-weight:bold;">  
						<th width="15%">Employee</th>
						<th width="5%">Year</th>
						<th width="10%">Type</th>
						<th width="35%">Title/Month</th>
						<th width="10%">Total $</th>
						<th width="10%">Supervisor</th>
						<th width="10%">Region</th>						
						<th width="5%">Function</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>

		</div>
		
		
