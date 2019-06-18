package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSecurityArcBean;
public class BussingContractorSecurityArcManager {
	public static BussingContractorSecurityArcBean addBussingContractorSecurityArc(BussingContractorSecurityArcBean bpbbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_sec_rec_arc(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, bpbbean.getId());
			stat.setInt(3, bpbbean.getSecurityId());
			stat.setString(4, bpbbean.getOldPassword());
			stat.setString(5, bpbbean.getNewPassword());
			stat.execute();
			Integer sid= ((OracleCallableStatement) stat).getInt(1);
			bpbbean.setId(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorBean addBussingContractorSecurityArc(BussingContractorSecurityArcBean bpbbean): "
					+ e);
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
		return bpbbean;
	}
	public static BussingContractorSecurityArcBean createBussingContractorSecurityBean(ResultSet rs) {
		BussingContractorSecurityArcBean abean = null;
		try {
				abean = new BussingContractorSecurityArcBean();
				abean.setId(rs.getInt("ID"));
				abean.setSecurityId(rs.getInt("SECURITY_ID"));
				abean.setOldPassword(rs.getString("OLD_PASSWORD"));
				abean.setNewPassword(rs.getString("NEW_PASSWORD"));
				abean.setDateChanged(new java.util.Date(rs.getTimestamp("DATE_CHANGED").getTime()));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}		
}
