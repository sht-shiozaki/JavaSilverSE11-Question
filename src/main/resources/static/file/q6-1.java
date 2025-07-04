int[] secA = { 2, 4, 6, 8, 10 };
int[] secB = { 2, 4,8 , 6 , 10};
int res1 = Arrays.mismatch(secA, secB);
int res2 = Arrays.compare(secA, secB);
System.out.println(res1 + " : " + res2);