<%@ page language="java"
        session="true"
         import="com.awsd.security.*, 
                 java.util.*"%>

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
    <title>Code of Ethics Training Module</title>    
 
  </head>
  <body>
  
<!-- TEACHER NAME UPDATE -->

<div class="container-fluid">		
				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="font-size:12px;">   
            
            <span style="font-size:16px;font-weight:bold;">Code of Ethics and Conduct</span><br/><br/>

The Newfoundland and Labrador English School District is committed to a respectful, ethical, open, and transparent workplace. 
Employees are entitled to a workplace that supports them in making ethical decisions while treating everyone with respect, and avoiding conflicts of interest.


<br/><br/>The NLESD developed a Code of Ethics and Conduct to support this commitment, and it can be <a href="https://www.nlesd.ca/about/doc/CodeofEthicsConduct.pdf" target="_blank">found here</a>.
 It applies to all employees and complements other employee professional codes of conduct. 
 It also supports a recommendation from the Auditor's General's Report - Management of the Procurement of Goods and Services.

<br/><br/>

<div class="alert alert-info">Employees are required to complete the training video below. The training will take approximately 35 minutes to complete.</div>


<div class="alert alert-warning">Employees must also complete a Declaration certificate. 
The Declaration states you have completed the training, and agree to abide by the District's Code of Ethics and Conduct, and the Conflict of Interest Policy. 
Once you complete the training video, the certificate download will become available for completion and uploading to MyHRP.

</div> 

<div class="noticePhone alert alert-danger" style="text-align:center;"><b>**** IMPORTANT NOTICE ****</b><br/> 
We detected you are on a mobile device (phone or tablet). We highly recommend you use a desktop computer or laptop for this training. 
Some mobile devices such as some newer iOS devices ( iPhone or iPad ) will not properly generate and save a PDF certificate.
You may need to repeat any training if you continue to use this mobile device if it fails to generate a certificate. Typical results from this failure are a blank certificate.</div>

<span style="font-size:16px;font-weight:bold;">Steps to Follow for your Training</span><br/>
<ol>
<li>Read all instructions and text on this page before doing anything.
<li>Recommend using a desktop PC or laptop computer to do this training.
<li>Play the video below from the play button in the middle of the screen.  <b>Do not play via YouTube</b>. This will result in NO certificate being issued.
<li>Play the video until it automatically stops. You can only play or pause. 
<li>Review all the resources and references listed below on this page.
<li>Once the video stops playing at the end, a Declaration certificate will become available to view via a link below the video.
<li>Proceed to view and download this Declaration certificate to your device.
<li>You must then upload this PDF file to the documents section (selecting the Code of Ethics and Conduct Declaration type) of your MyHR profile.
<li>To login to your MyHR profile, visit the Job Opportunities section of the NLESD website.
</ol>

<hr>

      
       <span style="font-size:16px;font-weight:bold;">Training Video</span><br/>       
       <br/><br/>
       <div align="center"><!-- &controls=0 -->
       	<iframe id="videoEmbed" width="100%" height="450" src="https://www.youtube.com/embed/h0oMcOvk8rw?enablejsapi=1&amp;controls=0" frameborder="0" style="border: solid 1px #37474F;max-width:800px;"></iframe>
		<br/><span style="color:Red;font-size:10px;"><b>NOTE:</b> Do NOT click on the YouTube logo bottom right of the video above or use full screen view as it will invalidate your training.</span>
		
		</div>

<br/>


    <div align="center"><a href="#looking-for-something-are-you" class="certifyLink btn btn-sm btn-primary disabled">Please watch the training video above. Your Declaration Certificate will be ready here once completed.</a><br/><br/>
            
<div class="alert alert-danger"><b>IMPORTANT NOTICE:</b>  Once the video has completed playing and finished automatically, the blue area above will turn GREEN and inform you that your certificate is READY.  Please be patient.
Click on the GREEN area to proceed to your certificate of training.
If you already watched the video, and downloaded your certificate, check your Downloads folder for your certificate. 
If you have lost it, you will need to re-watch the video to generate a new certificate.</div>
    
   </div>  
   </div>
  				</div>
  				<hr>
  				
  				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="font-size:12px;">   
  				<span style="font-size:16px;font-weight:bold;">Resources/References</span><br/>
  				
  				These resources include links to legislation, policies and other documents discussed throughout the session.

  				</div>
  				</div>
  				<br/>
  				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6" style="font-size:12px;">   
  				
  				
<ul> 				
<li><a href="https://assembly.nl.ca/legislation/sr/statutes/a01-2.htm" target="_blank">Access to Information and Protection of Privacy (ATIPPA)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1456147238937.pdf" target="_blank">Acceptable Use of Technology  (FIN-500)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1456153054578.pdf" target="_blank">Community Use of Schools (OPER-602)</a>
<li><a href="https://www.nlesd.ca/about/doc/CodeofEthicsConduct.pdf" target="_blank">Code of Ethics and Conduct</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1504806804954.pdf" target="_blank">Conflict of Interest (FIN-405)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1504805710711.pdf" target="_blank">Conflict of Interest Disclosure Form</a>
<li><a href="https://www.assembly.nl.ca/Legislation/sr/statutes/c30-1.htm" target="_blank">Conflict of Interest Act</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1456152103375.pdf" target="_blank">Disability Management (HR-809)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1637169448523.pdf" target="_blank">Email (FIN-501)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1456151700375.pdf" target="_blank">Employee Records (HR-804)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1529585996768.pdf" target="_blank">Facility Security and Access Control (OPER-605)</a>
<li><a href="https://drive.google.com/file/d/1aWEhD0i9IbK5HWVGm7u4Q2mWiYFruFlt/view?usp=sharing" target="_blank">Guide to NLESD Purchasing for Schools</a>
<li><a href="https://assembly.nl.ca/legislation/sr/statutes/h13-1.htm" target="_blank">Human Rights Code Newfoundland and Labrador</a>
</ul> 			
 </div>  
 <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6" style="font-size:12px;">
<ul>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1548691684460.pdf" target="_blank">Occupational Health and Safety (HR-808)</a>
<li><a href="https://www.assembly.nl.ca/legislation/sr/statutes/o03.htm" target="_blank">Occupational Health and Safety Act</a>
<li><a href="https://assembly.nl.ca/Legislation/sr/statutes/p37-2.htm" target="_blank">Public Interest Disclosure & Whistleblower Protection Act</a>
<li><a href="https://www.assembly.nl.ca/legislation/sr/statutes/p41-001.htm" target="_blank">Public Procurement Act</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1634057936324.pdf" target="_blank">Respectful Workplace/Harassment Prevention and Resolution (HR800)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1455910704843.pdf" target="_blank">Signing Authority (FIN-400)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1456147498671.pdf" target="_blank">Social Media Use (FIN-502)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1568054925928.pdf" target="_blank">Student Records (PROG-310)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1618488328738.pdf" target="_blank">Student Travel (PROG-320)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1504103153912.pdf" target="_blank">Travel - District Employees (FIN-400)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1560258837706.pdf" target="_blank">Vehicle Usage (FIN-504)</a>
<li><a href="https://www.nlesd.ca/includes/files/policies/doc/1555348470575.pdf" target="_blank">Whistleblowing: Employee Disclosure of Wrongdoing (HR-813)</a>
</ul>  
 
 </div>
  				
  </div>
  <hr>
  <div class="alert alert-info" style="text-align:center;font-size:11px;">For questions relating to the Code of Ethics and Conduct, please contact Susan Tobin at 709.757.4652 (<a href="mailto:geofftaylor@nlesd.ca?subject=COC Certification">susantobin1@nlesd.ca</a>).
  <br/>If you require technical support, please email Geoff Taylor (<a href="mailto:geofftaylor@nlesd.ca?subject=COC Certification">geofftaylor@nlesd.ca</a>). </div>
  
<hr>

<div align="center" style="padding-top:5px;padding-bottom:10px;"><a href="index.jsp" class="btn btn-danger btn-sm">Back to Training Modules</a></div>
   
   </div>

   
   <script>  
   
   if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
	   $(".noticePhone").css("display","block");
	 } else {
		 
		 $(".noticePhone").css("display","none");
	 }
   
   
   setInterval(refreshIframe1, 60000);
   
   function refreshIframe1() {	   
       $("#Frame1")[0].src = $("#Frame1")[0].src;      
   }
</script>
<iframe id="Frame1" src="keepalive.jsp" frameborder="0" height=0 width=0 frameborder=0 style="width:0;height:0;"></iframe>
   
  
   
   
  </body>
</html>
