<%@ page language="java"
         session="true"
         import="java.util.*,com.awsd.security.*"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck />

<%
  User usr = null;
  Permissions permissions = null;
  Permission p = null;
  
  Roles roles = null;
  Role r = null;
  
  Iterator<Permission> piter = null;
  Iterator<Role> riter = null;

  usr = (User) session.getAttribute("usr");
  
  permissions = new Permissions();
  roles = new Roles();
%>
<script language="JavaScript">
  var TREE_ITEMS = 
  [
    ['Member Services', null, 
      ['Applications', 'viewNextLoginApp.html',
        //['District Calendar',null,
       //   ['Legend', 'viewCalendarLegend.html', ],
       // ],
        //['Financial Package','Apps/Financial/financialReports.jsp',
       // ],
       	['Account Administration', 'Apps/Personnel/personnel_admin_view.jsp',
       		['<span style=\"color:red;\">Add New Account</span>', '/MemberServices/register.jsp', ],
        ],
        ['AntiBullying Pledge Administration', '',
         ['Todays Pledges', 'Apps/AntiBullyingPledge/index.jsp',],
         ['View All Pledges', 'Apps/AntiBullyingPledge/view_all_pledges.jsp',],
      	['Search Pledges', 'Apps/AntiBullyingPledge/search_pledges.jsp',],
      	['Reports', 'Apps/AntiBullyingPledge/reports.jsp',],
       	
     	],
        ['School Administration', '', 
         	['School Directory', 'Apps/Schools/school_directory.jsp', ],
          ['School Admin Summary', 'Apps/Schools/school_admin_view.jsp', ],
          ['School Family Summary', 'Apps/Schools/schoolfamilyadmin.jsp', ],
          ['School System Admin', 'Apps/Schools/schoolsystemadmin.jsp', ],
        ],
        ['Staff Directory', '', 
	        ['Contact List', 'Apps/StaffDirectory/staff_directory.jsp', ],
	      ],
        //['Weather Central', 'Apps/WeatherCentral/weathercentralmainconfig.jsp', 
        ['Status Central', '',
          ['Closure Code Admin', 'Apps/WeatherCentral/closurestatusadmin.jsp', ],
          ['Current School Closures', '/MemberServices/WeatherCentral/regionalized_school_admin.jsp', ],
        ],
       // ['Strike Central', null, 
        //  ['School Strike Group Admin', 'Apps/StrikeCentral/strikegroupadmin.jsp', ],
       // ],
      ],
    
      ['Security', null,
          ['Permissions', 'addPermission.html?passthrough=true',
            <%
              piter = permissions.iterator(); 
              while(piter.hasNext()) 
              {
                p = (Permission) piter.next();
            %>  
              ['<%=p.getPermissionUID()%>', 'viewPermission.html?uid=<%=p.getPermissionUID()%>'],
            <%}%>
          ],
        
          ['Roles', 'addRole.html?passthrough=true',
            <%
              riter = (Iterator<Role>) roles.iterator();
              while(riter.hasNext()) 
              {
                r = (Role) riter.next();
                
                if(!usr.checkRole("ADMINISTRATOR") && 
                	(r.getRoleUID().equalsIgnoreCase("ADMINISTRATOR") || r.getRoleUID().equalsIgnoreCase("SUB ADMINISTRATOR")))
                	continue;
            %>
              ['<%=r.getRoleUID()%>', 'viewRole.html?uid=<%=r.getRoleUID()%>'],
            <%}%>
          ],							
      ],
    ],
  ];
</script>
