<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.personnel.*,
                java.text.*"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<%
	User usr = (User) session.getAttribute("usr");
  Personnel[] emps = null;
  Personnel tmp = null;
  int page_num = 0;
  int colorcnt = 0;
  String prev_fn = "", prev_ln = "", prev_usr = "";
  String color0, color1;

  colorcnt = 0;
  color1="#E5F2FF";
  color0="#FFFFFF";
  
  if(request.getParameter("page") != null)
  {
    page_num = Integer.parseInt(request.getParameter("page"));
  }
  else
  {
    page_num = 0;
  }
    
  if((session.getAttribute("ADMIN-PERSONNEL_VIEW") == null)||(page_num == 0))
  {
    session.setAttribute("ADMIN-PERSONNEL_VIEW", new DistrictPersonnelAlphabetized());
  }
  
  emps = ((DistrictPersonnelAlphabetized)session.getAttribute("ADMIN-PERSONNEL_VIEW")).getPage(page_num);
%>

<html>
  <head>
    <title>Members Administration - Personnel Summary</title>
    <link href="/MemberServices/css/memberadmin.css" rel="stylesheet">
    <script type="text/javascript" src="/MemberServices/js/jquery-1.3.2.min.js"></script>
    <script type="text/javascript">
    	$('document').ready(function(){
				$('tr.personnel-info:even').css('background-color', '#FFFFFF');
				$('tr.personnel-info:odd').css('background-color', '#E5F2FF');
    	});
    </script>
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
                  <hr color="#333333" size="2" width="100%" align="right">
                  <span class="header1">Personnel Administration Summary</span><BR>
                  <hr color="#333333" size="2" width="100%" align="right">
                  <img src="/MemberServices/MemberAdmin/images/spacer.gif" width="1" height="10"><BR>
                  <table align="center" width="100%" cellpadding="5" cellspacing="0" border="0">
                    <tr>
                      <td colspan="2">
                        <%for (char i='A'; i <= 'Z'; i++){
                            if((i-'A') != page_num){%>
                              <a href="personnel_admin_view.jsp?page=<%=i - 'A'%>"><%=i%></a>
                        <%  }else{%>
                            <SPAN style='COLOR:#FF0000;FONT-WEIGHT:BOLD;'><%=i%></SPAN>
                        <%}}%>
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
                      <td  width="25%" valign="middle" >
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">Name</font></span><BR>
                      </td>
                      <td  width="20%" valign="middle" >
                      <span class="title"><font color="#FFFFFF" style="font-weight:bold;">Category</font></span><BR>
                      </td>
                      <td width="*">Options</td>
                    </tr>
                    <% 
                    	for(int i=0; i < emps.length; i++)
                      {
                        tmp = emps[i];
                    %>  <tr class='personnel-info'>
                          <td valign="middle" align="left">
                            <table width="100%" align="center" cellspacing="0" cellpadding="0">
                            
                              <tr>
                                <td width="25">
                                  <img src='../../images/blue-info.gif' style="cursor:hand;"
                                       onclick="top.document.location.href='../../../loginAs.html?pid=<%=tmp.getPersonnelID()%>';"><br>
                                </td>
                                
                                <% if(usr.checkRole("ADMINISTRATOR")) { %>
	                                <td width="*" style="cursor:hand;" onclick="document.location.href='viewPersonnelRecord.html?pid=<%=tmp.getPersonnelID()%>';">

	                                  <span class="text" <%=prev_usr.equals(tmp.getUserName())?"style='color:#FF0000;font-weight:bold;'":""%>><%=tmp.getFullName()%> <br /> (ID: <%=tmp.getPersonnelID()%> - <%=tmp.getUserName()%> <%=tmp.getEmailAddress().endsWith("@awsb.ca")?"&nbsp;<span style='color:#FF0000;font-weight:bold;font-size:9;'>[AWSB]</span>":""%></span>

	                                </td>
                                <%}else{%>
	                                <td width="*">
		                              	<span class="text" <%=prev_usr.equals(tmp.getUserName())?"style='color:#FF0000;font-weight:bold;'":""%>><%=tmp.getFullName()%> <br /> (ID: <%=tmp.getPersonnelID()%> - <%=tmp.getUserName()%>) <%=tmp.getEmailAddress().endsWith("@awsb.ca")?"&nbsp;<span style='color:#FF0000;font-weight:bold;font-size:9;'>[AWSB]</span>":""%></span>
		                              </td>
                                <%}%>
                              </tr>
                            </table>
                          </td>
                          
                          <td valign="middle" align="left">
                              <span class="text"><%=tmp.getPersonnelCategory().getPersonnelCategoryName()%></span>
                          </td>
                          
                          <td valign="middle" align="left">
                          	<a href="personnelEffectivePermissions.html?pid=<%=tmp.getPersonnelID()%>">effective permissions</a>&nbsp;|&nbsp;
                          	<a href="personnelAdminChange.html?pid=<%=tmp.getPersonnelID()%>">change profile</a>&nbsp;|&nbsp;
                            <a href="personnelAdminCategoryChange.html?pid=<%=tmp.getPersonnelID()%>">change category</a>&nbsp;|&nbsp;
                            <a href="personnelAdminChangeSchool.html?personnelID=<%=tmp.getPersonnelID()%>">change school</a>&nbsp;|&nbsp;
                            <a onclick="top.document.location.href='../../../loginAs.html?pid=<%=tmp.getPersonnelID()%>';" href='#'>login as</a>&nbsp;|&nbsp;
                            <a href="personnelDelete.html?pid=<%=tmp.getPersonnelID()%>&page=<%=page_num%>">delete</a>
                          </td>
                        </tr>
                    <%  prev_usr = tmp.getUserName();
                      }
                    %>
                  </table>
                </td>
              </tr>
              <tr>
                <td align="left" colspan="2">
                  <hr color="#333333" size="1" width="100%" align="right">
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