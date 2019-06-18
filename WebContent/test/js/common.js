var clicked = false;

var processing = new Image();
processing.src = "/MemberServices/images/processing_ani.gif";
//processing.src = "/MemberServices/images/processing.gif";
var authentication = new Image();
authentication.src = "/MemberServices/images/signin_status.gif";
//authentication.src = "/MemberServices/images/processing.gif";
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
  else if((form.user.value.indexOf("@") >= 0)&&(emailCheck(form.user.value)==false))
  {
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
    document.images['processing'].src = processing.src;
    document.images['confirm'].src='images/spacer.gif'; 
    document.images['confirm'].onmouseover="src='images/spacer.gif';" 
    document.images['confirm'].onmouseout="src='images/spacer.gif';"
    document.images['confirm'].onmousedown="src='images/spacer.gif';"
    document.images['confirm'].onmouseup="src='images/spacer.gif';"
    document.images['cancel'].src='images/spacer.gif'; 
    document.images['cancel'].onmouseover="src='images/spacer.gif';" 
    document.images['cancel'].onmouseout="src='images/spacer.gif';"
    document.images['cancel'].onmousedown="src='images/spacer.gif';"
    document.images['cancel'].onmouseup="src='images/spacer.gif';"
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

function isTeacher()
{
  var jobs = document.register.job;
        
  if(jobs.selectedIndex != -1)
  {
    if((jobs.options[jobs.selectedIndex].text=="TEACHER")
      || (jobs.options[jobs.selectedIndex].text=="VICE PRINCIPAL")
      || (jobs.options[jobs.selectedIndex].text=="PRINCIPAL")
      || (jobs.options[jobs.selectedIndex].text=="SCHOOL SECRETARY")
      || (jobs.options[jobs.selectedIndex].text=="STUDENT ASSISTANT")
      || (jobs.options[jobs.selectedIndex].text=="SCHOOL MAINTENANCE")
      || (jobs.options[jobs.selectedIndex].text=="GUIDANCE COUNSELLOR"))
    {
      document.register.school.disabled=false;
    }
    else
    {
      document.register.school.disabled=true;
    }
    document.register.school.selectedIndex=-1;
  }
}

function isSecretary()
{
  var jobs = document.register.job;
        
  if(jobs.selectedIndex != -1)
  {
    if(jobs.options[jobs.selectedIndex].text=="SCHOOL SECRETARY")
    {
      document.register.school.disabled=false;
    }
    else
    {
      document.register.school.disabled=true;
    }
    document.register.school.selectedIndex=-1;
  }
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

alert("Use FirstClass Email address for Member Services sign-in. (ie, johndoe@esdnl.ca)");
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

if(((domArr[0]!="awsb")&&(domArr[0]!="esdnl"))||(domArr[1]!="ca"))
{
  alert("awsb.ca or esdnl.ca domain name required.");
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

function validateRegistration(form)
{
  var check = true;

  if(form.firstname.value == "")
  {
    alert('First name required for registration.');
    check = false;
  }
  else if(form.lastname.value == "")
  {
    alert('Last name required for registration.');
    check = false;
  }
  else if(form.emailaddr.value == "")
  {
    alert('Email required for registration.');
    check = false;
  }
  else if(form.confirm_emailaddr.value == "")
  {
    alert('Email confirmation required for registration.');
    check = false;
  }
  else if(form.emailaddr.value != form.confirm_emailaddr.value)
  {
    alert('Email confirmation does not match.');
    check = false;
  }
  else if(form.job.selectedIndex == -1)
  {
    alert('Job Function required for registration.');
    check = false;
  }
  else if(form.job.selectedIndex != -1)
  {
    if((form.job.options[form.job.selectedIndex].text == "TEACHER")
      || (form.job.options[form.job.selectedIndex].text == "VICE PRINCIPAL")
      || (form.job.options[form.job.selectedIndex].text == "PRINCIPAL")
      || (form.job.options[form.job.selectedIndex].text == "SCHOOL SECRETARY")
      || (form.job.options[form.job.selectedIndex].text == "STUDENT ASSISTANT")
      || (form.job.options[form.job.selectedIndex].text == "STUDENT MAINTENANCE")
      || (form.job.options[form.job.selectedIndex].text == "GUIDENCE COUNSELLOR"))
    {
      if(form.school.selectedIndex == -1)
      {
        alert('School required for registration.');
        check = false;
      }   
    }
  }
  else if((form.emailaddr.value.indexOf("@") >= 0)&&(emailCheck(form.emailaddr.value)==false))
  {
    check = false;
    alert('Invalid FirstClass Email Address (ie; johndoe@esdnl.ca).');
  }
  
  if(check == true)
  {
    onClickAdd(form);
  }
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
    alert('FirstClass User ID required.');
    check = false;
  }
  else if(form.password.value == "")
  {
    alert('FirstClass Password required.');
    check = false;
  }
  else if(form.confirm_password.value == "")
  {
    alert('FirstClass Password Confirmation required.');
    check = false;
  }
  else if(form.password.value != form.confirm_password.value)
  {
    alert('FirstClass Confirmation Password does not match.');
    check = false;
  }
  else if(form.emailaddr.value == "")
  {
    alert('FirstClass Email Address required.');
    check = false;
  }
  else if(form.confirm_emailaddr.value == "")
  {
    alert('FirstClass Email Confirmation required.');
    check = false;
  }
  else if(form.emailaddr.value != form.confirm_emailaddr.value)
  {
    alert('FirstClass Email Confirmation does not match.');
    check = false;
  }
  else if((form.emailaddr.value.indexOf("@") >= 0)&&(emailCheck(form.emailaddr.value)==false))
  {
    check = false;
    alert('Invalid FirstClass Email Address (ie; johndoe@esdnl.ca).');
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