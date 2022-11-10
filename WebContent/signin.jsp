<%@ page language="java" isThreadSafe="false"%>

<html>
	<head>
	  <title>NLESD Member Services Login</title>
		<meta name="google-signin-client_id" content="362546788437-ntuv1jsloeagtrkn8vqd2d1r85hnt79t.apps.googleusercontent.com">
	  <meta name="viewport" content="width=device-width, initial-scale=1.0">  
	  <meta charset="utf-8">
	  <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<link rel="stylesheet" href="/MemberServices/includes/css/jquery-ui-1.10.3.custom.css" >
		<link rel="stylesheet" href="/MemberServices/includes/css/ms.css">
		<script type="text/javascript" src="/MemberServices/includes/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="/MemberServices/includes/js/jquery-ui-1.10.3.custom.js"></script>
		<script type="text/javascript" src="/MemberServices/includes/js/common.js"></script>
		<script type="text/javascript">
			$(function(){
				var images = ['0.jpg','1.jpg','2.jpg','3.jpg','4.jpg','5.jpg','6.jpg','7.jpg','8.jpg','9.jpg','10.jpg','11.jpg','12.jpg','13.jpg','14.jpg','15.jpg','16.jpg','17.jpg','18.jpg','19.jpg'];
			  $('html').css({'background': 'url(/MemberServices/includes/img/bg/' + images[Math.floor(Math.random() * images.length)] + ') no-repeat center center fixed',
			  	'-webkit-background-size':'cover',
			    '-moz-background-size':'cover',
			    '-o-background-size':'cover',
			    'background-size':'cover'});
			
				$(".img-swap").hover(
					function(){this.src = this.src.replace("-off","-on");},
				  function(){this.src = this.src.replace("-on","-off");});
			});
		</script>
	</head>

	<body>	
		<br/>
		<div class="mainContainer">
			<div class="section group">
				<div class="col full_block topper">
			  	<script src="/MemberServices/includes/js/date.js"></script>	
				</div>
				<div class="col full_block content" style="background:white;">			
					<br/>	
					<div align="center">
						<img src="/MemberServices/includes/img/msheader.png" class="msHeaderLogo"><br/>	
						<div style="width:500px; text-align:center;">
							
					  	<div id="my-signin2" style='padding: 25px; width: 240px;margin: 0 auto;'>
					  	<script src="https://accounts.google.com/gsi/client" async defer></script>
    						<div id="g_id_onload"
        						 data-client_id="362546788437-ntuv1jsloeagtrkn8vqd2d1r85hnt79t.apps.googleusercontent.com" data-auto_select="true"
        							data-callback="handleCredentialResponse">
    							</div>
    							<div class="g_id_signin" data-type="standard"></div>
					  	
					  	
					  	
					  	</div>
					  	
					  	<div id="my-signin2-error-alert" style="padding-bottom: 10px;display:none;color:Red;text-align:center;"></div>
						</div>
					</div>         
				  <br/>
				  <div class="welcomeMessage">
				  	<!-- 
						<b>Automatic New user Registration is currently disabled until further notice.</b> 
						<p>
		         	To obtain a new user account or for technical support with the login process <br/>please email Member Services Support at <a href="mailto:mssupport@nlesd.ca?subject=Member Services Support Request">mssupport@nlesd.ca</a>.
		         </p>
		          -->
		         <p>
		         	<span style="color:Red;font-weight:bold;">NOTICE:</span> This is NOT where you update your online Teaching Profile (Job Application / Personnel Package). <br/>
		           That process is a completely separate login available in <a href="/employment/teachingpositions.jsp">Applicant Services</a>.
		         </p>
				    <div align='center'>
							<A HREF='javascript:history.go(-1)'><img src="/MemberServices/includes/img/back-off.png" border="0" class="img-swap"></a>
						</div>
					</div>
					<br/><br/>
				</div>
		  </div>
		
		  <div class="section group">
		  	<div class="col full_block copyright">&copy; 2016 Newfoundland and Labrador English School District</div>
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