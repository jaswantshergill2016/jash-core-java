package assignment;

public class AsciiBubbleSort {

    public static void main(String[] args) {

        char arr[] = {'a','h','d','i','r','h','e','n'};

        for (int i=0;i<arr.length;i++){
            for(int j=i;j<arr.length;j++){
             if((int)arr[i] > (int)arr[j] ){
                 char temp =arr[j];
                 arr[j] = arr[i];
                 arr[i] = temp;

             }
            }
        }


        for(int k =0; k< arr.length;k++){
            System.out.println(arr[k]);
        }
    }
}
