import java.util.Scanner;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Collections;

class Main {
  public static void main(String[] args) {

    // creates an object of Scanner
    Scanner input = new Scanner(System.in);

    System.out.print("Enter the names of weekend's winners: ");

    // Enter the names of winners of weekend;
    String names = input.nextLine();

    // Prints the names of the winners
    System.out.println("The names of the winners " + names);
    //creates the array of names of winners
    String[] arrayNames = names.split(",");
    System.out.print("Enter the values to be won: ");


    // Enter the values to be won;
    String values = input.nextLine();
     //Creates the array of values to be won
    String[] arrayValues = values.split(",");
    // Prints the values to be won
    System.out.println("The values to be won " + values);



    //Converting the array elements into interger
    // Instead of looping the array and coverting elements one by one we can use the stream api for java to improve the big O notation of the program 
    
    Integer[] intArrayValues = Stream.of(arrayValues).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);


    // Let's get the sum of the array by looping through array of values to be won. Big o notation is O(n); 
    int sum = 0;

    for(int i = 0; i < intArrayValues.length; i++){

        sum += intArrayValues[i];

        
    }

    System.out.println("Total of values to be won:" + sum);


    // Let's get the fair value which each winner should get
    
    //Let's divide the total or sum of values by the number of winners


    int numberOfWinners = arrayNames.length;


    System.out.println("Number of winners:" + numberOfWinners);


    //Get the fair value to give winners


    int fairValueToEachWinners = sum/numberOfWinners;

    
    System.out.println("Fair values of prize of winners:" + fairValueToEachWinners);


    //Printing the winner and value they get


    for(int i =0; i < arrayNames.length; i++ ){

        System.out.println(arrayNames[i] + " will get value of " +  fairValueToEachWinners);


    }


    // To get the values that are almost or equivalent to fair amount of prizes

    // We need to sort the array and get values that are equivalent fair value to give the winners
    
  
    Arrays.sort(intArrayValues);
    

// Outer loop
    for(int i = 0; i < intArrayValues.length; i++){

// Inner nested loop pointing 1 index ahead
        for(int j = i+1; j < intArrayValues.length; j++){

             if((intArrayValues[i] + intArrayValues[j]) == fairValueToEachWinners){


              
              System.out.println(intArrayValues[i] + "," + intArrayValues[j]);


             }

        }


   }
    
// Big Oh notation will O(n)2 due to nested loop. 

//If given time it should be improved atleast to Big Oh notation O(n).

// closes the scanner
    input.close();
  }
}