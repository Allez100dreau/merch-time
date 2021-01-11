package unice.casDeTests;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneratingTestCase {

    //Declaration of limit for the price of all goodies
    private static final int LOWERBOUND = 1;
    private static  final int UPPERBOUND = 14;

    //Generating a random integer with a Gaussian law, between the bound below
    private static int generateNextGaussian(){
        Random rand = new Random();
        double price;
        do {
            price= Math.abs(rand.nextGaussian()*UPPERBOUND);
        }while (price>UPPERBOUND);
        return (int)Math.round(price);
    }

    //Generate an array for the price of all the goodies available
    private static int[] generatingPrice(int nbGoodies){
        int[] listPrice =new int[nbGoodies];
        int i=0;
        while(listPrice[i] == 0) {
            listPrice[i] = generateNextGaussian();
            i++;
            if(i ==nbGoodies ){break;}
        }
        return (listPrice);
    }

    //Generate a large amount of test case price whit particular one : only fill by number in range LOWERBOUDN to UPPERBOUND
    private static int[][] largeGeneratingPrice(int nbGoodies, int nbTest){
        int[][] res = new int[nbTest][nbGoodies];
        int j=LOWERBOUND;
        for (int i=0;i<nbTest;i++){
            if (i%2==0 && j<=UPPERBOUND){
                Arrays.fill(res[i],j);
                j++;
            }else
                for (int k=0;k<nbGoodies;k++) res[i][k] = generateNextGaussian();
        }
        return res;
    }

    private static ArrayList<int[]> formatOfTestCase(int[] capacity,int[] nbOfGoodies,int[] arrayOfPrice){
        ArrayList<int[]> caseTest= new ArrayList<>(3);
        caseTest.addAll(Arrays.asList(capacity, nbOfGoodies, arrayOfPrice));
        return caseTest;
    }

    //Generate the final test case with the good format
    private static ArrayList<int[]> generatingCaseTests(int[] capacity, int[] nbGoodies){
        final int[] listPrice = generatingPrice(nbGoodies[0]);
        return formatOfTestCase(capacity,nbGoodies,listPrice);
    }

    //useful if we need a large generation but need adjustment of the main method
    private static ArrayList<ArrayList<int[]>> largeGenerationOfTestCases(int nbOfTest,int[] cap,int[] nbOfGoodies){
        ArrayList<ArrayList<int[]>> largeTestCase = new ArrayList<>(nbOfTest);
        int[][] listOfPrice = largeGeneratingPrice(nbOfGoodies[0],nbOfTest);
        for (int i=0;i<nbOfTest;i++){
            largeTestCase.add(formatOfTestCase(cap,nbOfGoodies,listOfPrice[i]));
        }
        return(largeTestCase);
    }

    public static void main(String[] arg){

        //Three important data need to be call in arguments :
        // first the number of test case wanted,
        // in second the knapsack capacity,
        // in third the maximum number of goodies salable
        int nbOfTestCase =Integer.parseInt(arg[0]);
        int[] capacity = new int[]{Integer.parseInt(arg[1])};
        int[] nbGoodies = new int[]{Integer.parseInt(arg[2])};


        //useful if need
        //largeGenerationOfTestCases(nbOfTestCase,capacity ,nbGoodies);


        //Creation of file input
        int acc =0;
        for (int j=0;j<nbOfTestCase;j++){

            //Define the name for all our files and variables for it
            String fileName = "src/main/resources/input/Input"+acc+".txt";
            BufferedWriter bw = null;
            FileWriter fw = null;
            try{
                fw = new FileWriter(fileName);
                bw = new BufferedWriter(fw);
                for (int[] i : generatingCaseTests(capacity, nbGoodies)){
                    bw.write(Arrays.toString(i).replace("]","").replace("[","").replace(",","")+"\n");
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    if (bw != null)bw.close();
                    if (fw!=null)fw.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            acc++;
        }
    }
}
