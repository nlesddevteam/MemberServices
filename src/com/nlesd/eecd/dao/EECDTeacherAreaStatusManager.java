package com.nlesd.eecd.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.eecd.bean.EECDTeacherAreaStatusBean;
public class EECDTeacherAreaStatusManager {
	public static int addTeacherAreaStatus(EECDTeacherAreaStatusBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		Integer sid=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.add_ta_status(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getStatus().getValue());
			stat.setInt(3, vbean.getStatusBy());
			stat.setString(4, vbean.getStatusNotes());
			stat.setInt(5,vbean.getTeacherAreaId());
			stat.setInt(6,vbean.getPersonnelId());
			stat.execute();
			sid= ((OracleCallableStatement) stat).getInt(1);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addTeacherAreaStatus(EECDTeacherAreaStatusBean vbean): "
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
		return sid;
	}
}
