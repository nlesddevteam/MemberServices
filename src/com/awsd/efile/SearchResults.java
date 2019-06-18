package com.awsd.efile;

import java.util.*;


public class SearchResults 
{
  private ArrayList pages = null;
  private int doc_cnt;
  
  public SearchResults(ArrayList pages, int doc_cnt)
  {
    this.pages = pages;
    this.doc_cnt = doc_cnt;
  }

  public boolean isEmpty()
  {
    return (pages.size() == 0);
  }

  public Iterator getPage(int page)
  {
    Iterator iter =  null;

    if((page >= 1) && (page <= pages.size()))
    {
      iter = ((Vector) pages.get(page-1)).iterator();
    }

    return iter;
  }

  public int getDocumentCount()
  {
    return doc_cnt;
  }

  public int getPageCount()
  {
    return pages.size();
  }
}