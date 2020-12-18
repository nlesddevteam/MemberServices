<%@ page language="java"
         session="true"
         isThreadSafe="false" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<%@ taglib uri="/WEB-INF/personnel.tld" prefix="per" %>
<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN" />
    	<script type="text/javascript">
    	$('document').ready(function(){
        	
    		$('#loadingSpinner').css("display","none");
    		
	    	$('#supervisor_keytype').change(function() {
		    	
	    		onKeyTypeSelected(this, "${SRULE.supervisorKey}");
	    		
	    	});
	
	    	$('#user_keytype').change(function() {
		    	
	    		onKeyTypeSelected(this, "${SRULE.employeeKey}");
	    		
	    	});

	    	$('#btnAdd').click(function() {
	    		//process_message('server_message', 'Processing request...');
	    		//document.forms[0].submit();

	    		//if(opener.location.href.indexOf('listSupervisorRules.html') > 0)
	    			//opener.location.href='listSupervisorRules.html';
	    		addeditsupervisorrule();
	    	});

	    	$('#btnClose').click(function() {
	    		var surl="viewTravelClaimSystem.html";
	    		$("#pageContentBody").load(surl);
            	
	    	});

	    	if(${SRULE ne null}) {
		    	
	    		$('#supervisor_keytype').val('${SRULE.supervisorKeyType.value}');
	    		$('#supervisor_keytype').change();
	    		
	    		$('#user_keytype').val('${SRULE.employeeKeyType.value}');
	    		$('#user_keytype').change();
	    		
	    	}

    	});

    </script>
	
		
	
	<img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/supervisorrules.png" style="max-width:200px;" border=0/> 	
	
	<div class="siteHeaderBlue">Add/Edit Supervisor Rule</div>	
		
	<br/>Below you can add/edit a supervisor rule which would be default for a claiment.
	<br/><br/>
	
	<br/><div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
           <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
		
    <form name="add_claim_note_form" method="post" action="addSupervisorRule.html">
      <input type="hidden" name="op" value="CONFIRM">
      <c:if test="${SRULE ne null}">
      	<input type="hidden" name="rule_id" id="rule_id" value="${SRULE.ruleId}" />
      </c:if>
  
 
  
  <div class="alert alert-info">
  	<div class="form-group">
    		<label><b>1. Select Supervisor Key Type:</b></label>
    		 <tra:KeyTypes id="supervisor_keytype" cls="form-control" />                         
     </div>
       <div align="center"><div id='dataLoading1'  class="spinner-border text-danger" role="status" style="display:none;text-align:center;"></div></div>             
      <div id='supervisor_row' style="display:none;">
      		<div class="form-group">
    			<label><b>Select Supervisor or Role:</b></label>
       			<span id='supervisor_row_content'></span>
      		</div>
      </div>              
        </div>
       <div class="alert alert-warning"> 
       <div class="form-group">
    			<label><b>2. Select User Key Type:</b></label>       
      			<tra:KeyTypes id="user_keytype" cls="form-control" />        
        </div>    
         <div align="center"><div id='dataLoading2'  class="spinner-border text-danger" role="status" style="display:none;text-align:center;"></div></div>       
        <div id='user_row' style="display:none;">     
        <div class="form-group">
    			<label><b>Select User or Role:</b></label>           
               <span id='user_row_content' align="left"></span>
         </div>       
         </div>                                  
      	</div>
      	<div class="alert alert-secondary"> 
        <div id='division_row' class="rowToAdd">
        <div class="form-group">
    			<label><b>3. Select Division:</b></label>   
                    	
                    		<select id='division_id' name='division_id' class='form-control'>
                    			<option value="">--- Select Division ---</option>
                    			<c:forEach items="${DIVISIONS}" var='division'>
                    				<option value="${division.id}" ${SRULE ne null ? SRULE.division.id eq division.id ? 'SELECTED' : '' : ''}>${division.name}</option>
                    			</c:forEach>
                    		</select>                    	
            </div>
            </div>
            </div>      
            
            <div align="center">
          				<a href="#" id="btnAdd"  title="Save Rule" class="btn btn-sm btn-success"><i class="fas fa-save"></i> Save Rule</a> 
                      <a href="#" class="btn btn-danger btn-sm" title="Back" onclick="loadingData();loadMainDivPage('back');return false;"><i class="fas fa-step-backward"></i> Back</a>
            </div>        
    </form>

	
