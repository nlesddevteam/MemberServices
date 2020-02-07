package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.school.SchoolDB;
import com.awsd.security.User;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.constants.RequestToHireStatus;
public class RequestToHireManager {
	public static RequestToHireBean addRequestToHireBean(RequestToHireBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_request_to_hire(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getJobTitle());
			stat.setString(3, abean.getPreviousIncumbent());
			if(!(abean.getDateVacated() == null)){
				stat.setDate(4, new Date(abean.getDateVacated().getTime()));
			}else{
				stat.setDate(4, null);
			}
			stat.setString(5, abean.getWorkLocation());
			stat.setInt(6, abean.getPositionType());
			if(!(abean.getStartDate() == null)){
				stat.setDate(7, new java.sql.Date(abean.getStartDate().getTime()));
			}else{
				stat.setDate(7, null);
			}
			stat.setInt(8, abean.getSupervisor());
			stat.setInt(9, abean.getDivision());
			stat.setString(10,abean.getComments());
			stat.setString(11, abean.getRequestBy());
			stat.setString(12, abean.getPositionSalary());
			stat.setInt(13, abean.getPositionName());
			stat.setInt(14, abean.getPositionTerm());
			stat.setString(15, abean.getPositionHours());
			stat.setString(16, abean.getRequestType());
			stat.setInt(17, abean.getShiftDiff());
			if(!(abean.getEndDate() == null)){
				stat.setDate(18, new java.sql.Date(abean.getEndDate().getTime()));
			}else{
				stat.setDate(18, null);
			}
			stat.setInt(19, abean.getPrivateList());
			stat.execute();
			abean.setId(((OracleCallableStatement) stat).getInt(1));

			stat.close();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("RequestToHireBean addRequestToHireBean(RequestToHireBean abean)): " + e);
			throw new JobOpportunityException("Can not add Request To Hire to DB.", e);
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

		return abean;
	}
	public static void updateRequestToHireBean(RequestToHireBean abean) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_request_to_hire(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setString(1, abean.getJobTitle());
			stat.setString(2, abean.getPreviousIncumbent());
			if(!(abean.getDateVacated() == null)){
				stat.setDate(3, new Date(abean.getDateVacated().getTime()));
			}else{
				stat.setDate(3, null);
			}
			stat.setString(4, abean.getWorkLocation());
			stat.setInt(5, abean.getPositionType());
			if(!(abean.getStartDate() == null)){
				stat.setDate(6, new java.sql.Date(abean.getStartDate().getTime()));
			}else{
				stat.setDate(6, null);
			}
			stat.setInt(7, abean.getSupervisor());
			stat.setInt(8, abean.getDivision());
			stat.setString(9,abean.getComments());
			stat.setString(10, abean.getPositionSalary());
			stat.setInt(11, abean.getId());
			stat.setInt(12, abean.getPositionName());
			stat.setInt(13, abean.getPositionTerm());
			stat.setString(14, abean.getPositionHours());
			stat.setString(15, abean.getRequestType());
			stat.setInt(16, abean.getShiftDiff());
			if(!(abean.getEndDate() == null)){
				stat.setDate(17, new java.sql.Date(abean.getEndDate().getTime()));
			}else{
				stat.setDate(17, null);
			}
			stat.setInt(18, abean.getPrivateList());
			stat.execute();

			stat.close();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("RequestToHireBean addRequestToHireBean(RequestToHireBean abean)): " + e);
			throw new JobOpportunityException("Can not add Request To Hire to DB.", e);
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
	public static void approveRequestToHire(int rid, int status, String statusby) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			if(status == 2){
				stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.approve_rtf_dd(?,?,?); end;");
			}else if(status == 3){
				stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.approve_rtf_bc(?,?,?); end;");
			}else if(status == 4){
				stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.approve_rtf_ad(?,?,?); end;");
			}else if(status == 5){
				stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.approve_rtf_adhr(?,?,?); end;");
			}else if (status == 7) {
				stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.decline_rtf(?,?); end;");
			}
			

			stat.setInt(1, rid);
			stat.setInt(2, status);
			if(status <= 5){
				//not used by rejected and ad created
				stat.setString(3, statusby);
			}
			
			stat.execute();

			stat.close();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static void approveRequestToHireDD(int rid, int status, String statusby)): " + e);
			throw new JobOpportunityException("Can not approve Request To Hire", e);
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
	public static void approveRequestToHireBCPos(int rid, int status, String statusby, String posnumber) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.approve_rtf_bc(?,?,?,?); end;");
			stat.setInt(1, rid);
			stat.setInt(2, status);
			stat.setString(3, statusby);
			stat.setString(4,posnumber);
			
			stat.execute();

			stat.close();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static void approveRequestToHireDD(int rid, int status, String statusby)): " + e);
			throw new JobOpportunityException("Can not approve Request To Hire", e);
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
	public static RequestToHireBean createRequestToHireBean(ResultSet rs) {

		RequestToHireBean abean = null;
		try {
			abean = new RequestToHireBean();
			abean.setJobTitle(rs.getString("JOB_TITLE"));
			abean.setPreviousIncumbent(rs.getString("PREVIOUS_INCUMBENT"));
			if (rs.getDate("DATE_VACATED") != null)
				abean.setDateVacated(new java.util.Date(rs.getDate("DATE_VACATED").getTime()));
			
			abean.setWorkLocation(rs.getString("WORK_LOCATION"));
			abean.setPositionType(rs.getInt("POSITION_TYPE"));
			abean.setPositionSalary(rs.getString("POSITION_SALARY"));
			if (rs.getDate("START_DATE") != null)
				abean.setStartDate(new java.util.Date(rs.getDate("START_DATE").getTime()));
			if(!(rs.getString("SNAME").equals(","))){
				abean.setSupervisor(rs.getInt("SUPERVISIOR"));
				abean.setSupervisorName(rs.getString("sname"));
			}
			
			abean.setDivision(rs.getInt("DIVISION"));
			abean.setComments(rs.getString("COMMENTS"));
			if (rs.getDate("DD_APPROVED") != null)
				abean.setDdApproved(new java.util.Date(rs.getDate("DD_APPROVED").getTime()));
			//code changed to bring back user personnel name and id in one query instead of
			//mulitple calls.  Name for display, id used for email manager if needed
			if(!(rs.getString("ddname").equals(","))){
				abean.setDdApprovedBy(rs.getString("ddname"));
				abean.setDdApprovedById(Integer.parseInt(rs.getString("DD_APPROVED_BY")));
			}
			if (rs.getDate("BC_APPROVED") != null)
				abean.setBcApproved(new java.util.Date(rs.getDate("BC_APPROVED").getTime()));
			if(!(rs.getString("bcname").equals(","))){
				abean.setBcApprovedBy(rs.getString("bcname"));
				abean.setBcApprovedById(Integer.parseInt(rs.getString("BC_APPROVED_BY")));
			}
			
			if (rs.getDate("AD_APPROVED") != null)
				abean.setAdApproved(new java.util.Date(rs.getDate("AD_APPROVED").getTime()));
			if(!(rs.getString("adname").equals(","))){
				abean.setAdApprovedBy(rs.getString("adname"));
				abean.setAdApprovedById(Integer.parseInt(rs.getString("AD_APPROVED_BY")));
			}
						
			if (rs.getDate("ADHR_APPROVED") != null)
				abean.setAdhrApproved(new java.util.Date(rs.getDate("ADHR_APPROVED").getTime()));
			if(!(rs.getString("adhrname").equals(","))){
				abean.setAdhrApprovedBy(rs.getString("adhrname"));
				abean.setAdhrApprovedById(Integer.parseInt(rs.getString("ADHR_APPROVED_BY")));
			}
			
			abean.setComments(rs.getString("COMMENTS"));
			if(!(rs.getString("rby").equals(","))){
				abean.setRequestBy(rs.getString("rby"));
				abean.setRequestById(Integer.parseInt(rs.getString("REQUESTED_BY")));
			}
			
			if (rs.getDate("DATE_REQUESTED") != null)
				abean.setDateRequested(new java.util.Date(rs.getDate("DATE_REQUESTED").getTime()));
			if(rs.getString("LOC_DESCRIPTION") != null){
				abean.setLocationDescription(rs.getString("LOC_DESCRIPTION"));
			}
			abean.setStatus(RequestToHireStatus.get(rs.getInt("STATUS")));
			abean.setId(rs.getInt("ID"));
			abean.setCompetitionNumber(rs.getString("COMP_NUMB"));
			abean.setUnionCode(rs.getInt("UNIONID"));
			abean.setUnionCodeString(rs.getString("UNION_NAME"));
			abean.setPositionName(rs.getInt("POSITION_NAME"));
			abean.setPositionNameString(rs.getString("PDESCRIPTION"));
			abean.setPositionTerm(rs.getInt("POSITION_TERM"));
			abean.setPositionTermString(getPositionTermString(rs.getInt("POSITION_TERM")));
			abean.setPositionHours(rs.getString("POSITION_HOURS"));
			abean.setRequestType(rs.getString("REQUEST_TYPE"));
			if(rs.getString("REQUEST_TYPE") ==  null){
				abean.setRequestTypeString(getRequestTypeString("S"));
			}else{
				abean.setRequestTypeString(getRequestTypeString(rs.getString("REQUEST_TYPE")));
			}
			
			abean.setShiftDiff(rs.getInt("SHIFT_DIFF"));
			abean.setPositionNumber(rs.getString("POSITION_NUMBER"));
			abean.setPrivateList(rs.getInt("PRIVATE_LIST"));
			if (rs.getDate("END_DATE") != null)
				abean.setEndDate(new java.util.Date(rs.getDate("END_DATE").getTime()));
		}
		catch (Exception e) {
			abean = null;
		}

		return abean;
	}
	
	public static RequestToHireBean[] getRequestsToHireByStatus(int status) throws JobOpportunityException {

		Vector<RequestToHireBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<RequestToHireBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rth_by_status(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createRequestToHireBean(rs));

		}
		catch (SQLException e) {
			System.err.println("RequestToHireBean[] getRequestsToHireByStatus(int status): " + e);
			throw new JobOpportunityException("Can not extract RequestToHireBean from DB.", e);
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

		return ((RequestToHireBean[]) beans.toArray(new RequestToHireBean[0]));
	}
	public static RequestToHireBean getRequestToHireById(int status) throws JobOpportunityException {

		RequestToHireBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rth_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				bean = createRequestToHireBean(rs);

		}
		catch (SQLException e) {
			System.err.println("RequestToHireBean getRequestToHireById(int status): " + e);
			throw new JobOpportunityException("Can not extract RequestToHireBean from DB.", e);
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

		return bean;
	}
	public static RequestToHireBean getRequestToHireByCompNum(String cn) throws JobOpportunityException {

		RequestToHireBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rth_by_cn(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, cn);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				bean = createRequestToHireBean(rs);

		}
		catch (SQLException e) {
			System.err.println("getRequestToHireByCompNum(String cn): " + e);
			throw new JobOpportunityException("Can not extract RequestToHireBean from DB.", e);
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

		return bean;
	}
	public static boolean checkViewApproveButton(RequestToHireBean rbean,User user){
		boolean canview=false;
		//first check zone
		int zoneid;
		StringBuilder sb = new StringBuilder();
		sb.append("RTH-");
		try {
			Integer test =Integer.parseInt(rbean.getWorkLocation());
			switch (test) {
			case 0: // District Office
				zoneid = 1;
				break;
			case 4007: // Burin Satellite Office
				zoneid = 1;
				break;
			case 449: // St. Augustine's Primary
				zoneid = 1;
				break;
			case 455: // Janeway Hospital School
				zoneid = 1;
				break;
			case 1000:
			case 1001:
			case 1002://Labrador Regional Office
				zoneid = 4;
				break;
			case 1009: // Avalon West Satellite Office
				zoneid = 1;
				break;
			case 2008: // Vista Satellite Office
				zoneid = -1;
				break;
			case 2000:
			case 2001://Western Regional Office
				zoneid = 3;
				break;
			case 3000: //Nova Central Regional Office
			case 3030:
			case 3031:
			case 3032:
			case 3033:
			case 3034:
			case 3035:
			case 3036:
			case 3037:
			case 3038:
				zoneid = 2;
				break;
			case 5000: //District Conference Center
				zoneid = 1;
				break;
			default:
				zoneid = SchoolDB.getSchoolZoneBySchoolName(SchoolDB.getSchoolFromDeptId(test%1000).getSchoolName());
				
			}
			
			switch(rbean.getStatus().getValue()){
			case 1://submitted
				//user and division manager
				switch(zoneid){
				case 1://Eastern
					sb.append("EAST-");
					break;
				case 2://central
					sb.append("CENT-");
					break;
				case 3://Western
					sb.append("WEST-");
					break;
				case 4://Labrador
					sb.append("LABR-");
					break;	
				default://nlesd
					sb.append("EAST-");
					break;
				}
				sb.append(rbean.getDivisionStringShort() + "-DD");
				canview = user.checkRole(sb.toString());
				break;
			case 2://Division Approval
				//user,division manager
				//only one comptroller role
				canview = user.checkRole("RTH-BC");
				break;
			case 3://Comptroller Approval
				//user,division manager
				sb.append(rbean.getDivisionStringShort() + "-AD");
				canview = user.checkRole(sb.toString());
				break;
			case 4://Assistant Director Approval
				//user,division manager
				canview = user.checkRole("RTH-HR-AD");
				break;
			case 5://Assistant Director HR Approval
				//user,division manager
				canview = user.checkRole("RTH-POST-COMP");
				break;
			case 6://Ad Created
				//user
				canview=false;
				break;
			case 7://Rejected
				//user
				canview=false;
				break;
			default:
				canview=false;
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return canview;
	}
	public static RequestToHireBean[] getRequestsToHireSubmitted() throws JobOpportunityException {

		Vector<RequestToHireBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<RequestToHireBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rth_submitted; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createRequestToHireBean(rs));

		}
		catch (SQLException e) {
			System.err.println("RequestToHireBean[] getRequestsToHireSubmitted(): " + e);
			throw new JobOpportunityException("Can not extract RequestToHireBean from DB.", e);
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

		return ((RequestToHireBean[]) beans.toArray(new RequestToHireBean[0]));
	}
	public static TreeMap<String,Integer> getUnionCodes() throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,Integer> codes = new TreeMap<String,Integer>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rth_unions; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				codes.put(rs.getString("UNION_NAME"),rs.getInt("ID"));

		}
		catch (SQLException e) {
			System.err.println("TreeMap<String,Integer> getUnionCodes(): " + e);
			throw new JobOpportunityException("Can not extract UnionCodes from DB.", e);
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

		return codes;
	}
	public static String getUnionPositions(int id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rth_positions(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			sb.append("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<UNIONPOSITIONS>");
			while (rs.next()){
				sb.append("<UPOSITION>");
				sb.append("<ID>" + rs.getInt("ID") + "</ID>");
				sb.append("<PDESCRIPTION>" + rs.getString("PDESCRIPTION") + "</PDESCRIPTION>");
				if(rs.getString("JES_PAY") ==  null) {
					sb.append("<JESPAY></JESPAY>");
				}else {
					sb.append("</JESPAY>" + rs.getString("JES_PAY") + "</JESPAY>");
				}
				sb.append("<UNIONCODE>" + rs.getInt("UNION_CODE") + "</UNIONCODE>");
				sb.append("</UPOSITION>");
			}
			
			sb.append("</UNIONPOSITIONS>");

		}
		catch (SQLException e) {
			System.err.println("String getUnionPositions(int id) throws JobOpportunityException): " + e);
			throw new JobOpportunityException("Can not extract UnionPositions from DB.", e);
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
	private static String getPositionTermString(int id){
		String pstring="";
		switch(id){
		case 10:
			pstring="10 Month";
			break;
		case 12: 
			pstring="12 Month";
			break;
		default:
			pstring="";
			break;	
		}
		return pstring;
	}
	private static String getRequestTypeString(String s){
		String pstring="";
		if(s.equals("S")){
			pstring="Support";
		}else if(s.equals("M")){
			pstring="Management";
		}else{
			pstring="Other";
		}
		return pstring;
	}
	public static RequestToHireBean[] getMyRequestsToHireSubmitted(int pid) throws JobOpportunityException {

		Vector<RequestToHireBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<RequestToHireBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rth_by_pid(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, pid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createRequestToHireBean(rs));

		}
		catch (SQLException e) {
			System.err.println("RequestToHireBean[] getMyRequestsToHireSubmitted(int pid): " + e);
			throw new JobOpportunityException("Can not extract RequestToHireBean from DB.", e);
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

		return ((RequestToHireBean[]) beans.toArray(new RequestToHireBean[0]));
	}
	public static int getDivisionID(String division) {
		int divisionid=0;
		switch(division){
		case "PRO"://Eastern
			divisionid = 1;
			break;
		case "IT"://central
			divisionid=2;
			break;
		case "PBS"://Western
			divisionid=3;
			break;
		case "ST"://Labrador
			divisionid=4;
			break;
		case "FAC"://Labrador
			divisionid=5;
			break;	
		case "HR"://Labrador
			divisionid=6;
			break;	
		default://nlesd
			divisionid=6;
			break;
		}
		return divisionid;
	}
	public static int getZoneID(String zone) {
		int zoneid=0;
		switch(zone){
		case "EAST"://Eastern
			zoneid = 1;
			break;
		case "CENT"://central
			zoneid=2;
			break;
		case "WEST"://Western
			zoneid=3;
			break;
		case "LABR"://Labrador
			zoneid=4;
			break;	
		default://nlesd
			zoneid=1;
			break;
		}
		return zoneid;
	}
	public static RequestToHireBean[] getRequestsToHireApprovalsAD(int divisionid,int status) throws JobOpportunityException {
		Vector<RequestToHireBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<RequestToHireBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rth_approvals_ad(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, divisionid);
			stat.setInt(3, 0);
			stat.setInt(4, status);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createRequestToHireBean(rs));

		}
		catch (SQLException e) {
			System.err.println("getRequestsToHireApprovalsAD(int divisionid,int status): " + e);
			throw new JobOpportunityException("Can not extract RequestToHireBean from DB.", e);
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

		return ((RequestToHireBean[]) beans.toArray(new RequestToHireBean[0]));
	}
	public static RequestToHireBean[] getRequestsToHireApprovalsDD(int divisionid,int zoneid) throws JobOpportunityException {
		Vector<RequestToHireBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<RequestToHireBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rth_approvals_dd(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, divisionid);
			stat.setInt(3, zoneid);
			stat.setInt(4, RequestToHireStatus.SUBMITTED.getValue());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				//need to account for locations without zones: District Headquarters
				if(rs.getInt("ZONE_ID") > 0) {
					if(zoneid == rs.getInt("ZONE_ID")) {
						//get records for passed in zone
						beans.add(createRequestToHireBean(rs));
					}
				}else {
					//no zone so will show with eastern
					if(zoneid == 1) {
						beans.add(createRequestToHireBean(rs));
					}
				}
				

		}
		catch (SQLException e) {
			System.err.println("getRequestsToHireApprovalsAD(int divisionid,int status): " + e);
			throw new JobOpportunityException("Can not extract RequestToHireBean from DB.", e);
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

		return ((RequestToHireBean[]) beans.toArray(new RequestToHireBean[0]));
	}
	public static void deleteRequestToHire(int requestid)  {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.delete_request_to_hire(?); end;");

			stat.setInt(1, requestid);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteRequestToHire(int requestid): " + e);
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
}
