public class Calculos {
    public static double[][] RK4(double alfa, double beta, double gamma, double ro, int dias, double h, double varNPop) {
        double passosNumDia = 1 / h;


        int i = 0;
        double k1S = 0;
        double k2S = 0;
        double k3S = 0;
        double k4S = 0;
        double k1I = 0;
        double k2I = 0;
        double k3I = 0;
        double k4I = 0;
        double k1R = 0;
        double k2R = 0;
        double k3R = 0;
        double k4R = 0;

        double ynParaS = 0;
        double ynParaI = 0;
        double ynParaR = 0;

        double y0ParaS = varNPop - 1;
        double y0ParaI = 1;
        double y0ParaR = 0;

        double[][] resultadoYn = new double[dias][5];
        resultadoYn[0][0] = 0;
        resultadoYn[0][1] = y0ParaS;
        resultadoYn[0][2] = y0ParaI;
        resultadoYn[0][3] = y0ParaR;
        resultadoYn[0][4] = varNPop;

        for (i = 1; i < dias; i++) {
            for (int j = 0; j < passosNumDia; j++) {

                k1S = derivadaSOrdemT(beta, y0ParaS, y0ParaI);
                k1I = derivadaIOrdemT(ro, beta, y0ParaS, y0ParaI, gamma, alfa, y0ParaR);
                k1R = derivadaROrdemT(gamma, y0ParaI, alfa, y0ParaR, ro, beta, y0ParaS);

                k2S = derivadaSOrdemT(beta, y0ParaS + (h/2)*k1S, y0ParaI + (h/2)*k1I);
                k2I = derivadaIOrdemT(ro, beta, y0ParaS + (h/2)*k1S, y0ParaI +(h/2)*k1I, gamma, alfa, y0ParaR + (h/2)*k1R);
                k2R = derivadaROrdemT(gamma, y0ParaI + (h/2)*k1I, alfa, y0ParaR + (h/2)*k1R, ro, beta, y0ParaS + (h/2)*k1S);

                k3S = derivadaSOrdemT(beta, y0ParaS + (h/2)*k2S, y0ParaI + (h/2)*k2I);
                k3I = derivadaIOrdemT(ro, beta, y0ParaS + (h/2)*k2S, y0ParaI + (h/2)*k2I, gamma, alfa, y0ParaR + (h/2)*k2R);
                k3R = derivadaROrdemT(gamma, y0ParaI + (h/2)*k2I, alfa, y0ParaR + (h/2)*k2R, ro, beta, y0ParaS + (h/2)*k2S);

                k4S = derivadaSOrdemT(beta, y0ParaS + h*k3S, y0ParaI + h*k3I);
                k4I = derivadaIOrdemT(ro, beta, y0ParaS + h*k3S, y0ParaI + h*k3I, gamma, alfa, y0ParaR + h*k3R);
                k4R = derivadaROrdemT(gamma, y0ParaI + h*k3I, alfa, y0ParaR + h*k3R, ro, beta, y0ParaS + h*k3S);

                ynParaS = y0ParaS + (h/6) * (k1S + 2*k2S + 2*k3S + k4S);
                ynParaI = y0ParaI + (h/6) * (k1I + 2*k2I + 2*k3I + k4I);;
                ynParaR = y0ParaR + (h/6) * (k1R + 2*k2R + 2*k3R + k4R);;

                y0ParaS = ynParaS;
                y0ParaI = ynParaI;
                y0ParaR = ynParaR;


            }
            resultadoYn[i][0] = i;
            resultadoYn[i][1] = ynParaS;
            resultadoYn[i][2] = ynParaI;
            resultadoYn[i][3] = ynParaR;
            resultadoYn[i][4] = ynParaS + ynParaI + ynParaR;
        }

        return resultadoYn;
    }

    //−β.S.I
    public static double derivadaSOrdemT(double beta, double s, double i) {
        return -beta * s * i;
    }

    //ρ.β.S.I − γ.I + α.R
    public static double derivadaIOrdemT(double ro, double beta, double s, double i, double gama, double alfa, double r) {
        return ro * beta * s * i - gama * i + alfa * r;
    }

    //γ.I − α.R + (1 − ρ).β.S.I
    public static double derivadaROrdemT(double gama, double i, double alfa, double r, double ro, double beta, double s) {
        return gama * i - alfa * r + (1 - ro) * beta * s * i;
    }


    public static double[][] euler(int dias, double passo, int populacao, double beta, double gama, double ro, double alfa) {


        //As 3 funções
        double sn = 0;
        double in = 0;
        double rn = 0;

        //Valores iniciais das 3 funções
        double s0 = populacao - 1;
        double i0 = 1;
        double r0 = 0;

        //Precisamos de saber quantos passos tem um dia para podermos exportar devidamente para ficheiro csv
        double passosNumDia = 1 / passo;

        //A matriz de valores a devolver
        double[][] matriz = new double[dias][5];
        matriz[0][0] = 0;
        matriz[0][1] = s0;
        matriz[0][2] = i0;
        matriz[0][3] = r0;
        matriz[0][4] = populacao;

        //O método de Euler
        for (int i = 1; i < dias; i++) {
            for (int j = 0; j < passosNumDia; j++) {
                sn = s0 + passo * derivadaSOrdemT(beta, s0, i0);
                in = i0 + passo * derivadaIOrdemT(ro, beta, s0, i0, gama, alfa, r0);
                rn = r0 + passo * derivadaROrdemT(gama, i0, alfa, r0, ro, beta, s0);

                s0 = sn;
                i0 = in;
                r0 = rn;

            }

            matriz[i][0] = i;
            matriz[i][1] = sn;
            matriz[i][2] = in;
            matriz[i][3] = rn;
            matriz[i][4] = sn + in + rn;


        }

        return matriz;
    }
}
