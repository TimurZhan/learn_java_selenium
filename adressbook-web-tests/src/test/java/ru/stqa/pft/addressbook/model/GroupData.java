package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@XStreamAlias("group") //Тут указывает то, как будет называться каждый объект в файле groups.xml
@Entity //Эта аннотация объявляет класс GroupData привязанным к БД
@Table(name = "group_list") //Тут подсказываем программе, что класс GroupData равен таблице под названием group_list, в БД
public class GroupData {
  @XStreamOmitField //В данной аннотации указывем то, что id не будет отображаться в файле groups.xml
  @Id
  @Column (name = "group_id")
  private int id = Integer.MAX_VALUE;

  @Expose //В данной аннотации указывем то, что id не будет отображаться в файле groups.json, а будет отображаться данная строчка.
  @Column (name = "group_name")
  private String name;

  @Expose //В данной аннотации указывем то, что id не будет отображаться в файле groups.json, а будет отображаться данная строчка.
  @Column (name = "group_header")
  @Type(type = "text")
  private String header;

  @Expose //В данной аннотации указывем то, что id не будет отображаться в файле groups.json, а будет отображаться данная строчка.
  @Column (name = "group_footer")
  @Type(type = "text")
  private String footer;

  //Тут укакзывается связующая таблица в БД. В которой связываются между собой два объекта из Групп и Контактов
  @ManyToMany(mappedBy = "groups")
  private Set<ContactData> contacts = new HashSet<ContactData>();


  public GroupData withId(int id) {
    this.id = id;
    return this;
  }

  public GroupData withName(String name) {
    this.name = name;
    return this;
  }

  public GroupData withHeader(String header) {
    this.header = header;
    return this;
  }

  public GroupData withFooter(String footer) {
    this.footer = footer;
    return this;
  }


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getHeader() {
    return header;
  }

  public String getFooter() {
    return footer;
  }

  public Contacts getContacts() { //Тут будет возвращаться копия объекта типа GroupData
    return new Contacts(contacts);
  }

  @Override
  public String toString() {
    return "GroupData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", header='" + header + '\'' +
            ", footer='" + footer + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GroupData groupData = (GroupData) o;

    if (id != groupData.id) return false;
    if (name != null ? !name.equals(groupData.name) : groupData.name != null) return false;
    if (header != null ? !header.equals(groupData.header) : groupData.header != null) return false;
    return footer != null ? footer.equals(groupData.footer) : groupData.footer == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (header != null ? header.hashCode() : 0);
    result = 31 * result + (footer != null ? footer.hashCode() : 0);
    return result;
  }
}
