package com.company;

public class Element {
   private Node[] nodes;
   private double[][] hLocal;
   private int elementId;
   private  double[][] dNdKsi;
   private  double[][] dNdEta;
   private int no;
   private int alfa;
   private int temp;
   private double[] plElem;

    private double[][] cLocal;

    public Element(Node n1, Node n2, Node n3, Node n4, int id, int no, double param, double c, double ro, int alfa, int temp) {
       nodes = new Node[]{n1,n2,n3,n4};
       plElem = new double[4];
       elementId = id;
       hLocal = calculateLocalH(no, param);
       cLocal = calculateLocalC(no,c,ro);
       this.no = no;
       this.alfa = alfa;
       this.temp = temp;
       elHBCandPl();
    }

    public void showElement(){
        System.out.println("Element nr " + elementId + ": ");
        for (Node node : nodes) {
            System.out.println("\tNode nr " + node.getNodeId()+"\tx: " + node.getX() + "\ty: " + node.getY());
        }
    }

    private double[][][] calculateJacobians(DerivativeCalculator dCalculator, int no){
        dNdKsi = dCalculator.dNdKsi();
        dNdEta = dCalculator.dNdEta();
        double [][][] jacobians = new double[no*no][2][2];
        for (int i = 0; i < no*no; i++) {
            double dXdKsi = 0.0, dXdEta = 0.0, dYdKsi = 0.0, dYdEta = 0.0;
            for (int j = 0; j < 4; j++) {
                dXdEta += dNdEta[i][j] * nodes[j].getX();   // i - ktory wiersz w macierzy, j - po kolwi w wierszu n1, n2 ...
                dYdKsi += dNdKsi[i][j] * nodes[j].getY();   //nodes[j].get  - biore po kolei wsp x,y nodow
                dXdKsi += dNdKsi[i][j] * nodes[j].getX();
                dYdEta += dNdEta[i][j] * nodes[j].getY();
            }
            jacobians[i][0][0] = dXdKsi;
            jacobians[i][0][1] = dXdEta;
            jacobians[i][1][0] = dYdKsi;
            jacobians[i][1][1] = dYdEta;
        }

//        for (int i = 0; i <no*no; i++) {
//            for (int j = 0; j < 2 ; j++) {
//                for (int k = 0; k < 2 ; k++) {
//                    System.out.print(jacobians[i][j][k] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//
//        }
        return jacobians;
    }
    private double [][] calculateLocalH(int no, double param ){
        DerivativeCalculator dCalculator = new DerivativeCalculator(no);
       double[][][] jacobians = calculateJacobians(dCalculator, no);
       double[] dets = new double[no*no];

        for (int i = 0; i < dets.length; i++) {
            dets[i] = det(jacobians[i]);
        }

        double [][] dNdX = new double[no*no][4];
        double [][] dNdY = new double[no*no][4];

        for (int i = 0; i < jacobians.length; i++) {
            for (int j = 0; j < 4; j++) {
                dNdX[i][j] =(dNdKsi[i][j]*jacobians[i][1][1]+dNdEta[i][j]*(-jacobians[i][0][1]))*(1/dets[i]);
                dNdY[i][j] = (dNdKsi[i][j]*(-jacobians[i][1][0])+dNdEta[i][j]*jacobians[i][0][0])*(1/dets[i]);
            }
        }
        double [][][] pcLocH = new double[no*no][4][4];            //lokalne, dla pojedynczego punktu calkowania
        for (int i = 0; i < no*no; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    pcLocH[i][j][k] = ((dNdX[i][k] * dNdX[i][j])  + dNdY[i][k] * dNdY[i][j]) * dets[i] * param; //hpc x + hpcy
                }
            }
        }
        double[][] h = new double[4][4];        // lokalne dla elementu


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                h[i][j] = 0.0;
            }
        }

        for (int i = 0; i < no*no; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    h[j][k] += pcLocH[i][j][k]*dCalculator.getWeightKsi()[i] * dCalculator.getWeightEta()[i];  //tworze h lokalne calego elementu czyli sumuje wszystkie h wszystkicj pc
                }
            }
        }
        return h;
    }

    private double det(double[][] matrix){
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }

    public void printLocalH(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(gethLocal()[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private double [][] calculateLocalC(int no, double cp, double ro ){
        DerivativeCalculator d = new DerivativeCalculator(no);
        double[][] n = d.returnN();
        double[][][] jacobians = calculateJacobians(d, no);
        double[][][] cPoint = new double[no*no][nodes.length][nodes.length];
        for (int i = 0; i < no*no; i++) {
            for (int j = 0; j < nodes.length; j++) {
                for (int k = 0; k < nodes.length; k++) {
                    cPoint[i][j][k] = n[i][j] * n[i][k] * cp * ro * det(jacobians[i]);
                }
            }
        }

        double [][] c = new double[nodes.length][nodes.length];
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c.length; j++) {
                c[i][j] =0;
            }
        }
        for (int i = 0; i < no*no; i++) {
            for (int j = 0; j < nodes.length; j++) {
                for (int k = 0; k < nodes.length; k++) {
                    c[j][k] += cPoint[i][j][k] * d.getWeightEta()[i] * d.getWeightKsi()[i];
                }
            }
        }
//        for (int i = 0; i < c.length; i++) {
//            for (int j = 0; j < c[i].length ; j++) {
//                System.out.print(c[i][j] + " ");
//            }
//            System.out.println();
//        }
        return c;
    }

    private void elHBCandPl(){
        DerivativeCalculator dc = new DerivativeCalculator(no);
        double[][] hbcSide;

        if(nodes[0].isBc() && nodes[1].isBc()){     //sprawdzam czy bok jest na zewnatrz
            hbcSide = dc.calculateSideHBC(0,1);
            double l = Math.abs(nodes[1].getX() - nodes[0].getX())/2;
            addToH(hbcSide,l);
            double[] pSide = dc.calculateBottomPl(0,1);
            addToPlElem(pSide,l);   // pseudo p boku dodaje do P calego elemntu
        }

        if(nodes[1].isBc() && nodes[2].isBc()){
            hbcSide = dc.calculateSideHBC(1,2);
            double l = Math.abs(nodes[2].getY() - nodes[1].getY())/2;
            addToH(hbcSide,l);
            addToPlElem(dc.calculateBottomPl(1,2),l);
        }

        if(nodes[2].isBc() && nodes[3].isBc()){
            hbcSide = dc.calculateSideHBC(2,3);
            double l = Math.abs(nodes[3].getX() - nodes[2].getX())/2;
            addToH(hbcSide,l);
            addToPlElem(dc.calculateBottomPl(2,3),l);
        }

        if(nodes[3].isBc() && nodes[0].isBc()){
            hbcSide = dc.calculateSideHBC(3,0);
            double l = Math.abs(nodes[3].getY() - nodes[0].getY())/2;
            addToH(hbcSide,l);
            addToPlElem(dc.calculateBottomPl(3,0),l);
        }
    }

    private void addToH(double[][] matrix, double l){
        for (int i = 0; i < hLocal.length; i++) {
            for (int j = 0; j < hLocal[i].length; j++) {
                hLocal[i][j] += matrix[i][j] * alfa * l;
            }
        }
    }

    public void addToPlElem(double[] pSide, double l){
        for (int i = 0; i < 4; i++) {
            plElem[i] -= pSide[i] * l * temp * alfa;  //
        }
    }

    public Node[] getNodes() {
        return nodes;
    }

    public double[][] gethLocal() {
        return hLocal;
    }

    public double[][] getcLocal() {
        return cLocal;
    }

    public double[] getPlElem() {
        return plElem;
    }
}
