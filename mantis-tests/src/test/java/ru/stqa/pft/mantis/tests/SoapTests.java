package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTests extends TestBase {

  //Тест на получения списка проектов.
  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects(); //Получаем множество проектов в Мантисе
    System.out.println(projects.size()); //Выводим на конcоль кол-во проектов
    for (Project project : projects){//Выводим на конcоль названия проектов
      System.out.println(project.getName());
    }
  }

  //Тест на созадние баг-репорта в Мантисе
  @Test
  public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects(); //Получаем множество проектов в Мантисе (уже созданные записи в Мантисе)
    Issue issue = new Issue().withSummary("Test issue").withDescription("Test issue description").withProject(projects.iterator().next()); //Выбираем любой баг-репорт из списка уже существующих
    Issue created = app.soap().addIssue(issue); //Создаем новый баг-репорт
    Assert.assertEquals(issue.getSummary(), created.getSummary()); //Сравниваем объекты между собой (уже существующий баг-репорт и только что созданный)

  }

}
