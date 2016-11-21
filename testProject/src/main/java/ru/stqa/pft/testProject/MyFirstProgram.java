package ru.stqa.pft.testProject;

public class MyFirstProgram{

	public static void main(String[] args) {
		hello();

		double len = 5;
		System.out.println("Площадь квадрата  со стороной " + len + " = " + area(len));

		double e = 6;
		double t = 3;
		System.out.println("Площадь треугольника со сторонами " + e + " и " + t + " = " + area(e,t));
	}

	public static void hello() {
		System.out.println("Hello, world!");
	}

	public static double area(double i) {
		return i * i;
	}

	public static double area(double a, double b) {
		return  a * b;
	}

}