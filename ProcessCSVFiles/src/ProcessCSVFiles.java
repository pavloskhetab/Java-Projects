import com.opencsv.*;
import java.io.*;
import java.util.List;

public class UpdateStatusCSVProcess {
    public static void main(String args[]) {
        ProcessStatusToNextStatusTest("Process acquirer response", "Cardholder liable");
    }


    static void ProcessStatusToNextStatusTest(String lastStatus, String newStatus) {
        
        // Here we declare variable that will be used in our method.

        String fromCsvName = "";
        String toCsvName = "";

        CSVReader csvFromName = null;
        CSVReader csvToName = null;
        
        CSVWriter writeFrom = null;
        CSVWriter writeTo = null;


        try {

            if(lastStatus.equals("Process acquirer response") && newStatus.equals("Cardholder liable")) {

                // Initialise variables for path names of two csv files.
                fromCsvName = "C:\\Users\\pavlo\\JavaProjects\\ProcessCSVFiles\\src\\test\\resources\\ProcessAcquirerResponse.csv";
                toCsvName = "C:\\Users\\pavlo\\JavaProjects\\ProcessCSVFiles\\src\\test\\resources\\CardholderLiabl.csv";

                // Create two objects of FileReader class
                // one for Process Acquirer Response and one for Cardholder Liable;
                // with CSV file as parameter.
                FileReader readFromFile = new FileReader(fromCsvName);
                FileReader readToFile = new FileReader(toCsvName);

                // Create two CSVReader objects
                // one for Process Acquirer Response and one for Cardholder Liable;
                // which pass FileReader as parameter.
                csvFromName = new CSVReader(readFromFile);
                csvToName = new CSVReader(readToFile);


                // Create two array lists of type string and read all data
                // from Process Acquirer Response and from Cardholder Liable
                // then close the files.
                List <String[]> csvFromRows = csvFromName.readAll();
                csvFromName.close();

                List <String[]> csvToRows = csvToName.readAll();
                csvToName.close();
            
                // Create two objects of CSVWriter class
                // that will have a FileWriter class as a parameter.
                // This FileWriter class will contain
                // a CSV File as a parameter and set append to false.
                writeFrom = new CSVWriter(new FileWriter(fromCsvName, false));
                writeTo = new CSVWriter(new FileWriter(toCsvName, false));
                
                // Use dispute number to search in Process Acquirer Response list.
                String disputeNumber = "D-19052"; 

                 // Use the full dispute number to add it to our array list of type String.
                String[] fullDisputeNumber = "D-19052,,".split(",");

                // Method: Use a for loop to walk through Process Acquirer Response list in memory
                // and if string in the first column is equal to dispute number then add to Cardholder Liable list
                // then remove row from Process Acquirer Response List.
                if(csvFromRows.size() != 0) {
                    for(int i = 0; i < csvFromRows.size(); i++) {

                        // Checks if the first column in a row is equal to dipute number.
                        if(csvFromRows.get(i)[0].equals(disputeNumber)) {

                            // If dipute number found then we add full dispute number into Cardholder Liable list in memory,
                            csvToRows.add(disputeNumber); 

                            // and remove the row in Process Acquirer Response list in memory.
                            csvFromRows.remove(i);
                        }
                    }

                    System.out.println(csvFromRows);

                    // Add Cardholder Liable list to CardholderLiable.csv.
                    writeTo.writeAll(csvToRows);
                    writeTo.flush();
                    writeTo.close();

                    // Add Process Acquirer Response list to ProcessAcquirerResponse.csv.
                    writeFrom.writeAll(csvFromRows);
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