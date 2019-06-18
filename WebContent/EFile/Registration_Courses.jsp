<%@ page language="java"
        session="true"
         import="com.awsd.security.*,
                 com.awsd.efile.*,
                 com.awsd.school.*,
                 java.util.*"%>

<%!
  User usr = null;
  Subjects subjects = null;
  Subject s = null;
  Courses courses = null;
  Course c = null;
  Grade lvls[] = null;
  Iterator iter = null;
  Iterator crs_iter = null;
  int cnt =0, numsubs=0, i=0;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("EFILE-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.htmlp">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}
  lvls = (Grade[])request.getAttribute("Grades");

  subjects = new Subjects();
  numsubs = subjects.size();
%>

<html>
<head>
<title>E-File Repository - Member Services/Eastern School District</title>

<link rel="stylesheet" href="css/e-file.css">

<script language="JavaScript">
  <!-- hide
  function openWindow(url,id,w,h) {
    window.open(url,id,"toolbar=0,location=no,top=50,left=50,directories=0,status=0,menbar=0,scrollbars=0,resizable=0,width="+w+",height="+h);

         if (navigator.appName == 'Netscape') {
                 popUpWin.focus();
         }
  }

  function resetMsg()
  {
    var cell  = document.getElementById("msg");

    cell.innerHTML = "<img src='images/spacer.gif' width='1' height='5'><BR>";
  }
  // stop hiding -->
  </script>
</head>

<body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
  <script language="Javascript">
    history.go(1);
  </script>
  <form action="EFileCourseRegister.html" method="POST">
    <table width="100%" background="images/top_bg.gif" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="300" valign="middle" align="left">
          <img src="images/spacer.gif" width="300" height="1"><BR>
          <img src="images/spacer.gif" width="20" height="1"><span class="header">Logged in as "<%=usr.getPersonnel().getFullNameReverse()%>"</span><BR>
        </td>
        <td width="100%">

        </td>
        <td width="200" valign="top" align="right">
          <img src="images/top_logo.gif"><BR>
        </td>
      </tr>
    </table>

    <table width="100%" cellpadding="10" cellspacing="0" border="0">
      <tr>
        <td width="100%" valign="top">
          <center>
            <table width="70%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td width="100%" valign="top" align="left">
                  <BR>
                  <img src="images/registration_title.gif"><BR>
                </td>
              </tr>
              <tr>
                <td width="100%" valign="top" align="left">
                  Please select all courses you are currently teaching.<BR><BR>
                  <table width="100%" cellpadding="10" cellspacing="0" style="border-style: solid; border-width: 2px; border-color:#FEF153;">
                    <tr>
                      <td width="100%" valign="top" align="left" colspan="2">
                        <span class="header2">Course Information</span><BR>
                      </td>
                    </tr>                    
                    
                    
                    <tr>
                      <td width="50%" valign="top" align="left">
                        <table width="100%" align="center">
                          <%i=0;
                            iter = subjects.iterator();                        
                            while(iter.hasNext() && i < (numsubs/2))
                            {
                              s = (Subject) iter.next();
                              courses = new Courses(new Subject[]{s}, lvls);
                              crs_iter = courses.iterator();
                              
                              if(!crs_iter.hasNext())
                              {
                                numsubs--;
                                continue;
                              }
                          %>
                              <tr>
                                <td bgcolor="#F5F5EF">
                                  <FONT style="font-weight:bold;"><%=s.getSubjectName()%></FONT>
                                </td>
                              </tr>
                          <%
                              while(crs_iter.hasNext())
                              {
                                c = (Course) crs_iter.next();
                          %>
                                <tr>
                                  <td>
                                    <input type="checkbox" name="<%="C" + c.getCourseID()%>"><%=c.getCourseName()%>
                                  </td>
                                </tr>
                          <%  }
                              i++;
                            }
                          %>
                        </table>
                      </td>
                      <td width="50%" valign="top" align="left">
                        <table width="100%" align="center">
                          <%
                            while(iter.hasNext())
                            {
                              s = (Subject) iter.next();
                              courses = new Courses(new Subject[]{s}, lvls);
                              crs_iter = courses.iterator();
                              
                              if(!crs_iter.hasNext())
                              {
                                continue;
                              }
                          %>
                              <tr>
                                <td bgcolor="#F5F5EF">
                                  <FONT style="font-weight:bold;"><%=s.getSubjectName()%></FONT>
                                </td>
                              </tr>
                          <%
                              while(crs_iter.hasNext())
                              {
                                c = (Course) crs_iter.next();
                          %>
                                <tr>
                                  <td>
                                    <input type="checkbox" name="<%="C" + c.getCourseID()%>"><%=c.getCourseName()%>
                                  </td>
                                </tr>
                          <%  }
                            }
                          %>
                        </table>
                      </td>
                    </tr> 
                    
                    <tr>
                      <td width="100%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <img src="images/spacer.gif" width="1" height="5"><BR>
                      </td>
                    </tr>
                    
                    <tr>
                      <td width="100%" valign="top" align="right" bgcolor="#F5F5EF" colspan="2">
                        <input type="submit" value="Submit Course(s)" onclick="resetMsg(); return true;" style="font-family: Arial, Helvetica, sans-serif; font-size: 11px;">&nbsp;<input type="reset" name="reset" value="Reset" style="font-family: Arial, Helvetica, sans-serif; font-size: 11px;"><BR>
                      </td>
                    </tr>
                    <tr>
                      <td id="msg" width="100%" valign="top" align="left" bgcolor="#FFFFFF" colspan="2">
                        <% if(request.getAttribute("msg")!= null) { %>
                          <font color="#FF0000" style="font-weight:bold;"><%=request.getAttribute("msg")%></font>
                        <% } else { %>
                          <img src="images/spacer.gif" width="1" height="5"><BR>
                        <% } %>
                      </td>
                    </tr>
                  </table>

                  <img src="images/spacer.gif" width="1" height="50"><BR>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>

      <table width="100%" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td width="100%" height="28" align="left" valign="bottom" background="images/footer_bg.gif">
            <img src="images/footer.gif"><BR>
          </td>
        </tr>
      </table>
    
  </form>
</body>
</html>