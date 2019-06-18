<%@ page language="java"
        session="true"
         import="com.awsd.security.*,
                 com.awsd.efile.*,
                 com.awsd.efile.equestion.*,
                 com.awsd.school.*,
                 java.util.*,
                 com.awsd.servlet.*,
                 java.io.*,
                 java.text.*,
                 com.awsd.common.*"%>

<%!
  User usr = null;
  QuestionType type = null;
  Question q = null;
  Subject s = null;
  Grade g = null;
  Course c = null;
  Vector page = null;
  HashMap cart = null;
  Iterator iter = null;
  QuestionOptions options = null;
  QuestionOption option = null;
  Iterator opt_iter = null;
  int page_num;
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
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}
  cart = (HashMap) session.getAttribute("EQUESTION-CART");
  if(cart == null)
  {
    cart = new HashMap(10);
    session.setAttribute("EQUESTION-CART", cart);
  }

  page_num = Integer.parseInt(request.getParameter("page"));
  
  int q_cnt = 1;
%>
<html>
  <head>
    <title>E-File Repository - Member Services/Eastern School District</title>
    <link rel="stylesheet" href="../css/e-file.css">
    <script language="JavaScript" src="../../js/common.js"></script>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
    <table width="100%" cellpadding="10" cellspacing="0" border="0">
      <tr>
        <td width="100%" valign="top">
          <table width="60%" he cellpadding="0" cellspacing="0" border="0" align="left">
            <%if(request.getParameter("view") != null){%>
            <tr>
              <td width="100%" valign="middle" align="right">
                <img src="../images/view_cart_01.gif" border="0" style="cursor:hand;"
                     onmouseover="src='../images/view_cart_02.gif';"
                     onmouseout="src='../images/view_cart_01.gif';"
                     onclick="document.location.href='viewQuestionCart.html?page=<%=page_num%>';"><br>
              </td>
            </tr>
            <%}%>
            <tr>
              <td width="100%" valign="top" align="left" colspan="2">
                <table width="100%" cellpadding="5" cellspacing="1">
                  <% if(cart.size() < 1) { %>
                  <tr>
                    <td width="5%" valign="top" align="left" colspan="1" bgcolor="#F5F5EF">
                      &nbsp
                    </td>
                    <td colspan="5" valign="top" align="left" bgcolor="#F5F5EF">
                      <font color="#FF0000" style="font-weight:bold;">No Question found in cart.</font>
                    </td>
                  </tr>
                  <%} else {
                    iter = cart.entrySet().iterator();
                    while(iter.hasNext()) 
                    {
                      q = (Question)((Map.Entry)iter.next()).getValue();%>
                      <tr>
                        <td width="100%" align="left">
                          <table width="75%" cellspacing="0" cellpadding="0">
                            <tr>
                              <td>
                                <table width="100%" cellpadding="2" cellspacing="0">
                                  <tr>
                                    <td width="32" height="32px" style="font-size:12px;line-height:12px;" valign="middle"><%=q_cnt++%>.</td>
                                    <td width="*"><%=q.getStem()%></td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                            <% if(((q.getQuestionTypeID()==1)||(q.getQuestionTypeID()==2))&&(q.getOptions() != null) && (q.getOptions().size() > 0)){
                                opt_iter = q.getOptions().iterator();
                                while(opt_iter.hasNext()){
                                  option = (QuestionOption) opt_iter.next();%>
                                  <tr>
                                    <td width="100%" align="left">
                                      <table width="100%" cellpadding="2" cellspacing="">
                                        <tr>
                                          <td width="32" height="32" style="font-size:32px;line-height:32px;">&nbsp;</td>
                                          <%if(q.getQuestionTypeID() == 1){%>
                                              <td width="22"><img src="../images/radio_button_off.gif"><br></td>
                                          <%}else if(q.getQuestionTypeID() == 2){%>
                                              <td width="22"><img src="../images/checkbox_off.gif"><br></td>
                                          <%}%>
                                          <td width="*" align="left" valign="middle"><%=option.getOption().trim()%></td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                <%}
                              }else if(q.getQuestionTypeID()==3){%>
                                <tr>
                                  <td>
                                    <table width="100%" cellpadding="2" cellspacing="0">
                                      <tr>
                                        <td width="32" height="32px" align="center" style="font-size:32px;line-height:32px;" valign="middle">&nbsp;</td>
                                        <td width="*">
                                          <table width="100%" cellspacing="0" cellpadding="0">
                                            <tr>
                                              <td width="75">
                                                <table width="100%" cellspacing="0" cellpadding="0">
                                                  <tr>
                                                    <td width="22" >
                                                      <img src="../images/radio_button_off.gif"><br>
                                                    </td>
                                                    <td width="*">TRUE</td>
                                                  </tr>
                                                </table>
                                              </td>
                                              <td width="75">
                                                <table width="100%" cellspacing="0" cellpadding="0">
                                                  <tr>
                                                    <td width="22">
                                                      <img src="../images/radio_button_off.gif"><br>
                                                    </td>
                                                    <td width="*">FALSE</td>
                                                  </tr>
                                                </table>
                                              </td>
                                              <td width="*">&nbsp;</td>
                                            </tr>
                                          </table>
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                              <%}else if(q.getQuestionTypeID()==4){%>
                                <tr>
                                  <td>
                                    <table width="100%" cellpadding="2" cellspacing="0">
                                      <tr>
                                        <td width="32" height="32px" align="center" style="font-size:32px;line-height:32px;" valign="middle">&nbsp;</td>
                                        <td width="*">____________________________</td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                            <%}%>
                          </table>
                        </td>
                      </tr>
                    <%}
                  }%>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <%if(request.getParameter("view") == null){%>
    <script language="JavaScript">
        parent.cart_main.print_cal();
    </script>
    <%}%>
  </body>
</html>