package com.awsd.security.addressbook;

import com.awsd.security.*;
import com.awsd.security.SecurityException;

import java.util.*;


public class AddressBook extends AbstractCollection 
{
  private Vector addresses;
  
  public AddressBook() throws SecurityException
  {
    addresses = (Vector)(AddressBookDB.getAddressBook()).clone();
  }

  public AddressBook(String searchtxt) throws SecurityException
  {
    addresses = (Vector)(AddressBookDB.getAddressBook(searchtxt)).clone();
  }

  public boolean add(Object o)
  {
    if (o instanceof AddressBook)
    {
      addresses.add(o);
    }
    else
    {
      throw new ClassCastException("AddressBook collections can only contain UserAddress objects.");
    }

    return true;
  }

  public Iterator iterator()
  {
    return addresses.iterator();
  }

  public int size()
  {
    return addresses.size();
  }
}