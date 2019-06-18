package com.awsd.travel;

import com.awsd.personnel.*;

import java.util.*;


public class TravelClaims extends AbstractMap
{
  private Map claims;
  private Personnel p;
  
  public TravelClaims(Personnel p) throws TravelClaimException
  {
    this.p = p;
    claims = TravelClaimDB.getClaimsTreeMap(p);
  }

  public Set entrySet()
  {
    return claims.entrySet();
  }

  public Personnel getPersonnel()
  {
    return this.p;
  }
}