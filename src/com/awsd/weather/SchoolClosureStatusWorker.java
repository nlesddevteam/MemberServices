package com.awsd.weather;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Vector;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import java.util.Arrays;
import java.util.List;

import com.awsd.servlet.ControllerServlet;

public class SchoolClosureStatusWorker extends TimerTask {

	private Vector<SchoolSystem> systems = null;
//For LIVE copy
	private String nlesd_rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../../nlesdweb/WebContent/";
	
	//For local copy
	//private String nlesd_rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../../wtpwebapps/NLESDWEB/";
	

	public SchoolClosureStatusWorker(Vector<SchoolSystem> systems) {

		super();

		synchronized (systems) {
			this.systems = systems;
		}

		System.err.println("<<<<<< SCHOOLSYSTEM TIMER STARTED >>>>>");
	}

	public void setSchoolSystems(Vector<SchoolSystem> systems) {

		synchronized (this.systems) {
			this.systems = systems;
		}
	}

	public void run() {

		Calendar cur = null;

		try {
			synchronized (this.systems) {
				systems.clear();
				systems.addAll(SchoolSystemDB.getSchoolClosureStatuses());
				System.err.println("<<<<<< SCHOOLSYSTEMS RELOADED >>>>>");

				//this.writeCustomizeViewFileNLESDWeb();
				this.writeAllViewFileNLESDWeb();

				this.CBC_writeAllViewFile();

				cur = Calendar.getInstance();
				if (((cur.get(Calendar.MONTH) > Calendar.JUNE) && (cur.get(Calendar.MONTH) < Calendar.SEPTEMBER))
						|| ((cur.get(Calendar.MONTH) == Calendar.JUNE) && (cur.get(Calendar.DATE) > 25))) {
					System.err.println("<<<<<< SCHOOLSYSTEMS RELOADER STOPPED FOR SUMMER VACATION >>>>>");
					SchoolSystems.stopTimer();
				}
			}
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}

	public void writeAllViewFileNLESDWeb() {

		File all_view_tmp = null, all_view_real = null;
		PrintWriter writer = null;
		Vector<SchoolSystem> ss = null;
		ClosureStatus stat = null;

		try {
			synchronized (this.systems) {
				ss = new Vector<SchoolSystem>(systems);
			}

			all_view_tmp = new File(nlesd_rootbasepath + "statuscentral/schoolstatus.tmp");

			if (!all_view_tmp.getParentFile().exists())
				all_view_tmp.getParentFile().mkdirs();

			if (all_view_tmp.exists()) {
				System.err.println("<<<<< TEMP FILE ALREADY EXIST >>>>>");

				if (all_view_tmp.delete()) {
					System.err.println("<<<<< TEMP FILE DELETED >>>>>");
				}
				else {
					System.err.println("<<<<< TEMP FILE COULD NOT BE DELETED >>>>>");
				}
			}

			writer = new PrintWriter(new FileWriter(all_view_tmp), true);

			
			School schoolST = null;		
			
		
						
			try {
				Iterator<School> iter = SchoolDB.getSchoolsOffices().iterator();
				
				writer.println("<table id='schoolStatusTable' class='table table-condensed table-striped table-bordered' style='font-size:11px;' width='100%'>");
				writer.println("<thead><tr  style='text-transform:uppercase;font-weight:bold;'>"
						+ "<th width='20%'>SCHOOL/BUILDING</th>"
						+ "<th width='10%'>STATUS</th>"
						+ "<th width='40%'>DESCRIPTION/NOTE</th>"						
						+ "<th width='10%'>REGION</th>"
						+ "<th width='20%'>LOCATION</th>"
						+ "</tr></thead>");
				writer.println("<tbody>");
				
						
				while (iter.hasNext()) {
					
					schoolST = (School) iter.next();							
					int sid = schoolST.getSchoolID();		
				 int code=schoolST.getSchoolClosureStatus().getClosureStatusID();  	
			        
//ignore these school ids
				if (	 sid==281				//Avalon East
						|  sid==282		//Avalon West
						|| sid==273		//Former Avalon East School (Closed)
						|| sid==274		//Former Avalon West School (Closed)
						|| sid==275		//Former Burin School (Closed) 
						|| sid==276		//Former Vista School
						|| sid==475		//Former NCSD District/Regional Office
						|| sid==476		//Former ESDNL District/Reginal Office
						|| sid==477		//Former LSB Disitrict/Regional Office
						|| sid==478		//Former WSDNL District/Regional Office
						|| sid==410		//Long Island Academy
						|| sid==441 	//Heritage Academy
						|| sid==217		//Booth Memorial High School
						|| sid==424		//H.L. Strong Academy
						|| sid==216		//Bishops College					
						|| sid==286		//SEB Cabin
						|| sid==309		//Rowan Treatment Center
						|| sid==269		//TEST SCHOOL
						|| sid==284		//Vista Region
						|| sid==832		//Western Baie Verte BusDep
						|| sid==838		//Western Corner Brook Bus Depot
						|| sid==735		//Z test School
						|| sid==378		//Our Lady of Mercy Elementary			
						|| sid==285		//Burin Bus Depot
						|| sid==283		//Burin Region
						|| code==9
						) { 
					//Do nothing yet
				 				
				}else {	
					
									
					writer.println("<tr id="+schoolST.getSchoolID()+">");			
					writer.println("<td>"+schoolST.getSchoolName() + " " + schoolST.getSchoolID() +"</td>");					
			    		        
			      switch (code) {
			        case 186:
			        	writer.println("<td style='text-align:center;background-color:#fffc00;font-size:11px;color:black;font-weight:bold;'>BUS DELAYED</td>");
			          break;
			        case 143:
			        case 144:
			        case 145:
			        case 146:
			        	writer.println("<td style='text-align:center;background-color:#fffc00;font-size:11px;color:black;font-weight:bold;'>DELAYED OPENING</td>");
			          break;
			        case 183:
			        	writer.println("<td style='text-align:center;background-color:#bf00bd;font-size:11px;color:white;font-weight:bold;'>KINDERSTART SESSION</td>");
			          break;
			        case 123:			        
			        case 184:
			        case 185:
			        	writer.println("<td style='text-align:center;background-color:#ff0000;font-size:11px;color:white;font-weight:bold;'>OFFICE CLOSED</td>");
			          break;
			        case 8:
			        	writer.println("<td style='text-align:center;background-color:#ff8200;font-size:11px;color:white;font-weight:bold;'>OTHER STATUS</td>");
			          break;
			        case 7:
			        case 10:
			        	writer.println("<td style='text-align:center;background-color:#ff0000;font-size:11px;color:white;font-weight:bold;'>CLOSED ALL DAY</td>");
			          break;
			        case 82:
			        case 83:
			        case 84:
			        	writer.println("<td style='text-align:center;background-color:#ff0000;font-size:11px;color:white;font-weight:bold;'>CLOSED FOR PD</td>");
			          break;
			        case 102:
			        	writer.println("<td style='text-align:center;background-color:#0003ff;font-size:11px;color:white;font-weight:bold;'>CLOSED FOR HOLIDAY</td>");
			          break;
			        case 11:
			        	writer.println("<td style='text-align:center;background-color:#bf0000;font-size:11px;color:white;font-weight:bold;'>CLOSED FOR AFTERNOON</td>");
			          break;
			        case 62:
			        case 4:
			        case 21:
			        	writer.println("<td style='text-align:center;background-color:#bf0000;font-size:11px;color:white;font-weight:bold;'>CLOSED FOR MORNING</td>");
			          break;  
			        case 22:
			        	writer.println("<td style='text-align:center;background-color:#008001;font-size:11px;color:white;font-weight:bold;'>SUMMER BREAK</td>");
			          break;
			        case 163:
			        	writer.println("<td style='text-align:center;background-color:#fffc00;font-size:11px;color:red;font-weight:bold;'>CLOSING EARLY</td>");
			          break;  
			        case 9:
			        	writer.println("<td style='text-align:center;background-color:#008000;font-size:11px;color:white;font-weight:bold;'>OPEN</td>");
			          break;
			        case 122:
			        	writer.println("<td style='text-align:center;background-color:#1c91ec;font-size:11px;color:white;font-weight:bold;'>SCHOOL REOPENING</td>");
			          break;  
			        default:
			        	writer.println("<td style='text-align:center;background-color:#ff8200;font-size:11px;color:white;font-weight:bold;'>OTHER</td>");
			          break;	    
			      }   
			        writer.println("<td>"+schoolST.getSchoolClosureStatus().getClosureStatusDescription() +"</td>");  
			   
			        if (schoolST.getZone() !=null) {			    	  			    	
			    	  
			    	  int schoolZone=schoolST.getZone().getZoneId();		        
				      switch (schoolZone) {
				      
				        case 1:
				        	writer.println("<td class='region1solid' style='text-align:center;color:white;'>AVALON</td>");
				          break;
				        case 2:				        
				        	writer.println("<td class='region2solid' style='text-align:center;color:white;'>CENTRAL</td>");
				          break;
				        case 3:
				        	writer.println("<td class='region3solid' style='text-align:center;color:white;'>WESTERN</td>");
				          break;
				        case 4:
				        	writer.println("<td class='region4solid' style='text-align:center;color:white;'>LABRADOR</td>");
				          break;
				        case 5:
				        	writer.println("<td class='region5solid' style='text-align:center;color:white;'>PROVINCIAL</td>");
				          break;
				        default:
				        	writer.println("<td class='region5solid' style='text-align:center;color:white;'>PROVINCIAL</td>");
				          break;	      	  
				      }			    	
			      } else {
			    	  writer.println("<td class='region5solid' style='text-align:center;color:white;'>PROVINCIAL</td>");
			      }
			      
				     				      	
				     writer.println("<td>"+((schoolST.getTownCity() !=null)?schoolST.getTownCity()+", NL":"N/A")+"</td>");         
			      		
					
			        writer.println("</tr>");			        
					
				} 			
				}
				
				writer.println("</tbody>");
				writer.println(" </table>");
				
			}
			catch (SchoolException e) {
				e.printStackTrace(System.err);
			}
	
			writer.flush();
			writer.close();

			all_view_real = new File(nlesd_rootbasepath + "statuscentral/schoolstatus.html");

			if (all_view_real.exists()) {
				all_view_real.delete();
				System.err.println("<<<<<< EXISTING ALL VIEW FILE DELETED >>>>>>");
			}
			all_view_tmp.renameTo(all_view_real);
			ss.clear();
			System.err.println("<<<<<< ALL VIEW FILE REGENERATED >>>>>>");
			System.err.flush();
			System.gc();
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}


	public void CBC_writeAllViewFile() {

		File all_view_tmp = null, all_view_real = null;
		PrintWriter writer = null;
		Vector<SchoolSystem> ss = null;
		Iterator<SchoolSystem> sys_iter = null;
		Iterator<School> sch_iter = null;
		SchoolSystem sys = null;
		School school = null;
		ClosureStatus stat = null;
		boolean isOffice = false;

		try {
			synchronized (this.systems) {
				ss = new Vector<SchoolSystem>(systems);
			}

			all_view_tmp = new File(nlesd_rootbasepath + "statuscentral/cbc_status_central_all_view.tmp");

			if (!all_view_tmp.getParentFile().exists())
				all_view_tmp.getParentFile().mkdirs();

			if (all_view_tmp.exists()) {
				System.err.println("<<<<< TEMP FILE ALREADY EXIST >>>>>");

				if (all_view_tmp.delete()) {
					System.err.println("<<<<< TEMP FILE DELETED >>>>>");
				}
				else {
					System.err.println("<<<<< TEMP FILE COULD NOT BE DELETED >>>>>");
				}
			}

			writer = new PrintWriter(new FileWriter(all_view_tmp), true);

			sys_iter = ss.iterator();
			writer.println("<html>");
			writer.println("<head>");
			writer.println("<title>Newfoundland &amp; Labrador English School District - School Status Central</title>");
			writer.println("<link rel='stylesheet' href='//www.cbc.ca/nl/stormcentre/css/esdnl.css'>");
			writer.println(this.getGoogleAnalytics());
			writer.println("</head>");
			writer.println("<body>");
			writer.println(
					"<table width='100%' cellpadding='0' cellspacing='0' border='0' style='border:1px solid #C1CDD8;'>");
			writer.println("<tr id='bodyContainer'>");
			writer.println("<td width='100%' align='left' valign='top'>");
			writer.println("<table width='100%' cellpadding='0' cellspacing='0' border='0'>");
			writer.println("<tr>");
			writer.println("<td width='100%' align='left' valign='top'>");
			writer.println("<table width='100%' cellpadding='0' cellspacing='0' border='0'>");
			writer.println("<tr>");
			writer.println("<td width='100%' align='left' valign='top'>");
			writer.println("<table width='100%' cellpadding='0' cellspacing='0' border='0'>");
			while (sys_iter.hasNext()) {
				sys = (SchoolSystem) sys_iter.next();

				writer.println("<tr height='25' style='background-color: #DBEAF5;'>");
				writer.println("<td colspan='2' align='left' valign='middle'>");
				writer.println("&nbsp;<span class='weatherCentralSchoolSystem'>" + sys.getSchoolSystemName() + "</span>");
				writer.println("</td>");
				writer.println("</tr>");
				writer.println("<tr>");
				writer.println(
						"<td width='100%' height='1' align='left' valign='middle' colspan='4' style='background-color: #C1CDD8;'>");
				writer.println("<img src='http://www.nlesd.ca/images/spacer.gif' width='1' height='1'><BR>");
				writer.println("</td>");
				writer.println("</tr>");

				sch_iter = null;
				sch_iter = sys.getSchoolSystemSchools().iterator();
				while ((sch_iter != null) && sch_iter.hasNext()) {
					school = (School) sch_iter.next();
					stat = school.getSchoolClosureStatus();

					isOffice = (school.getSchoolName().endsWith("Office") || (school.getSchoolID() == 220));

					writer.println("<tr>");
					writer.println("<td width='50%' align='left' valign='top'>");
					writer.println("&nbsp;<span class='normalGrey10pxText'>" + school.getSchoolName() + "</span>");
					writer.println("</td>");
					writer.println("<td width='*' align='left' valign='top'>");

					writer.print("<span class='" + cssClass(stat.getClosureStatusID()) + "'>");

					writer.print(!isOffice ? stat.getClosureStatusDescription()
							: stat.getClosureStatusDescription().replaceAll("School", "Office"));
					writer.println("</span>");
					if (stat.getSchoolClosureNote() != null) {
						writer.print("<BR><span style='font-size:11px;color:#333333;'><b>Note</b>:&nbsp;"
								+ stat.getSchoolClosureNote() + "</span>");
					}
					writer.println("</td>");
					writer.println("</tr>");
				}
			}

			writer.println("</table>");
			writer.println("</td>");
			writer.println("</tr>");
			writer.println("</table>");
			writer.println("</td>");
			writer.println("</tr>");
			writer.println("</table>");
			writer.println("</td>");
			writer.println("</tr>");
			writer.println("</table>");
			writer.println("</body>");
			writer.println("</html>");
			writer.flush();
			writer.close();

			all_view_real = new File(nlesd_rootbasepath + "statuscentral/cbc_status_central_all_view.html");

			if (all_view_real.exists()) {
				all_view_real.delete();
				System.err.println("<<<<<< EXISTING CBC VIEW FILE DELETED >>>>>>");
			}
			all_view_tmp.renameTo(all_view_real);
			ss.clear();
			System.err.println("<<<<<< CBC VIEW FILE REGENERATED >>>>>>");
			System.err.flush();
			System.gc();
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}

	public static String cssClass(int id) {

		String css;

		switch (id) {
		case 4:
		case 7:
		case 10:
		case 11:
		case 21:
		case 22:
		case 62:
		case 82:
		case 83:
		case 84:
		case 102:
		case 123:
			css = "weatherCentralStatusClosed";
			break;
		case 6:
		case 5:
		case 42:
		case 63:
			css = "weatherCentralStatusDelayed";
			break;
		case 9:
			css = "weatherCentralStatusOpen";
			break;
		default:
			css = "weatherCentralStatusOpen";
		}

		return css;
	}

	private String encodeHTML(String raw) {

		return raw.replaceAll(new String(new char[] {
				(char) 39
		}), "&#39;") // single quote
				.replaceAll(new String(new char[] {
						(char) 44
				}), "&#44;") // comma
				.replaceAll(new String(new char[] {
						(char) 10
				}), "") // line feed
				.replaceAll(new String(new char[] {
						(char) 13
				}), ""); // cartiage return;
	}

	private String getGoogleAnalytics() {

		StringBuffer buf = new StringBuffer();

		buf.append("<script type='text/javascript'>");
		buf.append("var _gaq = _gaq || [];");
		buf.append("_gaq.push(['_setAccount', 'UA-29467925-1']);");
		buf.append("_gaq.push(['_setDomainName', 'esdnl.ca']);");
		buf.append("_gaq.push(['_trackPageview']);");

		buf.append("(function() {");
		buf.append(" var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;");
		buf.append(
				"ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';");
		buf.append("var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);");
		buf.append("})();");
		buf.append("</script>");

		return buf.toString();
	}
}