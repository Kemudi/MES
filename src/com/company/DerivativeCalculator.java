package com.company;

public class DerivativeCalculator {
    private int numberOfPoints;
    private double [] ksi;
    private double [] eta;
    private double [] weightKsi;
    private double [] weightEta;

    public DerivativeCalculator(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
        if(numberOfPoints == 2){
            ksi = new double[] {
                    -(1.0/Math.sqrt(3.0)),
                    (1.0 / Math.sqrt(3.0)),
                    (1.0 / Math.sqrt(3.0)),
                    -(1.0 / Math.sqrt(3.0))
            };

            weightKsi = new double[] {
                   1.0,1.0,1.0,1.0
            };

            eta = new double[] {
                    -(1.0/Math.sqrt(3.0)),
                    -(1.0 / Math.sqrt(3.0)),
                    (1.0 / Math.sqrt(3.0)),
                    (1.0 / Math.sqrt(3.0))
            };

            weightEta = new double[] {
                    1.0,1.0,1.0,1.0
            };

        }
        else if(numberOfPoints == 3) {
            ksi = new double[] {
                    -(Math.sqrt(3.0/5.0)),
                    0.0,
                    (Math.sqrt(3.0/5.0)),
                    -(Math.sqrt(3.0/5.0)),
                    0.0,
                    (Math.sqrt(3.0/5.0)),
                    -(Math.sqrt(3.0/5.0)),
                    0.0,
                    (Math.sqrt(3.0/5.0))
            };

            weightKsi = new double[] {
                    5.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0, 5.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0, 5.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0
            };

            eta = new double[] {
                    -(Math.sqrt(3.0/5.0)),
                    -(Math.sqrt(3.0/5.0)),
                    -(Math.sqrt(3.0/5.0)),
                    0.0,
                    0.0,
                    0.0,
                    (Math.sqrt(3.0/5.0)), (Math.sqrt(3.0/5.0)), (Math.sqrt(3.0/5.0))
            };

            weightEta = new double[] {
                    5.0 / 9.0, 5.0 / 9.0, 5.0 / 9.0, 8.0 / 9.0, 8.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0, 5.0 / 9.0, 5.0 / 9.0
            };
        }

        else if(numberOfPoints == 4) {
            double a = (3.0 / 7.0) + (2.0 / 7.0) * Math.sqrt(6.0 / 5.0);
            double b = (3.0 / 7.0) - (2.0 / 7.0) * Math.sqrt(6.0 / 5.0);
            ksi = new double[] {
                    -(Math.sqrt(a)),
                    -(Math.sqrt(b)),
                    (Math.sqrt(b)),
                    (Math.sqrt(a)),
                    -(Math.sqrt(a)),
                    -(Math.sqrt(b)),
                    (Math.sqrt(b)),
                    (Math.sqrt(a)),
                    -(Math.sqrt(a))
                    -(Math.sqrt(b)),
                    (Math.sqrt(b)),
                    (Math.sqrt(a)),
                    -(Math.sqrt(a)),
                    -(Math.sqrt(b)),
                    (Math.sqrt(b)),
                    (Math.sqrt(a)),
            };

            weightKsi = new double[] {

            };

            eta = new double[] {
                    -(Math.sqrt(3.0/5.0)),
                    -(Math.sqrt(3.0/5.0)),
                    -(Math.sqrt(3.0/5.0)),
                    0.0,
                    0.0,
                    0.0,
                    (Math.sqrt(3.0/5.0)), (Math.sqrt(3.0/5.0)), (Math.sqrt(3.0/5.0))
            };

            weightEta = new double[] {
                    5.0 / 9.0, 5.0 / 9.0, 5.0 / 9.0, 8.0 / 9.0, 8.0 / 9.0, 8.0 / 9.0, 5.0 / 9.0, 5.0 / 9.0, 5.0 / 9.0
            };
        }


    }

    public double[][] dNdKsi(){
        double[][] d = new double[numberOfPoints*numberOfPoints][4];

        for (int i = 0; i < numberOfPoints*numberOfPoints; i++) {
            for (int j = 0; j < 4; j++) {
                if(j==0){
                    d[i][j] = -(1.0/4.0) * (1-eta[i]);
                }
                else if(j==1){
                    d[i][j] = (1.0/4.0) * (1-eta[i]);
                }
                else if(j==2){
                    d[i][j] = (1.0/4.0) * (1+eta[i]);
                }
                else if(j==3){
                    d[i][j] = -(1.0/4.0) * (1+eta[i]);
                }
            }
        }
        return d;
    }



    public double[][] dNdEta(){
        double[][] d = new double[numberOfPoints*numberOfPoints][4];

        for (int i = 0; i < numberOfPoints*numberOfPoints; i++) {
            for (int j = 0; j < 4; j++) {
                if(j==0){
                    d[i][j] = -(1.0/4.0) * (1-ksi[i]);
                }
                else if(j==1){
                    d[i][j] = -(1.0/4.0) * (1+ksi[i]);
                }
                else if(j==2){
                    d[i][j] = (1.0/4.0) * (1+ksi[i]);
                }
                else if(j==3){
                    d[i][j] = (1.0/4.0) * (1-ksi[i]);
                }
            }
        }
        return d;
    }

    public double[][] returnN(){
        double[][] n = new double[numberOfPoints*numberOfPoints][4];

        for (int i = 0; i < numberOfPoints*numberOfPoints; i++) {
            for (int j = 0; j < 4; j++) {
                if(j==0){
                    n[i][j] = (1.0/4.0) * (1-ksi[i])*(1 - eta[i]);
                }
                else if(j==1){
                    n[i][j] = (1.0/4.0) * (1+ksi[i])*(1 - eta[i]);
                }
                else if(j==2){
                    n[i][j] = (1.0/4.0) * (1+ksi[i])*(1 + eta[i]);
                }
                else if(j==3){
                    n[i][j] = (1.0/4.0) * (1-ksi[i])*(1 + eta[i]);
                }
               // System.out.println(n[i][j]);
            }
        }
        return n;
    }

    public double [][] calculateSideHBC(int nodeStart, int nodeEnd){
        double[][] hbcSide = new double[4][4];
        double[][] n = new double[numberOfPoints][4];
        double[][][] hbcPoints = new double[numberOfPoints][4][4];
        double[] localKsi = new double[numberOfPoints];
        double[] localEta = new double[numberOfPoints];
        if(nodeStart == 0 && nodeEnd == 1) {
            for (int i = 0; i < numberOfPoints; i++) {
                localKsi[i] = ksi[i];
            }
            for (int i = 0; i < numberOfPoints; i++) {
                localEta[i] = -1;
            }

        }
        else if (nodeStart == 1 && nodeEnd == 2){
            for (int i = 0; i < numberOfPoints; i++) {
                localKsi[i] = 1;
            }
            for (int i = 0; i < numberOfPoints; i++) {
                localEta[i] = ksi[i];
            }

        }
        else if (nodeStart == 2 && nodeEnd == 3){
            for (int i = 0; i < numberOfPoints; i++) {
                localKsi[i] = ksi[i];
            }
            for (int i = 0; i < numberOfPoints; i++) {
                localEta[i] = 1;
            }

        }
        else if (nodeStart == 3 && nodeEnd == 0){
            for (int i = 0; i < numberOfPoints; i++) {
                localKsi[i] = -1;
            }
            for (int i = 0; i < numberOfPoints; i++) {
                localEta[i] = ksi[i];
            }

        }

        for (int i = 0; i < numberOfPoints; i++) {
            for (int j = 0; j < 4; j++) {
                if(j==0){
                    n[i][j] = (1.0/4.0) * (1-localKsi[i])*(1 - localEta[i]);
                }
                else if(j==1){
                    n[i][j] = (1.0/4.0) * (1+localKsi[i])*(1 - localEta[i]);
                }
                else if(j==2){
                    n[i][j] = (1.0/4.0) * (1+localKsi[i])*(1 + localEta[i]);
                }
                else if(j==3){
                    n[i][j] = (1.0/4.0) * (1-localKsi[i])*(1 + localEta[i]);
                }
                // System.out.println(n[i][j]);
            }
        }

        for(int i = 0; i < numberOfPoints; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                   hbcPoints[i][j][k] = n[i][j] * n[i][k];// kazdy pc trzyma 2 wymiarowe hbc
                }
            }
        }

        for(int i = 0; i < numberOfPoints; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    hbcSide[j][k] += hbcPoints[i][j][k] * weightKsi[i];

                    // System.out.println(n[i][j]);
                }
            }
        }

        return hbcSide;
    }

    public double[] calculateBottomPl(int nodeStart, int nodeEnd){
        double[] localKsi = new double[numberOfPoints];
        double[] localEta = new double[numberOfPoints];
        if(nodeStart == 0 && nodeEnd == 1) {
            for (int i = 0; i < numberOfPoints; i++) {
                localKsi[i] = ksi[i];
            }
            for (int i = 0; i < numberOfPoints; i++) {
                localEta[i] = -1;
            }

        }
        else if (nodeStart == 1 && nodeEnd == 2){
            for (int i = 0; i < numberOfPoints; i++) {
                localKsi[i] = 1;
            }
            for (int i = 0; i < numberOfPoints; i++) {
                localEta[i] = ksi[i];
            }

        }
        else if (nodeStart == 2 && nodeEnd == 3){
            for (int i = 0; i < numberOfPoints; i++) {
                localKsi[i] = ksi[i];
            }
            for (int i = 0; i < numberOfPoints; i++) {
                localEta[i] = 1;
            }

        }
        else if (nodeStart == 3 && nodeEnd == 0){
            for (int i = 0; i < numberOfPoints; i++) {
                localKsi[i] = -1;
            }
            for (int i = 0; i < numberOfPoints; i++) {
                localEta[i] = ksi[i];
            }

        }

        double[][] pPC = new double[numberOfPoints][4];
        for (int i = 0; i < numberOfPoints; i++) {
            for (int j = 0; j < 4; j++) {
                if(j==0){
                    pPC[i][j] = (1.0/4.0) * (1-localKsi[i])*(1 - localEta[i]);            //to sa p punktow calkowania - n dla wsp pkt calkowania na boku zewnetrznym
                }
                else if(j==1){
                    pPC[i][j] = (1.0/4.0) * (1+localKsi[i])*(1 - localEta[i]);
                }
                else if(j==2){
                    pPC[i][j] = (1.0/4.0) * (1+localKsi[i])*(1 + localEta[i]);
                }
                else if(j==3){
                    pPC[i][j] = (1.0/4.0) * (1-localKsi[i])*(1 + localEta[i]);
                }
                // System.out.println(n[i][j]);
            }
        }

        double [] pSide = new double[4];
        for (int i = 0; i < numberOfPoints; i++) {
            for (int j = 0; j < 4; j++) {
                pSide[j] += pPC[i][j] * weightKsi[i];      //sumuje p punktow calkowania i mnoze przez wagi

            }
        }
        return pSide;
    }


    public double[] getWeightKsi() {
        return weightKsi;
    }

    public double[] getWeightEta() {
        return weightEta;
    }
}
