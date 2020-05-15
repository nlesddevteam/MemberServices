package com.esdnl.personnel.jobs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.NLESDReferenceException;
import com.esdnl.personnel.jobs.bean.NLESDReferenceExternalBean;
public class NLESDReferenceExternalManager {
	public static NLESDReferenceExternalBean addNLESDReferenceExternalBean(NLESDReferenceExternalBean abean) throws NLESDReferenceException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_ref_chk_ext(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getProvidedBy());
			stat.setString(3, abean.getProvidedByPosition());
			stat.setString(4, abean.getQ1());
			stat.setString(5, abean.getQ2());
			stat.setString(6, abean.getQ3());
			stat.setString(7, abean.getQ4());
			stat.setString(8, abean.getQ7());
			stat.setString(9, abean.getQ7Comment());
			stat.setString(10, abean.getScale1());
			stat.setString(11, abean.getScale2());
			stat.setString(12, abean.getScale3());
			stat.setString(13, abean.getScale4());
			stat.setString(14, abean.getScale5());
			stat.setString(15, abean.getScale6());
			stat.setString(16, abean.getScale7());
			stat.setString(17, abean.getScale8());
			stat.setString(18, abean.getScale9());
			stat.setString(19, abean.getScale10());
			stat.setString(20, abean.getScale11());
			stat.setString(21, abean.getScale12());
			stat.setString(22, abean.getScale13());
			stat.setString(23, abean.getScale14());
			stat.setString(24, abean.getScale15());
			stat.setString(25, abean.getScale16());
			stat.setString(26, abean.getScale17());
			stat.setString(27, abean.getScale18());
			stat.setString(28, abean.getScale19());
			stat.setString(29, abean.getScale20());
			stat.setString(30, abean.getScale21());
			stat.setString(31, abean.getScale22());
			stat.setString(32, abean.getDomain1Comments());
			stat.setString(33, abean.getDomain2Comments());
			stat.setString(34, abean.getDomain3Comments());
			stat.setString(35, abean.getDomain4Comments());
			stat.setString(36, abean.getProfile().getUID());
			stat.setString(37, abean.getEmailAddress());
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

			System.err.println("NLESDReferenceExternalBean addNLESDReferenceTeacherBean(NLESDReferenceExternalBean abean) " + e);
			throw new NLESDReferenceException("Can not add NLESDReferenceExternalBean to DB.", e);
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
	public static NLESDReferenceExternalBean getNLESDReferenceExternalBean(int id) throws NLESDReferenceException {
		NLESDReferenceExternalBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_nlesd_ref_chk_req_e(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				eBean = createNLESDReferenceExternalBean(rs);
		}
		catch (SQLException e) {
			System.err.println("NLESDReferenceExternalBean getNLESDReferenceExternalBean(int id) " + e);
			throw new NLESDReferenceException("Can not extract NLESDReferenceTeacherBean from DB.", e);
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
	public static NLESDReferenceExternalBean updateNLESDReferenceExternalBean(NLESDReferenceExternalBean abean) throws NLESDReferenceException {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.update_ref_chk_ext(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, abean.getProvidedBy());
			stat.setString(2, abean.getProvidedByPosition());
			stat.setString(3, abean.getQ1());
			stat.setString(4, abean.getQ2());
			stat.setString(5, abean.getQ3());
			stat.setString(6, abean.getQ4());
			stat.setString(7, abean.getQ7());
			stat.setString(8, abean.getQ7Comment());
			stat.setString(9, abean.getScale1());
			stat.setString(10, abean.getScale2());
			stat.setString(11, abean.getScale3());
			stat.setString(12, abean.getScale4());
			stat.setString(13, abean.getScale5());
			stat.setString(14, abean.getScale6());
			stat.setString(15, abean.getScale7());
			stat.setString(16, abean.getScale8());
			stat.setString(17, abean.getScale9());
			stat.setString(18, abean.getScale10());
			stat.setString(19, abean.getScale11());
			stat.setString(20, abean.getScale12());
			stat.setString(21, abean.getScale13());
			stat.setString(22, abean.getScale14());
			stat.setString(23, abean.getScale15());
			stat.setString(24, abean.getScale16());
			stat.setString(25, abean.getScale17());
			stat.setString(26, abean.getScale18());
			stat.setString(27, abean.getScale19());
			stat.setString(28, abean.getScale20());
			stat.setString(29, abean.getScale21());
			stat.setString(30, abean.getScale22());
			stat.setString(31, abean.getDomain1Comments());
			stat.setString(32, abean.getDomain2Comments());
			stat.setString(33, abean.getDomain3Comments());
			stat.setString(34, abean.getDomain4Comments());
			stat.setString(35, abean.getProfile().getUID());
			stat.setInt(37, abean.getId());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("NLESDReferenceExternalBean updateNLESDReferenceExternalBean(NLESDReferenceExternalBean abean) " + e);
			throw new NLESDReferenceException("Can not update NLESDReferenceTeacherBean to DB.", e);
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
	public static NLESDReferenceExternalBean createNLESDReferenceExternalBean(ResultSet rs) {
		NLESDReferenceExternalBean abean = null;
		try {
			abean = new NLESDReferenceExternalBean();
			abean.setId(rs.getInt("REFERENCE_ID"));
			abean.setProvidedBy(rs.getString("PROVIDED_BY"));
			abean.setProvidedByPosition(rs.getString("PROVIDER_POSITION"));
			abean.setDateProvided(new java.util.Date(rs.getTimestamp("DATE_PROVIDED").getTime()));
			abean.setQ1(rs.getString("Q1"));
			abean.setQ2(rs.getString("Q2"));
			abean.setQ3(rs.getString("Q3"));
			abean.setQ4(rs.getString("Q4"));
			abean.setQ7(rs.getString("Q7"));
			abean.setQ7Comment(rs.getString("Q7_COMMENT"));
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
			abean.setScale15(rs.getString("SCALE15"));
			abean.setScale16(rs.getString("SCALE16"));
			abean.setScale17(rs.getString("SCALE17"));
			abean.setScale18(rs.getString("SCALE18"));
			abean.setScale19(rs.getString("SCALE19"));
			abean.setScale20(rs.getString("SCALE20"));
			abean.setScale21(rs.getString("SCALE21"));
			abean.setScale22(rs.getString("SCALE22"));
			abean.setDomain1Comments(rs.getString("DOMAIN_1_COMMENTS"));
			abean.setDomain2Comments(rs.getString("DOMAIN_2_COMMENTS"));
			abean.setDomain3Comments(rs.getString("DOMAIN_3_COMMENTS"));
			abean.setDomain4Comments(rs.getString("DOMAIN_4_COMMENTS"));
			abean.setReferenceScale(rs.getString("REFERENCE_SCALE"));
			abean.setProfile(ApplicantProfileManager.createApplicantProfileBean(rs));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
	public static NLESDReferenceExternalBean getLatestNLESDReferenceExternalBean(String sin ) throws NLESDReferenceException {
		NLESDReferenceExternalBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_nlesd_ref_chk_req_ext_l(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				eBean = createNLESDReferenceExternalBean(rs);
		}
		catch (SQLException e) {
			System.err.println("NLESDReferenceExternalBean getLatestNLESDReferenceExternalBean(String sin ) " + e);
			throw new NLESDReferenceException("Can not extract NLESDReferenceExternalBean from DB.", e);
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