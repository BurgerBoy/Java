import java.util.*;
import java.io.*;

public class P4{ 

    public static float lpt(int[][] training, int u, int v, int i){ 
    	float tnum = 0.1f;
	    float bnum = 0.4f;

	    for(int c = 0; c < training.length; c++){
	    	//this will always be training[c][5]
	    	if(training[c][5] == v){
	    		bnum++;
	    		if(training[c][i] == u){
	    			tnum++;
	    		}
	    	}
	    }
	    float result = log2(tnum/bnum);
        return result; 
    }

    


    //loop through entire set, calculate lp for single u, v, i
    //for testing, i increases and u corresponds to instance in training set
    //v stays the same until sum is complete.
    //lp(X.a1 = 2 | X.a6 = 1) + lp(X.a2 = 3 | X.a6 = 1)
    public static float lptester(int[][] testing, int u, int v, int i){ 
    	float tnum = 0.1f;
	    float bnum = 0.4f;

	    for(int c = 0; c < testing.length; c++){
	    	//this will always be training[c][5]
	    	if(testing[c][5] == v){
	    		bnum++;
	    		if(testing[c][i] == u){
	    			tnum++;
	    		}
	    	}
	    }
	    float result = log2(tnum/bnum);
        return result; 
    }

    public static float lptest1(int[][] testing){ 
    	float result = 0.0f;
    	//lp(X.a1 = 1)
	    result += lpt(testing,1,1,0);
	    result += lpt(testing,2,1,1);
	    result += lpt(testing,3,1,2);
	    result += lpt(testing,4,1,3);
	    result += lpt(testing,5,1,4);
        return result; 
    }

    public static float lptest2(int[][] testing){ 
    	float result = 0.0f;
    	//lp(X.a1 = 1)
	    result += lpt(testing,1,2,0);
	    result += lpt(testing,2,2,1);
	    result += lpt(testing,3,2,2);
	    result += lpt(testing,4,2,3);
	    result += lpt(testing,5,2,4);
        return result; 
    }

    public static float lptest3(int[][] testing){ 
    	float result = 0.0f;
    	//lp(X.a1 = 1)
	    result += lpt(testing,1,3,0);
	    result += lpt(testing,2,3,1);
	    result += lpt(testing,3,3,2);
	    result += lpt(testing,4,3,3);
	    result += lpt(testing,5,3,4);
        return result; 
    }

    public static float lpf(int[][] training, int v){ 
    	float tnum = 0.1f;
	    float bnum = 0.3f;
	    for(int c = 0; c < training.length; c++){
	    	//this will always be training[c][5]
	    
	    	if(training[c][5] == v){
	    		tnum++;
	    	}
	    	bnum++;
	    
	    }
	    float result = log2(tnum/bnum);
        return result; 
    }

    public static void printTrain(float[][] rh){
    	for(int v = 0; v < 3; v++){
	    	for(int u = 0; u < 4; u++){
				System.out.printf("%.4f ", rh[v][u]);	 
		    }
		    System.out.println();
	    }
    }

    public static float log2(float N) 
    { 
        // calculate log2 N indirectly 
        // using log() method 
        float result = (float)(Math.log(N) / Math.log(2)); 
  
        return result * (-1.0f); 
    }  
   

    public static void main(String args[]) throws FileNotFoundException {

        int ds[][] = new int[1000][6];
        int p = 0;
        try (Scanner scanner = new Scanner(new File("prog4Data.csv"));) {
		    scanner.nextLine();
		    while (scanner.hasNextLine()) {
		        //line.
		        String line = scanner.nextLine();
		        String tokens[] = line.split(",");
		        ds[p][0] = Integer.parseInt(tokens[0]);
		        ds[p][1] = Integer.parseInt(tokens[1]);
		        ds[p][2] = Integer.parseInt(tokens[2]);
		        ds[p][3] = Integer.parseInt(tokens[3]);
		        ds[p][4] = Integer.parseInt(tokens[4]);
		        ds[p][5] = Integer.parseInt(tokens[5]);
		        p++;
		    }
		    scanner.close();	
		}

		catch (FileNotFoundException ex){
        // insert code to run when exception occurs
    	}
    	//Dataset is filled.

    	//Ask for input.
		Scanner inp = new Scanner(System.in);
	    String input[] = inp.nextLine().split(" ");
	    inp.close();

	    int training[][] = new int[Integer.parseInt(input[0])][6];
	    int testing[][]  = new int[Integer.parseInt(input[1])][6];

	    

	    //Fill Training set
	    for(int i = 0; i < Integer.parseInt(input[0]); i++){
	    	//System.out.println(i);
	    	training[i][0] = ds[i][0];
	    	training[i][1] = ds[i][1];
	    	training[i][2] = ds[i][2];
	    	training[i][3] = ds[i][3];
	    	training[i][4] = ds[i][4];
	    	training[i][5] = ds[i][5];
	    }

	    //Fill Testing Set
	    int j = 0;
	    for(int i = 1000 - Integer.parseInt(input[1]); i < 1000; i++){ 	
	    	testing[j][0] = ds[i][0];
	    	testing[j][1] = ds[i][1];
	    	testing[j][2] = ds[i][2];
	    	testing[j][3] = ds[i][3];
	    	testing[j][4] = ds[i][4];
	    	testing[j][5] = ds[i][5];
	    	j++;	
	    }

	    float resHolder[][] = new float[15][4];
	    
	    float rh1[][] = new float[3][4];
	    float rh2[][] = new float[3][4];
	    float rh3[][] = new float[3][4];
	    float rh4[][] = new float[3][4];
	    float rh5[][] = new float[3][4];
	   
	    for(int v = 1; v < 4; v++){
	    	for(int u = 1; u < 5; u++){
				rh1[v-1][u-1] = lpt(training,u,v,0); 
		    }
	    }

	    for(int v = 1; v < 4; v++){
	    	for(int u = 1; u < 5; u++){
				rh2[v-1][u-1] = lpt(training,u,v,1); 
		    }
	    }

	    for(int v = 1; v < 4; v++){
	    	for(int u = 1; u < 5; u++){
				rh3[v-1][u-1] = lpt(training,u,v,2); 
		    }
	    }

	    for(int v = 1; v < 4; v++){
	    	for(int u = 1; u < 5; u++){
				rh4[v-1][u-1] = lpt(training,u,v,3); 
		    }
	    }

	    for(int v = 1; v < 4; v++){
	    	for(int u = 1; u < 5; u++){
				rh5[v-1][u-1] = lpt(training,u,v,4); 
		    }
	    }

	    for(int v = 1; v < 4; v++){
	  		System.out.printf("%.4f ", lpf(training, v));
	  		System.out.println();	
	    }

	    System.out.println();
	    printTrain(rh1);
	    System.out.println();
	    printTrain(rh2);
	    System.out.println();
	    printTrain(rh3);
	    System.out.println();
	    printTrain(rh4);
	    System.out.println();
	    printTrain(rh5);
	    System.out.println();


	    //Finding sum for each instance of training set.
	    //Assigning category based on v with lowest sum.
	    int testCats[] = new int[testing.length];

	    for(int jj = 0; jj < testing.length; jj++){
	    	float cats[] = new float[3];
	    	//Get sum for each instance and category
		    for(int i= 0; i < 5; i++){
		    	cats[0] += lptester(testing, testing[jj][i], 1 , i);
		    	cats[1] += lptester(testing, testing[jj][i], 2 , i);
		    	cats[2] += lptester(testing, testing[jj][i], 3 , i);
		    }
		    //Add category frequency
		    cats[0] += lpf(training, 1);
		    cats[1] += lpf(training, 2);
		    cats[2] += lpf(training, 3);
		    //Find minimum value and category
		    float minSum = cats[0]; 
		    int minSumCat = 0;
		    for(int i = 1; i < cats.length; i++){
		    	if(cats[i]< minSum){
		    		//set new minimum sum and index
		    		minSum = cats[i];
		    		minSumCat = i;
		    	}
		    }

		    minSumCat += 1; // accounting for 0 indexing
		    testCats[jj] = minSumCat; // set category for instance
	    }

	    
	    System.out.println();

	    
	    //Calculating truth table
	    float tp = 0.0f;
	    float tn = 0.0f;
	    float fp = 0.0f;
	    float fn = 0.0f;

	    for(int i = 0; i < testing.length; i++){
	    	// True Positives
	    	if((testing[i][5] == 3) && (testCats[i] == 3)){
	    		tp++;
	    	}
	    	
	    	// False Positives
	    	else if((testing[i][5] != 3) && (testCats[i] == 3)){
	    		fp++;
	    	}
	    	// False Negatives
	    	else if((testing[i][5] == 3) && (testCats[i] != 3)){
	    		fn++;
	    	}
	    	// True Negatives
	    	else if((testing[i][5] != 3) && (testCats[i] != 3)){
	    		tn++;
	    	}

	    }

	    System.out.println("True positives: "+tp);
	    System.out.println("False positives: "+fp);
	    System.out.println("True negatives: "+tn);
	    System.out.println("False negatives: "+fn);
	    
	    System.out.println("Precision: " + (tp) + "/" +(tp+fp));

	    System.out.println("Recall: " + (tp) + "/" +(tp+fn));

	    
	    System.out.printf("Precision: %.4f ", ((tp)/(tp+fp)));
	    System.out.printf("Recall: %.4f ", ((tp)/(tp+fn)));
	    System.out.println();


		

    } 
} 