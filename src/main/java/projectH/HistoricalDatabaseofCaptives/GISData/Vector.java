package projectH.HistoricalDatabaseofCaptives.GISData;

public class Vector {

    private double Ax;
    private double Ay;

    private double Bx;
    private double By;

    public Vector(double ax, double ay, double bx, double by) {
        Ax = ax;
        Ay = ay;
        Bx = bx;
        By = by;
    }

    public double getAx() {
        return Ax;
    }

    public void setAx(double ax) {
        Ax = ax;
    }

    public double getAy() {
        return Ay;
    }

    public void setAy(double ay) {
        Ay = ay;
    }

    public double getBx() {
        return Bx;
    }

    public void setBx(double bx) {
        Bx = bx;
    }

    public double getBy() {
        return By;
    }

    public void setBy(double by) {
        By = by;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "Ax=" + Ax +
                ", Ay=" + Ay +
                ", Bx=" + Bx +
                ", By=" + By +
                '}';
    }
}
