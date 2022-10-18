import java.util.Arrays;

public class Search_2DArray {
    public static void main(String[] args) {
        
        int arr[][] = {
            {33,6,9,},
            {21,45,76},
            {24,32,8,42,3},
            {22,4}
        };

        int target = 3;
        int ans[] = search(arr, target);
        System.out.println(Arrays.toString(ans));
        
    } 
    
    static int[] search(int arr[][], int target){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == target) {
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{-1,-1};
    }

}
