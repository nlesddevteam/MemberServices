package com.nlesd.bcs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.FileTypeBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class FileTypeManager {
	public static FileTypeBean getFIleTypeById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		FileTypeBean ebean = new FileTypeBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_file_type_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createFileTypeBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("FileTypeBean getFIleTypeById(Integer cid): "
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
		return ebean;
	}	
	public static FileTypeBean createFileTypeBean(ResultSet rs) {
		FileTypeBean abean = null;
		try {
				abean = new FileTypeBean();
				abean.setId(rs.getInt("ID"));
				abean.setFileName(rs.getString("FILENAME"));
				abean.setFileCategory(rs.getString("FILECATEGORY"));
				abean.setFieldName(rs.getString("FIELDNAME"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
