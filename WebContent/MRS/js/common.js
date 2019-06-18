function resizeIFrame(fid, mh)
{
	var fr = parent.document.getElementById(fid);
	fr.height = 0;
	fr.height = (((+document.body.scrollHeight)>(+mh))?(+document.body.scrollHeight):(+mh)) + (+25);
	fr.width = (+542);
}

function resizeIFrame2(fid, mh, eh)
{
	var fr = parent.document.getElementById(fid);
	fr.height = 0;
	fr.height = (((+document.body.scrollHeight)>(+mh))?(+document.body.scrollHeight):(+mh)) + (+eh);
	fr.width = (+542);
}

function process_message(id, str)
{
  var msg = document.getElementById(id);
  if(msg)
  {
    msg.innerHTML = "<table width='100%' cellspacing='0' cellpadding='0'><tr><td width='22' valign='middle'><img src='images/hour_glass_ani.gif'><br></td><td width='*' align='left' valign='middle' style='padding-left:3px;'>" + str + "</td></tr></table>";
  }
}

function button_info_msg(info)
{
  var msg = document.getElementById('button_info');
  if(msg)
  {
    msg.innerHTML = "<span style='padding-top:5px;'>" + info + "</span>";
  }
}

function toggleTableRowHighlight(target, color)
{
  var rowSelected = document.getElementById(target);
  
  rowSelected.style.backgroundColor=color; 
}

function loaded(){
	document.getElementById('preloaded').style.display='none';
	document.getElementById('loaded').style.display='inline';
}
