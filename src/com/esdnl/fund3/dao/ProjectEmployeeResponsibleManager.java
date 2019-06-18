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
import com.esdnl.fund3.bean.ProjectEmployeeResponsibleBean;
public class ProjectEmployeeResponsibleManager {
	public static Integer addNewEmployeeResponsible(ProjectEmployeeResponsibleBean pb) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_new_employee_res(?,?,?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setInt(2,pb.getProjectId());
				stat.setInt(3,pb.getRegionId());
				stat.setString(4,pb.getEmployeeName());
				stat.setString(5,pb.getEmployeeEmail());
				stat.setString(6,pb.getEmployeePhone());
				stat.execute();
				id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewEmployeeResponsible(ProjectEmployeeResponsibleBean pb) " + e);
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
	public static ProjectEmployeeResponsibleBean getProjectEmployeeResponsibleById(int id) throws Fund3Exception {
		ProjectEmployeeResponsibleBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_employee_res_id(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					mm = createProjectEmployeeResponsibleBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectEmployeeResponsibleBean getProjectEmployeeResponsibleById(int id) " + e);
			throw new Fund3Exception("Can not extract ProjectEmployeeResponsible from DB: " + e);
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
	public static ArrayList<ProjectEmployeeResponsibleBean> getProjectEmployeesResById(int id) throws Fund3Exception {
		ArrayList<ProjectEmployeeResponsibleBean>list = new ArrayList<ProjectEmployeeResponsibleBean>();
		ProjectEmployeeResponsibleBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_emp_res_by_pid(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					mm = createProjectEmployeeResponsibleBean(rs);
					list.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<ProjectEmployeeResponsibleBean> getProjectEmployeesResById(int id) " + e);
			throw new Fund3Exception("Can not extract ProjectEmployeeResponsible from DB: " + e);
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
	public static void deleteEmployeesResponsible(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.delete_fund3_emp_res(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteEmployeesResponsible(Integer tid) " + e);
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
	public static ProjectEmployeeResponsibleBean createProjectEmployeeResponsibleBean(ResultSet rs) {
		ProjectEmployeeResponsibleBean abean = null;
		try {
				abean = new ProjectEmployeeResponsibleBean();
				abean.setId(rs.getInt("ID"));
				abean.setProjectId(rs.getInt("PROJECT_ID"));
				abean.setEmployeeName(rs.getString("EMPLOYEE_NAME"));
				abean.setEmployeeEmail(rs.getString("EMPLOYEE_EMAIL"));
				abean.setEmployeePhone(rs.getString("EMPLOYEE_PHONE"));
				abean.setRegionText(rs.getString("PFTEXT"));
				abean.setRegionId(rs.getInt("REGION_ID"));

		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}