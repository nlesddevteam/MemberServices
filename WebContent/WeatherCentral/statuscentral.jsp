<%@ page contentType="text/html;charset=windows-1252"
         import="java.util.*,com.awsd.personnel.*,
                 com.awsd.weather.*,com.awsd.school.*"
         isThreadSafe="false"%>

<html>
  <head>
    <title>Eastern School District - School Status Central</title>
    <meta http-equiv="refresh" content="300"> 
    <script type="text/javascript">
      function status_central_loaded()
      {
        document.all.status_central_loading.style.display="none";
      }
    </script>
    <style>
      #status_central_loading {
        position:absolute; 
        left:0px; 
        top:0px; 
        width:176px; 
        height:210px; 
        z-index:199;  
        border: none;
        display:inline;
      }

      #status_central_container {
        position:absolute; 
        left:0px; 
        top:0px; 
        width:176px; 
        height:210px; 
        z-index:99;  
        border: none;
      }
    </style>
  </head>
<body>
      <table width="176" height="210" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="176" height="210" align="center" valign="top">
            <div id="status_central_loading">
              <OBJECT codeBase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="176" height="210"  classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000">
                <PARAM NAME="Movie" VALUE="http://www.esdnl.ca/flash/loading.swf">
                <PARAM NAME="Quality" VALUE="High">
                <PARAM NAME="BGColor" VALUE="#FFFFFF">
                <embed src="http://www.esdnl.ca/flash/loading.swf" 
                       quality="high" 
                       pluginspage="http://www.macromedia.com/go/getflashplayer" 
                       type="application/x-shockwave-flash" 
                       width="176" height="210">
                </embed>
              </OBJECT>
            </div>
            <div id="status_central_container">
              <iframe id="status_central_content" width="176" height="210" src="esdnl_schoolClosureStatus.jsp"	frameborder="0" scrolling="no"></iframe>
            </div>
          </td>
        </tr>
      </table>
  </body>
</html>