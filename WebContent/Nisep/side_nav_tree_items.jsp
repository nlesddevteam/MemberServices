<%@ page language="java"
         session="true"
         import="java.util.*,com.awsd.security.*,
                 com.esdnl.nicep.beans.*,
                 com.esdnl.nicep.dao.*"%>
<%
  User usr = null;
  
  usr = (User) session.getAttribute("usr");
  if(usr != null)
  {
    if(!(usr.getUserPermissions().containsKey("NISEP-VIEW")))
    {
%>    <jsp:forward page="/MemberServices/memberServices.html"/>
<%  }
  }
  else
  {
%>  <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="Secure Resource!<br>Please Login."/>
    </jsp:forward>
<%}
  
  AgencyDemographicsBean[] agencies = AgencyDemographicsManager.getAgencyDemographicsBeans();
  ArrayList[] students = StudentDemographicsManager.getStudentDemographicsBeans();
%>

<script language="JavaScript">
  var AGENCY_ITEMS = 
  [
      ['Agencies', null,
        ['<span style=color:#FF0000;>Add Agency</span>', 'addAgency.html', 
        ],
        <%for(int i=0; i < agencies.length; i++){%>
          ['<%=agencies[i].getName()%>','viewAgency.html?id=<%=agencies[i].getAgencyId()%>',
            ['View Students', 'viewAgencyStudents.html?id=<%=agencies[i].getAgencyId()%>',
            ],
          ],
        <%}%>
      ],
  ];
  
  var STUDENT_ITEMS = 
  [
      ['Students', null,
        ['<span style=color:#FF0000;>Add Student</span>', 'addStudent.html', 
        ],
        <%for(char l = 'A'; l <= 'Z'; l++){
            if(students[l - 'A'] != null){%>
              ['<span style=color:#FF0000;><%=l%></span>', null, 
              <%for(int i=0; i < students[l - 'A'].size(); i++){%>
                ['<%=((StudentDemographicsBean)students[l-'A'].get(i)).getFullname()%>','viewStudent.html?id=<%=((StudentDemographicsBean)students[l-'A'].get(i)).getStudentId()%>',
                  ['School History', 'viewStudentSchoolHistory.html?id=<%=((StudentDemographicsBean)students[l-'A'].get(i)).getStudentId()%>',
                  ]
                ],
              <%}%>
              ],
          <%}
         }%>
      ],
  ];
  
  var COORDINATOR_ITEMS = 
  [
      ['Coordinators', null,
        ['<span style=color:#FF0000;>Add Coordinator</span>', 'addCoordinator.html', 
        ],
      ],
  ];
  
  var HOSTFAMILY_ITEMS = 
  [
      ['Host Families', null,
        ['<span style=color:#FF0000;>Add Host Family</span>', 'addHostFamily.html', 
        ],
      ],
  ];
  
</script>
