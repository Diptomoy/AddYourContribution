import java.util.Arrays;

public class Selection_Sort {
    public static void main(String[] args) {
        int[] arr = {9,5,8,4,3,6,1};

        System.out.println("Before sorting the array is : ");
        System.out.println(Arrays.toString(arr));
        
        System.out.println("After selection sort the array is : ");
        selectionSortEasy(arr);
        System.out.println(Arrays.toString(arr));

    }
  
   static void selectionSortEasy(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if(arr[i] > arr[j]){
                    int temp = arr[i];
                    arr[i]=arr[j];
                    arr[j]=temp;
                }
            }
        }
    }
}
