package ru.stqa.pft.testProject;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SquareTests {

  @Test
  public void testArea(){
    Square m = new Square(5);
    Assert.assertEquals(m.area(), 20.0);
  }
}
