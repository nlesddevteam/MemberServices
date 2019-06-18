package com.awsd.efile;

import java.io.*;


public class EFileFilenameFilter implements FilenameFilter
{
  private int id;
  
  public EFileFilenameFilter(int id)
  {
    this.id = id;
  }
  
  public boolean accept(File dir, String name)
  {
    return name.startsWith(id + ".");
  }
}