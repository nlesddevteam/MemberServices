package com.awsd.security.crypto;

import com.awsd.common.*;

import javax.crypto.*;
import javax.crypto.spec.*;


public class PasswordEncryption 
{
  public static String encrypt(String pwd)
  {
    KeyGenerator keygen = null;
    SecretKey key = null;
    Cipher cipher = null;
    byte[] key_material = null;
    byte[] ciphertext = null;
    byte[] encrypted_pwd = null;
    String encrypted_pwd_hex = "";
    
    try
    {
      keygen = KeyGenerator.getInstance("DES");
      key = keygen.generateKey();
      key_material = key.getEncoded();
      
      
      cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
      
      cipher.init(Cipher.ENCRYPT_MODE, key);
      
      ciphertext = cipher.doFinal(pwd.getBytes());
      
      encrypted_pwd = new byte[key_material.length + ciphertext.length];
      System.arraycopy(key_material, 0, encrypted_pwd, 0, key_material.length);
      System.arraycopy(ciphertext, 0, encrypted_pwd, key_material.length, ciphertext.length);
      
      encrypted_pwd_hex = Hex.toHex(encrypted_pwd);
    }
    catch(Exception e)
    {
      e.printStackTrace(System.err);
      encrypted_pwd_hex = "";
    }
    
    return encrypted_pwd_hex;
  }
  
  public static String decrypt(String encrypted_hex_pwd)
  {
    SecretKeyFactory factory = null;
    SecretKeySpec keyspec = null;
    SecretKey key = null;
    Cipher cipher = null;
    byte[] key_material = null;
    byte[] ciphertext = null;
    byte[] encrypted_pwd = null;
    byte[] cleartext = null;
    
    try
    {
      encrypted_pwd = Hex.toByteArr(encrypted_hex_pwd);
      
      key_material = new byte[8];
      ciphertext = new byte[encrypted_pwd.length-8];
      
      System.arraycopy(encrypted_pwd, 0, key_material, 0, 8);
      System.arraycopy(encrypted_pwd, 8, ciphertext, 0, ciphertext.length);
      
      factory = SecretKeyFactory.getInstance("DES");
      
      key = factory.generateSecret(new DESKeySpec(key_material));
      
      cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
      
      cipher.init(Cipher.DECRYPT_MODE, key);
      
      cleartext = cipher.doFinal(ciphertext);
    }
    catch(Exception e)
    {
      e.printStackTrace(System.err);
      cleartext = null;
    }
    
    return new String(cleartext);
  }
}