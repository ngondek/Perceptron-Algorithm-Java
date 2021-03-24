import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Perceptron {

    private String trainingFileName;
    private String testFileName;
    private List<Observation> training;
    private List<Observation> test;
    private Map<String,Integer> classesNames;
    private double[] weights;
    private double theta = 0.2;

    Perceptron(String trainingFileName, String testFileName) {
        this.trainingFileName = trainingFileName;
        this.testFileName = testFileName;
    }

    void start(){
        training = new ArrayList<>();
        test = new ArrayList<>();
        classesNames = new HashMap<>();
        setClassesNames(trainingFileName);
        setObservationsFromFile(trainingFileName,training);
        learn();
        setObservationsFromFile(testFileName,test);
        classify();
        showResult();
        showErrorFactor();
    }

    private void setClassesNames(String fileName){
        String line = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            while((line = bufferedReader.readLine()) != null){

                String[] splittedLine = line.split(",");
                String className = splittedLine[splittedLine.length-1];
                if(!classesNames.containsKey(className)){
                    if(classesNames.containsValue(1))
                        classesNames.put(className,0);
                    else
                        classesNames.put(className,1);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setObservationsFromFile(String fileName, List<Observation> observationList) {
        String line = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            while((line = bufferedReader.readLine()) != null){

                String []observation = line.split(",");
                double[] attributes = new double[observation.length-1];
                for(int i = 0; i < observation.length-1; i++){
                    attributes[i] = Double.parseDouble(observation[i]);
                }
                int desired = classesNames.get(observation[observation.length-1]);
                observationList.add(new Observation(attributes,desired));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void learn(){
        getRandomWeights();

        double alpha = 0.2;
        int attributesSize = training.get(0).getAttributes().length;
        int maxLoops = 200;

        for(int i=0;i<maxLoops;i++)
        {
            int totalError = 0;
            for (Observation observation : training) {
                int output = decide(observation.getAttributes());
                int error = observation.getDesired() - output;
                totalError += error;

                for (int k = 0; k < attributesSize; k++)
                    weights[k] += alpha * observation.getAttributes()[k] * error;

                theta -= error * alpha;
            }
            if(totalError == 0)
                break;
        }
    }

    private void getRandomWeights(){
        weights = new double[training.get(0).getAttributes().length];
        for(int i = 0; i < weights.length; i++){
            weights[i] = Math.random();
        }
    }

    int decide(double[] attributes){
        double net = 0;
        for(int i =0 ; i <attributes.length; i++){
            net += attributes[i]*weights[i];
        }
        net += theta;

        int decision;
        if(net >= 0)
            decision =1;
        else
            decision = 0;

        return decision;
    }

    private void classify(){
        for(Observation observation : test){
            observation.setActual(decide(observation.getAttributes()));
        }
    }

    private void showResult(){
        for(Observation observation : test){
            String decision = getClassNameForCode(observation.actual);
            System.out.println(observation + " " + decision);
        }
    }

    String getClassNameForCode(int code){
        String classFromCode = "";
        for(Map.Entry<String,Integer> className: classesNames.entrySet()) {
            if (className.getValue().equals(code)) {
                classFromCode = className.getKey();
            }
        }
        return classFromCode;
    }

    private void showErrorFactor(){
        double observationsQuantity = test.size();
        double wrong = 0;

        for(Observation observation : test){
            if(observation.getActual() != observation.getDesired())
                wrong++;
        }

        double errorFactor = (observationsQuantity - wrong)/observationsQuantity*100;

        System.out.println("Correctly classified: " + errorFactor + "%");

    }

}
