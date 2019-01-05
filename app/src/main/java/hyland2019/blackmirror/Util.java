package hyland2019.blackmirror;

public class Util {

    public static byte[] hex2Byte(String str) { byte[] bytes = new byte[str.length() / 2]; for (int i = 0; i < bytes.length; i++) { bytes[i] = (byte) Integer .parseInt(str.substring(2 * i, 2 * i + 2), 16); } return bytes; }
    public static String byte2hex(byte[] b) { String hs = ""; String stmp = ""; for (int n = 0; n < b.length; n++) { stmp = (java.lang.Integer.toHexString(b[n] & 0XFF)); if (stmp.length() == 1) { hs = hs + "0" + stmp; } else { hs = hs + stmp; } if (n < b.length - 1) { hs = hs + ""; } } return hs; }


}
