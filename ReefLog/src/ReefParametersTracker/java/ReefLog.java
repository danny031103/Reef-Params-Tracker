package ReefParametersTracker.java;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

//JFreeChart imports
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;


public class ReefLog {
    //csv file to keep logs of the data
    private static final String ReefParams_file = "/Users/daniel.brito/Desktop/Random Code/ReefLog/data/ReefParams.csv";
    private static final String DATA_FOLDER = "data/";

    public static void main(String[]args){
        int choicee=0;
        while(choicee!=5){
        Scanner scn=new Scanner(System.in);
        System.out.println();
        System.out.println("Welcome to ReefLog!");
        System.out.println("What would you like to do:\n (1)Add New Log.\n (2)Fetch Parameters for Date.\n (3)Display All Logs.\n (4)Wipe Logs.\n (5)Quit.\n (6)Display Temperature Graph.\n (7)Display pH Graph.\n (8)Display Alkalinity Graph.\n (9)Display Ammonia Graph.\n (10)Display Nitrite Graph.\n (11)Display Nitrate Graph.\n (12)Display Phosphate Graph.\n (13)Display Calcium Graph.\n (14)Display Salinity Graph.");
        int choice=scn.nextInt();

        if(choice==1){
            choicee=1;
            //add logs until program is quit
            while(true){
                Scanner scnn=new Scanner(System.in);
                System.out.println("---------New Log---------");

                //date
                System.out.println("Date (MM-DD-YYYY):");
                String date=scnn.nextLine();
                
                //temperature
                System.out.println("Temperature (F):");
                double temperature=scnn.nextDouble();
                
                //Ammonia
                System.out.println("Ammonia (ppm):");
                double ammonia=scnn.nextDouble();
                

                System.out.println("Nitrite (ppm):");
                double nitrite=scnn.nextDouble();
                
                System.out.println("Nitrate (ppm):");
                double nitrate=scnn.nextDouble();
                
                System.out.println("pH:");
                double pH=scnn.nextDouble();
                
                System.out.println("Alkalinity (dKH):");
                double alkalinity=scnn.nextDouble();
                
                System.out.println("Phosphate (ppm):");
                double phosphate=scnn.nextDouble();

                System.out.println("Calcium (ppm):");
                double calcium=scnn.nextDouble();
                
                System.out.println("Salinity (ppt):");
                double salinity=scnn.nextDouble();
                
                //Save data
                AquariumLogEntry daylog = new AquariumLogEntry(date, temperature, ammonia, nitrite, nitrate, pH, alkalinity, phosphate, calcium, salinity);
                saveLog(daylog);

                //Add another?
                System.out.println("Log added!");
                Scanner scn2=new Scanner(System.in);
                System.out.println("Add another log? (Y/N)");
                String addother=scn2.nextLine().toUpperCase();
                //Loop breaker
                if(addother.equals("Y")||addother.equals("YES")){
                    continue;
                }
                else{
                    System.out.println("Closing ReefLog...");
                    break;
                }
            }
        }
        else if(choice==2){
            choicee=2;
            fetchParametersForDate(scn);
        }
        else if(choice==3){
            choicee=3;
            displayAllLogs();
        }
        else if(choice==4){
            choicee=4;
            wipeLogs();
        }
        else if(choice==5){
            choicee=5;
            System.out.println("Closing ReefLog...");
            break;
        }
        else if(choice==6){
            choicee=6;
            List<AquariumLogEntry> logEntries = displayAllLogs();
            displayTemperatureGraph(logEntries);
        }
        else if(choice==7){
            choicee=7;
            List<AquariumLogEntry> logEntries = displayAllLogs();
            displaypHGraph(logEntries);
        }
        else if(choice==8){
            choicee=8;
            List<AquariumLogEntry> logEntries = displayAllLogs();
            displayAlkalinityGraph(logEntries);
        }
        else if(choice==9){
            choicee=9;
            List<AquariumLogEntry> logEntries = displayAllLogs();
            displayAmmoniaGraph(logEntries);
        }
        else if(choice==10){
            choicee=10;
            List<AquariumLogEntry> logEntries = displayAllLogs();
            displayNitriteGraph(logEntries);
        }
        else if(choice==11){
            choicee=11;
            List<AquariumLogEntry> logEntries = displayAllLogs();
            displayNitrateGraph(logEntries);
        }
        else if(choice==12){
            choicee=12;
            List<AquariumLogEntry> logEntries = displayAllLogs();
            displayPhosphateGraph(logEntries);
        }
        else if(choice==13){
            choicee=13;
            List<AquariumLogEntry> logEntries = displayAllLogs();
            displayCalciumGraph(logEntries);
        }
        else if(choice==14){
            choicee=14;
            List<AquariumLogEntry> logEntries = displayAllLogs();
            displaySalinityGraph(logEntries);
        }
        else{
            System.out.println("Invalid choice.");
        }
    }
}
        private static List<AquariumLogEntry> displayAllLogs() {
                List<AquariumLogEntry> logEntries = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(ReefParams_file))) {
                    String line;
                    while ((line = reader.readLine())!=null) {
                        String[] paramsplit = line.split(",");
                        if (paramsplit.length==10) { 
                            System.out.println("---------------------------------------");
                            System.out.println("Parameters for " + paramsplit[0] + ":");
                            System.out.println("Temperature: " + paramsplit[1] + "°C");
                            System.out.println("Ammonia: " + paramsplit[2] + " ppm");
                            System.out.println("Nitrate: " + paramsplit[3] + " ppm");
                            System.out.println("Nitrite: " + paramsplit[4] + " ppm");
                            System.out.println("Alkalinity: " + paramsplit[5] + " dKH");
                            System.out.println("Phosphates: " + paramsplit[6] + " ppm");
                            System.out.println("Calcium: " + paramsplit[7] + " ppm");
                            System.out.println("Salinity: " + paramsplit[8] + " ppt");
                            System.out.println("pH: " + paramsplit[9]);
                            System.out.println("---------------------------------------");
                            System.out.println();

                            // Create AquariumLogEntry object and add it to the list
                            AquariumLogEntry logEntry = new AquariumLogEntry(
                                    paramsplit[0], // Date
                                    Double.parseDouble(paramsplit[1]), // Temperature
                                    Double.parseDouble(paramsplit[2]), // Ammonia
                                    Double.parseDouble(paramsplit[3]), // Nitrate
                                    Double.parseDouble(paramsplit[4]), // Nitrite
                                    Double.parseDouble(paramsplit[5]), // Alkalinity
                                    Double.parseDouble(paramsplit[6]), // Phosphate
                                    Double.parseDouble(paramsplit[7]), // Calcium
                                    Double.parseDouble(paramsplit[8]), // Salinity
                                    Double.parseDouble(paramsplit[9])  // pH
                            );
                            logEntries.add(logEntry);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return logEntries;
            }
        
        private static void saveLog(AquariumLogEntry log) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ReefParams_file, true))) {
                String line = log.getDate() + "," + log.getTemperature() + "," + log.getAmmonia() + "," +
                log.getNitrate() + "," + log.getNitrite() + "," + log.getAlkalinity() + "," + log.getPhosphate();
                writer.write(line);
                writer.newLine();
            } 
            catch (IOException e){
                e.printStackTrace();
            }
            // Move the CSV file to the data folder
            File sourceFile = new File(ReefParams_file);
            File destinationFolder = new File(DATA_FOLDER);

            try {
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs();
                }

                Path destinationPath = destinationFolder.toPath().resolve(sourceFile.getName());
                Files.move(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("CSV file has been moved to the data folder.");
            } 
            catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to move the CSV file to the data folder.");
            }
        }

        //Parameters for date
        private static void fetchParametersForDate(Scanner scanner) {
            System.out.print("Enter the date (MM-DD-YYYY) to fetch parameters: ");
            String dateToFetch=scanner.nextLine();
            try (BufferedReader reader=new BufferedReader(new FileReader(ReefParams_file))) {
                String line;
                boolean found=false;
                while((line=reader.readLine())!=null) {
                        String[] paramsplit=line.split(",");
                        if (paramsplit.length==7&&paramsplit[0].equals(dateToFetch)) {
                            System.out.println("Parameters for " + dateToFetch + ":");
                            System.out.println("Temperature: " + paramsplit[1] + "°C");
                            System.out.println("Ammonia: " + paramsplit[2] + " ppm");
                            System.out.println("Nitrate: " + paramsplit[3] + " ppm");
                            System.out.println("Nitrite: " + paramsplit[4] + " ppm");
                            System.out.println("Alkalinity: " + paramsplit[5] + " dKH");
                            System.out.println("Phosphates: " + paramsplit[6] + " ppm");
                            found=true;
                            break;  
                        }
                }
                if (!found) {
                    System.out.println("No data found for the specified date.");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
    public static void wipeLogs() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure you want to wipe all logs? (Y/N)");
        String wipe = scanner.nextLine();
        
        if (wipe.equalsIgnoreCase("Y")) {
            String csvFileName = "ReefParams.csv";
            String csvFilePath = "data/" + csvFileName;
            File csvFile = new File(csvFilePath);
    
            if (csvFile.exists()) {
                if (csvFile.delete()) {
                    System.out.println("The log file has been wiped (deleted).");
                } else {
                    System.out.println("Failed to wipe the log file.");
                }
            } else {
                System.out.println("The log file doesn't exist.");
            }
        }
        else {
            System.out.println("Cancelling wipe...");
        }
    }
     
        //Graphs
        public static void displayGraph(List<AquariumLogEntry> logEntries, String parameterName, String yAxisLabel) {
            TimeSeries series = new TimeSeries(parameterName);
        
            for (AquariumLogEntry entry : logEntries) {
                String dateStr = entry.getDate();
                int year = Integer.parseInt(dateStr.substring(6));
                int month = Integer.parseInt(dateStr.substring(0, 2));
                int day = Integer.parseInt(dateStr.substring(3, 5));
        
                //Day
                Day dayObj = new Day(day, month, year);
        
                double paramValue = entry.getParameterValue(parameterName);
        
                // Print debug information
                System.out.println("Date: " + dayObj);
                System.out.println("Parameter Value: " + paramValue);
        
                series.addOrUpdate(dayObj, paramValue);
            }
    
            TimeSeriesCollection dataset = new TimeSeriesCollection(series);
        
            JFreeChart chart = ChartFactory.createTimeSeriesChart(
                    "Parameter Trends Over Time", 
                    "Date",
                    yAxisLabel, 
                    dataset,
                    false, 
                    true, 
                    false 
            );
        
            XYPlot plot = (XYPlot) chart.getPlot();
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesPaint(0, Color.BLUE);
            plot.setBackgroundPaint(Color.LIGHT_GRAY);
            plot.setBackgroundImage(null);
            plot.setDomainGridlinePaint(Color.DARK_GRAY);
            plot.setRangeGridlinePaint(Color.DARK_GRAY);
            plot.setRenderer(renderer);
        
            displayChart(chart);
        }

        public static void displayChart(JFreeChart chart) {
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(1000, 800));
    
            JFrame frame = new JFrame("ReefLog Chart");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        public static void displayAmmoniaGraph(List<AquariumLogEntry> logEntries) {
            displayGraph(logEntries, "Ammonia", "Ammonia (ppm)");
        }

        public static void displayNitriteGraph(List<AquariumLogEntry> logEntries) {
            displayGraph(logEntries, "Nitrite", "Nitrite (ppm)");
        }

        public static void displayNitrateGraph(List<AquariumLogEntry> logEntries) {
            displayGraph(logEntries, "Nitrate", "Nitrate (ppm)");
        }

        public static void displaypHGraph(List<AquariumLogEntry> logEntries) {
            displayGraph(logEntries, "pH", "pH");
        }

        public static void displayAlkalinityGraph(List<AquariumLogEntry> logEntries) {
            displayGraph(logEntries, "Alkalinity", "Alkalinity (dKH ppm)");
        }

        public static void displayPhosphateGraph(List<AquariumLogEntry> logEntries) {
            displayGraph(logEntries, "Phosphate", "Phosphate (ppm)");
        }

        public static void displayCalciumGraph(List<AquariumLogEntry> logEntries) {
            displayGraph(logEntries, "Calcium", "Calcium (ppm)");
        }

        public static void displaySalinityGraph(List<AquariumLogEntry> logEntries) {
            displayGraph(logEntries, "Salinity", "Salinity (ppt)");
        }
        public static void displayTemperatureGraph(List<AquariumLogEntry> logEntries) {
            displayGraph(logEntries, "Temperature", "Temperature (F)");
        }
}

