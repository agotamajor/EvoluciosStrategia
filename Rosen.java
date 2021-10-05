public class Rosen {
    private int dim;

    public Rosen(int dim) {
        this.dim = dim;
    }

    public double[] szimLehutes(int n){
        int k = 0;
        double t0 = 1000000000;
        double t = t0;
        double S;
        double[] x = new double[n];
        double[] xLegjobb = new double[n];
        double[] xW = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = Math.random() * 2 - 1;
        }
        S = Rosenbrock(n, x);
        double Legjobb = S;
        while (t > 0) {
            for (int i = 0; i < n; i++) {
                xW[i] = x[i] + Math.random() * 4 - 2;
            }
            k++;
            double W = Rosenbrock(n, xW);
            double r = Math.random();
            if (W < S || r < Math.exp((S - W) / t)) {
                S = W;
                for (int i = 0; i < n; i++) {
                    x[i] = xW[i];
                }
            }
            t = Csokkent(1, t0, k);
            if (S < Legjobb) {
                Legjobb = S;
                for (int i = 0; i < n; i++) {
                    xLegjobb[i] = x[i];
                }
            }
        }
        return xLegjobb;
    }

    public double Rosenbrock(int n, double[] x) {
        double S = 0;
        for (int i = 0; i < n-1; i++) {
            S = S + (100 * (x[i+1] - x[i] * x[i]) * (x[i+1] - x[i] * x[i]) + (1 - x[i]) * (1 - x[i]));
        }
        return S;
    }

    public double Csokkent(int n, double t, int k) {
        double alpha = 0.9;
        switch(n) {
            case 1: //exponencialis
                return t * Math.pow(alpha, k);
            case 2: //linearis
                return t / (1 + alpha * k);
            default: //kvadratikus
                return t / (1 + alpha * k * k);
        }
    }
}
