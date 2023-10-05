#include<bits/stdc++.h>
#include <vector>
using namespace std;
int main()
{   vector<int> v;
    long long a=0,b=1;
    v.push_back(a);
    v.push_back(b);
    while(b<1000000000)
    {
      b=b+a;
      a=b-a;
      v.push_back(b);
    }
    for(int x:v) cout<<x<<" ";
    
 return 0;
}