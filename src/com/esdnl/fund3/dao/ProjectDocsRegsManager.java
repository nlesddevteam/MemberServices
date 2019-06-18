package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.ProjectDocsRegsBean;
public class ProjectDocsRegsManager {
	public static Integer addNewProjectDocsRegs(ProjectDocsRegsBean pb) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_fund3_project_document_r(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setInt(2,pb.getProjectId());
				stat.setInt(3,pb.getRegionId());
				stat.setDouble(4,pb.getDocumentId());
				stat.execute();
				id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewProjectDocsRegs(ProjectDocsRegsBean pb) " + e);
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
	public static ProjectDocsRegsBean createProjectDocsRegsBean(ResultSet rs) {
		ProjectDocsRegsBean abean = null;
		try {
				abean = new ProjectDocsRegsBean();
				abean.setProjectId(rs.getInt("PROJECT_ID"));
				abean.setRegionId(rs.getInt("REGION_ID"));
				abean.setDocumentId(rs.getInt("DOCUMENT_ID"));
				abean.setProjectId(rs.getInt("ID"));

		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}		
}
