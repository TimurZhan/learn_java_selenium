package ru.stqa.pft.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by Tim on 05.02.2017.
 */
public class GeoIpServiceTests {

  @Test
  public void testMyIp(){
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("188.168.20.132"); //Создан объект типа GeoIpServiceTests, который передает мой IP
    assertEquals(geoIP.getCountryCode(), "RUS"); //Тут происохдит сравнение IP который передан в переменной geoIP и текста "RUS". Нужно, чтобы они совпадали между собой.
  }

  @Test //Отрицательный тест кейс
  public void testInvalidIp(){
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("188.168.20.+++"); //Создан объект типа GeoIpServiceTests, который передает мой IP
    assertEquals(geoIP.getCountryCode(), "RUS"); //Тут происохдит сравнение IP который передан в переменной geoIP и текста "RUS". Нужно, чтобы они совпадали между собой.
  }

}
