package com.nlesd.bcs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.DocumentAuditBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class DocumentAuditManager {
	public static ArrayList<DocumentAuditBean> getDocumentAudit(int numberofdays) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<DocumentAuditBean> list = new ArrayList<DocumentAuditBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_document_changes(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, numberofdays);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				DocumentAuditBean abean = new DocumentAuditBean();
				abean = createDocumentAuditBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<DocumentAuditBean> getDocumentAudit(int numberofdays): "
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
	public static DocumentAuditBean createDocumentAuditBean(ResultSet rs) {
		DocumentAuditBean abean = null;
		try {
				abean = new DocumentAuditBean();
				abean.setId(rs.getInt("ID"));
				abean.setFileName(rs.getString("FILENAME"));
				abean.setFileAction(rs.getString("FILEACTION"));
				abean.setActionBy(rs.getString("ACTIONBY"));
				abean.setActionDate(new java.util.Date(rs.getTimestamp("ACTIONDATE").getTime()));
				abean.setParentId(rs.getInt("PARENTOBJECTID"));
				abean.setDocumentName(rs.getString("DOCUMENTNAME"));
				abean.setFileCategory(rs.getString("FILECATEGORY"));
				abean.setObjectName(rs.getString("ONAME"));
				abean.setCompanyName(rs.getString("COMNAME"));
		}catch (SQLException e) {
				abean = null;
		}
		return abean;
	}		
}
