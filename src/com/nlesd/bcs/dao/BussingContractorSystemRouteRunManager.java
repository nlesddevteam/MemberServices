package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemRouteRunBean;
public class BussingContractorSystemRouteRunManager {
	public static BussingContractorSystemRouteRunBean addBussingContractorSystemRouteRun(BussingContractorSystemRouteRunBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_route_run(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, vbean.getRouteTime());
			stat.setInt(3, vbean.getRouteId());
			stat.setString(4, vbean.getRouteRun());
			stat.setString(5, vbean.getAddedBy());
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
			System.err.println("static BussingContractorSystemRouteRunBean addBussingContractorSystemRouteRun(BussingContractorSystemRouteRunBean vbean): "
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
	public static ArrayList<BussingContractorSystemRouteRunBean> getRouteRuns(int routeid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorSystemRouteRunBean> list = new ArrayList<BussingContractorSystemRouteRunBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_route_runs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, routeid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorSystemRouteRunBean bean = createBussingContractorSystemRouteRunBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<BussingContractorSystemRouteRunBean> getRouteRuns(int routeid): "
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
	public static boolean deleteRouteRun(Integer vid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_route_run(?); end;");
			stat.setInt(1, vid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("static boolean deleteRouteRun(Integer vid):" + e);
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
	public static BussingContractorSystemRouteRunBean getRouteRunById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemRouteRunBean ebean = new BussingContractorSystemRouteRunBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_route_run_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemRouteRunBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static BussingContractorSystemRouteRunBean getRouteRunById(Integer cid):"
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
	public static void updateBussingContractorSystemRouteRun(BussingContractorSystemRouteRunBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_route_run(?,?,?); end;");
			stat.setString(1, vbean.getRouteTime());
			stat.setInt(2, vbean.getId());
			stat.setString(3, vbean.getRouteRun());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("updateBussingContractorSystemRouteRun(BussingContractorSystemRouteRunBean vbean): "
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
	public static BussingContractorSystemRouteRunBean getRouteRunByRun(Integer cid,String srun) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemRouteRunBean ebean = new BussingContractorSystemRouteRunBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_route_run_by_run(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.setString(3, srun);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemRouteRunBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemRouteRunBean getRouteRunByRun(Integer cid,String srun):"
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
	public static BussingContractorSystemRouteRunBean createBussingContractorSystemRouteRunBean(ResultSet rs) {
		BussingContractorSystemRouteRunBean abean = null;
		try {
				abean = new BussingContractorSystemRouteRunBean();
				abean.setId(rs.getInt("ID"));
				abean.setRouteTime(rs.getString("ROUTETIME"));
				abean.setRouteId(rs.getInt("ROUTEID"));
				abean.setRouteRun(rs.getString("ROUTERUN"));
				abean.setAddedBy(rs.getString("ADDEDBY"));
				Timestamp ts= rs.getTimestamp("DATEADDED");
				if(ts != null){
					abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATEADDED").getTime()));
				}
				//now get the school list
				
				HashMap<Integer,String> list = BussingContractorSystemRouteRunSchoolManager.getRunSchoolsDD(abean.getId());
				abean.setRunSchoolsDD(list);
				String schools="";
				for (Map.Entry<Integer, String> entry : list.entrySet()) {
					if(schools.length() > 0){
						schools += ", " + entry.getValue();
					}else{
						schools=entry.getValue();
					}
				}
				abean.setRunSchools(schools);
				//abean.setRunSchools(BussingContractorSystemRouteRunSchoolManager.getRunSchools(abean.getId()));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
