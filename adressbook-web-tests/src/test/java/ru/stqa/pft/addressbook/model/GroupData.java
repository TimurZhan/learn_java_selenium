package ru.stqa.pft.addressbook.model;

public class GroupData {
  private int id = Integer.MAX_VALUE;;
  private String name;
  private String header;
  private String footer;

  /** КОНСТРУКТОР УДАЛЕН при модификации данного объекта.
   Создан конструктор, который НЕ принимает ID в качестве параметра.
   Будет использоваться в случае, если в тестах будет использоваться данный объект без id.
  public GroupData(String name, String header, String footer) {
    this.id = Integer.MAX_VALUE;//Дефолтное значение для ID будет самое большое целое число (нужно для того, чтобы создаваемая группа оказалась последней при сортировке).
    this.name = name;
    this.header = header;
    this.footer = footer;
  }


  /** КОНСТРУКТОР УДАЛЕН при модификации данного объекта.
  public GroupData(int id, String name, String header, String footer) {
    this.id = id;
    this.name = name;
    this.header = header;
    this.footer = footer;
  }
  */

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
