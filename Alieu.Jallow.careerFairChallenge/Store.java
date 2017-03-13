/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class contains information about the number of times the bus has passed through an interest point
 * it also holds information about id, total time and date 
 * @author Alieu Jallow
 */
public class Store {

    //instance variables
    String id;
    int count;
    long totalTime;
    String prevDate;

    /**
     * constructor
     */
    public Store() {
        id = "";
        count = 1;
        totalTime = 0;
        prevDate = "";
    }
}
