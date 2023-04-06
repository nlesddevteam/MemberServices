<%@ page language="java" contentType="text/html" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<html> 
<head>
<title>Google Apps (GSuite)</title>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<style>
.card-header {font-weight:bold;font-size:16px;}
.cardImg {width:100%;max-width:200px;padding:3px;}
.containerPanel {float:left;width:25%;}
.containerPanelHeader {font-weight:bold;}
.card-body {text-align:center;}
.card {min-width:330px;margin-top:20px;max-width:410px;}
.card-text {text-align:left;}
.card-title {text-transform:uppercase;font-size:14px;font-weight:bold;color:white;}


</style>
</head>

<body>
<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
<div class="siteBodyTextBlack">
<img src="includes/img/google.png" border=0 style="max-height:100px;float:right;padding-top:10px;"/>
<div class="siteHeaderGreen">Google Workspace Applications</div>
 
<p>Google Workspace represents a proven, secure cloud environment for messaging, scheduling, communication and collaboration. 

<p>By using Google Workspace, we have joined thousands of other Districts across Canada and throughout the world who are already using Google Workspace for teaching and learning. Using this technology, teachers and students have access to a system that is easy to use, scalable and can be tailored to meet individual needs of our diverse staff.

<p>Google Workspace is based on a technology focused approach to introduce innovation for teaching and learning in our classrooms. By putting the right tools in your hands, teachers can better help students reach their full potential.

<p>Google Workspace is a core suite of applications that Google offers to schools and educational institutions.

<p>These communication and collaboration apps include Gmail, Calendar, Drive, Classroom, Docs and Sites, and also includes access to many more collaborative tools supported by Google.

<p>These applications exists completely online (or in the cloud), meaning that all creations can be accessed from any device with an Internet connection.

<p>For additional information regarding Google Workspace security and privacy check out <a href="doc/gsuitesecurityprivacy.pdf" target="_blank">Security and Privacy Using Google Workspace</a>.

					
<p><div align="center">

<p><a href="https://accounts.google.com" class="btn btn-sm btn-primary" target="_blank">Google Workspace Login</a>
<a href="gfaqs.jsp" class="btn btn-sm btn-primary">Google FAQs</a>
<a href="../navigate.jsp" class="btn btn-sm btn-danger">Back to StaffRoom</a>
</div>

					
<p>For more information on a particular Google App, please review the topics below:
   
  
 
 <div class="card-deck"> 
  <div class="card text-center">
  <div class="card-header">Google Classroom</div>
    <div class="card-body">
     <img src="img/icons/classroom.png" class="cardImg"/><br/><br/>
       	Google Classroom is a free web-based platform offered by Google that allows teachers and students to easily communicate, collaborate, and manage assignments. 
       	Teachers can create online classrooms, add students, and post assignments, announcements, and other materials. 
       	Students can join the class and access the materials, submit assignments, and participate in discussions. Google Classroom integrates with other Google services like Google Drive, allowing teachers and students to easily share and access files. It also offers features like grading and feedback, making it a useful tool for remote learning and blended classrooms. 
       	Overall, Google Classroom is a user-friendly platform that streamlines the process of teaching and learning.
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://classroom.google.com">Google Classroom</a> </div>
  </div>
  
  
  <div class="card text-center">
  <div class="card-header">Google Mail (GMail)</div>
    <div class="card-body">
     <img src="img/icons/mail.png" class="cardImg"/><br/>
     Gmail is a free email service offered by Google. It provides a user-friendly interface, powerful spam filtering system, and ample storage space. 
     Gmail also has advanced search capabilities, labels, and filters to help you stay organized. 
     It integrates with other Google services like Google Drive and Google Calendar, allowing you to attach files or schedule events from your inbox. 
     Overall, Gmail is a versatile and reliable email service that offers a wide range of features to help you communicate efficiently.				 
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://mail.google.com">G-Mail</a> </div>
  </div>
  
<!-- GOOGLE CONTACTS -->  
  <div class="card text-center">
  <div class="card-header">Google Contacts</div>
    <div class="card-body">
     <img src="img/icons/contacts.png" class="cardImg"/><br/>
      Google Contacts is a contact management tool offered by Google. It allows users to store and organize their contact information, including phone numbers, email addresses, and physical addresses. 
      Contacts can be imported from other sources, such as CSV files or other email services, and synced across devices. 
      Google Contacts also offers features like contact groups and labels for better organization, as well as a search function for quick access to specific contacts. 
      It integrates with other Google services like Gmail and Google Calendar, allowing users to easily access contact information while composing emails or scheduling events.
       Overall, Google Contacts is a useful tool for managing your personal and professional contacts.
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://www.google.com/contacts">Contacts</a> </div>
  </div>
  
<!-- GOOGLE CALENDAR -->    
  <div class="card text-center">
  <div class="card-header">Google Calendar</div>
    <div class="card-body">
     <img src="img/icons/calendar.png" class="cardImg"/><br/>
    Google Calendar is a free web-based calendar service offered by Google. It allows users to easily schedule and manage events, meetings, and appointments.
     Users can create multiple calendars and share them with others, making it a useful tool for both personal and professional use. 
     Google Calendar also integrates with other Google services, such as Gmail and Google Meet, allowing users to easily schedule events and meetings directly from their email. It offers features like reminders, notifications, and customizable views, making it a versatile and customizable tool. Overall, Google Calendar is a powerful and user-friendly calendar service that helps users stay organized and manage their time effectively.
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://calendar.google.com">Google Calendar</a> </div>
  </div>
  
<!-- GOOGLE DRIVE -->  
  <div class="card text-center">
  <div class="card-header">Google Drive</div>
    <div class="card-body">
     <img src="img/icons/drive.png" class="cardImg"/><br/>
       Google Drive is a cloud-based file storage and synchronization service provided by Google. 
       It allows users to store, access, and share files from any device with internet access. 
       Google Drive offers a generous amount of free storage space and integrates with other Google services like Google Docs, Sheets, and Slides, allowing users to create and edit documents, spreadsheets, and presentations directly within the platform. It also allows users to share files and collaborate on them in real-time, making it a useful tool for both personal and professional use. 
       Overall, Google Drive is a versatile and convenient file storage and sharing service that simplifies the process of managing and collaborating on digital files.
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://drive.google.com">Google Drive</a> </div>
  </div>
  
<!-- GOOGLE GROUPS -->    
  <div class="card text-center">
  <div class="card-header">Google Groups</div>
    <div class="card-body">
     <img src="img/icons/groups.png" class="cardImg"/><br/>
      Google Groups is a free online platform offered by Google that allows users to create and participate in online forums and email-based groups. Users can create public or private groups and invite others to join, making it a useful tool for both personal and professional use. Google Groups also offers features like moderation, custom branding, and advanced search capabilities, making it a versatile and customizable tool. It integrates with other Google services, such as Gmail and Google Drive, allowing users to easily collaborate and share files within their groups. Overall, Google Groups is a user-friendly and effective platform for online communication and collaboration.
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://groups.google.com">Google Groups</a> </div>
  </div>
  
  
<!-- GOOGLE DOCS -->  
  <div class="card text-center">
  <div class="card-header">Google Docs</div>
    <div class="card-body">
     <img src="img/icons/docs.png" class="cardImg"/><br/>
      Google Docs is a free web-based word processing software offered by Google. It allows users to create and edit documents, spreadsheets, and presentations online and collaboratively in real-time. Google Docs offers a range of features including formatting tools, sharing and collaboration options, revision history, and integration with other Google services like Google Drive and Google Meet. It provides an easy-to-use interface and seamless sharing capabilities, making it a useful tool for both personal and professional use. Overall, Google Docs simplifies the process of creating and sharing documents and enables real-time collaboration from anywhere with internet access.
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://docs.google.com">Google Docs</a> </div>
  </div>
  
  
<!-- GOOGLE SHEETS -->   
  <div class="card text-center">
  <div class="card-header">Google Sheets</div>
    <div class="card-body">
     <img src="img/icons/sheets.png" class="cardImg"/><br/>
       Google Sheets is a free web-based spreadsheet software offered by Google. It allows users to create, edit, and collaborate on spreadsheets online in real-time. Google Sheets offers a wide range of features including formatting tools, data analysis functions, and integration with other Google services like Google Drive and Google Calendar. It provides an easy-to-use interface and seamless sharing capabilities, making it a useful tool for both personal and professional use. Google Sheets also allows users to import and export data in a variety of formats, making it easy to work with data from other sources. Overall, Google Sheets simplifies the process of managing and analyzing data.
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://sheets.google.com">Google Sheets</a> </div>
  </div>
  
<!-- GOOGLE SLIDES --> 
  <div class="card text-center">
  <div class="card-header">Google Slides</div>
    <div class="card-body">
     <img src="img/icons/slides.png" class="cardImg"/><br/>
      Google Slides is a free web-based presentation software offered by Google. It allows users to create and edit presentations online and collaborate with others in real-time. Google Slides offers a range of features including slide templates, formatting tools, and integration with other Google services like Google Drive and Google Meet. It provides an easy-to-use interface and seamless sharing capabilities, making it a useful tool for both personal and professional use. Google Slides also allows users to add multimedia elements such as images, videos, and audio, making it easy to create engaging and interactive presentations. Overall, Google Slides simplifies the process of creating and sharing presentations.
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://slides.google.com">Google Slides</a> </div>
  </div>
  
<!-- GOOGLE FORMS -->  
  <div class="card text-center">
  <div class="card-header">Google Forms</div>
    <div class="card-body">
     <img src="img/icons/forms.png" class="cardImg"/><br/>
      Google Forms is a free web-based tool offered by Google that allows users to create customizable online surveys and quizzes. It offers a range of question types including multiple-choice, short answer, and rating scales, and provides options for formatting and customizing the appearance of the form. Google Forms also integrates with other Google services like Google Sheets, allowing users to easily collect and analyze responses. It offers features like automatic grading and real-time feedback, making it a useful tool for both personal and professional use. Overall, Google Forms simplifies the process of creating and distributing surveys and quizzes and enables users to collect and analyze data efficiently.
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://forms.google.com">Google Forms</a> </div>
  </div>
  
  
 <!-- GOOGLE SITES -->   
  <div class="card text-center">
  <div class="card-header">Google Sites</div>
    <div class="card-body">
     <img src="img/icons/sites.png" class="cardImg"/><br/><br/>
      Google Sites is a free web-based website creation tool offered by Google. It allows users to create and publish simple websites without the need for coding or web development experience. Google Sites provides an easy-to-use interface and drag-and-drop functionality, making it easy to create professional-looking websites in minutes. It also offers features like customizable templates, integration with other Google services like Google Drive, and collaboration options, making it a useful tool for both personal and professional use. Overall, Google Sites simplifies the process of creating and publishing websites, making it accessible to a wide range of users.
			  				
	<p>If you require a Google Site for your school, please contact us and we can provide a basic template (See <a href="https://sites.google.com/nlesd.ca/templatehow-tos/getting-started" target="_blank">Template Guide</a>) for your to start with.
       
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://sites.google.com">Google Sites</a> </div>
  </div>
  
<!-- GOOGLE TAKEOUT -->    
  <div class="card text-center">
  <div class="card-header">Google Takeout/Transfer</div>
    <div class="card-body">
     <img src="img/icons/takeout.png" class="cardImg"/><br/>
      Google Takeout is a free web-based service provided by Google that allows users to export a copy of their data from various Google services such as Gmail, Google Drive, Google Photos, and more. It offers a range of export options and allows users to select which data they want to export and in which format. Google Takeout is useful for creating backups of important data, transferring data between different Google accounts, and preserving data before deleting a Google account. Overall, Google Takeout provides an easy-to-use and secure way for users to export and backup their data from various Google services.
		
		<p><a href="doc/GoogleTakeoutTransfer.pdf" target="_blank">Options for Migrating and Download of your Data (PDF)</a>
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://takeout.google.com/transfer">Google Takeout</a> </div>
  </div>
  </div>
 
 


  
 
  
  <div style="clear:both;"></div>
<br/><br/>
 <div align="center"><a href="../navigate.jsp" class="btn btn-sm btn-danger">Back to StaffRoom</a></div>

 

</div> 
</div>
</div>

</body>
</html>