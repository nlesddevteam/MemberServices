package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemRouteBean;
import com.nlesd.bcs.bean.BussingContractorSystemRouteListBean;
public class BussingContractorSystemRouteManager {
	public static BussingContractorSystemRouteBean addBussingContractorSystemRoute(BussingContractorSystemRouteBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_route(?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, vbean.getRouteName());
			stat.setString(3, vbean.getRouteNotes());
			stat.setInt(4, vbean.getRouteSchool());
			stat.setString(5, vbean.getAddedBy());
			stat.setInt(6, vbean.getBoardOwned());
			stat.setInt(7, vbean.getVehicleType());
			stat.setInt(8, vbean.getVehicleSize());
			stat.execute();
			Integer sid= ((OracleCallableStatement) stat).getInt(1);
			vbean.setId(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static BussingContractorSystemRouteBean addBussingContractorSystemRoute(BussingContractorSystemRouteBean vbean): "
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
		return vbean;
	}
	public static ArrayList<BussingContractorSystemRouteBean> getRoutes() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorSystemRouteBean> list = new ArrayList<BussingContractorSystemRouteBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_routes; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorSystemRouteBean bean = createBussingContractorSystemRouteBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemRouteBean> getRoutes(): "
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
	public static BussingContractorSystemRouteBean getBussingContractorSystemRouteById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemRouteBean ebean = new BussingContractorSystemRouteBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_route_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemRouteBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemRouteBean getBussingContractorSystemRouteById(Integer cid):"
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
	public static BussingContractorSystemRouteBean updateBussingContractorSystemRoute(BussingContractorSystemRouteBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_route(?,?,?,?,?,?); end;");
			stat.setString(1, vbean.getRouteName());
			stat.setString(2, vbean.getRouteNotes());
			stat.setInt(3, vbean.getRouteSchool());
			stat.setInt(4, vbean.getId());
			stat.setInt(5, vbean.getVehicleType());
			stat.setInt(6, vbean.getVehicleSize());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemRouteBean updateBussingContractorSystemRoute(BussingContractorSystemRouteBean vbean): "
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
		return vbean;
	}
	public static ArrayList<BussingContractorSystemRouteBean> searchContractsByString(String searchBy, String searchFor){
		ArrayList<BussingContractorSystemRouteBean> list = new ArrayList<BussingContractorSystemRouteBean>();
		BussingContractorSystemRouteBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select bc.*,sc.SCHOOL_NAME,bcc.CONTRACTNAME from BCS_ROUTE bc left outer join school sc on bc.ROUTESCHOOL=sc.SCHOOL_ID");
			sb.append(" left outer join BCS_CONTRACT bcc on bc.ROUTECONTRACT=bcc.ID ");
			sb.append(" WHERE UPPER(" + getSearchByFieldName(searchBy) + ") like '%" + searchFor.toUpperCase() + "%'");
			sb.append(" and bc.ISDELETED='N' order by bc.ROUTENAME");
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorSystemRouteBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorSystemRouteBean> searchContractsByString(String searchBy, String searchFor) " + e);
			
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
	public static ArrayList<BussingContractorSystemRouteBean> searchRoutesByInteger(String searchBy,Integer searchInt){
		ArrayList<BussingContractorSystemRouteBean> list = new ArrayList<BussingContractorSystemRouteBean>();
		BussingContractorSystemRouteBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select bc.*,sc.SCHOOL_NAME,bcc.CONTRACTNAME from BCS_ROUTE bc left outer join school sc on bc.ROUTESCHOOL=sc.SCHOOL_ID");
			sb.append(" left outer join BCS_CONTRACT bcc on bc.ROUTECONTRACT=bcc.ID WHERE ");
			sb.append( getSearchByFieldName(searchBy) + " = " + searchInt);
			sb.append(" and bc.ISDELETED='N' order by bc.ROUTENAME");
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorSystemRouteBean(rs);
				list.add(ebean);
				}
			}
			catch (SQLException e) {
				System.err.println("static ArrayList<BussingContractorSystemRouteBean> searchRoutesByInteger(String searchBy, String searchFor, Integer searchInt): " + e);
				
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
	private static String getSearchByFieldName(String ddvalue){
			String searchby="";
			 if(ddvalue.equals("Name")){
				 searchby="bc.routename";
			 }else if(ddvalue.equals("School")){
				 searchby="bc.routeschool";
			 }else if(ddvalue.equals("Notes")){
				 searchby="bc.routenotes";
			 }
			
			return searchby;
	}
	public static boolean deleteRoute(Integer vid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_route(?); end;");
			stat.setInt(1, vid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("static boolean deleteRoute(Integer vid) " + e);
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
		return check;
	}
	public static ArrayList<BussingContractorSystemRouteBean> getBussingContractorSystemRouteByContactId(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemRouteBean ebean = new BussingContractorSystemRouteBean();
		ArrayList<BussingContractorSystemRouteBean> list = new ArrayList<BussingContractorSystemRouteBean>(); 
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_routes_by_contract(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemRouteBean(rs);
				list.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemRouteBean> getBussingContractorSystemRouteByContactId(Integer cid):"
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
	public static ArrayList<BussingContractorSystemRouteBean> getRoutesRegionalAdmin(int cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorSystemRouteBean> list = new ArrayList<BussingContractorSystemRouteBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_routes_reg(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorSystemRouteBean bean = createBussingContractorSystemRouteBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemRouteBean> getRoutes(): "
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
	public static ArrayList<BussingContractorSystemRouteBean> searchContractsByStringReg(String searchBy, String searchFor, Integer cid){
		ArrayList<BussingContractorSystemRouteBean> list = new ArrayList<BussingContractorSystemRouteBean>();
		BussingContractorSystemRouteBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select bc.*,sc.SCHOOL_NAME,bcc.CONTRACTNAME from BCS_ROUTE bc left outer join school sc on bc.ROUTESCHOOL=sc.SCHOOL_ID");
			sb.append(" left outer join BCS_CONTRACT bcc on bc.ROUTECONTRACT=bcc.ID ");
			sb.append(" WHERE UPPER(" + getSearchByFieldName(searchBy) + ") like '%" + searchFor.toUpperCase() + "%'");
			sb.append(" and bc.ISDELETED='N' and bc.BOARDOWNED=" + cid + " order by bc.ROUTENAME");
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorSystemRouteBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorSystemRouteBean> searchContractsByString(String searchBy, String searchFor) " + e);
			
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
	public static ArrayList<BussingContractorSystemRouteBean> searchRoutesByIntegerReg(String searchBy,Integer searchInt,Integer cid){
		ArrayList<BussingContractorSystemRouteBean> list = new ArrayList<BussingContractorSystemRouteBean>();
		BussingContractorSystemRouteBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select bc.*,sc.SCHOOL_NAME,bcc.CONTRACTNAME from BCS_ROUTE bc left outer join school sc on bc.ROUTESCHOOL=sc.SCHOOL_ID");
			sb.append(" left outer join BCS_CONTRACT bcc on bc.ROUTECONTRACT=bcc.ID WHERE ");
			sb.append( getSearchByFieldName(searchBy) + " = " + searchInt);
			sb.append(" and bc.ISDELETED='N' and bc.BOARDOWNED=" + cid + " order by bc.ROUTENAME");
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorSystemRouteBean(rs);
				list.add(ebean);
				}
			}
			catch (SQLException e) {
				System.err.println("static ArrayList<BussingContractorSystemRouteBean> searchRoutesByInteger(String searchBy, String searchFor, Integer searchInt): " + e);
				
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
	public static ArrayList<BussingContractorSystemRouteListBean> getRoutesList() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorSystemRouteListBean> list = new ArrayList<BussingContractorSystemRouteListBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_routes_list; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorSystemRouteListBean bean = createBussingContractorSystemRouteListBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemRouteListBean> getRoutesList(): "
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
	public static TreeMap<String,Integer> getRoutesListTM() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,Integer> list = new TreeMap<String,Integer>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_routes_list; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				list.put(rs.getString("ROUTENAME"),rs.getInt("ID"));
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<Integer,String> getRoutesListTM(): "
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
	public static ArrayList<BussingContractorSystemRouteListBean> getRoutesRegionalAdminList(int cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorSystemRouteListBean> list = new ArrayList<BussingContractorSystemRouteListBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_routes_list_region(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorSystemRouteListBean bean = createBussingContractorSystemRouteListBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemRouteListBean> getRoutesRegionalAdminList(int cid): "
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
	public static BussingContractorSystemRouteBean createBussingContractorSystemRouteBean(ResultSet rs) {
		BussingContractorSystemRouteBean abean = null;
		try {
				abean = new BussingContractorSystemRouteBean();
				abean.setId(rs.getInt("ID"));
				abean.setRouteName(rs.getString("ROUTENAME"));
				abean.setRouteNotes(rs.getString("ROUTENOTES"));
				abean.setRouteSchool(rs.getInt("ROUTESCHOOL"));
				abean.setAddedBy(rs.getString("ADDEDBY"));
				Timestamp ts= rs.getTimestamp("DATEADDED");
				if(ts != null){
					abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATEADDED").getTime()));
				}
				abean.setIsDeleted(rs.getString("ISDELETED"));
				abean.setRouteSchoolString(rs.getString("SCHOOL_NAME"));
				abean.setBoardOwned(rs.getInt("BOARDOWNED"));
				abean.setVehicleType(rs.getInt("VEHICLETYPE"));
				abean.setVehicleSize(rs.getInt("VEHICLESIZE"));
				abean.setContractBean(BussingContractorSystemContractManager.getBussingContractorSystemContractById(rs.getInt("ROUTECONTRACT")));
				//now we get the route runs
				abean.setRouteRuns(BussingContractorSystemRouteRunManager.getRouteRuns(abean.getId()));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
	public static BussingContractorSystemRouteBean getBussingContractorSystemRouteByName(String name) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemRouteBean ebean = new BussingContractorSystemRouteBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_route_by_name(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, name.toUpperCase());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemRouteBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemRouteBean getBussingContractorSystemRouteByName:"
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
	public static void updateBussingContractorSystemRouteVehicle(Integer rid,Integer vtype, Integer vsize) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_route_bus_imp(?,?,?); end;");
			stat.setInt(1, rid);
			stat.setInt(2, vtype);
			stat.setInt(3, vsize);
			stat.execute();
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("updateBussingContractorSystemRouteVehicle(Integer rid,Integer vtype, Integer vsize):"
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
	public static BussingContractorSystemRouteListBean createBussingContractorSystemRouteListBean(ResultSet rs) {
		BussingContractorSystemRouteListBean abean = null;
		try {
				abean = new BussingContractorSystemRouteListBean();
				abean.setId(rs.getInt("ID"));
				abean.setRouteName(rs.getString("ROUTENAME"));
				abean.setCompany(rs.getString("COMPANY"));
				abean.setFirstName(rs.getString("FIRSTNAME"));
				abean.setLastName(rs.getString("LASTNAME"));
				abean.setRouteRun(rs.getString("ROUTERUN"));
				abean.setRouteTime(rs.getString("ROUTETIME"));
				abean.setRouteSchools(rs.getString("ELEMENTS"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
