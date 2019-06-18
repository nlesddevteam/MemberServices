package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.bean.ProjectSchoolBean;
public class ProjectSchoolManager {
	public static Integer addNewProjectSchool(ProjectSchoolBean pb) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_new_project_school(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setInt(2,pb.getProjectId());
				stat.setInt(3,pb.getSchoolId());
				stat.setDouble(4,pb.getBudgetAmount());
				stat.execute();
				id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewProjectSchool " + e);
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
	public static ProjectSchoolBean getProjectSchoolById(int id) throws Fund3Exception {
		ProjectSchoolBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_school_by_id(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					mm = createProjectSchoolBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectSchoolBean getProjectRegionById " + e);
			throw new Fund3Exception("Can not extract ProjectSchool from DB: " + e);
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
		return mm;
	}
	public static ArrayList<ProjectSchoolBean> getProjectSchoolByProjectId(int id) throws Fund3Exception {
		ArrayList<ProjectSchoolBean> list = new ArrayList<ProjectSchoolBean>();
		ProjectSchoolBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_school_by_pid(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					mm = createProjectSchoolBean(rs);
					list.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<ProjectSchoolBean> getProjectSchoolByProjectId(int id) " + e);
			throw new Fund3Exception("Can not extract ProjectSchool from DB: " + e);
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
	public static void deleteSchool(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.delete_fund3_school(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteSchool(Integer tid) " + e);
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
	public static ProjectSchoolBean createProjectSchoolBean(ResultSet rs) {
		ProjectSchoolBean abean = null;
		try {
				abean = new ProjectSchoolBean();
				abean.setProjectId(rs.getInt("PROJECT_ID"));
				abean.setSchoolId(rs.getInt("SCHOOL_ID"));
				abean.setBudgetAmount(rs.getDouble("BUDGET_AMOUNT"));
				abean.setProjectId(rs.getInt("ID"));
				abean.setSchool(rs.getString("SCHOOL_NAME"));

		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}
