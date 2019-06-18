<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Newfoundland &amp; Labrador English School District - Event Calendar</title>
		<style type="text/css">@import 'css/redmond/jquery-ui-1.8.16.custom.css';</style>
	  <script type="text/javascript" src='js/jquery-1.6.1.min.js'></script>
	  <script type="text/javascript" src='js/jquery-ui-1.8.16.custom.min.js'></script>
	  <script type="text/javascript">
	  $('document').ready(function(){
			
			$('#loading-image').dialog({
				autoOpen: true,
				bgiframe: true,
				//width: 270,
				//height: 200,
				modal: true,
				hide: 'explode',
				closeOnEscape: false,
				draggable: false,
				resizable: false,   
				open: function(event, ui) { 
					$(".ui-dialog-titlebar").hide(); 
				}
			});
			
	  });
	  </script>
	</head>
	<body>
		<div id="loading-image">
			<table cellspacing='3' cellpadding='3' border='0' align="center">
				<tr>
					<td class="displayHeaderTitle" align="center" valign="middle">
						<img src='images/admin-index-loader.gif' /><span style="font-weight:bold;color:#C0C0C0;">Loading Calendar...</span>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>