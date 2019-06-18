package com.awsd.efile;

import java.util.*;

public class DocumentTypes  extends AbstractCollection 
{
  private Vector s;
  
  public DocumentTypes() throws DocumentTypeException
  {
    s = (Vector)(DocumentTypeDB.getDocumentTypes()).clone();
  }


  public boolean add(Object o)
  {
    if (o instanceof DocumentType)
    {
      s.add(o);
    }
    else
    {
      throw new ClassCastException("DocumentTypes collections can only contain DocumentType objects.");
    }

    return true;
  }

  public Iterator iterator()
  {
    return s.iterator();
  }

  public int size()
  {
    return s.size();
  }
}