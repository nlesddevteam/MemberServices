<%@ page language="java"
         import="java.util.*,
                  java.text.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%
  String op = (String)request.getAttribute("op");
  String comp_num = (String)request.getAttribute("COMP_NUM");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">@import 'includes/home.css';</style>
  <script type="text/javascript">
    function confirmed()
    {
        var textReason = document.getElementById("textReason");

        if (textReason.value == '') {
            alert("Please enter reason for cancellation");
            return false;
        } else {
            opener.document.location.href = parent.document.location.href + '&confirmed=true' +'&textReason=' + textReason.value;
            self.close();
        }
   	}
  </script>
</head>
<body>
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
<!--
	// Top Nav/Logo Container
	// This will be included
-->
  <table width="350" cellpadding="0" cellspacing="0" border="0" style="border-top: solid 1px #FFB700;border-bottom: solid 1px #FFB700; ">
    <tr>
      <td>   
        <table width="350" cellpadding="0" cellspacing="0" border="0">
          <tr height="79">
            <td width="350" align="left" valign="top" style="background-color:#FFB700;">
              <table width="350" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="154" align="left" valign="top">
                    <img src="images/admin_top_logo.gif"><BR>
                  </td>
                  <td width="150" align="left" valign="middle">		
                    <table cellpadding="0" cellspacing="0">
                      <tr><td align="center" style="padding-bottom:5px;"><span class="displayWhiteDate14px"><%=op%> POST</span></td></tr>
                      <tr><td align="center"><span class="displayWhiteDate"><%=(new SimpleDateFormat("EEEE, MMMM dd, yyyy")).format(Calendar.getInstance().getTime())%> </span></td></tr>
                    </table>
                  </td>
                </tr>
              </table>				
            </td>
          </tr>
        </table>
        <table width="350" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td width="350" align="left" valign="top">
              <table width="350" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="350" align="left" valign="top">		
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="350" align="left" valign="top" style="padding-top:8px;">
                          <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td class="displayBoxTitle" align="center" style="color:#333333;">
                                <br><br>
                                Are you sure you want to <%=op%> posting <%=comp_num%>?<br><br>
                                Please enter reason for cancellation<br><br>
                                <form>
                                <textarea rows="6" cols="40" id="textReason"></textarea>
                                  <input type="button" value="YES" onclick="confirmed();">
                                  <img src="images/spacer.gif" width="15px;" height="1px">
                                  <input type="button" value="NO" onclick="window.close();">
                                </form>
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
        </table>
      </td>
    </tr>
  </table>
  <jsp:include page="footer.jsp" flush="true" >
    <jsp:param name="width" value="350" />
  </jsp:include>
</body>
</html>
