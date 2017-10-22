import java.math.BigInteger;
import java.util.Random;
import java.io.*;
public class RSA {
    private BigInteger p, q, n, phi, e, d; /* public key components */
    private int bitLen = 1024;
    private int blkSz = 256; /* block size in bytes */
    private Random rand;
	
    private static String bytesToString(byte[] encrypted) {
        String str = "";
        for (byte b : encrypted){
			str += Byte.toString(b);
        }
        return str;
    }
	
    public byte[] encrypt(byte[] msg){
        return (new BigInteger(msg)).modPow(e, n).toByteArray();
    }
	
    public byte[] decrypt(byte[] msg){
        return (new BigInteger(msg)).modPow(d, n).toByteArray();
    }

    public RSA(){
        rand = new Random();
        p = BigInteger.probablePrime(bitLen, rand);
        q = BigInteger.probablePrime(bitLen, rand);
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitLen/2, rand);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0){
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
    }
    public RSA(BigInteger e, BigInteger d, BigInteger n){
        this.e = e;
        this.d = d;
        this.n = n;
    }
    
    public static void main (String[] args) throws java.lang.Exception{
        RSA rsaObj = new RSA();
        String msg = "Hello world! Security Laboratory";
        System.out.println("simulation of RSA algorithm");
        System.out.println("message(string) : " + msg);

        System.out.println("message(bytes) : " + bytesToString(msg.getBytes()));
        /* encrypt test message */
        byte[] ciphertext = rsaObj.encrypt(msg.getBytes());
        System.out.println("ciphertext(bytes) : " + bytesToString(ciphertext));
        /* decrypt ciphertext */
        byte[] plaintext = rsaObj.decrypt(ciphertext);
        System.out.println("plaintext(bytes) : " + bytesToString(plaintext));
        System.out.println("plaintext(string) : " + new String(plaintext));
    }
}