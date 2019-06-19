<%@ page  language="java" 
          session="true" 
          import="java.util.*,  
            com.awsd.pdreg.*,com.awsd.security.*, 
            com.awsd.common.Utils;"
            isThreadSafe="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="CALENDAR-VIEW" />
            

<%
  //final String STR_ICONPATH = "images/";


  User usr = (User) session.getAttribute("usr");
  UserPermissions permissions = null;
  RegisteredEvents revts = null;
  CloseoutEvents events = null;
  //Event evt = null;
  //Iterator iter = null;
  String color;
  String options[] = null;
  boolean opchk[] = null;
  int width;

  CalendarLegend legend = null;

  events = (CloseoutEvents)request.getAttribute("events");
  options = events.getOptions();
  width = 100/options.length;
  legend = new CalendarLegend();

  revts = new RegisteredEvents(usr.getPersonnel());
  opchk = new boolean[]{false, false, false};  
  
  Calendar reg_start = Calendar.getInstance();
  Date dt_reg_start = null;
  Calendar reg_end = Calendar.getInstance();
  Date dt_reg_end = null;
  Calendar now = Calendar.getInstance();
  Date dt_now = now.getTime();
  
  reg_start.clear();
  reg_start.setTime(events.getCloseOutEvent().getEventDate());
  reg_start.add(Calendar.WEEK_OF_YEAR, -3);
  dt_reg_start = reg_start.getTime();
  
  reg_end.clear();
  reg_end.setTime(reg_start.getTime());
  reg_end.add(Calendar.WEEK_OF_YEAR, 2);
  reg_end.add(Calendar.DATE, 2);
  dt_reg_end = reg_end.getTime();
%>

<html>

<head>
<title>Newfoundland &amp; Labrador English School District - District Close-Out PD SESSIONS</title>
<link href="../css/calendar.css" rel="stylesheet">
<style>
	td {font-family: Tahoma, Verdana, sans-serif; font-size: 12px;}
</style>

<script language="JavaScript" src="../js/common.js"></script>
<script type="text/javascript" src='js/jquery-1.6.1.min.js'></script>

<script language="Javascript">
  var chk_opt='';
  var chk_id='';
  
  var OPT_A_CHECKED = false;
  var OPT_B_CHECKED = false;
  var OPT_C_CHECKED = false;
  
  function processing(image)
  {
    document.images[image].src='images/processing_ani.gif'; 
    document.images[image].onmouseover="src='images/spacer.gif';" 
    document.images[image].onmouseout="src='images/spacer.gif';"
    document.images[image].onmousedown="src='images/spacer.gif';"
    document.images[image].onmouseup="src='images/spacer.gif';"
  }

  function ToggleTable(target, sw)
  {
    obj=(document.all) ? document.all[target] : document.getElementById(target);
    obj.style.display=sw;
  }
  
  function ToggleTables()
  {
    if(OPT_A_CHECKED == true)
    {
      ToggleTable('OPTION_B', 'none');
      ToggleTable('OPTION_C', 'none');
    }
    else if((OPT_B_CHECKED == true) || (OPT_C_CHECKED == true))
      ToggleTable('OPTION_A', 'none');
    else
    {
      ToggleTable('OPTION_A', 'inline');
      ToggleTable('OPTION_B', 'inline');
      ToggleTable('OPTION_C', 'inline');
    }
  }

  function manageCheckboxes(cb)
  {
    var chkbox = document.getElementById(cb);
    var option = cb.substring(0, cb.indexOf('_'));
    var id = cb.substring(cb.indexOf('_')+1);
    
    enableChkbox(option, id, chkbox.checked);
    
    if(chkbox.checked)
    {
      if(option == 'A')
      {
        OPT_A_CHECKED = true;
        
        ToggleTable('OPTION_B', 'none');
        ToggleTable('OPTION_C', 'none');
      }
      else if(option == 'B')
      {
        OPT_B_CHECKED = true;
        ToggleTable('OPTION_A', 'none');
      }
      else if(option == 'C')
      {
        OPT_C_CHECKED = true;
        ToggleTable('OPTION_A', 'none');
      }
    }
    else
    {
      if(option == 'A')
      {
        OPT_A_CHECKED = false;
        ToggleTable('OPTION_B', 'inline');
        ToggleTable('OPTION_C', 'inline');
      }
      else 
      {
        if(option == 'B')
        {
          OPT_B_CHECKED=false;
        }
        else if(option == 'C')
        {
          OPT_C_CHECKED=false;
        }
        
        if(!OPT_B_CHECKED && !OPT_C_CHECKED)
        {
          ToggleTable('OPTION_A', 'inline');
        }
      }
    }
    
    if($('.noevents').length < ${fn:length(events.options)}) {
		  $('.noevents').each(function(){
			  $(this).parent().parent().parent().parent().hide();
		  })
	  }
  }

  function validateRegistration()
  {
    if((OPT_A_CHECKED == true) || ((OPT_B_CHECKED==true)&&(OPT_C_CHECKED==true)))
    {
      processing('reg'); 
      closeoutreg.submit();
    }
    else
    {
      alert('Please Select select the appropriate number of events for registration.');
    }
  }

  function enableChkbox(option, id, chked)
  {
    var elems = document.closeoutreg.elements;
    var chkopt;
    var chkid;
    var n;
    for(i=0; i<elems.length; i++)
    {
      n = elems[i].name;
      chkopt = n.substring(0, n.indexOf('_'));
      chkid = n.substring(n.indexOf('_')+1);
      //alert('id:'+ id +'\nchkid:'+chkid);
      if((chkopt == option) && (chkid != id))
      {
        elems[i].disabled = chked;
      }
    }
  }
  
  $('document').ready(function(){
	  
	  ToggleTables();
	 
	  if($('.noevents').length < ${fn:length(events.options)}) {
		  $('.noevents').each(function(){
			  $(this).parent().parent().parent().parent().hide();
		  })
	  }
  });
  
</script>
</head>

<body bgcolor="#FFFFFF" marginheight="5" marginwidth="5" topmargin="5" leftmargin="5" rightmargin="5">
<form name="closeoutreg" action="closeoutRegistration.html" method="post">
<input type="hidden" name="id" value="<%=events.getCloseOutEvent().getEventID()%>">
<table cellspacing="0" cellpadding="2" border="0" width="100%">
  <tr>
    <td bgcolor="#4682B4" width="100%">
      <table align="center" cellspacing="0" cellpadding="2" border="0" width="100%">
        <tr>
          <td valign="middle" align="center">
            <font size="3" color="#FFFFFF"><b><%=events.getCloseOutEvent().getEventName()%></b></font>
          </td>
        </tr>
      </table>
      <table align="center" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0" border="0" width="100%">  
        <tr>
          <td align="center" colspan="2">
            <table cellspacing="4" cellpadding="0" border="0" width="100%">
              <tr>
                <%for(int i=0; i < options.length; i++) {%>
                <td id="<%="OPTION_" + options[i]%>" width="<%=width%>%" valign="top" style='display:inline;'>
                  <table bgcolor="#4682B4" width="100%" cellspacing="1" cellpadding="3" border="0">
                    <tr>
                      <td width="100%" bgcolor="#f4f4f4" align="left">
                        <b>Option <%=options[i]%></b>
                        <font size="1">
                        <% switch (i) {
                            case 0:
                        %>    (All Day)
                        <%    break;
                            case 1:
                        %>    (AM)
                        <%    break;
                            case 2:
                        %>    (PM)
                        <%}%>
                        </font>
                      </td>
                    </tr>
                  <%if(events.getEvents(options[i]).size() <= 0)
                    {
                  %>  <tr>
                        <td class='noevents' bgcolor="#ffffff" align="center" width="100%">
                          <font color="#FF0000"><B>No Close-Out Events Scheduled.</B></font>
                        </td>
                      </tr>
                  <%} 
                    else 
                    {
                      for(Event evt : events.getEvents(options[i]))
                      {
                        if(legend.containsKey(new Integer(evt.getSchedulerID())))
                        {
                          color = (String) legend.get(new Integer(evt.getSchedulerID()));
                        }
                        else
                        {
                          color = "FFFFFF";
                        }
                  %>    <tr>
                          <td width="100%" bgcolor="#<%=color%>" align="left">
                            <table width="100%">
                              <tr>
                                <td width="2%">
                                  <%if(revts.containsKey(new Integer(evt.getEventID()))){%>
                                    <img src='images/green_check.gif' border='0'>
                                    <script language="JavaScript">
                                        chk_opt = '<%=evt.getEventCloseoutOption()%>';
                                        chk_id = '<%=evt.getEventID()%>';
                                    </script>
                                    <%if(evt.getEventCloseoutOption().equals("A"))
                                      {
                                        opchk[0] = true;
                                    %>
                                      <script language="JavaScript">
                                        OPT_A_CHECKED = true;
                                      </script>
                                    <%}else if(evt.getEventCloseoutOption().equals("B"))
                                      {
                                        opchk[1] = true;
                                    %>
                                      <script language="JavaScript">
                                        OPT_B_CHECKED = true;
                                      </script>
                                    <%}else if(evt.getEventCloseoutOption().equals("C"))
                                      {
                                        opchk[2] = true;
                                    %>
                                      <script language="JavaScript">
                                        OPT_C_CHECKED = true;
                                      </script>
                                    <%}%>
                                  <%}else if(Utils.compareCurrentDate(evt.getEventDate())==0){%>
                                    <font style="font-weight:bold;">ACTIVE</font>
                                  <%}else if((evt.getEventEndDate()!= null)&&(Utils.compareCurrentDate(evt.getEventDate())==-1)&&(Utils.compareCurrentDate(evt.getEventEndDate())>=0)){%>
                                    <font style="font-weight:bold;">ACTIVE</font>
                                  <%}else if(Utils.compareCurrentDate(evt.getEventDate())==-1){%>
                                    <font style="font-weight:bold;">PAST</font>
                                  <%}else if(!evt.isFull()){%>
                                    <input type="checkbox" onclick="manageCheckboxes('<%=evt.getEventCloseoutOption()+ "_" +evt.getEventID()%>');" name="<%=evt.getEventCloseoutOption()+ "_" +evt.getEventID()%>">
                                  <%}else{%>
                                    <font style="font-weight:bold;">FULL</font>
                                  <%}%>
                                </td>
                                <td width="*">
                                  <%if(Utils.compareCurrentDate(evt.getEventDate())<=0){%>
                                    <a class="closeout" href="javascript:openWindow('registration', 'registerEvent.html?id=<%=evt.getEventID()%>', 430, 465, 1);"><%= evt.getEventName()%></a>
                                  <%}else if(!revts.containsKey(new Integer(evt.getEventID())) && (Utils.compareCurrentDate(evt.getEventDate())==1)){%>
                                    <a class="closeout" href="javascript:openWindow('registration', 'registerEvent.html?id=<%=evt.getEventID()%>&details=true', 430, 465, 1);"><%= evt.getEventName()%></a>
                                  <%}else{%>
                                    <a class="closeout" href="javascript:openWindow('deregistration', 'deregisterEvent.html?id=<%=evt.getEventID()%>', 430, 465, 1);"><%= evt.getEventName()%></a>
                                  <%}%>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                  		<%}%>
                      <script type="text/javascript">
                        if((chk_opt != '')&&(chk_id!=''))
                        {
                          enableChkbox(chk_opt, chk_id, true);
                        }
                        chk_opt='';
                        chk_id='';
                      </script>
                  <%}%>
                  </table>
                </td>
                <%}%>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td bgcolor="#4682B4" width="100%">
      <table bgcolor="#4682B4" align="center" cellspacing="0" cellpadding="0" border="0" width="100%">
        <tr>
          <td valign="middle" align="center" width="100%">
            <%if(opchk[0] || (opchk[1]&&opchk[2])){%>
              <img src="images/closeblue_01.jpg" 
               onmouseover="src='images/closeblue_02.jpg';" 
               onmouseout="src='images/closeblue_01.jpg';"
               onmousedown="src='images/closeblue_03.jpg';"
               onmouseup="src='images/closeblue_02.jpg';"
               onclick="self.close();">
            <%}else{%>
            	<%if((dt_reg_start.compareTo(dt_now) <= 0)){%>
		            <img id="reg" src="images/register_blue_01.jpg" 
		              onmouseover="src='images/register_blue_02.jpg';"
		              onmouseout="src='images/register_blue_01.jpg';"
		              onmousedown="src='images/register_blue_03.jpg';"
		              onmouseup="src='images/register_blue_02.jpg';"
		              onclick="validateRegistration();">
              <%}else{%>
              	<font style="font-weight:bold;color:#FFCC00;">Registration not available at this time.</font>
              <%}%>
            <%}%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>