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
<script type="text/javascript">
$(function(){
	$('.pledgeImageAll').each(function(){
			
		if($(this).attr('data-pledges') >4  && $(this).attr('data-pledges') <=9 ){
			$(this).css({'border-color' : '#cd7f32','border-width' : '2px'});	
			$(this).children('.numa').css({'color':'Black'});
		}	
		
		else if($(this).attr('data-pledges') >9  && $(this).attr('data-pledges') <=24){
			$(this).css({'border-color' : 'Silver','border-width' : '2px'});	
			$(this).children('.numa').css({'color':'Black'});
		}	
		else if($(this).attr('data-pledges') >24 && $(this).attr('data-pledges') <=49){
			$(this).css({'border-color' : '#ffd700','border-width' : '2px'});	
			$(this).children('.numa').css({'color':'Black'});
		}	
		else if($(this).attr('data-pledges') >49 && $(this).attr('data-pledges') <=99 ){
			$(this).css({'border-color' : 'Red','border-width' : '2px'});	
			$(this).children('.numa').css({'color':'Black'});
		}	
		else if($(this).attr('data-pledges') >100){
			$(this).css({'border-color' : '#ff00fc','border-width' : '4px'});	
			$(this).children('.numa').css({'color':'ff00fc'});
		}	
		else {
			$(this).css({'border-color' : '#E3F1E6','border-width' : '2px'});
			$(this).children('.numa').css({'color':'black'});
		}	
		
	});
});
</script>
<c:set var='pledges' value='<%=pledges%>' />
<c:set var="count" value="0" scope="page" />
		<!-- <div class="pledgeAreaTitle">All Schools and Total Pledges</div>-->
		
				<div align=center>
								
				
				<c:forEach items="${pledges}" var="pledge" varStatus="loopCounter">
						
				     <div class="pledgeImageAll" data-pledges='${pledge.totalPledges}'>	
            			<span class="numa" style="font-weight:bold;font-size:12px;">${pledge.totalPledgesConfirmed}</span><br/>            			      		
            			<a href="https://www.nlesd.ca/schools/schoolprofile.jsp?id=${pledge.schoolPicture}">            			
            			<img src="includes/img/${pledge.schoolPicture}.jpg" title="${pledge.schoolName} (${pledge.totalPledges} Pledges)" style="max-width:90%;height:auto;max-height:60px;" onError="this.onerror=null;this.src='includes/img/default.jpg';">
            			<br/>${pledge.schoolName}</a>
            		</div>  
            		
	            	  <c:set var="count" value="${count + 1}" scope="page"/>        	
                </c:forEach>	
				
				<c:if test="${count < 20}">
					<p><div class="quotes allPledges"><br/><SCRIPT src="includes/quotes.js"></script></div>	
					<p><div class="quotes allPledges"><br/><SCRIPT src="includes/quotes.js"></script></div>	
				</c:if>
				
				</div>			
					
						