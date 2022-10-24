import java.util.*;
class DiscountDemo{
    private int price;
    void calculate(int price)
    {
        this.price=price;
        if(this.price<=25000)
        {
            float discount =this.price -((5*this.price)/100);
            System.out.println("the price is "+discount);
        }
        else if(this.price>25000 && this.price<=50000)
        {
            float discount= (float) (this.price-((7.5*this.price)/100));
            System.out.println("price is"+discount);

        }
        else if(this.price>50000 && this.price<=100000)
        {
            float discount= (float) (this.price-((10*this.price)/100));
            System.out.println("price is"+discount);
        }
        else
        {
            float discount= (float) (this.price-((15*this.price)/100));
            System.out.println("Tax is"+discount);
        }
    }
}

public class Discount {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("the price is ");
        int price = sc.nextInt();
        DiscountDemo d = new DiscountDemo();
        d.calculate(price);
    }
}
