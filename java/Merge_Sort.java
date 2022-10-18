import java.util.Arrays;


public class Merge_Sort{


    static int[] mergeSort(int[] arr){

        if (arr.length==1) {
            return  arr;
        }

        int mid = arr.length/2;
        int[] left = mergeSort(Arrays.copyOfRange(arr, 0, mid));
        int[] right = mergeSort(Arrays.copyOfRange(arr, mid, arr.length));

        return merge(left, right);

    }


    static int[] merge(int[] first, int[] second){

        int[] mix = new int[first.length + second.length];
        int i = 0;
        int j = 0;
        int k = 0;


//  Comparing elements of the two arrays, less one is assigned to mix[] 

        while(i<first.length && j<second.length ){
            if(first[i] < second[j]){
                mix[k] = first[i];
                i++;
                k++;
            }
            else{
                mix[k] = second[j];
                j++;
                k++;
            }
        }


//  If one array elements are more than the other one, assign rest of array to mix[] 

        while(i<first.length){
            mix[k] = first[i];
            i++;
            k++;
        }

        while(j<second.length){
            mix[k] = second[j];
            j++;
            k++;
        }
        return mix;
    }





    public static void main(String[] args) {
        int[] arr = {8,4,3,12,5,6};
        arr=mergeSort(arr);
        System.out.println("After Merge sort : "+Arrays.toString(arr));
    }


}
