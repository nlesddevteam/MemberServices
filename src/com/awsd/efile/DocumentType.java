package com.awsd.efile;

public class DocumentType 
{
  private int id;
  private String name;
  
  public DocumentType(String name)
  {
    this.name = name;
    id = -1;
  }

  public DocumentType(int id, String name)
  {
    this(name);
    this.id = id;
  }

  public int getDocumentTypeID()
  {
    return this.id;
  }

  public String getDocumentTypeName()
  {
    return this.name;
  }
}