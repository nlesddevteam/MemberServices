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
	Integer totalpledges = AntiBullyingPledgeManager.getTotalPledges();
	Integer totalpledgesconfirmed = AntiBullyingPledgeManager.getTotalPledgesConfirmed();

%>

<c:set var='pledges' value='<%=pledges%>' />
<c:set var='totalpledges' value='<%=totalpledges%>' />
<c:set var='totalpledgesconfirmed' value='<%=totalpledgesconfirmed%>' />
		
<div class="pledgeTotal">${totalpledgesconfirmed} Pledges!</div>
				
				
				
					
						