<%@ page language="java"
         session="true"
         import="java.util.*, 
                 java.text.*,com.awsd.security.*,com.awsd.school.*,
                 com.esdnl.student.travel.bean.*,
                 com.esdnl.student.travel.dao.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="STUDENT-TRAVEL-ADMIN-VIEW,STUDENT-TRAVEL-PRINCIPAL-VIEW" />

<%
  User usr = (User) session.getAttribute("usr");
    
  boolean isPrincipal = usr.checkPermission("STUDENT-TRAVEL-PRINCIPAL-VIEW");
  boolean isSEO = usr.checkRole("SENIOR EDUCATION OFFICIER");
  
  TravelRequestBean[] reqs = null;
  
  if(isPrincipal)
    reqs = TravelRequestManager.getTravelRequestBeans(usr.getPersonnel().getSchool(), true);
  else if(isSEO)
  	reqs = TravelRequestManager.getTravelRequestBeans(SchoolFamilyDB.getSchoolFamily(usr.getPersonnel()));
  else
    reqs = TravelRequestManager.getTravelRequestBeans(true);
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  
  School s = null;
%>

<script language="JavaScript">
  var REQUEST_ITEMS = 
  [
      ['<span class=displayPageTitle>Requests</span>', null,
        
        <%if(reqs.length > 0){
          for(int i=0; i < reqs.length; i++){
            if((s == null) || (s.getSchoolID() != reqs[i].getSchoolId())){
              if(s!= null){%>
                ],
          <%}
              s = SchoolDB.getSchool(reqs[i].getSchoolId());%>
            ['<%=s.getSchoolName().replaceAll("'", "&#39;")%>',null,
          <%}%>
            ['<%=reqs[i].getDestination().replaceAll("'", "&#39;").replace("&", "&amp;") + " (" + sdf.format(reqs[i].getDepartureDate()) + ")"%>','viewRequest.html?id=<%=reqs[i].getRequestId()%>',
            ],
        <%}%>
          ],
        <%}else{%>
          ['<span style=color:#FF0000;>No Current Requests</span>', null, 
          ],
        <%}%>
      ],
  ];
  
  
  
</script>
