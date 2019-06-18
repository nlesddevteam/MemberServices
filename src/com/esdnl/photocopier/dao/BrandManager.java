package com.esdnl.photocopier.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.photocopier.bean.BrandBean;
import com.esdnl.photocopier.bean.PhotocopierException;

public class BrandManager {

	public static BrandBean[] getBrandBeans() throws PhotocopierException {

		Vector v_opps = null;
		BrandBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.photocopier_pkg.get_brands; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createBrandBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("BrandManager.getBrandBeans(): " + e);
			throw new PhotocopierException("Can not extract BrandBean from DB.", e);
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

		return (BrandBean[]) v_opps.toArray(new BrandBean[0]);
	}

	public static BrandBean getBrandBean(int brand_id) throws PhotocopierException {

		BrandBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.photocopier_pkg.get_brand(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, brand_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createBrandBean(rs);
			else
				eBean = null;
		}
		catch (SQLException e) {
			System.err.println("BrandManager.getBrandBeans(): " + e);
			throw new PhotocopierException("Can not extract BrandBean from DB.", e);
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

	public static BrandBean createBrandBean(ResultSet rs) {

		BrandBean aBean = null;
		try {
			aBean = new BrandBean();
			aBean.setId(rs.getInt("BRAND_ID"));
			aBean.setName(rs.getString("BRAND_NAME"));
		}
		catch (SQLException e) {
			aBean = null;
		}
		return aBean;
	}
}