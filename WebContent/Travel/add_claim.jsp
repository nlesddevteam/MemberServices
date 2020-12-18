<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
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


    <script>    

    $( document ).ready(function() {
    	
    	$('#loadingSpinner').css("display","none");    	
    	
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
        
        $(".pdClaimExt").click(function(){
        	 $("#monthly_claim_info").hide();
             $("#pd_claim_info").show();
            $("#control").show();
        });
        
    });
    
    function run_claim_type(type) {
      var group = document.forms[0].claim_type;
      group[type].click();
    }
    
    function valid(f) {
    	f.value = f.value.replace(/[^\w\s,\/.$+=-]/gi,'');
    	} 

    			var pageWordCountConf = {
    		    showParagraphs: true,
    		    showWordCount: true,
    		    showCharCount: true,
    		    countSpacesAsChars: true,
    		    countHTML: true,
    		    maxWordCount: -1,
    		    maxCharCount: 500,
    		}
    
</script>

<style>
.bootstrap-datetimepicker-widget table {font-size:11px;}
input { border:1px solid silver;}
</style>
      
    <form name="add_new_claim_form"  id="add_new_claim_form" method="post" action="addTravelClaim.html">
      <input type="hidden" name="op" value="YEAR-SELECT"><input type='hidden' id='claimid' name='claimid'>
             <img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/startclaim.png" style="max-width:300px;" border=0/>       	
            <div class="pageHeader">Start New Claim</div>
             <div class="pageBodyText">
             Please select a claim type below to begin. A Monthly claim is for general travel claims for all employees. 
             A PD or Professional Development claim is a claim to attend special informational or training sessions offered within the district that are typically listed and registered through the PD Calendar in Member Services. 
             In order to have a PD claim, you must be registered and have attended the session being claimed in the PD calendar. In some cases PD claims can be submitted for situations outside the normal travel claim criteria.
             </div>
             
            <div class="pageSubHeader">Choose Claim Type:</div>
             <div class="pageBodyText"> 
            <b>Make sure you select the proper claim type below. <span style="color:Red;">Failure to do so may result in a delayed or rejected claim.</span></b><br/><br/>
              <div style="padding-top:5px;font-size:14px;">
              <div class="form-check">
					  <label class="form-check-label">
					   &nbsp; <input type="radio" class="form-check-input monthlyClaim" name="claim_type" id="claim_type" value="0">Start a <b>Regular</b> Monthly Claim.
					  </label>
				</div>				
					   <div class="form-check">
					  <label class="form-check-label">
					    &nbsp; <input type="radio" class="form-check-input pdClaim" name="claim_type" id="claim_type" value="1">Start a <b>PD Expense Claim</b> for a Registered/Attended <b>PD Calendar Scheduled Event</b>.
					  </label>
				</div>  				
				<div class="form-check">
					  <label class="form-check-label">
					    &nbsp; <input type="radio" class="form-check-input pdClaimExt" name="claim_type" id="claim_type" value="1">Start a <b>PD Expense Claim</b> for a Registered/Attended PD Event that is <span style="color:Red;font-weight:bold;">NOT scheduled in the PD Calendar</span>.
					  </label>
				</div>                
               </div>
                
			 </div>
			 
<!-----------------  START MONTHLY CLAIM  ------------------------------------------------>       
     
             <div id="monthly_claim_info" style="padding:10px;display:none;margin-bottom:5px;margin-top:5px;border:1px solid silver;background-color:#FFF8DC;">
 			<div class="pageSubHeader"> Monthly Travel Claim Information</div>
                 
                 Fill out the information below to start a new monthly claim. <b>You can only submit ONE claim per month</b>.  All three steps are required to start a claim.
                 A claim can have multiple items included, and can be edited/updated anytime (before you submit) through the My Claims menu above once you have a claim started.           
            <br/><br/>
            <div class="alert alert-danger"><b>NOTICE:</b> 
            <ul>
            <li>If your supervisor is not listed, please contact your supervisor, school administrator, or regional office to notify they are not listed as an approved supervisor.
            <li>If the month is missing from the list means you already have a claim open or submitted for that month. Please check under My Claims menu to find the claim for that month you started or completed. 
            <li>You can only submit ONE monthly claim per month.
			<li>If you missed entering a claim before the system starts for the upcoming fiscal year, check the appropriate fiscal year selection before you enter a claim you missed to make sure its in the proper year.           
            </ul>
            </div> 
<hr><div style="font-size:24px;float:right;color:rgba(0,0,0,0.4);padding-right:5px;">1</div>
<b>1: YOUR SUPERVISOR</b><br/>      
<span class="quickAlert" id="tc_step1_error" style="display:none;"></span>
						<%if(sups.length > 1){%>
	                        <select name="supervisor_id" id="supervisor_id" class="form-control" required>
	                          <option value="SELECT SUPERVISOR">SELECT SUPERVISOR</option>
	                          <%for(int i=0; i < sups.length; i++){%>
	                            <%if((sups[i].getPersonnelID() != usr.getPersonnel().getPersonnelID()) 
	                                || usr.getUserRoles().containsKey("DIRECTOR") || usr.getUserRoles().containsKey("ADMINISTRATOR")){%>
	                              <option style="text-transform:capitalize;" value="<%=sups[i].getPersonnelID()%>" <%=((sup != null)&&(sup.getPersonnelID() == sups[i].getPersonnelID()))?"SELECTED":""%>><%=sups[i].getFullName().toLowerCase()%></option>
	                          <%  }}%>
	                        </select>	                        
	                      <%}else{%>
	                      	<input type="hidden" name='supervisor_id' id='supervisor_id' value='<%=sups[0].getPersonnelID()%>' />
	                      	&nbsp; &nbsp; <span style="font-size:12px;text-transform:capitalize;"><%=sups[0].getFullNameReverse().toLowerCase()%></span><br/>
	                    	<span style="font-size:11px;color:Green;"><i class="fas fa-check-square"></i> Your supervisor has been automatically assigned based on your position. If this supervisor is incorrect, you may change it on next page once claim is started.</span>
	                      <%}%>	                      
<hr><div style="font-size:24px;float:right;color:rgba(0,0,0,0.4);padding-right:5px;">2</div>
<b>2: SELECT YEAR OF THIS CLAIM:</b><br/>
<span class="quickAlert" id="tc_step2_error" style="display:none;"></span>
                      <select name="claim_year" id="claim_year" class="form-control" onchange="adjustMonths();" required >
                          <option value="SELECT YEAR" selected>SELECT YEAR</option>
                          <option value="<%=Utils.getCurrentSchoolYear()%>" <%=(Utils.getCurrentSchoolYear().equals(request.getAttribute("YEAR-SELECT")))%>><%=Utils.getCurrentSchoolYear()%></option>
                          <%
                            cal = Calendar.getInstance();
                            for(int i=1; i < 3; i++)
                            {
                              cal.add(Calendar.YEAR, -1);
                          %>  <option value="<%=Utils.getSchoolYear(cal)%>" <%=(Utils.getSchoolYear(cal).equals(request.getAttribute("YEAR-SELECT")))?"SELECTED":""%>><%=Utils.getSchoolYear(cal)%></option>
                        
                          <%}%>
                        </select>
<hr> <div style="font-size:24px;float:right;color:rgba(0,0,0,0.4);padding-right:5px;">3</div>                   
<b>3: SELECT MONTH OF THIS CLAIM:</b><br/>
<span class="quickAlert" id="tc_step3_error" style="display:none;"></span>
						<select name="claim_month" id="claim_month" class="form-control" required>
                          <option value="SELECT MONTH" selected>SELECT MONTH</option>
                          <%
                            cal = Calendar.getInstance();                            
                            if(Utils.getCurrentSchoolYear().equals(request.getAttribute("YEAR-SELECT"))) {
                              cur_month = cal.get(Calendar.MONTH);
                            } else {
                              cur_month = Calendar.JUNE;
                            }
                            cal.set(Calendar.YEAR, Integer.parseInt(((String)request.getAttribute("YEAR-SELECT")).substring(0, ((String)request.getAttribute("YEAR-SELECT")).indexOf("-"))));
                            cal.set(Calendar.MONTH, Calendar.JULY);
                            cur_year = Utils.getSchoolYear(cal);
                             
                            while(cur_year.equalsIgnoreCase(Utils.getSchoolYear(cal))&&(cal.get(Calendar.MONTH)!=cur_month))
                            {
                            	if((year_claims == null) ||
                                 (year_claims.get(new Integer(cal.get(Calendar.MONTH))) == null) ||
                                 (year_claims.get(new Integer(cal.get(Calendar.MONTH))) instanceof PDTravelClaim))
                              {
                          %>    
                          <option value="<%=cal.get(Calendar.MONTH)%>"><%=Utils.getMonthString(cal.get(Calendar.MONTH)) + " " + cal.get(Calendar.YEAR)%></option>
                          <%  }                              
                              cal.add(Calendar.MONTH, 1); 
                            }
                            if((year_claims == null)||(year_claims.get(new Integer(cal.get(Calendar.MONTH))) == null)){%>
                             <option value="<%=cur_month%>"><%=Utils.getMonthString(cur_month) + " " + cal.get(Calendar.YEAR)%></option>
                          <%}%>
                        </select>                                                                             
                        <br/><br/>           
            
            </div>
              
 <!--------------------------START A  PD CLAIM--------------------------------------- -->      
        
              <div id="pd_claim_info" style="padding:10px;display:none;margin-bottom:5px;margin-top:5px;border:1px solid silver;background-color:#F0F8FF;">
               <div class="pageSubHeader">PD Claim Information</div>              
               You can submit multiple PD claims per month depending on what PD's you attend or special travel you may have. 
              <br/><br/>
              <div class="alert alert-danger"><b>NOTICE:</b> 
            		<ul>
            		<li> Before you can put in a PD Travel Claim for a particular NLESD event, the <b>event attendance must have been completed and confirmed</b> by the PD event scheduler.
            		<li>You can only submit a PD Claim for an event you have been confirmed to have attended by the PD event scheduler.        
              		<li> Finish date should be the same as start date for a 1 day event.
            		<li>If your supervisor is not listed, please contact your supervisor, school administrator, or regional office to notify they are not listed as an approved supervisor.
           			<li>If the PD event you attended is not listed, change the PD type above as it may not be a NLESD Scheduled PD. If it was listed in the PD Calendar, and its not listed in the dropdown below, contact the event scheduler as they may need to complete event attendance.
            		 </ul>
            		</div>
 
 <hr><div style="font-size:24px;float:right;color:rgba(0,0,0,0.4);padding-right:5px;">1</div>
 <b>1. YOUR SUPERVISOR:</b><br/>	              
	                     <span class="quickAlert" id="pd_step5_error" style="display:none;"></span>	                     
                    <%if(sups.length > 1){%> 
		                        <select name="pd_supervisor_id" id="pd_supervisor_id" class="form-control">
		                          <option value="SELECT SUPERVISOR">SELECT SUPERVISOR</option>
		                          <%for(int i=0; i < sups.length; i++){
		                              if((sups[i].getPersonnelID() != usr.getPersonnel().getPersonnelID()) 
		                                || usr.getUserRoles().containsKey("DIRECTOR") || usr.getUserRoles().containsKey("ADMINISTRATOR")){%>
		                              <option style="text-transform:capitalize;" value="<%=sups[i].getPersonnelID()%>" ><%=sups[i].getFullName().toLowerCase()%></option>
		                          <%  }}%>
		                        </select>
		                      <%}else{%>
		                      	<input type="hidden" name='pd_supervisor_id' id='pd_supervisor_id' value='<%=sups[0].getPersonnelID()%>' />
		                      	&nbsp; &nbsp; <span style="font-size:14px;text-transform:capitalize;"><%=sups[0].getFullNameReverse().toLowerCase()%> </span><br/>
		                     	<span style="font-size:11px;color:Green;"><i class="fas fa-check-square"></i> Your supervisor has been automatically assigned based on your position. If this supervisor is incorrect, you may change it on next page once claim is started.</span>
	                    
		                      <%}%>	                
           
<hr><div style="font-size:24px;float:right;color:rgba(0,0,0,0.4);padding-right:5px;">2</div>
               <b>2. PD Title:</b><br/>
               <div class="pdRegular">
             Select the PD Event you officially attended from the dropdown menu below.   
             <br/><br/>
                <select id="pdtitle" class="form-control"  onchange="getpdclaimeventdetails();">
                <option value="-1">Please Select PD Event</option>
                <c:forEach var="p" items="${PDEVENTS}">
                <option value="${p.value }">${p.key}</option>
                </c:forEach>
               </select>    
               <br/><br/>
                             
               </div>
               <div class="pdExternal">               
               Please enter a valid title for this PD event (i.e. Name of the Session. Max 100 chars.).  <br/>
               <span style="font-size:11px;color:Red;"><i class="fas fa-exclamation-circle"></i>If this event IS in the PD Calendar, please change your PD Claim type  to a PD Calendar Scheduled Event above.</span>            
                <br/><br/>
                </div>
                 <input type="text" id="title" name="title" value="" class="form-control pdT" placeholder="Enter a PD Title" style="width:100%;max-width:600px;" onkeyup="valid(this)" onblur="valid(this)" maxlength="100" autocomplete="off">
                             
                <span class="quickAlert" id="pd_step3_error" style="display:none;"></span>                  
<hr><div style="font-size:24px;float:right;color:rgba(0,0,0,0.4);padding-right:5px;">3</div>             
              <b>3. PD START DATE:</b><br/>
               <span class="dateMsg"></span>
               <span class="quickAlert" id="pd_step1_error" style="display:none;"></span>
               <input  type="text" class="form-control datetimepicker-input" name="start_date" id="start_date" data-toggle="datetimepicker" placeholder="Start Date" data-target="#start_date" autocomplete="off" readonly/>		      

<hr><div style="font-size:24px;float:right;color:rgba(0,0,0,0.4);padding-right:5px;">4</div>
<b>4. PD FINISH DATE:</b><br/> 
  			 <span class="dateMsg"></span>
  			 <span class="quickAlert" id="pd_step2_error" style="display:none;"></span>
            <input type="text" class="form-control datetimepicker-input" name="finish_date" id="finish_date" data-toggle="datetimepicker" placeholder="Finish Date" data-target="#finish_date" autocomplete="off" readonly/>              

<hr><div style="font-size:24px;float:right;color:rgba(0,0,0,0.4);padding-right:5px;">5</div>
<b>5. PD DESCRIPTION:</b><br/>   
             <span class="descMsg"></span>
                <span class="quickAlert" id="pd_step4_error" style="display:none;"></span>
               <textarea id="desc" name="desc" class="form-control"  onkeyup="valid(this)" onblur="valid(this)"></textarea>                                  
             <br/>
             

                 
              </div>
              
           
              
              
     <script>  
     $(document).ready(function() {
    	 
    	 
    	 
    	 
    	 
    	   $(".pdClaimExt").click(function(){    		  
    		   if ($(".pdClaimExt").is(":checked")) {	    
    			   if (CKEDITOR.instances.desc) {   	    		
    	   	    		  CKEDITOR.instances.desc.destroy();
    	   	    		 }     	
    			   //$("#title").removeAttr("disabled");
    			  // $("#title").attr("type","text");
    			   $(".pdT").css("display","block");
    			   $("#desc").attr("readonly", false);     			  
    			   $("#start_date").attr("readonly", false); 
    			   $("#finish_date").attr("readonly", false); 
    			   $(".pdExternal").css("display","block");
    			   $(".pdRegular").css("display","none");
    			   $("#pd_claim_info").css("background-color","#FFF0F5");
    			   $('.dateMsg').html("Click in field to select date<br/>");
    			   $('.descMsg').html("Description of this PD as entered by the event scheduler.<br/>");
    			   CKEDITOR.replace('desc',{wordcount: pageWordCountConf,height:150});
    			}	    
    		 });    	
    	   
    	   $(".pdClaim").click(function(){    		  
    		   if ($(".pdClaim").is(":checked")) {
    			   
    			   //$("#title").prop("disabled",true);
    			   //$("#title").attr("type","hidden");
    			   $(".pdT").css("display","none")
    			   $(".pdExternal").css("display","none");
    			   $(".pdRegular").css("display","block");    			   
    			  if (CKEDITOR.instances.desc) {   	    		
   	    		  CKEDITOR.instances.desc.destroy();
   	    		 }     			   
    			   $("#pd_claim_info").css("background-color","#F0F8FF");
    			   $("#desc").attr("readonly",false);
    			   $("#start_date").attr("readonly",true); 
    			   $("#finish_date").attr("readonly", true); 
    			  // $('#desc').val('');
    			    CKEDITOR.replace('desc',{wordcount: pageWordCountConf,height:150});
    			   $('#start_date').val('');
    			   $('#finish_date').val('');
    			   $('#title').val('');
    			   $('.dateMsg').html("Date will be <span style='color:green;'>auto filled</span> based on selected PD event above.<br/>");
    			   $('.descMsg').html("Description will be <span style='color:green;'>auto filled</span> based on selected PD event above.<br/>");
    			}	    
    		 });
    	   
    	  });
     
      $(function () {
		                $('#start_date').datetimepicker({
		                	format: "DD/MM/YYYY"
		                });
		            });
       $(function () {
                	$('#finish_date').datetimepicker({
                		format: "DD/MM/YYYY"
                	});
           		 });
</script>             
              
              
              
              
                    
		                  <div align="center" id="control" style="display:none;">		                     
		                        <a href="#" class="btn btn-xs btn-success"  title="Start Claim"  onclick="findTheInvalidsAdding();submitnewtravelclaim();return false;">Start This Claim</a>		                      
		                        <a href="index.jsp" class="btn btn-xs btn-danger" title="Cancel Claim">Cancel This Claim</a>
		                  </div>   
    </form>
  
