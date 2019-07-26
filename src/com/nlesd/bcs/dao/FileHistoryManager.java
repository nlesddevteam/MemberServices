package com.nlesd.bcs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.TreeMap;

import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.FileHistoryBean;
import com.nlesd.bcs.bean.FileTypeBean;
import com.nlesd.bcs.constants.EntryTableConstant;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
public class FileHistoryManager {
	public static void deleteFile(FileTypeBean ftb, int objectid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.testing_pkg.delete_file(?,?,?); end;");
			stat.setInt(1,objectid);
			stat.setString(2,ftb.getFileCategory());
			stat.setString(3,ftb.getFieldName());
			stat.execute();
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteFile(FileTypeBean ftb, int objectid): "
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
		
	}
	public static TreeMap<Integer,FileHistoryBean> getFileHistory(int objectid, int filetype) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer, FileHistoryBean> hmap = new TreeMap<Integer, FileHistoryBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_file_history(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,objectid);
			stat.setInt(3,filetype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				FileHistoryBean fhb = createFileHistoryBean(rs);
				hmap.put(fhb.getId(),fhb);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<Integer,FileHistoryBean> getFileHistory(int objectid, int filetype): "
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
		return hmap;
	}
	public static int addFileHistory(FileHistoryBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		Integer sid=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_file_history(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, vbean.getFileName() );
			stat.setString(3, vbean.getFileAction());
			stat.setString(4, vbean.getActionBy());
			stat.setInt(5, vbean.getParentObjectId());
			stat.setInt(6, vbean.getParentObjectType());
			stat.execute();
			sid= ((OracleCallableStatement) stat).getInt(1);
			vbean.setId(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("int addFileHistory(FileHistoryBean vbean): "
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
	public static FileHistoryBean createFileHistoryBean(ResultSet rs) {
		FileHistoryBean abean = null;
		try {
				abean = new FileHistoryBean();
				abean.setId(rs.getInt("ID"));
				abean.setFileName(rs.getString("FILENAME"));
				abean.setFileAction(rs.getString("FILEACTION"));
				abean.setActionBy(rs.getString("ACTIONBY"));
				Timestamp ts= rs.getTimestamp("ACTIONDATE");
				if(ts != null){
					abean.setActionDate(new java.util.Date(rs.getTimestamp("ACTIONDATE").getTime()));
				}
				abean.setParentObjectId(rs.getInt("PARENTOBJECTID"));
				abean.setParentObjectType(rs.getInt("PARENTOBJECTTYPE"));
				if( rs.getString("FILECATEGORY").equals(EntryTableConstant.CONTRACTORVEHICLE.getDescription())) {
					abean.setPathType("V");
				}else {
					abean.setPathType("E");
				}
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
