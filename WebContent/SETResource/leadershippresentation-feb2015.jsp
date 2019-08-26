<%@ page language ="java" 
         session = "true"
         import = "com.awsd.security.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

	<head>
		<title>NLESD - Senior Educating Team Resources</title>
		<link href="includes/css/seo.css" rel="stylesheet" type="text/css">				
<script src="includes/js/jquery-1.9.1.js"></script>
	
		
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
	</script>
	</head>

	<body style="margin:20px;">
			
		<table width="800" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 3px solid Black; background-color: White;">
			<tr>
				<td>
					<img src="includes/images/setheader.png" alt="" width="800" border="0">
				</td>
			</tr>
			<tr>
				<td valign="top">
				<div class="header">Director's Leadership Update Presentation (Western Region) - February 18, 2015</div>
				
				<br/>
				<div class="mainbody">
				<esd:SecurityAccessRequired roles="ADMINISTRATOR,EXECUTIVE ASSISTANT,DIRECTOR,ASSISTANT DIRECTORS,SENIOR EDUCATION OFFICIER,SENIOR ADMINISTRATIVE OFFICER,PRINCIPAL,VICE PRINCIPAL">
				
				<iframe src="https://prezi.com/embed/wgpohmhnqg6d/?bgcolor=ffffff&amp;lock_to_path=1&amp;autoplay=0&amp;autohide_ctrls=0&amp;features=undefined&amp;token=undefined&amp;disabled_features=undefined" width="800" height="500" frameBorder="0" webkitAllowFullScreen mozAllowFullscreen allowfullscreen></iframe>
				</esd:SecurityAccessRequired>
					</div>
					
					<br/><br/>
					<div align="center">
					<A HREF='javascript:history.go(-1)'><img src="includes/images/back-off.png" border="0" class="img-swap"></a>				
					</div>
<br/>
				</td>
			</tr>
			<tr bgcolor="#000000">
				<td><div align="center" class="copyright">&copy; 2015 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
	</body>
</html>
