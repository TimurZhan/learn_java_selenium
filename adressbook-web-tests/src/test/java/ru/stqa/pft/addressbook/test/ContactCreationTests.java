package ru.stqa.pft.addressbook.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  //Провайдер тестовых данных. Это спец метод, нужный для цикличного создания тестов (В данном случаеи будет последовательно произведено 3 теста подряд).
  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException { //Итератор массива объектов
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))){ //Добавляем тест.данные из внешнего файла
      String json = "";
      String line =  reader.readLine(); //Читаем вытащенные тест.данные из файла (читается одна сточка).
      while (line != null){ //В цикле читаем каждую строчку файла до тех пор, пока они не закончатся.
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
      //Тут список объектов превращаем в поток, потом превращаем обратно в список для их итерации
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  //Провайдер тестовых данных. Это спец метод, нужный для цикличного создания тестов (В данном случаеи будет последовательно произведено 3 теста подряд).
  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException { //Итератор массива объектов
    try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {  //Добавляем тест.данные из внешнего файла
      String xml = "";
      String line =  reader.readLine(); //Читаем вытащенные тест.данные из файла (читается одна сточка).
      while (line != null){ //В цикле читаем каждую строчку файла до тех пор, пока они не закончатся.
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);//Тут обрабатываем аннотации, которые находятся в классе ContactData
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);//Тут коллекцию объектов ContactData из файла XML превращаем в список
      //Тут список объектов превращаем в поток, потом превращаем обратно в список для их итерации
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validContactsFromXml") //Привязываем тестовый провайдер к тесту.
  public void contactCreationTests(ContactData contact) {
    app.goTo().homePage();
    Contacts before = app.contact().all();//Тут происходит подсчет количества контактов(элементов в списке) ДО создания контакта.
    app.contact().create(contact, true);
    app.goTo().homePage();
    //Тут реализована проверка количества элементов (размер списка контактов), до и после. Необходимо, чтобы значения совпадали.
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();//Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ создания контакта.
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));//Cравниваем списки, после их упорядочивания.
  }

  @Test (enabled = false)
  public void testBadContactCreationTests() {
    app.goTo().homePage();
    Contacts before = app.contact().all();//Тут происходит подсчет количества контактов(элементов в списке) ДО создания контакта.
    ContactData contact = new ContactData()
            .withFirstname("sdfTe'st")
            .withLastname("Test3")
            .withAddress1("Test address")
            .withMobilePhoneNumber("8900045001")
            .withEmail("test1test3test2@mail.ru")
            .withGroup("Тест 2222");
    app.contact().create(contact, true);
    app.goTo().homePage();
    //Тут реализована проверка количества элементов (размер списка контактов), до и после. Необходимо, чтобы значения совпадали.
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();//Тут происходит подсчет количества контактов (элементов в списке) ПОСЛЕ создания контакта.
    assertThat(after, equalTo(before));//Cравниваем списки, после их упорядочивания.
  }

}
