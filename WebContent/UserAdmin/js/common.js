function resizeIFrame(fid, mh)
{
	var fr = parent.document.getElementById(fid);
	fr.height = 0;
	fr.height = (((+document.body.scrollHeight)>(+mh))?(+document.body.scrollHeight):(+mh));
	//fr.height = (+document.body.scrollHeight);
	//fr.width = (+document.body.scrollWidth);
}