<%@ page language="java"
         session="true"
         import="java.util.*,
                 java.text.*,
                 com.awsd.security.*,
                 com.esdnl.complaint.model.bean.*,
                 com.esdnl.complaint.model.constant.*,
                 com.esdnl.complaint.database.*,
                 com.esdnl.util.*"
         isThreadSafe="false"%>
<% 
  User usr = (User) session.getAttribute("usr");
  TreeMap map = null;
  
  if(usr.getUserPermissions().containsKey("COMPLAINT-MONITOR")
      || usr.getUserPermissions().containsKey("COMPLAINT-ADMIN"))
    map = ComplaintManager.getComplaintBeans2(false);
  else
    map = ComplaintManager.getComplaintBeans2(usr.getPersonnel(), false);
    
  TreeSet s = null;
  ComplaintBean c = null;
  Iterator iter = null;
  Iterator years = null;
  Map.Entry entry = null;
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>

<script language="JavaScript">
  var TREE_ITEMS = 
  [
    ['<SPAN class=side_nav_head>Complaints</SPAN>', null, 
      <%years = map.entrySet().iterator();
    		while(years.hasNext()){
    			entry = (Map.Entry) years.next();%>
    			['<%=(String)entry.getKey()%>', null,
	      	<%for(int i=0; i < ComplaintStatus.ALL.length; i++){
	        	s = (TreeSet)((HashMap)entry.getValue()).get(ComplaintStatus.ALL[i]);%>
	        	<%if(s != null && s.size() > 0){%>
	          ['<%=ComplaintStatus.ALL[i].getDescription()%> (<%=s.size()%>)', null,
	            <%iter = s.iterator();
	              while(iter.hasNext()){
	                c = (ComplaintBean) iter.next();%>
	                ['<%=sdf.format(c.getDateSubmitted())%> (<SPAN class=small_text><%=StringUtils.encodeHTML(c.getFullName())%></SPAN>)','viewAdminComplaintSummary.html?id=<%=c.getId()%>',
	                ],
	            <%}%>
	          ],
	        	<%}%>
	      	<%}%>
	      	],
	    <%}%>
    ],
  ];
</script>
