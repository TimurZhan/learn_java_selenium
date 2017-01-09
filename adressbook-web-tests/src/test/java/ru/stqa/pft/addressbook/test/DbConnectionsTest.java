package ru.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

public class DbConnectionsTest {

  @Test
  public void testDbConnection(){
    Connection conn = null;

    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?serverTimezone=UTC&user=root&password=");
      Statement st = conn.createStatement(); //Вытягиваем из базы данных какую-либо информацию.
      ResultSet rs = st.executeQuery("select group_id,group_name,group_header,group_footer from group_list");//Направляем запрос в БД, при помощи SQL запроса.
      Groups groups = new Groups(); //Тут сохраняем, в переменную groups, полученные из цикла значения полей таблицы group_list
      while (rs.next()){ //Тут, в цикле, получем значения всех полей перечисленных в объекте типа GroupData.
        groups.add(new GroupData()
                .withId(rs.getInt("group_id"))
                .withName(rs.getString("group_name"))
                .withHeader(rs.getString("group_header"))
                .withFooter(rs.getString("group_footer"))
        );
      }
      rs.close(); //Закрываем вызов, для сохранения полученной информации и освобождения памяти.
      st.close(); //Закрываем вызов, для сохранения полученной информации и освобождения памяти.
      conn.close(); //Закрываем вызов, для сохранения полученной информации и освобождения памяти.
      System.out.println(groups);

    } catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}