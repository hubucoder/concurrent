package cn.itcast.n4.reentrant;

public class test1 {
    public static void main(String[] args) {
        boolean b = repeatedSubstringPattern("babbabbabbabbab");
        System.out.println(b);
    }

    public static boolean repeatedSubstringPattern(String s) {
        // 只能是 偶数
        if(s.length()%2 != 0) return false;
        int[] next = new int[s.length()];
        int j = -1;
        int max = j;

        next[0] = j;
        for (int i = 1; i < s.length(); i++) {
            while (j >= 0 && s.charAt(i) != s.charAt(j+1)){
                j = next[j];
            }
            if(s.charAt(i) == s.charAt(j+1)){
                j++;
            }
            next[i] = j;
            if (next[i] > max) {
                max = next[i];

            }
        }
        System.out.println(next);
        System.out.println(max);
        // 找出 next 数组最大值，就是最长相等前后缀
        // s.length() - next.max+1 就是最长子串
        return s.length()%(s.length() - (max+1)) == 0? true:false;
    }
}
