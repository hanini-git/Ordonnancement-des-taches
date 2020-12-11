package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Ordon {

    public static List<List<Integer>> listCombin = new ArrayList<>();
    public static List<Integer> OrdonList=new ArrayList<>();
    public static int [] Ordonarray,data;
    public static int MinOptimal;
    public static List<Integer> listint=new ArrayList<>();
    public static int r=1,F,C,j,index,tmp,k;











    //calcul de f et avoir le optimale
    public static void CalculateWithArrayMethod(int [] array)
    {
        //System.out.println("Line");
        C=0;
        F=0;

        for (j=0;j<array.length;j++) {
            index = array[j];
            //System.out.println("index: " + index);


            C = C + Taches.taches[index][Taches.Duree];
            F = F + (Taches.taches[index][Taches.Poid] * Math.max(0, C - Taches.taches[index][Taches.DateLiv]));
            //System.out.print("C=" + C + " Wi= " + Taches.taches[index][Taches.Poid] + "  Ti=" + (C - Taches.taches[index][Taches.DateLiv]) + " DateLiv= " + Taches.taches[index][Taches.DateLiv]);
            //System.out.println(" ");

        }

            //System.out.println("Line");
            if(r==1)
            {
                MinOptimal=F;
                //Collections.copy(OrdonList,lo);
                Ordonarray=Arrays.copyOf(array,array.length);
                r=0;
            }
            // System.out.println("F ="+F);
            if(MinOptimal>F)
            {
                MinOptimal=F;
                // Collections.copy(OrdonList,lo);
                Ordonarray=Arrays.copyOf(array,array.length);


            }



    }

    public static void GetArray(int size)
    {
        data = new int[size];
        r=1;
        Ordonarray=new int[size];
        // int[][] ArrayofArray = new int[6][size];

        for (int i = 1; i <= size; i++) {
            data[i-1] = i;
        }

    }


    //Solutions n! possibles
    public static boolean permuteLexically(int[] data) {
        k = data.length - 2;
        while (data[k] >= data[k + 1]) {
            k--;
            if (k < 0) {
                return false;
            }
        }
        int l = data.length - 1;
        while (data[k] >= data[l]) {
            l--;
        }
        //swap(data, k, l);
        tmp=data[k];
        data[k]=data[l];
        data[l]=tmp;

        int length = data.length - (k + 1);
        for (int i = 0; i < length / 2; i++) {
            //swap(data, k + 1 + i, data.length - i - 1);
            tmp=data[k+1+i];
            data[k+1+i]=data[data.length - i - 1];
            data[data.length - i - 1]=tmp;
        }
        return true;
    }

}
