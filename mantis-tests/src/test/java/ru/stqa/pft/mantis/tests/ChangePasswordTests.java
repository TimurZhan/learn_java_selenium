package ru.stqa.pft.mantis.tests;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase{

  @BeforeMethod
  public void startMailServer(){
    app.mail().start();
  }

  @Test
  public void testChangePassword() throws IOException, MessagingException {
    long now = System.currentTimeMillis(); //Функция System.currentTimeMillis() возвращает текущее время
    String user = String.format("user%s", now); //Тут "now" подставляется вместо %s.
    String password = "password";
    String email = String.format("user%s@localhost.localdomain", now); //Тут "now" подставляется вместо %s.
    app.registration().start(user, email);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);//Тут ждем 2 письма в течени 10000млс (10 сек).
    String s = findConfirmationLink(mailMessages, email);//Тут находим письмо с указанным адресом (в переменной email) и извлекаем из него ссылку
    app.registration().finish(s, password);
    assertTrue(app.newSession().login(user, password)); //Тут производим проверку на то, что вход осуществлен корректно.
  }

  //В данном методе превращаем письмо
  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();

    /**В выражении "VerbalExpression.regex().find("http://")" ищем текст, у которого содержание "http://".
     *  Затем, в выражении "nonSpace()" идет какое-то кол-во НЕ проблеьных символов, где "oneOrMore()" указывает один или больше.
     *  Затем, полученный текст ссылки помещается в переменную regex.
     */
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();

    //Тут применяем полученный текст к тексту полученного письма. Затем возвращаем получившийся реузльтат.
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true) //Выражение alwaysRun = true означает то, что сервер остановится в любом случае. Даже если тест упадет
  public void stopMailServer(){
    app.mail().stop();
  }

}
