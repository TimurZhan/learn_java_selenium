package ru.stqa.pft.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {

  private CloseableHttpClient httpclient;
  private ApplicationManager app;

  public HttpSession(ApplicationManager app){
    this.app = app;
    httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
  }

  //Тут выполняется логин в багтрекере Мантис
  public boolean login(String username, String password) throws IOException {
    HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php"); //Тут создается запрос, который будет потом отправлен (тип Post).
    List<NameValuePair> params = new ArrayList<>(); //Тут формируется набор параметров, которые будут переданы в запросе.
    params.add(new BasicNameValuePair("username", username));
    params.add(new BasicNameValuePair("password", password));
    params.add(new BasicNameValuePair("secure_session", "on"));
    params.add(new BasicNameValuePair("return", "index.php"));
    post.setEntity(new UrlEncodedFormEntity(params)); //Тут происходит упаковка передаваемых параметров. Затем он помещается в запрос "post".
    CloseableHttpResponse response = httpclient.execute(post); //Тут происходит отправка запроса
    String body = getTextFrom(response); //Тут происходит получение текаста запроса (в виде кода на HTML).
    return body.contains(String.format("<span class=\"italic\">%s</span>", username)); //Тут происходит проверка того, что действит-но ли юзер вошел в систему.
  }

  private String getTextFrom(CloseableHttpResponse response) throws IOException {
    try {
      return EntityUtils.toString(response.getEntity());
    } finally {
      response.close();
    }
  }


  //Тут происходит определение пользователя, который, в данный момент времени, находится в системе.
  public boolean isLoggedInAs(String username) throws IOException {
    HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php");
    CloseableHttpResponse response = httpclient.execute(get);
    String body = getTextFrom(response);
    return body.contains(String.format("<span class=\"italic\">%s</span>", username));
  }

}
