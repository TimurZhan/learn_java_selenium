package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

  private ApplicationManager app;

  public SoapHelper(ApplicationManager app){
    this.app = app;
  }

  public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc  = getMantisConnect();

    //Этот метод (mc_projects_get_user_accessible) позволяет получить список проектов, к которым пользователь имеет доступ.
    //И помещается это все в локальную переменную (в массив проектов).
    ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
    return Arrays.asList(projects).stream()
            .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName())).collect(Collectors.toSet());
  }

  public MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    return new MantisConnectLocator()
            .getMantisConnectPort(new URL(app.getProperty("soap.url")));
  }

  //Метод, добавляющий баг-репорт в Мантис
  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories("administrator", "root", BigInteger.valueOf(issue.getProject().getId())); //Запрашиваем категорию, которую надо указывать при созаднии баг-репорта
    IssueData issueData = new IssueData(); //Тут конструируется объект, типа Баг-репорт.
    issueData.setSummary(issue.getSummary()); //Составная часть Баг-репорта.
    issueData.setDescription(issue.getDescription()); //Составная часть Баг-репорта.
    issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName())); //Составная часть Баг-репорта (тут добавляется проект)
    issueData.setCategory(categories[0]); //Составная часть Баг-репорта.
    BigInteger issueId = mc.mc_issue_add("administrator", "root", issueData); //Тут сохраняется ID только что созданного баг репорта.
    IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId); //Тут берем баг-репорт по его ID и сохраняем его в переменную
    return new Issue().withId(createdIssueData.getId().intValue()) //Тут происходит преобразование createdIssueData в модельный объект для дальнейшей работы.
            .withSummary(createdIssueData.getSummary())
            .withDescription(createdIssueData.getDescription())
            .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
                    .withName(createdIssueData.getProject().getName()));

  }

}
