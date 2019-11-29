<%@ page contentType="text/html;charset=windows-1252"
         import=" java.util.*,
                  com.esdnl.util.*"
         isThreadSafe="false"%>
<html>
  
 <head>
  	<title>PD Calendar</title>

<style>
.tableTitle {font-weight:bold;width:20%;color:White;text-transform:uppercase;}
.tableResult {font-weight:normal;width:80%;background-color:#ffffff;}
.tableTitleL {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
.tableResultL {font-weight:normal;width:30%;background-color:#ffffff;}
.tableTitleR {font-weight:bold;width:20%;background-color:#006400;color:White;text-transform:uppercase;}
.tableResultR {font-weight:normal;width:30%;background-color:#ffffff;}
input {border:1px solid silver;}
</style>
  </head>

  <body>
   <div class="container-fluid no-print" data-spy="affix" data-offset-top="0" style="position:fixed;width:100%;height:30px;background-color:#FF0000;color:White;text-align:center;font-weight:bold;padding:5px;">                      
  DECLINE PD REQUEST CONFIRMATION</span>
</div>
  
  
  <div class="registerEventDisplay" style="padding-top:25px;font-size:11px;">
   
   <div align="center" class="no-print"><a href="viewDistrictCalendar.html"><img class="topLogoImg" src="includes/img/pdcalheader.png" border=0 style="padding-bottom:10px;"/></a></div>
    
    
    <% if(request.getAttribute("msg") != null) { %>
  <div align="center" class="alert alert-danger"><%=request.getAttribute("msg")%></div>
    <%} else if(request.getAttribute("msgOK") != null) { %>
      <div align="center" class="alert alert-success"><%=request.getAttribute("msgOK")%></div>          
    <%} %>   
      <% if(!StringUtils.isEmpty(request.getParameter("action"))) { %>
          
         <form action="<%=request.getParameter("action")%>">
         <input type='hidden' name='CONFIRMED' value='TRUE'>
         <% 
          Iterator iter = request.getParameterMap().entrySet().iterator();
          Map.Entry entry = null;
          String key = null;
          String value = null;
          while(iter.hasNext())
          {
            entry = (Map.Entry) iter.next();
            key = (String)entry.getKey();
            value = request.getParameter(key);
            
            System.out.println(key + " : " + value);
            
            if(!key.equals("action")) {%>
              <input type='hidden' name="<%=key%>" value="<%=value %>">
            <%}%>
          <%}%>
          <table class='table table-striped table-condensed' style='font-size:11px;'>
          <tbody>
          <tr><td class='tableTitle'>Comments:</td>
         <td class='tableResult'>
         	<div id="mes_Error" class="alert alert-danger" style="display:none;">ERROR: Character limit exceeded. You are only allowed to input 2000 characters.</div>
			<textarea name='comments' id="comments" rows=8 class='form-control'></textarea>
            <div style="width:100%;margin-top:2px;text-align:right;font-size:9;color:grey;">Max Characters: 2000 - Remain: <span id="mes_remain">2000</span></div>
          </td></tr>
          <tr><td colspan=2 align='center' style='padding-top:10px;text-align:center;'><input type='submit' class="btn btn-xs btn-danger" value='DECLINE'></td></tr>
         </tbody>
         </table>
         </form>
          
      <% } %>
        
   </div>
   
   <script>
$('#comments').keypress(function(e) {
    var tval = $('#comments').val(),
        tlength = tval.length,
        set = 2000,
        remain = parseInt(set - tlength);
    $('#mes_remain').text(remain);    
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
    	$('#mes_Error').css('display','block').delay(4000).fadeOut();
        $('#comments').val((tval).substring(0, tlength - 1))
    }
});
</script>
   
   
  </body>
</html>
