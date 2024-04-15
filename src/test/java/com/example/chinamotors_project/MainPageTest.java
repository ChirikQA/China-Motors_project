package com.example.chinamotors_project;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPageTest {
    MainPage mainPage = new MainPage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp(){
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        open("https://solyarka.com/");
    }

    @Test
    public void close_banner() {
        sleep(10000);
        $(By.cssSelector("body > div.popup1.popup1--adv.fade > div.popup1-adv > button")).shouldBe(visible);

        $(By.cssSelector("body > div.popup1.popup1--adv.fade > div.popup1-adv > button")).click();
    }

    @Test
    public void click_auth_button() {
        $(By.cssSelector("body > header > div > div > nav > ul > li:nth-child(4) > form > a > button > span")).shouldBe(visible);

        $(By.xpath("/html/body/header/div/div/nav/ul/li[4]/form/a/button/span")).click();
    }

    @Test
    public void click_main_menu() {
        $(By.xpath("/html/body/header/div/div/div[1]")).shouldBe(visible);

        $(By.xpath("/html/body/header/div/div/div[1]")).click();
    }

    @Test
    public void click_low_price_btn() {
        $(By.cssSelector("body > div.container > div.content.wrapper > div.content_list > div.main-pros > div > a:nth-child(3)")).click();
    }

}