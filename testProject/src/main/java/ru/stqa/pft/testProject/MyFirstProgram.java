package ru.stqa.pft.testProject;

public class MyFirstProgram{

	public static void main(String[] args) {
		hello();

		Square m = new Square(15); // Создан новый объект m (экземпляр класса), в который передается класс (объект) типа Square со значением.
		System.out.println("Площадь квадрата  со стороной " + m.n + " = " +  m.area()); // Значение объекта m передается ф-ии area, в качестве параметра.

		Rectangle v = new Rectangle(6, 13);
		System.out.println("Площадь треугольника со сторонами " + v.a + " и " + v.b + " = " + v.area());
	}

	public static void hello() {
		System.out.println("Hello, world!");
	}

}