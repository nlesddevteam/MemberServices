<%@ page language ="java" 
         session = "true"
         import = "java.util.*,java.text.DateFormat,
         					 com.nlesd.antibullyingpledge.bean.*,
         					 com.nlesd.antibullyingpledge.dao.*,com.esdnl.util.*,com.awsd.security.*"
         isThreadSafe="false"%>
         
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />
<%
	//SurveyBean[] surveys = SurveyManager.getSuveryBeans();
	List<AntiBullyingPledgeBean> pledges = AntiBullyingPledgeManager.getTodaysPledges();
	DateFormat fullDF = DateFormat.getDateInstance(DateFormat.FULL);
	Date today = new Date();
%>
<c:set var='pledges' value='<%=pledges%>' />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>NLESD - Bullying Pledge Admin</title>
   			<link href="/MemberServices/css/memberadmin.css" rel="stylesheet" />
    		<style type='text/css'>
    			th { text-align: left; color: #ffffff; font-weight:bold; }

    		</style>
	<script language="Javascript" src="js/jquery-1.9.1.min.js"></script>
	<!-- Add mousewheel plugin (this is optional) -->
	<script type="text/javascript" src="fancybox/jquery.mousewheel-3.0.6.pack.js"></script>
	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="fancybox/jquery.fancybox.js?v=2.1.5"></script>
	<link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox.css?v=2.1.5" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="fancybox/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
	<script type="text/javascript" src="fancybox/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="fancybox/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
	<script type="text/javascript" src="fancybox/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="fancybox/helpers/jquery.fancybox-media.js?v=1.0.6"></script>
	<script type="text/javascript">
			$('document').ready(function(){
				$('tr.datalist:odd').css('background-color', '#E5F2FF');
			});
	</script>
	<script type="text/javascript">
		function OpenPopUp(id)
		{
				$("#deleteid").val(id);
				$('.fancybox').fancybox();
		}
		function closewindowcancel()
		{
			$.fancybox.close(); 
		}
	    function closewindow()
	    {
	    	//blank fields
	    	$("#deleteid").val("");
			var i=1;
	    	//loop through rows
			$('#showpledges tr.datalist').each(function(){
				{
				if(i % 2 === 0 )
				{
					$(this).css('background-color', 'WHITE');
					
				}else{
					$(this).css('background-color', '#E5F2FF');
					
				}
				i=i+1;
				}
			});
			$.fancybox.close(); 
	    	
	    	document.getElementById("step1").style.display = 'block';
	    	document.getElementById("step2").style.display = 'none';
	    }
	   	function deletepledge()
	    {
	    	if(ajaxRequestInfo())
	    		{	
	    			//remove row
	    			var rowid= '#' + $('#deleteid').val();
					$(rowid).remove();
					//show confirmation message
		    	document.getElementById("step1").style.display = 'none';
		    	document.getElementById("step2").style.display = 'block';
	    		}
	    }
	    
	    function ajaxRequestInfo()
	    {
	    	var fieldvalue = $.trim($('#deleteid').val());
	    	var isvalid=false;
	    	$.ajax(
	     			{
	     				type: "POST",  
	     				url: "deleteAntiBullyingPledge.html",
	     				data: {
	     					id: fieldvalue
	     				}, 
	     				success: function(xml){
	     					$(xml).find('INFO').each(function(){
	     							if($(this).find("MESSAGE").text() == "PLEDGEDELETED")
	     								{
											
											isvalid=true;
	    	                   				
	     								}else{
	     									alert($(this).find("MESSAGE").text());
	     									
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
</script>
</head>
	<body style='margin:0px; background-image:url(/MemberServices/MemberAdmin/images/bg.gif);'>
	<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
	  <tr>
	    <td width="100%" valign="top" bgcolor="#333333">
	      <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR />
	    </td>
	  </tr>
			<tr>
				<td width="*" height="400" valign="top">
					<table width="75%" border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr>
							<td>
								<hr noshade color="#333333" size="2" width="100%" align="right">
                  				<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
                    				<tr>
                      					<td align="left"><span class="header1">New Pledge Listing for <%=fullDF.format(today)%></span></td>
                    				</tr>
                    			</table>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
								<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0" style="border:none;" id="showpledges">
									<tr bgcolor="#000066">
										<th>First Name</th>
										<th>Last Name</th>
										<th>Grade</th>
										<th>School</th>
										<th>Email</th>
										<th></th>
									</tr>
									<c:choose>
										<c:when test='${ fn:length(pledges) gt 0 }'>
											<c:forEach items="${pledges}" var="pledge">
												<tr class='datalist' id='${pledge.pk}'>
													<td class='list'>${pledge.firstName}</td>
													<td class='list'>${pledge.lastName}</td>
													<td class='list'>${pledge.gradeLevel}</td>
													<td class='list'>${pledge.schoolName}</td>
													<td class='list'>${pledge.email}</td>
													<td class='list' align='center'><a class="fancybox" href="#inline1" title="Confirm Deletion" onclick="OpenPopUp(${pledge.pk});">Delete</a></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr><td colspan='7'>No pledges submitted today.</td></tr>
										</c:otherwise>
									</c:choose>
								</table>
								<br>&nbsp;<br>
							</td>
						</tr>
					</table>
				</td>
			</tr>
	</table>
	<div id="inline1" style="width:400px;display: none;">
		<div id="step1">
			<table width="100%">
			<tr><td align="center"><h2>Are you sure you want to delete this pledge?</h2></td></tr>
			<tr><td  align="center"><input type="button" value="Yes" onclick="deletepledge();">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="button" value="No" onclick="closewindowcancel();"></td></tr>
			</table>
			<input type="hidden" id="deleteid">
		</div>
			<div id="step2"  style="width:400px;display: none;">
			<table  width="100%">
			<tr><td align="center"><h2>Pledge has been deleted.</h2></td></tr>
			<tr><td align="center"><input type="button" value="Close Window" onclick="closewindow();"></td></tr>
			</table>
		</div>
		</div>
	</body>
</html>