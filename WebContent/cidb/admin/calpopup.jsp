<%@ page language="java"%>

<html>
<head>
<title>Select Date, Please.</title>
<style>
td {
	font-family: Tahoma, Verdana, sans-serif;
	font-size: 12px;
}
</style>
<script language="JavaScript">

// months as they appear in the calendar's title
var ARR_MONTHS = ["January", "February", "March", "April", "May", "June",
		"July", "August", "September", "October", "November", "December"];
// week day titles as they appear on the calendar
var ARR_WEEKDAYS = ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"];
// day week starts from (normally 0-Mo or 1-Su)
var NUM_WEEKSTART = 1;
// path to the directory where calendar images are stored. trailing slash req.
var STR_ICONPATH = '../includes/images/';

var re_url = new RegExp('datetime=(\\-?\\d+)');
var dt_current = (re_url.exec(String(window.location))
	? new Date(new Number(RegExp.$1)) : new Date());
var re_id = new RegExp('id=(\\d+)');
var num_id = (re_id.exec(String(window.location))
	? new Number(RegExp.$1) : 0);
var obj_caller = (window.opener ? window.opener.calendars[num_id] : null);

if (obj_caller && obj_caller.year_scroll) {
	// get same date in the previous year
	var dt_prev_year = new Date(dt_current);
	dt_prev_year.setFullYear(dt_prev_year.getFullYear() - 1);
	if (dt_prev_year.getDate() != dt_current.getDate())
		dt_prev_year.setDate(0);
	
	// get same date in the next year
	var dt_next_year = new Date(dt_current);
	dt_next_year.setFullYear(dt_next_year.getFullYear() + 1);
	if (dt_next_year.getDate() != dt_current.getDate())
		dt_next_year.setDate(0);
}

// get same date in the previous month
var dt_prev_month = new Date(dt_current);
dt_prev_month.setMonth(dt_prev_month.getMonth() - 1);
if (dt_prev_month.getDate() != dt_current.getDate())
	dt_prev_month.setDate(0);

// get same date in the next month
var dt_next_month = new Date(dt_current);
dt_next_month.setMonth(dt_next_month.getMonth() + 1);
if (dt_next_month.getDate() != dt_current.getDate())
	dt_next_month.setDate(0);

// get first day to display in the grid for current month
var dt_firstday = new Date(dt_current);
dt_firstday.setDate(1);
dt_firstday.setDate(1 - (7 + dt_firstday.getDay() - NUM_WEEKSTART) % 7);

// function passing selected date to calling window
function set_datetime(n_datetime, b_close) {
	if (!obj_caller) return;
  
  var t_str = null;
  var hh = null;
  var mm = null;
  
  if(document.cal){
    if(document.cal.timeAMPM.options[document.cal.timeAMPM.selectedIndex].value == 'PM')
      hh = ((new Number(document.cal.timeH.options[document.cal.timeH.selectedIndex].value)).valueOf() + 12);
    else
      hh = document.cal.timeH.options[document.cal.timeH.selectedIndex].value;
    mm = document.cal.timeM.options[document.cal.timeM.selectedIndex].value;
    t_str = hh + ':' + mm;
  }
  
	var dt_datetime = obj_caller.prs_time((document.cal ? t_str : ''), new Date(n_datetime));

	if (!dt_datetime) return;
	if (b_close) {
		window.close();
		
		obj_caller.target.value = (document.cal
			? obj_caller.gen_tsmp(dt_datetime)
			: obj_caller.gen_date(dt_datetime)
		);
		
		if(window.opener && window.opener.document.forms['availability_viewDateForm']){
			window.opener.document.forms['availability_viewDateForm'].submit();
		}
	}
	else obj_caller.popup(dt_datetime.valueOf());
}

</script>
</head>
<body bgcolor="#FFFFFF" marginheight="5" marginwidth="5" topmargin="5"
	leftmargin="5" rightmargin="5">
<table class="clsOTable" cellspacing="0" border="0" width="100%">
	<tr>
		<td bgcolor="#4682B4">
		<table cellspacing="1" cellpadding="3" border="0" width="100%">
			<tr>
				<td colspan="7">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tr>
						<script language="JavaScript">
						document.write(
						'<td>'+(obj_caller&&obj_caller.year_scroll?'<a href="javascript:set_datetime('+dt_prev_year.valueOf()+')"><img src="'+STR_ICONPATH+'prev_year.gif" width="16" height="16" border="0" alt="previous year"></a>&nbsp;':'')+'<a href="javascript:set_datetime('+dt_prev_month.valueOf()+')"><img src="'+STR_ICONPATH+'prev.gif" width="16" height="16" border="0" alt="previous month"></a></td>'+
						'<td align="center" width="100%"><font color="#ffffff">'+ARR_MONTHS[dt_current.getMonth()]+' '+dt_current.getFullYear() + '</font></td>'+
						'<td><a href="javascript:set_datetime('+dt_next_month.valueOf()+')"><img src="'+STR_ICONPATH+'next.gif" width="16" height="16" border="0" alt="next month"></a>'+(obj_caller && obj_caller.year_scroll?'&nbsp;<a href="javascript:set_datetime('+dt_next_year.valueOf()+')"><img src="'+STR_ICONPATH+'next_year.gif" width="16" height="16" border="0" alt="next year"></a>':'')+'</td>'
						);
						</script>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<script language="JavaScript">

// print weekdays titles
for (var n=0; n<7; n++)
	document.write('<td bgcolor="#87cefa" align="center"><font color="#ffffff">'+ARR_WEEKDAYS[(NUM_WEEKSTART+n)%7]+'</font></td>');
document.write('</tr>');

// print calendar table
var dt_current_day = new Date(dt_firstday);
while (dt_current_day.getMonth() == dt_current.getMonth() ||
	dt_current_day.getMonth() == dt_firstday.getMonth()) {
	// print row heder
	document.write('<tr>');
	for (var n_current_wday=0; n_current_wday<7; n_current_wday++) {
		if (dt_current_day.getDate() == dt_current.getDate() &&
			dt_current_day.getMonth() == dt_current.getMonth())
			// print current date
			document.write('<td bgcolor="#ffb6c1" align="center" width="14%">');
		else if (dt_current_day.getDay() == 0 || dt_current_day.getDay() == 6)
			// weekend days
			document.write('<td bgcolor="#dbeaf5" align="center" width="14%">');
		else
			// print working days of current month
			document.write('<td bgcolor="#ffffff" align="center" width="14%">');

		document.write('<a href="javascript:set_datetime('+dt_current_day.valueOf() +', true);">');

		if (dt_current_day.getMonth() == this.dt_current.getMonth())
			// print days of current month
			document.write('<font color="#000000">');
		else 
			// print days of other months
			document.write('<font color="#606060">');
			
		document.write(dt_current_day.getDate()+'</font></a></td>');
		dt_current_day.setDate(dt_current_day.getDate()+1);
	}
	// print row footer
	document.write('</tr>');
}
if (obj_caller && obj_caller.time_comp)
{
	document.write('<form onsubmit="javascript:set_datetime('+dt_current.valueOf()+', true)" name="cal">');
  document.write('<tr><td colspan="7" bgcolor="#87CEFA" valign="middle">');
  document.write('<font color="White" face="tahoma, verdana" size="2">Time: </font>');
  
  document.write('<select name="timeH">'
  + '<option value="01"' + (((this.dt_current.getHours()==1)||((this.dt_current.getHours()-12)==1))?" SELECTED":"") + '>1</option>'
  + '<option value="02"' + (((this.dt_current.getHours()==2)||((this.dt_current.getHours()-12)==2))?" SELECTED":"") + '>2</option>'
  + '<option value="03"' + (((this.dt_current.getHours()==3)||((this.dt_current.getHours()-12)==3))?" SELECTED":"") + '>3</option>'
  + '<option value="04"' + (((this.dt_current.getHours()==4)||((this.dt_current.getHours()-12)==4))?" SELECTED":"") + '>4</option>'
  + '<option value="05"' + (((this.dt_current.getHours()==5)||((this.dt_current.getHours()-12)==5))?" SELECTED":"") + '>5</option>'
  + '<option value="06"' + (((this.dt_current.getHours()==6)||((this.dt_current.getHours()-12)==6))?" SELECTED":"") + '>6</option>'
  + '<option value="07"' + (((this.dt_current.getHours()==7)||((this.dt_current.getHours()-12)==7))?" SELECTED":"") + '>7</option>'
  + '<option value="08"' + (((this.dt_current.getHours()==8)||((this.dt_current.getHours()-12)==8))?" SELECTED":"") + '>8</option>'
  + '<option value="09"' + (((this.dt_current.getHours()==9)||((this.dt_current.getHours()-12)==9))?" SELECTED":"") + '>9</option>'
  + '<option value="10"' + (((this.dt_current.getHours()==10)||((this.dt_current.getHours()-12)==10))?" SELECTED":"") + '>10</option>'
  + '<option value="11"' + (((this.dt_current.getHours()==11)||((this.dt_current.getHours()-12)==11))?" SELECTED":"") + '>11</option>'
  + '<option value="00"' + (((this.dt_current.getHours()==12)||((this.dt_current.getHours())==0))?" SELECTED":"") + '>12</option>'
  + '</select>'
  + '<b>:</b>'
  + '<select name="timeM">'
  + '<option value="00"' + ((this.dt_current.getMinutes() == 0)?" SELECTED":"") + '>00</option>'
  + '<option value="01"' + ((this.dt_current.getMinutes() == 1)?" SELECTED":"") + '>01</option>'
  + '<option value="02"' + ((this.dt_current.getMinutes() == 2)?" SELECTED":"") + '>02</option>'
  + '<option value="03"' + ((this.dt_current.getMinutes() == 3)?" SELECTED":"") + '>03</option>'
  + '<option value="04"' + ((this.dt_current.getMinutes() == 4)?" SELECTED":"") + '>04</option>'
  + '<option value="05"' + ((this.dt_current.getMinutes() == 5)?" SELECTED":"") + '>05</option>'
  + '<option value="06"' + ((this.dt_current.getMinutes() == 6)?" SELECTED":"") + '>06</option>'
  + '<option value="07"' + ((this.dt_current.getMinutes() == 7)?" SELECTED":"") + '>07</option>'
  + '<option value="08"' + ((this.dt_current.getMinutes() == 8)?" SELECTED":"") + '>08</option>'
  + '<option value="09"' + ((this.dt_current.getMinutes() == 9)?" SELECTED":"") + '>09</option>'
  + '<option value="10"' + ((this.dt_current.getMinutes() == 10)?" SELECTED":"") + '>10</option>'
  + '<option value="11"' + ((this.dt_current.getMinutes() == 11)?" SELECTED":"") + '>11</option>'
  + '<option value="12"' + ((this.dt_current.getMinutes() == 12)?" SELECTED":"") + '>12</option>'
  + '<option value="13"' + ((this.dt_current.getMinutes() == 13)?" SELECTED":"") + '>13</option>'
  + '<option value="14"' + ((this.dt_current.getMinutes() == 14)?" SELECTED":"") + '>14</option>'
  + '<option value="15"' + ((this.dt_current.getMinutes() == 15)?" SELECTED":"") + '>15</option>'
  + '<option value="16"' + ((this.dt_current.getMinutes() == 16)?" SELECTED":"") + '>16</option>'
  + '<option value="17"' + ((this.dt_current.getMinutes() == 17)?" SELECTED":"") + '>17</option>'
  + '<option value="18"' + ((this.dt_current.getMinutes() == 18)?" SELECTED":"") + '>18</option>'
  + '<option value="19"' + ((this.dt_current.getMinutes() == 19)?" SELECTED":"") + '>19</option>'
  + '<option value="20"' + ((this.dt_current.getMinutes() == 20)?" SELECTED":"") + '>20</option>'
  + '<option value="21"' + ((this.dt_current.getMinutes() == 21)?" SELECTED":"") + '>21</option>'
  + '<option value="22"' + ((this.dt_current.getMinutes() == 22)?" SELECTED":"") + '>22</option>'
  + '<option value="23"' + ((this.dt_current.getMinutes() == 23)?" SELECTED":"") + '>23</option>'
  + '<option value="24"' + ((this.dt_current.getMinutes() == 24)?" SELECTED":"") + '>24</option>'
  + '<option value="25"' + ((this.dt_current.getMinutes() == 25)?" SELECTED":"") + '>25</option>'
  + '<option value="26"' + ((this.dt_current.getMinutes() == 26)?" SELECTED":"") + '>26</option>'
  + '<option value="27"' + ((this.dt_current.getMinutes() == 27)?" SELECTED":"") + '>27</option>'
  + '<option value="28"' + ((this.dt_current.getMinutes() == 28)?" SELECTED":"") + '>28</option>'
  + '<option value="29"' + ((this.dt_current.getMinutes() == 29)?" SELECTED":"") + '>29</option>'
  + '<option value="30"' + ((this.dt_current.getMinutes() == 30)?" SELECTED":"") + '>30</option>'
  + '<option value="31"' + ((this.dt_current.getMinutes() == 31)?" SELECTED":"") + '>31</option>'
  + '<option value="32"' + ((this.dt_current.getMinutes() == 32)?" SELECTED":"") + '>32</option>'
  + '<option value="33"' + ((this.dt_current.getMinutes() == 33)?" SELECTED":"") + '>33</option>'
  + '<option value="34"' + ((this.dt_current.getMinutes() == 34)?" SELECTED":"") + '>34</option>'
  + '<option value="35"' + ((this.dt_current.getMinutes() == 35)?" SELECTED":"") + '>35</option>'
  + '<option value="36"' + ((this.dt_current.getMinutes() == 36)?" SELECTED":"") + '>36</option>'
  + '<option value="37"' + ((this.dt_current.getMinutes() == 37)?" SELECTED":"") + '>37</option>'
  + '<option value="38"' + ((this.dt_current.getMinutes() == 38)?" SELECTED":"") + '>38</option>'
  + '<option value="39"' + ((this.dt_current.getMinutes() == 39)?" SELECTED":"") + '>39</option>'
  + '<option value="40"' + ((this.dt_current.getMinutes() == 40)?" SELECTED":"") + '>40</option>'
  + '<option value="41"' + ((this.dt_current.getMinutes() == 41)?" SELECTED":"") + '>41</option>'
  + '<option value="42"' + ((this.dt_current.getMinutes() == 42)?" SELECTED":"") + '>42</option>'
  + '<option value="43"' + ((this.dt_current.getMinutes() == 43)?" SELECTED":"") + '>43</option>'
  + '<option value="44"' + ((this.dt_current.getMinutes() == 44)?" SELECTED":"") + '>44</option>'
  + '<option value="45"' + ((this.dt_current.getMinutes() == 45)?" SELECTED":"") + '>45</option>'
  + '<option value="46"' + ((this.dt_current.getMinutes() == 46)?" SELECTED":"") + '>46</option>'
  + '<option value="47"' + ((this.dt_current.getMinutes() == 47)?" SELECTED":"") + '>47</option>'
  + '<option value="48"' + ((this.dt_current.getMinutes() == 48)?" SELECTED":"") + '>48</option>'
  + '<option value="49"' + ((this.dt_current.getMinutes() == 49)?" SELECTED":"") + '>49</option>'
  + '<option value="50"' + ((this.dt_current.getMinutes() == 50)?" SELECTED":"") + '>50</option>'
  + '<option value="51"' + ((this.dt_current.getMinutes() == 51)?" SELECTED":"") + '>51</option>'
  + '<option value="52"' + ((this.dt_current.getMinutes() == 52)?" SELECTED":"") + '>52</option>'
  + '<option value="53"' + ((this.dt_current.getMinutes() == 53)?" SELECTED":"") + '>53</option>'
  + '<option value="54"' + ((this.dt_current.getMinutes() == 54)?" SELECTED":"") + '>54</option>'
  + '<option value="55"' + ((this.dt_current.getMinutes() == 55)?" SELECTED":"") + '>55</option>'
  + '<option value="56"' + ((this.dt_current.getMinutes() == 56)?" SELECTED":"") + '>56</option>'
  + '<option value="57"' + ((this.dt_current.getMinutes() == 57)?" SELECTED":"") + '>57</option>'
  + '<option value="58"' + ((this.dt_current.getMinutes() == 58)?" SELECTED":"") + '>58</option>'
  + '<option value="59"' + ((this.dt_current.getMinutes() == 59)?" SELECTED":"") + '>59</option>'
  + '</select>'
  + '<select name="timeAMPM">'
  + '<option value="AM"' +  ((this.dt_current.getHours() < 12)?" SELECTED":"") + '>AM</option>'
  + '<option value="PM"' +  ((this.dt_current.getHours() >= 12)?" SELECTED":"") + '>PM</option>'
  + '</select>'
  + '</td></tr></form>');
  
  //document.write('<input type="text" name="time" value="'+obj_caller.gen_time(this.dt_current)+'" size="8" maxlength="8">');
}
</script>
		</table>
		</td>
	</tr>
</table>
</body>
</html>

