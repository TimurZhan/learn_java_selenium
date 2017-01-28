package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username); //Тут заполняется поле username
    type(By.name("email"), email); //Тут заполняется поле email
    click(By.cssSelector("input[value='Signup']")); //Тут нажимается кнопка Signup
  }

  public void finish(String s, String password) {
    wd.get(s); //Тут проходим по ссылке
    type(By.name("password"), password); //Заполняем поле
    type(By.name("password_confirm"), password); //Заполняем поле
    click(By.cssSelector("input[value='Update User']")); //Тут нажимается кнопка
  }
}
