package array;

public class Array2 {
    public static void main(String[] args) {
        String [][] array1=new String[3][2];
        //行数
        int row=array1.length;
        //列数
        int col=array1[0].length;

        for(int i=0;i<array1.length;i++){
            for(int j=0;j<array1[i].length;j++){
                array1[i][j]=i+"_"+j;
                System.out.print(array1[i][j]+" ");
            }
            System.out.println("");
        }
        System.out.println("====================");
        String[][] array2=new String[col][row];
        for(int i=0;i<array2.length;i++){
            for(int j=0;j<array2[i].length;j++){
                //System.out.println(i+"="+j);
                System.out.print(array1[j][i]+" ");
                //array2[i][j]=array1[j][i];
                //System.out.println(array2[i][j]);
            }
            System.out.println("");
        }



    }
}
