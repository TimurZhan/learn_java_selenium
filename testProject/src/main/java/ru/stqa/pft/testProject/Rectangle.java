package ru.stqa.pft.testProject;

/**
 * Данный класс описывает структуру объекта, хранящего информацию о треугольнике. С двумя атрибутами.
 */
public class Rectangle {

  public double a; //Объявлен атрибут a
  public double b; //Объявлен атрибут b

  public Rectangle (double i, double q){
    this.a = i;
    this.b = q;
  } // Объявлен конструктор, в котором значение атрибутов a и b равно значению передаваемых в конструктор параметров double i и double q

  public double area() {
    return  this.a * this.b;
  } // Объявлен метод area, который проводит вычисление и возвращается результат.

}

