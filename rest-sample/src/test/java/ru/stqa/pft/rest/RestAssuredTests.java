package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;

public class RestAssuredTests {

  //Нужно для авторизации
  @BeforeClass
  public void init(){
    RestAssured.authentication = RestAssured.basic("LSGjeU4yP1X493ud1hNniA==", "");
  }

  //Тест на создание баг-репорта.
  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssues(); //Получаем список баг-репотров, до создания нового.
    Issue newIssue = new Issue().withSubject("Test issue 1").withDescription("New test issue"); //Создаем новый объект (баг-репорт), чтобы добавить его позже.
    int issueId = createIssue(newIssue); //Создаем новый баг-репорт в системе. Также будет возвращать ЙД данного баг-репорта.
    Set<Issue> newIssues = getIssues(); //Получаем список баг-репотров, уже включающий новосозданный баг-репорт.
    oldIssues.add(newIssue.withId(issueId)); //Добавляем в старый список новосозданный объект.
    assertEquals(newIssues, oldIssues); //Сравниваем новый и старый списки.
  }

  private Set<Issue> getIssues() throws IOException {
    //Получаем список всех баг-репортов в формате json.
    //String json = getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json")).returnContent().asString(); //Авторизируемся в системе.

    //Получаем список всех баг-репортов в формате json (второй вариант).
    String json = RestAssured.get("http://demo.bugify.com/api/issues.json").asString();
    JsonElement parsed = new JsonParser().parse(json); //Извлекаем переданное значение и пробразовываем в нужный формат.
    JsonElement issues = parsed.getAsJsonObject().get("issues"); //Извлекаем ИЗ переданного значения по ключу issues

    //Преобразовываем во множество объектов типа issue. Где первый параметр это то, откуда извлекается инфа(issues).
    //А второй параметр описывает тип данных, который должен получиться в конце(new TypeToken<Set<Issue>>(){}.getType()).
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  //private Executor getExecutor(){
    //return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA==", ""); //Получаем доступ к системе (Указываем имя, логин).
  //}

  //Метод для создания баг-репорта
  private int createIssue(Issue newIssue) throws IOException {
    //Непосредственно создание баг-репорта при помощи удаленного интерфейса REST.
    //String json = getExecutor().execute(Request.Post("http://demo.bugify.com/api/issues.json")
            //.bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                    //new BasicNameValuePair("description", newIssue.getDescription())))
           // .returnContent().asString();

    //Непосредственно создание баг-репорта при помощи удаленного интерфейса REST (второй вариант)
    String json = RestAssured.given()
            .parameter("subject", newIssue.getSubject())
            .parameter("description", newIssue.getDescription()).post("http://demo.bugify.com/api/issues.json").asString();
    JsonElement parsed = new JsonParser().parse(json); //Извлекаем переданное значение и пробразовываем в нужный формат.
    return parsed.getAsJsonObject().get("issue_id").getAsInt(); //Извлекаем ИЗ переданного значения по ключу issue_id и представляем его как целое число.
  }

}