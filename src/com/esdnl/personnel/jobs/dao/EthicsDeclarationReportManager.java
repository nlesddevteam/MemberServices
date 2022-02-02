package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.EthicsDeclarationReportBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class EthicsDeclarationReportManager {
	public static ArrayList<EthicsDeclarationReportBean> getLatestEthicsDeclarationsByDays(int numdays) {
		ArrayList<EthicsDeclarationReportBean> v_opps = null;
		EthicsDeclarationReportBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			v_opps = new ArrayList<EthicsDeclarationReportBean>(3);
		
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_latest_ethics_dec(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, numdays);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				eBean = createEthicsDeclarationReportBean(rs);
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("static ArrayList<EthicsDeclarationReportBean> getLatestEthicsDeclarationsByDays(int numdays) : "
					+ e);
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
		
		return v_opps;
	}
	public static EthicsDeclarationReportBean createEthicsDeclarationReportBean (ResultSet rs) {

		EthicsDeclarationReportBean abean = null;
		try {
			abean = new EthicsDeclarationReportBean();
			
			abean.setEmployeeName(rs.getString("LASTNAME") + ", " + rs.getString("FIRSTNAME"));
			if(rs.getString("LOCATION") == null) {
				abean.setEmployeeLocation("");
			}else {
				abean.setEmployeeLocation(rs.getString("LOCATION"));
			}
			if(rs.getTimestamp("CREATED_DATE") ==  null) {
				abean.setCreatedDate(null);
			}else {
				abean.setCreatedDate(new Date(rs.getTimestamp("CREATED_DATE").getTime()));
			}
			abean.setDocumentId(rs.getInt("DOCUMENT_ID"));
			abean.setEmployeeSin(rs.getString("SIN"));
			if(rs.getString("EMAIL") == null) {
				//no link to sds use their applicant email
				abean.setEmployeeEmail(rs.getString("APEMAIL"));
			}else {
				//linked to sds use their sds email
				abean.setEmployeeEmail(rs.getString("EMAIL"));
			}
			
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}
