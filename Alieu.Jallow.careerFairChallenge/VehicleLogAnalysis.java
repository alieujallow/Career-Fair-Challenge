/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class contains the variance, standard deviation, mean and calculate distance methods
 * @author Alieu Jallow
 */
public class VehicleLogAnalysis {

    //instance variable
    String vehicleMovement;

    /**
     * constructor
     *
     * @param vehicleMovementFile the vehicle movement log file
     */
    public VehicleLogAnalysis(String vehicleMovementFile) {
        vehicleMovement = vehicleMovementFile;
    }

    /**
     * This method generates a file called output-task1.txt containing the
     * solution to task 1
     */
    public void task1() {
        try {

            //declaration of variables
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
            Date date;

            String previousDateWithoutTime = "";
            String curentDateWithoutTime = "";

            double currentLongitude = 0.00;
            double currentLatitude = 0.00;

            double prevLongitude = 0.00;
            double prevLatitude = 0.00;

            String currentTimeStamp = "";
            String prevTimeStamp = "";

            double longitudeTotal = 0.00;
            double latitudeTotal = 0.00;

            FileReader fileReader = new FileReader(vehicleMovement);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";

            //reading and ignoring the first line of the log file
            line = bufferedReader.readLine();

            int count = 0;

            while ((line = bufferedReader.readLine()) != null) {

                //storing the prvious data
                prevLongitude = currentLongitude;
                prevLatitude = currentLatitude;
                prevTimeStamp = currentTimeStamp;
                previousDateWithoutTime = curentDateWithoutTime;

                //spliting a row of data from the csv file
                String[] data = line.split(",");

                //storing the current time stamp, latitude and longitude
                currentTimeStamp = data[1];
                currentLatitude = Double.parseDouble(data[2]);
                currentLongitude = Double.parseDouble(data[3]);

                //formats the time stamp
                date = simpleDateFormat1.parse(currentTimeStamp);
                curentDateWithoutTime = simpleDateFormat2.format(date);

                //checks if the previous time stamp is not empty
                if (!prevTimeStamp.equals("")) {
                    //calcualtes the total logitudinal and latitudinal values
                    if (!previousDateWithoutTime.equals(curentDateWithoutTime)) {
                        count++;
                        longitudeTotal += prevLongitude;
                        latitudeTotal += prevLatitude;
                        previousDateWithoutTime = curentDateWithoutTime;
                    }
                }
            }
            //adds the last row of the list
            count++;
            longitudeTotal += prevLongitude;
            latitudeTotal += prevLatitude;

            //closes the buffered reader
            bufferedReader.close();

            //compute average latitude and longitude
            NumberFormat formatter = new DecimalFormat("#0.00");
            longitudeTotal = longitudeTotal / count;

            latitudeTotal = latitudeTotal / count;

            //creating the outful file
            try {
                //creates a file
                File outputTask1 = new File("output-task1.txt");

                //checks if the file does not exist create a new file
                if (!outputTask1.exists()) {
                    outputTask1.createNewFile();
                }

                //creates a file writer and a buffered writer
                FileWriter fileWriter = new FileWriter("output-task1.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                //writes to the file
                bufferedWriter.write("Latitude \t\t Longitude");
                bufferedWriter.newLine();
                bufferedWriter.write(formatter.format(latitudeTotal) + "\t\t\t" + formatter.format(longitudeTotal));
                bufferedWriter.flush();

                //closes the buffered writer
                bufferedWriter.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method generates a file called output-task2.txt containing the
     * solution to task 2
     *
     * @param gpsPoints the name of the file with the GPS interest points
     */
    public void task2(File gpsPoints) {
        try {
            //variables
            ArrayList<InterestPoint> interestPoints = new ArrayList<InterestPoint>();
            ArrayList<Store> storeList = new ArrayList<Store>();

            //processing the date
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/mm/yyyy HH:mm");

            FileReader fileReader = new FileReader(gpsPoints.getName());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";

            //reading and ignoring the first line of the intrest point file
            line = bufferedReader.readLine();

            //LOADS THE DATA FROM THE INTEREST POINT FILE
            //reading the interest points file
            while ((line = bufferedReader.readLine()) != null) {
                //spliting a row of data from the interest poits file
                String[] data = line.split(",");

                //creating an interest point and adding it to the interestpoint list
                interestPoints.add(new InterestPoint(Integer.parseInt(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3])));

                //creating stores
                storeList.add(new Store());
            }
            //ENDS LOADING THE DATA IN THE LIST

            //moving to the csv log file
            fileReader = new FileReader(vehicleMovement);
            bufferedReader = new BufferedReader(fileReader);
            line = "";

            //reading and ignoring the first line of the log file
            line = bufferedReader.readLine();

            //variables for computing the distance
            double lat1 = 0.00;
            double longi1 = 0.00;

            double lat2 = 0.00;
            double longi2 = 0.00;
            double distance = 0.00;

            Date date1 = null;
            Date date2 = null;

            //reading the csv log file
            while ((line = bufferedReader.readLine()) != null) {
                //spliting a row of data from the log file
                String[] data = line.split(",");

                //reading the interestpoint file
                for (int i = 0; i < interestPoints.size(); i++) {

                    //computes the distance between two points
                    lat1 = interestPoints.get(i).getLatitude();
                    longi1 = interestPoints.get(i).getLongitude();

                    lat2 = Double.parseDouble(data[2]);
                    longi2 = Double.parseDouble(data[3]);

                    distance = calculateDistance(lat1, longi1, lat2, longi2);

                    //checking if the distance is less than radius
                    if (distance < interestPoints.get(i).getRadius()) {
                        //checks if the id is empty
                        if (storeList.get(i).id.equals("")) {

                            //stores the id
                            storeList.get(i).id = data[0];

                            //Stores the time
                            storeList.get(i).prevDate = data[1];
                        } else {
                            //if the id is different increase count
                            if (!storeList.get(i).id.equals(data[0])) {
                                //increase count
                                storeList.get(i).count++;
                            } else {

                                //if the ids are the same
                                date1 = simpleDateFormat.parse(storeList.get(i).prevDate);
                                date2 = simpleDateFormat.parse(data[1]);

                                //the difference is in milliseconds
                                long timeDifference = date2.getTime() - date1.getTime();
                                long minutesDifference = timeDifference / (60 * 1000) % 60;
                                storeList.get(i).totalTime += minutesDifference;

                            }
                            storeList.get(i).id = data[0];
                            storeList.get(i).prevDate = data[1];
                        }
                    }
                }
            }

            //WRITING THE OUTPUT TO output-task2.txt 
            //creates a file
            File outputTask2 = new File("output-task2.txt");
            NumberFormat formatter = new DecimalFormat("#0.00");

            //checks if the file does not exist create a new file
            if (!outputTask2.exists()) {
                outputTask2.createNewFile();
            }

            //creates a file writer and a buffered writer
            FileWriter fileWriter = new FileWriter(outputTask2.getName());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("IP_ID \t Latitude \t Longitude \t Radius \t  Count \t Average Time(minutes)");
            bufferedWriter.newLine();
            double averageTime = 0;
            for (int i = 0; i < interestPoints.size(); i++) {
                //if the count is 1, set it to 0
                if (storeList.get(i).count == 1) {
                    storeList.get(i).count = 0;
                }

                //if the count is 0 set average time to 0
                if (storeList.get(i).count == 0) {
                    averageTime = 0.0;
                } else {
                    //computes average time
                    averageTime = (double) storeList.get(i).totalTime / storeList.get(i).count;
                }

                bufferedWriter.write(interestPoints.get(i).getId() + "\t " + interestPoints.get(i).getLatitude() + "\t"
                        + interestPoints.get(i).getLongitude() + "\t " + interestPoints.get(i).getRadius()
                        + "\t\t  " + storeList.get(i).count + "\t\t " + formatter.format(averageTime));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method generates a file called output-task3.txt containing the
     * solution to task 3
     */
    public void task3() {

        try {
            //declaration of variables
            ArrayList<Store> storeList = new ArrayList<Store>();
            ArrayList<Store> unUsualTrips = new ArrayList<Store>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/mm/yyyy HH:mm");
            Date date1 = null;
            Date date2 = null;
            int totalTime = 0;

            FileReader fileReader = new FileReader("2.vehicle_log.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";

            String prevID = "";
            String currentID = "";

            String prevDate = "";
            String currentDate = "";
            Store store;

            //reading and ignoring the first line of the vehicle log file
            bufferedReader.readLine();

            //COMPUTES THE DURATION IN MINUTES OF EACH TRIP MADE BY THE BUS
            while ((line = bufferedReader.readLine()) != null) {
                prevID = currentID;
                prevDate = currentDate;

                String[] data = line.split(",");

                currentID = data[0];
                currentDate = data[1];

                if (!prevID.equals("")) {
                    //checks if the previous and current ids are equal
                    if (!prevID.equals(currentID)) {
                        store = new Store();
                        store.id = prevID;
                        store.totalTime = totalTime;

                        if (store.totalTime >= 13) {
                            storeList.add(store);
                        } else {
                            unUsualTrips.add(store);
                        }
                        totalTime = 0;
                    } else {
                        //they are equal
                        //calculates the duration for the trip
                        date1 = simpleDateFormat.parse(prevDate);
                        date2 = simpleDateFormat.parse(currentDate);

                        //the difference is in milliseconds
                        long timeDifference = date2.getTime() - date1.getTime();

                        long minutesDifference = timeDifference / (60 * 1000) % 60;
                        totalTime += minutesDifference;
                    }
                }
            }//END OF COMPUTATION

            //ADDS THE LAST TRIP 
            store = new Store();
            store.id = prevID;
            store.totalTime = totalTime;
            if (store.totalTime >= 13) {
                storeList.add(store);
            } else {
                unUsualTrips.add(store);
            }

            //COMPUTES THE OUTLIERS IN THE DATA
            double mean = getMean(storeList);
            double standardDeviation = getStandardDeviation(storeList);
            double lowBound = mean - (standardDeviation * 2.0);
            double highBound = mean + (standardDeviation * 2.0);

            //adds the outliers to the unusual trips
            for (int i = 0; i < storeList.size(); i++) {
                if (storeList.get(i).totalTime > highBound) {
                    unUsualTrips.add(storeList.get(i));
                }
            }

            //WRITES THE OUPUT TO output-task3.txt
            //creates a file
            File outputTask3 = new File("output-task3.txt");

            //checks if the file does not exist create a new file
            if (!outputTask3.exists()) {
                outputTask3.createNewFile();
            }

            //creates a file writer and a buffered writer
            FileWriter fileWriter = new FileWriter(outputTask3.getName());
            BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter);

            boolean printTotal = true;
            for (int i = 0; i < unUsualTrips.size(); i++) {
                if (printTotal) {
                    //prints the total number of unusual trips
                    Integer size = unUsualTrips.size();
                    bufferedWriter1.write(size.toString());
                    bufferedWriter1.newLine();
                    printTotal = false;
                }
                //prints the ids of the unusual trips
                bufferedWriter1.write(unUsualTrips.get(i).id);
                bufferedWriter1.newLine();

            }
            bufferedWriter1.flush();
            bufferedWriter1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the Variance
     *
     * @param str
     * @return variance
     */
    private double getVariance(ArrayList<Store> str) {
        double mean = getMean(str);
        double temp = 0;
        for (int i = 0; i < str.size(); i++) {
            temp += (str.get(i).totalTime - mean) * (str.get(i).totalTime - mean);
        }
        return temp / str.size();
    }

    /**
     * returns the standard Deviation
     *
     * @param str
     * @return standard deviation
     */
    private double getStandardDeviation(ArrayList<Store> str) {
        return Math.sqrt(getVariance(str));
    }

    /**
     * Calculates the mean
     *
     * @param str
     * @return mean
     */
    private double getMean(ArrayList<Store> str) {
        double sum = 0;
        for (int i = 0; i < str.size(); i++) {
            sum += str.get(i).totalTime;
        }
        return sum / str.size();
    }

    /**
     * Calculates the distance between two GPS points in Kilometers
     *
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     */
    private double calculateDistance(
            double latitude1, double longitude1, double latitude2, double longitude2) {
        int earthRadius = 6371; // average radius of the earth in km
        double latitudeDifference = Math.toRadians(latitude2 - latitude1);
        double longitudeDifference = Math.toRadians(longitude2 - longitude1);
        double comp = Math.sin(latitudeDifference / 2) * Math.sin(latitudeDifference / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(longitudeDifference / 2) * Math.sin(longitudeDifference / 2);
        double answer = 2 * Math.atan2(Math.sqrt(comp), Math.sqrt(1 - comp));
        double distance = earthRadius * answer;
        return distance;
    }
}
