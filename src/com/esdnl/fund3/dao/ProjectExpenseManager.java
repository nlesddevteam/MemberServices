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
import com.esdnl.fund3.bean.ProjectExpensesBean;
public class ProjectExpenseManager {
	public static Integer addNewProjectExpense(ProjectExpensesBean pb) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_new_project_expense(?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setInt(2,pb.getProjectId());
				stat.setString(3,pb.getExpenseDetails());
				stat.execute();
				id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewProjectExpense(ProjectExpensesBean pb)" + e);
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
	public static ProjectExpensesBean getProjectExpenseById(int id) throws Fund3Exception {
		ProjectExpensesBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_expense_by_id(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					mm = createProjectExpensesBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectExpensesBean getProjectExpenseById(int id) " + e);
			throw new Fund3Exception("Can not extract ProjectExpenses from DB: " + e);
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
	public static ArrayList<ProjectExpensesBean> getProjectExpenseByProjectId(int id) throws Fund3Exception {
		ArrayList<ProjectExpensesBean>list = new ArrayList<ProjectExpensesBean>();
		ProjectExpensesBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_expense_by_pid(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					mm = createProjectExpensesBean(rs);
					list.add(mm);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<ProjectExpensesBean> getProjectExpenseByProjectId(int id) " + e);
			throw new Fund3Exception("Can not extract ProjectExpenses from DB: " + e);
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
	public static void deleteExpenses(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.delete_fund3_expenses(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteExpenses(Integer tid) " + e);
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
	public static ProjectExpensesBean createProjectExpensesBean(ResultSet rs) {
		ProjectExpensesBean abean = null;
		try {
				abean = new ProjectExpensesBean();
				abean.setId(rs.getInt("ID"));
				abean.setProjectId(rs.getInt("PROJECT_ID"));
				abean.setExpenseDetails(rs.getString("EXPENSE_DETAILS"));

		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
}