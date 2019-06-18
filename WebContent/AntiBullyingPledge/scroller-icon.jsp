		<%@ page language ="java" 
         session = "true"
         import = "java.util.*,java.text.DateFormat,
         					 com.nlesd.antibullyingpledge.bean.*,
         					 com.nlesd.antibullyingpledge.dao.*,com.esdnl.util.*"
         isThreadSafe="false"%>
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%	List<AntiBullyingPledgeSchoolTotalsBean> pledges = AntiBullyingPledgeManager.getAllSchoolTotals(); %>

<!DOCTYPE html>
<html lang="en">
  <head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <meta name="dcterms.created" content="Tue, 27 Jan 2015 12:42:59 GMT">
    <meta name="description" content="">
    <meta name="keywords" content="">	
	 <link rel="stylesheet" href="https://www.nlesd.ca/includes/css/jquery-ui-1.10.3.custom.css" >
	 <link rel="stylesheet" href="includes/bullyapp.css">
		<script src="https://www.nlesd.ca/includes/js/jquery-1.7.2.min.js"></script>
		<script src="https://www.nlesd.ca/includes/js/jquery-1.9.1.js"></script>
		<script src="https://www.nlesd.ca/includes/js/jquery-ui-1.10.3.custom.js"></script>
		
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
	 	</script>
<style>
div.scrollWrapper {position: relative;overflow: hidden;width: 100%;height: 100%;}
div.scrollableArea {position: relative;width:auto;height:100%;}
#makeMeScrollable	{width:100%;height:100%;position: relative;}
#makeMeScrollable div.scrollableArea{position: relative;overflow:hidden;float: left;margin:0;padding:0;-webkit-user-select:none;-khtml-user-select:none;-moz-user-select: none;	-o-user-select: none;user-select: none;	}
</style>	

	<title>NLESD Anti-Bullying Pledge</title>
    
  </head>
  <body> 
  
<c:set var='pledges' value='<%=pledges%>' />	
	
		
				<div id="makeMeScrollable" style="background-color:White;">							
				<c:forEach items="${pledges}" var="pledge" varStatus="loopCounter">
				<div style="float:left;width:85px;height:auto;background-color:White;">
					<div align="center"><span class="PledgeNum" style="font-weight:bold;font-size:12px;">${pledge.totalPledgesConfirmed}</span><br/>  
                 <img src="includes/img/${pledge.schoolPicture}.jpg" title="${pledge.schoolName} (${pledge.totalPledges} Pledges)" style="max-width:90%;height:auto;max-height:60px;" onError="this.onerror=null;this.src='includes/img/default.jpg';"></a> 
            		</div>
            	</div> 
            	  
            	     		           	
                </c:forEach>	
				
				</div>	   		

			<script src="includes/js/jquery.mousewheel.min.js" type="text/javascript"></script>
			<script src="includes/js/jquery.smoothdivscroll-1.3-min.js" type="text/javascript"></script>
			<script type="text/javascript">		
					$(document).ready(function () {			
						$("div#makeMeScrollable").smoothDivScroll({
						autoScrollingMode: "always"				
						});
					});
			</script>	

  </body>
</html>