package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantRefRequestBean;

public class ApplicantRefRequestManager {
	public static ApplicantRefRequestBean addApplicantRefRequestBean(ApplicantRefRequestBean abean){
		
		Connection con = null;
		CallableStatement stat = null;
		int id;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
		
			stat = con.prepareCall("begin ? :=  awsd_user.PERSONNEL_JOBS_PKG.add_app_ref_req(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getEmailAddress());
			stat.setInt(3, abean.getFkAppSup());
			stat.setString(4, abean.getApplicantId());
			stat.execute();
			id= ((OracleCallableStatement) stat).getInt(1);
			abean.setId(id);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
		
			System.err.println("ApplicantSupervisorBean addApplicantSupervisorBean(ApplicantSupervisorBean abean): " + e);
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
	public static ApplicantRefRequestBean getApplicantRefRequestBean(int id){

		ApplicantRefRequestBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.PERSONNEL_JOBS_PKG.get_appl_ref_req_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantRefRequestBean(rs,true);
			}
		}
		catch (SQLException e) {
			System.err.println("ApplicantRefRequestBean getApplicantRefRequestBean(int id) " + e);
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

		return eBean;
	}
	public static ApplicantRefRequestBean getApplicantRefRequestBeanS(int id, String sin){

		ApplicantRefRequestBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.PERSONNEL_JOBS_PKG.get_appl_ref_req_by_id_s(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.setString(3, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantRefRequestBean(rs,false);
			}
		}
		catch (SQLException e) {
			System.err.println(" ApplicantRefRequestBean getApplicantRefRequestBeanS(int id, String sin)) " + e);
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

		return eBean;
	}
	public static int getApplicantSupervisorId(String eid){

		Connection con = null;
		CallableStatement stat = null;
		int id=0;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.PERSONNEL_JOBS_PKG.get_app_sup_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, eid);
			stat.execute();
			id = ((OracleCallableStatement) stat).getInt(1);
			
		}
		catch (SQLException e) {
			System.err.println("int getApplicantSupervisorId(String eid) " + e);
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
	public static ApplicantRefRequestBean getApplicantRefRequestBeanByRef(int id){

		ApplicantRefRequestBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.PERSONNEL_JOBS_PKG.get_appl_ref_req_by_rid(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantRefRequestBean(rs,false);
			}
		}
		catch (Exception e) {
			System.err.println("ApplicantRefRequestBean getApplicantRefRequestBeanByRef(int id) " + e);
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

		return eBean;
	}
	public static void sendApplicantRefRequest(int id,String reftype,String refstatus){

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.PERSONNEL_JOBS_PKG.send_app_ref_request(?,?,?); end;");
			stat.setInt(1, id );
			stat.setString(2, refstatus );
			stat.setString(3, reftype );
			stat.execute();
					}
		catch (Exception e) {
			System.err.println("ApplicantRefRequestBean sendApplicantRefRequest(int id,String reftype,String refstatus) " + e);
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
	public static void applicantReferenceCompleted(int id,String refstatus, int fkreference){

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.PERSONNEL_JOBS_PKG.app_ref_req_complete(?,?,?); end;");
			stat.setInt(1, id );
			stat.setString(2, refstatus );
			stat.setInt(3, fkreference );
			stat.execute();
					}
		catch (Exception e) {
			System.err.println("void applicantReferenceCompleted(int id,String refstatus, int fkreference) " + e);
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
	public static void applicantReferenceDeclined(int id,String refstatus){

		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.PERSONNEL_JOBS_PKG.app_ref_req_declined(?,?); end;");
			stat.setInt(1, id );
			stat.setString(2, refstatus );
			stat.execute();
					}
		catch (Exception e) {
			System.err.println("void applicantReferenceDeclined(int id,String refstatus) " + e);
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
	public static ApplicantRefRequestBean createApplicantRefRequestBean(ResultSet rs,boolean extra) {

		ApplicantRefRequestBean aBean = null;
		try {
			aBean = new ApplicantRefRequestBean();

			aBean.setId(rs.getInt("ID"));
			aBean.setEmailAddress(rs.getString("EMAIL_ADDRESS"));
			aBean.setRequestStatus(rs.getString("REQUEST_STATUS"));
			if (rs.getDate("DATE_REQUESTED") != null)
				aBean.setDateRequested(new java.util.Date(rs.getDate("DATE_REQUESTED").getTime()));
			if (rs.getDate("DATE_STATUS") != null)
				aBean.setDateStatus(new java.util.Date(rs.getDate("DATE_STATUS").getTime()));
			aBean.setFkAppSup(rs.getInt("FK_APP_SUP"));
			aBean.setFkReference(rs.getInt("FK_REFERENCE"));
			aBean.setReferenceType(rs.getString("REFERENCE_TYPE"));
			aBean.setApplicantId(rs.getString("APPLICANT_ID"));
			if(extra) {
				StringBuilder sb = new StringBuilder();
				sb.append(rs.getString("SURNAME"));
				if(rs.getString("MAIDENNAME")!= null) {
					sb.append("(nee " + rs.getString("MAIDENNAME") + ")");
				}
				sb.append(", " + rs.getString("FIRSTNAME"));
				if(rs.getString("MIDDLENAME")!= null) {
					sb.append(" " + rs.getString("MIDDLENAME"));
				}
				aBean.setApplicantName(sb.toString());
			}
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}	
}
