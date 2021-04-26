package com.company;

public class Calka {
    public static double f(double a, double b){
        return -2*Math.pow(a,2) * b + 2*a*b +4;
    }

    public static double calka(int schemat){
        double [] w;
        double [] p;

        if(schemat == 2){
            w = new double[]{1.0,1.0};
            p = new double[]{-1/(Math.sqrt(3)), 1/(Math.sqrt(3))};
        }
       else{
            w = new double[]{5.0/9.0, 8.0/9.0, 5.0/9.0};
            p = new double[]{-Math.sqrt(3.0/5.0), 0.0, Math.sqrt(3.0/5.0)};
        }

        double wynik =0.0;
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w.length; j++) {
                wynik+= w[i]*w[j]*f(p[i],p[j]);

            }
        }
        return wynik;
    }

    public static void main(String[] args) {
        System.out.println(calka(2));
    }
}
