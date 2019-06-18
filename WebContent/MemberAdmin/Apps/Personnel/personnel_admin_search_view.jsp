<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.personnel.*,
                java.text.*"%>
<%!
  User usr = null;
  Personnel[] emps = null;
  Personnel tmp = null;
  int page_num = 0;
  int colorcnt = 0;
  String prev_fn = "", prev_ln = "", prev_usr = "";
  String color0, color1;
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
  colorcnt = 0;
  color1="#E5F2FF";
  color0="#FFFFFF";
  
  
  emps = (Personnel[]) request.getAttribute("SEARCHRESULTS");
%>
<html>
  <head>
    <title>Members Administration - Personnel Summary</title>
    <link href="/MemberServices/css/memberadmin.css" rel="stylesheet">
  </head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0" background="/MemberServices/MemberAdmin/images/bg.gif">
<form action="searchPersonnel.html">
<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td width="100%" valign="top" bgcolor="#333333">
      <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
    </td>
  </tr>
  <tr>
      <td width="100%" valign="top">
          <center>
            <table width="90%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="100%" valign="top">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <span class="header1">Personnel Administration Summary</span><BR>
                  <hr noshade color="#333333" size="2" width="100%" align="right">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <table align="center" width="100%" cellpadding="5" cellspacing="0" border="0">
                    <tr>
                      <td colspan="2">
                        <%for (char i='A'; i <= 'Z'; i++)
                          out.print("<a href='personnel_admin_view.jsp?page=" + (i - 'A') + "'>" + i + "</a>&nbsp;");
                        %>
                      </td>
                      <td colspan="2" align="right">
                      	
	                      	<table cellpadding="0" cellspacing="0">
	                          <tr>
	                            <td class="label" width="30">Find:</td>
	                            <td width="75"><input type="text" id="name" name="name" value="" style="WIDTH:150px;" class="requiredinput"></td>
	                            <td width="30">
	                              <a href="" onclick="document.forms[0].submit(); return false;" class="small_blue">
	                                <img src="../../images/search_off.gif" 
	                                     style="cursor:hand;padding-left:4px;" border="0"
	                                     onmouseover="src='../../images/search_on.gif';"
	                                     onmouseout="src='../../images/search_off.gif';"><br>
	                              </a>
	                            </td>
	                          </tr>
	                        </table>
                        
                      </td>
                    </tr>
                    <tr bgcolor="#000066">
                      <td  width="200" valign="middle" >
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">ID / Name</font></span><BR>
                      </td>
                      <td  width="150" valign="middle" >
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">Email</font></span><BR>
                      </td>
                      <td  width="50" valign="middle" >
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">Username</font></span><BR>
                      </td>
                      <td  width="50" valign="middle" >
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">Password</font></span><BR>
                      </td>
                       <td width="250" valign="middle" >
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">School</font></span><BR>
                      </td>
                      <td  width="250" valign="middle" >
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">Category</font></span><BR>
                      </td>                     
                      <td width="*">
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">Options</font></span><BR>
                      </td>
                    </tr>
                    <% //while(iter.hasNext())
                    	for(int i=0; i < emps.length; i++)
                      {
                        //tmp = (Personnel) iter.next();
                        tmp = emps[i];
                    %>  <tr>
                          <td bgcolor="<%=((colorcnt%2)==0)?color0:color1%>" valign="middle" align="left">
                            <table width="100%" align="center" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="25">
                                  <img src='../../images/blue-info.gif' style="cursor:hand;"
                                       onclick="top.document.location.href='../../../loginAs.html?pid=<%=tmp.getPersonnelID()%>';"><br>
                                </td>
                                <% if(usr.checkRole("ADMINISTRATOR")) { %>
                                <td width="50" style="cursor:hand;" onclick="document.location.href='viewPersonnelRecord.html?pid=<%=tmp.getPersonnelID()%>';">
	                                  <span class="text" <%=prev_usr.equals(tmp.getUserName())?"style='color:#FF0000;font-weight:bold;'":""%>><%=tmp.getPersonnelID()%> <%=tmp.getEmailAddress().endsWith("@awsb.ca")?"&nbsp;<span style='color:#FF0000;font-weight:bold;font-size:9;'>[AWSB]</span>":""%></span>
	                                </td>
	                                <td width="*" style="cursor:hand;" onclick="document.location.href='viewPersonnelRecord.html?pid=<%=tmp.getPersonnelID()%>';">
	                                  <span class="text" <%=prev_usr.equals(tmp.getUserName())?"style='color:#FF0000;font-weight:bold;'":""%>><%=tmp.getFullName()%></span>
	                                </td>
	                               
                                <%}else{%>
                                <td width="50">
		                              	<span class="text"  <%=prev_usr.equals(tmp.getUserName())?"style='color:#FF0000;font-weight:bold;'":""%>><%=tmp.getPersonnelID()%> <%=tmp.getUserName()%>) <%=tmp.getEmailAddress().endsWith("@awsb.ca")?"&nbsp;<span style='color:#FF0000;font-weight:bold;font-size:9;'>[AWSB]</span>":""%></span>
		                              </td>
	                                <td width="*">
		                              	<span class="text"  <%=prev_usr.equals(tmp.getUserName())?"style='color:#FF0000;font-weight:bold;'":""%>><%=tmp.getFullName()%></span>
		                              </td>
		                              
                                <%}%>
                              </tr>
                            </table>
                            
                          </td>
                           <% if(usr.checkRole("ADMINISTRATOR")) { %>
                          <td bgcolor="<%=((colorcnt%2)==0)?color0:color1%>" valign="middle" align="left">
                                      <span class="text"><%=tmp.getEmailAddress()%></span>
	                                </td>
                          <td bgcolor="<%=((colorcnt%2)==0)?color0:color1%>" valign="middle" align="left">
	                                  <span class="text"><%=tmp.getUserName()%></span>
	                                </td>
	                      <td bgcolor="<%=((colorcnt%2)==0)?color0:color1%>" valign="middle" align="left">
	                                  <span class="text"><%=tmp.getPassword()%></span>
	                                </td>
                          <td bgcolor="<%=((colorcnt%2)==0)?color0:color1%>" valign="middle" align="left">
	                                  <span class="text"><%=(tmp.getSchool() != null ? tmp.getSchool().getSchoolName() : "NO SCHOOL")%></span>
	                                </td> 
	                      <%}else{%> 
	                      
	                       <td bgcolor="<%=((colorcnt%2)==0)?color0:color1%>" valign="middle" align="left">
                                                
	                                  <span class="text">N/A</span>
	                                </td>
	                      <td bgcolor="<%=((colorcnt%2)==0)?color0:color1%>" valign="middle" align="left">
	                                  <span class="text">N/A</span>
	                                </td>
                          <td bgcolor="<%=((colorcnt%2)==0)?color0:color1%>" valign="middle" align="left">
	                                  <span class="text">N/A</span>
	                                </td>
                          <td bgcolor="<%=((colorcnt%2)==0)?color0:color1%>" valign="middle" align="left">
	                                  <span class="text">N/A</span>
	                                </td> 
	                               
	                        <%}%>                                 
                          <td bgcolor="<%=((colorcnt%2)==0)?color0:color1%>" valign="middle" align="left">
                              <span class="text"><%=tmp.getPersonnelCategory().getPersonnelCategoryName()%></span>
                          </td>                         
                           
                          
                          <td bgcolor="<%=((colorcnt%2)==0)?color0:color1%>" valign="middle" align="left">
                          	<a href="personnelEffectivePermissions.html?pid=<%=tmp.getPersonnelID()%>">permissions</a>&nbsp;|&nbsp;
                          	<a href="personnelAdminChange.html?pid=<%=tmp.getPersonnelID()%>">profile</a>&nbsp;|&nbsp;
                            <a href="personnelAdminCategoryChange.html?pid=<%=tmp.getPersonnelID()%>">category</a>&nbsp;|&nbsp;
                            <a href="personnelAdminChangeSchool.html?personnelID=<%=tmp.getPersonnelID()%>">school</a>&nbsp;|&nbsp;
                            <a onclick="top.document.location.href='../../../loginAs.html?pid=<%=tmp.getPersonnelID()%>';" href='#'>login as</a>&nbsp;|&nbsp;
                            <a href="personnelDelete.html?pid=<%=tmp.getPersonnelID()%>&page=<%=page_num%>">delete</a>
                          </td>
                        </tr>
                    <%   colorcnt++;
                         //prev_fn = tmp.getFirstName();
                         //prev_ln = tmp.getLastName();
                         
                         prev_usr = tmp.getUserName();
                        }
                    %>
                  </table>
                </td>
              </tr>
              <tr>
                <td align="left" colspan="2">
                  <hr noshade color="#333333" size="1" width="100%" align="right">
                </td>
              </tr>
            </table>
          </center>
      </td>
    </tr>
    </table>
    </form>
  </body>
</html>