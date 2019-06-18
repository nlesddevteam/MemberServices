<%@ page language="java"
         isThreadSafe="false"%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<!DOCTYPE html PUBLIC"-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Pragma" content="no-cache" />
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type" />
		<meta content="utf-8" http-equiv="encoding" />
    <title>Member Services 3.0 - Kindergarten Registration - <decorator:title default="Home" /></title>

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
			
			#outer-main-table { background-color: White; border: 1px solid #00407A; }
			
			@media print {
				body { background-color:white; }
				
				.noprint { display: none; }
				
				#outer-main-table { border: none; }
			}
		</style>
		<decorator:head />      
  </head>

  <body bgcolor="#BF6200">

	<table id='outer-main-table' width="955" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr class='noprint'>
			<td>
				<img src="<c:url value='/schools/registration/kindergarten/includes/images/header.png' />" border="0" alt="Kindergarten Registration" />
			</td>
		</tr>

		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<table align="center" width="950" cellspacing="5" cellpadding="0" border="0" class="bodytext" style='min-height:600px;'>
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
					<span class="copyright">Member Services 3.0 &copy; 2013
						Newfoundland and Labrador English School District &middot; All Rights Reserved</span>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>