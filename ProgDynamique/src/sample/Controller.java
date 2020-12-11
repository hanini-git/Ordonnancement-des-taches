package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.security.util.Length;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller {
    @FXML
    TableView tabv;
    @FXML
    TextField txtnbtache;
    @FXML
    ProgressBar progbar;
    @FXML
    Label ResMin,ResSeq,LabelprogbarState;
    @FXML
    ScrollPane scrollp;
    @FXML
    Button btnOrdon1,btnGRVNS,btnOrdon2,btnCancel,btnCopyRes,btnShowLog;
    @FXML
    ProgressIndicator indicatorProg;

    public static int sizeTache;
    public static StringBuilder StringLog;

    public void initialize()
    {
       // indicatorProg.setVisible(true);

        txtnbtache.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtnbtache.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    //Generer des aleatroiement
    public void CallGenerate() {
        if (txtnbtache.getText() == null || txtnbtache.getText().trim().isEmpty()) {
System.out.println("empty");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Programmation Dynamique...");
            alert.setHeaderText("Nombre de Taches");
            alert.setContentText("Vous devez enter un nombre de taches");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else{
            StringLog=new StringBuilder();
        GetSize();
        System.out.println("hi");
            scrollp.setVisible(false);
            btnCopyRes.setVisible(false);
            btnShowLog.setVisible(false);

            //CsvWritter.writeCsvFile("hihi.txt");
            System.out.println(sizeTache);

            Taches.Generate(sizeTache);
        Taches.ShowTaches();
            Taches.GetTachesMethod2();
            Taches.ShowTaches2();

                    File dir=new File("TachesGenerees");

           if(!dir.exists())
           {
               dir.mkdir();
           }            //CsvWritter.writeCsvFile("hihi.txt");
            String file="TachesGenerees/"+sizeTache+" Taches (id-"+String.valueOf(System.currentTimeMillis())+").txt";
//           String path ="TachesGenerees/"+file;
        CsvWritter.writeCsvFile(file);
        tabv.setItems(CsvFileReader.readCsvFile(file));
            ButtonType open =new ButtonType("Ouvrir le fichier genere",ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType ok =new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);

            Alert alert = new Alert(Alert.AlertType.INFORMATION,"",open,ok);
            alert.setTitle("Programmation Dynamique...");
            alert.setHeaderText("Generation Fichier");
            alert.setContentText("Fichier Genere sous nom : \n"+file);
            File d=new File(file);
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ok) {
                    System.out.println("Pressed OK.");
                }
                if (rs == open) {
                    System.out.println("Open OK.");
                    try {
                       // Runtime.getRuntime().exec("open "+file);
                        java.awt.Desktop.getDesktop().open(d);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            btnOrdon1.setDisable(false);
                    btnGRVNS.setDisable(false);
                    btnOrdon2.setDisable(false);
        }


    }

    //Avoir le nombre de taches generes actuel
    public void GetSize()
    {
        sizeTache=Integer.parseInt(txtnbtache.getText());
    }




    //Methode ordonnancement 1
    public void CallOrdonM1()

    {
        Task<Void> taskOrdonM1 = new Task<Void>() {

            @Override
            protected Void call() throws Exception {


                int i=1;
                StringLog.append(Taches.Factorial(sizeTache) + " Solutions");

                Ordon.GetArray(sizeTache);
                do {
                    //System.err.println(Arrays.toString(data));
                    Ordon.CalculateWithArrayMethod(Ordon.data);
                    StringLog.append(System.lineSeparator());
                    StringLog.append("Solution Realisable "+i);
                    StringLog.append(System.lineSeparator());
                    StringLog.append("F ( "+Arrays.toString(Ordon.data)+" ) = "+Ordon.F);
                    StringLog.append(System.lineSeparator());
                    i++;
                } while (Ordon.permuteLexically(Ordon.data));
                System.out.println("min optimal: " + Ordon.MinOptimal);
                System.out.println("min seauence : " + Arrays.toString(Ordon.Ordonarray));
                //String res= "La Valeur Min optimale : "+Ordon.MinOptimal+"\n La séquence Min optimale : "+Arrays.toString(Ordon.Ordonarray);
                scrollp.setVisible(true);
                btnCopyRes.setVisible(true);
                btnShowLog.setVisible(true);

                indicatorProg.setVisible(false);
                btnCancel.setVisible(false);


                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ResMin.setText("La Valeur Min optimale : " + Ordon.MinOptimal);
                        ResSeq.setText("La séquence Min optimale : " + Arrays.toString(Ordon.Ordonarray));

                    }
                });



                return null;
            }

        };




        if (sizeTache>20 || sizeTache<2) {
            System.out.println("empty");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Programmation Dynamique...");
            alert.setHeaderText("Nombre de Taches");
            alert.setContentText("Vous devez enter un nombre de taches moins de 20");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else {


            StringLog=new StringBuilder();

            indicatorProg.setVisible(true);
            btnCancel.setVisible(true);

            System.out.println(Taches.Factorial(sizeTache) + " Solutions");

            Ordonthread=new Thread(taskOrdonM1);
            Ordonthread.start();


        }



    }


    //Importer un fichier de donnees text deja exisitant
    public void Browse()
    {

        StringLog=new StringBuilder();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(

                new FileChooser.ExtensionFilter("Txt Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);



        if (selectedFile != null) {

            scrollp.setVisible(false);

            btnCopyRes.setVisible(false);
            btnShowLog.setVisible(false);

            System.out.println("File selected: " + selectedFile.getName());
            System.out.println("File selected path: " +  selectedFile.getAbsolutePath());
            tabv.setItems(CsvFileReader.readCsvFile(selectedFile.getAbsolutePath()));
            sizeTache=tabv.getItems().size();
            btnOrdon1.setDisable(false);
            btnGRVNS.setDisable(false);
            btnOrdon2.setDisable(false);
            txtnbtache.setText(String.valueOf(sizeTache));


        }

          else {

            System.out.print("File selection cancelled.");

    }



    }


    //MEthode GeneralVNS (Amelioration Avec RVNS + GEneral VNS avec VND comme REcherche Locale)
    public void GeneralVNS()
    {


        Task<Void> taskGeneralVNS = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(progbar.progressProperty(), 0)),
                        new KeyFrame(Duration.seconds(18), e-> {
                            // do anything you need here on completion...
                            ResMin.setText("La Valeur Min optimale du meilleur voisin: "+VNS.FGlobal);
                            ResSeq.setText("La séquence Min du meilleur voisin : "+Arrays.toString(VNS.GlobalVoisinageArray));
                            System.out.println("10 Seconds over General VNS");
                            System.out.println("Voisinage Global General");
                            System.out.println("Global array "+Arrays.toString(VNS.GlobalVoisinageArray));
                            System.out.println("Voisinage Global :  "+VNS.FGlobal);
                            progbar.setVisible(false);
                            scrollp.setVisible(true);
                            btnCopyRes.setVisible(true);
                            btnShowLog.setVisible(true);

                            LabelprogbarState.setVisible(false);

                        }, new KeyValue(progbar.progressProperty(), 1))
                );
                timeline.setCycleCount(1);
                timeline.play();

                int i=1;
                while (System.currentTimeMillis()<VNS.end)
                {   //m += 0.1;
                    // progbar.setProgress(m);

                    VNS.GeneralVNS();
                    //System.out.println("Global array "+Arrays.toString(VNS.GlobalVoisinageArray));
                    //System.out.println("Voisinage Global :  "+VNS.FGlobal);
                    StringLog.append("Iteration "+i);
                    StringLog.append(System.lineSeparator());
                    StringLog.append("La voisin minimale actuelle : "+Arrays.toString(VNS.GlobalVoisinageArray));
                    StringLog.append(System.lineSeparator());
                    StringLog.append("La valeur du Voisin : "+VNS.FGlobal);
                    StringLog.append(System.lineSeparator());

                    i++;

                 /*   Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            ResMin.setText("La Valeur Min optimale du meilleur voisin: "+VNS.FGlobal);
                            ResSeq.setText("La séquence Min du meilleur voisin : "+Arrays.toString(VNS.GlobalVoisinageArray));
                        }
                    });*/





                    //m++;
                }




                return null;
            }

        };
        Task<Void> taskReducedVNS = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                // double max = 137;
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(progbar.progressProperty(), 0)),
                        new KeyFrame(Duration.seconds(10), e-> {

                            // do anything you need here on completion...
                            ResMin.setText("La Valeur Min optimale du meilleur voisin: "+VNS.FGlobal);
                            ResSeq.setText("La séquence Min du meilleur voisin : "+Arrays.toString(VNS.GlobalVoisinageArray));
                            System.out.println("10 Seconds over Reduced VNS");
                            LabelprogbarState.setText("Etape 2: General VNS (GVNS)");
                            StringLog.append(System.lineSeparator());
                            StringLog.append(System.lineSeparator());
                            StringLog.append("Etape 2: General VNS (GVNS)");
                            StringLog.append(System.lineSeparator());

                            VNS.start=System.currentTimeMillis();
                            VNS.end=VNS.start+15*1000; //10 seconds VNS
                            new Thread(taskGeneralVNS).start();

                            System.out.println("Voisinage Global Reduced");
                            System.out.println("Global array "+Arrays.toString(VNS.GlobalVoisinageArray));
                            System.out.println("Voisinage Global :  "+VNS.FGlobal);
                        }, new KeyValue(progbar.progressProperty(), 1))
                );
                timeline.setCycleCount(1);
                timeline.play();
                int i=1;
                while (System.currentTimeMillis()<VNS.end)
                {   //m += 0.1;
                    // progbar.setProgress(m);

                    VNS.ReducedVNS();
                    //System.out.println("Global array "+Arrays.toString(VNS.GlobalVoisinageArray));
                    //System.out.println("Voisinage Global :  "+VNS.FGlobal);
                    StringLog.append("Iteration "+i);
                    StringLog.append(System.lineSeparator());
                    StringLog.append("La voisin minimale actuelle : "+Arrays.toString(VNS.GlobalVoisinageArray));
                    StringLog.append(System.lineSeparator());
                    StringLog.append("La valeur du Voisin : "+VNS.FGlobal);
                    StringLog.append(System.lineSeparator());






                    i++;


                }





                return null;
            }

        };

        StringLog=new StringBuilder();

        VNS.Initialize(sizeTache);
        progbar.setVisible(true);
        scrollp.setVisible(false);
        btnCopyRes.setVisible(false);
        btnShowLog.setVisible(false);

        LabelprogbarState.setVisible(true);
        LabelprogbarState.setText("Etape 1: Amelioration de X avec Reduced VNS");

        StringLog.append("Etape 1: Amelioration de X avec Reduced VNS");
        StringLog.append(System.lineSeparator());
        StringLog.append(System.lineSeparator());

        new Thread(taskReducedVNS).start();
        //new Thread(taskGeneralVNS).start();





        //m++;
        //}
        //ResMin.setText("La Valeur Min optimale du meilleur voisin: "+VNS.FGlobal);
        //ResSeq.setText("La séquence Min du meilleur voisin : "+Arrays.toString(VNS.GlobalVoisinageArray));
        //System.out.println("Global array "+Arrays.toString(VNS.GlobalVoisinageArray));
        //System.out.println("Voisinage Global :  "+VNS.FGlobal);

    }

    //Apropos de notre application
    public void CallInfo()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Programmation Dynamique...");
        alert.setHeaderText("Application d'Ordonnancement et Recherche a voisinage variable generale ");
        alert.setContentText("Mohamed Hanini | Khalil Moutafatin | MASTER M2SI | INSEA ");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }
    Thread Ordonthread;


    //Methode Ordonnacement 2
    public void CallOrdonM2()
    {
        Task<Void> taskOrdonM2 = new Task<Void>() {

            @Override
            protected Void call() throws Exception {



                int arry[] = OrdonM2.createArrayFromNumber(sizeTache);
                OrdonM2.iterate_combinations_All(arry);
                OrdonM2.ListCombinsNewAll.remove(0);
                for (int i=0;i<OrdonM2.ListCombinsNewAll.size();i++){
                    OrdonM2.FonctionOrdonnacement(sizeTache,OrdonM2.toIntArray(OrdonM2.ListCombinsNewAll.get(i)));
                }
                System.out.println("DONE");
                System.out.println("Minimum Valeur : "+OrdonM2.HMapGlobalValues.get("f"+OrdonM2.MaxKey(OrdonM2.ListOrdrOptimal)));
                System.out.println();
                String v = OrdonM2.FuncOrdreFinal(OrdonM2.ListOrdrOptimal);
                String Rv= OrdonM2.ReverseStr(v);
                System.out.println("Ordre Optimal = "+ OrdonM2.RV(OrdonM2.ReverseStr(OrdonM2.FuncOrdreFinal(OrdonM2.ListOrdrOptimal))));
                System.out.println("done m2");

              /*  Set set = OrdonM2.ListOrdrOptimal.entrySet();
                Iterator iterator = set.iterator();
                while(iterator.hasNext()) {
                    Map.Entry mentry = (Map.Entry) iterator.next();
                    System.out.print("key is: " + mentry.getKey() + " & Value is: ");
                    System.out.println(mentry.getValue());
                }*/
                TreeMap<String, Integer> sorted = new TreeMap<>(OrdonM2.HMapGlobalValues);
                Set<Map.Entry<String, Integer>> mappings = sorted.entrySet();

                Map<String, String> treeMap = new TreeMap<String, String>(
                        new Comparator<String>() {
                            @Override
                            public int compare(String s1, String s2) {
                                if (s1.length() < s2.length()) {
                                    return -1;
                                } else if (s1.length() > s2.length()) {
                                    return 1;
                                } else {
                                    return s1.compareTo(s2);
                                }
                            }
                        });

                treeMap.putAll(OrdonM2.ListOrdrOptimal);
                int i=2;
                int prev=10;
                Set set = treeMap.entrySet();
                Iterator iterator = set.iterator();
                System.out.println("Etape 1");
                StringLog.append("Etape 1");
                StringLog.append(System.lineSeparator());
                StringLog.append(System.lineSeparator());
                while(iterator.hasNext()) {
                    Map.Entry mentry = (Map.Entry)iterator.next();
                    if(mentry.getKey().toString().length()==prev+3 || mentry.getKey().toString().length()==prev+2)
                    { System.out.println("Etape "+i);
                        StringLog.append(System.lineSeparator());
                        StringLog.append(System.lineSeparator());
                    StringLog.append("Etape "+i);
                        StringLog.append(System.lineSeparator());
                        StringLog.append(System.lineSeparator());
                        i++;

                    }
                    System.out.print("f"+ mentry.getKey()+" = ");
                    StringLog.append("f"+ mentry.getKey()+" = ");

                    prev=mentry.getKey().toString().length();

                    for(Map.Entry<String, Integer> mapping : mappings){
                        if (mapping.getKey().equals("f"+mentry.getKey()))

                        { System.out.println(mapping.getValue());
                            StringLog.append(mapping.getValue());
                            StringLog.append(System.lineSeparator());}

                    }
                }
                scrollp.setVisible(true);
                btnCopyRes.setVisible(true);
                btnShowLog.setVisible(true);

                indicatorProg.setVisible(false);
                btnCancel.setVisible(false);



                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ResMin.setText("La Valeur Min optimale : " + OrdonM2.HMapGlobalValues.get("f" + OrdonM2.MaxKey(OrdonM2.ListOrdrOptimal)));
                        ResSeq.setText("La séquence Min optimale : " + OrdonM2.RV(OrdonM2.ReverseStr(OrdonM2.FuncOrdreFinal(OrdonM2.ListOrdrOptimal))));

                    }
                });



                return null;
            }

        };






        if (sizeTache>20 || sizeTache<2) {
            System.out.println("empty");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Programmation Dynamique...");
            alert.setHeaderText("Nombre de Taches");
            alert.setContentText("Vous devez enter un nombre de taches moins de 20");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else {
            StringLog=new StringBuilder();
            btnCancel.setVisible(true);
            indicatorProg.setVisible(true);
            OrdonM2.Initialise();
            Taches.GetTachesMethod2();
            Ordonthread = new Thread(taskOrdonM2);
            Ordonthread.start();

        }



    }

    //Annuler l'execution d'un ordonnancement
    public void CancelOrdon()
    {
        Ordonthread.stop();
        System.out.println("interroped");
        btnCancel.setVisible(false);
        indicatorProg.setVisible(false);


    }

    //Copieage du Resultat dans le presse-papier
    public void CopyResultat()
    {
        String res = ResMin.getText()+" \n"+ResSeq.getText();
        StringSelection stringSelection = new StringSelection(res);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Programmation Dynamique...");
        alert.setHeaderText("Resultat");
        alert.setContentText("Le Résultat est copié dans le presse-papier avec succés");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });


    }

    //Affichage du Journal d'execution
    public void ShowLog()
    {
        Path p=null;
        try {
            p=Files.createTempFile("Logs",".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //File file = new File("Logs/".txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(p.toFile());
            fr.write(String.valueOf(StringLog));
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // Runtime.getRuntime().exec("open "+file);
            java.awt.Desktop.getDesktop().open(p.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }






    }
}

