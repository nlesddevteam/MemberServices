package com.esdnl.fund3.handler;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.esdnl.fund3.bean.CustomReportBean;
import com.esdnl.fund3.dao.CustomReportManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class RunCustomReportRequestHandler extends RequestHandlerImpl {
	public RunCustomReportRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
				if(form.exists("reportid")){
					//get sql from db
					String reportsql = CustomReportManager.getReportsSql(form.getInt("reportid"));
					CustomReportBean crb = CustomReportManager.getCustomReportById(form.getInt("reportid"));
					List<Map<String, Object>> testing = CustomReportManager.runCustomReport(reportsql);
					//update report ran
					CustomReportManager.updateReportDateLastUsed(form.getInt("reportid"));
					request.setAttribute("rows", testing);
					request.setAttribute("reportname", crb.getReportTitle() );
					path = "custom_report_results.jsp";
				}else{
					List<Map<String, Object>> testing;
					StringBuilder sbselect = new StringBuilder();
					StringBuilder sbwhere = new StringBuilder();
					StringBuilder sbfrom = new StringBuilder();
					sbselect.append("SELECT fp.PROJECT_ID as ID,fp.PROJECT_NUMBER as \"PROJECT NUMBER \" ");
					sbfrom.append(" FROM FUND3_PROJECT fp ");
					
					boolean usedfunding=false;
					boolean usedemployee=false;
					boolean usedsds=false;
					//now we check the custom fields and build the sql
					if(!(form.get("chkprojectname") == null)){
						sbselect.append(",fp.PROJECT_NAME as \"PROJECT NAME\"");
						//now we add the where clause
						if(form.getInt("lstproject") > 0){
							if(sbwhere.length() == 0){
								sbwhere.append("WHERE fp.PROJECT_ID=" + form.getInt("lstproject"));
							}else{
								sbwhere.append("AND fp.PROJECT_ID=" + form.getInt("lstproject"));
							}
						}
					}
					if(!(form.get("chkaccountnumber") == null)){
						sbselect.append(",fp.PROJECT_NUMBER as \"PROJECT NUMBER \"");
						if(!(form.get("txtaccountnumber").isEmpty())){
							if(sbwhere.length() == 0){
								sbwhere.append("WHERE fp.PROJECT_NUMBER='" + form.get("PROJECT_NUMBER") + "'");
							}else{
								sbwhere.append(" AND " + "fp.PROJECT_NUMBER='" + form.get("PROJECT_NUMBER") + "'");
							}
						}
					}
					//from sds, we save if the field is used and build the sql last
					if(!(form.get("chkfunding") == null)){
						usedsds=true;
					}
					if(!(form.get("chkexpended") == null)){
						usedsds=true;
					}
					if(!(form.get("chkencumbered") == null)){
						usedsds=true;
					}				
					if(!(form.get("chkbudget") == null)){
						usedsds=true;
					}				
					if(!(form.get("chkbalance") == null)){
						usedsds=true;
					}
					//from FUND3_PROJECT_REGION
					if(!(form.get("chkregion") == null)){
						sbselect.append(",fpr.BUDGET_AMOUNT as \"REGION BUDGET \"");
						sbselect.append(",(select dd_text from FUND3_DROPDOWN_ITEMS where ID=fpr.REGION_ID) as REGION");
						sbfrom.append(" inner join FUND3_PROJECT_REGION fpr on fp.PROJECT_ID=fpr.PROJECT_ID");
						//now we check to see if any regions are to be filtered
						String regions[] = form.getArray("lstregion");
						if(regions != null){
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE fpr.REGION_ID in (" + StringUtils.join(regions,",") + ")");
							}else{
								sbwhere.append(" AND fpr.REGION_ID in (" + StringUtils.join(regions,",") + ")");
							}
							
						}
						
					}
					//from FUND3_PROJECT_SCHOOL
					if(!(form.get("chkschool") == null)){
						sbselect.append(",fps.BUDGET_AMOUNT as \"SCHOOL BUDGET \"");
						sbselect.append(",(select school_name from SCHOOL where SCHOOL_ID=fps.SCHOOL_ID) as SCHOOL");
						sbfrom.append(" inner join FUND3_PROJECT_SCHOOL fps on fp.PROJECT_ID=fps.PROJECT_ID");
						//add fields for sds
						if(usedsds){
							sbselect.append(",ss.SCHOOL_NAME,ss.DEPT_ID");
							sbfrom.append(" inner join SCHOOL ss on fps.SCHOOL_ID=ss.SCHOOL_ID");
						}
						//now we check to see if any schools are to be filtered
						String schools[] = form.getArray("lstschools");
						if(schools != null){
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE fps.SCHOOL_ID in (" + StringUtils.join(schools,",") + ")");
							}else{
								sbwhere.append(" AND fps.SCHOOL_ID in (" + StringUtils.join(schools,",") + ")");
							}
							
						}
					}
					if(!(form.get("chkfundingp") == null)){
						//sbselect.append(",fpf.FUNDING_ID");
						sbselect.append(",(select dd_text from FUND3_DROPDOWN_ITEMS where ID=fpf.funding_id) as \"FUNDING PARTNER \"");
						sbfrom.append(" inner join FUND3_PROJECT_FUNDING fpf on fp.PROJECT_ID=fpf.PROJECT_ID");
						usedfunding=true;
						//now we check to see if any funding partners are to be filtered
						String partners[] = form.getArray("lstfunding");
						if(partners != null){
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE fpf.FUNDING_ID in (" + StringUtils.join(partners,",") + ")");
							}else{
								sbwhere.append(" AND fpf.FUNDING_ID in (" + StringUtils.join(partners,",") + ")");
							}
							
						}
					}
					if(!(form.get("chksponsor") == null)){
						sbselect.append(",fpf.CONTACT_NAME as \"CONTACT NAME\",fpf.CONTACT_EMAIL as \"CONTACT EMAIL \",fpf.CONTACT_PHONE as \"CONTACT PHONE\"");
						if(! usedfunding){
							sbfrom.append(" inner join FUND3_PROJECT_FUNDING fpf on fp.PROJECT_ID=fpf.PROJECT_ID");
						}
						if(!(form.get("txtsponsor").isEmpty())){
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE upper(fpf.CONTACT_NAME) like'%" + form.get("txtsponsor").toUpperCase() + "%'");
							}else{
								sbwhere.append(" AND upper(fpf.CONTACT_NAME) like'%" + form.get("txtsponsor").toUpperCase() + "%'");
							}
						}
					}
					//from project
					if(!(form.get("chkdaterange") == null)){
						sbselect.append(",fp.DATE_FUNDING_APPROVED as \"DATE FUNDING APPROVED\"");
						if(!(form.get("daterangestart").isEmpty()) && !(form.get("daterangeend").isEmpty())){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date startdate = sdf.parse(form.get("daterangestart"));
							Date enddate = sdf.parse(form.get("daterangeend"));
							sdf.applyPattern("yy-MM-dd");
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE fp.DATE_FUNDING_APPROVED between '" + sdf.format(startdate).toString() + "' and '" + sdf.format(enddate).toString() +"'");
							}else{
								sbwhere.append(" WHERE fp.DATE_FUNDING_APPROVED between " + sdf.format(startdate).toString() + " and " + sdf.format(enddate).toString() + "'");
							}						
						}
					}
					if(!(form.get("chkstartdate") == null)){
						sbselect.append(",fp.PROJECT_START_DATE as \"PROJECT START DATE\"");
					}
					if(!(form.get("chkenddate") == null)){
						sbselect.append(",fp.PROJECT_END_DATE as \"PROJECT END DATE\"");
					}
					if(!(form.get("chkcategory") == null)){
						sbselect.append(",(select dd_text from FUND3_DROPDOWN_ITEMS where ID=fp.PROJECT_CATEGORY) as CATEGORY");
						//now we check to see if any categories are to be filtered
						String categories[] = form.getArray("lstcategory");
						if(categories != null){
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE fp.PROJECT_CATEGORY in (" + StringUtils.join(categories,",") + ")");
							}else{
								sbwhere.append(" AND fp.PROJECT_CATEGORY in (" + StringUtils.join(categories,",") + ")");
							}
							
						}
					}
					if(!(form.get("chkposition") == null)){
						sbselect.append(",(select dd_text from FUND3_DROPDOWN_ITEMS where ID=fp.POSITION_RESPONSIBLE) as \"POSITION RESPONSIBLE\"");
						//now we check to see if any positions are to be filtered
						String positions[] = form.getArray("lstposition");
						if(positions != null){
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE fp.POSITION_RESPONSIBLE in (" + StringUtils.join(positions,",") + ")");
							}else{
								sbwhere.append(" AND fp.POSITION_RESPONSIBLE in (" + StringUtils.join(positions,",") + ")");
							}
							
						}
					}
					//from project employee res
					if(!(form.get("chkemployee") == null)){
						sbselect.append(",fper.EMPLOYEE_NAME as \"EMPLOYEE NAME\"");
						sbselect.append(",(select dd_text from FUND3_DROPDOWN_ITEMS where ID=fper.region_id) as \"EMP REGION\"");
						sbfrom.append(" inner join FUND3_PROJECT_EMPLOYEES_RES fper on fp.PROJECT_ID=fper.PROJECT_ID");
						usedemployee=true;
						if(!(form.get("txtemployee").isEmpty())){
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE upper(fper.EMPLOYEE_NAME) like'%" + form.get("txtemployee").toUpperCase() + "%'");
							}else{
								sbwhere.append(" AND upper(fper.EMPLOYEE_NAME) like'%" + form.get("txtemployee").toUpperCase() + "%'");
							}
						}					
					}
					if(!(form.get("chkemployeeemail")== null)){
						sbselect.append(",fper.EMPLOYEE_EMAIL  as \"EMPLOYEE EMAIL\"");
						if(! usedemployee){
							sbfrom.append(" inner join FUND3_PROJECT_EMPLOYEES_RES fper on fp.PROJECT_ID=fper.PROJECT_ID");
							usedemployee=true;
						}
					}
					if(!(form.get("chkemployeetelephone") == null)){
						sbselect.append(",fper.EMPLOYEE_PHONE  as \"EMPLOYEE PHONE\"");
						if(! usedemployee){
							sbfrom.append(" inner join FUND3_PROJECT_EMPLOYEES_RES fper on fp.PROJECT_ID=fper.PROJECT_ID");
							usedemployee=true;
						}
					}
					//from project
					if(!(form.get("chkdescription") == null)){
						sbselect.append(",fp.PROJECT_DESCRIPTION  as \"PROJECT DESCRIPTION\"");
						if(!(form.get("txtdescription").isEmpty())){
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE upper(fp.PROJECT_DESCRIPTION) like'%" + form.get("txtdescription").toUpperCase() + "%'");
							}else{
								sbwhere.append(" AND upper(fp.PROJECT_DESCRIPTION) like'%" + form.get("txtdescription").toUpperCase() + "%'");
							}
						}	
					}
					if(!(form.get("chkrequirements") == null)){
						sbselect.append(",fp.SPECIAL_REQUIREMENTS  as \"SPECIAL REQUIREMENTS\"");
						if(!(form.get("txtspecial").isEmpty())){
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE upper(fp.SPECIAL_REQUIREMENTS) like'%" + form.get("txtspecial").toUpperCase() + "%'");
							}else{
								sbwhere.append(" AND upper(fp.SPECIAL_REQUIREMENTS) like'%" + form.get("txtspecial").toUpperCase() + "%'");
							}
						}
					}
					if(!(form.get("chkcriteria") == null)){
						sbselect.append(",fp.REPORT_REQUESTED  as \"REPORT REQUESTED\",fp.FIRST_REPORT_DATE  as \"FIRST REPORT DATE\",fp.REPORT_EMAIL  as \"EMPLOYEE EMAIL\"");
						sbselect.append(",(select dd_text from FUND3_DROPDOWN_ITEMS where ID=fp.REPORT_FREQUENCY) as freqtext");
					}
					if(!(form.get("chkstatus") == null)){
						sbselect.append(",fp.IS_ACTIVE  as \"IS ACTIVE\"");
						if(form.getInt("isactive") == 1){
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE fp.IS_ACTIVE = 1");
							}else{
								sbwhere.append(" AND fp.IS_ACTIVE = 1");
							}
						}else{
							if(sbwhere.length() == 0){
								sbwhere.append(" WHERE fp.IS_ACTIVE = 0");
							}else{
								sbwhere.append(" AND fp.IS_ACTIVE = 0");
							}
						}
							
					}
					//now we add the select statments for sds
					if(usedsds){
						StringBuilder sbsds = new StringBuilder();
						StringBuilder finalsql = new StringBuilder();
						if(!(form.get("chkregion") == null)){
							sbsds.append("SELECT FUND,ACCOUNT, ss.ZONE_ID ");
							if(!(form.get("chkexpended") == null)){
								sbsds.append(",sum(NEXPENDITURE) \"Next Expend\" ,sum(CEXPENDITURE) \"Current Expend\",sum(PEXPENDITURE) \"Previous Expend\"");
							}
							if(!(form.get("chkencumbered") == null)){
								sbsds.append(",sum(NENCUMBERED) \"Next Encumber\",sum(CENCUMBERED) \"Current Encumber\",sum(PENCUMBERED)  \"Previous Encumber\"");
							}
							if(!(form.get("chkbudget") == null)){
								sbsds.append(",sum(NBUDGET) \"Next Budget\",sum(CBUDGET) \"Current Budget\",sum(PBUDGET) \"Previous Budget\"");
							}
							if(!(form.get("chkbalance") == null)){
								sbsds.append(",sum(NBALANCE) \"Next Balanace\",sum(CBALANCE) \"Current Balance\",sum(PBALANCE) \"Previous Balance\"");
							}
							sbsds.append(" from FUND3_ACGLMMAS ");
							sbsds.append(" inner join SCHOOL ss on ss.DEPT_ID=FUND3_ACGLMMAS.COSTCENTER ");
							sbsds.append(" group by FUND,ACCOUNT,ss.ZONE_ID");
							finalsql.append("select * from(");
							finalsql.append(sbselect.toString() + ",fpr.REGION_ID ");
							finalsql.append(sbfrom.toString() + " ");
							finalsql.append(sbwhere.toString());
							finalsql.append(") testing inner join (");
							finalsql.append(sbsds);
							finalsql.append(") fdata on testing.\"PROJECT NUMBER \" = fdata.ACCOUNT and testing.REGION_ID=fdata.ZONE_ID");
							testing = CustomReportManager.runCustomReport(finalsql.toString());
						}else if(!(form.get("chkschool") == null)){
							finalsql.append("SELECT * FROM ( ");
							finalsql.append(sbselect.toString() + " ");
							finalsql.append(sbfrom.toString() + " ");
							finalsql.append(sbwhere.toString() + " ");
							finalsql.append(") testing inner join FUND3_ACGLMMAS fa on testing.\"PROJECT NUMBER \"=fa.ACCOUNT and fa.COSTCENTER=testing.DEPT_ID");
							testing = CustomReportManager.runCustomReport(finalsql.toString());
						}else{
							sbsds.append("SELECT ACCOUNT ");
							if(!(form.get("chkexpended") == null)){
								sbsds.append(",sum(NEXPENDITURE) \"Next Expend\" ,sum(CEXPENDITURE) \"Current Expend\",sum(PEXPENDITURE) \"Previous Expend\"");
							}
							if(!(form.get("chkencumbered") == null)){
								sbsds.append(",sum(NENCUMBERED) \"Next Encumber\",sum(CENCUMBERED) \"Current Encumber\",sum(PENCUMBERED)  \"Previous Encumber\"");
							}
							if(!(form.get("chkbudget") == null)){
								sbsds.append(",sum(NBUDGET) \"Next Budget\",sum(CBUDGET) \"Current Budget\",sum(PBUDGET) \"Previous Budget\"");
							}
							if(!(form.get("chkbalance") == null)){
								sbsds.append(",sum(NBALANCE) \"Next Balanace\",sum(CBALANCE) \"Current Balance\",sum(PBALANCE) \"Previous Balance\"");
							}
							sbsds.append(" from FUND3_ACGLMMAS group by FUND,ACCOUNT ");
							finalsql.append("select * from(");
							finalsql.append(sbselect.toString() + " ");
							finalsql.append(sbfrom.toString() + " ");
							finalsql.append(sbwhere.toString());
							finalsql.append(") testing inner join (");
							finalsql.append(sbsds);
							finalsql.append(") fdata on fdata.ACCOUNT = testing.\"PROJECT NUMBER \"");
							testing = CustomReportManager.runCustomReport(finalsql.toString());
						}
						
					}else{
						//now we save the sql
						testing = CustomReportManager.runCustomReport(sbselect.toString() + sbfrom.toString() + sbwhere.toString());
					}
					
					request.setAttribute("rows", testing);
					path = "custom_report_results.jsp";
					
				}
				
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "custom_report_results.jsp";
		}
		return path;
	}


}