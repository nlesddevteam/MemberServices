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
import com.awsd.servlet.ControllerServlet;

public class SchoolClosureStatusWorker extends TimerTask {

	private Vector<SchoolSystem> systems = null;
	//For LIVE copy
	private String nlesd_rootbasepath = ControllerServlet.CONTEXT_BASE_PATH + "/../../nlesdweb/WebContent/";
	
	public SchoolClosureStatusWorker(Vector<SchoolSystem> systems) {

		super();

		synchronized (systems) {
			this.systems = systems;
		}

		System.err.println("<<<<<< SCHOOL STATUS REPORT TIMER STARTED >>>>>");
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
				System.err.println("<<<<<< SCHOOL STATUS REPORT RELOADED >>>>>");

				//this.writeCustomizeViewFileNLESDWeb();
				this.writeAllViewFileNLESDWeb();

				//this.CBC_writeAllViewFile();

				cur = Calendar.getInstance();
				if (((cur.get(Calendar.MONTH) > Calendar.JUNE) && (cur.get(Calendar.MONTH) < Calendar.SEPTEMBER))
						|| ((cur.get(Calendar.MONTH) == Calendar.JUNE) && (cur.get(Calendar.DATE) > 25))) {
					System.err.println("<<<<<< SCHOOL STATUS REPORT RELOADER STOPPED FOR SUMMER VACATION >>>>>");
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

			all_view_tmp = new File(nlesd_rootbasepath + "schools/generated/schoolstatus.tmp");

			if (!all_view_tmp.getParentFile().exists())
				all_view_tmp.getParentFile().mkdirs();

			if (all_view_tmp.exists()) {
				System.err.println("<<<<< SCHOOL STATUS REPORT TEMP FILE ALREADY EXIST >>>>>");

				if (all_view_tmp.delete()) {
					System.err.println("<<<<< SCHOOL STATUS REPORT TEMP FILE DELETED >>>>>");
				}
				else {
					System.err.println("<<<<< SCHOOL STATUS REPORT TEMP FILE COULD NOT BE DELETED >>>>>");
				}
			}

			writer = new PrintWriter(new FileWriter(all_view_tmp), true);

			School schoolST = null;		
					
						
			try {
				Iterator<School> iter = SchoolDB.getSchoolsOffices().iterator();
				
				
				writer.println("<script>");				
				writer.println("$('document').ready(function(){$('#schoolStatusTable').DataTable({ 'order': [[ 0, 'asc' ]],'lengthChange': false, responsive: true, 'paging':   false,'lengthMenu': [[10, 20, 50, 100, -1], [10, 20, 50, 100, 'All']],");
				writer.println("dom: 'Bfrtip', buttons: [	'copy','csv','excel',{extend: 'pdf',orientation: 'portrait', messageTop: 'SCHOOL STATUS REPORT',messageBottom: null,exportOptions: { columns: [ 0,1,2,3,4]}},");
				writer.println("{ extend: 'print', orientation: 'portrait', messageTop: 'SCHOOL STATUS REPORT',messageBottom: null, exportOptions: {columns: [ 0,1,2,3,4] } }] });});");
				writer.println("</script>");		
				
				
				writer.println("<table id='schoolStatusTable' class='table table-sm responsive table-striped table-bordered' style='font-size:11px;' width='100%'>");
				writer.println("<thead class='thead-dark'><tr style='text-transform:uppercase;font-weight:bold;color:White;'>"
						+ "<th>SCHOOL/BUILDING</th>"
						+ "<th>STATUS</th>"
						+ "<th>DESCRIPTION/NOTES</th>"
						+ "<th>FAMILY</th>"	
						+ "<th>REGION</th>"
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
					writer.println("<td><span style='font-size:12px;font-weight:bold;'>"+schoolST.getSchoolName() + "</span><br/>");					
					writer.println(((schoolST.getTownCity() !=null)?schoolST.getTownCity()+", NL":"N/A")+"</td>");
					
					
					switch (code) {
			        case 186:
			        	writer.println("<td class='busDelayed' style='vertical-align:middle;'>BUS DELAYED</td>");
			          break;
			        case 143:
			        case 144:
			        case 145:
			        case 146:
			        	writer.println("<td class='delayedOpening' style='vertical-align:middle;'>DELAYED OPENING</td>");
			          break;
			        case 183:
			        	writer.println("<td class='kinderstartSession' style='vertical-align:middle;'>KINDERSTART SESSION</td>");
			          break;
			        case 123:			        
			        case 184:
			        case 185:
			        	writer.println("<td class='officeClosed' style='vertical-align:middle;'>OFFICE CLOSED</td>");
			          break;
			        case 8:
			        	writer.println("<td class='otherStatus' style='vertical-align:middle;'>OTHER STATUS</td>");
			          break;
			        case 7:
			        case 10:
			        	writer.println("<td class='closedAllDay' style='vertical-align:middle;'>CLOSED ALL DAY</td>");
			          break;
			        case 82:
			        case 83:
			        case 84:
			        	writer.println("<td class='closedForPD' style='vertical-align:middle;'>CLOSED FOR PD</td>");
			          break;
			        case 102:
			        	writer.println("<td class='closedForHoliday' style='vertical-align:middle;'>CLOSED FOR HOLIDAY</td>");
			          break;
			        case 11:
			        	writer.println("<td class='closedForAfternoon' style='vertical-align:middle;'>CLOSED FOR AFTERNOON</td>");
			          break;
			        case 62:
			        case 4:
			        case 21:
			        	writer.println("<td class='closedForMorning' style='vertical-align:middle;'>CLOSED FOR MORNING</td>");
			          break;  
			        case 22:
			        	writer.println("<td class='summerBreak' style='vertical-align:middle;'>SUMMER BREAK</td>");
			          break;
			        case 163:
			        	writer.println("<td class='closingEarly' style='vertical-align:middle;'>CLOSING EARLY</td>");
			          break;  
			        case 9:
			        	writer.println("<td class='schoolOpen' style='vertical-align:middle;'>OPEN</td>");
			          break;
			        case 122:
			        	writer.println("<td class='schoolReopening' style='vertical-align:middle;'>SCHOOL REOPENING</td>");
			          break;  
			        default:
			        	writer.println("<td class='otherStatus' style='vertical-align:middle;'>OTHER</td>");
			          break;	 
			      }         
			         			      
			        writer.println("<td style='vertical-align:middle;'><span style='font-size:12px;'>"+schoolST.getSchoolClosureStatus().getClosureStatusDescription()+"</span>");  
			        writer.println(((schoolST.getSchoolClosureStatus().getSchoolClosureNote() !=null)?"<br/><div style='margin-top:5px;'><b>NOTE:</b> "+schoolST.getSchoolClosureStatus().getSchoolClosureNote()+"</div>":""));  
			        writer.println("</td>");     			        
			       
			        
			        if(schoolST.getSchoolFamily()!=null) {
			        	
			        	String schoolFam = schoolST.getSchoolFamily().getSchoolFamilyName();
			        				        	
			        	if(schoolFam.equalsIgnoreCase("FOS 01")) {
			        		writer.println("<td class='text-nowrap familyAlign family1' style='vertical-align:middle;'>FOS 01</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 02")) {
			        		writer.println("<td class='text-nowrap familyAlign family2' style='vertical-align:middle;'>FOS 02</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 03")) {
			        		writer.println("<td class='text-nowrap familyAlign family3' style='vertical-align:middle;'>FOS 03</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 04")) {
			        		writer.println("<td class='text-nowrap familyAlign family4' style='vertical-align:middle;'>FOS 04</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 05")) {
			        		writer.println("<td class='text-nowrap familyAlign family5' style='vertical-align:middle;'>FOS 05</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 06")) {
			        		writer.println("<td class='text-nowrap familyAlign family6' style='vertical-align:middle;'>FOS 06</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 07")) {
			        		writer.println("<td class='text-nowrap familyAlign family7' style='vertical-align:middle;'>FOS 07</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 08")) {
			        		writer.println("<td class='text-nowrap familyAlign family8' style='vertical-align:middle;'>FOS 08</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 09")) {
			        		writer.println("<td class='text-nowrap familyAlign family9' style='vertical-align:middle;'>FOS 09</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 10")) {
			        		writer.println("<td class='text-nowrap familyAlign family10' style='vertical-align:middle;'>FOS 10</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 11 (DSS)")) {
			        		writer.println("<td class='text-nowrap familyAlign family11' style='vertical-align:middle;'>FOS 11 (DSS)</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 12 (DSS)")) {
			        		writer.println("<td class='text-nowrap familyAlign family12' style='vertical-align:middle;'>FOS 12 (DSS)</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 13 (DSS)")) {
			        		writer.println("<td class='text-nowrap familyAlign family13' style='vertical-align:middle;'>FOS 13 (DSS)</td>");
			        	} else if (schoolFam.equalsIgnoreCase("FOS 14 (DSS)")) {
			        		writer.println("<td class='text-nowrap familyAlign family14' style='vertical-align:middle;'>FOS 14 (DSS)</td>");
			        	} else {
			        		writer.println("<td class='text-nowrap familyAlign familyDefault' style='vertical-align:middle;'>N/A</td>");
			        	}      	
			        	
			        }
			        else {			        	
						    writer.println("<td class='text-nowrap familyAlign familyDefault' style='vertical-align:middle;'>N/A</td>");
						  
			        }
			        
			        
			        if (schoolST.getZone() !=null) {			    	  			    	
			    	  
			    	  int schoolZone=schoolST.getZone().getZoneId();		        
				      switch (schoolZone) {
				      
				        case 1:
				        	writer.println("<td class='officeAvalon' style='text-align:center;vertical-align:middle;font-weight:bold;'>AVALON</td>");
				          break;
				        case 2:				        
				        	writer.println("<td class='officeCentral' style='text-align:center;vertical-align:middle;font-weight:bold;'>CENTRAL</td>");
				          break;
				        case 3:
				        	writer.println("<td class='officeWestern' style='text-align:center;vertical-align:middle;font-weight:bold;'>WESTERN</td>");
				          break;
				        case 4:
				        	writer.println("<td class='officeLabrador' style='text-align:center;vertical-align:middle;font-weight:bold;'>LABRADOR</td>");
				          break;
				        case 5:
				        	writer.println("<td class='officeProvincial' style='text-align:center;vertical-align:middle;font-weight:bold;'>PROVINCIAL</td>");
				          break;
				        default:
				        	writer.println("<td class='officeProvincial' style='text-align:center;vertical-align:middle;font-weight:bold;'>PROVINCIAL</td>");
				          break;	      	  
				      }			    	
			      } else {
			    	  writer.println("<td class='officeProvincial' style='text-align:center;vertical-align:middle;font-weight:bold;'>PROVINCIAL</td>");
			      }
			      
			       
			       
			        
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

			all_view_real = new File(nlesd_rootbasepath + "schools/generated/schoolstatus.html");

			if (all_view_real.exists()) {
				all_view_real.delete();
				System.err.println("<<<<<< EXISTING SCHOOL STATUS REPORT FILE DELETED >>>>>>");
			}
			all_view_tmp.renameTo(all_view_real);
			ss.clear();
			System.err.println("<<<<<< SCHOOL STATUS REPORT FILE REGENERATED >>>>>>");
			System.err.flush();
			System.gc();
		}
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.flush();
		}
	}


	
}