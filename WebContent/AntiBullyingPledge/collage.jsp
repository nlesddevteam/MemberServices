<%@ page language="java" contentType="text/html"%>


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
		
			
		
			 <div class="col full_block" style="position: relative;  text-align:center;">
			  	 <div class="txtPadding">			 	 		 	  
			 	  <jsp:include page="includes/outputAllPledgesfill.jsp" />
			 	  
			 	  </div>	
			 </div>		
			 
			
	
		</div>


 		<div class="section group">
	   		<div class="col full_block copyright">&copy; 2015 Newfoundland and Labrador English School District &middot; NLESD Privacy Policy </div>
		</div>			
  
  </div>

  </body>
</html>