import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.List;

public class UpdateStatusCSVProcess {
    public static void main(String args[]) {
        ProcessStatusToNextStatusTest("Process acquirer response", "Cardholder liable");
    }


    static void ProcessStatusToNextStatusTest(String lastStatus, String newStatus) {

        CSVReader csvFromName = null;
        CSVReader csvToName = null;
        CSVWriter writeFrom = null;
        CSVWriter writeTo = null;

        String fromCsvName = "";
        String toCsvName = "";

        try {

            if(lastStatus.equals("Process acquirer response") && newStatus.equals("Cardholder liable")) {
                fromCsvName = "C:\\Users\\pavlo\\JavaProjects\\ProcessCSVFiles\\src\\test\\resources\\ProcessAcquirerResponse.csv";
                toCsvName = "C:\\Users\\pavlo\\JavaProjects\\ProcessCSVFiles\\src\\test\\resources\\CardholderLiabl.csv";

                File fromFile = new File(fromCsvName);
                csvFromName = new CSVReader(new FileReader(fromFile));

                List <String[]> csvFromRows = csvFromName.readAll();
                csvFromName.close();



                File toFile = new File(toCsvName);
                csvToName = new CSVReader(new FileReader(toFile));

                List <String[]> csvToRows = csvToName.readAll();
                csvToName.close();


                String disputeNumber = "D-19052";

                // At this point all of the rows have been read.
                // Below lines creates the new csvFromName and csvToName so rows not yet ready to process will be written back.
                writeFrom = new CSVWriter(new FileWriter(fromCsvName, false));

                writeTo = new CSVWriter(new FileWriter(toCsvName, true));


                // Method: Use a for loop to walk through Process Acquirer Response list
                // and if string at column 1 is equal to dispute number then add to Cardholder Liable list
                // then remove row from Process Acquirer Response List.
                if(csvFromRows.size() != 0) {
                    for(int i = 0; i < csvFromRows.size(); i++) {
                        if(csvFromRows.get(i)[1].equals(disputeNumber)) {
                            //csvToRows.add(disputeNumber);
                            csvFromRows.remove(i);
                        }
                    }

                    System.out.println(csvFromRows);

                    // Add Cardholder Liable list (in memory) to CardholderLiable.csv
                    writeTo.writeAll(csvToRows);
                    writeTo.flush();
                    writeTo.close();

                    // Add Process Acquirer Response list (in memory) to ProcessAcquirerResponse.csv
                    writeFrom.writeAll(csvToRows);
                    writeFrom.flush();
                    writeFrom.close();

                }

            }

        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

