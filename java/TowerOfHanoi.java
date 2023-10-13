import java.util.*;

public class TowerOfHanoi
{
    public static void towerOfHanoi(int n, char source, char temp, char destination)
    {
        if(n==1)
        {
            System.out.println("Disk "+n+": "+source+" ---> "+destination);
            return;
        }
        towerOfHanoi(n-1,source,destination,temp);
        System.out.println("Disk "+n+": "+source+" ---> "+destination);
        towerOfHanoi(n-1,temp,source,destination);

    }

    public static void main(String[] args)
    {
        int n;
	    char source='A', temp='B', destination='C';
        Scanner sc=new Scanner(System.in);
	    System.out.print("Enter the number of disks: ");
	    n=sc.nextInt();
        sc.close();
	    if(n<0)
        System.out.println("\nPlease enter a positive no. of disks...");
	    else
	    {
		    System.out.println("\nSequence is...");
		    towerOfHanoi(n,source,temp,destination);
		    System.out.println("\nTotal no. of moves required: "+(int)(Math.pow(2,n)-1));
		}
    }
}