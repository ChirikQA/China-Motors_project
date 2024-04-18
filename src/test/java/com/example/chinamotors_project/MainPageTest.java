package com.example.chinamotors_project;
import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MainPageTest {


    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp(){
        open("https://china-motors.org/");
    }

    @Test
    public void dropDown_presentation() {
        $(By.xpath("/html/body/header/div[2]/div/nav/ul/li[5]/button")).click();
        sleep(2000);

        ElementsCollection dropdownOptions = $$(By.xpath("/html/body/main/div[2]/div[1]/section[1]/form/div/div[1]/div"));

        if (dropdownOptions.isEmpty()) {
            System.out.println("Выпадающий список пуст");
            fail("Выпадающий список не может быть пустым");
        } else {
            System.out.println("Выпадающий список не пуст");
        }
    }

    @Test
    public void dropDown_dealerPage_model() {
        $(By.xpath("/html/body/header/nav/div/ul/li[8]")).click();
        sleep(2000);
        $(By.xpath("/html/body/main/div[2]/div/form/div[3]/div/div[2]")).click();

        List<WebElement> options = $(By.xpath("/html/body/main/div[2]/div/form/div[3]/div/div[2]")).findElements(By.tagName("li"));

        if (options.isEmpty()) {
            System.out.println("Выпадающий список пуст");
        } else {
            System.out.println("Выпадающий список не пуст");
            fail("Выпадающий список не может быть заполненным");
        }
    }

    @Test
    public void popup_allDealers() {
        $(By.xpath("/html/body/div[2]/div[1]/div[1]/div/div[3]/div/a")).click();

        sleep(2000);

        List<String> actualBrands = new ArrayList<>();
        for (SelenideElement element : $$(".popup__content .brand")
                .filter(visible)) {
            actualBrands.add(element.getText());
        }

        List<String> expectedBrands = List.of(
                "AITO", "AIWAYS", "ARCFOX", "AVATR", "BAIC GROUP", "BAOJUN", "BRILLIANCE AUTO", "BYD", "CHANGAN", "CHERY",
                "DFM", "DAYUN", "DEEPAL", "DENZA", "DORCEN", "EXEED", "FAW", "FOTON MOTOR", "FEIFAN", "GAC", "GONOW",
                "GEELY", "GREAT WALL", "HAFEI", "HIPHI", "HAIMA AUTO", "HANTENG AUTOS", "HAVAL", "HONGQI", "HUAWEI",
                "IM MOTORS", "JAC", "JAECOO", "JETOUR", "KAIYI", "LANDWIND", "LEAP MOTOR", "LI AUTO", "LIFAN", "LIVAN",
                "LYNK&CO", "MG", "NETA", "NIO", "OMODA", "ORA", "OTING", "POLESTAR", "RISING AUTO (FEIFAN)", "ROEWE",
                "SAIC MOTOR", "SAIC MAXUS", "SWM", "SINOTRUK VGV", "SKYWELL", "TANK", "VENUCIA", "VOYAH", "WEY", "WM MOTOR",
                "WULING", "XPENG", "XIAOMI", "ZOTYE AUTO", "ZXAUTO", "ZEEKR"
        );


        assertEquals(expectedBrands, actualBrands, "Отображаемые бренды в попапе не совпадают с ожидаемыми");
    }

    @Test
    public void find_logo_dealers() {
        sleep(2000);
        List<String> expectedBrands = List.of(
                "Geely", "Changan", "Exeed", "DFM", "Chery", "FAW", "GAC", "Great Wall", "Haval", "JAC", "Nio", "BYD", "Lifan"
        );

        List<String> actualLogosAltTexts = new ArrayList<>();

        for (SelenideElement logo : $$(".popular__logos a img")) {
            logo.shouldBe(visible);
            String altText = logo.getAttribute("alt");
            actualLogosAltTexts.add(altText);
            System.out.println("Found brand logo with alt text: " + altText);
        }

        for (String brand : expectedBrands) {
            assert actualLogosAltTexts.contains(brand) : "Expected brand '" + brand + "' is not found among the logos.";
        }
    }

    @Test
    public void dropDown_dealerPage_brand() {
        $(By.xpath("/html/body/header/nav/div/ul/li[8]")).click();
        sleep(2000);
        $(By.xpath("/html/body/main/div[2]/div/form/div[3]/div/div[1]")).click();

        List<WebElement> options = $(By.xpath("/html/body/main/div[2]/div/form/div[3]/div/div[1]/div[2]/ul")).findElements(By.tagName("li"));

        if (options.isEmpty()) {
            System.out.println("Выпадающий список пуст");
            fail("Выпадающий список не может быть пустым");
        } else {
            System.out.println("Выпадающий список не пуст");

        }
    }

    @Test
    public void click_new_car_name(){
        $(By.xpath("/html/body/div[2]/div[1]/div[1]/div/section[1]/div/div[2]/div[1]/a[1]/div/div[2]/div[1]")).click();
        sleep(2000);
        String expectedUrl = "https://china-motors.org/products/paladin-2nk";
        String currentUrl = WebDriverRunner.url();
        assertEquals(expectedUrl, currentUrl);
    }

    @Test
    public void click_newBig_car_img(){
        $(By.xpath("/html/body/div[2]/div[1]/div[1]/div/section[1]/div/div[2]/div[2]/a[1]/div/div[2]")).click();
        sleep(2000);
        String expectedUrl = "https://china-motors.org/products/monjaro-sds";
        String currentUrl = WebDriverRunner.url();
        assertEquals(expectedUrl, currentUrl);
    }

    @Test
    public void click_newBig_car_name(){
        $(By.xpath("/html/body/div[2]/div[1]/div[1]/div/section[1]/div/div[2]/div[2]/a[1]/div/div[1]/div[1]")).click();
        sleep(2000);
        String expectedUrl = "https://china-motors.org/products/monjaro-sds";
        String currentUrl = WebDriverRunner.url();
        assertEquals(expectedUrl, currentUrl);
    }

    @Test
    public void click_new_car_img(){
        $(By.xpath("/html/body/div[2]/div[1]/div[1]/div/section[1]/div/div[2]/div[1]/a[1]/div/div[1]/img")).click();
        sleep(2000);
        String expectedUrl = "https://china-motors.org/products/paladin-2nk";
        String currentUrl = WebDriverRunner.url();
        assertEquals(expectedUrl, currentUrl);
    }

    @Test
    public void new_carBlock(){
        $$(".newcars_row .row_block").shouldHave(CollectionCondition.size(10));

        for (SelenideElement selenideElement : $$(".newcars_row .row_block")) {
            String carName = selenideElement.$(".row_block-name p").getText();
            String carImage = selenideElement.$(".row_block-img img").getAttribute("src");
            System.out.println("Название новинки: " + carName);
            System.out.println("Изображение новинки: " + carImage);

            assert carName != null && carImage != null : "Название или изображение равны null!";
        }

        $$(".newcars_row2 .row2_block").shouldHave(CollectionCondition.size(2));

        for (SelenideElement newCar : $$(".newcars_row2 .row2_block")) {
            String carName = newCar.$(".row2-info .row2-name p").getText();
            String carImage = newCar.$(".row2-img img").getAttribute("src");
            System.out.println("Название второй новинки: " + carName);
            System.out.println("Изображение второй новинки: " + carImage);

            assert carName != null && carImage != null : "Название или изображение равны null!";
        }
    }

    private int getStatusCode(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            return connection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}