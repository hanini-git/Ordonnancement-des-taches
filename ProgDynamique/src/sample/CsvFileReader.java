package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFileReader {



    private static final String COMMA_DELIMITER = ",";

    private static final int Tache_ID_IDX = 0;

    private static final int Weight_IDX = 2;

    private static final int DueDate_IDX = 3;

    private static final int Time_IDX = 1;

    public static ObservableList<Taches> readCsvFile(String fileName) {

        BufferedReader fileReader = null;

        try {

            ObservableList<Taches> taches = FXCollections.observableArrayList();
            String line = "";

            //Create the file reader

            fileReader = new BufferedReader(new FileReader(fileName));

            //Read the CSV file header to skip it

            fileReader.readLine();

            //Read the file line by line starting from the second line

            while ((line = fileReader.readLine()) != null) {

                //Get all tokens available in line

                String[] tokens = line.split(COMMA_DELIMITER);

                if (tokens.length > 0) {

                    //Create a new student object and fill his  data

                    Taches tache = new Taches(Integer.parseInt(tokens[Tache_ID_IDX]), Integer.parseInt(tokens[Time_IDX]), Integer.parseInt(tokens[Weight_IDX]), Integer.parseInt(tokens[DueDate_IDX]));

                    taches.add(tache);

                }

            }


            Taches.taches = new int[taches.size() + 1][3];
            for(int i=1;i<Taches.taches.length;i++) {
                System.out.println(taches.get(i-1).getDuree()+" size:"+taches.size());

               // System.out.println("Duree "+i+" : "+] = "+Taches.taches[i][j]);
                Taches.taches[i][Taches.Duree] = taches.get(i-1).getDuree();
                Taches.taches[i][Taches.Poid] = taches.get(i-1).getPoids();
                Taches.taches[i][Taches.DateLiv] = taches.get(i-1).getDateliv();

            }
            for(int i=1;i<Taches.taches.length;i++) {
                for(int j=0;j<Taches.taches[i].length;j++) {

                    System.out.print("Taches.taches["+i+"]["+j+"] = "+Taches.taches[i][j]);
                    System.out.println("");
                }
            }

            return taches;


        }

        catch (Exception e) {

            System.out.println("Error in CsvFileReader !!!");

            e.printStackTrace();

        } finally {

            try {

                fileReader.close();

            } catch (IOException e) {

                System.out.println("Error while closing fileReader !!!");

                e.printStackTrace();

            }

        }
        return null;

    }



}
