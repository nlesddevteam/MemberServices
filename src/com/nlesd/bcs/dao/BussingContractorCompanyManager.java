package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorCompanyBean;
public class BussingContractorCompanyManager {
	public static BussingContractorCompanyBean addBussingContractorCompany(BussingContractorCompanyBean bpbbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_cont_company(?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, bpbbean.getContractorId());
			stat.setString(3, bpbbean.gettRegular());
			stat.setString(4, bpbbean.gettAlternate());
			stat.setString(5, bpbbean.gettParent());
			stat.setString(6, bpbbean.getCrSameAs());
			stat.setString(7, bpbbean.getCrFirstName());
			stat.setString(8, bpbbean.getCrLastName());
			stat.setString(9, bpbbean.getCrPhoneNumber());
			stat.setString(10, bpbbean.getCrEmail());
			stat.setString(11, bpbbean.getToSameAs());
			stat.setString(12, bpbbean.getToFirstName());
			stat.setString(13, bpbbean.getToLastName());
			stat.setString(14, bpbbean.getToPhoneNumber());
			stat.setString(15, bpbbean.getToEmail());
			stat.execute();
			Integer sid= ((OracleCallableStatement) stat).getInt(1);
			bpbbean.setId(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorCompanyBean addBussingContractorCompany(BussingContractorCompanyBean bpbbean): "
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
		return bpbbean;
	}
	public static BussingContractorCompanyBean getBussingContractorCompanyById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorCompanyBean ebean = new BussingContractorCompanyBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_cont_company_by_cid(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorCompanyBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorBean getBussingContractorById(Integer cid): "
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
	public static BussingContractorCompanyBean updateBussingContractorCompany(BussingContractorCompanyBean bpbbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_cont_company(?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, bpbbean.getContractorId());
			stat.setString(2, bpbbean.gettRegular());
			stat.setString(3, bpbbean.gettAlternate());
			stat.setString(4, bpbbean.gettParent());
			stat.setString(5, bpbbean.getCrSameAs());
			stat.setString(6, bpbbean.getCrFirstName());
			stat.setString(7, bpbbean.getCrLastName());
			stat.setString(8, bpbbean.getCrPhoneNumber());
			stat.setString(9, bpbbean.getCrEmail());
			stat.setString(10, bpbbean.getToSameAs());
			stat.setString(11, bpbbean.getToFirstName());
			stat.setString(12, bpbbean.getToLastName());
			stat.setString(13, bpbbean.getToPhoneNumber());
			stat.setString(14, bpbbean.getToEmail());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorCompanyBean updateBussingContractorCompany(BussingContractorCompanyBean bpbbean): "
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
		return bpbbean;
	}
	public static BussingContractorCompanyBean updateBussingContractorCompanyArc(BussingContractorCompanyBean bpbbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_cont_company_arc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, bpbbean.getContractorId());
			stat.setString(3, bpbbean.gettRegular());
			stat.setString(4, bpbbean.gettAlternate());
			stat.setString(5, bpbbean.gettParent());
			stat.setString(6, bpbbean.getCrSameAs());
			stat.setString(7, bpbbean.getCrFirstName());
			stat.setString(8, bpbbean.getCrLastName());
			stat.setString(9, bpbbean.getCrPhoneNumber());
			stat.setString(10, bpbbean.getCrEmail());
			stat.setString(11, bpbbean.getToSameAs());
			stat.setString(12, bpbbean.getToFirstName());
			stat.setString(13, bpbbean.getToLastName());
			stat.setString(14, bpbbean.getToPhoneNumber());
			stat.setString(15, bpbbean.getToEmail());
			stat.setInt(16, bpbbean.getId());
			stat.execute();
			//Integer sid= ((OracleCallableStatement) stat).getInt(1);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorCompanyBean updateBussingContractorCompanyArc(BussingContractorCompanyBean bpbbean): "
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
		return bpbbean;
	}		
	public static BussingContractorCompanyBean createBussingContractorCompanyBean(ResultSet rs) {
		BussingContractorCompanyBean abean = null;
		try {
				abean = new BussingContractorCompanyBean();
				abean.setId(rs.getInt("ID"));
				abean.setContractorId(rs.getInt("CONTRACTORID"));
				abean.settRegular(rs.getString("TREGULAR"));
				abean.settAlternate(rs.getString("TALTERNATE"));
				abean.settParent(rs.getString("TPARENT"));
				abean.setCrSameAs(rs.getString("CRSAMEAS"));
				abean.setCrFirstName(rs.getString("CRFIRSTNAME"));
				abean.setCrLastName(rs.getString("CRLASTNAME"));
				abean.setCrPhoneNumber(rs.getString("CRPHONENUMBER"));
				abean.setCrEmail(rs.getString("CREMAIL"));
				abean.setToSameAs(rs.getString("TOSAMEAS"));
				abean.setToFirstName(rs.getString("TOFIRSTNAME"));
				abean.setToLastName(rs.getString("TOLASTNAME"));
				abean.setToPhoneNumber(rs.getString("TOPHONENUMBER"));
				abean.setToEmail(rs.getString("TOEMAIL"));
				
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
