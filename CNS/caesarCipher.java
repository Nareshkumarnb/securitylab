import java.util.*;
class caesarCipher
{
public static String encode(String enc, int offset)
{
offset = offset % 26 + 26;
StringBuilder encoded = new StringBuilder();
for (char i : enc.toCharArray())
{
if (Character.isLetter(i))
{
if (Character.isUpperCase(i))
{
encoded.append((char) ('A' + (i - 'A' + offset) % 26 ));
}
else
{
encoded.append((char) ('a' + (i - 'a' + offset) % 26 ));
}
}
else
{
encoded.append(i);
}
}
return encoded.toString();
}
public static String decode(String enc, int offset)
{
return encode(enc, 26-offset);
}
public static void main (String[] args) throws java.lang.Exception{
String msg = "Hello welcome to Security Laboratory";
System.out.println("simulation of Caesar Cipher");
System.out.println("input message : " + msg);
System.out.printf( "encoded message : ");
System.out.println(caesarCipher.encode(msg, 12));
System.out.printf( "decoded message : ");
System.out.println(caesarCipher.decode(caesarCipher.encode(msg, 12), 12));
}
}