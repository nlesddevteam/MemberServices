package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.DropdownItemBean;
public class DropdownManager {
	public static HashMap<Integer,String> getDropdownValues(int id) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		HashMap<Integer, String> hmap = new HashMap<Integer, String>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_dropdown_items_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				hmap.put(rs.getInt("ID"), rs.getString("DD_TEXT"));
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("HashMap<Integer,String> getDropdownValues(int id): "
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
	public static TreeMap<Integer,String> getDropdownValuesTM(int id) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer, String> hmap = new TreeMap<Integer, String>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_dropdown_items_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				hmap.put(rs.getInt("ID"), rs.getString("DD_TEXT"));
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<Integer,String> getDropdownValuesTM(int id): "
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
	public static TreeMap<Integer,String> getDropdownValuesTMP(int id) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer, String> hmap = new TreeMap<Integer, String>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_dropdown_items_by_pid(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				hmap.put(rs.getInt("ID"), rs.getString("DD_TEXT"));
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<Integer,String> getDropdownValuesTMP(int id): "
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
	public static ArrayList<DropdownItemBean> getDropdownValuesArrayList(int id) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<DropdownItemBean> list = new ArrayList<DropdownItemBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_dropdown_items_by_id_s(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				DropdownItemBean dib = new DropdownItemBean();
				dib.setId(rs.getInt("ID"));
				dib.setText(rs.getString("DD_TEXT"));
				list.add(dib);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<DropdownItemBean> getDropdownValuesArrayList(int id): "
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
	public static String getDropdownItemText(int id) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		String ddText="";
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_dropdown_item_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ddText = rs.getString("DD_TEXT");
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("String getDropdownItemText(int id): "
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
		return ddText;
	}
	public static TreeMap<String,Integer>getSchools()  {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,Integer> schoollist = new TreeMap<String,Integer>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.BCS_PKG.get_schools; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while(rs.next()) {
				schoollist.put(rs.getString("SCHOOL_NAME"),rs.getInt("SCHOOL_ID"));
			}
		}
		catch (SQLException e) {
			System.err.println("static TreeMap<String,Integer>getSchools()  " + e);
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
