<%@ page language="java"
         import="java.util.*,
                  java.text.*,com.awsd.security.*,
                  com.esdnl.servlet.*,
                  com.awsd.school.*,
                  com.esdnl.student.travel.bean.*,
                  com.esdnl.student.travel.constant.*,com.esdnl.util.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="STUDENT-TRAVEL-PRINCIPAL-VIEW" />

<%
  User usr = (User) session.getAttribute("usr");
  Form form = (Form) request.getAttribute("FORM");
  TravelRequestBean treq = (TravelRequestBean) request.getAttribute("TRAVELREQUESTBEAN");
%>


<html>
<head>
  <title>Student Travel Management</title>
 <script>
var pageWordCountConf = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 2000,
	}
</script>
  
</head>
<body style="margin-top:15px;">

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading">Add a Travel Request</div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 	
Please complete the form below. Most all fields are required (*). 
<br/><br/>


<form id="frmAddTravelRequest" action="addRequest.html" method="post" ENCTYPE="multipart/form-data" onsubmit = "return(validate());">
          <%if(treq != null){%>
            <input type='hidden' name='op' value='UPDATE'>
            <input type='hidden' name='request_id' value='<%=treq.getRequestId()%>'>
          <%}else{%>
            <input type='hidden' name='op' value='ADD'>
          <%}%>
          	
          	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	            <div class="input-group">
		    	<span class="input-group-addon">DESTINATION*:</span>
          		<input type="text" name="destination" id="destination" class="form-control" placeholder="Enter the destination(s) separated by comma." value="<%=(form!=null)?form.get("destination"):(treq != null)?treq.getDestination():""%>">
          		</div>
          	</div>
          	<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
	            <div class="input-group">
		    	<span class="input-group-addon">DEPARTURE DATE*:</span>         	
          		<input type="text" readonly name="departure_date" id="departure_date" placeholder="Enter Departure Date" class="form-control" value="<%=(form!=null)?form.get("departure_date"):(treq != null)?treq.getDepartureDateFormated():""%>">
           		</div>
           	</div>
           	<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
	            <div class="input-group">
		    	<span class="input-group-addon">RETURN DATE*:</span>         	
          		<input type="text" readonly name="return_date" id="return_date" class="form-control" placeholder="Enter Return Date" value="<%=(form!=null)?form.get("return_date"):(treq != null)?treq.getReturnDateFormatted():""%>">
            	</div>
           	</div>
            
    		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	            <div class="input-group">
		    	<span class="input-group-addon">REASON FOR TRAVEL*:</span>    		
         		<textarea name="rational" id="rational" class="form-control"><%=(form!=null)?form.get("rational"):(treq != null)?treq.getRational():""%></textarea>
     			</div>
     		</div>
     		<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
	            <div class="input-group">
		    	<span class="input-group-addon"># SCHOOL DAYS MISSED*:</span>
		    	<input type="text" name="days_missed" id="days_missed" class="form-control" placeholder="Enter Number" value="<%=(form!=null)?form.get("days_missed"):(treq != null)?treq.getDaysMissed():""%>">
    			</div>
    		</div>
     		<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
	            <div class="input-group">
		    	<span class="input-group-addon">GRADES INVOLVED*:</span>
     			<input type="text" name="grades" id="grades" class="form-control" placeholder="Grade(s)" value="<%=(form!=null)?form.get("grades"):(treq != null)?treq.getGrades():""%>">
     			</div>
     		</div>
     		<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
	            <div class="input-group">
		    	<span class="input-group-addon"># STUDENTS INVOLVED*:</span>
         		<input type="text" name="num_students" id="num_students" class="form-control" placeholder="Enter Number" value="<%=(form!=null)?form.get("num_students"):(treq != null)?Integer.toString(treq.getNumStudents()):""%>">
    			</div>
    		</div>
    		<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
	            <div class="input-group">
		    	<span class="input-group-addon">TOTAL # CHAPERONES*:</span>    		
         		<input type="text" name="total_chaperons" id="total_chaperons" class="form-control" placeholder="Enter Number" value="<%=(form!=null)?form.get("total_chaperons"):(treq != null)?Integer.toString(treq.getTotalChaperons()):""%>">
     			</div>
     		</div>
     		<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
	            <div class="input-group">
		    	<span class="input-group-addon">TOTAL # TEACHER CHAPERONES*:</span>	
     			<input type="text" name="total_teacher_chaperons" id="total_teacher_chaperons" class="form-control" placeholder="Enter Number" value="<%=(form!=null)?form.get("total_teacher_chaperons"):(treq != null)?Integer.toString(treq.getTotalTeacherChaperons()):""%>">
    			</div>
    		</div>
    		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	            <div class="input-group">
		    	<span class="input-group-addon">TEACHER CHAPERONE NAME(S)*:</span>    	
         		<input type="text" name="teacher_chaperons" id="teacher_chaperons" placeholder="Enter Full Names of Chaperones." class="form-control" value="<%=(form!=null)?form.get("teacher_chaperons"):(treq != null)?treq.getTeacherChaperon():""%>">
     			</div>
     		</div>
     		<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
	            <div class="input-group">
		    	<span class="input-group-addon">TOTAL # NON-TEACHER CHAPERONES*:</span>
     			<input type="text" name="total_nonteacher_chaperons" id="total_nonteacher_chaperons" class="form-control" placeholder="Enter Number" value="<%=(form!=null)?form.get("total_nonteacher_chaperons"):(treq != null)?Integer.toString(treq.getTotalOtherChaperons()):""%>">
      			</div>
      		</div>
      		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	            <div class="input-group">
		    	<span class="input-group-addon">NON-TEACHER CHAPERONE NAME(S)*:</span>                 
         		<input type="text" name="other_chaperons" id="other_chaperons" placeholder="Enter Full Names of Chaperones." class="form-control" value="<%=(form!=null)?form.get("other_chaperons"):(treq != null)?treq.getOtherChaperon():""%>">
  				</div>
  			</div>
  			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	            <div class="input-group">
		    	<span class="input-group-addon">Has each chaperone been approved by the Principal?*:</span> 
       		     <div class="form-control" style="overflow: auto;height:auto;">        
         			<input type="radio" name="chaperons_approved" id="chaperons_approved" value="true" <%=(form!=null && form.getBoolean("chaperons_approved"))?"CHECKED":(treq != null && treq.isChaperonsApproved())?"CHECKED":""%>> Yes
         			<input type="radio" name="chaperons_approved" id="chaperons_approved" value="false" <%=(form!=null && !form.getBoolean("chaperons_approved"))?"CHECKED":(treq != null && !treq.isChaperonsApproved())?"CHECKED":""%>> No
      			</div>
      			</div>
      		</div>
      		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	            <div class="input-group">
		    	<span class="input-group-addon">Does this trip involve billeting?*:</span> 
       		     <div class="form-control" style="overflow: auto;height:auto;">        
      				<input type="radio" name="billeting_involved" id="billeting_involved" value="true" <%=(form!=null && form.getBoolean("billeting_involved"))?"CHECKED":(treq != null && treq.isBilletingInvolved())?"CHECKED":""%>> Yes
         			<input type="radio" name="billeting_involved" id="billeting_involved" value="false" <%=(form!=null && !form.getBoolean("billeting_involved"))?"CHECKED":(treq != null && !treq.isBilletingInvolved())?"CHECKED":""%>> No
      			</div>
      			</div>
      		</div>
      		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	            <div class="input-group">
		    	<span class="input-group-addon">Is there any school fundraising associated with this trip?*:</span> 
       		     <div class="form-control" style="overflow: auto;height:auto;">      
    		    <input type="radio" name="school_fundraising" id="school_fundraising" value="true" <%=(form!=null && form.getBoolean("school_fundraising"))?"CHECKED":(treq != null && treq.isSchoolFundraising())?"CHECKED":""%>> Yes
         		<input type="radio" name="school_fundraising" id="school_fundraising" value="false" <%=(form!=null && !form.getBoolean("school_fundraising"))?"CHECKED":(treq != null && !treq.isSchoolFundraising())?"CHECKED":""%>> No
     			</div>
     			</div>
     		</div>
     		<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
	            <div class="input-group">
		    	<span class="input-group-addon">EMERGENCY CONTACT (xxx-xxx-xxxx)*:</span>        		         
	     		<input type="text" name="emergency_contact" id="emergency_contact" placeholder="Telephone Number" class="form-control" value="<%=(form!=null)?form.get("emergency_contact"):(treq != null)?treq.getEmergencyContact():""%>">
     			</div>
     		</div>
     		<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
	            <div class="input-group">
		    	<span class="input-group-addon">ITINERARY DOCUMENT (PDF ONLY)*:</span> 
	    		<input type="file" name="itinerary_document" id="itinerary_document" class="form-control" value="<%=(form!=null)?form.get("itinerary_document"):(treq != null)?treq.getIteneraryFilename():""%>">
    			</div>
    		</div>
    		<div style="clear:both;"></div>
    		<br/>	
    		<div align="center" class="formERR alert alert-danger" style="display:none;padding-bottom:10px;"></div>	
    		<%if(request.getAttribute("msgERR") != null){%>
    		<div align="center" class="alert alert-danger" style="display:none;padding-bottom:10px;"><%=(String)request.getAttribute("msgERR")%></div>	
    		 <%}%>
    		<%if(request.getAttribute("msgOK") != null){%>
    		<div align="center" class="alert alert-success" style="display:none;padding-bottom:10px;"><%=(String)request.getAttribute("msgOK")%></div>	
         	 <%}%>
         	<div align="center"><input class="btn btn-xs btn-success" type="submit" value="<%=(treq == null)?"ADD":"Update"%> REQUEST"> <a href="index.jsp" class="btn btn-xs btn-danger">CANCEL</a> <a href="index.jsp" class="btn btn-xs btn-danger">BACK TO LIST</a> </div> 
           
        </form>
   
   </div></div></div></div>   
  

 <script>
    CKEDITOR.replace( 'rational',{wordcount: pageWordCountConf,height:150});
 </script>
 
 <script>
   $('document').ready(function(){
		$("#departure_date,#return_date").datepicker({
	      	changeMonth: true,
	      	changeYear: true,
	      	dateFormat: "dd/mm/yy"
	   });
   });
 </script>
                              
</body>
</html>
