import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Write name of file with train-set ");
        String trainingFileName = input.nextLine();
        System.out.println("Write name of file with test-set ");
        String testFileName = input.nextLine();

        Perceptron perceptron = new Perceptron(trainingFileName,testFileName);
        perceptron.start();

        while(true){
            System.out.println("Write new vector to classify, separated by coma: ");
            String[] splittedLine = input.nextLine().split(",");
            double[] observation = new double[splittedLine.length];
            for(int i =0; i < splittedLine.length; i++){
                observation[i] = Double.parseDouble(splittedLine[i]);
            }
            int decision = perceptron.decide(observation);
            System.out.println(perceptron.getClassNameForCode(decision));
        }
    }
}
