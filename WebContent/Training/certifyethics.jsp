<%@ page language="java"
        session="true"
         import="com.awsd.security.*, 
                 java.util.*"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<esd:SecurityCheck />
<%
  User usr = null;
  usr = (User) session.getAttribute("usr");  
  if(usr == null)
  {%>
  	 <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="This is a Secure Resource!<br>Please Login."/>
    </jsp:forward>
  <%} %> 


<html>
  <head>
    <title>Member Services - Certify Ethics</title>    

   <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js" integrity="sha512-BNaRQnYJYiPSqHHDb58B0yaPfCu+Wgds8Gp/gU33kqBtgNS4tSPHuGibyoeqMV/TJlSKda6FXzoEyYGjTe+vXA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

   <c:set var="now" value="<%=new java.util.Date()%>" /> 
  <fmt:formatDate value="${now}" pattern="EEEE, MMMM d, y" var="certifyDate" />	
      
      <style>
      .btn {margin:2px;}      
      </style>
      
  </head>
  <body>
  


<div class="container-fluid no-print">		
				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="font-size:12px;">   
            
   Congratulations on completing your Code of Ethics and Conduct Training. <br/><br/>
   
   Please download this certificate using the link below and upload it to your MyHR profile under the documents section. You can also Print a copy of the certificate.<br/><br/>
     
   The PDF file will download to your web browsers set 'Downloads' folder on your device and saved as <b>COEC-Certificate.pdf</b>.<br/><br/>
   You will need to know where this file is saved in order to select it for upload to your MyHR profile. DO NOT LOSE this file as you will need to redo the training module to generate a new one. You should also Print a copy.
   Take a screenshot if you experience issues and save it. 
        <br/><br/>
   <div class="alert alert-danger" style="text-align:center;"><b>**** IMPORTANT NOTICE ****</b><br/>
   Some current <b>iOS devices (Apple)</b> will not allow generation of a PDF or saving the file.
    If the DOWNLOAD CERTIFICATE PDF link below fails, you may take a screenshot of this certificate on this page, and, using MS Word or Google Docs, insert the screenshot image and save as a PDF file. 
   Some Apple devices will open the PDF in a new window. If this happens, please SAVE the PDF file, or print the page as PDF, or take a screenshot.
    </div>
  
   
   </div>
  				</div>
 </div>   

<div align="center" class="no-print" style="padding-top:5px;padding-bottom:10px;"><a href="#" onClick="printToPDF();" class="btn btn-sm btn-primary">1. DOWNLOAD CERTIFICATE PDF</a> <a href='#' class="btn btn-sm btn-primary" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print();">2. PRINT CERTIFICATE</a></div>
  
<div id="printJob">
<div id="printable" style="max-width:900px;min-width:900px;text-align:center;margin: 0 auto;border:4px groove silver;padding:20px;background-color:#fffcf7;">


<img src="includes/img/nlesd-colorlogo.png" style="width:100%;max-width:400px;">

<br/><br/>
  <div class="curved-text" style="font-size:36px;font-style:italic;color:Silver;">Employee Training Declaration</div>  

<br/>
This is to certify that<br/><br/>

<span style="font-size:24px;font-weight:bold;"><%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></span><br/><br/>

has completed<br/><br/>
 
<span style="font-size:24px;font-weight:bold;">Code of Ethics and Conduct Training</span><br/><br/>
 
for the Newfoundland and Labrador English School District on<br/><br/>

<b>${certifyDate}</b>.<br/><br/>

<span style="font-size:11px;">The Code includes, but is not limited to: conflicts of interest, whistleblowing, the acceptance of gifts/benefits/travel,
 accountability, maintaining a respectful, harassment free and safe work environment, the acceptable use of technology and social media,
 the protection of District assets, and the accurate reflection of information in transactions, documentation, records and correspondence. <br/><br/>

I agree to abide by the Code of Ethics and Conduct and the Conflict of Interest Policy as a condition of my employment.<br/><br/>

I acknowledge and accept that any violation of the Code can lead to disciplinary action up to and including dismissal without compensation. 

  </span>
 <br/><br/>
 <span style="font-size:9px;color:Silver;">This certificate automatically generated via NLESD Training Modules App &copy;2022 Newfoundland and Labrador English School District.<br/>
 <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%> agrees to the terms of this certificate and agrees to add to their currently active MyHR profile(s).
 </span>
 
</div>
   </div>
<div class="no-print">
<hr>

<div class="alert alert-info" style="text-align:center;font-size:11px;">For questions relating to the Code of Ethics and Conduct, please contact Susan Tobin at 709.757.4652 (<a href="mailto:geofftaylor@nlesd.ca?subject=COC Certification">susantobin1@nlesd.ca</a>).
  <br/>If you require technical support, please email Geoff Taylor (<a href="mailto:geofftaylor@nlesd.ca?subject=COC Certification">geofftaylor@nlesd.ca</a>). </div>
<hr>
  
<div align="center"  style="padding-top:5px;padding-bottom:10px;"><a href="#" onClick="printToPDF();" class="btn btn-sm btn-primary">1. DOWNLOAD CERTIFICATE PDF</a> 
<a href='#' class="btn btn-sm btn-primary" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print();">2. PRINT CERTIFICATE</a>  
<a href="index.jsp" class="btn btn-danger btn-sm">Back to Training Modules</a></div>

   </div>
  <script>
function printToPDF() {
	  console.log('converting doc...');
	  var printableArea = document.getElementById('printable');
	  printableArea.style.fontFeatureSettings = '"liga" 0';
	  html2canvas(printableArea, {
	    useCORS: true,
	    
	    onrendered: function(canvas) {

	     var pdf = new jsPDF('p', 'pt', 'letter');	    
	   
	      var pageHeight = 850;
	      var pageWidth = 920; 
	      for (var i = 0; i <= printableArea.clientHeight / pageHeight; i++) {
	        var srcImg = canvas;
	        var sX = 0;
	        var sY = pageHeight * i; // start 1 pageHeight down for every new page
	        var sWidth = pageWidth;
	        var sHeight = pageHeight;
	        var dX = 0;
	        var dY = 0;
	        var dWidth = pageWidth;
	        var dHeight = pageHeight;

	        window.onePageCanvas = document.createElement("canvas");
	        onePageCanvas.setAttribute('width', pageWidth);
	        onePageCanvas.setAttribute('height', pageHeight);
	        var ctx = onePageCanvas.getContext('2d');
	        ctx.drawImage(srcImg, sX, sY, sWidth, sHeight, dX, dY, dWidth, dHeight);

	        var canvasDataURL = onePageCanvas.toDataURL("image/png", 1.0);
	        var width = onePageCanvas.width;
	        var height = onePageCanvas.clientHeight;

	        if (i > 0) // if we're on anything other than the first page, add another page
	          pdf.addPage(612, 791); // 8.5" x 11" in pts (inches*72)

	        pdf.setPage(i + 1); // now we declare that we're working on that page
	        pdf.addImage(canvasDataURL, 'PNG', 20, 40, (width * .62), (height * .62)); // add content to the page

	      }
	      
	      if (/android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(navigator.userAgent.toLowerCase())) {
	    	    window.open(pdf.output('bloburl', { filename: 'COEC-Certificate.pdf' }))
	    	} else {	      
	      pdf.save('COEC-Certificate.pdf');
	    	}
	    }
	  });
	}
</script> 
   
  </body>
</html>
