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



    	<script>
    		$( document ).ready(function() {
    			$("#spinnerWarning").html("");		
    			var lastletter="A";
    			$(".letterPage").text(lastletter);
    			$.cookie("lastletter", lastletter, {expires: 1 }); //Rest on page load
    			var _alphabets = $('.alphabet > a');
			    _alphabets.click(function () {
			    	 lastletter=$.cookie('lastletter');			    	
			    	var table = $('#rejected-table').DataTable();
			    	table.clear();
			    	table.destroy();
			    	
			        var _letter = $(this);
			        
			        $(this).removeClass("btn-primary").addClass("btn-danger");
			        $(this).removeClass("btn-primary").addClass("btn-danger");
			        var _ll  = "#"+$.cookie('lastletter');	
			        $(_ll).removeClass("btn-danger").addClass("btn-primary");
			        
			        _text = $(this).text();
			        _count = 0;
			        
			        if(_text=="All") {
			        	$("#spinnerWarning").html("<br/>BE PATIENT - YOU SELECTED <b>ALL</b>.<br/>THIS WILL TAKE A FEW MINUTES!<br/><br/><a class='btn btn-sm btn-danger' href='index.jsp'>CANCEL</a><br/>");
					} else {
			        	$("#spinnerWarning").html("");			        	
			        }
			        
			        getrejectedtravelclaimsbyletter(_text);
			        $(".letterPage").text(_text);
			        $.cookie("lastletter", _text, {expires: 1 });    	
			    });
			    
    			$('#claimMessage').css("display","none");
    			$.cookie('backurl', 'claimsRejectedLetter.html', {expires: 1 }); 
    			//$.cookie('lettertoload', _text, {expires: 1 });     		
    			getrejectedtravelclaimsbyletter("A");    			
    		});
    		
		</script>
		
  	<img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/rejected_stamp.png" style="max-width:200px;" border=0/> 
		<div class="siteHeaderRed">Travel Claims Rejected</div>
			

	<br/>Below is a list of travel claims that are currently rejected sorted by name (ascending), year (descending).  To sort data by a particular column, click the column header or use the search tool at right to look for the person in question.
	<br/><br/><div style="float:right;font-size:72px;color:rgba(65, 105, 225,0.3);" class="letterPage"></div>
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
	
			
				<table id="rejected-table" class="table table-condensed table-striped table-bordered rejectedClaimsTable" style="font-size:11px;background-color:White;" width="100%">	
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
			