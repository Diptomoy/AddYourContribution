//GCD of 2 numbers
class GCD{
	public static void main(String[] args){
		int a=12,b=8,gcd=1;
		for(int i=1;i<=a && i<=b;i++){
			if(a%i==0 && b%i==0){
				gcd=i;
			}
		}
		System.out.println("GCD of "+a+" and "+b+" is= "+gcd);
	}
}
