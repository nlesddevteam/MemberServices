package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.CustomReportFieldBean;
import com.esdnl.fund3.bean.Fund3Exception;
public class CustomReportFieldManager {
	public static HashMap<String,CustomReportFieldBean> getCustomReportFields(Integer reportid) throws Fund3Exception {
		HashMap<String,CustomReportFieldBean> hmap = new HashMap<String,CustomReportFieldBean>();
		CustomReportFieldBean crfb = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_custom_report_fields(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, reportid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				crfb = createCustomReportFieldBean(rs);
				hmap.put(crfb.getFieldName(), crfb);
			}
		}
		catch (SQLException e) {
			System.err.println("HashMap<String,CustomReportFieldBean> getCustomReportFields(Integer reportid) " + e);
			throw new Fund3Exception("Can not extract custom report fields from DB: " + e);
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
		return hmap;
	}
	public static CustomReportFieldBean createCustomReportFieldBean(ResultSet rs) {
		CustomReportFieldBean abean = null;
		try {
			abean = new CustomReportFieldBean();
			abean.setId(rs.getInt("ID"));
			abean.setReportId(rs.getInt("REPORT_ID"));
			abean.setFieldName(rs.getString("FIELD_NAME"));
			abean.setFieldCriteria(rs.getString("FIELD_CRITERIA"));
			abean.setFieldUsed(rs.getInt("FIELD_USED"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}	
}