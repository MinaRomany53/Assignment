package assignment.problem;
 // @author Mina Romany
import java.util.Scanner;
public class AssignmentProblem {
    static Scanner input = new Scanner(System.in);
    /*---------------------------------------- Helpful Methods ----------------------------------------*/
    // Display Matrix  O(n2)
    static void displayMatrix(int M[][] ) {
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                 System.out.print(M[i][j]+ " ");               
            }    
            System.out.println(" ");
        }
        System.out.println(" ");
    }
    
    // Copy Original Matrix O(n2)
    static int[][] saveFirstmatrix(int M[][] , int M_copied[][] ) {
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                M_copied[i][j] = M[i][j];               
            }    
        }
        return M_copied;
    }
    
    // Calculate number of Lines drawing (horizontal or vertical) O(n)
    static int calcLines (boolean rowLines[],boolean colLines[]){
        int linesCounter = 0;
        for (int i = 0; i <rowLines.length ; i++) {
            if (rowLines[i] == true ) {linesCounter++;}
            if (colLines[i] == true ) {linesCounter++;}
        }
        return linesCounter;
    }
    /*--------------------------------------------------------------------------------------------------*/
   
    /*---------------------------------------- Hungarian Algorithm Methods ----------------------------------------*/
    // Subtract row minimum O(2n**2)
    static int[][] subRowmin (int M[][]){
        int n = M.length;
        for (int i = 0; i < n; i++) {
                    int minNumber = M[i][0]; // minNumber = first num in each row
            // get minimum number in the Row
            for (int j = 0; j < n; j++) {
                if (M[i][j]<minNumber ){minNumber = M[i][j];}
            }
            // Subtract it from all elements in the Row
            for (int j = 0; j < n; j++) {
                M[i][j] = M[i][j] - minNumber;
            }      
        }
        return M;
    } 
    
    // Subtract col minimum O(2n**2)
    static int[][] subColmin (int M[][]){
        int n = M.length;
        for (int j = 0; j< n; j++) {
                    int minNumber = M[0][j]; // minNumber = first num in each col
            // get minimum number in the col
            for (int i = 0; i < n; i++) {
                if (M[i][j]<minNumber ){minNumber = M[i][j];}
            }
            // Subtract it from all elements in the col
            for (int i = 0; i < n; i++) {
               M[i][j] = M[i][j] - minNumber;
            }       
        }
        return M;
    } 
   
    
    // Cover all zeros in a Matrix with a minimum number of lines (horizontal or vertical) + assign one job to one person  O(n**2)
    static int[] drawZeroslines (int M[][]) {
        int n = M.length;
        int solution[] = new int [n]; // store person(index) Assign to Job(element)
        boolean rowLines [] = new boolean [n]; // RowLines all False       ex. if n= 4 [r1,r2,r3,r4] 4 
        boolean colLines [] = new boolean [n]; // ColLines all False       ex. if n= 4 [c1,c2,c3,c4] 4 
        // check row zeros (Vertical lines) (one zero in a row = vertical line)
        for (int i = 0; i < n; i++) {
            int countZeros = 0; 
            int colNo = 0; // store col number to make vertical line
            for (int j = 0; j < n; j++) {
                if(M[i][j] == 0 && colLines[j] == false) {countZeros++; colNo = j;}
            }
            // لو الصف فيه زيرو واحد و العمود ده متعملش عليه خط قبل كدا اعمل عليه خط 
            if (countZeros == 1 && colLines[colNo] == false ){
                colLines[colNo] = true; 
                solution[i] = colNo; // asigning person index to one job index from firstMatrix
            } 
        }
        // check col zeros (Horizontal lines) (one zero in a col = horizontal line)
        for (int j = 0; j < n; j++) {
            int countZeros = 0; 
            int rowNo = 0; // store row number to make horizontal line
            for (int i = 0; i < n; i++) {
                // هنا فيه حاجة زيادة لازم تتأكد ان الزيرو ده مش معمول عليه خط فيرتيكال اصلا
                if(M[i][j] == 0 && colLines[j] == false) {countZeros++; rowNo = i;}
            }

            if (countZeros == 1 && rowLines[rowNo] == false){
                rowLines[rowNo] = true; 
                solution[rowNo] = j; // asigning person index to one job index from firstMatrix
            } 
        }
        // Check last time that no zeros left uncovered    
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(M[i][j] == 0 && colLines[j] == false && rowLines[i] == false ) {
                rowLines[i] = true; 
                solution[i] = j; // asigning person index to one job index from firstMatrix           
                }
            }
        }
        
        // Get number of all Lines (horizontal or vertical)
        int noLines = calcLines(rowLines,colLines);
            System.out.println("noLines => "+ noLines) ;
        
        // Check if (number of Lines = size of matrix)
        if (noLines == n) {
            return solution;
        } // return solution Done#
        
        else {
            // need one more step
            System.out.println("----------------Step 4: Create additional zeros----------------");
            // Step-4: Create additional zeros
            // Find the smallest uncovered number with no lines O(n**2)
            int minNumber = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (colLines[j] == false && rowLines[i] == false && M[i][j] < minNumber) {minNumber = M[i][j];}
                }
            }
            // Subtract this number from all uncovered elements 
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (colLines[j] == false && rowLines[i] == false) {M[i][j]-=minNumber;}
                }
                
            }
            // Add it to all elements that are covered twice
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (colLines[j] == true && rowLines[i] == true) {M[i][j]+=minNumber;}
                }    
            }
            displayMatrix(M);
            return drawZeroslines(M);          
        }       
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    
    /*------------------------------------------------Main------------------------------------------------*/
    public static void main(String[] args) {
        // get Size of the Balanced Matrix
        System.out.println("Enter Size for Balanced Matrix");
        int n = input.nextInt();
        // Create Balanced Matrix
        int matrix[][] = new int[n][n]; 
        // get matrix inputs
        System.out.println("Enter Elements for Balanced Matrix "+n+"X"+n);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {    
                matrix[i][j]=input.nextInt();
            }    
        }
        // Save a copy of the Original Matrix (use it at the end to get solution)
        int firstMatrix[][] = new int[n][n];
        saveFirstmatrix(matrix,firstMatrix);
        System.out.println("----------------Step-1: Subtract Row minimum----------------");
        // Step-1: Subtract Row minimum
        subRowmin(matrix);
        displayMatrix(matrix);
        System.out.println("----------------Step-2: Subtract Col minimum----------------");
        // Step-2: Subtract Col minimum
        subColmin(matrix);
        displayMatrix(matrix);
        System.out.println("----------------Step 3: Cover all zeros with a minimum number of lines----------------");
        // Step-3: Cover all zeros with a minimum number of lines (horizontal or vertical)
        int solution [] = drawZeroslines(matrix); // index = person(row)  value = Job(col) 
        System.out.println("---------------------------------------Solution---------------------------------------");
        //Display Solution
            int sumJobsvalues = 0;
            for (int i = 0; i < solution.length; i++) {
                int personCount = i+1;
                int jobCount = solution[i]+1;
                sumJobsvalues+=firstMatrix[i][solution[i]]; // رقم الاندكس لكل عنصر فيه هو رقم الصف والقيمة هيا رقم العمود
                System.out.println("Person "+ personCount +" "+"Assign to " +"Job "+jobCount);           
            }
            System.out.println("The optimal value equals => "+ sumJobsvalues  );
    }
}