#include <bits/stdc++.h>
using namespace std;
int main()
{
    int n;
    cin >> n;
    long long a[n], b[n];
    for (int i = 0; i < n; i++)
    {
        cin >>a[i];
        b[i]=a[i];
    }
    sort(b, b + n);
    if (b[0] == b[1])
    {
        cout << "Still Rozdil";
    }
    else
    {
        for (int i = 0; i < n; i++)
        {
            if (a[i] == b[0])
            {
                cout << i + 1;
                break;
            }
        }
    }

    return 0;
}