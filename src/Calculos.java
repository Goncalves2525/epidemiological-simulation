public class Calculos {

    public double derivadaROrdemT(double gama, double i, double alfa, double r, double ro, double beta, double s) {
        return gama * i - alfa * r + (1 - ro) * beta * s * i;
    }
}
