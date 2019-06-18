<%@ page language="java"
         session="true"
         import="com.awsd.common.*"
         isThreadSafe="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
<%@ taglib uri="/WEB-INF/personnel.tld" prefix="per" %>

<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN,TRAVEL-EXPENSE-EDIT-BUDGET" />

    	
    	<script type="text/javascript" src="includes/js/travel_ajax_v1.js"></script>
    	<script type="text/javascript">

    	$(document).ready(function(){
    		$('#loadingSpinner').css("display","none");
    		
	    	$('#btnAdd').click(function() {
	    		//process_message('server_message', 'Processing request...');
	    		addedittravelbudget();
	    		//document.forms[0].submit();
	    	});

	    	$('#btnClose').click(function() {
		    	if(window.opener.jQuery('#claim_details').attr('src'))
		    		window.opener.jQuery('#claim_details').attr('src', 'listTravelBudgets.html');
		    	else
		    		window.opener.location.reload();

		    	self.close();
	    	});
				
				$('#filter_applicant').click(function(){search_applicants();});
				$('#applicant_filter').change(function(){search_applicants();});

				$('#fiscal_year').val('<%=Utils.getCurrentSchoolYear()%>');
									
			});

			function search_applicants(){
				
				if($('#applicant_filter').val() == '') {
					alert('Please enter search criteria.');
					return; 
				}
				
				//$('#filter_applicant').html('Searching...');
				$('#filter_applicant').attr('disabled', 'disabled');
	
				$('#personnel_id').html("<option value='-1'>--- Select One ---</option>");
				
				
				$.post('addTravelBudget.html?op=PERSONNEL_FILTER',
					{
						criteria : $('#applicant_filter').val()
					},
					function(data){
						var xmlDoc=data;
					    
				    //formatting reference check request beans
				    
				    var beans = xmlDoc.getElementsByTagName("PERSONNEL");
				    
				    if(beans.length > 0)
				    {	
					    var strOptions = "<option value='-1'>--- Select One ---</option>";
				    	for(var i = 0; i < beans.length; i++)
				    	{
					    	strUid = beans[i].getElementsByTagName('ID')[0].childNodes[0].nodeValue;
					    	strDisplay = beans[i].getElementsByTagName('DISPLAY')[0].childNodes[0].nodeValue;
					    	
				    		strOptions = strOptions + "<option value='" + strUid + "'>" + strDisplay + "</option>";
				    	}
	
				    	$('#personnel_id').html(strOptions);
	
				    	//$('#personnel_id').effect("highlight", {}, 3000)
				    	
				    	if(beans.length == 1) {
					    	$('#personnel_id').attr('selectedIndex', 0);
				    	}
	
				    	//$('#personnel_id').focus();
				    }
				    else
					    alert('Search did not find any applicants matching your criteria.');
	
				    //$('#filter_applicant').html('Search');
				    $('#filter_applicant').removeAttr('disabled');
					}
				);
				
			}	
    	
    </script>
    	

	<div id="printJob"> 	
		
	<div class="claimHeaderText">Add/Edit Travel Budget</div>	
		
	<br/>Enter annual travel budgets for select employees.<br/><br/>
	
	<br/><div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
           <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	
	
	
    <form name="add_claim_note_form" method="post" action="addTravelBudget.html">
      <input type="hidden" name="op" value="CONFIRM" />
      <c:if test="${BUDGET ne null}">
      	<input type="hidden" name="budget_id" value="${BUDGET.budgetId}" />
      </c:if>
      <table width="100%">
        
        <tr>
          <td id="form_body" width="100%">
           
                  <table width="300" cellpadding="4" cellspacing="0" border="0">
                    <tr style="padding-left:14px;padding-top:20px;">
                      <td align="left"><b>Employee:</b><br/>
                      	<c:choose>
                      		<c:when test="${BUDGET ne null}">
                      			<input type="hidden" name="personnel_id" id="personnel_id" value="${BUDGET.personnel.personnelID}" />
                      			${BUDGET.personnel.fullName}
                      		</c:when>
                      		<c:otherwise>
                      			<input type='text' id='applicant_filter' name='applicant_filter'  class='requiredinput' placeholder="Enter Name to Search" style='width:150px;' />
								<a id='filter_applicant' href='#'><img src="includes/img/viewsm-off.png" class="img-swap" title="Search Name" border=0 style="padding-bottom:5px;"></a><br/>
								<select id="personnel_id" name="personnel_id" class='requiredinput' style='width:150px;'>
									<option value="-1">--- Select One ---</option>
								</select>	
                      		</c:otherwise>
                      	</c:choose>
                      </td>
                    </tr>
                    <tr>
                     <td>&nbsp;</td>
                     </tr>                    
                    <tr>
                      <td align="left"><b>Fiscal Year:</b><br/>
                      	<c:choose>
                      		<c:when test="${BUDGET ne null}">
                      			<input type="hidden" name="fiscal_year" style='width:100px;' value="${BUDGET.fiscalYear}" />
                      			${BUDGET.fiscalYear}
                      		</c:when>
                      		<c:otherwise>
                      			<select id='fiscal_year' name='fiscal_year' style='width:100px;' class='requiredinput'>
		                      		<c:forEach items="${FISCALYEARS}" var="year">
		                      			<option value="${year}">${year}</option>
		                      		</c:forEach>
		                      	</select> 	
                      		</c:otherwise>
                      	</c:choose>
                      </td>
                    </tr>
                    
                    <tr>
                     <td>&nbsp;</td>
                     </tr> 
                    
                    <tr>
                      <td align="left"><b>Budgeted Amount:</b><br/>
                        <input type='text'  id="budgeted_amount" name='budgeted_amount' class="requiredinput" style="width:100px;" value='${BUDGET ne null ? BUDGET.amount : "" }' />
                      </td>
                    </tr>
                                       
                    <c:if test="${BUDGET eq null}"> 
                    <tr>
                     <td>&nbsp;</td>
                     </tr> 
                    
	                    <tr>
	                      <td align="left"><b>Supervisor:</b><br/>
	                        <per:PersonnelList id="supervisor_id" role='TRAVEL-CLAIM-SUPERVISOR-VIEW' cls='requiredinput' style='width:200px;' />
	                      </td>
	                    </tr>
	                    <tr>
                     <td>&nbsp;</td>
                     </tr> 
	                    <tr id='division_row'>
	                    	<td align="left"><b>Division:</b><br/>
	                    		<select id='division_id' style='width:150px;' name='division_id' class='requiredinput'>
	                    			<option value="-1">--- Select Division ---</option>
	                    			<c:forEach items="${DIVISIONS}" var='division'>
	                    				<option value="${division.id}" ${SRULE ne null ? SRULE.division.id eq division.id ? 'SELECTED' : '' : ''}>${division.name}</option>
	                    			</c:forEach>
	                    		</select>
	                    	</td>
	                    </tr>
                    </c:if>
                    <tr>
                     <td>&nbsp;</td>
                     </tr> 
                    <tr>
                    <td>
                    
                    <img id='btnAdd' src="includes/img/save-off.png" class="img-swap" title="Save Budget" border=0> &nbsp; <a href="index.jsp"><img src="includes/img/cancel-off.png" title="Cancel" class="img-swap" border=0></a>
                    
                    </td>
                    
                    
                    </tr>
                    
                  </table>
             
                  
               
          </td>
        </tr>
      </table>
    </form>
    </div>

