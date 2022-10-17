public class Sudoku
{  
public boolean isSafe(int[][] b, int r, int c, int n)  
{  
  
for (int d = 0; d < b.length; d++)  
{  
  
  
if (b[r][d] == n)   
{  
return false;  
}  
}  
  
  
for (int r1 = 0; r1 < b.length; r1++)  
{  
  
 
if (b[r1][c] == n)  
{  
return false;  
}  
}  
  

int sqt = (int)Math.sqrt(b.length);  
int boxRowSt = r - r % sqt;  
int boxColSt = c - c % sqt;  
  
for (int r1 = boxRowSt; r1 < boxRowSt + sqt; r1++)  
{  
for (int d = boxColSt; d < boxColSt + sqt; d++)  
{  

if (b[r1][d] == n)  
{  
return false;  
}  
}  
}  
  

return true;  
}  
  
public boolean solveSudoku(int[][] b, int num)  
{  
int r = -1;  
int c = -1;  
boolean isVacant = true;  
for (int i = 0; i < num; i++)  
{  
for (int j = 0; j < num; j++)  
{  
if (b[i][j] == 0)  
{  
r = i;  
c = j;  
  
 
isVacant = false;  
break;  
}  
}  
  
if (!isVacant)   
{  
break;  
}  
}  
  

if (isVacant)  
{  
return true;  
}  
  

for (int no = 1; no <= num; no++)  
{  
if (isSafe(b, r, c, no))  
{  
b[r][c] = no;  
if (solveSudoku(b, num))  
{  

return true;  
}  
else  
{  
  
b[r][c] = 0;  
}  
}  
}  
return false;  
}  
  
public void display(int[][] b, int n)  
{  
  

for (int i = 0; i < n; i++)  
{  
for (int d = 0; d < n; d++)  
{  
System.out.print(b[i][d]);  
System.out.print(" ");  
}  
  
System.out.print("\n");  
  
if ((i + 1) % (int)Math.sqrt(n) == 0)  
{  
System.out.print("");  
}  
}  
}  
  
