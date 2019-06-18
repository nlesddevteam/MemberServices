package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemEmployeeTrainingBean;
public class BussingContractorSystemEmployeeTrainingManager {
	public static BussingContractorSystemEmployeeTrainingBean addEmployeeTraining(BussingContractorSystemEmployeeTrainingBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_emp_training(?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getTrainingType());
			if(vbean.getTrainingDate() == null){
				stat.setTimestamp(3, null);
			}else{
				stat.setTimestamp(3, new Timestamp(vbean.getTrainingDate().getTime()));
			}
			if(vbean.getExpiryDate() == null){
				stat.setTimestamp(4, null);
			}else{
				stat.setTimestamp(4, new Timestamp(vbean.getExpiryDate().getTime()));
			}
			stat.setString(5, vbean.getNotes());
			stat.setInt(6, vbean.getFkEmployee());
			stat.setString(7, vbean.gettDocument());
			stat.setInt(8,vbean.getTrainingLength());
			stat.setString(9, vbean.getProvidedBy());
			stat.setString(10,vbean.getLocation());
			stat.execute();
			Integer sid= ((OracleCallableStatement) stat).getInt(1);
			vbean.setPk(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println(" BussingContractorSystemEmployeeTrainingBean addEmployeeTraining(BussingContractorSystemEmployeeTrainingBean vbean): "
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
	public static ArrayList<BussingContractorSystemEmployeeTrainingBean> getEmployeeTrainingById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemEmployeeTrainingBean ebean = new BussingContractorSystemEmployeeTrainingBean();
		ArrayList<BussingContractorSystemEmployeeTrainingBean> list = new ArrayList<BussingContractorSystemEmployeeTrainingBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_emp_training(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemEmployeeTrainingBean(rs);
				list.add(ebean);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemEmployeeTrainingBean> getEmployeeTrainingById(Integer cid):"
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
	public static boolean deleteEmployeeTraining(Integer vid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_emp_training(?); end;");
			stat.setInt(1, vid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean deleteEmployeeTraining(Integer vid)  " + e);
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
	public static BussingContractorSystemEmployeeTrainingBean getEmployeeTrainingByTrainingId(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemEmployeeTrainingBean ebean = new BussingContractorSystemEmployeeTrainingBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_emp_training_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemEmployeeTrainingBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemEmployeeTrainingBean> getEmployeeTrainingById(Integer cid):"
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
	public static BussingContractorSystemEmployeeTrainingBean updateEmployeeTraining(BussingContractorSystemEmployeeTrainingBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_emp_training(?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, vbean.getTrainingType());
			if(vbean.getTrainingDate() == null){
				stat.setTimestamp(2, null);
			}else{
				stat.setTimestamp(2, new Timestamp(vbean.getTrainingDate().getTime()));
			}
			if(vbean.getExpiryDate() == null){
				stat.setTimestamp(3, null);
			}else{
				stat.setTimestamp(3, new Timestamp(vbean.getExpiryDate().getTime()));
			}
			stat.setString(4, vbean.getNotes());
			stat.setInt(5, vbean.getFkEmployee());
			stat.setString(6, vbean.gettDocument());
			stat.setInt(7,vbean.getTrainingLength());
			stat.setString(8, vbean.getProvidedBy());
			stat.setString(9,vbean.getLocation());
			stat.setInt(10, vbean.getPk());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static BussingContractorSystemEmployeeTrainingBean updateEmployeeTraining(BussingContractorSystemEmployeeTrainingBean vbean): "
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
	public static BussingContractorSystemEmployeeTrainingBean createBussingContractorSystemEmployeeTrainingBean(ResultSet rs) {
		BussingContractorSystemEmployeeTrainingBean abean = null;
		try {
				abean = new BussingContractorSystemEmployeeTrainingBean();
				abean.setPk(rs.getInt("PK"));
				abean.setTrainingType(rs.getInt("TRAININGTYPE"));
				Timestamp ts= rs.getTimestamp("TRAININGDATE");
				if(ts != null){
					abean.setTrainingDate(new java.util.Date(rs.getTimestamp("TRAININGDATE").getTime()));
				}
				ts= rs.getTimestamp("EXPIRYDATE");
				if(ts != null){
					abean.setExpiryDate(new java.util.Date(rs.getTimestamp("EXPIRYDATE").getTime()));
				}
				abean.setNotes(rs.getString("NOTES"));
				abean.setFkEmployee(rs.getInt("FKEMPLOYEE"));
				abean.setIsDeleted(rs.getString("ISDELETED"));
				abean.settDocument(rs.getString("TDOCUMENT"));
				abean.setTrainingLength(rs.getInt("TRAININGLENGTH"));
				abean.setProvidedBy(rs.getString("PROVIDEDBY"));
				abean.setLocation(rs.getString("LOCATION"));
		}catch (SQLException e) {
				abean = null;
		}
		return abean;
	}		
}
