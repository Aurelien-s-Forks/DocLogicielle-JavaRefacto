import main.java.com.helpers.SystemHelpers;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

/**
 * The Record class' purpose is to read and write records to a randomaccess file.
 */
public class Record {
    private final boolean myDebug = false;
    private int recID;
    private int quantity;
    private String toolType = "";
    private String brandName = "";
    private String toolDesc = "";
    private String partNum = "";
    private String cost = "";
    private long filePos;
    private long fileLen;

    public static int getSize() {
        return 582;
    }

    public void ReadRec(RandomAccessFile file) throws IOException {
        char ch;
        StringTokenizer tokens;
        StringBuilder str = new StringBuilder();
        String str2;
        int i = 0, loopCtl = 585, len;
        long rem = fileLen - filePos;

        SystemHelpers.sysPrint(myDebug, "ReadRec() 1a: Remaining bytes is " + rem);
        SystemHelpers.sysPrint(myDebug, "ReadRec() 1b: Reading ints");

        recID = file.readInt();
        SystemHelpers.sysPrint(myDebug, "ReadRec() 1c: recID  is " + recID);

        quantity = file.readInt();
        SystemHelpers.sysPrint(myDebug, "ReadRec() 2: Reading string");

        while (i < loopCtl) {
            ch = file.readChar();
            str.append(ch);
            len = str.length();

            if (i > 4) {
                str2 = str.substring(len - 4, len - 1);
                if (str2.equals(";;;"))
                    break;
            }
            i++;
        }

        SystemHelpers.sysPrint(myDebug, "ReadRec() 3a: str is " + str);
        SystemHelpers.sysPrint(myDebug, "ReadRec() 3b: Reading string. ii =s " + i);
        sysPrint("ReadRec() 4: The value of str is " + str);
        tokens = new StringTokenizer(str.toString(), ";");

        String[] recordTokens = new String[7];

        if (tokens.countTokens() >= 4) {
            sysPrint("ReadRec() 5: The number of tokens is " + tokens.countTokens());
            i = 2;

            while (i < 7) {
                recordTokens[i] = tokens.nextToken();
                i++;
            }

            toolType = recordTokens[2];
            brandName = recordTokens[3];
            partNum = recordTokens[4];
            cost = recordTokens[5];
            toolDesc = recordTokens[6];
        } else {
            sysPrint("ReadRec() 6: There are no records to read.");
        }
    }

    /**
     * Fill in the passed string with blanks.
     */
    public StringBuffer fill(String str, StringBuffer buf, int len) {
        String strTwo = "                     " + "                                             ";

        if (str != null) {
            buf.setLength(len);
            buf = new StringBuffer(str + strTwo);
        } else {
            buf = new StringBuffer(strTwo);
        }

        if (len == 0) {
            buf.setLength(45);
        } else {
            buf.setLength(len);
        }
        return buf;
    }

    public void write(RandomAccessFile file) throws IOException {
        StringBuffer buf = new StringBuffer(" ");
        String str = "", str2 = "";

        file.writeInt(recID);
        file.writeInt(quantity);

        str = str + toolType + ";;";
        str = str + brandName + ";;";
        str = str + partNum + ";;";
        str = str + cost + ";;";
        str = str + toolDesc + ";;;";

        buf = fill(str, buf.delete(0, 451), 451);

        file.writeChars(buf.toString());
        sysPrint("write(): - The value of recID is " + recID);
        sysPrint("write(): - The value of quantity is " + quantity);
        sysPrint("write(): - The value of str2 is " + str + " with a length of " + str2.length());
        sysPrint("write(): - The length of buf is " + buf.length());
    }

    public void writeInteger(RandomAccessFile file, int a) throws IOException {
        file.writeInt(a);
    }

    public int getRecID() {
        return recID;
    }

    public void setRecID(int p) {
        recID = p;
    }

    public String getToolType() {
        return toolType.trim();
    }

    public void setToolType(String f) {
        toolType = f;
    }

    public String getToolDesc() {
        return toolDesc.trim();
    }

    public void setToolDesc(String f) {
        toolDesc = f;
    }

    public String getPartNumber() {
        return partNum.trim();
    }

    public void setPartNumber(String f) {
        partNum = f;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int q) {
        quantity = q;
    }

    public String getBrandName() {
        return brandName.trim();
    }

    public void setBrandName(String f) {
        brandName = f;
    }

    public String getCost() {
        return cost.trim();
    }

    public void setCost(String f) {
        cost = f;
    }

    public void setFilePos(long fp) {
        filePos = fp;
    }

    public void setFileLen(long fl) {
        fileLen = fl;
    }

    public void sysPrint(String str) {
        if (myDebug) System.out.println(str);
    }
}
