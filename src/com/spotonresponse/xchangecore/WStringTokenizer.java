package com.spotonresponse.xchangecore;

import java.util.NoSuchElementException;

public class WStringTokenizer
{
  private String tbt;
  private String d;
  private int startpos = 0;

  public WStringTokenizer(String str, String delim)
  {
    this.tbt = new String(str);
    this.d = new String(delim);
  }

  public int countTokens()
  {
    int tokens = 0;
    int temp = this.startpos;
    try
    {
      while (true)
      {
        nextToken();
        tokens++;
      }
    }
    catch (NoSuchElementException localNoSuchElementException) {
      this.startpos = temp;
    }return tokens;
  }

  public boolean hasMoreElements() {
    return hasMoreTokens();
  }

  public boolean hasMoreTokens() {
    if (countTokens() > 0) return true;
    return false;
  }

  public Object nextElement() {
    return this.d;
  }

  public String nextToken() throws NoSuchElementException {
    int result = 0;

    if (this.startpos > this.tbt.length()) throw new NoSuchElementException();
    result = this.tbt.indexOf(this.d, this.startpos);
    if (result < 0) result = this.tbt.length();
    String s = new String(this.tbt.substring(this.startpos, result));
    this.startpos = (result + this.d.length());
    return s;
  }

  public String nextToken(String delim) throws NoSuchElementException {
    this.d = delim;
    return nextToken();
  }
}