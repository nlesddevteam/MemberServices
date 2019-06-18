<%@ page language="java"
         session="true"
         import="com.awsd.security.*,com.awsd.personnel.*,
                 com.awsd.financial.*,
                 java.util.*,
                 java.io.*"%>

<%!
  User usr = null;
  FinancialAccounts accounts = null;
  FinancialPersonnel personnel = null;
  FinancialReports reports = null;
  Account acc = null;
  Personnel p = null;
  Report rpt = null;
  Report tmp = null;
  Iterator iter = null;
  String choice =  null;
%>
<%
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
  accounts = new FinancialAccounts();
  personnel = new FinancialPersonnel();
  reports = new FinancialReports();

  iter = accounts.iterator();

  if(request.getAttribute("rpt") != null)
  {
    rpt = (Report) request.getAttribute("rpt");
  }
  else
  {
    rpt = null;
  }

  choice = (String) request.getAttribute("choice");
%>
<html>
	<head>
		<title>Eastern School District Financial Package - Member Services</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<style type="text/css">@import "/MemberServices/Financial/css/financial.css";</style>
		<script type="text/javascript">
			function toggle(target)
      		{
       			obj=(document.all) ? document.all[target] : document.getElementById(target);
        		obj.style.display=(obj.style.display=='none') ? 'inline' : 'none';
      		}
		</script>	
	</head>
	<body background="/MemberServices/MemberAdmin/images/bg.gif">
    <form name="finprem" action="financialAdmin.html" method="post">    
      <input type="hidden" name="op" value="UNKNOWN">
      <input type="hidden" name="id" value="-1">
    <% if(rpt != null){%>
      <input type="hidden" name="rpt_id" value="<%=rpt.getReportID()%>">
    <%}%>
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td width="100%" align="left" valign="top">
					<table width="100%" cellpadding="0" cellspacing="0" border="0" style="border-style: solid; border-color: #000000; border-width: 1px;">
						<tr>
							<td width="100%" align="left" valign="top">
								<table width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td width="220" align="left" valign="middle">
											<img src="/MemberServices/Financial/images/logo.gif"><BR>
										</td>
										<td width="330" align="left" valign="middle">
											<span id="title" class="boldTitle">Creating & Modifying Reports</span>
										</td>
										<td width="200" align="right" valign="middle">
											
										</td>
									</tr>
									<tr>
										<td width="100%" align="center" valign="top" colspan="3">
											<table width="60%" cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td width="100%" align="left" valign="middle" style="background-image: url('/MemberServices/Financial/images/title_bg.jpg');">
														<table width="100%" cellpadding="0" cellspacing="0" border="0">
															<tr>																
																<td width="98%" align="left" valign="middle">
																	<table width="100%" cellpadding="0" cellspacing="0" border="0">
																		<tr>
																			<td width="100%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>
																						<td width="8" align="left" valign="top">
																							<img src="/MemberServices/Financial/images/left_title.jpg"><BR>
																						</td>
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTitle">Report Options</span>&nbsp;<span style="color:#FFFFFF;">(Please select one of the following choices)</span><BR>
																						</td>
																						<td width="10" align="left" valign="top">
																							<img src="/MemberServices/Financial/images/right_title.jpg"><BR>
																						</td>
																					</tr>
																				</table>																				
																			</td>																																	
																		</tr>																		
																	</table>
																</td>																															
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="top">
														<img src="/MemberServices/Financial/images/spacer.gif" width="1" height="1"><BR>
													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="top">
														<table width="100%" cellpadding="3" cellspacing="1" border="0">
															<tr height="20" style="background-color: #CCE8C2;">															
																<td width="100%" align="left" valign="middle">																	
																	<input name="choice" type="radio" value='CREATE NEW' onClick="if(!this.readonly){toggle('createNewReport'); toggle('createNewReportPermissions'); this.readonly=true; if(document.all.newReportName)document.all.newReportName.focus(); document.all.choice[1].readonly=false; document.all.openReportName.style.display='none'; document.all.openReport.style.display='none';}">Create New Report<BR>
																</td>																
															</tr>
															<tr height="20" style="background-color: #CCE8C2;">															
																<td width="100%" align="left" valign="middle">
																	<input name="choice" type="radio" value='OPEN EXISTING' onClick="if(!this.readonly){document.all.createNewReport.style.display='none'; document.all.createNewReportPermissions.style.display='none'; this.readonly=true; document.all.choice[0].readonly=false; document.all.openReportName.style.display='inline';}">Open Existing Report<BR>																	
																</td>																
															</tr>
														</table>
													</td>
												</tr>
                        <tr>
                          <td width="100%" align="left" valign="top" colspan="3">
                            <img src="/MemberServices/Financial/images/spacer.gif" width="1" height="15"><BR>
                          </td>
                        </tr>
											</table>
										</td>
									</tr>
									
									<tr id="createNewReport" style="display: none;">
										<td width="100%" align="center" valign="top" colspan="3">
											<table width="60%" cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td width="100%" align="left" valign="middle" style="background-image: url('/MemberServices/Financial/images/title_bg.jpg');">
														<table width="100%" cellpadding="0" cellspacing="0" border="0">
															<tr>																
																<td width="98%" align="left" valign="middle">
																	<table width="100%" cellpadding="0" cellspacing="0" border="0">
																		<tr>
																			<td width="100%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>
																						<td width="8" align="left" valign="top">
																							<img src="/MemberServices/Financial/images/left_title.jpg"><BR>
																						</td>
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTitle">Create New Report</span><BR>
																						</td>
																						<td width="10" align="left" valign="top">
																							<img src="/MemberServices/Financial/images/right_title.jpg"><BR>
																						</td>
																					</tr>
																				</table>																				
																			</td>																																	
																		</tr>																		
																	</table>
																</td>																															
															</tr>
														</table>

													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="top">
														<img src="/MemberServices/Financial/images/spacer.gif" width="1" height="1"><BR>
													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="top">
														<table width="100%" cellpadding="3" cellspacing="1" border="0">
															<tr height="20" style="background-color: #CCE8C2;">															
																<td width="100%" align="left" valign="middle">
                                  <% if(rpt == null){%>
                                    Report Name:&nbsp;<input type="text" size="40" name="newReportName">&nbsp;<input type="button" value="Create" onclick="document.forms[0].submit();">
                                  <%}else{%>
                                    Report Name:&nbsp;<%=rpt.getReportName()%>
                                  <%}%>
																</td>																
															</tr>															
														</table>
													</td>
												</tr>												
											</table>
										</td>
									</tr>
									
<!-- //Permissions Begin// ------------------->
									<tr id="createNewReportPermissions" style="display: none;">
										<td width="100%" align="center" valign="top" colspan="3">
											<table width="50%" cellpadding="0" cellspacing="0" border="0">
                        <tr>
                          <td width="100%" align="left" valign="top" colspan="3">
                            <img src="/MemberServices/Financial/images/spacer.gif" width="1" height="15"><BR>
                          </td>
                        </tr>
												<tr>
													<td width="100%" align="left" valign="middle">
														<table width="100%" cellpadding="0" cellspacing="0" border="0">
															<tr>																
																<td width="98%" align="left" valign="middle">
																	<table width="100%" cellpadding="0" cellspacing="0" border="0">
																		<tr>
																			<td width="100%" align="left" valign="middle">
																				<table width="300" cellpadding="0" cellspacing="0" border="0">
																					<tr>
																						<td width="150" align="left" valign="top">
																							<img name="accountTab" src="/MemberServices/Financial/images/account_tab_01.jpg" style="cursor: none;" onClick="document.userTab.src='/MemberServices/Financial/images/users_tab_02.jpg'; document.userTab.style.cursor='hand'; this.src='/MemberServices/Financial/images/account_tab_01.jpg'; this.style.cursor='none'; document.all.userPermissions.style.display='none'; document.all.accountPermissions.style.display='inline';"><BR>
																						</td>																						
																						<td width="150" align="left" valign="top">
																							<img name="userTab" src="/MemberServices/Financial/images/users_tab_02.jpg" style="cursor: hand;" onClick="document.accountTab.src='/MemberServices/Financial/images/account_tab_02.jpg'; document.accountTab.style.cursor='hand'; this.src='/MemberServices/Financial/images/users_tab_01.jpg'; this.style.cursor='none'; document.all.userPermissions.style.display='inline'; document.all.accountPermissions.style.display='none';"><BR>
																						</td>
																					</tr>
																				</table>																				
																			</td>																																	
																		</tr>																		
																	</table>
																</td>																															
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="top">
														<img src="/MemberServices/Financial/images/spacer.gif" width="1" height="1"><BR>
													</td>
												</tr>
												<tr id="accountPermissions" style="display: inline;">
													<td width="100%" align="left" valign="top">
														<table width="100%" cellpadding="3" cellspacing="1" border="0">
															<tr height="20" style="background-color: #CCE8C2;">															
																<td width="100%" align="left" valign="middle">																	
																	<table width="100%" cellpadding="3" cellspacing="0" border="0">
																		<tr>
																			<td width="45%" align="left" valign="top">
																				&nbsp;Accounts Available<BR>
																				<select name="availableAccounts" size="13" multiple style="width: 300px;font-family:arial;font-size:10px;">
                                          <%while(iter.hasNext())
                                            {
                                              acc = (Account) iter.next();
                                              if((rpt==null) || ((rpt != null) && !rpt.getReportAccounts().containsKey(acc.getFormatedAccountCode())))
                                              {
                                          %>    <option value='<%=acc.getFormatedAccountCode()%>'><%="[" + acc.getFormatedAccountCode()+"] " + acc.getAccountDescription()%></option>
                                          <%  }
                                            }%>
																				</select>
																			</td>
																			<td width="10%" align="center" valign="middle">
																				<input type="button" value=" > " onclick="document.finprem.op.value='ADD REPORT ACCOUNT'; document.finprem.submit();"><BR>
																				<input type="button" value=" < " onclick="document.finprem.op.value='DEL REPORT ACCOUNT'; document.finprem.submit();"><BR>
																			</td>
																			<td width="45%" align="right" valign="top">
																				Accounts Selected&nbsp;<BR>
																				<select name="selectedAccounts" size="13" multiple style="width: 300px;font-family:arial;font-size:10px;">
                                        <%if(rpt != null)
                                          {
                                            iter = rpt.getReportAccounts().entrySet().iterator();
                                            while(iter.hasNext())
                                            {
                                              acc = (Account) (((Map.Entry)iter.next()).getValue());
                                        %>    <option value='<%=acc.getFormatedAccountCode()%>'><%="[" + acc.getFormatedAccountCode()+"] " + acc.getAccountDescription()%></option>
                                        <%  }
                                          }
                                        %>
																				</select>
																			</td>
																		</tr>
																	</table>																	
																</td>																
															</tr>															
														</table>
													</td>
												</tr>
												<tr id="userPermissions" style="display: none;">
													<td width="100%" align="left" valign="top">
														<table width="100%" cellpadding="3" cellspacing="1" border="0">
															<tr height="20" style="background-color: #CCE8C2;">															
																<td width="100%" align="left" valign="middle">																	
																	<table width="100%" cellpadding="3" cellspacing="0" border="0">
																		<tr>
																			<td width="45%" align="left" valign="top">
																				&nbsp;Users Available<BR>
																				<select name="availableUsers" size="13" multiple style="width: 300px;font-family:arial;font-size:10px;">
                                          <%iter = personnel.iterator();
                                            while(iter.hasNext())
                                            {
                                              p = (Personnel) iter.next();

                                              if(((rpt == null) || ((rpt != null) && !rpt.getReportPersonnel().containsKey(new Integer(p.getPersonnelID())))))
                                              {
                                          %>	  <option value="<%=p.getPersonnelID()%>"><%=p.getFullName()%></option>
                                          <%  }
                                            }%>
																				</select>
																			</td>
																			<td width="10%" align="center" valign="middle">
																				<input type="button" value=" > " onclick="document.finprem.op.value='ADD REPORT USER'; document.finprem.submit();"><BR>
																				<input type="button" value=" < " onclick="document.finprem.op.value='DEL REPORT USER'; document.finprem.submit();"><BR>
																			</td>
																			<td width="45%" align="right" valign="top">
																				Users Selected&nbsp;<BR>
																				<select name="selectedUsers" size="13" multiple style="width: 300px;font-family:arial;font-size:10px;">
                                          <%if(rpt != null)
                                          {
                                            iter = rpt.getReportPersonnel().entrySet().iterator();
                                            while(iter.hasNext())
                                            {
                                              p = (Personnel) (((Map.Entry)iter.next()).getValue());
                                        %>    <option value='<%=p.getPersonnelID()%>'><%=p.getFullName()%></option>
                                        <%  }
                                          }
                                        %>
																				</select>
																			</td>
																		</tr>
																	</table>
																</td>																
															</tr>															
														</table>
													</td>
												</tr>												
											</table>
										</td>
									</tr>
<!-- //Permissions End// ---------------->
<!--------------------------------------->
<!-- Open Existing Report Begins // ----->
									<tr id="openReportName" style="display: none;">
										<td width="100%" align="center" valign="top" colspan="3">
											<table width="60%" cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td width="100%" align="left" valign="middle" style="background-image: url('/MemberServices/Financial/images/title_bg.jpg');">
														<table width="100%" cellpadding="0" cellspacing="0" border="0">
															<tr>																
																<td width="98%" align="left" valign="middle">
																	<table width="100%" cellpadding="0" cellspacing="0" border="0">
																		<tr>
																			<td width="100%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>
																						<td width="8" align="left" valign="top">
																							<img src="/MemberServices/Financial/images/left_title.jpg"><BR>
																						</td>
																						<td width="100%" align="left" valign="middle">
																							<span class="whiteTitle">Open Existing Report</span><BR>
																						</td>
																						<td width="10" align="left" valign="top">
																							<img src="/MemberServices/Financial/images/right_title.jpg"><BR>
																						</td>
																					</tr>
																				</table>																				
																			</td>																																	
																		</tr>																		
																	</table>
																</td>																															
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="top">
														<img src="/MemberServices/Financial/images/spacer.gif" width="1" height="1"><BR>
													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="top">
														<table width="100%" cellpadding="3" cellspacing="1" border="0">
															<tr height="20" style="background-color: #CCE8C2;">															
																<td width="100%" align="left" valign="middle">																	
																	Report Name:&nbsp;
																	<select name="openExistingReport" size="1" style="width: 300px;" onChange="document.forms[0].op.value='OPEN EXISTING'; document.forms[0].submit();">
                                    <option value="-1">SELECT REPORT TO OPEN</option>
                                    <%iter = reports.iterator();
                                      while(iter.hasNext())
                                      {
                                        tmp = (Report) iter.next();
                                    %><option value="<%=tmp.getReportID()%>" <%=((choice!=null)&&(choice.equals("OPEN EXISTING:"))&&(tmp.getReportID()==rpt.getReportID()))?"SELECTED":""%>><%=tmp.getReportName()%></option>
                                    <%}%>
																	</select>																						
																</td>																
															</tr>															
														</table>
													</td>
												</tr>												
											</table>
										</td>
									</tr>
									<tr>
										<td width="100%" align="left" valign="top" colspan="3">
											<img src="/MemberServices/Financial/images/spacer.gif" width="1" height="15"><BR>
										</td>
									</tr>
									<tr id="openReport" style="display: none;">
										<td width="100%" align="center" valign="top" colspan="3">
											<table width="60%" cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td width="100%" align="left" valign="middle">
														<table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-image: url('/MemberServices/Financial/images/title_bg.jpg');">
															<tr>																
																<td width="98%" align="left" valign="middle">
																	<table width="100%" cellpadding="0" cellspacing="0" border="0">
																		<tr>
																			<td width="100%" align="left" valign="middle">
																				<table width="100%" cellpadding="0" cellspacing="0" border="0">
																					<tr>
																						<td width="8" align="left" valign="top">
																							<img src="/MemberServices/Financial/images/left_title.jpg"><BR>
																						</td>
																						<td width="100%" align="left" valign="middle">
                                              <%if(rpt != null){%>
                                                <table width="100%">
                                                  <tr width="100%">
                                                    <td width="50%" align="left">
                                                      <span class="whiteTitle"><%= rpt.getReportName() %></span>      
                                                    </td>
                                                    <td width="50%" align="right">
                                                      <a href="javascript:document.forms[0].op.value='DEL REPORT'; document.forms[0].submit();"><font style='font-size:9px;'>[delete]</font></a>
                                                    </td>
                                                  </tr>
                                                </table>
                                              <%}%>
																						</td>
																						<td width="10" align="left" valign="top">
																							<img src="/MemberServices/Financial/images/right_title.jpg"><BR>
																						</td>
																					</tr>
																				</table>
																			</td>																																	
																		</tr>																		
																	</table>
																</td>																															
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="top">
														<img src="/MemberServices/Financial/images/spacer.gif" width="1" height="1"><BR>
													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="top">
														<table width="100%" cellpadding="3" cellspacing="1" border="0">
															<tr height="20" style="background-color: #CCE8C2;">															
																<td width="100%" align="left" valign="middle">																	
																	<table width="100%" cellpadding="3" cellspacing="0" border="0">
																		<tr>
																			<td width="100%" align="left" valign="middle" colspan="2">
                                        <table width="100%">
                                          <tr width="100%">
                                            <td width="50%" align="left">
                                              <span style="font-weight: bold; font-size: 12px;">Accounts:</span>
                                            </td>
                                            <td width="50%" align="right">
                                              <a href="javascript: openWindow();"><font style='font-size:9px;'>[Add New Account(s)]</font></a>
                                            </td>
                                          </tr>
                                        </table>
																			</td>
																		</tr>
                                    <%if((rpt!=null)&&(choice!=null)&&choice.equalsIgnoreCase("OPEN EXISTING:"))
                                      {
                                        iter = rpt.getReportAccounts().entrySet().iterator();
                                        while(iter.hasNext())
                                        {
                                          acc = (Account)((Map.Entry) iter.next()).getValue();
                                    %>    <tr>
                                            <td width="90%" align="left" valign="top">
                                              <%="["+acc.getFormatedAccountCode()+"] " + acc.getAccountDescription()%><BR>
                                            </td>																			
                                            <td width="10%" align="left" valign="top">
                                              <a href="javascript:document.forms[0].op.value='DEL REPORT ACCOUNT'; document.forms[0].id.value='<%=acc.getFormatedAccountCode()%>'; document.forms[0].submit();">delete</a>
                                            </td>
                                          </tr>
                                    <%  }
                                      }%>
																	</table>																	
																</td>																
															</tr>															
														</table>
													</td>
												</tr>
												<tr>
													<td width="100%" align="left" valign="top">
														<table width="100%" cellpadding="3" cellspacing="1" border="0">
															<tr height="20" style="background-color: #CCE8C2;">															
																<td width="100%" align="left" valign="middle">																	
																	<table width="100%" cellpadding="3" cellspacing="0" border="0">
																		<tr>
																			<td width="100%" align="left" valign="middle" colspan="2">
                                        <table cellpadding="0" cellspacing="0" border="0"width="100%">
                                          <tr width="100%">
                                            <td width="50%" align="left">
                                              <span style="font-weight: bold; font-size: 12px;">Users:</span>
                                            </td>
                                            <td width="50%" align="right">
                                              <a href="javascript: openWindow();"><font style='font-size:9px;'>[Add New Users]</font></a>
                                            </td>
                                          </tr>
                                        </table>
																			</td>
																		</tr>
                                    <%if((rpt!=null)&&(choice!=null)&&choice.equalsIgnoreCase("OPEN EXISTING:"))
                                      {
                                        iter = rpt.getReportPersonnel().entrySet().iterator();
                                        while(iter.hasNext())
                                        {
                                          p = (Personnel)((Map.Entry) iter.next()).getValue();
                                    %>    <tr>
                                            <td width="90%" align="left" valign="top">
                                              <%=p.getFullName()%><BR>
                                            </td>																			
                                            <td width="10%" align="left" valign="top">
                                              <a href="javascript:document.forms[0].op.value='DEL REPORT USER'; document.forms[0].id.value='<%=p.getPersonnelID()%>';document.forms[0].submit();">Delete</a>
                                            </td>
                                          </tr>
                                    <%  }
                                      }%>
																	</table>
																</td>																
															</tr>															
														</table>
													</td>
												</tr>												
											</table>
										</td>
									</tr>
<!-- Open Existing Report Ends // ----->
									<tr>
										<td width="100%" align="left" valign="top" colspan="3">
											<img src="/MemberServices/Financial/images/spacer.gif" width="1" height="10"><BR>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>						
				</td>
				<td width="4" align="left" valign="top" style="background-image: url('/MemberServices/Financial/images/right_bg.jpg');">
					<img src="/MemberServices/Financial/images/spacer.gif" width="4" height="1"><BR>
				</td>
			</tr>
			<tr>
				<td width="749" align="left" valign="top" style="background-image: url('/MemberServices/Financial/images/botton_bg.jpg');">
				
				</td>
				<td width="5" align="left" valign="top">
					<img src="/MemberServices/Financial/images/bottom_right_bg.jpg"><BR>
				</td>
			</tr>
		</table>
    </form>
    <% if(choice != null) {%>
      <script type="text/Javascript">
        <% if(choice.equalsIgnoreCase("CREATE NEW:ACCOUNT")) {%>
          document.finprem.choice[0].click();
        <% } else if(choice.equalsIgnoreCase("CREATE NEW:USER")) {%>
          document.finprem.choice[0].click();
          document.all.userTab.click();
        <%} else if(choice.equalsIgnoreCase("OPEN EXISTING:")) {%>
          document.finprem.choice[1].click();
          <%if(rpt != null){%>
            document.all.openReport.style.display='inline';
        <%  }
          }%>
      </script>
    <%}%>
	</body>
</html>


