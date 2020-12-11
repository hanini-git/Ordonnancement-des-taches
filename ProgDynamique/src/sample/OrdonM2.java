package sample;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
public class OrdonM2 {

    public static int matGlb[][];
    public static List<Integer> combination;
    public static HashMap<String, Integer> HMapGlobalValues = new HashMap<String, Integer>();
    public static HashMap<String, Integer> ListOrdFinal = new HashMap<String, Integer>();
    public static HashMap<String, String> ListOrdrOptimal = new HashMap<String, String>();
    public static ArrayList<List<Integer>> ListCombinsNew = new ArrayList<>();
    public static ArrayList<List<Integer>> ListCombinsNewAll = new ArrayList<>();
    public static int CurrentLengh;
    public static int sommePi;
    public static int OrdonnResult;
    public static int MinTab[];
    public static int Ti[];
    public static int valUsed;
    public static int CountComs;
    public static String Conca;
    public static void Initialise()
    {
         HMapGlobalValues = new HashMap<String, Integer>();
        ListOrdFinal = new HashMap<String, Integer>();
        ListOrdrOptimal = new HashMap<String, String>();
        ListCombinsNew = new ArrayList<>();
        ListCombinsNewAll = new ArrayList<>();
    }
    public static int generateNumberRandomly(int min, int max){
        int random = ThreadLocalRandom.current().nextInt(min,max+1);
        return random;
    }

    public static void printRow(int[] row) {
        System.out.print("\t");
        for (int i : row) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }



    public static int[] BigTiFunction(int n){
        //int mat[][]=matGlb;
        int Ti[] = new int[n];
        for(int i=0;i<n;i++) {
            Ti[i]=Math.max(0, matGlb[0][i]-matGlb[2][i]);
        }
        return Ti;
    }

    public static int getMinValue(int[] numbers){
        int minValue = numbers[0];
        for(int i=1;i<numbers.length;i++){
            if(numbers[i] < minValue){
                minValue = numbers[i];
            }
        }
        return minValue;
    }

    public static <K, V> K getKeyFromMinValue(Map<K, V> map, V value) {
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }

    public static String FuncOrdreFinal(HashMap<String, String> Hm ){
        String key=MaxKey(Hm);
        String val="";
        String NVal="";

        while (Hm.containsKey(key)) {
            val = Hm.get(key);
            String[] parts = val.split("]");
            key = parts[0] + "]";
            NVal = NVal + "-" + parts[1];
        }
        return NVal;
    }

    public static String ReverseStr(String str){
        String reverse="";
        for(int i = str.length() - 1; i >= 0; i--)
        {
            reverse = reverse + str.charAt(i);
        }
        return reverse;
    }

    public static String MaxKey(HashMap<String, String> map){
        String maxKey = null;
        for (String key : map.keySet())
        {
            if (maxKey == null || map.get(key).length() > map.get(maxKey).length())
            {
                maxKey = key;
            }
        }
        return maxKey;
    }

    public static String ConcatCombi(int [] array){

        boolean firstTime = true;
        StringBuilder sb = new StringBuilder(50);
        for (int word : array) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(',');
                sb.append(' ');
            }
            sb.append(word);
        }

        String finalResult = sb.toString();
        return finalResult;
    }

    public static void iterate_combinations_All(int[] elems) {
        int n = elems.length;
        for(int num = 0;num < (1 << n);num++) {
            combination = new ArrayList<>();
            for(int ndx = 0;ndx < n;ndx++) {
                if((num & (1 << ndx)) != 0) {
                    combination.add(elems[ndx]);
                }
                /*if (combination.size()==n-1)
                    ListCombins.add(combination);*/
            }
            ListCombinsNewAll.add(combination);
            Collections.sort(ListCombinsNewAll, new Comparator<List<Integer>>() {
                @Override
                public int compare(List<Integer> o1, List<Integer> o2) {
                    return o1.size() - o2.size();
                }

            });
        }
    }

    public static void iterate_combinations(int[] elems) {
        int n = elems.length;
        for(int num = 0;num < (1 << n);num++) {
            List<Integer> combination = new ArrayList<>();
            for(int ndx = 0;ndx < n;ndx++) {
                if((num & (1 << ndx)) != 0) {
                    combination.add(elems[ndx]);
                }
                if (combination.size()==n-1)
                    ListCombinsNew.add(combination);
            }
            //ListCombinsNew.add(combination);
            Collections.sort(ListCombinsNew, new Comparator<List<Integer>>() {
                @Override
                public int compare(List<Integer> o1, List<Integer> o2) {
                    return o1.size() - o2.size();
                }

            });
        }
    }

    public static void FonctionOrdonnacement(int n,int... arrComs){
         CurrentLengh = arrComs.length;
         sommePi=0;
         OrdonnResult=0;

         MinTab= new int[arrComs.length];
         Ti = BigTiFunction(n);
         valUsed=0;
         CountComs=0;


        for (int j=0;j<arrComs.length;j++){
            CountComs++;
        }

        int arrT[]=new int[CountComs];
        int Init=0;

        for (int arrCom : arrComs) {
            if (CurrentLengh==1){
                System.out.print("arcom: "+arrCom);
                OrdonnResult= matGlb[1][arrCom-1]*Ti[arrCom-1];
                HMapGlobalValues.put("f["+arrCom+"]",OrdonnResult);
                ListOrdrOptimal.put("["+arrCom+"]","[]"+Integer.toString(arrCom));
            }else {
                for (int arrComes : arrComs){
                    sommePi+=matGlb[0][arrComes-1];
                }
                for (int j=0;j<arrComs.length;j++){
                    arrT[j]=arrComs[j];
                }

                iterate_combinations(arrT);
                ListCombinsNew.remove(0);
                ListCombinsNew.remove(ListCombinsNew.size()-1);

                Conca=ConcatCombi(arrT);


                for (int kk=0;kk<arrComs.length;kk++){
                    if (!ListCombinsNew.get(kk).contains(arrCom)) {
                        valUsed=arrComs[kk];
                    }
                }


                for (int k=0;k<ListCombinsNew.get(Init).size();k++){
                    OrdonnResult=HMapGlobalValues.get("f"+ListCombinsNew.get(Init))+matGlb[1][valUsed-1]*Math.max(0, sommePi-matGlb[2][valUsed-1]);
                    ListOrdFinal.put(ListCombinsNew.get(Init)+""+valUsed,OrdonnResult);
                }
                MinTab[Init]=OrdonnResult;
                Init++;
                sommePi=0;

                ListOrdrOptimal.put("["+Conca+"]",getKeyFromMinValue(ListOrdFinal,getMinValue(MinTab)));
                ListCombinsNew = new ArrayList<>();
                HMapGlobalValues.put("f["+Conca+"]",getMinValue(MinTab));
            }

        }
        ListOrdFinal=new HashMap<>();
    }

    public static int[] toIntArray(List<Integer> list)  {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e;
        return ret;
    }

    public static int[] createArrayFromNumber(int number) {
        int[] arr = new int[number];
        for (int i=0;i<number;i++)
        {
            arr[i]=i+1;
        }

        return arr;
    }

    public static String RV(String str){
        String[] parts = str.split("-");
        String ss="";
        String GlbStr ="";
        for(int i=0;i<parts.length;i++){
            if (parts[i].length()>1){
                ss=ReverseStr(parts[i]);
                GlbStr=GlbStr+ss+"-";
            }
            else
                GlbStr=GlbStr+parts[i]+"-";
        }
        return GlbStr;
    }
}
