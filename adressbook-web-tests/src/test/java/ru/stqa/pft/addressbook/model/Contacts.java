package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Данный класс нужен для превращения потока данных о контактах во множество.
 * Он описывает построение коллекций с расширенным набором методов.
 * Этот класс будет использоваться для
 */
public class Contacts extends ForwardingSet<ContactData> {

  private Set<ContactData> delegate1;

  public Contacts(Contacts contacts) {
    this.delegate1 = new HashSet<ContactData>(contacts.delegate1);
  }

  //Конструктор без параметров
  public Contacts() {
    this.delegate1 = new HashSet<ContactData>();
  }

  @Override
  protected Set<ContactData> delegate() {
    return delegate1;
  }

  public Contacts withAdded(ContactData contact){
    Contacts contacts = new Contacts(this);
    contacts.add(contact);
    return contacts;
  }

  public Contacts without(ContactData contact){
    Contacts contacts = new Contacts(this);
    contacts.remove(contact);
    return contacts;
  }

}
