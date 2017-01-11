package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Данный класс нужен для превращения потока данных о группах во множество.
 * Он описывает построение коллекций с расширенным набором методов.
 * Этот класс будет использоваться для
 */
public class Groups extends ForwardingSet<GroupData>{

  //Это атрибут, в который будет возвращаться объект. Он бует являться множеством.
  private Set<GroupData> delegate1;

  //Консруктор, создающий копию существующего объекта. Будет использоваться в методе withAdded
  // В параметр "(Groups groups)" передается множество из существующего объекта.
  public Groups(Groups groups) {
    this.delegate1 = new HashSet<GroupData>(groups.delegate1); //new HashSet<> строит новое множество, которое содержит те же самые элементы, что и старое. "this.delegate1 =" присваивает новосзданное множество в качестве атрибута в новый создаваемый объект
  }

  //Конструктор, который не имеет правметров.
  public Groups() {
    this.delegate1 = new HashSet<GroupData>();
  }

  @Override
  //Данный метод будет возвращать объект типа GroupData в атрибут delegate1
  protected Set<GroupData> delegate() {
    return delegate1;
  }

/**Данный метод нужен для того, чтобы вызовы методов множно было вытягивать в цепочки и строить из них каскады.
 * Он возвращает НОВЫЙ объект (старый объект НЕ меняется) с добавленной новой группой.
 * Это дает возможность работать и со старым множестом, БЕЗ добавленной группы, и с новым множеством с уже добавленной группой.
 */
  public Groups withAdded(GroupData group){
    Groups groups = new Groups(this);//Тут создается новая группа
    groups.add(group); //Тут, в новосозданную копию добавляем объект, который передан в качестве параметра.
    return groups; //Тут, возвращаем построенную копию с добавленной группой.
  }

  //Этот метод делает копию, из которой удалена группа.
  public Groups without(GroupData group){
    Groups groups = new Groups(this);//Тут создается новая группа
    groups.remove(group); //Тут, в новосозданную копию добавляем объект, который передан в качестве параметра.
    return groups; //Тут, возвращаем построенную копию с добавленной группой.
  }

}
