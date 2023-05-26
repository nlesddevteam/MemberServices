package com.awsd.admin.apps.categories.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import com.awsd.personnel.PersonnelCategory;
import com.esdnl.dao.DAOUtils;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class CategoriesManager {
	public static int addNewCategory(String catname, String catdesc) {

		Connection con = null;
		CallableStatement stat = null;
		Integer sid = 0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.category_pkg.add_new_category(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, catname.toUpperCase());
			stat.setString(3, catdesc.toUpperCase());

			stat.execute();
			sid= ((OracleCallableStatement) stat).getInt(1);
			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static void addNewCategory(String catname, String catdesc): " + e);
			
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
	public static PersonnelCategory getCategoryById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		PersonnelCategory ebean = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.category_pkg.get_category_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createPersonnelCategory(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static PersonnelCategory getCategoryById(Integer cid):"
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
	public static TreeMap<String,Integer> getCategoryRolesById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,Integer> list = new TreeMap<String,Integer>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.category_pkg.get_category_roles_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				list.put(rs.getString("ROLEID"), rs.getInt("PERSONNELCATID"));
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<String,Integer> getCategoryRolesById(Integer cid):"
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
	public static void removeRoleFromCategory(Integer cid, String rname) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.category_pkg.delete_role_from_category(?,?); end;");
			stat.setInt(1, cid);
			stat.setString(2, rname);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void removeRoleFromCategory(Integer cid, String rname):"
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
	public static ArrayList<String> getRolesDropdown(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<String>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.category_pkg.get_roles_dropdown(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				list.add(rs.getString("ROLE_ID"));
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<String> getRolesDropdown(Integer cid):"
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
	public static void addRoleCategoryy(int catid, String roleid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.category_pkg.add_role_category(?,?); end;");
			stat.setInt(1, catid);
			stat.setString(2, roleid);
			stat.execute();
			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static int addRoleCategoryy(int catid, String roleid): " + e);
			
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
	public static void updateCategoryRolePersonnel(Integer newcat, Integer pid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.category_pkg.update_profile_category(?,?); end;");
			stat.setInt(1, newcat);
			stat.setInt(2, pid);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("updateCategoryRolePersonnel(Integer newcat, Integer pid):"
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
	public static PersonnelCategory createPersonnelCategory(ResultSet rs)  {
		//new function that will replace old one once all queries have been updated with full data being returned in one query
		PersonnelCategory abean = null;
		try {
				abean = new PersonnelCategory(rs.getInt("PERSONNELCATEGORY_ID"),rs.getString("PERSONNELCATEGORY_NAME"), rs.getString("PERSONNELCATEGORY_DESC"));
		}
		catch (SQLException e) {
				abean = null;
				e.printStackTrace();
		}
		return abean;
	}
}
