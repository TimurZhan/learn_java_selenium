package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {

  private final ApplicationManager app;
  private FTPClient ftp;

  //Тут инициализируется ФТП клиент, который и будет устанавливать соединение и передавать файлы.
  public FtpHelper(ApplicationManager app){
    this.app = app;
    ftp = new FTPClient();
  }

  //Данный метод загружает нужный файл, а старый временно переименовывается
  public void upload(File file, String target, String backup) throws IOException {
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(backup); //Тут удаляется предыдущая резервная копия файла
    ftp.rename(target, backup); //Тут переименовывается удаленный файл, затем делается резервная копия
    ftp.enterLocalPassiveMode(); //Тут включается пассивный режим передачи файлов.

    //Тут передается локальный файл на удаленную машину, в которой он сохраняется в файл под именем target.
    ftp.storeFile(target, new FileInputStream(file));
    ftp.disconnect(); //Тут происходит разрыв соединения.
  }

  //Данный метод восстанавливает старый файл
  public void restore(String backup, String target) throws IOException {
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(backup);
    ftp.rename(target, backup);
    ftp.disconnect();
  }
}
