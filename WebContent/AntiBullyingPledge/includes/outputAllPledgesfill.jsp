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



<%
	
	List<AntiBullyingPledgeSchoolTotalsBean> pledges = AntiBullyingPledgeManager.getAllSchoolTotals();

%>



<c:set var='pledges' value='<%=pledges%>' />	
	
		
				<div id="makeMeScrollable" style="background-color:White;">							
				<c:forEach items="${pledges}" var="pledge" varStatus="loopCounter">
				<!--<div style="float:left;width:85px;height:auto;background-color:White;">
					<div align="center"><span class="PledgeNum" style="font-weight:bold;font-size:12px;">${pledge.totalPledgesConfirmed}</span><br/>  
                 <img src="includes/img/${pledge.schoolPicture}.jpg" title="${pledge.schoolName} (${pledge.totalPledges} Pledges)" style="max-width:90%;height:auto;max-height:60px;" onError="this.onerror=null;this.src='includes/img/default.jpg';"></a> 
            		</div>
            	</div>    -->  
            	<div style="float:left;height:auto;background-color:White;">
					<div align="center"><span class="PledgeNum" style="font-size:12px;color:black;">&nbsp;${pledge.schoolName} (${pledge.totalPledgesConfirmed})&nbsp;&middot;</span><br/>  
                 <!-- <img src="includes/img/${pledge.schoolPicture}.jpg" title="${pledge.schoolName} (${pledge.totalPledges} Pledges)" style="max-width:90%;height:auto;max-height:60px;" onError="this.onerror=null;this.src='includes/img/default.jpg';"></a> --> 
            		</div>
            	</div>   
            	     		           	
                </c:forEach>	
				
				</div>			
					
			