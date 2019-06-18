package com.esdnl.payadvice.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessErrorManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ExportPayProcessErrorsCSVRequestHandler  extends RequestHandlerImpl {
	public ExportPayProcessErrorsCSVRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		Integer paygroupid = form.getInt("payid");
		try {
				String data=NLESDPayAdvicePayrollProcessErrorManager.getNLESDPayAdvicePayrollProcessErrorsCSV(paygroupid);
				reponse.setContentType("application/csv");
				reponse.setHeader("content-disposition","filename=processerrors.csv");
				PrintWriter out = reponse.getWriter();
				out.print(data);
				out.flush();
				out.close();
	
				path = null;
		}
		catch (Exception e) {
				e.printStackTrace(System.err);
				path = null;
		}

		return null;
	}
}