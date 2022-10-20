import java.util.Scanner;  
import java.lang.Math;  
public class ArmstsrongNumberExample  
{  
//function to check if the number is Armstrong or not  
static boolean isArmstrong(int n)   
{   
int t, digits=0, last=0, sum=0;    
t=n;   
 
while(t>0)    
{   
t = t/10;   
digits++;   
}   
t = n;   
while(t>0)   
{   
    
last = t % 10;   

sum +=  (Math.pow(last, digits));   
   
t = t/10;   
}    
if(n==sum)    
return true;       
else return false;   
}    
public static void main(String args[])     
{     
int num;   
Scanner sc= new Scanner(System.in);  
System.out.print("Enter the limit: ");  
 
num=sc.nextInt();  
System.out.println("Armstrong Number up to "+ num + " are: ");  
for(int i=0; i<=num; i++)  
  
if(isArmstrong(i))  

System.out.print(i+ ", ");  
}   
}  
