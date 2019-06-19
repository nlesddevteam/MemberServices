<%@ page language ="java" 
         session = "true"
         import = "com.awsd.security.*"
         isThreadSafe="false"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

	<head>
		<title>Fund 3 MANAGEMENT SYSTEM</title>
				<link href="includes/css/fund3.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/bootstrap.min.css" />
		<link href="includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/hover_drop_2.css" />
		 <link rel="stylesheet" href="includes/css/jquery-ui.css" />	
		<script type="text/javascript" src="includes/js/jquery-1.9.1.min.js"></script>
		<script src="includes/js/iefix.js" type="text/javascript"></script>
		<script src="includes/js/jquery.validate.js" type="text/javascript"></script>
		<!-- Add mousewheel plugin (this is optional) -->
		<script type="text/javascript" src="includes/fancybox/jquery.mousewheel-3.0.6.pack.js"></script>
		<!-- Add fancyBox main JS and CSS files -->
		<script type="text/javascript" src="includes/fancybox/jquery.fancybox.js?v=2.1.5"></script>
		<link rel="stylesheet" type="text/css" href="includes/fancybox/jquery.fancybox.css?v=2.1.5" media="screen" />
		<!-- Add Button helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="includes/fancybox/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
		<script type="text/javascript" src="includes/fancybox/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
		<!-- Add Thumbnail helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="includes/fancybox/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
		<script type="text/javascript" src="includes/fancybox/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
		<!-- Add Media helper (this is optional) -->
		<script type="text/javascript" src="includes/fancybox/helpers/jquery.fancybox-media.js?v=1.0.6"></script>
		<script type="text/javascript" src="includes/js/changepopup.js"></script>
		<script type="text/javascript">
		$(document).ready(function()
		{
			$('#dropdown').change(function(){
				ajaxRequestInfo();
			 });
			$('#dropdownitems').click(function(){
				 getDropdownItem();
				 
			 });
		});
		</script>
	</head>

	<body style="margin:20px;">
		    <form id="pol_cat_frm" action="updateContact.html" method="post">
      			<input type="hidden" id="op" name="op" value="CONFIRM">	
		<table width="1000px" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 3px solid Black; background-color: White;">
			<tr>
			<td align="center">
			<jsp:include page="header.jsp" />
			<br />
			<br />
			</td>
			</tr>
			<tr align="center">
				<td>
				<br />
<br />
<br />

				<table width="500" style="background: #f3f6f7;border-radius:10px;-moz-border-radius:10px;-webkit-border-radius:10px;">
				<tr>
				<th colspan='2' align='center' class="fund3addscreentextnb">VIEW DROPDOWNS <input type="hidden" value="${contact.id}" id="id" name="id"><br /></th>
				</tr>
				<tr>
				<td class="messageText" colspan='2' align='center'>
					<%if(request.getAttribute("msg")!=null){%>
					<%=(String)request.getAttribute("msg")%>
                 	<%} %>   
				</td>
				</tr>
				

				<tr>
				<td>Dropdown</td>
				<td>
					<select id="dropdown" name="dropdown"  class="inputtext">
					<option value='0'>---- Please Select ----</option>
            			<c:forEach var="item" items="${dropdowns}" >
            				<option value="${item.id}">${fn:toUpperCase(item.ddName)}</option>
    					</c:forEach>            		
                    </select>
				</td>
				</tr>
				<tr><td colspan='2'></td></tr>
				<tr><td colspan='2'></td></tr>
				<tr>
					<td colspan='2'>
						<table width="100%">
							<tr>

								<td width="75%">
									<select id="dropdownitems" name="dropdownitems" size="10" style="width:100%;" ></select>
								</td>
								<td>
									<table>
										<tr>
											<td>
												<a class="fancybox" href="#inline1" title="" onclick="OpenPopUpAdd();">
												<input type="button" value="Add Item"  class="btnExampleFixed">
												</a>
											</td>
										</tr>
										<tr>
											<td>
												<a class="fancybox" href="#inline1" title="" onclick="OpenPopUpEdit();">
												<input type="button" value="Edit Item"  class="btnExampleFixed">
												</a>
											</td>
										</tr>
										<tr>
											<td>
												<a class="fancybox" href="#inline2" title="" onclick="OpenPopUpDelete();">
												<input type="button" value="Delete Item"  class="btnExampleFixed">
												</a>
											</td>
										</tr>																				
									</table>
									
									
								</td>

						</table>
					</td>
				</tr>
			</table>
			<jsp:include page="fund3links.jsp" />

			<tr bgcolor="#000000">
				<td><div align="center" class="copyright">&copy; 2014-15 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
		</form>
		<div id="inline1" style="width:600px;display: none;">
		
			<span class="headertitle" id="headertitle">Add New Item</span><input type="hidden" id="hidid" name="hidid"><input type="hidden" id="hiditid" name="hiditid">
			<table width="500px;" cellpadding="0" cellspacing="3" align="center" border="0" style="padding: 5px;">
				<tr>
					<td>
						Dropdown Text
					</td>
					<td>
						<input type="text" id="dropdowntext" name="dropdowntext" style="width:350px;">
					</td>					
				</tr>
				<tr>
					<td>
						Dropdown Status
					</td>
					<td>
						<select id="dropdownstatus" name="dropdownstatus">
							<option value="1" selected>ACTIVE</option>
							<option value="0">INACTIVE</option>
						</select>
					</td>					
				</tr>
				<tr>
					<td align="center"><input type="button" value="Add" onclick="return ChooseButtonAction();" id="btnadd" ></td>
					<td align="center"><input type="button" value="Cancel"></td>
				</tr>

			</table>
		</div>
		<div id="inline2" style="width:600px;display: none;">
		
			<span class="headertitle2" id="headertitle2">Delete Item</span>
			<table width="500px;" cellpadding="0" cellspacing="3" align="center" border="0" style="padding: 5px;">
				<tr>
					<td colspan="2" align="center">
						Are You Sure You Would Like To Delete The Following Item?
					</td>
				
				</tr>
				<tr>
					<td colspan="2" align="center">
						<span id="deleteitem"></span>
					</td>
					
				</tr>
				<tr>
					<td align="center"><input type="button" value="Delete" onclick="return deleteDropdownItem();" id="btndelete" ></td>
					<td align="center"><input type="button" value="Cancel" onclick="closewindow();"></td>
				</tr>

			</table>
		</div> 		 
	</body>
</html>