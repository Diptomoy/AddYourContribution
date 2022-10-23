#include<bits/stdc++.h>
using namespace std;
int main()
{
    int n,min=10000,i1,i2;
    cin>>n;
    int a[n+1];
    for(int i=0;i<n;i++)
      cin>>a[i];
    for(int i=0;i<n;i++)
      if(abs(a[i%n]-a[(i+1)%n])<min)
      {
        min=abs(a[i%n]-a[(i+1)%n]);
        i1=i%n;
        i2=(i+1)%n;
      }
      cout<<i1+1<<" "<<i2+1;
    
 return 0;
}