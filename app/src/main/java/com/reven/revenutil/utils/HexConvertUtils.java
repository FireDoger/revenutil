package com.reven.revenutil.utils;

/**
 * Created by Reven
 * on 2022/3/18.
 */
public class HexConvertUtils
{

    public static String convertStringToHex(String str)
    {

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++)
        {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString();
    }

    public static String convertHexToString(String hex)
    {

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i < hex.length() - 1; i += 2)
        {

            String s = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(s, 16);
            sb.append((char) decimal);
            sb2.append(decimal);
        }

        return sb.toString();
    }

    public static byte[] hexStringToBytes(String hexString)
    {
        if (hexString == null || hexString.equals(""))
        {
            return null;
        }
        // toUpperCase将字符串中的所有字符转换为大写
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        // toCharArray将此字符串转换为一个新的字符数组。
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++)
        {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    //返回匹配字符
    private static byte charToByte(char c)
    {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    //将字节数组转换为short类型，即统计字符串长度
    public static short bytes2Short2(byte[] b)
    {
        short i = (short) (((b[1] & 0xff) << 8) | b[0] & 0xff);
        return i;
    }

    //将字节数组转换为16进制字符串
    public static String BinaryToHexString(byte[] bytes)
    {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes)
        {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex + " ";
        }
        return result;
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param hexString 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String hexString)
    {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2)
        {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }

    //    public static void main(String[] args)
    //    {
    //
    //        System.out.println("======ASCII码转换为16进制======");
    //        String str = "*00007VERSION\\n1$";
    //        System.out.println("字符串: " + str);
    //        String hex = HexConvertUtils.convertStringToHex(str);
    //        System.out.println("====转换为16进制=====" + hex);
    //
    //        System.out.println("======16进制转换为ASCII======");
    //        System.out.println("Hex : " + hex);
    //        System.out.println("ASCII : " + HexConvertUtils.convertHexToString(hex));
    //
    //        byte[] bytes = HexConvertUtils.hexStringToBytes(hex);
    //
    //        System.out.println(HexConvertUtils.BinaryToHexString(bytes));
    //    }
}