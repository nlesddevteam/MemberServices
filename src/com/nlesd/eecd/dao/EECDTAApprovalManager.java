package com.nlesd.eecd.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.eecd.bean.EECDTAApprovalBean;
public class EECDTAApprovalManager {
	public static ArrayList<EECDTAApprovalBean> getAdminApprovals(int sid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<EECDTAApprovalBean> list = new ArrayList<EECDTAApprovalBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.eecd_pkg.get_eecd_approvals_admin(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, sid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				EECDTAApprovalBean abean = new EECDTAApprovalBean();
				abean = createEECDTAApprovalBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<EECDAreaBean> getAllEECDAreas(): "
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
		return list;
	}
	public static EECDTAApprovalBean createEECDTAApprovalBean(ResultSet rs) {
		EECDTAApprovalBean abean = null;
		try {
				abean = new EECDTAApprovalBean();
				abean.setTeacherAreaId(rs.getInt("ID"));
				abean.setTeacherPersonnelId(rs.getInt("PERSONNEL_ID"));
				abean.setAreaId(rs.getInt("AREA_ID"));
				abean.setTeacherName(rs.getString("PERSONNEL_FIRSTNAME") + " " + rs.getString("PERSONNEL_LASTNAME"));
				abean.setAreaDescription(rs.getString("AREA_DESCRIPTION"));
				if(!(rs.getTimestamp("DATE_SUBMITTED") ==  null)){
					abean.setDateSubmitted(new java.util.Date(rs.getTimestamp("DATE_SUBMITTED").getTime()));
				}
				
			}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
