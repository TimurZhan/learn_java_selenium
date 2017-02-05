package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.stqa.pft.mantis.model.MailMessage;
import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//Умеет ходить по протоколу telnet на почтовый сервер, и может создавать там пользователей.
// Также, умеет получать почту по протоколу pop3.
public class JamesHelper {

  private ApplicationManager app;

  private TelnetClient telnet;
  private InputStream in;
  private PrintStream out;

  private Session mailSession;
  private Store store;
  private String mailserver;

  public JamesHelper(ApplicationManager app) {
    this.app = app;
    telnet = new TelnetClient(); //При инициализации JamesHelper, создается TelnetClient
    mailSession = Session.getDefaultInstance(System.getProperties()); //Тут создается почтовая сессия
  }

  //Это метод для проверки, что юзер существует
  public boolean doesUserExist(String name) {
    initTelnetSession();
    write("verify " + name);
    String result = readUntil("exist");
    closeTelnetSession();
    return result.trim().equals("User " + name + " exist");
  }

  //Тут создается новый юзер
  public void createUser(String name, String passwd) {
    initTelnetSession(); //Устанавливается соединение по протоколу Telnet
    write("adduser " + name + " " + passwd); //Дается команда для добавления юзера
    String result = readUntil("User " + name + " added"); //Ожидаем получения текста на консоль, который указан в скобках.
    closeTelnetSession(); //Соодинение разрывается.
  }

  //Это метод для удаления юзера
  public void deleteUser(String name) {
    initTelnetSession();
    write("deluser " + name);
    String result = readUntil("User " + name + " deleted");
    closeTelnetSession();
  }

  //Тут инициализируется сессия по протоколу Telnet
  private void initTelnetSession() {
    mailserver = app.getProperty("mailserver.host"); //Тут получаем свойство из конфигурационного файла local.properties
    int port = Integer.parseInt(app.getProperty("mailserver.port")); //Тут получаем свойство из конфигурационного файла local.properties
    String login = app.getProperty("mailserver.adminlogin"); //Тут получаем свойство из конфигурационного файла local.properties
    String password = app.getProperty("mailserver.adminpassword"); //Тут получаем свойство из конфигурационного файла local.properties

    try {
      telnet.connect(mailserver, port); //Устанавливается соединение с почтовым сервером
      in = telnet.getInputStream(); //Перехватывается входной поток. Нужно для считки данных, которые Telnet отправляет нам
      out = new PrintStream(telnet.getOutputStream()); //Перехватывается выходной поток. Нужен для передачи команд Telnetу

    } catch (Exception e) {
      e.printStackTrace();
    }

    //Собственно тут происходит чтение и запись данных для Telnet. Тут описывается схема взаимодействия с сервером.
    readUntil("Login id:");
    write("");
    readUntil("Password:");
    write("");

    readUntil("Login id:");
    write(login);
    readUntil("Password:");
    write(password);

    readUntil("Welcome " + login + ". HELP for a list of commands");
  }

  //Чтение данных перехваченных с сервера Telnet
  private String readUntil(String pattern) {
    try {
      char lastChar = pattern.charAt(pattern.length() - 1);
      StringBuffer sb = new StringBuffer();
      char ch = (char) in.read();
      while (true) {
        System.out.print(ch);
        sb.append(ch);
        if (ch == lastChar) {
          if (sb.toString().endsWith(pattern)) {
            return sb.toString();
          }
        }
        ch = (char) in.read();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  //Запись и отправка данных на сервер Telnet
  private void write(String value) {
    try {
      out.println(value);
      out.flush();
      System.out.println(value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void closeTelnetSession() {
    write("quit");
  }

  //Этот метод похволяет удалить все письма, которые пользовтель мог получить.
  public void drainEmail(String username, String password) throws MessagingException {
    Folder inbox = openInbox(username, password);
    for (Message message : inbox.getMessages()) {
      message.setFlag(Flags.Flag.DELETED, true);
    }
    closeFolder(inbox);
  }

  private void closeFolder(Folder folder) throws MessagingException {
    folder.close(true);
    store.close();
  }

  //Метод осуществляющий открытие почтового ящика
  private Folder openInbox(String username, String password) throws MessagingException {
    store = mailSession.getStore("pop3");
    store.connect(mailserver, username, password);
    Folder folder = store.getDefaultFolder().getFolder("INBOX");
    folder.open(Folder.READ_WRITE);
    return folder;
  }

  //Метод осуществляющий ожидание письма с сервера
  public List<MailMessage> waitForMail(String username, String password, long timeout) throws MessagingException {
    long now = System.currentTimeMillis();
    while (System.currentTimeMillis() < now + timeout) {
      List<MailMessage> allMail = getAllMail(username, password);
      if (allMail.size() > 0) {
        return allMail;
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  //Этот меттод извлекает сообщение из почтового ящика и превращает его в модельный объект типа MailMessage.
  public List<MailMessage> getAllMail(String username, String password) throws MessagingException {
    Folder inbox = openInbox(username, password); //Тут почтовый ящик открывается
    List<MailMessage> messages = Arrays.asList(inbox.getMessages()).stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
    closeFolder(inbox); //Тут почтовый ящик закрывается.
    return messages;
  }

  //Метод преобразующий реальное почтовое письмо в модельный объект
  public static MailMessage toModelMail(Message m) {
    try {
      return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}
