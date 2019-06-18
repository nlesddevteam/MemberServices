package com.esdnl.personnel.v2.database.sds;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.esdnl.dao.DAOUtils;

public class SyncManager {

	private static int QUERYTIMEOUT = 1800;

	private static Date lastUpdateStart = null;
	private static Date lastUpdateFinish = null;

	public static boolean canSync() {

		boolean check = false;

		if ((lastUpdateStart == null) || ((lastUpdateFinish != null) && lastUpdateFinish.after(lastUpdateStart)))
			check = true;
		else if (lastUpdateFinish == null) {
			Calendar start = Calendar.getInstance();
			start.clear();
			start.setTime(lastUpdateStart);
			start.add(Calendar.SECOND, QUERYTIMEOUT);

			//something happened and the process did not finish!
			if (start.before(Calendar.getInstance().getTime()))
				check = true;
		}
		return check;
	}

	synchronized public static void loadTables() {

		Connection con = null;
		CallableStatement stat = null;

		try {
			if (canSync()) {

				lastUpdateStart = Calendar.getInstance().getTime();

				con = DAOUtils.getConnection();
				con.setAutoCommit(true);

				stat = con.prepareCall("begin awsd_user.sds_hr.load_tables; end;");

				stat.setQueryTimeout(QUERYTIMEOUT);

				stat.execute();

				lastUpdateFinish = Calendar.getInstance().getTime();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
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
}
