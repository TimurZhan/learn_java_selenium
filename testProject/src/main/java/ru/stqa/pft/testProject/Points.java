package ru.stqa.pft.testProject;

public class Points {

  public static void main(String[] args) {

    Point p1 = new Point();
    Point p2 = new Point();
    p1.x1 = 5;
    p1.x2 = 10;
    p2.y1 = 5;
    p2.y2 = 10;

    System.out.println("Расстояние между точками X и Y = " + distance(p1, p2));
  }

  public static double distance(Point p1, Point p2) {

    return Math.sqrt(((p1.x2 - p1.x1)*(p1.x2 - p1.x1)) + ((p2.y2 - p2.y1)*(p2.y2 - p2.y1)));

  }

}