package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorRouteListBean;
public class BussingContractorRouteListManager {
	public static ArrayList<BussingContractorRouteListBean> getContractorsRoutes(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorRouteListBean ebean = new BussingContractorRouteListBean();
		ArrayList<BussingContractorRouteListBean> list = new ArrayList<BussingContractorRouteListBean>(); 
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contractor_routes(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorRouteListBean(rs);
				list.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<BussingContractorRouteListBean> getContractorsRoutes(Integer cid):"
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
	public static BussingContractorRouteListBean createBussingContractorRouteListBean(ResultSet rs) {
		BussingContractorRouteListBean abean = null;
		try {
				abean = new BussingContractorRouteListBean();
				abean.setRouteName(rs.getString("ROUTENAME"));
				abean.setSchoolName(rs.getString("SCHOOL_NAME"));
				abean.setFirstName(rs.getString("FIRSTNAME"));
				abean.setLastName(rs.getString("LASTNAME"));
				abean.setvPlateNumber(rs.getString("VPLATENUMBER"));
				abean.setId(rs.getInt("ID"));
				abean.setContractId(rs.getInt("CONTRACTID"));}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}		
}
