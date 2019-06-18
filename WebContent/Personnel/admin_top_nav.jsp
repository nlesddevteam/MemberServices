<%@ page language="java"  import="java.util.*, java.text.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%
	boolean showHead = request.getParameter("showHead") != null ? Boolean.parseBoolean(request.getParameter("showHead")) : true;
	boolean showSearch = request.getParameter("showSearch") != null ? Boolean.parseBoolean(request.getParameter("showSearch")) : true;
%>
<script type="text/javascript">
	$('document').ready(function(){
		$('#btn_search').click(function(){
			document.location.href = 'searchJob.html?term=' + $('#txt_filter').val() +'&type=' + $('input:radio[name=search_type]:checked').val();
			return false;
		});
		
		$('#txt_filter').keypress(function(e){
			if(e.which == 13)
				$('#btn_search').click();
		});
		
		$('#txt_filter').focus();
	});
</script>
<table width="760" cellpadding="0" cellspacing="0" border="0" class='noprint'>
	<tr height="79">
		<td width="760" align="left" valign="top" style="background-color: #FFB700;">
			<table width="760" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td width="154" align="left" valign="top"><img src="<c:url value='/Personnel/images/admin_top_logo.gif' />" /><BR /></td>
					<td width='606'>
						<table width="100%" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="<%= (showSearch ? "396" : "100%") %>" align="center" valign="bottom">
									<table cellspacing="0" cellpadding="0" align='center'>
										<tr>
											<td style="font-family: arial;color:#FFFFFF;" align='center'><h1>Newfoundland &amp; Labrador<%= showSearch ? "<br />" : "&nbsp;" %>English School District</h1></td>
										</tr>
										<tr>
											<td align='center'>
												<span class="displayWhiteDate">
													<%=(new SimpleDateFormat("EEEE, MMMM dd, yyyy")).format(Calendar.getInstance().getTime())%>
												</span>
											</td>
										</tr>
									</table>
								</td>
								<% if(showSearch) { %>
								<!-- **BEGINS** Help & Support / Search of Top Logo -->
								<td width="210" align="right" valign="middle">
									
									<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
										<table width="100%" cellpadding="0" cellspacing="0" border="0">
											<tr>
												<td width="100%" align="left" valign="middle" colspan="2"><input
													id="txt_filter" type="text" class="inputSearchBox" style='width:95%;'></td>
											</tr>
											<tr>
												<td width="100" align="left" valign="middle"><input
													type="radio" id='search_type_1' name='search_type' value='1' checked> <span
													class="displaySearchBy">By Competition #</span><BR />
												</td>
												<td width="*" align="left" valign="middle" rowspan="3">
													<a href="#"><img id='btn_search' src="<c:url value='/Personnel/images/search_go.gif' />" border="0" /></a>
												</td>
											</tr>
											<tr>
												<td width="100" align="left" valign="middle"><input
													type="radio" id='search_type_2' name='search_type' value='2'> <span
													class="displaySearchBy">By Location</span><BR />
												</td>
											</tr>
											<tr>
												<td width="100" align="left" valign="middle"><input
													type="radio" id='search_type_3' name='search_type' value='3'> <span
													class="displaySearchBy">By Applicant</span><BR />
												</td>
											</tr>
										</table>
									</esd:SecurityAccessRequired>
								</td>
								<!-- **ENDS** Help & Support / Search of Top Logo -->
								<% } %>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<!-- **BEGINS** Top Nav Menu / Buttons for Support / Search -->
		<td width="760" align="left" valign="top">
		<table width="760" height="44" cellpadding="0" cellspacing="0"
			border="0"
			style="width: 760px; height: 44px; background: #DEDFDE url('<c:url value="/Personnel/images/admin_spacer.gif" />'); background-position: bottom; background-repeat: repeat-x;">
			<tr>
				<td width="10" align="left" valign="bottom"><img
					src="<c:url value='/Personnel/images/spacer.gif' />" width="10" height="1" /><BR />
				</td>
				<!--
                  <td width="130" align="left" valign="bottom">
                    <img src="images/home_deselected.gif"><BR />
                  </td>
                  <td width="4" align="left" valign="bottom">
                    <img src="images/spacer.gif" width="4" height="1"><BR />
                  </td>
                  <td width="106" align="left" valign="bottom">
                    <img src="images/teachers_deselected.gif"><BR />
                  </td>
                  <td width="4" align="left" valign="bottom">
                    <img src="images/spacer.gif" width="4" height="1"><BR />
                  </td>
                  <td width="106" align="left" valign="bottom">
                    <img src="images/staff_deselected.gif"><BR />
                  </td>
                  <td width="4" align="left" valign="bottom">
                    <img src="images/spacer.gif" width="4" height="1"><BR />
                  </td>
                  <td width="106" align="left" valign="bottom">
                    <img src="images/subs_deselected.gif"><BR />
                  </td>
                  <td width="4" align="left" valign="bottom">
                    <img src="images/spacer.gif" width="4" height="1"><BR />
                  </td>
                  -->
				<td width="106" align="left" valign="bottom"><img src="<c:url value='/Personnel/images/admin_selected.gif' />" /><BR />
				</td>
				<td width="<%= (showHead ? "474" : "654" ) %>" align="left" valign="bottom"><img src="<c:url value='/Personnel/images/spacer.gif' />" width="4" height="1" /><BR />
				</td>
				<% if(showHead){ %>
					<td width="180" height="44" align="left" valign="bottom">
						<table width="180" height="44" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="180" align="right" valign="top">
									<img src="<c:url value='/Personnel/images/home_support_deselected.gif' />" /><img src="<c:url value='/Personnel/images/home_search_selected.gif' />" /><BR />
								</td>
							</tr>
							<tr>
								<td width="180" align="right" valign="bottom">
									<img src='<c:url value="images/admin_topnav_head.jpg" />' /><BR />
								</td>
							</tr>
						</table>
					</td>
				<% } %>
			</tr>
		</table>
		</td>
		<!-- **ENDS** Top Nav Menu / Buttons for Support / Search -->
	</tr>
</table>