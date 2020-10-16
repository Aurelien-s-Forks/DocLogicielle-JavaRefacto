import main.java.com.helpers.SystemHelpers;

import java.io.*;
import java.util.*;

/**
 * ******************************************************************
 * class: The Record class' purpose is to read and write records to
 * a randomaccess file.
 */
public class Record {
   private int recID;
   private int quantity;
   private String toolType = "";
   private String brandName = "";
   private String toolDesc  = "";
   private String partNum = "";
   private String cost = "";
   private String[] recordTokens;
   private final boolean myDebug = false;
   private long filePos;
   private long fileLen;

    /**
     * ******************************************************************
     * Method: ReadRec() Reads a record from the specified RandomAccessFile.
     * 1- Read the first integer
     * 2- Read the second integer
     * 3- Read characters one at a time until we reach a string of
     * ';;;'. This indicates that we have reached the end of the
     * character string for this particular record.
     * 4- Load the resulting string into a StringTokenizer object.
     * 5- We are looking for 7 tokens, so if the token count is
     * greater than 4, we will tokenize the string.
     * 6- The tokens are loaded into a string array and then into the
     * class variables.
     *
     * @param file the file
     * @throws IOException the io exception
     */
    public void ReadRec(RandomAccessFile file) throws IOException {
      char ch;
      StringTokenizer tokens;
      String str = "", str2 = "";
      int ii = 0 , loopCtl = 585 , len = 0;
      long rem = fileLen - filePos;

      SystemHelpers.sysPrint(myDebug,"ReadRec() 1a: Remaining bytes is " + rem);
      SystemHelpers.sysPrint(myDebug,"ReadRec() 1b: Reading ints");

      recID    = file.readInt();
      SystemHelpers.sysPrint(myDebug,"ReadRec() 1c: recID  is " +  recID);

      quantity = file.readInt();
      SystemHelpers.sysPrint(myDebug,"ReadRec() 2: Reading string");

      /** Read characters until we get to;;; which
       *  indicates the end of the record */
      while (ii < loopCtl) {
         ch =  file.readChar();
         str = str + ch;
         len = str.length();

         if(ii > 4) {
            str2 = str.substring(len - 4 , len - 1);
            if(str2.equals(";;;")) {
                break;
            }
         }
         ii++;
      }

      SystemHelpers.sysPrint(myDebug,"ReadRec() 3a: str is " + str);
      SystemHelpers.sysPrint(myDebug,"ReadRec() 3b: Reading string. ii =s " + ii);
      sysPrint( "ReadRec() 4: The value of str is " + str);
      tokens = new StringTokenizer(str , ";;");

      recordTokens = new String[7];

      if(tokens.countTokens() >= 4) {
         sysPrint( "ReadRec() 5: The number of tokens is " + tokens.countTokens());
         ii = 2;

         /** Load the tokens into a string array. */
         while(ii < 7) {
            recordTokens[ii] = tokens.nextToken();
            ii++;
         }

         toolType  = recordTokens[2];
         brandName = recordTokens[3];
         partNum   = recordTokens[4];
         cost      = recordTokens[5];
         toolDesc  = recordTokens[6];
      } else {
         sysPrint( "ReadRec() 6: There are no records to read.");
      }
   }

    /**
     * *******************************************************
     * The fill() method is used to fill in the passed string with
     * blanks.
     *
     * @param str the str
     * @param buf the buf
     * @param len the len
     * @return the string buffer
     */
    public StringBuffer fill(String str, StringBuffer buf , int len) {
       String strTwo = "                     " + "                                             ";

       if(str != null) {
          buf.setLength(len);
          buf = new StringBuffer(str + strTwo);
       } else {
          buf = new StringBuffer(strTwo);
       }

       if(len == 0) {
          buf.setLength(45);
       } else {
          buf.setLength(len);
       }
      return buf;
   }

    /**
     * ***********************************************************
     * write() Writes a record to the specified RandomAccessFile.
     * 1- First it writes a int (recid) to the output file
     * 2- Next it writes the quantity as an int.
     * 3- Then it writes the remaing record as a string.
     *
     * @param file the file
     * @throws IOException the io exception
     */
    public void write(RandomAccessFile file) throws IOException {
      StringBuffer buf  = new StringBuffer(" ");
      String str = "" , str2 = "";

      file.writeInt(recID);
      file.writeInt(quantity);

      str = str + toolType + ";;";
      str = str + brandName + ";;";
      str = str + partNum + ";;";
      str = str + cost  + ";;";
      str = str + toolDesc + ";;;";

      buf = fill (str , buf.delete(0, 451), 451);

      file.writeChars(buf.toString());
      sysPrint( "write(): - The value of recID is " + recID);
      sysPrint( "write(): - The value of quantity is " + quantity);
      sysPrint( "write(): - The value of str2 is " + str + " with a length of " + str2.length());
      sysPrint( "write(): - The length of buf is " + buf.length());
   }


    /**
     * *******************************************************
     * Method: writeInteger() is used to write an integer to the
     * randomaccess file.
     *
     * @param file the file
     * @param a    the a
     * @throws IOException the io exception
     */
    public void writeInteger(RandomAccessFile file , int a) throws IOException {
      file.writeInt(a);
   }

    /**
     * Gets rec id.
     *
     * @return the rec id
     */
    public int getRecID() { return recID; }

    /**
     * Gets tool type.
     *
     * @return the tool type
     */
    public String getToolType() { return toolType.trim(); }

    /**
     * Gets tool desc.
     *
     * @return the tool desc
     */
    public String getToolDesc() { return toolDesc.trim(); }

    /**
     * Gets part number.
     *
     * @return the part number
     */
    public String getPartNumber() { return partNum.trim(); }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() { return quantity; }

    /**
     * Gets brand name.
     *
     * @return the brand name
     */
    public String getBrandName() { return brandName.trim(); }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public String getCost() { return cost.trim(); }

    /**
     * Sets tool type.
     *
     * @param f the f
     */
    public void setToolType(String f) { toolType = f; }

    /**
     * Sets rec id.
     *
     * @param p the p
     */
    public void setRecID(int p) { recID = p; }

    /**
     * Sets cost.
     *
     * @param f the f
     */
    public void setCost(String f) { cost = f; }

    /**
     * Sets brand name.
     *
     * @param f the f
     */
    public void setBrandName(String f) { brandName = f; }

    /**
     * Sets tool desc.
     *
     * @param f the f
     */
    public void setToolDesc(String f) { toolDesc = f; }

    /**
     * Sets part number.
     *
     * @param f the f
     */
    public void setPartNumber(String f) { partNum = f; }

    /**
     * Sets quantity.
     *
     * @param q the q
     */
    public void setQuantity(int q) { quantity = q; }

    /**
     * Sets file pos.
     *
     * @param fp the fp
     */
    public void setFilePos(long fp) { filePos = fp; }

    /**
     * Sets file len.
     *
     * @param fl the fl
     */
    public void setFileLen(long fl) { fileLen = fl; }

    /**
     * Sys print.
     *
     * @param str the str
     */
    public void sysPrint(String str ) {
      if(myDebug) {
         System.out.println(str);
      }
   }

    /**
     * Gets size.
     *
     * @return the size
     */
    public static int getSize() {
      return 582;
   }
}
