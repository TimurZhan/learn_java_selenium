package ru.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod//Метод осуществляющий проверку предусловия.
  public void ensurePrecondition(){
    app.goTo().groupPage();
    if (app.group().list().size() == 0){ //Создана проверка предусловия того, что редактируемая группа существует.
      app.group().create(new GroupData("Test1", "drgdgdg", "dgfdgdgdgd"));
    }
  }

  @Test
  public void testGroupDeletionTest() {
    List<GroupData> before = app.group().list();//Тут происходит подсчет количества групп(элементов в списке) ДО создания группы.
    int index = before.size() - 1;
    app.group().delete(index);
    List<GroupData> after = app.group().list();//Тут происходит подсчет количества групп (элементов в списке) ПОСЛЕ создания группы.
    Assert.assertEquals(after.size(), before.size() - 1);//Тут реализована проверка количества элементов (размер списка групп), до и после. Необходимо, чтобы значение before было меньше значения after.
    before.remove(index);//Удаляем не нужный элемент, для того чтобы привести старый список в соответствии с обновленным. Данное действие необходимо для проверки, что оставшиейся элементы остались же ими.
    Assert.assertEquals(before, after);//Тут происходит проверка элементов (группы, как объекты) старого и нового списков. Нужно чтобы данные в них полностью совпали друг с другом.
    //app.getSessionHelper().logoutProgram();
  }

}
