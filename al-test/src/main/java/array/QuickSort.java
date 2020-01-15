package array;

public class QuickSort {
    private static void sort(int[] array, int low, int high) {
        if(low>high){
            return;
        }
        int i = low;
        int j = high;
        int t = 0;
        i = low;
        j = high;
        //基准数
        int temp = array[low];

        while (i < j) {
            while (array[j] >= temp) {
                j--;
            }

            while (array[i] <= temp) {
                i++;
            }
            if (i < j) {
                t = array[i];
                array[i] = array[j];
                array[j] = t;
            }
        }

        //i=j 基准数和array[i] 交换
        array[low] = array[i];
        array[i] = temp;
        sort(array, low, j - 1);
        sort(array, j + 1, high);
    }

    /**
     *
     * @param args
     */

    public static void main(String[] args) {
        int[] array = {9, 1, 5, 8, 3, 10, 16};
        sort(array, 0, array.length - 1);
        /*for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");

        }*/
        String str="/*"+"abc"+"*/";/*aaa*/
        /*
        sd
        ds
        ds
         */
    }

}