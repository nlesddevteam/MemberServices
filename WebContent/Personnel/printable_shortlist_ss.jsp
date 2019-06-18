<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.awsd.security.*,
                  com.awsd.school.*,
                  com.awsd.school.bean.*,
                  com.awsd.mail.bean.AlertBean,
                  com.esdnl.util.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.awsd.security.crypto.*,
                 com.esdnl.personnel.v2.model.sds.bean.*,
                 com.esdnl.personnel.v2.database.sds.*" 
         isThreadSafe="false"%>
	
		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
	JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
	ApplicantProfileBean[] applicants = null;

	if (session.getAttribute("JOB_SHORTLIST") instanceof ApplicantProfileBean[])
		applicants = (ApplicantProfileBean[]) session
				.getAttribute("JOB_SHORTLIST");
	else if (session.getAttribute("JOB_SHORTLIST") instanceof TreeMap) {
		TreeMap<String, TreeMap<Integer, Vector<ApplicantProfileBean>>> regions = (TreeMap<String, TreeMap<Integer, Vector<ApplicantProfileBean>>>) session
				.getAttribute("JOB_SHORTLIST");

		TreeSet<ApplicantProfileBean> appls = new TreeSet<ApplicantProfileBean>();

		for (Map.Entry<String, TreeMap<Integer, Vector<ApplicantProfileBean>>> r : regions
				.entrySet()) {
			for (Map.Entry<Integer, Vector<ApplicantProfileBean>> d : r
					.getValue().entrySet()) {
				appls.addAll(d.getValue());
			}
		}

		applicants = appls.toArray(new ApplicantProfileBean[0]);
	}
	SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

%>
<html>
<head>
<title>
  Competition # <%=job.getCompetitionNumber()%> Short List
</title>
<style>
@media print {		
				
				.content{
					font-family:verdana,sans-serif;
  				font-size:9.5px;
  				margin: auto;
  				width:650px;
					min-height: 675px;
				}
				.mainFooter{display:none;}
				#noPrintThis2 {display:none;}
				#noPrintThis{display:none;}
				#empTable1{font-size:9.5px;}
		 		table { page-break-inside:auto;border:none !important; }
   				tr    { page-break-inside:avoid; page-break-after:auto;border:none !important; }
   				td {border:none !important;}
		
		}
  		.pageBreak {
				page-break-after: always;
			}
			
			.tableTitle {font-weight:bold;width:15%;}
.tableResult {font-weight:normal;width:85%;}
.tableQuestion {font-weight:normal;width:70%;}
.tableAnswer {font-weight:normal;width:30%;}
.tableTitleL {font-weight:bold;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;width:15%;}
.tableResultR {font-weight:normal;width:35%;}

input {border:1px solid silver;}	
			
		</style>
</head>
<body>
<%if(applicants.length > 0) { %>
  <div align="center"><img src="includes/img/nlesd-colorlogo.png" width="400"></div><br/><br/>

 <%for(int j=0; j < applicants.length; j++){
	  pageContext.setAttribute("APPLICANT", applicants[j], PageContext.SESSION_SCOPE);
  	%>
	<jsp:include page="printable_applicant_profile_ss.jsp">
		<jsp:param name="sin" value="<%=applicants[j].getSIN()%>"/>
	</jsp:include>
	
  	<DIV style="page-break-after:always"></DIV>
  	
  <%}%>
  
 		<script>
			$('document').ready(function(){
	      window.print();
	      });
			
		</script>
					<%} else {%>
                        <br/><br/>
                        <div class="alert alert-danger" align="center">Sorry, no applicants currently shortlisted to print.</div>
                        <br/><br/>
                        <%} %>
</body>
</html>
