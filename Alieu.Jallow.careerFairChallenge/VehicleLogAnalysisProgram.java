/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
/**
 * This class contains the main method for running the program
 * @author Alieu Jallow
 */
public class VehicleLogAnalysisProgram 
{
   /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //creating an obbject of the vehicleLogAnalysis class
        VehicleLogAnalysis vehicleLogAnalysis = new VehicleLogAnalysis("2.vehicle_log.csv");
        
        //calling task 1
        vehicleLogAnalysis.task1();
        
      
        //creating a file
        File file = new File("1.interest_points.csv");
        
        //calling task 2
        vehicleLogAnalysis.task2(file);
        
        //calling task 3
         vehicleLogAnalysis.task3();
    
    }
    
}
