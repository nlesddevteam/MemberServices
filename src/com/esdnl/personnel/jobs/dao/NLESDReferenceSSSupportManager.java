package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.NLESDReferenceException;
import com.esdnl.personnel.jobs.bean.NLESDReferenceSSSupportBean;
public class NLESDReferenceSSSupportManager {
	public static NLESDReferenceSSSupportBean addNLESDReferenceSSSupportBean(NLESDReferenceSSSupportBean abean) throws NLESDReferenceException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_ref_chk_ss_sup(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getProvidedBy());
			stat.setString(3, abean.getProvidedByPosition());
			stat.setString(4, abean.getQ1());
			stat.setString(5, abean.getQ2());
			stat.setString(6, abean.getQ3());
			stat.setString(7, abean.getQ4());
			stat.setString(8, abean.getQ5());
			stat.setString(9, abean.getQ6());
			stat.setString(10, abean.getQ7());
			stat.setString(11, abean.getQ7Comment());
			stat.setString(12, abean.getQ8());
			stat.setString(13, abean.getQ8Comment());
			stat.setString(14, abean.getQ9());
			stat.setString(15, abean.getQ10());
			stat.setString(16, abean.getQ11());
			stat.setString(17, abean.getQ12());
			stat.setString(18, abean.getScale1());
			stat.setString(19, abean.getScale2());
			stat.setString(20, abean.getScale3());
			stat.setString(21, abean.getScale4());
			stat.setString(22, abean.getScale5());
			stat.setString(23, abean.getScale6());
			stat.setString(24, abean.getScale7());
			stat.setString(25, abean.getScale8());
			stat.setString(26, abean.getScale9());
			stat.setString(27, abean.getScale10());
			stat.setString(28, abean.getScale11());
			stat.setString(29, abean.getScale12());
			stat.setString(30, abean.getScale13());
			stat.setString(31, abean.getScale14());
			stat.setString(32, abean.getProfile().getUID());
			stat.setString(33,abean.getProviderEmail());
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

			System.err.println("NLESDReferenceSSSupportBean addNLESDReferenceSSSupportBean(NLESDReferenceSSSupportBean abean): " + e);
			throw new NLESDReferenceException("Can not add NLESDReferenceSSSupportBean to DB.", e);
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
	public static NLESDReferenceSSSupportBean getNLESDReferenceSSSupportBean(int id) throws NLESDReferenceException {
		NLESDReferenceSSSupportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_nlesd_ref_chk_req_ss(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				eBean = createNLESDReferenceSSSupportBean(rs);
		}
		catch (SQLException e) {
			System.err.println("NLESDReferenceSSSupportBean getNLESDReferenceSSSupportBean(int id): " + e);
			throw new NLESDReferenceException("Can not extract NLESDReferenceSSSupportBean from DB.", e);
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
	public static NLESDReferenceSSSupportBean createNLESDReferenceSSSupportBean(ResultSet rs) {
		NLESDReferenceSSSupportBean abean = null;
		try {
			abean = new NLESDReferenceSSSupportBean();
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
			abean.setQ7(rs.getString("Q7"));
			abean.setQ7Comment(rs.getString("Q7_COMMENTS"));
			abean.setQ8(rs.getString("Q8"));
			abean.setQ8Comment(rs.getString("Q8_COMMENTS"));
			abean.setQ9(rs.getString("Q9"));
			abean.setQ10(rs.getString("Q10"));
			abean.setQ11(rs.getString("Q11"));
			abean.setQ12(rs.getString("Q12"));
			abean.setScale1(rs.getString("SCALE1"));
			abean.setScale2(rs.getString("SCALE2"));
			abean.setScale3(rs.getString("SCALE3"));
			abean.setScale4(rs.getString("SCALE4"));
			abean.setScale5(rs.getString("SCALE5"));
			abean.setScale6(rs.getString("SCALE6"));
			abean.setScale7(rs.getString("SCALE7"));
			abean.setScale8(rs.getString("SCALE8"));
			abean.setScale9(rs.getString("SCALE9"));
			abean.setScale10(rs.getString("SCALE10"));
			abean.setScale11(rs.getString("SCALE11"));
			abean.setScale12(rs.getString("SCALE12"));
			abean.setScale13(rs.getString("SCALE13"));
			abean.setScale14(rs.getString("SCALE14"));
			abean.setReferenceScale(rs.getString("REFERENCE_SCALE"));
			abean.setProfile(ApplicantProfileManager.createApplicantProfileBean(rs));
			abean.setProviderEmail(rs.getString("PROVIDER_EMAIL"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}	
	public static NLESDReferenceSSSupportBean getLatestNLESDReferenceSSSupportBean(String sin ) throws NLESDReferenceException {
		NLESDReferenceSSSupportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_nlesd_ref_chk_req_ss_l(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				eBean = createNLESDReferenceSSSupportBean(rs);
		}
		catch (SQLException e) {
			System.err.println("NLESDReferenceSSSupportBean getLatestNLESDReferenceSSSupportBean(String sin ): " + e);
			throw new NLESDReferenceException("Can not extract NLESDReferenceSSSupportBean from DB.", e);
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
