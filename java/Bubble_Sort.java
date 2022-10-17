import java.util.*;

class SortFunc{
    void Sort(int arr[], int n){ 
        for (int i=0; i<n-1; i++){
            for (int j = 0; j < n-i-1; j++) {
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
        System.out.println("After Bubble sorting the elements of the array are : ");
        for(int i=0; i<n; i++){
            System.out.println(arr[i]);
        }
    
    }

}




public class Bubble_Sort {
    public static void main(String[] args){
        
        System.out.println("Enter the size of the array : ");
        Scanner sc1 = new Scanner(System.in);
        int n = sc1.nextInt();
        
        int arr[] = new int[n];
        System.out.println("Enter the elements of the array :");
        for(int i=0; i<n; i++){
            arr[i] = sc1.nextInt();
        }
        
        System.out.println("Before sorting the elements of the array are :");
        for(int i=0; i<n; i++){
            System.out.println(arr[i]);
        }
        
        SortFunc obj = new SortFunc(); 
        obj.Sort(arr, n);
        
        }

}
