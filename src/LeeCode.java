import java.util.*;

public class LeeCode {

    /**
     * 煎饼排序
     * 每次选择数组中最大的一个元素将它换到第一个位置，然后将整个数组反转 这样 需要排序的个数减1 依次类推
     * @param A  需要排序的数组
     * @return 反转的大小的集合
     */
    public List<Integer> pancakeSort(int[] A) {
        List<Integer> list = new ArrayList<>();
        int size = A.length-1;
        while(0<size){
            if(findIndex(A,size)<size){
                list.add(findIndex(A,size)+1);
                list.add(size+1);
            }
            reverse(A,0,findIndex(A,size));
            reverse(A,0,size--);
        }
        return list;
    }

    public static int[] reverse(int[] A, int i, int j){
        for(;i<j;i++,j--){
            int temp=A[i];
            A[i]=A[j];
            A[j]=temp;
        }
        return A;
    }
    private int findIndex(int[] a, int size) {
        int max = 0 ;
        int k = 0;
        for(int i = 0 ;i<size; i++){
            if(a[i]>max){
                max =a[i];
                k = i;
            }
        }
        return k ;
    }

    /**
     * 给定一个字符串和一个字符串字典，找到字典里面最长的字符串，该字符串可以通过删除给定字符串的某些字符来得到。如果答案不止一个，返回长度最长且字典顺序最小的字符串。如果答案不存在，则返回空字符串
     *
     * 将链表中的字符串进行排序，然后遍历字符串，如果发现一个字符串是给定字符串的子串，则返回这个字符串
     * @param s 给定对比的字符串
     * @param d 字符串链表
     * @return 所求的字符串
     */
    public String findLongestWord(String s, List < String > d) {
        //排序后则我们越需要的字符串就越有可能出现在前面
        d.sort((o1, o2) -> o2.length() != o1.length() ? o2.length() - o1.length() : o1.compareTo(o2));
        for(String str :d){
            if(isSubString(str,s)){
                return  str;
            }
        }
        return "";
    }

    private boolean isSubString(String str, String s) {
        int j = 0;
        for(int i = 0 ; i<s.length() && j<str.length(); i++){
            if(str.charAt(j) == s.charAt(i)){
                j++;
            }
        }
        return j==str.length();
    }

    /**
     * 使用集合进行实现
     * 两个数组的交集
     * @param nums1 数组1
     * @param nums2 数组2
     * @return 两个数组的交集 结果是一个数组
     */

    public int[] intersect1(int[] nums1, int[] nums2) {
        List<Integer> list1 = new ArrayList<>();
        for(int num :nums1){
            list1.add(num);
        }
        List<Integer> list2 = new ArrayList<>();
        for(int num :nums2){
            if(list1.contains(num)){
                list2.add(num);
                list1.remove(Integer.valueOf(num));
            }
        }
        int []res = new int[list2.size()];
        int i= 0;
        for(int num :list2){
            res[i++] = num;
        }
        return res;
    }
    /**
     * 两个数组的交集
     * 排序预处理
     * @param nums1 数组1
     * @param nums2 数组2
     * @return 两个数组的交集 结果是一个数组
     */

    public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> list = new ArrayList<>();
        for(int i = 0 , j = 0 ;i< nums1.length&& j <nums2.length;){
            if(nums1[i]<nums2[j]){
                i++;
            }else if(nums1[i]>nums2[j]){
                j++;
            }else{
                list.add(nums1[i]);
                i++;
                j++;
            }
        }
        int []res = new int[list.size()];
        for(int i = 0 ; i < list.size();i++){
            res[i] = list.get(i);
        }
        return res;
    }
    /**
     * 给定一个整数数组，判断数组中是否有两个不同的索引 i 和 j，使得 nums [i] 和 nums [j] 的差的绝对值最大为 t，并且 i 和 j 之间的差的绝对值最大为 ķ
     *  由于索引方便定位 故使用索引维持一个滑动窗口在窗口内进行查找 窗口大小 为以nums[i]为中心 t为半径的区域
     *  使用哈系树的两个方法 分别是 seiling 和 floor 分别用来查找区间内最大的数和最小的数
     * @param nums 整数数组
     * @param k 索引差的最大值
     * @param t 元素差的最大值
     * @return 是否有符合条件的元素
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        int n = nums.length;
        if(n == 0){
            return false;
        }
        TreeSet<Long> set = new TreeSet<>();
        for(int i = 0 ; i <nums.length ; i++){
            // nums[i]-t ____nums[i]中最大的值
            Long ceiling = set.ceiling((long)nums[i] - t);
            //以nums[i]+/- t为半径进行 查找 若存在在区间内的元素 即符合条件的元素
            if(ceiling !=null && ceiling <= nums[i] ){
                return true;
            }
            Long floor = set.floor((long) t+nums[i]);
            if(floor!=null && floor >= nums[i]){
                return true;
            }
            set.add((long) nums[i]);
            if(i>=k){
                set.remove((long) nums[i-k]);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] nums = {1,5,9,1,5,9};
        LeeCode l = new LeeCode();
        System.out.println(l.containsNearbyAlmostDuplicate(nums,2,3));
        int []n = {1,2,3};
        System.out.println(l.pancakeSort(n));
    }
}
