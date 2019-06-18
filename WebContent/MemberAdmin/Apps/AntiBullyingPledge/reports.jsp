<%@ page language ="java" 
         session = "true"
         import = "java.util.*,java.text.DateFormat,
         					 com.nlesd.antibullyingpledge.bean.*,
         					 com.nlesd.antibullyingpledge.dao.*,com.esdnl.util.*,com.awsd.security.*,com.nlesd.antibullyingpledge.dao.*"
         isThreadSafe="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<c:set var='reportvalues' value='<%=AntiBullyingPledgeManager.ReportSelection.values()%>' />
<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
		<title>NLESD - AntiBullying Pledge Admin</title>
		<script language="Javascript" src="js/jquery-1.9.1.min.js"></script>
		<script src="js/jquery-ui.js"></script>
  		<link href="/MemberServices/css/memberadmin.css" rel="stylesheet" />
  		<script type="text/javascript">
$(function()
{
	$("#runreport").click
	(
		function(e)
		{
    		var selectreport = $.trim($('#selectreport').val());
    		var selectperiod = $.trim($('#selectperiod').val());
    		if(selectreport == "-1")
    		{
    			alert("Please select a report");
    			return;
    		}
    		if(selectreport == "4")
    		{
    				if($.trim($('#selectperiod').val()) == "-1")
    				{
    					alert("Please select a reporting period.");
    					return;
    				}
    		}
    		
    		if(selectreport == "3")
    		{
    	    	//clear table
    	    	cleartable();
    	    	//get new data
    	    	ajaxRequestInfo();
    	    	//hid report image div
    	    	document.getElementById("divrun").style.display = 'none';
    	    	//unhide datatable div
    	    	document.getElementById("tabledata").style.display = 'block';
    	    	
    		}else{
			var test="";
			if(selectreport == "4")
			{
				test="testingreport.html?report=" + selectreport + "&selectperiod=" + selectperiod;
			}else{
				test="testingreport.html?report=" + selectreport;
			}
			$("#imgchart").attr("src",test);
		
	    	//hid report image div
	    	document.getElementById("divrun").style.display = 'block';
	    	//unhide datatable div
	    	document.getElementById("tabledata").style.display = 'none';
		}	
		
	});
});
function ajaxRequestInfo()
{
	var isvalid=false;
	$.ajax(
 			{
 				type: "POST",  
 				url: "showSchoolPledgeTotals.html",
 				data: {
 					
 				}, 
 				success: function(xml){
 					var i=1;
 					$(xml).find('BULLYING-PLEDGE-TOTAL').each(function(){
 							
 							if($(this).find("MESSAGE").text() == "PLEDGESFOUND")
 								{
									var newrow="";
									if(i % 2 == 0)
									{
									newrow ="<tr style='background-color:#E5F2FF;' id='" + $(this).find("SCHOOLID").text() + "'>";
 									}else{
 										newrow ="<tr style='background-color:#white;' id='" + $(this).find("SCHOOLID").text() + "'>";
 									}
 									//alert("found");
									//now we add each one to the table
									newrow += "<td>" + $(this).find("SCHOOLNAME").text() + "</td>";
									newrow += "<td align='center'>" + $(this).find("TOTALPLEDGES").text() + "</td>";
									newrow += "<td align='center'>" + $(this).find("PLEDGESCONFIRMED").text() + "</td>";
									$('table#showpledges tr:last').after(newrow);
									i=i+1;
									isvalid=true;
	                   				
 								}else{
 									//alert($(this).find("MESSAGE").text());
 									
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
function cleartable()
{
	$('#showpledges td').parent().remove();
}
   $(function(){
	$("#selectreport").change(function(){
   		var selectedvalue = this.value;
		//now we will show the right entry box
		if(selectedvalue == 4)
		{
			$('#periodheader').show();
			$('#periodselect').show();
		}else{
			$('#periodheader').hide();
			$('#periodselect').hide();
		}	
	});
});
</script>
	</head>
	<body style='margin:0px; background-image:url(/MemberServices/MemberAdmin/images/bg.gif);'>
	<br>
	<br>
	<table align="center" width="100%" cellpadding="0" cellspacing="0">
	  <tr>
	    <td width="100%" valign="top" bgcolor="#333333" colspan="2">
	    </td>
	  </tr>
			<tr>
				<td height="400" width="25%" valign="top">
					<table border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr align="left">
							<td align="left"><span class="header1">AntiBullying Pledge Reports</span>
							<br>
							<br>
							</td>
						</tr>
						<tr  align="left">
							<td align="left"><span class="header1">Please select a report</span>
							<br>
							<br>
							</td>
						</tr>
						<tr>
							<td align="left">
								<select id="selectreport">
									<c:forEach items="${reportvalues}" var="report">
                                           <option value='${report.id}'>${report.description}</option>
                                    </c:forEach>
								</select>
								<br>
								<br>
							</td>
						</tr>
						<tr>
						<tr  align="left">
							<td align="left">
								<div id="periodheader" style="display:none;">
								<span class="header1">Please select the reporting period</span>
							</div>
							<br>
							<br>
							</td>
						</tr>
						<tr>
							<td align="left">
								<div id="periodselect" style="display:none;">
								<select id="selectperiod">
									<option value="-1">Please select</option>
									<option value="7">Last Week</option>
									<option value="14">Last Two Weeks</option>
									<option value="21">Last Three Weeks</option>
									<option value="28">Last Four Weeks</option>
								</select>
								</div>
								<br>
								<br>
							</td>
						</tr>
						<tr>
						<td><input type="button" value="Run Report" id="runreport"></td>
						</tr>
				</table>
				</td>
				<td width="75%">
					<div id="divrun" style="display:none;">
						<center>
							<img src="" id="imgchart" alt="">
						</center>
					</div>
				<div id="tabledata" style="display:none;">
					<table align="center" width="100%" cellspacing="1" style="font-size: 11px;" cellpadding="1" border="0" id="showpledges">
						<tr  bgcolor="#E5F2FF"><th colspan="3" align="center"><h1>Total Pledges By School</h1></th></tr>
						<tr  bgcolor="#E5F2FF">
							<th align="left">School Name</th>
							<th>Total Pledges</th>
							<th>Pledges Confirmed</th>
						</tr>
						<tr><td colspan="3"></td></tr>
					</table>
				</div>
				</td>
			</tr>
		</table>
	</body>
</html>