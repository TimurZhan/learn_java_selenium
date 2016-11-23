package ru.stqa.pft.testProject;

/**
 * Данный класс описывает структуру объекта, хранящего информацию о квадрате. С одним атрибутом n
 */
public class Square {

  public double n; //Объявлен атрибут n

  public Square (double i){
    this.n = i;
  } // Объявлен конструктор, в котором значение атрибута n равно значению передаваемого в конструктор параметра double i.

  public double area() {
    return this.n * this.n;
  } // Объявлен метод area, который проводит вычисление и возвращается результат.

}
