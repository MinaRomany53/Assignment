package exhaustivesearch;
 // @author Mina Romany
import java.util.Arrays;
import java.util.Scanner;
public class ExhaustiveSearch {
    static Scanner input = new Scanner(System.in);
   
    /*---------------------------------------- Helpful Methods ----------------------------------------*/
    // make array(n) from 1 to n thedn get all permutations for this array
    static int[] firstPermutearray (int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            arr[i] = i+1;    
        }
        return arr;
    }
    
    // sum all values(jobs)from a matrix using array values as index 
    static int sumvalues (int arr[], int M[][]) {
        int n = arr.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum+=M[i][arr[i]-1];
        }
        return sum;
    }
    
    // Copy array elements in newArray 
    static int[] copyArray (int arr[]) {
        int n = arr.length;
        int newArray[] = new int [n];
        for (int i = 0; i < n; i++) {
            newArray[i] = arr[i];
        }
        return newArray;
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    
    /*-------------------------------------- Main Method ----------------------------------------------------*/
    public static void main(String[] args) {
        // get Size of the Balanced Matrix
        System.out.println("Enter Size for Balanced Matrix");
        int n = input.nextInt();
        // Create Balanced Matrix
        int matrix[][] = new int [n][n];
        // get matrix inputs
        System.out.println("Enter Elements for Balanced Matrix "+n+"X"+n);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {    
                matrix[i][j]=input.nextInt();
            }    
        }
        
      
        // Assignment Problem (fisrt permutation)
        int A[] = new int [n]; 
        A = firstPermutearray(A); // Create array save all possibilities  ex. n =4  A = {1,2,3,4} to get permutations
        
        int solution[] = new int [n]; // for saving array (jobs indexes) that get the minimum optimal solution
        solution = A; // Save first permutation jobs indexe
        
        int minOptimal = sumvalues(A,matrix); // for saving the minimum optimal solution      
        
        
        // heap's algorithm, iterative --to get all permutations
        // make idx array with zeros
        int[] idx = new int[A.length];
        Arrays.fill(idx, 0);
       
        for (int i = 1; i < A.length;) {
          if (idx[i] < i) {
            int swap = i % 2 * idx[i];
            int tmp = A[swap];
            A[swap] = A[i];
            A[i] = tmp;

            // Assignment Problem (new permutation)
            int sum = 0;
            sum = sumvalues(A,matrix);
            if (sum < minOptimal) {
                minOptimal = sum;  // new minimum Optimal solution
                solution = copyArray(A); // Save jobs indexe in solution array
            }

            idx[i]++;
            i = 1;
          } else {
            idx[i++] = 0;
          }
        }
        
        
        System.out.println("---------------------------------------Solution---------------------------------------");
        //Display Solution
        for (int i = 0; i < solution.length; i++) {
            int personCount = i+1;
            System.out.println("Person "+ personCount +" "+"Assign to " +"Job "+solution[i]);           
        }
        System.out.println("The optimal value equals => "+ minOptimal);
           
    }
}
