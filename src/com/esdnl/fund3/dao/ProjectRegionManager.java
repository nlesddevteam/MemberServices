package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.bean.ProjectRegionBean;
public class ProjectRegionManager {
	public static Integer addNewProjectRegion(ProjectRegionBean pb) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_new_project_region(?,?,?); end;");
				stat.registerOutParameter(1, OracleTypes.INTEGER);
				stat.setInt(2,pb.getProjectId());
				stat.setInt(3,pb.getRegionId());
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
			System.err.println("Integer addNewProjectRegion " + e);
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
	public static ProjectRegionBean getProjectRegionById(int id) throws Fund3Exception {
		ProjectRegionBean mm = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_region_by_id(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					mm = createProjectRegionBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectRegionBean getProjectRegionById " + e);
			throw new Fund3Exception("Can not extract ProjectRegion from DB: " + e);
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
	public static TreeMap<String,Double> getProjectRegionByIdEdit(int id) throws Fund3Exception {
		TreeMap<String,Double> mm = new TreeMap<String,Double>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_regions_edit(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					if(rs.getString("PROJECT_ID")== null)
					{
						//no value for budget
						mm.put(rs.getString("DD_TEXT"), -1.00);
					}else{
						mm.put(rs.getString("DD_TEXT"), rs.getDouble("BUDGET_AMOUNT"));
					}
			}
		}
		catch (SQLException e) {
			System.err.println("ProjectRegionBean getProjectRegionByIdEdit " + e);
			throw new Fund3Exception("Can not extract ProjectRegion from DB: " + e);
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
	public static TreeMap<String,Double> getProjectRegionsByProject(int id) throws Fund3Exception {
		TreeMap<String,Double> mm = new TreeMap<String,Double>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_regions(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					mm.put(rs.getString("PRTEXT"), rs.getDouble("BUDGET_AMOUNT"));
					
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<String,Double> getProjectRegionsByProject(int id) " + e);
			throw new Fund3Exception("Can not extract ProjectRegion from DB: " + e);
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
	public static ArrayList<String> getProjectRegionsByProjectEmailList(int id) throws Fund3Exception {
		ArrayList<String> alist = new ArrayList<String>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_project_regions(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, id);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()) {
					if(rs.getString("PRTEXT").equals("EASTERN")){
						alist.add("EAST");
					}
					if(rs.getString("PRTEXT").equals("WESTERN")){
						alist.add("WEST");
					}
					if(rs.getString("PRTEXT").equals("CENTRAL")){
						alist.add("CENTRAL");
					}
					if(rs.getString("PRTEXT").equals("LABRADOR")){
						alist.add("LAB");
					}
					if(rs.getString("PRTEXT").equals("PROVINCIAL")){
						alist.add("EAST");
						alist.add("WEST");
						alist.add("CENTRAL");
						alist.add("LAB");
					}
				}
		}
		catch (SQLException e) {
			System.err.println(" ArrayList<String> getProjectRegionsByProjectEmailList(int id) " + e);
			throw new Fund3Exception("Can not extract ProjectRegion from DB: " + e);
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
		return alist;
	}	
	public static void deleteRegion(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.delete_fund3_region(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("deleteRegion(Integer tid) " + e);
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
	public static ProjectRegionBean createProjectRegionBean(ResultSet rs) {
		ProjectRegionBean abean = null;
		try {
				abean = new ProjectRegionBean();
				abean.setProjectId(rs.getInt("PROJECT_ID"));
				abean.setRegionId(rs.getInt("REGION_ID"));
				abean.setBudgetAmount(rs.getDouble("BUDGET_AMOUNT"));
				abean.setProjectId(rs.getInt("ID"));

		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}	
}
