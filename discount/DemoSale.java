import java.util.*;
class Sale{
    private int price;
    private int dis1;
    private int dis2;
    private int dis3;
    void discount(int price,int dis1)
    {
        this.price = price;
        this.dis1=dis1;
        int pad=price -((dis1*price)/100);
        System.out.println("price after one discount is"+pad);
    }
    void discount(int price,int dis1,int dis2)
    {
        this.price = price;
        this.dis1=dis1;
        this.dis2=dis2;
        int pad1=price -((dis1*price)/100);
        int pad=pad1 -((dis2*pad1)/100);
        System.out.println("price after one discount is"+pad);
    }
    void discount(int price,int dis1,int dis2,int dis3)
    {
        this.price = price;
        this.dis1=dis1;
        this.dis2=dis2;
        this.dis3=dis3;
        int pad2=price -((dis1*price)/100);
        int pad1=pad2 -((dis2*pad2)/100);
        int pad=pad1 -((dis3*pad1)/100);
        System.out.println("price after one discount is"+pad);

    }

}

public class DemoSale {
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("enter the price of the book");
        int price =sc.nextInt();
        int dis1=sc.nextInt();
        int dis2=sc.nextInt();
        int dis3=sc.nextInt();
        Sale s=new Sale();
        s.discount(price,dis1);
        s.discount(price,dis1,dis2);
        s.discount(price,dis1,dis2,dis3);
    }

}
