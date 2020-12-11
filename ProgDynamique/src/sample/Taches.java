package sample;

import java.util.Random;

public class Taches {


    public static int Duree=0;
    public static int Poid=1;
    public static int DateLiv=2;
    public static int taches[][];
    public static int MatGBL[][];;
    public static int ArrayNumtaches[];

    private int id,poids,dateliv,duree;

    public int getId() {
        return id;
    }

    public int getPoids() {
        return poids;
    }

    public int getDateliv() {
        return dateliv;
    }

    public int getDuree() {
        return duree;
    }

    public Taches(int id,int duree, int poids, int dateliv) {
        this.id = id;
        this.poids = poids;
        this.dateliv = dateliv;
        this.duree = duree;
    }
// public static int ListContrib[][];

    public static long Factorial(int number) {
        long result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }

    public static void Generate(int n)
    {
        taches=new int[n+1][3];
        Random r =new Random();
        for(int i=1;i<taches.length;i++) {
            for(int j=0;j<taches[i].length;j++) {
                   if(j==0){
                       taches[i][j]=r.nextInt(100)+1;
                   }
                   else if (j==1)
                   {
                       taches[i][j]=r.nextInt(10)+1;
                   }


            }



        }


        int total=GetTotalDuree(taches);
        System.out.println("total = "+total);
        int min= (int) (0.2*total);
        System.out.println("min = "+min);
        int max= (int) (0.6*total);
        System.out.println("max = "+max);
        for(int i=1;i<taches.length;i++) {
            for(int j=0;j<taches[i].length;j++) {
                if(j==2){
                    taches[i][j]=r.nextInt(max)+min;
                }

            }


        }

    }

    public static void ShowTaches()
    {
        for(int i=1;i<taches.length;i++) {
            for(int j=0;j<taches[i].length;j++) {

                System.out.println("taches["+i+"]["+j+"] = "+taches[i][j]);
            }
            }

    }
    public static void GetArrayTaches()
    {
        ArrayNumtaches=new int[taches.length-1];
        for(int i=1;i<taches.length;i++) {

                ArrayNumtaches[i-1]=i;
                System.out.println(i);

        }

    }
    public static int GetTotalDuree( int tachesT[][])
    {   int total=0;
        for(int i=1;i<tachesT.length;i++) {

                total=total+tachesT[i][Taches.Duree];



        }
       return total;
    }

    public static void GetTachesMethod2()
    {

        int n=taches.length;
        OrdonM2.matGlb=new int[3][n-1];
        for(int i=0;i<OrdonM2.matGlb.length;i++) {
            for(int j=0;j<OrdonM2.matGlb[i].length;j++) {
                OrdonM2.matGlb[i][j]= taches[j+1][i];

            }
        }


    }

    public static void ShowTaches2()
    {
        for(int i=0;i<OrdonM2.matGlb.length;i++) {
            for(int j=0;j<OrdonM2.matGlb[i].length;j++) {

                System.out.println("MatGBL["+i+"]["+j+"] = "+OrdonM2.matGlb[i][j]);
            }
        }

    }



}
