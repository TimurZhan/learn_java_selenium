package ru.stqa.pft.mantis.model;

public class MailMessage {

  public String to; //Тут указывается кому пришло письмо
  public String text; //Тут указывается текст письма

  public MailMessage(String to, String text){
    this.to = to;
    this.text = text;
  }

}
