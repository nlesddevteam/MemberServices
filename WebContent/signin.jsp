<%@ page language="java" isThreadSafe="false"%>

<html>
	<head>
	  <title>NLESD Member Services Login</title>
	  <meta name="google-signin-client_id" content="362546788437-ntuv1jsloeagtrkn8vqd2d1r85hnt79t.apps.googleusercontent.com">
	  <meta name="viewport" content="width=device-width, initial-scale=1.0">  
	  <meta charset="utf-8">
	  <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<!-- CSS STYLESHEET FILES CDN -->
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.0/animate.min.css"/>	
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css"/>		
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/jszip-2.5.0/dt-1.12.1/af-2.4.0/b-2.2.3/b-colvis-2.2.3/b-html5-2.2.3/b-print-2.2.3/cr-1.5.6/date-1.1.2/fc-4.1.0/fh-3.2.4/kt-2.7.0/r-2.3.0/rg-1.2.0/rr-1.2.8/sc-2.0.7/sb-1.3.4/sp-2.0.2/sl-1.4.0/sr-1.1.1/datatables.min.css"/>
 		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css" />
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous"/>
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto"/>		
		
 <!--  CSS LOCAL FILES - ANY MODIFICATIONS DO HERE-->  		
  		<link rel="stylesheet" href="/MemberServices/StaffRoom/includes/css/staffroom.css?ver=${todayVer}"/>				
	
<!-- JAVASCRIPT FILES CDN-->			
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>	
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
		<script src="https://cdn.datatables.net/v/dt/jszip-2.5.0/dt-1.12.1/af-2.4.0/b-2.2.3/b-colvis-2.2.3/b-html5-2.2.3/b-print-2.2.3/cr-1.5.6/date-1.1.2/fc-4.1.0/fh-3.2.4/kt-2.7.0/r-2.3.0/rg-1.2.0/rr-1.2.8/sc-2.0.7/sb-1.3.4/sp-2.0.2/sl-1.4.0/sr-1.1.1/datatables.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="https://cdn.ckeditor.com/4.4.5.1/standard/ckeditor.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/jQuery.print@1.5.1/jQuery.print.min.js"></script> 
		<script src="https://kit.fontawesome.com/053757fa2e.js" crossorigin="anonymous"></script>		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/jquery.validate.min.js" crossorigin="anonymous"></script>
	 	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-confirmation/1.0.7/bootstrap-confirmation.min.js" crossorigin="anonymous"></script>
	  	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/js/bootstrap-multiselect.min.js" crossorigin="anonymous"></script>
		<script src="https://cdn.datatables.net/plug-ins/1.10.21/api/order.neutral().js"></script>		
		<script src="https://cdn.jsdelivr.net/npm/chart.js@2/dist/Chart.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
		
<!-- JAVASCRIPT FILES LOCAL- ANY MODIFICATIONS DO HERE-->		
 		<script src="/MemberServices/StaffRoom/includes/js/jssor.slider-28.1.0.min.js"></script>
		<script src="/MemberServices/StaffRoom/includes/js/staffroom.js?ver=${todayVer}"></script>			 
		
	</head>

	<body>	
	<!-- Get the loading data animation ready, in front and hidden -->
	<div id="loadingSpinner" style="display:none;z-index:99999;">
	<div id="spinner" style="text-align:center;padding:5px;">
	<img src="/MemberServices/StaffRoom/includes/img/nllogo-load.png" border="0" style="padding:5px;"/><br/>
	<div class="spinner-grow text-primary"></div>
		<div class="spinner-grow text-success"></div>
		<div class="spinner-grow text-info"></div>
		<div class="spinner-grow text-warning"></div>
		<div class="spinner-grow text-danger"></div>
		<br/>Loading. Please wait!

	</div>
	</div>
	
<!-- NAVIGATION MENU -->

					<div class="headerFull">
				   			<img src="/MemberServices/StaffRoom/includes/img/header.png" style="width:100%;min-width:300px;border-top:5px solid red;" title="Newfoundland and Labrador English School District">
				   	</div>
				   	<div class="headerLarge">
				   			<img src="/MemberServices/StaffRoom/includes/img/header-large.png" style="width:100%;min-width:300px;border-top:5px solid red;" title="Newfoundland and Labrador English School District">
				   	</div>
				   	<div class="headerMedium">
				   			<img src="/MemberServices/StaffRoom/includes/img/header-medium.png" style="width:100%;min-width:300px;border-top:5px solid red;" title="Newfoundland and Labrador English School District">
				   	</div>
				   	<div class="headerSmall">
				   			<img src="/MemberServices/StaffRoom/includes/img/header-small.png" style="width:100%;min-width:300px;border-top:5px solid red;" title="Newfoundland and Labrador English School District">
				   	</div>


	
<!-- PAGE BODY -->	
			<div class="mainContainer">
				<div class="container-fluid">					
						<div id="printJob">		
						<br/><br/>
						<div class="alert alert-info" style="font-size:12px;text-align:center;">	
						Please wait to auto login to your StaffRoom account or select your NLESD Google Account to proceed.<br/> 
						<b>If this is your first login, you will be asked to register and confirm before a new account is created for you.</b>
						</div>
						<br/>
															
					  	<div id="my-signin2" style='padding: 5px; width: 240px;margin: 0 auto;text-align:center;'>
					  	<img title="Google Authentication" src="/MemberServices/StaffRoom/includes/img/google.png" border=0 style="padding-bottom:10px;"/><br/>
					  	
					  	<script src="https://accounts.google.com/gsi/client" async defer></script>
					  	
    						<div id="g_id_onload"
        						 data-client_id="362546788437-ntuv1jsloeagtrkn8vqd2d1r85hnt79t.apps.googleusercontent.com" 
        						 data-auto_select="true"
        						 data-callback="handleCredentialResponse">
    						</div>
    							<div class="g_id_signin" data-type="standard"></div>
					  	</div>
					  	
					  	<br/>
					  	
					  	<div id="my-signin2-error-alert" class="alert alert-danger" style="padding-bottom: 10px;display:none;text-align:center;"></div>
					  	
					  	<div class="alert alert-warning"><b>NOTICE:</b> 
					  	You must be a valid NLESD employee with a valid nlesd.ca email account in order to continue and/or register. 
					  	If you already had a StaffRoom (MemberServices) Account and you are asked to re-register, 
						you may have changed your email address. Please cancel at the registration screen and contact the <a href="mailto:geofftaylor@nlesd.ca?subject=StaffRoom New User Registration">System Administrator</a>
						to update your current account with your new/updated email address.</div> 
						</div>	
						<br/>	
					  	
					  						  						  	
					  	
					  	
						</div>
					</div>         
				  <br/>
				
						
						
										
			
<br/>&nbsp;<br/>

<!-- FOOTER FLOATING TAGLINE -->	
	<div class="mainFooter">	
	<div class="row" >
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">   	 		
		  <div class="copyright">&copy; 2023 NLESD &middot; All Rights Reserved &middot; <a onclick="loadingData();" href="/privacypolicy.jsp" style="color:white;">Privacy Policy</a> &middot; 
		  <a onclick="loadingData();" href="/termsofuse.jsp" style="color:white;">Terms</a></div>		
		  </div> 
	</div> 	
	</div>




<!-- ENABLE LOADING SPINNER -->
<script>
		function loadingData() {
			$("#loadingSpinner").css("display","block");
		}
</script>


	
		
		<script type="text/javascript">
			function onSuccess(googleUser) {
			  	//var profile = googleUser.getBasicProfile();
			  	var ctoken = parseJwt(googleUser.credential);
			  	  console.log('ID: ' + ctoken.sub); // Do not send to your backend! Use an ID token instead.
				  console.log('Name: ' + ctoken.name);
				  console.log('Image URL: ' + ctoken.picture);
				  console.log('Email: ' + ctoken.email);
				  var params = {};
				  //params.id_token = id_token;
				  params.id_token = googleUser.credential;
				  params.picture_u=ctoken.picture;				 
				  $.getJSON('/MemberServices/google/ajax/validate-login.html', params, function(data) {
					  console.log(data);
					  
					  if(data.success) {
					  	location.href='/MemberServices/' + data.redirect
					  }
					  else {
						  $('#my-signin2-error-alert').html(data.error).show();
						  
						  if(data.logout){
							  var gauth = gapi.auth2.getAuthInstance();
							  
							  gauth.signOut();
						  }
					  }
					  
					})
					.fail(function( jqxhr, textStatus, error ) {
					    var err = textStatus + ", " + error;
					    $('#my-signin2-error-alert').html("Request Failed: " + err ).show();
					    
					    console.log(err);
					})
					.always(function() {
						//console.log( "complete" );
					});
			  }
			  
			  function onFailure(error) {
			    console.log(error);
			    
			    $('#my-signin2-error-alert').html(error).show();
			  }
			  
			  
			  function handleCredentialResponse(response){
				  if(response && !response.error){
					  onSuccess(response);
					  
				  }else{
					  onFailure(response.error);
				  }
				  
			  }
			  function parseJwt (token) {
				    var base64Url = token.split('.')[1];
				    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
				    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
				        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
				    }).join(''));

				    return JSON.parse(jsonPayload);
				};
		</script>




		
	</body>
</html>