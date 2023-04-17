<%@ page language="java"
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!DOCTYPE html>
<html lang="en">
  <head>

    <meta http-equiv="Pragma" content="no-cache" />
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type" />
		<meta content="utf-8" http-equiv="encoding" />
    <title>Kindergarten Registration - <decorator:title default="Home" /></title>

    <link href="<c:url value='/schools/registration/kindergarten/includes/css/style.css' />" rel="stylesheet" />
   	<link href="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/south-street/jquery-ui.css"
		        type="text/css" rel="Stylesheet" />
		        
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"  type="text/javascript"></script>
		<script src="<c:url value='/schools/registration/kindergarten/includes/js/jquery-ui-timepicker-addon.js' />"  type="text/javascript"></script>
	  <script type="text/javascript">
	      jQuery(function(){
	    	     $(".swap img").hover(
	    	          function(){this.src = this.src.replace("-off","-on");},
	    	          function(){this.src = this.src.replace("-on","-off");
	    	     });
	    	});
		</script>
		<style type="text/css">
			
			
			 body {font-family: Verdana, Geneva, sans-serif;color: Black;-webkit-text-size-adjust: 100%;background:none;background-color:none;}
html { background: radial-gradient(#ffffff,#ffffff,#FDF5E6) no-repeat center center fixed;-webkit-background-size: cover; -moz-background-size: cover; -o-background-size: cover; background-size: cover; } 
.mainContainer {font-size:11px;max-width:1250px;min-width:300px;display: block;  margin-left: auto;  margin-right: auto;padding-top:10px;padding-bottom:15px; }
    
			
			
			#outer-main-table { background-color: White; border: 1px solid #00407A; }
			
			@media print {
				body { background-color:white; }
				
				.noprint { display: none; }
				
				#outer-main-table { border: none; }
			}
		</style>
		<decorator:head />      
  </head>

  <body>
<div class="mainContainer">	
	
	<table id='outer-main-table' width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr class='noprint'>
			<td>
				<img src="<c:url value='/schools/registration/kindergarten/includes/images/header.png' />" border="0" alt="Kindergarten Registration" style="max-width:100%;"/>
			</td>
		</tr>

		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<table align="center" width="100%" cellspacing="5" cellpadding="0" border="0" class="bodytext" style='min-height:600px;'>
					<tr>
						<td style='height: 450px;' valign='top'>
							<decorator:body />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr bgcolor="#000000">
			<td>
				<div align="center">
					<span class="copyright">Member Services &copy; 2020-23
						Newfoundland and Labrador English School District &middot; All Rights Reserved</span>
				</div>
			</td>
		</tr>
	</table>
	</div>
</body>
</html>