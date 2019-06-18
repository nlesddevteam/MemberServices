package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.AuditLogBean;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.bean.ProjectBean;
public class ProjectManager {
	public static Integer addNewProject(ProjectBean pb) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_fund3_project(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2,pb.getProjectName());
			if(pb.getDateFundingApproved() == null){
				stat.setTimestamp(3, null);
			}else{
				stat.setTimestamp(3, new Timestamp(pb.getDateFundingApproved().getTime()));
			}
			if(pb.getProjectStartDate() == null){
				stat.setTimestamp(4, null);
			}else{
				stat.setTimestamp(4, new Timestamp(pb.getProjectStartDate().getTime()));
			}
			if(pb.getProjectEndDate() == null){
				stat.setTimestamp(5, null);
			}else{
				stat.setTimestamp(5, new Timestamp(pb.getProjectEndDate().getTime()));
			}
			stat.setInt(6, pb.getProjectCategory());
			stat.setInt(7, pb.getPositionResponsible());
			stat.setString(8,pb.getEmployeeName());
			stat.setString(9,pb.getEmployeeEmail());
			stat.setString(10,pb.getEmployeePhone());
			stat.setString(11,pb.getProjectDescription());
			stat.setString(12,pb.getSpecialRequirements());
			stat.setInt(13, pb.getReportRequested());
			if(pb.getFirstReportDate() == null){
				stat.setTimestamp(14, null);
			}else{
				stat.setTimestamp(14, new Timestamp(pb.getFirstReportDate().getTime()));
			}
			stat.setInt(15, pb.getReportFrequency());
			stat.setString(16,pb.getReportEmail());
			stat.setString(17, pb.getAddedBy());
			stat.setInt(18, pb.getProjectStartDateTBD());
			stat.setInt(19, pb.getProjectEndDateTBD());
			stat.setString(20, pb.getProjectNumber());
			stat.setInt(21, pb.getProjectYear());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewProject(ProjectBean pb) " + e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return id;
	}
	public static void updateNewProject(ProjectBean pb) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.update_fund3_project(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1,pb.getProjectName());
			if(pb.getDateFundingApproved() == null){
				stat.setTimestamp(2, null);
			}else{
				stat.setTimestamp(2, new Timestamp(pb.getDateFundingApproved().getTime()));
			}
			if(pb.getProjectStartDate() == null){
				stat.setTimestamp(3, null);
			}else{
				stat.setTimestamp(3, new Timestamp(pb.getProjectStartDate().getTime()));
			}
			if(pb.getProjectEndDate() == null){
				stat.setTimestamp(4, null);
			}else{
				stat.setTimestamp(4, new Timestamp(pb.getProjectEndDate().getTime()));
			}
			stat.setInt(5, pb.getProjectCategory());
			stat.setInt(6, pb.getPositionResponsible());
			stat.setString(7,pb.getEmployeeName());
			stat.setString(8,pb.getEmployeeEmail());
			stat.setString(9,pb.getEmployeePhone());
			stat.setString(10,pb.getProjectDescription());
			stat.setString(11,pb.getSpecialRequirements());
			stat.setInt(12, pb.getReportRequested());
			if(pb.getFirstReportDate() == null){
				stat.setTimestamp(13, null);
			}else{
				stat.setTimestamp(13, new Timestamp(pb.getFirstReportDate().getTime()));
			}
			stat.setInt(14, pb.getReportFrequency());
			stat.setString(15,pb.getReportEmail());
			stat.setString(16, pb.getAddedBy());
			stat.setInt(17, pb.getProjectStartDateTBD());
			stat.setInt(18, pb.getProjectEndDateTBD());
			stat.setString(19, pb.getProjectNumber());
			stat.setInt(20, pb.getProjectYear());
			stat.setInt(21, pb.getIsActive());
			stat.setString(22, pb.getAvailabilityNotes());
			stat.setInt(23, pb.getProjectId());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateNewProject(ProjectBean pb) " + e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
	}	
	public static ProjectBean getProjectById(int id) throws Fund3Exception {
		ProjectBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_project_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				mm = createProjectBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectBean getProjectById(int id) " + e);
			throw new Fund3Exception("Can not extract Project from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return mm;
	}
	public static Vector<ProjectBean> getProjectsByName(String projectname) throws Fund3Exception {
		Vector<ProjectBean> projects = null;
		ProjectBean project = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			projects= new Vector<ProjectBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.search_project_by_name(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, projectname);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				project = createProjectBean(rs);
				projects.add(project);
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectManager getProjectsByName(String projectname) " + e);
			throw new Fund3Exception("Can not extract projects from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return projects;
	}
	public static Vector<ProjectBean> getProjectsByNumber(String projectnumber) throws Fund3Exception {
		Vector<ProjectBean> projects = null;
		ProjectBean project = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			projects= new Vector<ProjectBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.search_project_by_number(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, projectnumber);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				project = createProjectBean(rs);
				projects.add(project);
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectManager getProjectsByNumber(String projectnumber) " + e);
			throw new Fund3Exception("Can not extract projects from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return projects;
	}
	public static Vector<ProjectBean> getProjectsByNumberAll() throws Fund3Exception {
		Vector<ProjectBean> projects = null;
		ProjectBean project = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			projects= new Vector<ProjectBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.search_project_by_number_all; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				project = createProjectBean(rs);
				projects.add(project);
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectManager getProjectsByNumberAll() " + e);
			throw new Fund3Exception("Can not extract projects from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return projects;
	}	
	public static Vector<ProjectBean> getProjectsByOther(String[] regions,String[] categories,String[]positions,String[]fiscals,String inactive) throws Fund3Exception {
		Vector<ProjectBean> projects = null;
		ProjectBean project = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			projects= new Vector<ProjectBean>(5);
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			boolean usedwhere=false;
			sb.append("SELECT distinct p.*,dd.dd_text as prtext FROM AWSD_USER.FUND3_PROJECT p inner join FUND3_DROPDOWN_ITEMS dd on p.POSITION_RESPONSIBLE=dd.ID ");
			sb.append("inner join FUND3_PROJECT_REGION pr on p.PROJECT_ID=pr.PROJECT_ID where ");
			if(regions != null){
				sb.append("Region_ID in (" + StringUtils.join(regions,",") + ")");
				usedwhere=true;
			}
			if(categories != null){
				if(usedwhere){
					sb.append(" and PROJECT_CATEGORY in (" + StringUtils.join(categories,",") + ")");
				}else{
					sb.append(" PROJECT_CATEGORY in (" + StringUtils.join(categories,",") + ")");
					usedwhere=true;
				}
			}
			if(positions != null){
				if(usedwhere){
					sb.append(" and POSITION_RESPONSIBLE in (" + StringUtils.join(positions,",") + ")");
				}else{
					sb.append(" POSITION_RESPONSIBLE in (" + StringUtils.join(positions,",") + ")");
					usedwhere=true;
				}
			}
			if(fiscals != null){
				if(usedwhere){
					sb.append(" and PROJECT_YEAR in (" + StringUtils.join(fiscals,",") + ")");
				}else{
					sb.append(" PROJECT_YEAR in (" + StringUtils.join(fiscals,",") + ")");
					usedwhere=true;
				}
			}
			if(inactive != null){
				if(usedwhere){
					sb.append(" and p.IS_ACTIVE=1");
				}else{
					sb.append(" p.IS_ACTIVE=1");
				}
			}
				
			sb.append(" order by project_name");
			
			sb.insert(0,"SELECT * FROM (");
			sb.append(")  testing inner join (SELECT ACCOUNT ,sum(CBUDGET) \"Current Budget\",sum(CBALANCE) \"Current Balance\" from FUND3_ACGLMMAS group by FUND,ACCOUNT ) fdata on fdata.ACCOUNT = testing.project_number");
			System.out.println(sb.toString());
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				project = createProjectBean(rs);
				projects.add(project);
			}
		}
		catch (SQLException e) {
			System.err.println("Vector<ProjectBean> getProjectsByOther(String[] regions,String[] categories,String[]positions,String[]fiscals) " + e);
			throw new Fund3Exception("Can not extract projects from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return projects;
	}
	public static TreeMap<Integer,String> getAllProjects() throws Fund3Exception {
		TreeMap<Integer,String> projects = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			projects= new TreeMap<Integer,String>();
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_all_projects; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				projects.put(rs.getInt("PROJECT_ID"), rs.getString("PROJECT_NAME") + " (" + rs.getString("PROJECT_NUMBER") + ")");
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectManager TreeMap<Integer,String> getAllProjects()" + e);
			throw new Fund3Exception("Can not extract projects from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return projects;
	}
	public static String getProjectRegions(Integer pid) throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_regions(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				if(sb.length() == 0){
					sb.append(rs.getString("PRTEXT"));
				}else{
					sb.append(" , ");
					sb.append(rs.getString("PRTEXT"));
				}
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectManager String getProjectRegions(Integer pid)" + e);
			throw new Fund3Exception("Can not extract project regions from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return sb.toString();
	}
	public static void updateProjectStatus(int projectid,int projectstatus,String statusnotes,String statusby) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.update_fund3_project_status(?,?,?,?); end;");
			stat.setInt(1,projectstatus);
			stat.setString(2,statusnotes);
			stat.setString(3, statusby);
			stat.setInt(4,projectid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateProjectStatus(int projectid,int projectstatus,String statusnotes,String statusby) " + e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
	}
	public static Vector<ProjectBean> getProjectApprovals() throws Fund3Exception {
		Vector<ProjectBean> projects = null;
		ProjectBean project = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			projects= new Vector<ProjectBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_projects_approvals; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				project = createProjectBean(rs);
				projects.add(project);
			}
		}
		catch (SQLException e) {
			System.err.println("Vector<ProjectBean> getProjectApprovals() " + e);
			throw new Fund3Exception("Can not extract projects from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return projects;
	}
	public static void updateProjectNumber(int projectid,String projectnumber) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.update_fund3_project_number(?,?); end;");
			stat.setInt(1,projectid);
			stat.setString(2,projectnumber);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateProjectNumber(int projectid,String projectnumber) " + e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
	}	
	public static ProjectBean createProjectBean(ResultSet rs) {
		ProjectBean abean = null;
		try {
			abean = new ProjectBean();
			abean.setProjectId(rs.getInt("PROJECT_ID"));
			abean.setProjectName(rs.getString("PROJECT_NAME"));
			Timestamp ts= rs.getTimestamp("DATE_FUNDING_APPROVED");
			if(ts != null){
				abean.setDateFundingApproved(new java.util.Date(rs.getTimestamp("DATE_FUNDING_APPROVED").getTime()));
			}
			ts=rs.getTimestamp("PROJECT_START_DATE");
			if(ts != null){
				abean.setProjectStartDate(new java.util.Date(rs.getTimestamp("PROJECT_START_DATE").getTime()));
			}
			ts=rs.getTimestamp("PROJECT_END_DATE");
			if(ts != null){
				abean.setProjectEndDate(new java.util.Date(rs.getTimestamp("PROJECT_END_DATE").getTime()));
			}
			abean.setProjectCategory(rs.getInt("PROJECT_CATEGORY"));
			abean.setPositionResponsible(rs.getInt("POSITION_RESPONSIBLE"));
			abean.setEmployeeEmail(rs.getString("EMPLOYEE_EMAIL"));
			abean.setEmployeeName(rs.getString("EMPLOYEE_NAME"));
			abean.setEmployeePhone(rs.getString("EMPLOYEE_PHONE"));
			abean.setProjectDescription(rs.getString("PROJECT_DESCRIPTION"));
			abean.setSpecialRequirements(rs.getString("SPECIAL_REQUIREMENTS"));
			abean.setReportRequested(rs.getInt("REPORT_REQUESTED"));
			ts=rs.getTimestamp("FIRST_REPORT_DATE");
			if(ts != null){
				abean.setFirstReportDate(new java.util.Date(rs.getTimestamp("FIRST_REPORT_DATE").getTime()));
			}
			abean.setReportFrequency(rs.getInt("REPORT_FREQUENCY"));
			abean.setReportEmail(rs.getString("REPORT_EMAIL"));
			abean.setAddedBy(rs.getString("ADDED_BY"));
			ts=rs.getTimestamp("DATE_ADDED");
			if(ts != null){
				abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
			}
			abean.setProjectStartDateTBD(rs.getInt("PROJECT_START_DATE_TBD"));
			abean.setProjectEndDateTBD(rs.getInt("PROJECT_End_DATE_TBD"));
			abean.setPositionResponsibleText(rs.getString("PRTEXT"));
			abean.setProjectNumber(rs.getString("PROJECT_NUMBER"));
			abean.setProjectYear(rs.getInt("PROJECT_YEAR"));
			abean.setIsActive(rs.getInt("IS_ACTIVE"));
			abean.setAvailabilityNotes(rs.getString("AVAILABILITY_NOTES"));
			abean.setStatusNotes(rs.getString("STATUS_NOTES"));
			abean.setProjectStatus(rs.getInt("PROJECT_STATUS"));
			abean.setStatusBy(rs.getString("STATUS_BY"));
			ts=rs.getTimestamp("STATUS_DATE");
			if(ts != null){
				abean.setStatusDate(new java.util.Date(rs.getTimestamp("STATUS_DATE").getTime()));
			}
			//now we get the region list
			abean.setProjectRegionsList(getProjectRegions(abean.getProjectId()));
			abean.setProjectSchool(ProjectSchoolManager.getProjectSchoolByProjectId(abean.getProjectId()));
			abean.setProjectFunding(ProjectFundingManager.getProjectFundingProjectId(abean.getProjectId()));
			abean.setProjectExpense(ProjectExpenseManager.getProjectExpenseByProjectId(abean.getProjectId()));
			abean.setProjectDocuments(ProjectDocumentManager.getProjectFilesByProjectId(abean.getProjectId()));
			ArrayList<AuditLogBean> test = AuditLogManager.getAuditLogByProject(abean.getProjectId());
			abean.setProjectEmpRes(ProjectEmployeeResponsibleManager.getProjectEmployeesResById(abean.getProjectId()));
			abean.setAuditLog(test);
			//now we check to see if the extended fields from sds are there
			//not all queries use the fields
			try
			  {
			    rs.findColumn("CURRENT BUDGET");
			    abean.setCurrentBudget(rs.getDouble("CURRENT BUDGET"));
			  } catch (SQLException sqlex)
			  {
			    abean.setCurrentBudget(0);
			  }
			  try
			  {
			    rs.findColumn("CURRENT BALANCE");
			    abean.setCurrentBalance(rs.getDouble("CURRENT BALANCE"));
			  } catch (SQLException sqlex)
			  {
			    abean.setCurrentBalance(0);
			  }
			
		}
		catch (SQLException e) {
			System.err.println("ProjectManager createProjectBean(ResultSet rs)" + e);
			abean = null;
		} catch (Fund3Exception e) {
			// TODO Auto-generated catch block
			System.err.println("ProjectManager createProjectBean(ResultSet rs)" + e);
			abean = null;
		}
		return abean;
	}	
}
