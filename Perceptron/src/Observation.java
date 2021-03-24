import java.util.Arrays;

public class Observation {
    double[] attributes;
    int desired;
    int actual;

    public Observation(double[] attributes, int desired) {
        this.attributes = attributes;
        this.desired = desired;
    }

    public double[] getAttributes() {
        return attributes;
    }

    public int getDesired() {
        return desired;
    }

    public int getActual() {
        return actual;
    }

    public void setAttributes(double[] attributes) {
        this.attributes = attributes;
    }

    public void setDesired(int desired) {
        this.desired = desired;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    @Override
    public String toString() {
        String string = "";
        for(Double attribute : attributes){
            string += attribute + " ";
        }
        return string;
    }
}
