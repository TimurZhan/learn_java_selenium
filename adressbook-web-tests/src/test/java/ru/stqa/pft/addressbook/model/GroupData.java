package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    return name != null ? name.equals(groupData.name) : groupData.name == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
