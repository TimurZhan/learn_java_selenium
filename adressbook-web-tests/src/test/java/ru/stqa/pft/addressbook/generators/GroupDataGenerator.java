package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Генератор тестовых данных для Групп
 */
public class GroupDataGenerator {

  //Создан атрибут для создания опции командной строки, под названием "-c"
  @Parameter(names = "-c", description = "Group count")
  public int count;

  //Создан атрибут для создания опции командной строки, под названием "-f"
  @Parameter(names = "-f", description = "Target file")
  public String file;

  //Создан атрибут для создания опции командной строки, под названием "-d"
  @Parameter(names = "-d", description = "Data format")
  public String format;

  //Данная функция, в качестве параметра, принимает массив строк (кол-во групп и путь к файлу).
  public static void main(String[] args) throws IOException {
    GroupDataGenerator generator = new GroupDataGenerator();//Создан объект текущего класса

    //Создан объект JCommander. Он нужен для правильного вывода сообщения на консоль. У которого параметр generator должен быть заполнен атрибутами count и file.
    //Парметр args заполняется теми опциями, которые переданы в коммандонй строке.
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex){ //Если исключение возникло, то выводится сообщение на консоль
      jCommander.usage(); //Метод usage() выводит информацию о том, какие опции доступны (А доступны две опции: -c и -f).
      return;
    }
    generator.run();//Тут производится запуск этого объекта
  }

  private void run() throws IOException {
    List<GroupData> groups = generateGroups(count); //Генератор тестовых данных
    if (format.equals("csv")){
      saveAsCsv(groups, new File(file)); //Сохранение тесовых данных в файл, в формате csv
    }  else if (format.equals("xml")){
      saveAsXml(groups, new File(file)); //Сохранение тесовых данных в файл в формате xml
    } else if (format.equals("json")){
      saveAsJson(groups, new File(file)); //Сохранение тесовых данных в файл в формате xml
    } else {
      System.out.println("Ne ponyatnyi format " + format);
    }
  }

  //Реализован метод для преобразования тестовых данных объекта GroupData в файл формата JSON.
  private void saveAsJson(List<GroupData> groups, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(groups);
    try (Writer writer = new FileWriter(file)) { // "Writer writer" Открывает файл для записи, в него, тестовых данных. А try автоматически закрывет writer после его использования
      writer.write(json);
    }
  }

  //Реализован метод для преобразования тестовых данных объекта GroupData в файл формата XML.
  private void saveAsXml(List<GroupData> groups, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class); //Настройка для файла XML. Тут указываем то, как будет называться тег. Его название берется из класса GroupData
    String xml = xstream.toXML(groups); //Тут преобразовываем объект в строчку, которая будет содержаться в файле XML.
    try (Writer writer = new FileWriter(file)) { // "Writer writer" Открывает файл для записи, в него, тестовых данных. А try автоматически закрывет writer после его использования
      writer.write(xml);
    }
  }

  //"throws IOException" означает, что если возникнет исключение, оно будет передано выше другому мотоду (в частности main)
  private void saveAsCsv(List<GroupData> groups, File file) throws IOException {
    try (Writer writer = new FileWriter(file)) {//Открывает файл для записи, в него, тестовых данных)
      for (GroupData groups1 : groups){//Проходимся по всем группам в списке.
        //Записываем в файл каждую группу по очередно. Где Name, Header, Footer (данные группы) ставится вместо %s
        writer.write(String.format("%s;%s;%s\n", groups1.getName(), groups1.getHeader(), groups1.getFooter()));
      }
    }
  }

  private List<GroupData> generateGroups(int count) {
    List<GroupData> groups2 = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++){
      groups2.add(
              new GroupData()
              .withName(String.format("test %s", i))
              .withHeader(String.format("header %s", i))
              .withFooter(String.format("footer %s", i))
      );
    }
    return groups2;
  }

}
