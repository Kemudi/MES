package com.company;

public class Main {

    public static final double PARAM = 25;
    public static final int NUMBER_OF_POINTS = 3;
    public static void main(String[] args) {
	    GlobalData.readData();
       // System.out.println(GlobalData.nW);

//        Node n1 = new Node(0.0,0.0,1);
//        Node n2 = new Node(4.0,0.0,2);
//        Node n3 = new Node(4.0,6.0,3);
//        Node n4 = new Node(0.0,6.0,4);

 //       Element element = new Element(n1,n2,n3,n4,15,NUMBER_OF_POINTS,PARAM);
 //       element.printLocalH();
        Frame frame = new Frame();
        frame.calculateTemp();
       // frame.printGlobalH();
//        for (Element element : frame.getElements()) {
//
//            for (int i = 0; i < element.gethLocal().length; i++) {
//                for (int j = 0; j < element.gethLocal()[i].length; j++) {
//                    System.out.print(element.gethLocal()[i][j] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
        //
     //   frame.printGlobalH();

//        for (Element element : frame.getElements()) {
//            for (int i = 0; i < element.getPl().length; i++) {
//                System.out.print(element.getPl()[i] + " ");
//            }
//            System.out.println();
//        }

      //  frame.printGlobalPl();

//        DerivativeCalculator derivativeCalculator = new DerivativeCalculator(2);
//        for (double[] doubles : derivativeCalculator.dNdEta()) {
//
//            for (int i = 0; i < doubles.length; i++) {
//
//                System.out.print(doubles[i]+" ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//        for (double[] doubles : derivativeCalculator.dNdKsi()) {
//
//            for (int i = 0; i < doubles.length; i++) {
//
//                System.out.print(doubles[i]+" ");
//            }
//            System.out.println();
//        }
    }
}
