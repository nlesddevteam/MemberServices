<%@ page language ="java" 
         session = "true"
         import = "java.util.*,java.text.DateFormat,com.nlesd.antibullyingpledge.bean.*,com.nlesd.antibullyingpledge.dao.*,com.esdnl.util.*,com.awsd.security.*"
         isThreadSafe="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />
<%
		AntiBullyingPledgeBean bpb = new AntiBullyingPledgeBean();
		List<AntiBullyingPledgeSchoolListBean> map = bpb.getSchoolListings();
%>
<c:set var='searchvalues' value='<%=AntiBullyingPledgeManager.SearchBy.values()%>' />
<c:set var='schools' value='<%=map%>' />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>NLESD - Bullying Pledge Admin</title>
   			<link href="/MemberServices/css/memberadmin.css" rel="stylesheet" />
    		<style type='text/css'>
    			th { text-align: left; color: #ffffff; font-weight:bold; }
    			.myTable td{ width:25% !important; }
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
	<style type="text/css">@import 'includes/jquery-ui-timepicker-addon.css';</style>
	<script src="js/jquery-ui.js"></script>
	<script src="js/jquery-ui-timepicker-addon.js"></script>
    <script>
	$(function() {

	  $('#searchdate').datepicker({
		  dateFormat: "dd/mm/yy"
		});
	});
	</script>
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
				if(i % 2 == 0 )
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
	    function cleartable()
	    {
	    	$('#showpledges td').parent().remove();
	    }
	    function deletepledge()
	    {
	    	if(ajaxRequestDelete())
	    		{	
	    			//remove row
	    			var rowid= '#' + $('#deleteid').val();
					$(rowid).remove();
					//show confirmation message
		    	document.getElementById("step1").style.display = 'none';
		    	document.getElementById("step2").style.display = 'block';
	    		}
	    }
	    function searchpledges()
	    {
	    	//clear table
	    	cleartable();
	    	//get new data
	    	ajaxRequestInfo();
	    }
	    function checksearchfor()
	    {
	    	var searchby = $.trim($('#selectsearchby').val());
	    	if(searchby == "-1")
	    	{
	    		alert("Please select a value for Search By");
	    		return false;
	    	}else{
	    		//if it is confirm code, email, first name or last name then we check the searchtext
	    		if(searchby == "1" || searchby == "3" || searchby == "4" || searchby == "6")
	    		{
	    			if($.trim($('#searchtext').val())== "")
	    			{
	    	    		alert("Please enter a value to Search For");
	    	    		return false;
	    			}
	    		}else if( searchby =="2")
	    		{
	    			if($.trim($('#searchdate').val())== "")
	    			{
	    	    		alert("Please select a date to  Search For");
	    	    		return false;
	    			}
	    		}else if( searchby =="5")
	    		{
	    			if($.trim($('#selectgrade').val())== "-1")
	    			{
	    	    		alert("Please select a grade to  Search For");
	    	    		return false;
	    			}
	    		}else if( searchby =="7")
	    		{
	    			if($.trim($('#selectyes').val())== "-1")
	    			{
	    	    		alert("Please select confirmed or not confirmed to Search For");
	    	    		return false;
	    			}
	    		}else{
	    			if($.trim($('#selectschool').val())== "-1")
	    			{
	    	    		alert("Please select school to Search For");
	    	    		return false;
	    			}
	    		}
	    	}
	    	return true;
	    }
	    function ajaxRequestInfo()
	    {
	    	var searchby = $.trim($('#selectsearchby').val());
	    	var searchtext = $.trim($('#searchtext').val());
	    	var selectschool = $.trim($('#selectschool').val());
	    	var selectyes = $.trim($('#selectyes').val());
	    	var selectgrade = $.trim($('#selectgrade').val());
	    	var selectdate = $.trim($('#searchdate').val());
	    	if(!checksearchfor())
	    	{
	    		return false;	
	    	}
	    	var isvalid=false;
	    	$.ajax(
	     			{
	     				type: "POST",  
	     				url: "searchPledges.html",
	     				data: {
	     					searchby: searchby,searchtext: searchtext,selectschool: selectschool, selectgrade: selectgrade,selectyes: selectyes,selectdate: selectdate
	     				}, 
	     				success: function(xml){
	     					var i=1;
	     					$(xml).find('BULLYING-PLEDGE').each(function(){
	     							
	     							if($(this).find("MESSAGE").text() == "PLEDGEFOUND")
	     								{
											var newrow="";
											if(i % 2 == 0)
											{
											newrow ="<tr style='background-color:#E5F2FF;' id='" + $(this).find("PK").text() + "'>";
	     									}else{
	     										newrow ="<tr style='background-color:#white;' id='" + $(this).find("PK").text() + "'>";
	     									}
	     									//alert("found");
											//now we add each one to the table
											newrow += "<td>" + $(this).find("FIRSTNAME").text() + "</td>";
											newrow += "<td>" + $(this).find("LASTNAME").text() + "</td>";
											newrow += "<td>" + $(this).find("GRADELEVEL").text() + "</td>";
											newrow += "<td>" + $(this).find("SCHOOLNAME").text() + "</td>";
											newrow += "<td>" + $(this).find("EMAIL").text() + "</td>";
											newrow += "<td>" + $(this).find("DATESUBMITTED").text() + "</td>";
											//newrow += "<td>" + $(this).find("PK").text() + "</td>";
											newrow += "<td><a class='fancybox' href='#inline1' title='Confirm Deletion' onclick='OpenPopUp(" + $(this).find("PK").text() + ");'>Delete</a></td>";
											//<td class='list' align='center'><a class="fancybox" href="#inline1" title="Confirm Deletion" onclick="OpenPopUp(${pledge.pk});">Delete</a></td>
											newrow +="</tr>";
											$('table#showpledges tr:last').after(newrow);
											i=i+1;
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
	    $(function(){
				$("#selectsearchby").change(function(){
	        		var selectedvalue = this.value;
					//now we will show the right entry box
					if(selectedvalue == 1 || selectedvalue == 3 || selectedvalue == 4 || selectedvalue == 6 )
					{
						$('#showgrade').hide();
						$('#showyes').hide();
						$('#showschool').hide();
						$('#showdate').hide();
						$('#showtext').show();
						
					}else if(selectedvalue == 2)
					{
						$('#showgrade').hide();
						$('#showyes').hide();
						$('#showschool').hide();
						$('#showdate').show();
						$('#showtext').hide();
					}else if(selectedvalue == 5)
					{
						$('#showgrade').show();
						$('#showyes').hide();
						$('#showschool').hide();
						$('#showdate').hide();
						$('#showtext').hide();
					}else if(selectedvalue == 7)
					{
						$('#showgrade').hide();
						$('#showyes').show();
						$('#showschool').hide();
						$('#showdate').hide();
						$('#showtext').hide();
					}else if(selectedvalue == 8)
					{
						$('#showgrade').hide();
						$('#showyes').hide();
						$('#showschool').show();
						$('#showdate').hide();
						$('#showtext').hide();
					}
				});
			});
	   	function ajaxRequestDelete()
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
			<form id="frmSearch  action="" method="post">
		<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
	  		<tr>
	    		<td width="100%" valign="top" bgcolor="#333333">
	      			<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR />
	    		</td>
	  		</tr>
			<tr>
			<td width="*" height="400" valign="top">
					<table width="100%" border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr>
							<td>
								<hr noshade color="#333333" size="2" width="100%" align="right">
                  				<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
                    				<tr>
                      					<td align="left"><span class="header1">Search Pledges</span></td>
                    				</tr>
                    			</table>
                  				<hr noshade color="#333333" size="2" width="100%" align="right">
							
								<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0" class="myTable">
									<tr><td class="headertitle" colspan="3"></td></tr>
									<tr>
										<td>Search Pledges By</td>
										<td>
										<select id="selectsearchby" name="selectsearchby">
											<c:forEach items="${searchvalues}" var="search">
                                            	<option value='${search.id}'>${search.description}</option>
                                            </c:forEach>
										</select>
										</td>
										<td>
										<div id="showtext" style="display:none;">
											<input type="text" id="searchtext" name="searchtext">
										</div>
										<div id="showgrade"  style="display:none;">
											<select id="selectgrade">
											<option value='-1' selected='true'>Please select</option>
											<option value='0'>K</option>
											<option value='1'>1</option>
											<option value='2'>2</option>
											<option value='3'>3</option>
											<option value='4'>4</option>
											<option value='5'>5</option>
											<option value='6'>6</option>
											<option value='7'>7</option>
											<option value='8'>8</option>
											<option value='9'>9</option>
											<option value='10'>Level I</option>
											<option value='11'>Level 2</option>
											<option value='12'>Level 3</option>
											</select>
										</div>
										<div id="showyes"  style="display:none;">
											<select id="selectyes">
											<option value='-1' selected='true'>Please select</option>
											<option value='Yes'>Yes</option>
											<option value='No'>No</option>

											</select>
										</div>
										<div id="showdate"  style="display:none;">
											<input type="text" id="searchdate">
										</div>
										<div id="showschool"  style="display:none;">
											<select id="selectschool">
											<option value='-1' selected='true'>Please select</option>
													<c:forEach items="${schools}" var="school">
                                            			<option value='${school.schoolId}'>${school.schoolName}</option>
                                            		</c:forEach>
											</select>
										</div>
										</td>
										<td><input type="button" id="searchbutton" onclick="searchpledges();" value="Search Pledges"></td>
									</tr>
									</table>
								<table align="center" width="100%" cellspacing="1" style="font-size: 11px;" cellpadding="1" border="0" id="showpledges">
									<tr bgcolor="#000066">
										<th>First Name</th>
										<th>Last Name</th>
										<th>Grade</th>
										<th>School</th>
										<th>Email</th>
										<th>Date Submitted</th>
										<th></th>
									</tr>
									<tr><td colspan="7"></td></tr>
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
		</form>
	</body>
</html>