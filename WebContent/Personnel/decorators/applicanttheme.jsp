<%@ page language="java"  import="java.util.*, java.text.*" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html>
<html>
<head>
 <title><decorator:title default="Newfoundland &amp; Labrador English School District -  Personnel Package 2.0" /></title>
  <style type="text/css">@import 'includes/personnelmenu.css';</style>
 	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/personnelmenu.js"></script> 
	<decorator:head />
</head>
<body>
<table cellpadding="0" cellspacing="0" width ="955px" align="center" style="border: 1px solid #00407A;">
<tr bgcolor="#00407A"><td align="center" style="color: #FFFFFF; font-family: Arial;"><%=(new SimpleDateFormat("EEEE, MMMM dd, yyyy")).format(Calendar.getInstance().getTime())%></td></tr>
<tr><td><img alt="Personnel Package 2.0" src="images/v2/header_applicantservices.jpg" width="760" /></td></tr>
	<tr>
		<td>		
		  <table cellpadding="2" cellspacing="2" align="center" width="945px;" class="ppbody">
  			<tr><td>				
				
			<decorator:body />
				
			</td></tr>                         
		</table>  	
		</td>
	</tr>
	<tr>
      <td width="100%" class="displayCopyright" > 
        Personnel Package 2.0 Copyright &copy; 2013 Newfoundland &amp; Labrador English School District. All Rights Reserved.
      </td>
	</tr>
</table>
</body>
</html>


				