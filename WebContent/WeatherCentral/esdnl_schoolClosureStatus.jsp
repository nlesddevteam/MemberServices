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
  Cookie cookies[] = null;
  Cookie cookie = null;
  String cookie_val = null;
  Calendar start_cal = Calendar.getInstance();
  Calendar end_cal = null;

  System.out.println("SCROLLER LOADING[" + request.getRemoteAddr()+ "]....");
  
  systems = new SchoolSystems();
  sys_iter = systems.iterator();

  end_cal = Calendar.getInstance();

  System.out.println("....GOT DATA[" + request.getRemoteAddr() + "] (" + (end_cal.getTimeInMillis() - start_cal.getTimeInMillis()) + ")");

  cookies = request.getCookies();
  if(cookies != null)
  {
    for(int i=0; i < cookies.length; i++)
    {
      if(cookies[i].getName().equalsIgnoreCase("statuscentral_config"))
      {
        cookie_val = cookies[i].getValue();
        break;
      }
    }

    //System.err.println(cookie_val);
  }
%>

<html>
  <head>
    <title>Eastern School District - School Status Central</title>
    <meta http-equiv="refresh" content="300"> 
    <link rel="stylesheet" href="http://www.esdnl.ca/statuscentral/css/weathercentral.css">
    <script language="JavaScript" src="http://www.esdnl.ca/statuscentral/js/dhtmllib.js"></script>
    <script language="JavaScript" src="http://www.esdnl.ca/statuscentral/js/scroller.js"></script>
    <script language="JavaScript" src="http://www.esdnl.ca/statuscentral/js/rounded_corner.js"></script>
    <script language="JavaScript">
      function run_scroller() 
      {        
        var layer;
        var x, y;
           
        // Locate placeholder layer so we can use it to position the scrollers.      
        layer = getLayer("scroller_content");
        x = getPageLeft(layer);
        y = getPageTop(layer);
           
        // Create the first scroller and position it.
        myScroller1.create();
        myScroller1.hide();
        myScroller1.moveTo(x, y);
        myScroller1.setzIndex(100);
        myScroller1.show();
        if(myScroller1.stopped)
          reconcilImages(3);
      }

      function reconcilImages(flag)
      {
        if(flag == 1) //forward scrolling
        {
          document.all.img_sforward.src='http://www.esdnl.ca/statuscentral/images/scroll_forward_on.gif';
          document.all.img_sbackward.src='http://www.esdnl.ca/statuscentral/images/scroll_backward_off.gif';
          document.all.img_pause.src='http://www.esdnl.ca/statuscentral/images/pause_off.gif';
        }
        else if(flag == 2) //backward scrolling
        {
          document.all.img_sforward.src='http://www.esdnl.ca/statuscentral/images/scroll_forward_off.gif';
          document.all.img_sbackward.src='http://www.esdnl.ca/statuscentral/images/scroll_backward_on.gif';
          document.all.img_pause.src='http://www.esdnl.ca/statuscentral/images/pause_off.gif';
        }
        else if(flag == 3) //paused
        {
          document.all.img_sforward.src='http://www.esdnl.ca/statuscentral/images/scroll_forward_off.gif';
          document.all.img_sbackward.src='http://www.esdnl.ca/statuscentral/images/scroll_backward_off.gif';
          document.all.img_pause.src='http://www.esdnl.ca/statuscentral/images/pause_on.gif';
        }
        else if(flag == 4) //scroll back one
        {
          document.all.img_sforward.src='http://www.esdnl.ca/statuscentral/images/scroll_forward_off.gif';
          document.all.img_sbackward.src='http://www.esdnl.ca/statuscentral/images/scroll_backward_off.gif';
          document.all.img_pause.src='http://www.esdnl.ca/statuscentral/images/pause_on.gif';
        }
        else if(flag == 5) //scroll forward one
        {
          document.all.img_sforward.src='http://www.esdnl.ca/statuscentral/images/scroll_forward_off.gif';
          document.all.img_sbackward.src='http://www.esdnl.ca/statuscentral/images/scroll_backward_off.gif';
          document.all.img_pause.src='http://www.esdnl.ca/statuscentral/images/pause_on.gif';
        }
      }
      
      function restart()
      {
        if(!myScroller1.reversed)
        {
          myScroller1.start();
          reconcilImages(1);
        }
        else
        {
          myScroller1.reverse();
          reconcilImages(2);
        }
      }
    </script>
    <style type="text/css">
      p {
        font-family: Arial, Helvetica, sans-serif; 
        font-size: 10px; 
        line-height: 11px; 
        color: #333333;
        height:123px;
      }

      #scroller_background {
        position:absolute; 
        left:0px; 
        top:0px; 
        width:176px; 
        height:210px; 
        z-index:99;
        background-image:Url(http://www.esdnl.ca/statuscentral/images/status_central_container.gif); 
        border: none;
      }

      #scroller_content { 
        position: absolute;
        width: 143px;
        height: 123px;
        
        left: 15px; 
        top: 15px; 
        visibility: visible; 
        clip:rect(0, 143, 123, 0);
        z-index:100;
      }

      #scroller_loading {
        position:absolute; 
        left:0px; 
        top:0px; 
        width:176px; 
        height:210px; 
        z-index:199;  
        border: none;
      }
    </style>
  </head>
<body onload='parent.status_central_loaded(); run_scroller();'>
      <table width="143" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td> 
            <div id="tempholder"></div>
              <script language="JavaScript">                     
              //SET SCROLLER APPEARANCE AND MESSAGES
              var myScroller1 = new Scroller(0, 0, 143, 123, 1, 1, true); //(xpos, ypos, width, height, border, padding, rounded)
              myScroller1.setColors("#333333", "#F8F8F1", "#246C97"); //(fgcolor, bgcolor, bdcolor)
              myScroller1.setFont("Tahoma", 1);
              myScroller1.setCornerImages(new Array(new Corner('http://www.esdnl.ca/statuscentral/images/status_central_rounded_borderTL.gif', 6, 6),
                                                    new Corner('http://www.esdnl.ca/statuscentral/images/status_central_rounded_borderTR.gif', 6, 6),
                                                    new Corner('http://www.esdnl.ca/statuscentral/images/status_central_rounded_borderBL.gif', 6, 6),
                                                    new Corner('http://www.esdnl.ca/statuscentral/images/status_central_rounded_borderBR.gif', 6, 6)));
              <%while(sys_iter.hasNext()){
                  system = (SchoolSystem) sys_iter.next();
                  if((cookie_val==null) || cookie_val.equals("") || cookie_val.equals("|"))
                  {%>
                    myScroller1.addItem("<span class='weatherCentralSchoolSystem'><%=system.getSchoolSystemName()%></span>");
                <%}
                  else if(cookie_val.indexOf("|SYS-" + system.getSchoolSystemID() + "|") >= 0)
                  {%>
                    myScroller1.addItem("<span class='weatherCentralSchoolSystem'><%=system.getSchoolSystemName()%></span>");
                <%}
                  sch_iter = null;
                  sch_iter = system.getSchoolSystemSchools().iterator();
                  while((sch_iter != null) && sch_iter.hasNext()){
                    school = (School) sch_iter.next();
                    stat = school.getSchoolClosureStatus();
                    if((cookie_val==null) || cookie_val.equals("") || cookie_val.equals("|"))
                    {
                      if((stat.getSchoolClosureNote() != null) && !stat.getSchoolClosureNote().equalsIgnoreCase("")){%>
                        myScroller1.addItem('<div id="status_<%=school.getSchoolID()%>" class="floater_off" onmouseover="this.className=\'floater_on\'; myScroller1.stop(); reconcilImages(3);" onmouseout="this.className=\'floater_off\'; restart();"><%=school.getSchoolName()%><BR><span class="<%=cssClass(stat.getClosureStatusID())%>"><%=stat.getClosureStatusDescription() + "<BR>" + stat.getSchoolClosureNote().replaceAll("\n", "<BR>")%></span></div>');
                      <%}else{%>
                        myScroller1.addItem('<div id="status_<%=school.getSchoolID()%>" class="floater_off" onmouseover="this.className=\'floater_on\'; myScroller1.stop(); reconcilImages(3);" onmouseout="this.className=\'floater_off\'; restart();"><%=school.getSchoolName()%><BR><span class="<%=cssClass(stat.getClosureStatusID())%>"><%=stat.getClosureStatusDescription()%></span></div>');
                      <%}
                    }
                    else if(cookie_val.indexOf("|" + school.getSchoolID() + "|") >= 0)
                    {
                      if((stat.getSchoolClosureNote() != null) && !stat.getSchoolClosureNote().equalsIgnoreCase("")){%>
                        myScroller1.addItem('<div id="status_<%=school.getSchoolID()%>" class="floater_off" onmouseover="this.className=\'floater_on\'; myScroller1.stop(); reconcilImages(3);" onmouseout="this.className=\'floater_off\'; restart();"><%=school.getSchoolName()%><BR><span class="<%=cssClass(stat.getClosureStatusID())%>"><%=stat.getClosureStatusDescription() + "<BR>" + stat.getSchoolClosureNote().replaceAll("\n", "<BR>")%></span></div>');
                      <%}else{%>
                        myScroller1.addItem('<div id="status_<%=school.getSchoolID()%>" class="floater_off" onmouseover="this.className=\'floater_on\'; myScroller1.stop(); reconcilImages(3);" onmouseout="this.className=\'floater_off\'; restart();"><%=school.getSchoolName()%><BR><span class="<%=cssClass(stat.getClosureStatusID())%>"><%=stat.getClosureStatusDescription()%></span></div>');
                      <%}
                    }
                  }
                }
                System.gc();
                %>         
              //SET SCROLLER PAUSE
              myScroller1.setPause(500); //set pause beteen msgs, in milliseconds
            </script>
            <div id="scroller_content"></div>
           </td>
          </tr>
        </table>
    <div id="scroller_background"></div>
    <div id="p7sc1Backward" 
         style="position:absolute; left:16px; top:178px; width:24px; height:24px; z-index:103;">
          <a href="javascript:;" 
             onMouseDown="myScroller1.backward(); reconcilImages(4); return false;"
             onClick="return false" 
             onFocus="if(this.blur)this.blur()">
             <img src="http://www.esdnl.ca/statuscentral/images/backward_off.gif" 
                  width="24" height="24" border="0" alt="Scroll back one"
                  onmouseover="this.src='http://www.esdnl.ca/statuscentral/images/backward_on.gif';"
                  onmouseout="this.src='http://www.esdnl.ca/statuscentral/images/backward_off.gif';">
          </a>
    </div>
    <div id="p7sc1ScrollForward" 
         style="position:absolute; left:110px; top:178px; width:24px; height:24px; z-index:103;">
          <a href="javascript:;" 
             onMouseDown="myScroller1.start(); reconcilImages(1); return false;" 
             onClick="return false" 
             onFocus="if(this.blur)this.blur()">
             <img id="img_sforward" 
                  src="http://www.esdnl.ca/statuscentral/images/scroll_forward_on.gif" 
                  width="24" height="24" border="0" alt="Scroll forward"
                  onmouseover="this.src='http://www.esdnl.ca/statuscentral/images/scroll_forward_on.gif';"
                  onmouseout="if(myScroller1.stopped || myScroller1.reversed)this.src='http://www.esdnl.ca/statuscentral/images/scroll_forward_off.gif'; else this.src='http://www.esdnl.ca/statuscentral/images/scroll_forward_on.gif';">
          </a>
    </div>
    <div id="p7sc1Pause" 
         style="position:absolute; left:78px; top:178px; width:24px; height:24px; z-index:103;">
         <a href="javascript:;" 
            onMouseDown="myScroller1.stop(); reconcilImages(3); return false;" 
            onClick="return false" 
            onFocus="if(this.blur)this.blur()">
            <img id="img_pause" 
                 src="http://www.esdnl.ca/statuscentral/images/pause_off.gif"
                 onmouseover="this.src='http://www.esdnl.ca/statuscentral/images/pause_on.gif';"
                 onmouseout="if(!myScroller1.stopped)this.src='http://www.esdnl.ca/statuscentral/images/pause_off.gif';else this.src='http://www.esdnl.ca/statuscentral/images/pause_on.gif';"
                 width="24" height="24" border="0" alt="pause">
         </a>
    </div>
    <div id="p7sc1ScrollBackward" 
         style="position:absolute; left:46px; top:178px; width:24px; height:24px; z-index:103;">
          <a href="javascript:;" 
             onMouseDown="myScroller1.reverse(); reconcilImages(2); return false;" 
             onClick="return false" 
             onFocus="if(this.blur)this.blur()">
             <img id="img_sbackward" 
                  src="http://www.esdnl.ca/statuscentral/images/scroll_backward_off.gif" 
                  width="24" height="24" border="0" alt="Scroll reverse"
                  onmouseover="this.src='http://www.esdnl.ca/statuscentral/images/scroll_backward_on.gif';"
                  onmouseout="if(myScroller1.stopped || !myScroller1.reversed)this.src='http://www.esdnl.ca/statuscentral/images/scroll_backward_off.gif'; else this.src='http://www.esdnl.ca/statuscentral/images/scroll_backward_on.gif';">
          </a>
    </div>
    <div id="p7sc1Forward" 
         style="position:absolute; left:139px; top:178px; width:24px; height:24px; z-index:103;">
          <a href="javascript:;" 
             onMouseDown="myScroller1.forward(); reconcilImages(5); return false;" 
             onClick="return false" 
             onFocus="if(this.blur)this.blur()">
             <img src="http://www.esdnl.ca/statuscentral/images/forward_off.gif" 
                  width="24" height="24" border="0" alt="Scroll forward one"
                  onmouseover="this.src='http://www.esdnl.ca/statuscentral/images/forward_on.gif';"
                  onmouseout="this.src='http://www.esdnl.ca/statuscentral/images/forward_off.gif';">
          </a>
    </div>
  </body>
</html>