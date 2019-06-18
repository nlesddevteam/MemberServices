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
  int page_num;
  boolean canDelete = false;
  boolean added = false;
  SimpleDateFormat sdf = null;
  QuestionOptions options = null;
  QuestionOption option = null;
  Iterator opt_iter = null;
  boolean first = true;
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

  canDelete = usr.getUserPermissions().containsKey("EFILE-DELETE-DOCUMENT");

  sdf = new SimpleDateFormat("dd-MMM-yyyy");

  int q_cnt = 1;
%>
<html>
  <head>
    <title>E-File Repository - Member Services/Eastern School District</title>
    <link rel="stylesheet" href="../css/e-file.css">
    <script language="JavaScript" src="../../js/common.js"></script>
    <script language="JavaScript">
      function print_cal()
      {
        parent.cart_hidden.focus();
        window.print();
      }
    </script>
  </head>

  <body topmargin="0" bottommargin="0" leftmargin="0" rightmargin="0" marginwidth="0" marginheight="0">
    <jsp:include page="equestion_menu.jsp" flush="true"/>
    <table width="100%" cellpadding="10" cellspacing="0" border="0">
      <tr>
        <td width="100%" valign="top">
          <table width="60%" he cellpadding="0" cellspacing="0" border="0" align="center">
            <tr>
              <td width="50%" valign="top" align="left">
                <BR>
                <a href="javascript:parent.document.location='../EFileRepositoryChooser.html';" class="navigation">home</a>&nbsp;<img src="../images/nav_arrow.gif">&nbsp;<a href="javascript:parent.document.location='searchResults.html?page=<%=page_num%>';" class="navigation">search results</a>&nbsp;<img src="../images/nav_arrow.gif">&nbsp;view cart<BR>
                <img src="../images/view_cart_title.gif"><BR>
              </td>
              <td width="50%" align="right" valign="bottom">
                <img src="../images/view_printable_cart_01.gif" border="0" style="cursor:hand;"
                   onmouseover="src='../images/view_printable_cart_02.gif';"
                   onmouseout="src='../images/view_printable_cart_01.gif';"
                   onclick="parent.document.location.href='viewPrintableQuestionCart.html?view&page=<%=page_num%>';">
                &nbsp;
                <img src="../images/print_cart_01.gif" border="0" style="cursor:hand;"
                   onmouseover="src='../images/print_cart_02.gif';"
                   onmouseout="src='../images/print_cart_01.gif';"
                   onclick="self.parent.cart_hidden.location.href='viewPrintableQuestionCart.html?page=<%=page_num%>';">
                &nbsp;
                <img src="../images/clear_cart_01.gif" border="0" style="cursor:hand;"
                   onmouseover="src='../images/clear_cart_02.gif';"
                   onmouseout="src='../images/clear_cart_01.gif';"
                   onclick="parent.document.location.href='clearQuestionCart.html?page=<%=page_num%>';"><br>
              </td>
            </tr>
            <tr>
              <td width="100%" valign="top" align="left" colspan="2">
                <table width="100%" cellpadding="5" cellspacing="1" style="border-top-style: solid; border-top-width: 2px; border-top-color:#FEF153;border-bottom-style: solid; border-bottom-width: 2px; border-bottom-color:#FEF153;">
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
                          <table width="75%" cellspacing="0" cellpadding="0" style="border:solid 1px #CCCCCC;">
                            <tr>
                              <td>
                                <table width="100%" cellpadding="2" cellspacing="0">
                                  <tr>
                                    <td width="32" height="32px" style="border-right:solid 1px #CCCCCC;background-color:#e4e4e4;color:#00008B;font-size:24px;line-height:24px;" valign="middle"><%=q_cnt++%>.</td>
                                    <td width="*"><%=q.getStem()%></td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                            <% if(((q.getQuestionTypeID()==1)||(q.getQuestionTypeID()==2))&&(q.getOptions() != null) && (q.getOptions().size() > 0)){
                                opt_iter = q.getOptions().iterator();
                                first = true;
                                while(opt_iter.hasNext()){
                                  option = (QuestionOption) opt_iter.next();%>
                                  <tr>
                                    <td width="100%" align="left">
                                      <table width="100%" cellpadding="2" cellspacing="">
                                        <tr>
                                          <td width="32" height="32" style="border-right:solid 1px #CCCCCC;background-color:#E4E4E4;color:#8B0000;font-size:32px;line-height:32px;">&nbsp;</td>
                                          <%if(q.getQuestionTypeID() == 1){%>
                                              <td width="22" <%=(first)?"style='background-color:#d4d0c8;border-top:solid 1px #CCCCCC;'":"style='background-color:#d4d0c8;'"%>><img src="../images/radio_button_off.gif"><br></td>
                                          <%}else if(q.getQuestionTypeID() == 2){%>
                                              <td width="22" <%=(first)?"style='background-color:#d4d0c8;border-top:solid 1px #CCCCCC;'":"style='background-color:#d4d0c8;'"%>><img src="../images/checkbox_off.gif"><br></td>
                                          <%}%>
                                          <td width="*" align="left" valign="middle"  <%=(first)?"style='background-color:#d4d0c8;border-top:solid 1px #CCCCCC;'":"style='background-color:#d4d0c8;'"%>><%=option.getOption().trim()%></td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                  <% first = false;
                                }
                              }else if(q.getQuestionTypeID()==3){%>
                                <tr>
                                  <td>
                                    <table width="100%" cellpadding="2" cellspacing="0">
                                      <tr>
                                        <td width="32" height="32px" align="center" style="border-right:solid 1px #CCCCCC;background-color:#E4E4E4;color:#8B0000;font-size:32px;line-height:32px;" valign="middle">&nbsp;</td>
                                        <td width="*" style="border-top:solid 1px #CCCCCC;background-color:#d4d0c8;">
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
                                        <td width="32" height="32px" align="center" style="border-right:solid 1px #CCCCCC;background-color:#E4E4E4;color:#8B0000;font-size:32px;line-height:32px;" valign="middle">&nbsp;</td>
                                        <td width="*" style="border-top:solid 1px #CCCCCC;">____________________________</td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                            <%}%>
                          </table>
                        </td>
                        <td width="30" align="center" valign="top">
                          <img src="../images/remove_cart_item_01.gif" border="0" style="cursor:hand;"
                             onmouseover="src='../images/remove_cart_item_02.gif';"
                             onmouseout="src='../images/remove_cart_item_01.gif';"
                             onclick="parent.document.location.href='removeQuestionFromCart.html?qid=<%=q.getQuestionID()%>&page=<%=page_num%>';"
                             alt="Remove question from cart">
                        </td>
                      </tr>
                    <%}
                  }%>
                </table>
                <img src="../images/spacer.gif" width="1" height="100"><BR>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <table width="100%" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="100%" height="28" align="left" valign="bottom" background="../images/footer_bg.gif">
          <img src="../images/footer.gif"><BR>
        </td>
      </tr>
    </table>
  </body>
</html>