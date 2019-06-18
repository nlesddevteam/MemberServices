package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemRouteContractBean;
public class BussingContractorSystemRouteContractManager {
	public static BussingContractorSystemRouteContractBean addBussingContractorSystemRouteContract(BussingContractorSystemRouteContractBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_route_contract(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getRouteId());
			stat.setInt(3, vbean.getContractId());
			stat.setString(4, vbean.getAddedBy());
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
			System.err.println("static BussingContractorSystemRouteBean addBussingContractorSystemRouteContract(BussingContractorSystemRouteContractBean vbean) : "
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
	public static boolean removeRouteContract(Integer cid, Integer rid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.remove_route_contract(?,?); end;");
			stat.setInt(1, cid);
			stat.setInt(2, rid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("static boolean removeRouteContract(Integer cid, Integer rid)  " + e);
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
	public static BussingContractorSystemRouteContractBean getBussingContractorSystemRouteContractById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemRouteContractBean ebean = new BussingContractorSystemRouteContractBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_route_contract(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemRouteContractBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static BussingContractorSystemRouteContractBean getBussingContractorSystemRouteContractById(Integer cid):"
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
	public static BussingContractorSystemRouteContractBean getBussingContractorSystemRouteContractByCR(Integer cid,Integer rid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemRouteContractBean ebean = new BussingContractorSystemRouteContractBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_route_contract_by_cr(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.setInt(3, rid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemRouteContractBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static BussingContractorSystemRouteContractBean getBussingContractorSystemRouteContractByCR(Integer cid,Integer rid):"
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
	public static BussingContractorSystemRouteContractBean createBussingContractorSystemRouteContractBean(ResultSet rs) {
		BussingContractorSystemRouteContractBean abean = null;
		try {
				abean = new BussingContractorSystemRouteContractBean();
				abean.setId(rs.getInt("ID"));
				abean.setRouteId(rs.getInt("ROUTEID"));
				abean.setContractId(rs.getInt("CONTRACTID"));
				abean.setAddedBy(rs.getString("ADDEDBY"));
				Timestamp ts= rs.getTimestamp("DATEADDED");
				if(ts != null){
					abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATEADDED").getTime()));
				}
				abean.setIsDeleted(rs.getString("ISDELETED"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
