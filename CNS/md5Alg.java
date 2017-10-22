import java.util.*;
class md5Alg {
	private static final int INIT_A = 0x67452301;
	private static final int INIT_B = (int)0xEFCDAB89L;
	private static final int INIT_C = (int)0x98BADCFEL;
	private static final int INIT_D = 0x10325476;
	/* per-round shift amounts */
	private static final int[] SHIFT_AMTS = { 7, 12, 17, 22, 5, 9, 14, 20, 4, 11, 16, 23, 6, 10, 15, 21 };
	/* binary integer part of sin's of integers (Radians) as constants */
	private static final int[] TABLE_T = new int[64];
	static {
		for (int i = 0; i < 64; i++){
			TABLE_T[i] = (int)(long)((1L << 32) * Math.abs(Math.sin(i + 1)));
		}
	}
	/* compute message digest (hash value) */
	public static byte[] computeMd5(byte[] msg){
		int msgLenBytes = msg.length; /* msg length (bytes) */
		long msgLenBits = (long)msgLenBytes << 3; /* msg length (bits) */
		int numBlks = ((msgLenBytes + 8) >>> 6) + 1; /* number of blocks */
		int totLen = numBlks << 6; /* total length */
		byte[] padB = new byte[totLen - msgLenBytes]; /* padding bytes */
		/* pre-processing with padding */
		padB[0] = (byte)0x80;
		for (int i = 0; i < 8; i++){
			padB[padB.length - 8 + i] = (byte)msgLenBits;
			msgLenBits >>>= 8;
		}
		int a = INIT_A;
		int b = INIT_B;
		int c = INIT_C;
		int d = INIT_D;
		int[] buf = new int[16];
		for (int i = 0; i < numBlks; i ++){
			int idx = i << 6;
			for (int j = 0; j < 64; j++, idx++){
				buf[j >>> 2] = ((int)((idx < msgLenBytes) ? msg[idx] :
				padB[idx - msgLenBytes]) << 24) | (buf[j >>> 2] >>> 8);
			}
			/* initialize hash value for this chunk */
			int origA = a;
			int origB = b;
			int origC = c;
			int origD = d;
				for (int j = 0; j < 64; j++){
				int div16 = j >>> 4;
				int f = 0;
				int bufIdx = j; /* buf idx */
				switch (div16){
					case 0: {
						f = (b & c) | (~b & d);
						break;
					}
					case 1:{
						f = (b & d) | (c & ~d);
						bufIdx = (bufIdx * 5 + 1) & 0x0F;
						break;
					}
					case 2:{
						f = b ^ c ^ d;
						bufIdx = (bufIdx * 3 + 5) & 0x0F;
						break;
					}
					case 3:{
						f = c ^ (b | ~d);
						bufIdx = (bufIdx * 7) & 0x0F;
						break;
					}
				}
				/* left rotate */
				int temp = b + Integer.rotateLeft(a + f + buf[bufIdx] + TABLE_T[j], SHIFT_AMTS[(div16 << 2) | (j & 3)]);
				a = d;
				d = c;
				c = b;
				b = temp;
			}
			/* add this chunk's hash to result so far */
			a += origA;
			b += origB;
			c += origC;
			d += origD;
		}
		byte[] md5 = new byte[16];
		int cnt = 0;
		for (int i = 0; i < 4; i++)
		{
		int n = (i == 0) ? a : ((i == 1) ? b : ((i == 2) ? c : d));
		for (int j = 0; j < 4; j++)
		{
		md5[cnt++] = (byte)n;
		n >>>= 8;
		}
		}
		return md5;
	}
	public static String toHexString(byte[] b){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++)
		{
		sb.append(String.format("%02x", b[i] & 0xFF));
		}
		return sb.toString();
	}
	public static void main (String[] args) throws java.lang.Exception{
		String msg = "hello world";
		System.out.println("simulation of MD5 algorithm");
		System.out.println("input msg : " + msg);
		System.out.println("md5 hash : " + toHexString(computeMd5(msg.getBytes())));
	}
}