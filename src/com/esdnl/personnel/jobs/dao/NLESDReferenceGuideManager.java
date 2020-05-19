package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.NLESDReferenceException;
import com.esdnl.personnel.jobs.bean.NLESDReferenceGuideBean;
public class NLESDReferenceGuideManager {
	public static NLESDReferenceGuideBean addNLESDReferenceGuideBean(NLESDReferenceGuideBean abean) throws NLESDReferenceException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_ref_chk_gui(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getProvidedBy());
			stat.setString(3, abean.getProvidedByPosition());
			stat.setString(4, abean.getQ1());
			stat.setString(5, abean.getQ2());
			stat.setString(6, abean.getQ3());
			stat.setString(7, abean.getQ4());
			stat.setString(8, abean.getQ5());
			stat.setString(9, abean.getQ6());
			stat.setString(10, abean.getQ6Comment());
			stat.setString(11, abean.getScale1());
			stat.setString(12, abean.getScale2());
			stat.setString(13, abean.getScale3());
			stat.setString(14, abean.getScale4());
			stat.setString(15, abean.getScale5());
			stat.setString(16, abean.getScale6());
			stat.setString(17, abean.getScale7());
			stat.setString(18, abean.getScale8());
			stat.setString(19, abean.getScale9());
			stat.setString(20, abean.getProfile().getUID());
			stat.setString(21, abean.getEmailAddress());
			stat.execute();
			int id = ((OracleCallableStatement) stat).getInt(1);
			abean.setId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("NLESDReferenceGuideBean addNLESDReferenceGuideBean(NLESDReferenceGuideBean abean): " + e);
			throw new NLESDReferenceException("Can not add NLESDReferenceGuideBean to DB.", e);
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
	public static NLESDReferenceGuideBean getNLESDReferenceGuideBean(int id) throws NLESDReferenceException {
		NLESDReferenceGuideBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_nlesd_ref_chk_req_gui(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				eBean = createNLESDReferenceGuideBean(rs);
		}
		catch (SQLException e) {
			System.err.println("NLESDReferenceGuideManager.getNLESDReferenceGuideBean(int): " + e);
			throw new NLESDReferenceException("Can not extract NLESDReferenceGuideBean from DB.", e);
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
	public static NLESDReferenceGuideBean updateNLESDReferenceGuideBean(NLESDReferenceGuideBean abean) throws NLESDReferenceException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_ref_chk_gui(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, abean.getProvidedBy());
			stat.setString(2, abean.getProvidedByPosition());
			stat.setString(3, abean.getQ1());
			stat.setString(4, abean.getQ2());
			stat.setString(5, abean.getQ3());
			stat.setString(6, abean.getQ4());
			stat.setString(7, abean.getQ5());
			stat.setString(8, abean.getQ6());
			stat.setString(9, abean.getQ6Comment());
			stat.setString(10, abean.getScale1());
			stat.setString(11, abean.getScale2());
			stat.setString(12, abean.getScale3());
			stat.setString(13, abean.getScale4());
			stat.setString(14, abean.getScale5());
			stat.setString(15, abean.getScale6());
			stat.setString(16, abean.getScale7());
			stat.setString(17, abean.getScale8());
			stat.setString(18, abean.getScale9());
			stat.setString(19, abean.getProfile().getUID());
			stat.setInt(20, abean.getId());
			stat.setString(21, abean.getEmailAddress());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("NLESDReferenceGuideBean updateNLESDReferenceGuideBean(NLESDReferenceGuideBean abean): " + e);
			throw new NLESDReferenceException("Can not update NLESDReferenceGuideBean to DB.", e);
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
	public static NLESDReferenceGuideBean createNLESDReferenceGuideBean(ResultSet rs) {
		NLESDReferenceGuideBean abean = null;
		try {
			abean = new NLESDReferenceGuideBean();
			abean.setId(rs.getInt("REFERENCE_ID"));
			abean.setProvidedBy(rs.getString("PROVIDED_BY"));
			abean.setProvidedByPosition(rs.getString("PROVIDER_POSITION"));
			abean.setDateProvided(new java.util.Date(rs.getTimestamp("DATE_PROVIDED").getTime()));
			abean.setQ1(rs.getString("Q1"));
			abean.setQ2(rs.getString("Q2"));
			abean.setQ3(rs.getString("Q3"));
			abean.setQ4(rs.getString("Q4"));
			abean.setQ5(rs.getString("Q5"));
			abean.setQ6(rs.getString("Q6"));
			abean.setQ6Comment(rs.getString("Q6_COMMENT"));
			abean.setScale1(rs.getString("SCALE1"));
			abean.setScale2(rs.getString("SCALE2"));
			abean.setScale3(rs.getString("SCALE3"));
			abean.setScale4(rs.getString("SCALE4"));
			abean.setScale5(rs.getString("SCALE5"));
			abean.setScale6(rs.getString("SCALE6"));
			abean.setScale7(rs.getString("SCALE7"));
			abean.setScale8(rs.getString("SCALE8"));
			abean.setScale9(rs.getString("SCALE9"));
			abean.setReferenceScale(rs.getString("REFERENCE_SCALE"));
			abean.setEmailAddress(rs.getString("PROVIDER_EMAIL"));
			abean.setProfile(ApplicantProfileManager.createApplicantProfileBean(rs));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
	public static NLESDReferenceGuideBean getLatestNLESDReferenceGuideBean(String sin ) throws NLESDReferenceException {
		NLESDReferenceGuideBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_nlesd_ref_chk_req_gui_l(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				eBean = createNLESDReferenceGuideBean(rs);
		}
		catch (SQLException e) {
			System.err.println("NLESDReferenceAdminManager.getNLESDReferenceGuideBean(int): " + e);
			throw new NLESDReferenceException("Can not extract NLESDReferenceGuideBean from DB.", e);
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
}	

