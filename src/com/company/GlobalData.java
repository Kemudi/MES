package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GlobalData {
    public static double H;
    public static double W;
    public static int nH;
    public static int nW;
    public static int nE;
    public static int nN;
    public static double dX;
    public static double dY;

    public static int cp;
    public static int ro;
    public static int t0;
    public static int alfa;
    public static int startTemp;
    public static int time;
    public static int dTime;

    public static void readData(){

        Scanner reader;

        {
            try {
                reader = new Scanner(new File("resources/dane.txt"));
                H = Double.parseDouble(reader.nextLine());
                W = Double.parseDouble(reader.nextLine());
                nH = Integer.parseInt(reader.nextLine());
                nW = Integer.parseInt(reader.nextLine());
                cp = Integer.parseInt(reader.nextLine());
                ro = Integer.parseInt(reader.nextLine());
                t0 = Integer.parseInt(reader.nextLine());
                alfa = Integer.parseInt(reader.nextLine());
                startTemp = Integer.parseInt(reader.nextLine());
                time = Integer.parseInt(reader.nextLine());
                dTime = Integer.parseInt(reader.nextLine());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        nE = (nH-1)*(nW-1);
        nN = nH * nW;
        dX = W/(nW-1);
        dY = H/(nH - 1);
    }


}
