/******************************************************************************
* scroller.js                                                                 *
*                                                                             *
* Copyright 1999 by Mike Hall.                                                *
* Visit http://www.dynamicdrive.com                                           *
* Last update: July 21st, '02 by Dynamic Drive for NS6 functionality.         *
*                                                                             *
* Allows you to create scrolling displays on a page. Multiple scrollers can   *
* be defined, each with it's own parameters and list of items. Item text can  *
* include basic HTML tags, including links and images.                        *
* Note: requires dhtmllib.js.                                                 *
******************************************************************************/

//*****************************************************************************
// Scroller constructor.
//*****************************************************************************

function Scroller(x, y, width, height, border, padding, rounded) {

  this.x = x;
  this.y = y;
  this.width = width;
  this.height = height;
  this.border = border;
  this.padding = padding;
  this.rounded = rounded;

  this.items = new Array();
  this.corners = new Array();
  
  this.created = false;
  this.reverse = false;

  // Set default colors.

  this.fgColor = "#000000";
  this.bgColor = "#ffffff";
  this.bdColor = "#000000";

  // Set default font.

  this.fontFace = "Arial,Helvetica";
  this.fontSize = "2";

  // Set default scroll timing values.

  this.speed = 50;
  this.pauseTime = 2000;

  // Define methods.

  this.setColors = scrollerSetColors;
  this.setFont = scrollerSetFont;
  this.setSpeed = scrollerSetSpeed;
  this.setPause = scrollersetPause;
  this.addItem = scrollerAddItem;
  this.create = scrollerCreate;
  this.show = scrollerShow;
  this.hide = scrollerHide;
  this.moveTo = scrollerMoveTo;
  this.moveBy = scrollerMoveBy;
  this.getzIndex = scrollerGetzIndex;
  this.setzIndex = scrollerSetzIndex;
  this.stop = scrollerStop;
  this.start = scrollerStart;
  this.restart = scrollerRestart;
  this.end = scrollerEnd;
  this.reverse = scrollerReverse;
  this.setCornerImages = scrollerSetCornerImages;
}

//*****************************************************************************
// Scroller methods.
//*****************************************************************************

function scrollerSetColors(fgcolor, bgcolor, bdcolor) {

  if (this.created) {
    alert("Scroller Error: Scroller has already been created.");
    return;
  }
  this.fgColor = fgcolor;
  this.bgColor = bgcolor;
  this.bdColor = bdcolor;
}

function scrollerSetFont(face, size) {

  if (this.created) {
    alert("Scroller Error: Scroller has already been created.");
    return;
  }
  this.fontFace = face;
  this.fontSize = size;
}

function scrollerSetSpeed(pps) {

  if (this.created) {
    alert("Scroller Error: Scroller has already been created.");
    return;
  }
  this.speed = pps;
}

function scrollersetPause(ms) {

  if (this.created) {
    alert("Scroller Error: Scroller has already been created.");
    return;
  }
  this.pauseTime = ms;
}

function scrollerAddItem(str) {

  if (this.created) {
    alert("Scroller Error: Scroller has already been created.");
    return;
  }
  this.items[this.items.length] = str;
}

function scrollerSetCornerImages(images)
{
   if (this.created) {
    alert("Scroller Error: Scroller has already been created.");
    return;
  }

  if(images.length < 4)
  {
    alert("4 corner graphics required.");
    this.rounded = false;
    return;
  }
  for(var i=0; i < images.length; i++)
  {
    this.corners[this.corners.length] = images[i];
  }

  return;
}

function scrollerCreate() {

  var start, end;
  var str;
  var i, j;
  var x, y;

  if (!isMinNS4 && !ie && !dom)
    return;

  // On first scroller, start interval timer.

  if (scrollerList.length == 0)
    setInterval('scrollerGo()', scrollerInterval);

  // Create the scroller only once.

  if (this.created) {
    alert("Scroller Error: Scroller has already been created.");
    return;
  }
  this.created = true;

  // Copy first item to the end of the list, this lets us scroll from the last
  // defined item to the first without jumping.

  this.items[this.items.length] = this.items[0];

  // Set up HTML code for item text.

  start = '<table border=0'
        + ' cellpadding=' + (this.padding + this.border)
        + ' cellspacing=0'
        + ' width=' + this.width
        + ' height=' + this.height + '>'
        + '<tr><td>'
        + '<font'
        + ' color="' + this.fgColor + '"'
        + ' face="' + this.fontFace + '"'
        + ' size=' + this.fontSize + '>';
  end   = '</font></td></tr></table>';

  // Build the layers.

  if (isMinNS4) {
  /***
    NEED TO ADD ROUNDED CORNER CAPABILITY FOR NS
   ***/
    this.baseLayer = new Layer(this.width);
    this.scrollLayer = new Layer(this.width, this.baseLayer);
    this.scrollLayer.visibility = "inherit";
    this.itemLayers = new Array();
    for (i = 0; i < this.items.length; i++) {
      this.itemLayers[i] = new Layer(this.width, this.scrollLayer);
      this.itemLayers[i].document.open();
      this.itemLayers[i].document.writeln(start + this.items[i] + end);
      this.itemLayers[i].document.close();
      this.itemLayers[i].visibility = "inherit";
    }

    // Set background colors.

    setBgColor(this.baseLayer, this.bdColor);
    setBgColor(this.scrollLayer, this.bgColor);
  }

  if (ie||dom) {
    i = scrollerList.length;
    str = '<div id="scroller' + i + '_baseLayer"'
        + ' style="position:absolute;'
        + ' background-color:' + this.bdColor + ';'
        + ' width:' + this.width + 'px;'
        + ' height:' + this.height + 'px;'
        + ' overflow:hidden;'
        + ' visibility:hidden;">\n';

    if(this.rounded)
    {
      str += '<div id="scroller' + i + '_cornerLayerTL"'
          + ' style="position:absolute;'
          + ' width:' + this.corners[0].getWidth() + 'px;'
          + ' height:' + this.corners[0].getHeight() + 'px;'
          + ' background:url(' + this.corners[0].getPath() + '); no-repeat top left fixed'
          + ' visibility:inherit;"></div>\n';

      str += '<div id="scroller' + i + '_cornerLayerTR"'
          + ' style="position:absolute;'
          + ' width:' + this.corners[1].getWidth() + 'px;'
          + ' height:' + this.corners[1].getHeight() + 'px;'
          + ' background:url(' + this.corners[1].getPath() + '); no-repeat top right fixed'
          + ' visibility:inherit;"></div>\n';
    }
    
    str += '<div id="scroller' + i + '_scrollLayer"'
        + ' style="position:absolute;'
        + ' background-color: ' + this.bgColor + ';'
        + ' width:' + this.width + 'px;'
        + ' height:' + (this.height * this.items.length) + 'px;'
        + ' visibility:inherit;">\n';
    for (j = 0; j < this.items.length; j++) {
      str += '<div id="scroller' + i + '_itemLayers' + j + '"'
          +  ' style="position:absolute;'
          +  ' width:' + this.width + 'px;'
          +  ' height:' + this.height + 'px;'
          +  ' visibility:inherit;">\n'
          +  start + this.items[j] + end
          +  '</div>\n';
    }
    str += '</div>\n';
    if(this.rounded)
    {
      str += '<div id="scroller' + i + '_cornerLayerBL"'
          + ' style="position:absolute;'
          + ' width:' + this.corners[2].getWidth() + 'px;'
          + ' height:' + this.corners[2].getHeight() + 'px;'
          + ' background:url(' + this.corners[2].getPath() + '); no-repeat bottom left fixed'
          + ' visibility:inherit;"></div>\n';

      str += '<div id="scroller' + i + '_cornerLayerBR"'
          + ' style="position:absolute;'
          + ' width:' + this.corners[3].getWidth() + 'px;'
          + ' height:' + this.corners[3].getHeight() + 'px;'
          + ' background:url(' + this.corners[3].getPath() + '); no-repeat bottom right fixed'
          + ' visibility:inherit;"></div>\n';
    }
    str += '</div>\n';

    // Insert HTML code at end of page. For IE4, need to scroll window to
    // end of page, insert and scroll back to correct bug.

    if (!(ie&&window.print)) {
      x = getPageScrollX();
      y = getPageScrollY();
      window.scrollTo(getPageWidth(), getPageHeight());
    }
    if (ie)
    document.all.tempholder.innerHTML=str
    else if (dom)
    document.getElementById("tempholder").innerHTML=str
//EDIT HERE//////////////////////////////////////////////////////////////////////////////
    if (!(ie&&window.print))
      window.scrollTo(x, y);

    // Get handles to each layer.

    this.baseLayer = getLayer("scroller" + i + "_baseLayer");
    if(this.rounded)
    {
      this.cornerLayerTL = getLayer("scroller" + i + "_cornerLayerTL");
      this.cornerLayerTR = getLayer("scroller" + i + "_cornerLayerTR");
      this.cornerLayerBL = getLayer("scroller" + i + "_cornerLayerBL");
      this.cornerLayerBR = getLayer("scroller" + i + "_cornerLayerBR");
    }
    this.scrollLayer = getLayer("scroller" + i + "_scrollLayer");
    this.itemLayers = new Array();
    for (j = 0; j < this.items.length; j++)
      this.itemLayers[j] = getLayer("scroller" + i + "_itemLayers" + j);
  }

  // Position and clip base, scroll layers and rounded corners if necessary.

  moveLayerTo(this.baseLayer, this.x, this.y);
  clipLayer(this.baseLayer, 0, 0, this.width, this.height);
  if(this.rounded)
  {
    moveLayerTo(this.cornerLayerTL, 0, 0);
    clipLayer(this.cornerLayerTL, 0, 0, this.corners[0].getWidth(), this.corners[0].getHeight());

    moveLayerTo(this.cornerLayerTR, this.width - this.corners[1].getWidth(), 0);
    clipLayer(this.cornerLayerTR, 0, 0, this.corners[1].getWidth(), this.corners[1].getHeight());

    moveLayerTo(this.cornerLayerBL, 0, this.height - this.corners[2].getHeight());
    clipLayer(this.cornerLayerBL, 0, 0, this.corners[2].getWidth(), this.corners[2].getHeight());

    moveLayerTo(this.cornerLayerBR, this.width - this.corners[3].getWidth(), this.height - this.corners[3].getHeight());
    clipLayer(this.cornerLayerBR, 0, 0, this.corners[3].getWidth(), this.corners[3].getHeight());
  }
  moveLayerTo(this.scrollLayer, this.border, this.border);
  clipLayer(this.scrollLayer, 0, 0,
            this.width - 2 * this.border, this.height - 2 * this.border);

  // Position and clip each item layer.

  x = 0;
  y = 0;
  for (i = 0; i < this.items.length; i++) {
    moveLayerTo(this.itemLayers[i], x, y);
    clipLayer(this.itemLayers[i], 0, 0, this.width, this.height);
    y += this.height;
  }

  // Set up scrolling parameters.

  this.stopped = false;
  this.reversed = false;
  this.currentY = 0;
  this.stepY = this.speed / (1000 / scrollerInterval);
  this.stepY = Math.min(this.height, this.stepY);
  this.nextY = this.height;
  this.maxY = this.height * (this.items.length - 1);
  this.paused = true;
  this.counter = 0;

  // Add to global list.

  scrollerList[scrollerList.length] = this;

  // Display it.

  showLayer(this.baseLayer);
}

function scrollerShow() {

  if (this.created)

    showLayer(this.baseLayer);
}

function scrollerHide() {

  if (this.created)
    hideLayer(this.baseLayer);
}

function scrollerMoveTo(x, y) {

  if (this.created)
    moveLayerTo(this.baseLayer, x, y);
}

function scrollerMoveBy(dx, dy) {

  if (this.created)
    moveLayerBy(this.baseLayer, dx, dy);
}

function scrollerGetzIndex() {

  if (this.created)
    return(getzIndex(this.baseLayer));
  else
    return(0);
}

function scrollerSetzIndex(z) {

  if (this.created)
  {
    setzIndex(this.baseLayer, z);
    if(this.rounded)
    {
      setzIndex(this.cornerLayerTL, z+1);
      setzIndex(this.cornerLayerTR, z+1);
      setzIndex(this.cornerLayerBL, z+1);
      setzIndex(this.cornerLayerBR, z+1);
    }
  }
}

function scrollerStart() {

  this.stopped = false;
  this.reversed = false;
}

function scrollerStop() {

  this.stopped = true;
}

function  scrollerRestart()
{
  this.stopped = true;
  this.currentY = 0;
  this.paused = false;
  this.counter = 0;
  this.nextY = this.height;
  scrollLayerTo(this.scrollLayer, 0, Math.round(this.currentY), false);
}

function scrollerEnd()
{
  this.stopped = true;
  this.currentY = this.maxY - this.height;
  this.nextY = this.maxY;
  this.paused = false;
  this.counter = 0;
  scrollLayerTo(this.scrollLayer, 0, Math.round(this.currentY), false);
}

function scrollerReverse()
{
  if(this.reversed)
    return;
  
  this.reversed = true;
  this.stopped = false;
}

//*****************************************************************************
// Code for scrolling.
//*****************************************************************************

// An array is used to hold a pointer to each scroller that is defined. The
// scrollerGo() function runs at regular intervals and updates each scroller
// in this list.

var scrollerList     = new Array();
var scrollerInterval = 20;

function scrollerGo() {

  var i;

  // Update each scroller object in the list.

  for (i = 0; i < scrollerList.length; i++) {

    // If stopped, skip.

    if (scrollerList[i].stopped);

    // If paused, update counter.

    else if (scrollerList[i].paused) {
      scrollerList[i].counter += scrollerInterval;
      if (scrollerList[i].counter > scrollerList[i].pauseTime)
        scrollerList[i].paused = false;
    }

    // Scroll it.

    else {
      if(!scrollerList[i].reversed)
      {
        scrollerList[i].currentY += scrollerList[i].stepY;

        // Pause it if the next item has scrolled into view.
        
        if (scrollerList[i].currentY == scrollerList[i].nextY) {
          scrollerList[i].paused = true;
          scrollerList[i].counter = 0;
          scrollerList[i].currentY = scrollerList[i].nextY;
          scrollerList[i].nextY += scrollerList[i].height;
        }

        // When we reach the end, start over.

        if (scrollerList[i].currentY >= scrollerList[i].maxY) {
          scrollerList[i].currentY -= scrollerList[i].maxY;
          scrollerList[i].nextY = scrollerList[i].height;
        }
      }
      else
      {
        scrollerList[i].currentY -= scrollerList[i].stepY;

        // Pause it if the next item has scrolled into view.

        if (scrollerList[i].currentY == scrollerList[i].nextY) {
          scrollerList[i].paused = true;
          scrollerList[i].counter = 0;
          scrollerList[i].currentY = scrollerList[i].nextY;
          scrollerList[i].nextY -= scrollerList[i].height;
        }

        // When we reach the beginning, start over.

        if (scrollerList[i].currentY <= 0) {
          scrollerList[i].currentY += scrollerList[i].maxY;
          scrollerList[i].nextY = scrollerList[i].maxY-scrollerList[i].height;
        }
      }

      
      scrollLayerTo(scrollerList[i].scrollLayer,
                    0, Math.round(scrollerList[i].currentY),
                    false);
    }
  }
}

//*****************************************************************************
// Code to handle a window resize.
//*****************************************************************************

// These variables are used to determine if a resize event is a true one.
// Necessary due to a bug in older NS4 releases.

var origWidth;
var origHeight;

// Fix for resize bug.

if (isMinNS4) {
  origWidth  = window.innerWidth;
  origHeight = window.innerHeight;
}
window.onresize = scrollerReload;

function scrollerReload() {

  // Reload page in case of a browser resize. First make sure it's a true
  // resize.

  if (isMinNS4 && origWidth == window.innerWidth && origHeight == window.innerHeight)
    return;
  window.location.href = window.location.href;
}
