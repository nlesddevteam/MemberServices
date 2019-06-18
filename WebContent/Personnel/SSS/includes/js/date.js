d = new Array(
"Sunday",
"Monday",
"Tuesday",
"Wednesday",
"Thursday",
"Friday",
"Saturday"
);
m = new Array(
"January",
"February",
"March",
"April",
"May",
"June",
"July",
"August",
"September",
"October",
"November",
"December"
);

today = new Date();
day = today.getDate();
year = today.getYear();

if (year < 2000)    
year = year + 1900; 

end = "";
if (day==1 || day==21 || day==31) end="";
if (day==2 || day==22) end="";
if (day==3 || day==23) end="";
day+=end;

document.write("Today is  ");
document.write(d[today.getDay()]+", "+m[today.getMonth()]+" ");
document.write(day+", " + year);
