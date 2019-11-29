


function goBack() {
    window.history.back()
}


var clicked = false;

var processing = new Image();
processing.src = "/MemberServices/images/processing_ani.gif";
var authentication = new Image();
authentication.src = "/MemberServices/images/signin_status.gif";

function openWindow(id,url,w,h, scroll) {
  var winl = (screen.width-w)/2;
  var wint = (screen.height - h - 25 )/2;

  window.open(url,id,"titlebar=0,toolbar=0,location=no,top="+wint+",left="+winl+",directories=0,status=1,menbar=0,scrollbars="+scroll+",resizable=1,width="+w+",height="+h);

  //if (navigator.appName == 'Netscape') {
  //          popUpWin.focus();
  //  }
}

function validateSignin(form)
{
  var check = true;

  if(form.user.value == "")
  {
    alert('Email address required for sign-in.');
    check = false;
    document.getElementById('user').focus();
  }
  else if(form.user.value.indexOf("@") >= 0)
  {
	alert('Your Firstclass User ID should be used instead of your email address.');
    check = false;
    document.getElementById('user').focus();
    document.getElementById('user').select();
  }
  else if(form.pass.value == "")
  {
    alert('Password required for sign-in.');
    check = false;
    document.getElementById('pass').focus();
  }
  
  if(check == true)
  {
    document.images['processing'].src=authentication.src;

    //used for old sign-in page
    //document.images['login'].src='images/spacer.gif'; 
    //document.images['login'].onmouseover="src='images/spacer.gif';" 
    //document.images['login'].onmouseout="src='images/spacer.gif';"
    //document.images['login'].onmousedown="src='images/spacer.gif';"
    //document.images['login'].onmouseup="src='images/spacer.gif';"

   
    //document.images['reset'].src='images/spacer.gif'; 
    //document.images['reset'].onmouseover="src='images/spacer.gif';" 
    //document.images['reset'].onmouseout="src='images/spacer.gif';"
    //document.images['reset'].onmousedown="src='images/spacer.gif';"
    //document.images['reset'].onmouseup="src='images/spacer.gif';"

    //form.submit();
  }

  return check;
}

function onClick(form)
{
  if(clicked == false)
  {
    clicked = true;    
    form.submit();
  }
  else
  {
    alert('Processing...');
  }
}

function onClickAdd(form)
{
  if(clicked == false)
  {
    clicked = true;
    document.images['processing'].src = processing.src;
    document.images['add'].src='images/spacer.gif'; 
    document.images['add'].onmouseover="src='images/spacer.gif';" 
    document.images['add'].onmouseout="src='images/spacer.gif';"
    document.images['add'].onmousedown="src='images/spacer.gif';"
    document.images['add'].onmouseup="src='images/spacer.gif';"
    form.submit();
  }
  else
  {
    alert('Processing...');
  }
}

function go(url) {
    if (document.images)
        location.replace(url);
    else
        location.href = url;
}



function emailSelect()
{
  var emails = self.document.addressbook.emailaddr;
  var recipent = self.opener.document.signin.user;
        
  if(emails.selectedIndex != -1)
  {
    recipent.value= emails.options[emails.selectedIndex].value;
  }
}

function emailCheck (emailStr) 
{

/* The following variable tells the rest of the function whether or not
to verify that the address ends in a two-letter country or well-known
TLD.  1 means check it, 0 means don't. */

var checkTLD=1;

/* The following is the list of known TLDs that an e-mail address must end with. */

var knownDomsPat=/^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum|ca)$/;

/* The following pattern is used to check if the entered e-mail address
fits the user@domain format.  It also is used to separate the username
from the domain. */

var emailPat=/^(.+)@(.+)$/;

/* The following string represents the pattern for matching all special
characters.  We don't want to allow special characters in the address. 
These characters include ( ) < > @ , ; : \ " . [ ] */

var specialChars="\\(\\)><@,;:\\\\\\\"\\.\\[\\]";

/* The following string represents the range of characters allowed in a 
username or domainname.  It really states which chars aren't allowed.*/

var validChars="\[^\\s" + specialChars + "\]";

/* The following pattern applies if the "user" is a quoted string (in
which case, there are no rules about which characters are allowed
and which aren't; anything goes).  E.g. "jiminy cricket"@disney.com
is a legal e-mail address. */

var quotedUser="(\"[^\"]*\")";

/* The following pattern applies for domains that are IP addresses,
rather than symbolic names.  E.g. joe@[123.124.233.4] is a legal
e-mail address. NOTE: The square brackets are required. */

var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;

/* The following string represents an atom (basically a series of non-special characters.) */

var atom=validChars + '+';

/* The following string represents one word in the typical username.
For example, in john.doe@somewhere.com, john and doe are words.
Basically, a word is either an atom or quoted string. */

var word="(" + atom + "|" + quotedUser + ")";

// The following pattern describes the structure of the user

var userPat=new RegExp("^" + word + "(\\." + word + ")*$");

/* The following pattern describes the structure of a normal symbolic
domain, as opposed to ipDomainPat, shown above. */

var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$");

/* Finally, let's start trying to figure out if the supplied address is valid. */

/* Begin with the coarse pattern to simply break up user@domain into
different pieces that are easy to analyze. */

var matchArray=emailStr.match(emailPat);

if (matchArray==null) {

/* Too many/few @'s or something; basically, this address doesn't
even fit the general mould of a valid e-mail address. */

alert("Use FirstClass Email address for Member Services sign-in. (ie, johndoe@nlesd.ca)");
return false;
}
var user=matchArray[1];
var domain=matchArray[2];

// Start by checking that only basic ASCII characters are in the strings (0-127).

for (i=0; i<user.length; i++) {
if (user.charCodeAt(i)>127) {
alert("Ths username contains invalid characters.");
return false;
   }
}
for (i=0; i<domain.length; i++) {
if (domain.charCodeAt(i)>127) {
alert("Ths domain name contains invalid characters.");
return false;
   }
}

// See if "user" is valid 

if (user.match(userPat)==null) {

// user is not valid

alert("The username doesn't seem to be valid.");
return false;
}

/* if the e-mail address is at an IP address (as opposed to a symbolic
host name) make sure the IP address is valid. */

var IPArray=domain.match(ipDomainPat);
if (IPArray!=null) {

// this is an IP address

for (var i=1;i<=4;i++) {
if (IPArray[i]>255) {
alert("Destination IP address is invalid!");
return false;
   }
}
return true;
}

// Domain is symbolic name.  Check if it's valid.
 
var atomPat=new RegExp("^" + atom + "$");
var domArr=domain.split(".");
var len=domArr.length;
//for (i=0;i<len;i++) {
//if (domArr[i].search(atomPat)==-1) {
//alert("The domain name does not seem to be valid.");
//   }
//}

if(((domArr[0]!="nlesd")&&(domArr[0]!="esdnl"))||(domArr[1]!="ca"))
{
  alert("nlesd.ca or esdnl.ca domain name required.");
  return false;
}

/* domain name seems valid, but now make sure that it ends in a
known top-level domain (like com, edu, gov) or a two-letter word,
representing country (uk, nl), and that there's a hostname preceding 
the domain or country. */

if (checkTLD && domArr[domArr.length-1].length!=2 && 
domArr[domArr.length-1].search(knownDomsPat)==-1) {
alert("The address must end in a well-known domain or two letter " + "country.");
return false;
}

// Make sure there's a host name preceding the domain.

if (len<2) {
alert("This address is missing a hostname!");
return false;
}

// If we've gotten this far, everything's valid!
return true;
}



function validateAccountConvert(form)
{
  var check = true;

  if(form.firstname.value == "")
  {
    alert('First name required.');
    check = false;
  }
  else if(form.lastname.value == "")
  {
    alert('Last name required.');
    check = false;
  }
  else if(form.uid.value == "")
  {
    alert('User ID required.');
    check = false;
  }
  else if(form.password.value == "")
  {
    alert('Password required.');
    check = false;
  }
  else if(form.confirm_password.value == "")
  {
    alert('Password Confirmation required.');
    check = false;
  }
  else if(form.password.value != form.confirm_password.value)
  {
    alert('Confirmation Password does not match.');
    check = false;
  }
  else if(form.emailaddr.value == "")
  {
    alert('Email Address required.');
    check = false;
  }
  else if(form.confirm_emailaddr.value == "")
  {
    alert('Email Confirmation required.');
    check = false;
  }
  else if(form.emailaddr.value != form.confirm_emailaddr.value)
  {
    alert('Email Confirmation does not match.');
    check = false;
  }
  else if((form.emailaddr.value.indexOf("@") >= 0)&&(emailCheck(form.emailaddr.value)==false))
  {
    check = false;
    alert('Invalid Email Address (ie; johndoe@nlesd.ca).');
    document.getElementById('emailaddr').focus();
    document.getElementById('emailaddr').select();
  }
  
  
  
  if(check == true)
  {
    onClickAdd(form);
  }
}

function submitenter(myfield,e)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   if(validateSignin(myfield.form))myfield.form.submit();
   return false;
   }
else
   return true;
}

function toggleTableRowHighlight(target, color)
{
  var rowSelected = document.getElementById(target);
  
  rowSelected.style.backgroundColor=color; 
}

function numberTest (sNr)
{
    var reFloatF1 = /^(\d+)$/; // format 1
    
    return (reFloatF1.test (sNr));
}

function validateEvent(form)
{
  var check = true;
  var seltxt;

  if(form.evttype.selectedIndex == 0)
  {
	  
	  $("#scheduleMessage").css("display","block").delay(5000).html("<b>ERROR:</b> Please select an Event Type.").fadeOut();
	  $("#evttype").focus();
	  check = false;
  }
  else if(form.EventName.value=="")
  {    
    
	  $("#scheduleMessage").css("display","block").delay(5000).html("<b>ERROR:</b> You have to give your event a title. Geez by'e!").fadeOut();
	  $("#EventName").focus();
	  check = false;
  }
  else if(form.EventName.value.length > 150)
  {    
    	$("#scheduleMessage").css("display","block").delay(5000).html("<b>ERROR:</b> Opps! you have too many characters in your Title. 150 Max please.").fadeOut();
	    $("#EventName").focus();
	    check = false;
  }
  else if (CKEDITOR.instances.EventDesc.getData() ==""){		    
    $("#scheduleMessage").css("display","block").delay(5000).html("<b>ERROR:</b> How is anyone to know what this event is about if you don't enter a description? Please tell us what this event will be about.").fadeOut();
	$("#EventDesc").focus();
	check = false;
  }
  else if(form.EventDesc.value.length > 500)
  {
	  	$("#scheduleMessage").css("display","block").delay(5000).html("<b>ERROR:</b> Description cannot exceed 500 characters! Please adjust.").fadeOut();
		$("#EventDesc").focus();
		check = false;
  }
  else if(form.EventLocation.value=="")
  {
	  	$("#scheduleMessage").css("display","block").delay(5000).html("<b>ERROR:</b> Sounds like a great event! Only problem is you didnt specify a location. Please enter a location for your event.").fadeOut();
		$("#lstSchoolID").focus();
		check = false;
  }
  else if(form.EventLocation.value.length > 150)
  {
	  	$("#scheduleMessage").css("display","block").delay(5000).html("<b>ERROR:</b> Too many characters for your Location. Must not exceed 150. Please adjust.").fadeOut();
		$("#lstSchoolID").focus();
		check = false;
  }
  else if($('#lstSchoolZone').val() == "")
  {
    alert( 'Region must be selected.' );
    check = false;
  }
  else if(form.EventDate.value=="")
  {
    alert( 'Date is Required' );
    check = false;
  }
  else
  {
    seltxt = form.evttype.options[form.evttype.selectedIndex].text;
    
    if(seltxt == 'CLOSE-OUT DAY PD SESSION')
    {
      if(form.closeoutoption.selectedIndex == 0)
      {
        alert('Please select close-out session option.');
        check = false;
      }
    }

    if(seltxt == 'DISTRICT CALENDAR CLOSE-OUT ENTRY')
    {
      if(form.EventEndDate.value=="")
      {
        alert( 'End Date is Required' );
        check = false;
      }
    }
    
    if(check && ((seltxt == 'PD OPPORTUNITY') || (seltxt == 'CLOSE-OUT DAY PD SESSION')))
    {
      if(form.limited.checked)
      {
        if(form.max.value=="")
        {
          alert('Maximum Number of Participants required.');
          check = false;
        }
        else if(! numberTest(form.max.value))
        {
          alert('Invalid number format. Should be positive number only.');
          check = false;
        }
      }
    }

    if(check && form.evttime.checked)
    {
      if(form.shour.selectedIndex==-1)
      {
        alert( 'Start Time Hour is Required' );
        check = false;
      }
      else if(form.sminute.selectedIndex==-1)
      {
        alert( 'Start Time Minute is Required' );
        check = false;
      }
      else if(form.sAMPM.selectedIndex==-1)
      {
        alert( 'Start Time AM/PM is Required' );
        check = false;
      }
      else if(form.fhour.selectedIndex==-1)
      {
        alert( 'Finish Time Hour is Required' );
        check = false;
      }
      else if(form.fminute.selectedIndex==-1)
      {
        alert( 'Finish Time Minute is Required' );
        check = false;
      }
      else if(form.fAMPM.selectedIndex==-1)
      {
        alert( 'Finish Time AM/PM is Required' );
        check = false;
      }
      else if((eval(form.shour.value) > eval(form.fhour.value))&&(form.sAMPM.value==form.fAMPM.value))
      {
    	  if(eval(form.shour.value) < 12){
    		  alert( 'Start time later then Finish time.' );
    		  check = false;
    	  }
      }
      else if((eval(form.shour.value) == eval(form.fhour.value))&&(form.sAMPM.value==form.fAMPM.value)
        &&((eval(form.sminute.value) > eval(form.fminute.value))))
      {
        alert( 'Start time later then Finish time.' );
        check = false;
      }
    }
    
    if(check && (seltxt == 'SCHOOL PD REQUEST'))
    {
      if(form.agendafile.value == null)
      {
        alert( 'Please select adgenda file.' );
        check = false;
      }
    }
    
    $('#evtreqs option:selected').each(function(){
    	if($(this).attr('extrainfo') && $('#' + $(this).attr('extrainfo')).val() == ''){
    		alert('Please enter ' + $(this).attr('extrainfo') + '.');
    		check = false;
    	}
    })
  }

  return check;
}
