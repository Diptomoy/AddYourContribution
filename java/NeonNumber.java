import java.util.*;
public class NeonNumber
{
  public static void main(String args[])
  {
    int sumSquareDigits=0, num;
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter a number: ");
    
    num = scanner.nextInt();
    scanner.close();
    int square = num * num;
   
    while(square != 0)
    {
      int lastDigit = square % 10;
      sumSquareDigits = sumSquareDigits + lastDigit;
      square = square / 10;
    }
   
    if(num == sumSquareDigits)
      System.out.println(num + " is a Neon Number.");
    else
      System.out.println(num + " is not a Neon Number.");
  }
}  