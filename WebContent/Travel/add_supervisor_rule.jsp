<%@ page language="java"
         session="true"
         isThreadSafe="false" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<%@ taglib uri="/WEB-INF/personnel.tld" prefix="per" %>
<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN" />

    	
    	<script type="text/javascript" src="includes/js/travel_ajax_v1.js"></script>
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
	
		
	<div id="printJob"> 	
		
	<div class="claimHeaderText">Add/Edit Supervisor Rule</div>	
		
	<br/>Below you cabn add/edit a supervisor rule which would be default for a claiment<br/><br/>
	
	<br/><div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
           <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
		
    <form name="add_claim_note_form" method="post" action="addSupervisorRule.html">
      <input type="hidden" name="op" value="CONFIRM">
      <c:if test="${SRULE ne null}">
      	<input type="hidden" name="rule_id" id="rule_id" value="${SRULE.ruleId}" />
      </c:if>
      <table width="100%" cellpadding="0" cellspacing="0" align="center">
        
        <tr>
          <td id="form_body" width="100%">
           
            
                  <table width="300" cellpadding="4" cellspacing="0" border="0" >
                    <tr>
                      <td align="left"><b>Select Supervisor Key Type:</b><br/>
                        <tra:KeyTypes id="supervisor_keytype" cls="requiredinput" />
                        <span id='supervisor_loading' style='font-weight:bold;font-size:12px;color:#FF0000;display:none;'>Loading Supervisor List, please wait...</span>
                      </td>
                    </tr>
                    <tr id='supervisor_row' style="display:none;">
                    	<td id='supervisor_row_content' align="left">
                    		&nbsp;
                    	</td>
                    </tr>
                     <tr>
                     <td>&nbsp;</td>
                     </tr>
                    <tr>
                      <td align="left"><b>Select User Key Type:</b><br/>
                        <tra:KeyTypes id="user_keytype" cls="requiredinput" />
                        <span id='user_loading' style='font-weight:bold;font-size:12px;color:#FF0000;display:none;'>Loading User list, please wait...</span>
                      </td>
                    </tr>
                    <tr id='user_row' style="display:none;">
                    	<td id='user_row_content' align="left">
                    		&nbsp;
                    	</td>
                    </tr>
                     <tr>
                     <td>&nbsp;</td>
                     </tr>
                    <tr id='division_row'>
                    	<td align="left"><b>Select Division:</b><br/>
                    		<select id='division_id' name='division_id' class='requiredinput'>
                    			<option value="">--- Select Division ---</option>
                    			<c:forEach items="${DIVISIONS}" var='division'>
                    				<option value="${division.id}" ${SRULE ne null ? SRULE.division.id eq division.id ? 'SELECTED' : '' : ''}>${division.name}</option>
                    			</c:forEach>
                    		</select>
                    	</td>
                    </tr>
                   <tr>
                     <td>&nbsp;</td>
                   </tr>
                    <tr>
                     
                     <td>
                        <img id='btnAdd' src="includes/img/save-off.png" class="img-swap" title="Save Rule" border=0> &nbsp; <a href="index.jsp"><img src="includes/img/cancel-off.png" title="Cancel" class="img-swap" border=0></a>
                     </td>
                     </tr>
              	 </table>
          </td>
        </tr>
      </table>
    </form>
    </div>
	
