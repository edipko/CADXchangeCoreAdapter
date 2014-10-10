package com.spotonresponse.xchangecore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class EncryptDecrypt
{
  static Logger logger = Logger.getLogger(EncryptDecrypt.class);

  public String decryptPass(String encPass)
  {
    String secret = new String("10293azxsDC57483");
    String iv = new String("alskdj92846oqmnz");
    ByteArrayOutputStream fos = new ByteArrayOutputStream();

    int i = 0;
    String enc64 = encPass;
    byte[] enc64bytes = enc64.getBytes();
    byte[] dec64bytes = Base64.decodeBase64(enc64bytes);

    String dec64 = new String();
    for (i = 0; i < dec64bytes.length; i++)
      dec64 = dec64 + (char)dec64bytes[i];
    try
    {
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), "AES");
      IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

      cipher.init(2, keySpec, ivSpec);
      ByteArrayInputStream fis = new ByteArrayInputStream(dec64bytes);
      CipherInputStream cis = new CipherInputStream(fis, cipher);

      byte[] b = new byte[8];
      while ((i = cis.read(b)) != -1) {
        fos.write(b, 0, i);
      }
      fos.flush();
      fos.close();
      cis.close();
      fis.close();
    }
    catch (InvalidKeyException e)
    {
      logger.error(e);
    }
    catch (InvalidAlgorithmParameterException e) {
      logger.error(e);
    }
    catch (NoSuchAlgorithmException e) {
      logger.error(e);
    }
    catch (NoSuchPaddingException e) {
      logger.error(e);
    }
    catch (IOException e) {
      logger.error(e);
    }

    logger.debug("Password is: " + fos.toString().trim());
    return fos.toString().trim();
  }
}