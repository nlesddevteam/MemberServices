package com.esdnl.photocopier.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.photocopier.bean.PhotocopierException;
import com.esdnl.photocopier.bean.SupplierBean;

public class SupplierManager {

	public static SupplierBean[] getSupplierBeans() throws PhotocopierException {

		Vector v_opps = null;
		SupplierBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.photocopier_pkg.get_suppliers; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createSupplierBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("SupplierManager.getSupplierBeans(): " + e);
			throw new PhotocopierException("Can not extract SupplierBean from DB.", e);
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

		return (SupplierBean[]) v_opps.toArray(new SupplierBean[0]);
	}

	public static SupplierBean getSupplierBean(int company_id) throws PhotocopierException {

		SupplierBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.photocopier_pkg.get_supplier(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, company_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createSupplierBean(rs);
			else
				eBean = null;
		}
		catch (SQLException e) {
			System.err.println("SupplierManager.getSupplierBeans(): " + e);
			throw new PhotocopierException("Can not extract SupplierBean from DB.", e);
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

		return eBean;
	}

	public static SupplierBean createSupplierBean(ResultSet rs) {

		SupplierBean aBean = null;
		try {
			aBean = new SupplierBean();
			aBean.setId(rs.getInt("COMPANY_ID"));
			aBean.setName(rs.getString("COMPANY_NAME"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}