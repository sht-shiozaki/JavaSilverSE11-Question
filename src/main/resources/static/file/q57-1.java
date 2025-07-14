次の毎月の住宅ローンの支払いを計算する式があります。
※n:乗
          n
    r(1+r)
M=P -------
          n
    r(1+r) -1

および次のコードがあります。

double m; // monthly payment
double r = 0.05 / 12; // monthly interest rate
int p = 100_000; // principal
int n = 180; // number of payments