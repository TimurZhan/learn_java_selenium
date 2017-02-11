package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Генератор тестовых данных для Контактов
 */
public class ContactDataGenerator {

  //Создан атрибут для создания опции командной строки, под названием "-c"
  @Parameter(names = "-c", description = "Contact count")
  public int count;

  //Создан атрибут для создания опции командной строки, под названием "-f"
  @Parameter(names = "-f", description = "Target file")
  public String file;

  //Создан атрибут для создания опции командной строки, под названием "-d"
  @Parameter(names = "-d", description = "Data format")
  public String format;

  //Данная функция, в качестве параметра, принимает массив строк (кол-во контактов и путь к файлу).
  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();//Создан объект текущего класса


    //Создан объект JCommander. Он нужен для правильного вывода сообщения на консоль. У которого параметр generator должен быть заполнен атрибутами count и file.
    //Парметр args заполняется теми опциями, которые переданы в коммандонй строке.
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex){ //Если исключение возникло, то выводится сообщение на консоль
      jCommander.usage(); //Метод usage() выводит информацию о том, какие опции доступны (А доступны три опции: -c, -f и -d).
      return;
    }
    generator.run();//Тут производится запуск этого объекта
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count); //Генератор тестовых данных
    if (format.equals("json")){
      saveAsJson(contacts, new File(file)); //Сохранение тесовых данных в файл в формате xml
    } else if (format.equals("xml")){
      saveAsXml(contacts, new File(file)); //Сохранение тесовых данных в файл в формате xml
    } else {
      System.out.println("Ne ponyatnyi format " + format);
    }
  }

  //Реализован метод для преобразования тестовых данных объекта ContactData в файл формата JSON.
  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) { // "Writer writer" Открывает файл для записи, в него, тестовых данных. А try автоматически закрывет writer после его использования
      writer.write(json);
    }

  }

  //Реализован метод для преобразования тестовых данных объекта ContactData в файл формата XML.
  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class); //Настройка для файла XML. Тут указываем то, как будет называться тег. Его название берется из класса ContactData
    String xml = xstream.toXML(contacts); //Тут преобразовываем объект в строчку, которая будет содержаться в файле XML.
    try (Writer writer = new FileWriter(file)) { // "Writer writer" Открывает файл для записи, в него, тестовых данных. А try автоматически закрывет writer после его использования
      writer.write(xml);
    }
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts2 = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++){
      contacts2.add(
              new ContactData()
                      .withFirstname(String.format("Firstname %s", i))
                      .withLastname(String.format("Lastname %s", i))
                      .withAddress1(String.format("Address1 %s", i))
                      .withMobilePhoneNumber(String.format("8900045001", i))
                      .withEmail(String.format("test1test3test2@mail.ru", i))
                      .withPhoto(new File("src/test/resources/rt.jpg"))
      );
    }
    return contacts2;
  }

}

