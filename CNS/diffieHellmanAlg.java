import java.util.*;
class diffieHellmanAlg{
public static void main (String[] args) throws java.lang.Exception{
	int p = 11; /* publicly known (prime number) */
	int g = 2; /* publicly known (primitive root) */
	int x = 9; /* only Alice knows this secret */

	int y = 4; /* only Bob knows this secret */
	double aliceSends = (Math.pow(g,x))%p;
	double bobComputes = (Math.pow(aliceSends,y))%p;
	double bobSends = (Math.pow(g,y))%p;
	double aliceComputes = (Math.pow(bobSends,x))%p; double sharedSecret = (Math.pow(g,(x*y)))%p;
	System.out.println("simulation of Diffie-Hellman key exchange algorithm");
	System.out.println("aliceSends : " + aliceSends);
	System.out.println("bobComputes : " + bobComputes);
	System.out.println("bobSends : " + bobSends);
	System.out.println("aliceComputes : " + aliceComputes);
	System.out.println("sharedSecret : " + sharedSecret);
	/* shared secrets should match and equality is transitive */
	if ((aliceComputes == sharedSecret) && (aliceComputes == bobComputes))
		System.out.println("success: shared secrets matches! " + sharedSecret);
	else
		System.out.println("error: shared secrets does not match");
	}
}