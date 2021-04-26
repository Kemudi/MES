package com.company;

import java.util.Arrays;

public class Frame {
    private Node[] nodes;
    private Element[] elements;
    private double [][] hGlobal;
    private double [][] cGlobal;
    private double [] plGlobal;


    public Frame() {
        nodes = new Node[GlobalData.nN];
        elements = new Element[GlobalData.nE];
        double x, y;
        Node node;
        for (int i = 0; i < GlobalData.nW; i++) {
            for (int j = 0; j < GlobalData.nH; j++) {
                boolean bc = false;
                x = i * GlobalData.dX;
                y = j * GlobalData.dY;
                if(x == 0 || y ==0 || x == GlobalData.W || y == GlobalData.H){
                    bc = true;
                }
                int no = i*GlobalData.nH + j;
                nodes[no] = new Node(x,y,no,bc);
            }
        }
        int ID1,ID2,ID3,ID4;

        for (int i = 0; i < GlobalData.nW-1; i++) {
            for (int j = 0; j < GlobalData.nH-1; j++) {
                ID1 = j + i * GlobalData.nH;
                ID2 = ID1 + GlobalData.nH;
                ID3 = ID2 + 1;
                ID4 = ID1 + 1;
                int no = i*(GlobalData.nH-1)+j;
                elements[no]=new Element(nodes[ID1],nodes[ID2], nodes[ID3], nodes[ID4], no, Main.NUMBER_OF_POINTS, Main.PARAM, GlobalData.cp, GlobalData.ro, GlobalData.alfa, GlobalData.t0);
            }
        }

        hGlobal = calculateGlobalH();
        calculateGlobalC();
        plGlobal= calculatePlGlobal();
    }


    public void showElements(){
        for (int i = 0; i < elements.length; i++) {
            elements[i].showElement();
        }
    }

    private double [][] calculateGlobalH(){
        hGlobal = new double[nodes.length][nodes.length];
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].getNodes().length; j++) {
                for (int k = 0; k < elements[i].getNodes().length; k++) {
                    hGlobal[elements[i].getNodes()[j].getNodeId()][elements[i].getNodes()[k].getNodeId()] += elements[i].gethLocal()[j][k];
                }
            }

        }
        return hGlobal;
    }

    public void printGlobalH(){
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                System.out.print(hGlobal[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void calculateGlobalC(){
        cGlobal = new double[nodes.length][nodes.length];
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].getNodes().length; j++) {
                for (int k = 0; k < elements[i].getNodes().length; k++) {
                    cGlobal[elements[i].getNodes()[j].getNodeId()][elements[i].getNodes()[k].getNodeId()] += elements[i].getcLocal()[j][k];
                }
            }

        }
    }

    public void printGlobalC(){
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                System.out.print(cGlobal[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private double[] calculatePlGlobal(){
        double[] pGl = new double[nodes.length];
        for (Element element : elements) {
            for (int i = 0; i < element.getNodes().length; i++) {
                pGl[element.getNodes()[i].getNodeId()] += element.getPlElem()[i];
            }
        }
        return pGl;
    }

    public void printGlobalPl(){
        for (int i = 0; i < plGlobal.length; i++) {
            System.out.println(plGlobal[i]);
        }
    }

    public double[] calculateTemp(){
        double [] temp = new double[GlobalData.nN];
        for (int i = 0; i < GlobalData.nN; i++) {
            temp[i] = GlobalData.startTemp;
        }
        for (int i = 0; i < GlobalData.time/GlobalData.dTime; i++) {
            double [][] matrixH = new double[hGlobal.length][hGlobal[0].length];
            double [] pVector = new double[plGlobal.length];
            for (int j = 0; j < hGlobal.length; j++) {
                double cRowSumm = 0;        //wynik mnozenia macierzy c przez wektor temperatury
                for (int k = 0; k <hGlobal[0].length; k++) {
                    matrixH[j][k] = hGlobal[j][k] + (cGlobal[j][k]/GlobalData.dTime);
                    cRowSumm += (cGlobal[j][k]/GlobalData.dTime)*temp[k];
                }
                pVector[j] = -plGlobal[j] + cRowSumm;
            }

            temp = Gauss.lsolve(matrixH, pVector); //obliczam uklad rownan gaussem
            double [] pomAr = temp.clone();
            Arrays.sort(pomAr);

            System.out.println(i + ": min - " + pomAr[0] + ", max - " + pomAr[pomAr.length-1]); //w kazdym kroku czasowym

        }
        return temp;

    }

    public Element[] getElements() {
        return elements;
    }
}