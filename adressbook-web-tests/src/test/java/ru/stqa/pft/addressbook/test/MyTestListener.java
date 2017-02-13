package ru.stqa.pft.addressbook.test;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.yandex.qatools.allure.annotations.Attachment;


//Расширение, нужное для создания скирншота, в случае, если тест упадет.
public class MyTestListener implements ITestListener {
  @Override
  public void onTestStart(ITestResult result) {

  }

  @Override
  public void onTestSuccess(ITestResult result) {

  }

  @Override
  public void onTestFailure(ITestResult result) {
    ApplicationManager app = (ApplicationManager) result.getTestContext().getAttribute("app"); //Получаем доступ к ApplicationManager через контекст выполнения тестов
    saveScreenshot(app.takeScreenchot());
  }

  //Прицепляем скриншот к отчету Allure
  @Attachment(value = "Page screenshot", type = "image/png")
  public byte[] saveScreenshot(byte[] screenShot) {
    return screenShot;
  }

  @Override
  public void onTestSkipped(ITestResult result) {

  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

  }

  @Override
  public void onStart(ITestContext context) {

  }

  @Override
  public void onFinish(ITestContext context) {

  }
}