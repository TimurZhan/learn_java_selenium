package ru.stqa.pft.addressbook.test;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class); //Тут инициализируем логгер.

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));


  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p){
    logger.info("Start test " + m.getName() + " with parametres " + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m){
    logger.info("Stop test " + m.getName());
  }


  public void verifyGroupListInUI(){
    if (Boolean.getBoolean("verifyUI")) { //Эта строчка настраивает возможность отключения данной проверки, если она не нужна.
      Groups dbGroups = app.db().groups(); //Берем списко групп из БД
      Groups uiGroups = app.group().all(); //Берем списко групп со страницы веб-приложения.

      /** Сравниваем списки. Причем у dbGroups, для сравнения берем только ID и Имя группы. Хедер и Футер мешают сравнению,
       * так как у uiGroups есть только ID и Имя группы.*/
      assertThat(uiGroups, equalTo(dbGroups.stream().map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyContactListInUI(){
    if (Boolean.getBoolean("verifyUI")) { //Эта строчка настраивает возможность отключения данной проверки, если она не нужна.
      Contacts dbContacts = app.db().contacts(); //Берем списко групп из БД
      Contacts uiContacts = app.contact().all(); //Берем списко групп со страницы веб-приложения.

      /** Сравниваем списки. Причем у dbContacts, для сравнения берем только ID и Имя группы. Хедер и Футер мешают сравнению,
       * так как у uiContacts есть только ID и Имя группы.*/
      assertThat(uiContacts, equalTo(dbContacts.stream().map((c) -> new ContactData()
              .withId(c.getId())
              .withFirstname(c.getFirstname())
              .withLastname(c.getLastname())).collect(Collectors.toSet())));
    }
  }

}