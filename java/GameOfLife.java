import java.util.Scanner;

public class GameOfLife
{
    public static void inputBoard(int mat[][], int row, int column)
    {
        int i,j;
        Scanner sc=new Scanner(System.in);
        for(i=0;i<=row-1;i++)
            for(j=0;j<=column-1;j++)
                mat[i][j]=sc.nextInt();
    }
    
    public static void printBoard(int mat[][], int row, int column)
    {
        int i,j;
        for(i=0;i<=row-1;i++)
        {
            for(j=0;j<=column-1;j++)
            {
                if(mat[i][j]==1)
                    System.out.print(mat[i][j]+"  ");
                else
                    System.out.print("_  ");
            }
            System.out.println();
        }
    }
    
    public static void solveGameOfLife(int board[][], int row, int column)
    {
        int i,j,count;
        int[][] matrix=new int[row][column];
        for(i=0;i<=row-1;i++)
            for(j=0;j<=column-1;j++)
                matrix[i][j]=board[i][j];
        for(i=0;i<=row-1;i++)
            for(j=0;j<=column-1;j++)
            {
                count=0;
                if(matrix[i][j]==1)
                {  
                    if(i!=0)
                    {
                        if(j!=0)
                            if(matrix[i-1][j-1]==1)
                                count++;
                        if(matrix[i-1][j]==1)
                            count++;
                        if(j!=column-1)
                            if(matrix[i-1][j+1]==1)
                                count++;
                    }
                    if(j!=0)
                        if(matrix[i][j-1]==1)
                            count++;
                    if(j!=column-1)
                            if(matrix[i][j+1]==1)
                                count++;
                    if(i!=row-1)
                    {
                        if(j!=0)
                            if(matrix[i+1][j-1]==1)
                                count++;
                        if(matrix[i+1][j]==1)
                            count++;
                        if(j!=column-1)
                            if(matrix[i+1][j+1]==1)
                                count++;
                    }
                    if(count<2 || count>3)
                            board[i][j]=0;
                }
                else if(matrix[i][j]==0)
                {
                    if(i!=0)
                    {
                        if(j!=0)
                            if(matrix[i-1][j-1]==1)
                                count++;
                        if(matrix[i-1][j]==1)
                                count++;
                        if(j!=column-1)
                            if(matrix[i-1][j+1]==1)
                                count++;
                    }
                    if(j!=0)
                        if(matrix[i][j-1]==1)
                            count++;
                    if(j!=column-1)
                        if(matrix[i][j+1]==1)
                            count++;
                    if(i!=row-1)
                    {
                        if(j!=0)
                            if(matrix[i+1][j-1]==1)
                                count++;
                        if(matrix[i+1][j]==1)
                            count++;
                        if(j!=column-1)
                            if(matrix[i+1][j+1]==1)
                                count++;
                    }
                    if(count==3)
                        board[i][j]=1;
                }
            }
    }
    
    public static void main(String[] args)
    {
        int i,row,column,count;
	    Scanner sc=new Scanner(System.in);
        System.out.print("Enter the number of rows and columns of the board: ");
        row=sc.nextInt();
        column=sc.nextInt();
        int[][] board=new int[row][column];
        System.out.println("\nEnter the board elements (Use '1' for filled spaces & '0' for blank spaces)...");
        inputBoard(board,row,column);
        System.out.print("\nEnter how many generations to print: ");
        count=sc.nextInt();
        System.out.println("\nBoard before updation (Previous Generation)...");
        printBoard(board,row,column);
        sc.close();

        for(i=1;i<=count;i++)
        {
            solveGameOfLife(board,row,column);

            System.out.println("\nBoard after updation (Generation "+i+")...");
            printBoard(board,row,column);
        }
    }
}