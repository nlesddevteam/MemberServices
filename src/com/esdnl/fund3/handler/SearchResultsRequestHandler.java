package com.esdnl.fund3.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.esdnl.fund3.dao.ProjectManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class SearchResultsRequestHandler extends RequestHandlerImpl {
	public SearchResultsRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
	    try {
	    		String projects[] = form.getArray("lstproject");
	    		if(projects != null){
	    			request.setAttribute("projects", ProjectManager.getProjectsByName(StringUtils.join(projects,",")));
	    		}else{
	    			String projectnumber=form.get("txtproject");
	    			String inactive=form.get("chkinactive");
	    			if(!projectnumber.isEmpty()){
	    				if(projectnumber.toUpperCase().equals("ALL")){
	    					request.setAttribute("projects", ProjectManager.getProjectsByNumberAll());
	    				}else{
	    					request.setAttribute("projects", ProjectManager.getProjectsByNumber(projectnumber));
	    				}
	    			}else{
	    				//now we check for the other search items Region,Category,Position,Fiscal Year
	    				String regions[] = form.getArray("lstregion");
	    				String categories[] = form.getArray("lstcategory");
	    				String positions[] = form.getArray("lstposition");
	    				String fiscals[] = form.getArray("lstfiscal");
	    				request.setAttribute("projects", ProjectManager.getProjectsByOther(regions,categories,positions,fiscals,inactive));
	    				
	    			}
	    		}
	    		
	    		
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "quick_search_results.jsp";
	    return path;
	}
}
