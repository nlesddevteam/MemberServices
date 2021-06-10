/******************************************************************************
* rounded_corner.js                                                           *
*                                                                             *
* Copyright 2004 by Chris Crane.                                              *
*                                                                             *
******************************************************************************/

//*****************************************************************************
// Corner constructor.
//*****************************************************************************

function Corner(path, width, height) {

  this.path = path;
  this.width = width;
  this.height = height;
  
  // Define methods.

  this.getPath = cornerGetPath;
  this.getWidth = cornerGetWidth;
  this.getHeight = cornerGetHeight;
}

//*****************************************************************************
// Scroller methods.
//*****************************************************************************
function cornerGetPath()
{
  return this.path;
}

function cornerGetWidth()
{
  return this.width;
}

function cornerGetHeight()
{
  return this.height;
}