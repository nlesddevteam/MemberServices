package com.esdnl.payadvice.dao;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.time.DateUtils;

import com.cete.dynamicpdf.Document;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.PageDimensions;
import com.cete.dynamicpdf.PageOrientation;
import com.cete.dynamicpdf.PageSize;
import com.cete.dynamicpdf.Template;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.WebColor;
import com.cete.dynamicpdf.cryptography.RC4128Security;
import com.cete.dynamicpdf.pageelements.Label;
import com.cete.dynamicpdf.pageelements.Line;
import com.cete.dynamicpdf.pageelements.Rectangle;
import com.esdnl.payadvice.bean.NLESDPayAdviceEmployeeInfoBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayGroupBean;
import com.esdnl.payadvice.bean.NLESDPayAdviceSubWorkHistoryBean;
public class NLESDPayAdviceCreateManualHistoryPageManager {
    private Template template = new Template();
    // Page Dimensions of pages
    private static PageDimensions pageDimensions = new PageDimensions(PageSize
                          .LETTER, PageOrientation.PORTRAIT, 54.0f);
    // Current page that elements are being added to
    private static Page currentPage;
    // Top Y coordinate for the body of the report
    private static float bodyTop = 38;
    // Bottom Y coordinate for the body of the report
    private static float bodyBottom = pageDimensions.getBody().getBottom()
                                - pageDimensions.getBody().getTop();
    // Current Y coordinate where elements are being added
    private static float currentY;
    // Used to control the alternating background
    private static boolean alternateBG;
	public String createWorkHistory(String empnumber,Integer paygroupid,String foldername)
	{
		// Create a document and set it's properties
        Document document = new Document();
        document.setCreator("viewworkhistory");
        document.setAuthor("nlesd");
        document.setTitle("workhistory");
    	// Adds elements to the header template
    	setTemplate(empnumber,paygroupid);
    	document.setTemplate(template);
        // Builds the report
    	boolean test = buildDocument(document,paygroupid,empnumber);
    	if(test){
	    		// Outputs the Invoices to the current web page
	        	// Create RC4 128 bit encryption security object that prevents text copying.
	    		RC4128Security security = new RC4128Security(empnumber); 
	    		security.setAllowCopy(false);
	             // Add the security object to the document
	    		document.setSecurity(security);
	            // Outputs the W-9 to the file
	    		String filename = foldername + "/history/HIS" + empnumber + ".pdf";
	        	document.draw(filename);
	        	return filename;
        }else{
    		return "";
    	}
	}
	public String createWorkHistoryPage(String empnumber,Integer paygroupid,String foldername,Document doc)
	{
		// Create a document and set it's properties
		Page p = new Page();
		setTemplate(empnumber,paygroupid);
		doc.getSections().begin(template);
		p.setApplyDocumentTemplate(false);
		p.setApplySectionTemplate(true);
		
    	boolean test = buildDocument(doc,paygroupid,empnumber);
    	if(test){
    		String filename = foldername + "/history/HIS" + empnumber + ".pdf";
        	return filename;
        }else{
    		return "";
    	}
	}
    private void setTemplate(String empnumber,Integer paygroupid) {
        // Adds elements to the header template
    	try {
				NLESDPayAdviceEmployeeInfoBean eb = NLESDPayAdviceEmployeeInfoManager.getNLESDPayAdviceEmployeeInfoBean(paygroupid, empnumber);
				NLESDPayAdvicePayGroupBean pg = NLESDPayAdvicePayGroupManager.getNLESDPayAdvicePayGroupBean(paygroupid);
				template.getElements().add(new Label(eb.getEmpName(), 0, 0, 504, 12, 
	                    Font.getHelveticaBold(), 15));
		    	template.getElements().add(new Label("Work History", 0, 0, 
	                    504, 12, Font.getHelveticaBold(), 15, 
	                    TextAlign.CENTER));
		    	//adjust the dates to correct weeks
		    	Date startDate= DateUtils.addDays(new SimpleDateFormat("MM/dd/yyyy").parse(pg.getPayBgDt()),-14);
		    	Date endDate= DateUtils.addDays(new SimpleDateFormat("MM/dd/yyyy").parse(pg.getPayEndDt()),-14);
		    	Format formatter = new SimpleDateFormat("yyyy-MM-dd");template.getElements().add(new Label("(" + formatter.format(startDate) + " - " + formatter.format(endDate) +")", 0, 0, 504, 12, Font.getHelveticaBold(), 15,TextAlign.RIGHT));
    			template.getElements().add(new Line(0, 23, 504, 23));
		    	template.getElements().add(new Line(0, bodyBottom, 504, bodyBottom));

		} catch (NLESDPayAdviceException e) {
				e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    }
        
    private boolean buildDocument(Document document, Integer id,String emp) {
    	// Builds the PDF document with data from the ResultSet
    	boolean isrecords=false;
    	try {
    		TreeMap<Integer,NLESDPayAdviceSubWorkHistoryBean> tmap = NLESDPayAdviceSubWorkHistoryManager.getNLESDPayAdviceWorkHistory(id, emp);
        	if(tmap.isEmpty()){
        		return false;
        	}else
        	{
        		addNewPage(document);
        		for(Map.Entry<Integer,NLESDPayAdviceSubWorkHistoryBean> entry : tmap.entrySet()) {
        			NLESDPayAdviceSubWorkHistoryBean ebean = (NLESDPayAdviceSubWorkHistoryBean)entry.getValue();
        		  		addRecord(ebean,document);
        		}
        		isrecords=true;
        	}
        } catch (Exception ex) {
            	System.err.println("cannot access the TreeMap :"+ex.getMessage());
        }
        return isrecords;
    }
    
    private void addNewPage(Document document) {
        	// Adds a new page to the document
        	currentPage = new Page(pageDimensions);
        	currentY = bodyTop;
        	alternateBG = false;
        	document.getPages().add(currentPage);
    }
    private void addRecord(NLESDPayAdviceSubWorkHistoryBean th,Document document) {
        	// Adds a new page to the document if needed
    	if (currentY > bodyBottom) {
            addNewPage(document);
        }
    	try {
            // Adds alternating background to document if needed
            if(!alternateBG){
            	currentPage.getElements().add(new Rectangle(0, currentY+3, 504, 18, 
                                    new WebColor("E0E0FF"), new WebColor("E0E0FF")));
            }
            // Adds Labels to the document with data from current record
            currentPage.getElements().add(new Label("DATE: ", 2, 
                                  currentY + 3, 40, 11, Font.getTimesBold(),
                                  11));
            currentPage.getElements().add(new Label(th.getDur(), 43, 
                                  currentY + 3, 70, 11, Font.getTimesRoman(),
                                  11));
            currentPage.getElements().add(new Label("HRS: ", 190, 
                                  currentY + 3, 40, 11, Font.getTimesBold(),
                                  11));
            currentPage.getElements().add(new Label(Double.toString(th.getTlQuantity()), 231, 
                                  currentY + 3, 40, 11, Font.getTimesRoman(),
                                  11));
            currentPage.getElements().add(new Label("RATE: ", 281, 
                                  currentY + 3, 40, 11, Font.getTimesBold(),
                                  11));
            currentPage.getElements().add(new Label(Double.toString(th.getCompRateUsed()), 321, 
                                  currentY + 3, 40, 11, Font.getTimesRoman(),
                                  11));            
            currentPage.getElements().add(new Label("Total: ", 427, 
                                  currentY + 3, 40, 11, Font.getTimesBold(),
                                  11));
            currentPage.getElements().add(new Label(Double.toString(th.getOtherEarnings()), 465, 
                                  currentY + 3, 40, 11, Font.getTimesRoman(),
                                  11));
            currentY += 18;
            
            if(!alternateBG){
            currentPage.getElements().add(new Rectangle(0, currentY+3, 504, 18, 
                                    new WebColor("E0E0FF"), new WebColor("E0E0FF")));
            }
    	    currentPage.getElements().add(new Label("SCHOOL: ", 2, 
                                  currentY + 3, 60, 11, Font.getTimesBold(),
                                  11));
            currentPage.getElements().add(new Label(th.getDescr(), 62, 
                                  currentY + 3, 218, 11, Font.getTimesRoman(),
                                  11));        
    	    currentPage.getElements().add(new Label("EMPLOYEE: ", 281, 
                                  currentY + 3, 80, 11, Font.getTimesBold(),
                                  11));
            currentPage.getElements().add(new Label(th.getName1(), 360, 
                                  currentY + 3, 140, 11, Font.getTimesRoman(),
                                  11)); 
    	} catch (Exception ex) {
    			System.err.println("cannot retrieve data from ResultSet :"
                             +ex.getMessage());
        }
        // Toggles alternating background
        alternateBG = !alternateBG;
        // Increments the current Y position on the page
        currentY += 18;
    }
}
