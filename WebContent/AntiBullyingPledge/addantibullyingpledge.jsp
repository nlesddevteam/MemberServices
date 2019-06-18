
<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.esdnl.util.*,
                 com.nlesd.antibullyingpledge.bean.*;" 
         isThreadSafe="false"%>


<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>


<!DOCTYPE html>
<html lang="en">
  <head>
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <meta name="dcterms.created" content="Tue, 27 Jan 2015 12:42:59 GMT">
    <meta name="description" content="">
    <meta name="keywords" content="">	
	 <link rel="stylesheet" href="https://www.nlesd.ca/includes/css/jquery-ui-1.10.3.custom.css" >
	 <link rel="stylesheet" href="includes/bullyapp.css">
		<script src="https://www.nlesd.ca/includes/js/jquery-1.7.2.min.js"></script>
		<script src="https://www.nlesd.ca/includes/js/jquery-1.9.1.js"></script>
		<script src="https://www.nlesd.ca/includes/js/jquery-ui-1.10.3.custom.js"></script>
		
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
	 	</script>
	
<%
		
		AntiBullyingPledgeBean bpb = new AntiBullyingPledgeBean();
		if(request.getAttribute("BULL") != null)
		{
  			bpb = (AntiBullyingPledgeBean) request.getAttribute("BULL");

		}

			List<AntiBullyingPledgeSchoolListBean> map = bpb.getSchoolListings();

%>
<c:set var='pledges' value='<%=map%>' />
<c:set var='bpledge' value='<%=bpb%>' />
	 
<script>
$(document).ready(function() {
	//used to stored the query string with the confirm and remove
	var vars = [], hash;
	//used to store a value to tell the page that it was added to the database
	var pk=$("#pk").val();
	$("#formDisplay").show();
	
	

	//split the string and get the parameters
    var q = document.URL.split('?')[1];
    if(q != undefined){
        q = q.split('&');
        for(var i = 0; i < q.length; i++){
            hash = q[i].split('=');
            vars.push(hash[1]);
            vars[hash[0]] = hash[1];
        }
	}
    
 	

 
 	//now we check to see if was submitted or a link clicked
    if(vars.length >0 || pk ==-1)
	{

    	//pledge submitted
    	if(vars['ptype'] == null && pk ==-1)
    	{
    		//Thank you for your pledge.  An confirmation email has been sent to your email address.  Please click the link in the email to confirm your pledge.
    		$("#formDisplay").hide();
    		$(".titletext").text("Pledge Submitted");  		
    		
    		$(".messagetext").text("Thank you for your pledge.  An confirmation email has been sent to your email address. YOU MUST CONFIRM YOUR PLEDGE IN THE EMAIL BEFORE IT IS DISPLAYED ON OUR SITE. Please click the link in the email to confirm your pledge.");
    		
    	}else if(vars['ptype'] == "removed")//remove link clicked
    	{
    		if(ajaxRequestDelete(vars['rid']))
    		{
    			$("#formDisplay").hide();
        		$(".titletext").text("Pledge Removed");
        		$(".messagetext").text("Your NLESD AntiBullying Pledge has been removed.");
        		
    		}else{
    			$("#formDisplay").hide();
           		$(".titletext").text("Error Removing Pledge");
        		$(".messagetext").text("An error has occurred, please try again later.");
    		}
    		

    	}else if(vars['ptype'] == "confirmed")//confirmed link clicked
    	{
    		if(ajaxRequestConfirmed(vars['rid']))
    		{
        		$(".titletext").text("Pledge Confirmed");
        		$(".messagetext").text("Your NLESD AntiBullying Pledge has been confirmed.");
        		$("#formDisplay").hide();
    		}else{
           		$(".titletext").text("Error Confirming Pledge");
        		$(".messagetext").text("An error has occurred, please try again later.");
        		$("#formDisplay").hide();
    		}
    	}
    	
    	$("#showPledgeMessage").show();
    	$("#mainPledge").hide();
	}else{//new pledge or error on pledge submit
	   	$("#showPledgeMessage").hide();
    	$("#mainPledge").show();
	}
});

function ajaxRequestDelete(test)
{
	var isvalid=false;
	
	$.ajax(
 			{
 				type: "POST",  
 				url: "cancelAntiBullyingPledge.html",
 				data: {
 					rid: test
 				}, 
 				success: function(xml){
 					
					
 					
 					$(xml).find('INFO').each(function(){
 							var response = $(this).find("MESSAGE").text();
 							
 							
 							if(response == "PLEDGE DELETED")
 								{

 							   isvalid=true;
	                   				
 								}


 					});

 					
 				},
 				  error: function(xhr, textStatus, error){
 				      alert(xhr.statusText);
 				      alert(textStatus);
 				      alert(error);
 				  },
 				dataType: "text",
 				async: false
 			}
 		);
	
	

	return isvalid;
	

	

}

function ajaxRequestConfirmed(test)
{
	var isvalid=false;
	
	$.ajax(
 			{
 				type: "POST",  
 				url: "confirmAntiBullyingPledge.html",
 				data: {
 					rid: test
 				}, 
 				success: function(xml){
 					
					
 					
 					$(xml).find('INFO').each(function(){
 							var response = $(this).find("MESSAGE").text();
 							
 							
 							if(response == "PLEDGE CONFIRMED")
 								{
									isvalid=true;
	                   				
 								}


 					});

 					
 				},
 				  error: function(xhr, textStatus, error){
 				      alert(xhr.statusText);
 				      alert(textStatus);
 				      alert(error);
 				  },
 				dataType: "text",
 				async: false
 			}
 		);
	
	

	return isvalid;
	

	

}

$(function(){
    $("#schoolselect").change(function () {
    	var school=$('#schoolselect').val();
    	
    	
    	
    	
    	
    	
    	var newimage="includes/img/" + school + ".jpg";
    	
    	
    	$('#schoolimage').attr('src',newimage);
    	$('#showimage').show();
    	
        });
    });

function validatecheck()
{

	if ($('#chkAccept').is(":checked"))
	{
	  // it is checked
	  return true;
	}else{
		
	}
	alert("Please check the Stand Up to Bullying checkbox");
	return false;
	
}

		

</script>
  


	
<title>NLESD Anti-Bullying Pledge</title>
    
</head>
  <body> 
  <div class="mainContainer">
  	   	<div class="section group">
	   		<div class="col full_block topper">
				 	<script src="includes/date.js"></script>
			</div>
	   		
	   		<div class="col full_block content">
				 	<div class="gt760"><img src="includes/header-large.jpg" alt="My NLESD Anti-Bullying Pledge" style="max-width:100%;height:auto;"></div>
				 	<div class="gt640"><img src="includes/header-med.jpg" alt="My NLESD Anti-Bullying Pledge" style="max-width:100%;height:auto;"></div>
				 	<div class="lt640"><img src="includes/header-sm.jpg" alt="My NLESD Anti-Bullying Pledge" style="max-width:100%;height:auto;"></div>
			</div>			
			<div class="victimNotice">
			 	  IF YOU ARE A VICTIM OF BULLYING OR KNOW SOMEONE WHO IS REPORT THE INCIDENT TO YOUR SCHOOL ADMINISTRATION
		    </div>			
  		</div>

		<div class="section group">
		
			 <div class="col menu_block content">	
			 	 <jsp:include page="includes/totalPledges.jsp" />
			 	<jsp:include page="includes/menu.jsp" />
			 	<div class="menuImg"><img src="includes/stopbullying.png" alt="Stop Bullying!" style="max-width:100%;height:auto;">
			 	<div  align="center">
			 	<a href="http://www.nlesd.ca"><img src="includes/nlesdlogo.png" alt="NLESD" style="max-width:80%;height:auto;"></a><br/>
			 		Suite 601, Atlantic Place<br/>
					215 Water Street<br/>
 					St. John's, NL &middot; A1C 6C9<br/>
 					Tel: (709) 758-2372<br/><br/>
			 		</div></div>
			 		
			 </div>


  		<div class="col content_block">
			  	 <div class="txtPadding">
			 	  <span class="heading1">Complete Your Pledge</span><br/><br/>		 	  
			 	  
			 	  
			 	                     
                                            
                  <%if(request.getAttribute("msg")!=null){%>
                             <div align="center" style="color:White; font-size:12px; font-weight:bold; background-color:Red;">        
                            &nbsp;ERROR: <%=(String)request.getAttribute("msg")%> &nbsp;
                              </div><br/>     
                   <%}%>                                   
                                
                           <div id="formDisplay">
                           
                           <form  id="frmAddBullyingPledge"  action="/MemberServices/AntiBullyingPledge/addAntiBullyingPledge.html" method="post" onsubmit="return (validatecheck());" >
                               
                            <span>Please complete following form to complete your Anti-Bullying Pledge. <br/><br/><b>All fields are required.</b>                         
                                                  	 
                            
                            </span>

						   <p>
						   <div style="float:left;width:50%;">
									   	<label>
			                               <span class="frmLabel">First Name:</span><br/>
			                           <input type="text" name="firstname" tabindex=1 id="firstname"  placeholder="First Name" class="input" value="${empty bpledge ? null : bpledge.firstName}" >
			                            </label>
									   <br/><br/>
									   <label>
                               				<span class="frmLabel">Email Address:</span><br/>
                               			<input type="text" name="email" id="email" tabindex=3 class="input" placeholder="Your Email Address" value="${empty bpledge ? null : bpledge.email}">
                           			</label>
									 <br/><br/>
									  <label>
                               <span class="frmLabel">School:</span><br/>  
									   <select id="schoolselect" name="schoolselect" tabindex=5 class="inputSelect">
                                  <option value='-1' selected>Your School</option>
										<c:forEach items="${pledges}" var="pledge">
                                        	<option value='${pledge.schoolId}'>${pledge.schoolName}</option>
	                                    </c:forEach>
						 </select>
									   </label>
									   
                           </div>
                           
                           
                           
                           <div style="float:left;width:50%;">
									   <label>
			                               <span class="frmLabel">Last Name:</span><br/>
			                              <input type="text" name="lastname" id="lastname" tabindex=2 placeholder="Last Name" class="input" value="${empty bpledge ? null : bpledge.lastName}">
			                           </label>
									   <br/><br/>
						   				<label>
                               				<span class="frmLabel">Confirm Email:</span><br/>
                               				<input type="text" name="confirmemail" id="confirmemail" tabindex=4 placeholder="Re-Enter Email" class="input" value="${empty bpledge ? null : bpledge.confirmEmail}">
                           				</label>
						   				<br/><br/>
						   				<label>
                      				<span class="frmLabel">Grade/Association:</span><br/>
                      				<select id="gradeselect" name="gradeselect" tabindex=6 class="inputSelect">
                         			<option value='-1' selected>Your Grade/Association</option>
                         				<option value='0'>K</option>
																<% for(int x=1; x<14;x++) {%>
                               		<option value='<%=x%>'><%=x%></option>
                               	<%}%>
                               	<option value='99'>Graduate</option>
                               	<option value='999'>Staff</option>
                               	<option value='9999'>Community Member</option>
															</select>
                      </label>
						   
						   </div>
                           
                            <div style="float:left;width:100%;padding-top:10px;">
                            <label>
						   <span style="float:left;width:20px;"><input type="checkbox" id="chkAccept"></span><span style="float:left;width:90%;font-weight:bold;">I pledge to stand up for victims of bullying, and to try and prevent bullying wherever possible.</span>
						    </label>
                            </div>
                            
                                      
                                    
                             <div style="float:left;width:100%;padding-top:10px;">
                                    <div id="showimage" align="center" style="display:none;">
                                    <img src="includes/img/default.jpg" id="schoolimage" onError="this.onError=null;this.src='includes/img/default.jpg';"/>
                                    </div>
                                    <br/>
                                    <div align="center">                                    
											<input type="submit" value="Submit" class="submit">  &nbsp; <input type="reset" class="submit" value="Reset" />
											<input type="hidden" id="pk" value="${empty bpledge ? null : bpledge.pk}">
                                    </div>   
                                      <br/><br/>
                                      
                                      By pressing Submit, I agree with the <b><a href="policy.jsp">NLESD Submission Policy</a></b> relating to the use of my information for the purpose of this Anti-Bullying Pledge.
                                        
                                    <%if(request.getAttribute("msg")!=null){%>
                             <br/><br/><div align="center" style="color:White; font-size:12px; font-weight:bold; background-color:Red;">        
                            &nbsp;ERROR: <%=(String)request.getAttribute("msg")%> &nbsp;
                              </div><br/>     
                   <%}%> 
                   
                  <jsp:include page="includes/pledgeLegend.jsp" />
                                                        
                                  <p><div align="center" style="font-size:11px;color:Red;"><b>Important Note</b><br/>Once you make your pledge, do not forget to check your email to confirm your pledge!</div>
                                 <div class="quotes"><br/><SCRIPT  src="includes/quotes.js"></script></div>   
                                 </div>
                                </form>
                                
                                
                                
                                
                       </div>
  						
							<div id="showPledgeMessage" style="display:none;">
									<span id="titletext" class="titletext" style="color:#007F01;font-weight:bold; font-size:14px;"></span><br/>								
									
									<span id="messagetext" class="messagetext" style="color:#000000;font-size:11px;"></span>
							</div>
							
							
 				</div>	
 				
 				
 				
 				
 				
			 </div>	
 

			<jsp:include page="footer.jsp" />
                      
                      