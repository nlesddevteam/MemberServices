<%@ page language="java"
          session="true"
          import="com.awsd.security.*,
                  com.awsd.pdreg.*,com.awsd.personnel.*"
          isThreadSafe="false"%>

<%!
  User usr = null;
  UserPermissions permissions = null;
  String msg;
%>
<%
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    permissions = usr.getUserPermissions();
    if(!(permissions.containsKey("CALENDAR-VIEW") 
        && (permissions.containsKey("CALENDAR-SCHEDULE")
            || permissions.containsKey("CALENDAR-VIEW-PARTICIPANTS"))))
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
    msg = (String) request.getAttribute("msg");
%>
<html>
  <head>  
   <title>PD Calendar</title>

    <script language="JavaScript">
      function processing()
      {
       $("#processing").css("display","inline");
       $("#sendEmail").css("display","none");
        document.email.submit();
      }
      $("#loadingSpinner").css("display","none");
      </script>
      <style>
      .tableTitle {font-weight:bold;width:10%;color:Black;text-transform:uppercase;}
      .tableResult {font-weight:normal;width:90%;background-color:#ffffff;}
      .tableTitleL {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
      .tableResultL {font-weight:normal;width:30%;background-color:#ffffff;}
      .tableTitleR {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
      .tableResultR {font-weight:normal;width:30%;background-color:#ffffff;}
      input {border:1px solid silver;}

      </style>
  </head>
  <body>
   	<div class="container-fluid no-print" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;height:30px;background-color:#008B8B;color:White;text-align:center;font-weight:bold;padding:5px;">                      
  	EMAIL PARTICIPANTS OF THIS EVENT
	</div>
	<div class="registerEventDisplay" style="padding-top:25px;font-size:11px;">
    <div style="margin-left:5px;margin-right:5px;"> 
   	<div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>
  

  <% if(request.getAttribute("msg") != null) { %>
	  <div align="center" class="alert alert-info"><%=request.getAttribute("msg")%></div>
	  <div align="center"><a class="no-print btn btn-xs btn-danger" href="viewDistrictCalendar.html" title="Back to Calendar">Back to Calendar</a></div>  
  <%} else if(request.getAttribute("msgOK") != null) { %>
	  <div align="center" class="alert alert-success" id="successEmail"><%=request.getAttribute("msgOK")%></div>
	  <div align="center"><a class="no-print btn btn-xs btn-danger" href="viewDistrictCalendar.html" title="Back to Calendar">Back to Calendar</a></div>  
  <%} else if(request.getAttribute("msgERR") != null) { %>
	  <div align="center" class="alert alert-danger" id="errorEmail"><%=request.getAttribute("msgERR")%></div>
	  <div align="center"><a class="no-print btn btn-xs btn-danger" href="viewDistrictCalendar.html" title="Back to Calendar">Back to Calendar</a></div>  
  <%} else { %>
  	
    	<form name="email" action="sendEventParticipantsEmail.html" method="post">      
      	<div id='processing' style="display:none;text-align:center;" class="alert alert-info">Sending Email...</div>     
	      To send a message to the participants of the &quot;<b><%=request.getAttribute("subject")%></b>&quot; event, please fill out the fields below and click Send Message. 
	      You can add additional recipients to the To, Cc, and Bcc fields if required. (Separate each email address by a semicolon.)
	      <br/><br/>
        <div class="formTitle">FROM:</div>
        <div class="formBody">	
        <input type="text" name="from" class="form-control" value="<%=usr.getPersonnel().getEmailAddress()%>" readonly>
       </div> 
       <div class="formTitle"> TO:</div>
       <div class="formBody">	
            <textarea name="to" rows="4" class="form-control"><%=request.getAttribute("to")%></textarea>
        </div>
        <div class="formTitle">CC:</div>
        <div class="formBody">	
            <textarea name="cc" rows="2" class="form-control"></textarea>
        </div>
        <div class="formTitle">BCC:</div>
        <div class="formBody">	
            <textarea name="bcc" rows="2" class="form-control"></textarea>
          </div>
          <div class="formTitle">SUBJECT:</div>
          <div class="formBody">	
            <input type="text" name="subject" class="form-control" readonly value='<%=request.getAttribute("subject")%>'>
          </div>
         <div class="formTitle">MESSAGE:</div>
         <div class="formBody">	
          	<div id="mes_Error" class="alert alert-danger" style="display:none;">ERROR: Character limit exceeded. You are only allowed to input 2000 characters.</div>
			<textarea name="message" id="message" rows="8" class="form-control"></textarea>
            <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2000 - Remain: <span id="mes_remain">2000</span></div>
			</div>			
            <div align="center" class="no-print navBottom">
          <a href="#" class="no-print btn btn-xs btn-primary" id="sendEmail" onclick="processing();">Send Message</a> <a class="no-print btn btn-xs btn-danger" href="javascript:history.go(-1);">Cancel</a>
          </div>
                     


    </form>
  <%} %>
    </div></div>
        <script>
$('#message').keypress(function(e) {
    var tval = $('#message').val(),
        tlength = tval.length,
        set = 2000,
        remain = parseInt(set - tlength);
    $('#mes_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#mes_Error').css('display','block').delay(4000).fadeOut();
        $('#message').val((tval).substring(0, tlength - 1))
    }
});
</script>
    
    
  </body>
</html>
