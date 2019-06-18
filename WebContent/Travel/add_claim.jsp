<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.pdreg.*,
                 com.awsd.common.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
  User usr = null;
  Calendar cal  = null;
  TreeMap year_claims = null;
  int cur_month;
  int new_claim_id;
  Personnel[] sups = null;
  Personnel sup = null;
  Personnel p = null;
  String cur_year;
  Iterator events = null;
  Event evt = null;
  SimpleDateFormat sdf = null;
  String ctype = null;
  TravelClaim claim = null;
  String claimadded=null;	

  usr = (User) session.getAttribute("usr");
  
  if(request.getAttribute("NEW_CLAIM_ID") != null)
  {
    new_claim_id = ((Integer)request.getAttribute("NEW_CLAIM_ID")).intValue();
    claimadded=(String) request.getAttribute("CLAIM_ADDED");
  }
  else
  {
    new_claim_id = -1;
    
    year_claims = (TreeMap) request.getAttribute("YEAR-CLAIMS");
    sups = (Personnel[]) request.getAttribute("SUPERVISORS");
    sup = (Personnel) request.getAttribute("SUPERVISOR");
    sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
    ctype = (String) request.getAttribute("CLAIM-TYPE");
  }
  
%>


    <script language="JavaScript">
      function run_claim_type(type)
      {
        var group = document.forms[0].claim_type;
        group[type].click();
      }
      </script>
    <script>
    $( document ).ready(function() {
    	
    	$('#loadingSpinner').css("display","none");
    	
    	$( "#finish_date" ).datepicker({
		      changeMonth: true,//this option for allowing user to select month
		      changeYear: true, //this option for allowing user to select from year range
		      dateFormat: "dd/mm/yy"
		 });
    	$( "#start_date" ).datepicker({
		      changeMonth: true,//this option for allowing user to select month
		      changeYear: true, //this option for allowing user to select from year range
		      dateFormat: "dd/mm/yy"
		 });
        $('#claim_year').change(function(){
			adjustMonths();

        });
        
  
        $(".monthlyClaim").click(function(){
            $("#monthly_claim_info").show();
            $("#pd_claim_info").hide();
            $("#control").show();
        });
        
        $(".pdClaim").click(function(){
            $("#monthly_claim_info").hide();
            $("#pd_claim_info").show();
            $("#control").show();
        });
        
    });
    

	</script>
	<script type="text/JavaScript">
function valid(f) {
f.value = f.value.replace(/[^\w\s,\/.$+=-]/gi,'');
} 
</script>

   
      
    <form name="add_new_claim_form" id="add_new_claim_form" method="post" action="addTravelClaim.html">
      <input type="hidden" name="op" value="YEAR-SELECT"><input type='hidden' id='claimid' name='claimid'>
      
              		<div class="alert alert-warning" style="display:none;">
  						<span id="errormessage"></span>
					</div>	
             <div class="claimHeaderText">Start New Claim</div>
             Please select a claim type below to begin. A Monthly claim is for general travel claims for all employees. 
             A PD or Professional Development claim is a claim to attend special informational or training sessions offered within the district that are typically listed and registered through the PD Calendar in Member Services. In order to have a PD claim, you must be registered and have attended the session being claimed. In some cases PD claims can be submitted for situations outside the normal travel claim criteria.
             
            <br/><br/><b>Choose Claim Type:</b><br/>
              
                              <input type="radio" class="monthlyClaim" name="claim_type" id="claim_type" value="0">&nbsp;Monthly Claim.<br/>                     
                              <input type="radio" class="pdClaim" name="claim_type" id="claim_type" value="1">&nbsp;PD Expense Claim.
                          
              <br/>
              <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;text-align:center;"></div>
               <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;text-align:center;"></div>
               
              <div id="monthly_claim_info" style="padding:10px;display:none;margin-bottom:5px;margin-top:5px;font-size:11px;border:1px solid silver;background-color:#FFF8DC;">
                 <div class="claimHeaderText">Monthly Claim Information</div>
                      Fill out the infomation below to start a new monthly claim. You can only submit ONE claim per month. A claim can have multiple items included, and can be edited/updated anytime (before you submit) through the My Claims menu above once you have a claim started.
                      
                      <br/><br/><b>Select your supervisor:</b><br/><i style="color:Grey;">If your supervisor is not listed below, please contact your supervisor, school admin, or regional office.</i><br/>
	                      <%if(sups.length > 1){%>
	                        <select name="supervisor_id" id="supervisor_id" class="requiredinput" style="width:250px;;margin-top:3px;margin-bottom:5px;">
	                          <option value="SELECT SUPERVISOR">SELECT SUPERVISOR</option>
	                          <%for(int i=0; i < sups.length; i++){%>
	                            <%if((sups[i].getPersonnelID() != usr.getPersonnel().getPersonnelID()) 
	                                || usr.getUserRoles().containsKey("DIRECTOR") || usr.getUserRoles().containsKey("ADMINISTRATOR")){%>
	                              <option style="text-transform:capitalize;" value="<%=sups[i].getPersonnelID()%>" <%=((sup != null)&&(sup.getPersonnelID() == sups[i].getPersonnelID()))?"SELECTED":""%>><%=sups[i].getFullName().toLowerCase()%></option>
	                          <%  }}%>
	                        </select>
	                      <%}else{%>
	                      	<input type="hidden" name='supervisor_id' id='supervisor_id' value='<%=sups[0].getPersonnelID()%>' />
	                      	<span style="text-transform:capitalize;"><%=sups[0].getFullName().toLowerCase()%></span>
	                      <%}%>
                     <p>                     
                     <b>Select the Year of this claim:</b><br/>
                      <select name="claim_year" id="claim_year" class="requiredinput" style="width:250px;;margin-top:3px;margin-bottom:5px;" onchange="adjustMonths();" >
                          <option value="SELECT YEAR" selected>SELECT YEAR</option>
                          <option value="<%=Utils.getCurrentSchoolYear()%>" <%=(Utils.getCurrentSchoolYear().equals(request.getAttribute("YEAR-SELECT")))%>><%=Utils.getCurrentSchoolYear()%></option>
                          <%
                            cal = Calendar.getInstance();
                            for(int i=1; i < 3; i++)
                            {
                              cal.add(Calendar.YEAR, -1);
                          %>  <option value="<%=Utils.getSchoolYear(cal)%>" <%=(Utils.getSchoolYear(cal).equals(request.getAttribute("YEAR-SELECT")))?"SELECTED":""%>><%=Utils.getSchoolYear(cal)%></option>
                          <%}
                          %>
                        </select>
                     <p>
                     <b>Select the Month of the claim:</b><br/><i style="color:Grey;">If the month is missing from the list below means you already have a claim open or submitted for that month. Please check under My Claims menu above. Also, if you missed entering a claim before the system starts for the upcoming year, check the year selection above. </i><br/>
                        <select name="claim_month" id="claim_month" class="requiredinput" style="width:250px;margin-top:3px;margin-bottom:5px;">
                          <option value="-1" SELECTED>SELECT MONTH</option>
                          <%
                            cal = Calendar.getInstance();
                            
                            if(Utils.getCurrentSchoolYear().equals(request.getAttribute("YEAR-SELECT")))
                              cur_month = cal.get(Calendar.MONTH);
                            else
                              cur_month = Calendar.JUNE;

                            cal.set(Calendar.YEAR, Integer.parseInt(((String)request.getAttribute("YEAR-SELECT")).substring(0, ((String)request.getAttribute("YEAR-SELECT")).indexOf("-"))));
                            cal.set(Calendar.MONTH, Calendar.JULY);
                            cur_year = Utils.getSchoolYear(cal);
                             
                            while(cur_year.equalsIgnoreCase(Utils.getSchoolYear(cal))
                              &&(cal.get(Calendar.MONTH)!=cur_month))
                            {
                            	if((year_claims == null) ||
                                 (year_claims.get(new Integer(cal.get(Calendar.MONTH))) == null) ||
                                 (year_claims.get(new Integer(cal.get(Calendar.MONTH))) instanceof PDTravelClaim))
                              {
                          %>    <option value="<%=cal.get(Calendar.MONTH)%>"><%=Utils.getMonthString(cal.get(Calendar.MONTH)) + " " + cal.get(Calendar.YEAR)%></option>
                          <%  }
                              
                              cal.add(Calendar.MONTH, 1); 
                            }

                            if((year_claims == null)||(year_claims.get(new Integer(cal.get(Calendar.MONTH))) == null)){%>
                             <option value="<%=cur_month%>"><%=Utils.getMonthString(cur_month) + " " + cal.get(Calendar.YEAR)%></option>
                          <%}%>
                        </select>
                        <br/>
                     
              </div>
              <div id="pd_claim_info" style="padding:10px;display:none;margin-bottom:5px;margin-top:5px;font-size:11px;border:1px solid silver;background-color:#F0F8FF;">
                <div class="claimHeaderText">School PD Claim Information</div>
               Fill out the infomation below to start a new PD claim. You can submit multiple PD claims per month depending on what PD's you attend or special travel you may have.
               <br/><br/><b>PD Title:</b><br/><i style="color:Grey;">Please enter a valid title for this PD (i.e. Name of the Session).</i><br/>             
               <input type="text" id="title" name="title" value="" class="requiredinput" placeholder="Enter a PD Title" style="width:250px;margin-bottom:5px;" onkeyup="valid(this)" onblur="valid(this)">
               <br/><b>PD Description:</b><br/><i style="color:Grey;">Enter a description for this PD.</i><br/>   
               <textarea id="desc" name="desc" class="requiredinput" style="width:90%;min-width:250px; height:60px;margin-bottom:5px;" onkeyup="valid(this)" onblur="valid(this)"></textarea>
               <br/><b>PD Start Date:</b><br/>                      
               <input class="requiredinput_date" type="text" name="start_date" id="start_date" style="width:100px;margin-bottom:5px;" value="" readonly>
              <br/> <b>PD Finish Date:</b><br/><i style="color:Grey;">Finish date should be the same as start date for a 1 day event.</i><br/>
               <input class="requiredinput_date" type="text" name="finish_date" id="finish_date" style="width:100px;margin-bottom:5px;" value="" readonly>
                      
                      
                      
                    <%if(usr.getPersonnel().getSupervisor() == null){%>
	                    
	                    
	                    <br/><b>The Supervisor of this PD:</b><br/><i style="color:Grey;">If the supervisor is not listed below, please contact your supervisor, school admin, or regional office.</i><br/>
	                    
	                        <%if(sups.length > 1){%>
		                        <select name="pd_supervisor_id" id="pd_supervisor_id" style="width:250px;margin-top:3px;margin-bottom:5px;" class="requiredinput">
		                          <option value="SELECT SUPERVISOR">SELECT SUPERVISOR</option>
		                          <%for(int i=0; i < sups.length; i++){
		                              if((sups[i].getPersonnelID() != usr.getPersonnel().getPersonnelID()) 
		                                || usr.getUserRoles().containsKey("DIRECTOR") || usr.getUserRoles().containsKey("ADMINISTRATOR")){%>
		                              <option style="text-transform:capitalize;" value="<%=sups[i].getPersonnelID()%>" <%=((sup != null)&&(sup.getPersonnelID() == sups[i].getPersonnelID()))?"SELECTED":""%>><%=sups[i].getFullName().toLowerCase()%></option>
		                          <%  }}%>
		                        </select>
		                      <%}else{%>
		                      	<input type="hidden" name='pd_supervisor_id' id='pd_supervisor_id' value='<%=sups[0].getPersonnelID()%>' />
		                      	<span style="text-transform:capitalize;"><%=sups[0].getFullNameReverse().toLowerCase()%> </span>
		                      <%}%>
	                     
                    <%}%>
                 
                   

                 
              </div>
                    
		                  <div align="center" id="control" style="display:none;">
		                     
		                        <a href="#"><img src="includes/img/startclaim1-off.png" class="img-swap" border=0 title="Start Claim" onclick="findTheInvalidsAdding();submitnewtravelclaim();"></a>
		                      
		                        <a href="index.jsp"><img src="includes/img/cancel1-off.png" class="img-swap" border=0 title="Cancel Claim"></a>
		                   </div>   
    </form>
  

