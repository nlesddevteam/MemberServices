package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.bean.ProjectDocumentsBean;
public class ProjectDocumentManager {
	public static Integer addNewProjectDocument(ProjectDocumentsBean pdb) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_fund3_project_document(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setInt(2,pdb.getProjectId());
			stat.setString(3, pdb.getFileName());
			stat.setString(4, pdb.getoFileName());
			stat.setString(5, pdb.getAddedBy());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewProjectDocument(ProjectDocumentsBean pdb) " + e);
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
		return id;
	}	
	public static ArrayList<ProjectDocumentsBean> getProjectFilesByProjectId(int id) throws Fund3Exception {
		ArrayList<ProjectDocumentsBean>list = new ArrayList<ProjectDocumentsBean>();
		ProjectDocumentsBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_files_by_pid(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					mm = createProjectDocumentsBean(rs);
					list.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<ProjectDocumentsBean> getProjectFilesByProjectId(int id) " + e);
			throw new Fund3Exception("Can not extract ProjectDocuments from DB: " + e);
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
		return list;
	}
	public static void deleteProjectDocument(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.delete_fund3_document(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteProjectDocument(Integer tid) " + e);
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
	public static ProjectDocumentsBean createProjectDocumentsBean(ResultSet rs) {
		ProjectDocumentsBean abean = null;
		try {
				abean = new ProjectDocumentsBean();
				abean.setId(rs.getInt("ID"));
				abean.setProjectId(rs.getInt("PROJECT_ID"));
				abean.setFileName(rs.getString("FILE_NAME"));
				abean.setoFileName(rs.getString("O_FILE_NAME"));
				abean.setAddedBy(rs.getString("ADDED_BY"));
				Timestamp ts=rs.getTimestamp("DATE_ADDED");
				if(ts != null){
					abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATE_ADDED").getTime()));
				}					
				abean.setFileDeleted(rs.getString("FILE_DELETED"));
				abean.setDeletedBy(rs.getString("DELETED_BY"));
				ts=rs.getTimestamp("DATE_DELETED");
				if(ts != null){
					abean.setDateDeleted(new java.util.Date(rs.getTimestamp("DATE_DELETED").getTime()));
				}					
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}
