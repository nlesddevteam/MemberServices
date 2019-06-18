<%@ page contentType="text/html;charset=windows-1252"
         import="java.util.*,com.awsd.personnel.*,
                 com.awsd.weather.*,com.awsd.school.*"
         isThreadSafe="false"%>

<%!
  SchoolSystems systems = null;
  SchoolSystem system = null;
  ClosureStatus stat = null;
  School school = null;
  
  Iterator sys_iter = null;
  Iterator sch_iter = null;

  private String cssClass(int id)
  {
    String css;
    
    switch(id)
    {
      case 4:
      case 7:
      case 10:
      case 11:
      case 21:
      case 22:
      case 62:
      case 84:
      case 102:
      case 123:
        css = "weatherCentralStatusClosed";
        break;
      case 6:
      case 5:
      case 42:
      case 63:
        css = "weatherCentralStatusDelayed";
        break;
      case 9:
        css = "weatherCentralStatusOpen";
        break;
      default:
        css ="weatherCentralStatusOpen";
    }

    return css;
  }
%>
<%
  systems = new SchoolSystems();
  sys_iter = systems.iterator();
%>

<html>
<head>
<title></title>
<meta http-equiv="refresh" content="300"> 
<link rel="stylesheet" href="http://www.esdnl.ca/scripts/weathercentral.css">
</head>
<body>
<div id="tempholder"></div>
<script language="JavaScript" src="http://www.esdnl.ca/scripts/dhtmllib.js"></script>
<script language="JavaScript" src="http://www.esdnl.ca/scripts/scroller.js"></script>
<script language="JavaScript">                     
  //SET SCROLLER APPEARANCE AND MESSAGES
  var myScroller1 = new Scroller(0, 0, 170, 75, 1, 5); //(xpos, ypos, width, height, border, padding)
  myScroller1.setColors("#333333", "#F8F8F1", "#F8F8F1"); //(fgcolor, bgcolor, bdcolor)
  myScroller1.setFont("Tahoma", 1);
  <%while(sys_iter.hasNext()){
      system = (SchoolSystem) sys_iter.next();%>
      myScroller1.addItem("<span class='weatherCentralSchoolSystem'><%=system.getSchoolSystemName()%></span>");
  <%  sch_iter = null;
      sch_iter = system.getSchoolSystemSchools().iterator();
      while((sch_iter != null) && sch_iter.hasNext()){
        school = (School) sch_iter.next();
        stat = school.getSchoolClosureStatus();
        if((stat.getSchoolClosureNote() != null) && !stat.getSchoolClosureNote().equalsIgnoreCase("")){%>
          myScroller1.addItem('<%=school.getSchoolName()%><BR><span class="<%=cssClass(stat.getClosureStatusID())%>"><%=stat.getClosureStatusDescription() + "<BR>" + stat.getSchoolClosureNote().replaceAll("\n", "<BR>")%></span>');
        <%}else{%>
          myScroller1.addItem('<%=school.getSchoolName()%><BR><span class="<%=cssClass(stat.getClosureStatusID())%>"><%=stat.getClosureStatusDescription()%></span>');
        <%}%>
  <%}}%>         
  //SET SCROLLER PAUSE
  myScroller1.setPause(500); //set pause beteen msgs, in milliseconds
           
  function runmikescroll() 
  {        
    var layer;
    var mikex, mikey;
           
    // Locate placeholder layer so we can use it to position the scrollers.      
    layer = getLayer("placeholder");
    mikex = getPageLeft(layer);
    mikey = getPageTop(layer);
           
    // Create the first scroller and position it.
    myScroller1.create();
    myScroller1.hide();
    myScroller1.moveTo(mikex, mikey);
    myScroller1.setzIndex(100);
    myScroller1.show();
  }
           
  window.onload=runmikescroll
</script>
<div id="placeholder" style="position:relative; width:170px; height:75px;"></div>
</body>
</html>