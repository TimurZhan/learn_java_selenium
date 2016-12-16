package ru.stqa.pft.addressbook.model;

public class GroupData {
  private final String name;
  private final String header;
  private final String footer;

  public GroupData(String name, String header, String footer) {
    this.name = name;
    this.header = header;
    this.footer = footer;
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
  /**
   * Сгенерированный метод toString, который используется для проебразования объекта в строку (примитивный тип данных).
   * Необходимо это для текстового представления, если возникла ошибка при сравнении списков.
   */
  public String toString() {
    return "GroupData{" +
            "name='" + name + '\'' +
            '}';
  }

  @Override
  /**
   * Сгенерированный метод equals, который используется для сравнения объектов в списке.
   * Это сделано в связи с тем, что Java самостоятельно не умеет этого делать
   */
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GroupData groupData = (GroupData) o;

    return name != null ? name.equals(groupData.name) : groupData.name == null;

  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}
