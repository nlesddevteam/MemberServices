<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
                 com.awsd.common.*,
                 com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 com.nlesd.bcs.bean.*"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%

	//BussingContractorBean bcbean = (BussingContractorBean) session.getAttribute("CONTRACTOR");
	session.setAttribute("CONTRACTOR",null) ;

%>
<script src="includes/js/menu.js"></script>
   <script>
   $(document).ready(function () {
    $('.menuBCSC').click(function () {
    	$("#loadingSpinner").css("display","inline");
    });
    
    $('.menuBCSC1').click(function () {
    	$("#loadingSpinner").css("display","inline").delay(3000).fadeOut();
    });
    
});
</script>
<div id="loadingSpinner" style="display:none;"><div id="spinner"><img src="includes/img/animated-bus.gif" width="200" border=0><br/>Transporting data, please wait...</div></div>
<div id="busingMenu" style="z-index:999;">

<ul>
     <li><a href="#" target="_blank"><i class="fa fa-fw fa-folder-open-o"></i> File</a>
        <ul>
           <li><a href='contractorMain.html' class="menuBCSC" onclick="closeMenu();"><i class="fa fa-fw fa-home"></i> Home</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewSystemDocuments.html');"><i class="fa fa-fw fa-files-o"></i> Policies & Procedures</a></li>
           <li><a href="#" class="menuBCSC1" title='Print this page (pre-formatted)' onclick="closeMenu();jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><i class="fa fa-fw fa-print"></i> Print</a></li>
           <li><a href="contractorLogin.html" class="menuBCSC"><i class="fa fa-fw fa-reply"></i> Exit</a></li>
        </ul>
     </li>
     <li><a href="#"><i class="fa fa-fw fa-users"></i> Employees</a>
        <ul>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('addNewEmployee.html?vid=-1');"><i class="fa fa-fw fa-plus"></i> Add New</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorEmployees.html?status=1');"><i class="fa fa-fw fa-clock-o"></i> Not Submitted</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorEmployees.html?status=6');"><i class="fa fa-fw fa-clock-o"></i> Pending Approval</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorEmployees.html?status=2');"><i class="fa fa-fw fa-check"></i> View Approved</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorEmployees.html?status=4');"><i class="fa fa-fw fa-ban"></i> View Suspended</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorEmployees.html?status=3');"><i class="fa fa-fw fa-close"></i> Not Approved</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorEmployees.html?status=7');"><i class="fa fa-fw fa-close"></i> Temporarily On Hold</a></li>
           <%if(bcbean.getBoardOwned().equals("Y")){ %>
           		<li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorEmployeesReg.html');"><i class="fa fa-fw fa-tripadvisor"></i> View Regional</a></li>
        	<%} %>
        	<li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorEmployees.html?status=-1');"><i class="fa fa-fw fa-tripadvisor"></i> View All</a></li>
        </ul>
     </li>
     <li><a href="#"><i class="fa fa-fw fa-bus"></i> Fleet</a>
        <ul>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('addNewVehicle.html?vid=-1');"><i class="fa fa-fw fa-plus"></i> Add New</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorVehicles.html?status=1');"><i class="fa fa-fw fa-clock-o"></i> Not Submitted</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorVehicles.html?status=6');"><i class="fa fa-fw fa-clock-o"></i> Pending Approval</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorVehicles.html?status=2');"><i class="fa fa-fw fa-check"></i> View Approved</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorVehicles.html?status=4');"><i class="fa fa-fw fa-ban"></i> View Suspended</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorVehicles.html?status=3');"><i class="fa fa-fw fa-close"></i> Not Approved</a></li>
            <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorVehicles.html?status=7');"><i class="fa fa-fw fa-close"></i> Temporarily On Hold</a></li>
            <%if(bcbean.getBoardOwned().equals("Y")){ %>
           		<li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewVehiclesEmployeesReg.html');"><i class="fa fa-fw fa-tripadvisor"></i> View Regional</a></li>
        	<%} %>
           
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorVehicles.html?status=-1');"><i class="fa fa-fw fa-tripadvisor"></i> View All</a></li>
        
        
        </ul>
     </li>
     <li><a href="#"><i class="fa fa-fw fa-file-text-o"></i> Contracts</a>
        <ul>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewMyContracts.html');"><i class="fa fa-fw fa-tripadvisor"></i> View Contracts</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewMyRoutes.html');"><i class="fa fa-fw fa-photo"></i> View Routes</a></li>
        </ul>
     </li>
     <li><a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
        <ul>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContactInfo.html');"><i class="fa fa-fw fa-envelope-o"></i> Contact Information</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewSecurityInfo.html');"><i class="fa fa-fw fa-lock"></i> Security Information</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewCompanyInfo.html');"><i class="fa fa-fw fa-fort-awesome"></i> Company Information</a></li>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewContractorDocuments.html');"><i class="fa fa-fw fa-file-pdf-o"></i> Documents</a></li>
        </ul>
     </li>
          <li><a href="#"><i class="fa fa-fw fa-user"></i> Reports</a>
        <ul>
           <li><a href="#" class="menuBCSC" onclick="closeMenu();loadMainDivPage('viewEmployeeDetailsCR.html');"><i class="fa fa-fw fa-envelope-o"></i> Employee Details</a></li>
        </ul>
     </li>
  </ul>


   
</div>
<p>