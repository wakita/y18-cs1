# Python 3 の正規表現機能についての実験
# a?a?a?aaa のような正規表現の aaa のような文字列への適用に要する時間の測定

import re
from time import time

def benchmark(pattern, string):
    time_start = time()
    pattern.match(string)
    return time() - time_start


i = 1
while True:
    t = benchmark(re.compile('a?' * i + 'a' * i), 'a' * i)
    print('{:>2}: {:2.2f}s'.format(i, t))
    if t > 10: break
    i = i + 1

'''
ベンチマークの実行結果

実行環境
    Python 3.5.2 |Anaconda 4.2.0 (x86_64)| (default, Jul  2 2016, 17:52:12)
    [GCC 4.2.1 Compatible Apple LLVM 4.2 (clang-425.0.28)] on darwin

    MacBook Pro (Retina, 15-inch, Mid 2014 model
    2.5 GHz Intel Core i7, 16GB 1600 MHz DDR3)

 1: 0.00s
 2: 0.00s
 3: 0.00s
 4: 0.00s
 5: 0.00s
 6: 0.00s
 7: 0.00s
 8: 0.00s
 9: 0.00s
10: 0.00s
11: 0.00s
12: 0.00s
13: 0.00s
14: 0.00s
15: 0.00s
16: 0.00s
17: 0.01s
18: 0.01s
19: 0.03s
20: 0.06s
21: 0.13s
22: 0.25s
23: 0.52s
24: 1.06s
25: 2.11s
26: 4.32s
27: 9.18s
28: 18.78s'''
