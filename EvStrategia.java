import java.util.Random;

public class EvStrategia {
    public static double max(double[] x) {
        double max = x[0];
        int n = x.length;
        for (int i = 1; i < n; i++) {
            if (max < x[i]) {
                max = x[i];
            }
        }
        return max;
    }

    public static double[] mutacio(double[] gen, double sigma, int dim) {
        Random rand = new Random();
        double ujSig = sigma * Math.exp(Math.pow(1/dim, 0.5) *  rand.nextGaussian());
        for (int i = 0; i < dim; i++) {
            gen[i] = gen[i] + ujSig * rand.nextGaussian();
        }
        return gen;
    }

    public static double Rechenberg(double p, double sig) {
        if (p > 1/5) {
            return sig / 0.85;
        } else if (p < 1/5) {
            return sig * 0.85;
        } else {
            return sig;
        }
    }

    public static double[] minKereses(Rosen rosen, int dim, int lam) {
        double[] gen = rosen.szimLehutes(dim);
        double sigma = Math.random() * max(gen);
        int nemValt = 0;
        int count = 0;
        while (nemValt < 100 && count < 100000) {
            int k = 0;
            int it = 0;
            double szulo = rosen.Rosenbrock(dim, gen);
            double[][] kov = new double[lam][];
            for (int i = 0; i < lam; i++) {
                kov[i] = mutacio(gen, sigma, dim);
            }
            for (double[] elem: kov) {
                if (rosen.Rosenbrock(dim, elem) < szulo) {
                    gen = elem;
                    szulo = rosen.Rosenbrock(dim, elem);
                    nemValt = 0;
                    k++;
                }
                it++;
            }
            sigma = Rechenberg(k/it, sigma);
            nemValt++;
            count++;
        }
        return gen;
    }

    public static void main(String[] args) {
        int dim = 2;
        Rosen rosen = new Rosen(dim);
        double[] x = minKereses(rosen, dim, 1000);
        System.out.println(rosen.Rosenbrock(dim, x));
        for (int i = 0; i < dim; i++) {
            System.out.println(x[i]);
        }
    }
}
