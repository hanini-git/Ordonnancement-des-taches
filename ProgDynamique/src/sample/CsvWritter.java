package sample;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvWritter {
    private static final String COMMA_DELIMITER = ",";

    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String FILE_HEADER = "Tache,Duree,Poid,DateLiv";

    public static void writeCsvFile(String fileName) {


        FileWriter fileWriter = null;



        try {

            fileWriter = new FileWriter(fileName);


            fileWriter.append(FILE_HEADER.toString());


            fileWriter.append(NEW_LINE_SEPARATOR);

            for(int i=1;i<Taches.taches.length;i++) {


                fileWriter.append(String.valueOf(i));

                fileWriter.append(COMMA_DELIMITER);

                fileWriter.append(String.valueOf(Taches.taches[i][Taches.Duree]));

                fileWriter.append(COMMA_DELIMITER);

                fileWriter.append(String.valueOf(Taches.taches[i][Taches.Poid]));

                fileWriter.append(COMMA_DELIMITER);

                fileWriter.append(String.valueOf(Taches.taches[i][Taches.DateLiv]));

                fileWriter.append(NEW_LINE_SEPARATOR);

            }
            System.out.println("CSV file was created successfully !!!");



        }
        catch (Exception e) {

            System.out.println("Error in CsvFileWriter !!!");

            e.printStackTrace();

        }
        finally
        {

            try {

                fileWriter.flush();

                fileWriter.close();

            } catch (IOException e) {

                System.out.println("Error while flushing/closing fileWriter !!!");

                e.printStackTrace();

            }



        }



    }
}
