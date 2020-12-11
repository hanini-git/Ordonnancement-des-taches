package sample;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class VNS {


    public static int [] tmparr,Voisinagearray,VoisinagearrayLocal,CurrentVoisinageArray,ShakingVoisinageArray,GlobalVoisinageArray,OptimalGlobalVoisingaeARRay;
    public static int FVoisinageOptimal,FVoisinageOptimalLocal,FShaking,FGlobal;
    public static long start,end,endReduced;

    public static int r=1,rL,F,FLocal,C,j,index,nStructure=0,m=0,l,k,Ftmp,tmp;
    public static List <Integer>listmp,lis;
    public static Random rand =new Random();
    public static void Initialize(int size)

    {

        r=1;

        GlobalVoisinageArray=new int[size];
        //Voisinagearray=new int[size];
        //OptimalGlobalVoisingaeARRay=new int[size];

        VoisinagearrayLocal=new int[size];
        ShakingVoisinageArray=new int[size];




        for (int i = 1; i <= size; i++) {
            GlobalVoisinageArray[i-1] = i;
        }
         start=System.currentTimeMillis();
         //endReduced=start+5*1000;
         end=start+10*1000; //10 seconds VNS




    }





    public static void Shaking(int k)
    {


        ShakingVoisinageArray = Arrays.copyOf(GlobalVoisinageArray, GlobalVoisinageArray.length);

        if(k==1)
        { int i,j;
          //Insertion
          i =rand.nextInt(ShakingVoisinageArray.length);
          do{
              j =rand.nextInt(ShakingVoisinageArray.length);
          }while (j==i || j==i+1);

          List<Integer> lis=new ArrayList<>();
          for(int t = 0; t < ShakingVoisinageArray.length; t++)
          {
              lis.add(ShakingVoisinageArray[t]);
          }
          int a=lis.get(i);
          lis.remove(i);
          //System.out.print("lenth ="+CurrentVoisinageArray.length);

          //System.out.println("i="+i);
          //System.out.println("j="+j);

          lis.add(j,a);
          for(int t = 0; t < ShakingVoisinageArray.length; t++)
          {
              ShakingVoisinageArray[t]=lis.get(t);
          }








      }
        else if (k==2)
      {
          //Swap
          int i,j;
          i =rand.nextInt(ShakingVoisinageArray.length);
          j =rand.nextInt(ShakingVoisinageArray.length);


          int tmp=ShakingVoisinageArray[i];
          ShakingVoisinageArray[i]=ShakingVoisinageArray[j];
          ShakingVoisinageArray[j]=tmp;

      }
      else if (k==3)
      {

          //Left_Pivot
          int t=(rand.nextInt(ShakingVoisinageArray.length))-1;




          for (int i = 0; i < t / 2; i++)
          {
              int tempswap = ShakingVoisinageArray[i];
              ShakingVoisinageArray[i] = ShakingVoisinageArray[t - i - 1];
              ShakingVoisinageArray[t - i - 1] = tempswap;
          }



      }

    }






    //Avoir le minimum local a partir dune tel structure de voisinage
    public static void GetMinLocal(int l)
    {
        tmparr=new int[ShakingVoisinageArray.length];
        rL=1;




        if(l==1){
            //Insertion



            lis=new ArrayList<>();
            for(int k = 0; k < ShakingVoisinageArray.length; k++)
            {
                lis.add(ShakingVoisinageArray[k]);
            }
            for(int i=0;i<lis.size();i++){


                //System.out.print("lenth ="+CurrentVoisinageArray.length);

                //System.out.println("i="+i);
                //System.out.println("j="+j);
                for(int j=0;j<lis.size();j++)
                {
                    if(j!=i+1 && j!=i){
                        listmp=new ArrayList<>(lis);
                        int b=listmp.get(i);

                        listmp.remove(i);
                        listmp.add(j,b);


                        for(int k = 0; k < tmparr.length; k++)
                        {
                            tmparr[k]=listmp.get(k);
                        }
                        //System.out.println("i="+i+"  j="+j+" : "+Arrays.toString(tmparr));
                        FLocal=CalculateF(tmparr);
                        if (rL == 1) {
                            FVoisinageOptimalLocal = FLocal;
                            //Collections.copy(OrdonList,lo);
                            VoisinagearrayLocal = Arrays.copyOf(tmparr, tmparr.length);
                            rL = 0;

                        }
                        if(FLocal < FVoisinageOptimalLocal){
                            FVoisinageOptimalLocal = FLocal;
                            VoisinagearrayLocal = Arrays.copyOf(tmparr, tmparr.length);
                        }

                    }
                }

            }

        }
        if(l==2){
            //SWAP


            for(int i=0;i<ShakingVoisinageArray.length;i++)
            {   //tmparr
                for(int j=i+1;j<ShakingVoisinageArray.length;j++)
                {   System.arraycopy( ShakingVoisinageArray, 0, tmparr, 0, ShakingVoisinageArray.length );

                    tmp=tmparr[i];
                    tmparr[i]=tmparr[j];
                    tmparr[j]=tmp;
                    //System.out.println(Arrays.toString(tmparr));
                    FLocal=CalculateF(tmparr);
                    if (rL == 1) {
                        FVoisinageOptimalLocal = FLocal;
                        //Collections.copy(OrdonList,lo);
                        VoisinagearrayLocal = Arrays.copyOf(tmparr, tmparr.length);
                        rL = 0;

                    }
                    if(FLocal < FVoisinageOptimalLocal){
                        FVoisinageOptimalLocal = FLocal;
                        VoisinagearrayLocal = Arrays.copyOf(tmparr, tmparr.length);
                    }

                }
            }


        }
        if(l==3){
            //Lef_Pivot


            for(int k=2;k<ShakingVoisinageArray.length;k++)
            {   System.arraycopy( ShakingVoisinageArray, 0, tmparr, 0, ShakingVoisinageArray.length );
                for (int i = 0; i < k / 2; i++)
                {
                    int tempswap = tmparr[i];
                    tmparr[i] = tmparr[k - i - 1];
                    tmparr[k - i - 1] = tempswap;
                }

                //System.out.println(Arrays.toString(tmparr));
                FLocal=CalculateF(tmparr);
                if (rL == 1) {
                    FVoisinageOptimalLocal = FLocal;
                    //Collections.copy(OrdonList,lo);
                    VoisinagearrayLocal = Arrays.copyOf(tmparr, tmparr.length);
                    rL = 0;

                }
                if(FLocal < FVoisinageOptimalLocal){
                    FVoisinageOptimalLocal = FLocal;
                    VoisinagearrayLocal = Arrays.copyOf(tmparr, tmparr.length);
                }
            }

        }






    }


    //Calcul de f
    public static int CalculateF(int [] data)
    {

        C=0;
        Ftmp=0;
        // System.out.println("current"+System.currentTimeMillis());
        //System.out.println("end "+end);


        // m++;
        for (j = 0; j < data.length; j++) {
            index = data[j];
            //System.out.println("index: " + index);


            C = C + Taches.taches[index][Taches.Duree];
            Ftmp = Ftmp + (Taches.taches[index][Taches.Poid] * Math.max(0, C - Taches.taches[index][Taches.DateLiv]));
            //System.out.print("C=" + C + " Wi= " + Taches.taches[index][Taches.Poid] + "  Ti=" + (C - Taches.taches[index][Taches.DateLiv]) + " DateLiv= " + Taches.taches[index][Taches.DateLiv]);
            //System.out.println(" ");

        }

        //System.out.println("Line");
      return Ftmp;
    }

    //Move or not Global (l)
    public static void MoveOrNotLocal()
    {
        if(FVoisinageOptimalLocal < FShaking)
        {
            FShaking=FVoisinageOptimalLocal;
            ShakingVoisinageArray = Arrays.copyOf(VoisinagearrayLocal, VoisinagearrayLocal.length);
            l=1;
        }
        else {
            l=l+1;
        }
    }

    //REcherche local avec VND
    public static void LocalSearchByVND()
    {
             l=1;
             while(l<3)
             {
                 GetMinLocal(l);
                 MoveOrNotLocal();
             }
    }


    //Move or not Global (k)
    public static void MoveOrNotGlobal()
    {
        if(FShaking < FGlobal)
        {

            FGlobal=FShaking;
            GlobalVoisinageArray = Arrays.copyOf(ShakingVoisinageArray, ShakingVoisinageArray.length);
            k=1;
        }
        else {
            k=k+1;
        }
    }

    //General VNS Algorithm implémenté
    public static void GeneralVNS()
    {
        FGlobal=CalculateF(GlobalVoisinageArray);
        k=1;
        while(k<3)
        {
            Shaking(k);
            FShaking=CalculateF(ShakingVoisinageArray);
            LocalSearchByVND();
            MoveOrNotGlobal();

        }
        System.out.println("Voisinage Global Loop");
        System.out.println("Global array :"+Arrays.toString(GlobalVoisinageArray));
        System.out.println("Voisinage Global :  "+FGlobal);
    }


    //Amelioration de x avec Reducued VNS
    public static void ReducedVNS()
    {
        FGlobal=CalculateF(GlobalVoisinageArray);
        k=1;
        while(k<3)
        {
            Shaking(k);
            FShaking=CalculateF(ShakingVoisinageArray);
            MoveOrNotGlobal();

        }
        System.out.println("Voisinage Reduced Loop");
        System.out.println("Global array :"+Arrays.toString(GlobalVoisinageArray));
        System.out.println("Voisinage Global :  "+FGlobal);
    }

}
