<%@ page language="java" isThreadSafe="false"%>

<html>
	<head>
	  <title>NLESD StaffRoom Login</title>
	  <meta name="google-signin-client_id" content="362546788437-ntuv1jsloeagtrkn8vqd2d1r85hnt79t.apps.googleusercontent.com">
	  <meta name="viewport" content="width=device-width, initial-scale=1.0">  
	  <meta charset="utf-8">
	  <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</head>

	<body>	
	<div class="row pageBottomSpace">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
	<div class="siteBodyTextBlack">	
		
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
							
						<br/>	
			  	
</div>
</div>
</div>
		
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