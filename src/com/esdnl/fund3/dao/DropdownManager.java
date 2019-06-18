package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.DropdownBean;
import com.esdnl.fund3.bean.DropdownItemBean;
import com.esdnl.fund3.bean.Fund3Exception;
public class DropdownManager {
	public static Vector<DropdownBean> getDropdowns() throws Fund3Exception {
		Vector<DropdownBean> dropdowns = null;
		DropdownBean dropdown = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			dropdowns= new Vector<DropdownBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_dropdowns; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				dropdown = createDropdownBean(rs);
				dropdowns.add(dropdown);
			}
		}
		catch (SQLException e) {
			System.err.println("static Vector<DropdownBean> getDropdowns() " + e);
			throw new Fund3Exception("Can not extract dropdowns from DB: " + e);
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
		return dropdowns;
	}
	public static ArrayList<DropdownItemBean> getDropdownItems(Integer ddid) throws Fund3Exception {
		ArrayList<DropdownItemBean> dropdownitems = new ArrayList<DropdownItemBean>();
		DropdownItemBean dropdownitem = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_dropdown_items(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, ddid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				dropdownitem = createDropdownItemBean(rs);
				dropdownitems.add(dropdownitem);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<DropdownItemBean> getDropdownItems(Integer ddid) " + e);
			throw new Fund3Exception("Can not extract dropdowns from DB: " + e);
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
		return dropdownitems;
	}
	public static DropdownItemBean getDropdownItemById(Integer ddid) throws Fund3Exception {
		DropdownItemBean dropdownitem = new DropdownItemBean();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_dropdown_item_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, ddid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next()) {
				dropdownitem = createDropdownItemBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("DropdownItemBean getDropdownItemById(Integer ddid) " + e);
			throw new Fund3Exception("Can not extract dropdowns from DB: " + e);
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
		return dropdownitem;
	}
public static Integer addDropdownItem(DropdownItemBean ebean) throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		Integer id=0;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_fund3_dropdown_item(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, ebean.getDdText());
			stat.setInt(3, ebean.getIsActive());
			stat.setInt(4, ebean.getDdId());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			System.err.println("Integer addDropdownItem(DropdownItemBean ebean) " + e);
			throw new Fund3Exception("Can not add dropdown item to DB: " + e);
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
	public static void updateDropdownItem(DropdownItemBean ebean) throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.fund3_pkg.update_fund3_dropdown_item(?,?,?); end;");
			stat.setString(1, ebean.getDdText());
			stat.setInt(2, ebean.getIsActive());
			stat.setInt(3, ebean.getId());
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("updateDropdownItem(DropdownItemBean ebean) " + e);
			throw new Fund3Exception("Can not update dropdown item to DB: " + e);
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
	public static void deleteDropdownItem(Integer id) throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.fund3_pkg.delete_fund3_dropdown_item(?); end;");
			stat.setInt(1,id);
			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void deleteDropdownItem(Integer id) " + e);
			throw new Fund3Exception("Can not delete dropdown item to DB: " + e);
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
	public static DropdownBean createDropdownBean(ResultSet rs) {
		DropdownBean abean = null;
		try {
			abean = new DropdownBean();
			abean.setId(rs.getInt("ID"));
			abean.setDdName(rs.getString("DD_NAME"));
			abean.setIsActive(rs.getInt("IS_ACTIVE"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
	public static DropdownItemBean createDropdownItemBean(ResultSet rs) {
		DropdownItemBean abean = null;
		try {
			abean = new DropdownItemBean();
			abean.setId(rs.getInt("ID"));
			abean.setDdText(rs.getString("DD_TEXT"));
			abean.setIsActive(rs.getInt("IS_ACTIVE"));
			abean.setDdId(rs.getInt("DD_ID"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
	public static TreeMap<String,Integer>getSchoolsByRegion(String ddid) throws Fund3Exception {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,Integer> schoollist = new TreeMap<String,Integer>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_schools(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, ddid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next()) {
				schoollist.put(rs.getString("SCHOOL_NAME"),rs.getInt("SCHOOL_ID"));
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<Integer,String>getSchoolsByRegion(Integer ddid) " + e);
			throw new Fund3Exception("Can not extract schools from DB: " + e);
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
		return schoollist;
	}	
}