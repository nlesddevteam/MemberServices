<%@ page contentType="text/html; charset=iso-8859-1" language="java"
    session="true"
    import="java.sql.*,
            java.util.*,
            java.text.*,com.awsd.security.*,
            com.awsd.weather.*"%>

<%
  User usr = null;
  ClosureStatuses stats = null;
  ClosureStatus stat = null;
  Iterator iter = null;

  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}

  stats = new ClosureStatuses();
  iter = stats.iterator();
%>

<html>
<head>
<title></title>
<style type="text/css">@import "/MemberServices/css/memberservices-new.css";</style>

<script language="JavaScript">
	function toggle(target)
		{
			obj=(document.all) ? document.all[target] : document.getElementById(target);
			obj.style.display=(obj.style.display=='none') ? 'inline' : 'none';
		}
		
	function toggleHeight(target2)
		{
			obj=(parent.document.all) ? parent.document.all[target2] : parent.document.getElementById(target2);
			obj.style.height=(obj.style.height=='26px') ? '208px' : '26px';
			
		}
</script>
</head>
<body>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" >	
		<tr>
			<td width="100%" height="26" align="left" valign="middle" style="background-image: url('/MemberServices/MemberAdmin/images/container_title_bg.jpg');">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="100%" align="left" valign="middle">
							<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="4" height="1"><span class="containerTitleWhite">Members Admin</span><BR>
						</td>
						<td width="50" align="right" valign="middle">
							<img src="/MemberServices/MemberAdmin/images/minimize_icon.gif" onClick="toggle('bodyContainer');"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><img src="/MemberServices/MemberAdmin/images/close_icon.gif" onClick="document.location='../home.jsp';" alt="Close" style="cursor: hand;"><img src="/MemberServices/MemberAdmin/images/spacer.gif" width="5" height="1"><BR>
						</td>
					</tr>											
				</table>										
			</td>
		</tr>		
		<tr id="bodyContainer">
			<td width="100%" align="left" valign="top">				
				<table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding: 10px;">
					<tr>
						<td width="100%" align="left" valign="top">								
							<table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding: 10px;">
								<tr>
									<td width="100%" align="left" valign="top">
										<span class="boldBlack11pxLower">School Closure Status Admin</span>&nbsp;<a href="javascript:(toggle('addClosureStatus'));" class="11pxBlueLink">[+] Add New</a><BR><BR>
										<form name="addClosureStatusForm" action="weatherStatusCodeAdmin.html" method="get">
											<input type="hidden" name="op" value="add" />
											<table id="addClosureStatus" width="100%" cellpadding="0" cellspacing="0" border="0" style="display: none;">
	                      
												<tr>
													<td width="20%" align="left" valign="top">
														<span class="boldBlack10pxTitle">Description:</span>
													</td>
													<td width="80%" align="left" valign="top">
														<input type="text" name="status_desc" class="selectBox" style="width: 100%;" />
													</td>
												</tr>
												<tr>
													<td width="100%" align="right" valign="top" colspan="2">
														<img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="5"><BR>
														<img src="/MemberServices/MemberAdmin/images/ok_button_01.gif" border="0" 
	                               onMousedown="src='/MemberServices/MemberAdmin/images/ok_button_02.gif';"
	                               onMouseup="src='/MemberServices/MemberAdmin/images/ok_button_01.gif';"
	                               onclick="if(document.addClosureStatusForm.status_desc.value!='')document.addClosureStatusForm.submit(); else alert('Status Description Required.');">
	                          &nbsp;&nbsp;
	                          <img src="/MemberServices/MemberAdmin/images/cancel_button_01.gif" border="0" 
	                               onClick="" 
	                               onMousedown="src='/MemberServices/MemberAdmin/images/cancel_button_02.gif';" 
	                               onMouseup="src='/MemberServices/MemberAdmin/images/cancel_button_01.gif';" >
	                          <BR>													
													</td>												
												</tr>
											</table>	
										</form>									
										<hr noshade="noshade" size="1" width="100%">										
										<table width="100%" cellpadding="0" cellspacing="0" border="0">
											<tr>
												<td width="40" align="left" valign="middle">
													<span class="boldBlack10pxTitle">Code</span>&nbsp;&nbsp;
												</td>
												<td width="100%" align="left" valign="middle">
													<span class="boldBlack10pxTitle">Description</span>
												</td>
												<td width="39" align="left" valign="middle">
													
												</td>
												<td width="36" align="left" valign="middle">
													
												</td>
											</tr>
                      <%while(iter.hasNext())
                        {
                          stat = (ClosureStatus) iter.next();
                      %>  <tr>
                            <td width="40" align="left" valign="middle">
                              <span class="normalBlack10pxText"><%=stat.getClosureStatusID()%></span>
                            </td>
                            <td width="100%" align="left" valign="middle">
                              <span class="normalBlack10pxText"><%=stat.getClosureStatusDescription()%></span>
                            </td>
                            <td width="39" align="left" valign="middle">
                              <a href="weatherStatusCodeAdmin.html?op=del&status_id=<%=stat.getClosureStatusID()%>">
                              <img src="/MemberServices/MemberAdmin/images/delete_icon.gif" border="0" alt="Delete"></a>&nbsp;<BR>
                            </td>
                            <td width="36" align="left" valign="middle">
                              <a href=""><img src="/MemberServices/MemberAdmin/images/modify_icon.gif" border="0" alt="Modify"></a>&nbsp;<BR>
                            </td>
                          </tr>
                      <%}%>
										</table>																													
									</td>
								</tr>
							</table>							
						</td>
					</tr>
				</table>
			</td>
		</tr>					
	</table>
</body>
</html>
