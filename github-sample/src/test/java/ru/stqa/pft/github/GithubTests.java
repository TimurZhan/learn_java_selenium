package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  //Поучаем из Github инфу о коммитах.
  @Test
  public void testCommits() throws IOException {
    //Тут устанавливается соединение с Github через API(удаленный програмный интерфейс).
    Github github = new RtGithub("d01101eaec5a7caccbeb1458d2031f9c2998655c");

    //Получаем список коммитов из моего репозитория
    RepoCommits commits = github.repos().get(new Coordinates.Simple("Timysh", "learn_java_selenium")).commits();

    //В цикле выводим список коммитов на консоль
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())){
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
