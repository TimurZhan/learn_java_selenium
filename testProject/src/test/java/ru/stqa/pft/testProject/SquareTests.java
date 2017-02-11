package ru.stqa.pft.testProject;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SquareTests {

  @Test
  public void testArea(){ //Создан тестовый метод (тест-кейс), проверяющий правильность работы метода "area" у класса "Square"
    Square m = new Square(5); //Тут вызывается на проверку тестируемый класс "Square". Т.е. создается новый квадрат со стороной 5

    /** Тут вызван вспомогательный класс "Assert" фреймоврка TestNG. Данный класс позволяет
     * увидеть более подробный отчет об ошибке, если таковая случится. Использован метод assertEquals этого класса, который
     * сравнивает два объекта в указанных в скобках: (актуальное значение, фактическое (проверяемое) значение).
     */
    Assert.assertEquals(m.area(), 25.0);

  }

}
