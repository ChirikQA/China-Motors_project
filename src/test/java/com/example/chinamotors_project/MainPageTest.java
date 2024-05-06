package com.example.chinamotors_project;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class MainPageTest {


    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("https://china-motors.org/");
        Configuration.timeout = 10000; // 10 секунд
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
    public void click_new_car_name() {
        $(By.xpath("/html/body/div[2]/div[1]/div[1]/div/section[1]/div/div[2]/div[1]/a[1]/div/div[2]/div[1]")).click();
        sleep(2000);
        String expectedUrl = "https://china-motors.org/products/paladin-2nk";
        String currentUrl = WebDriverRunner.url();
        assertEquals(expectedUrl, currentUrl);
    }

    @Test
    public void click_newBig_car_img() {
        $(By.xpath("/html/body/div[2]/div[1]/div[1]/div/section[1]/div/div[2]/div[2]/a[1]/div/div[2]")).click();
        sleep(2000);
        String expectedUrl = "https://china-motors.org/products/monjaro-sds";
        String currentUrl = WebDriverRunner.url();
        assertEquals(expectedUrl, currentUrl);
    }

    @Test
    public void click_newBig_car_name() {
        $(By.xpath("/html/body/div[2]/div[1]/div[1]/div/section[1]/div/div[2]/div[2]/a[1]/div/div[1]/div[1]")).click();
        sleep(2000);
        String expectedUrl = "https://china-motors.org/products/monjaro-sds";
        String currentUrl = WebDriverRunner.url();
        assertEquals(expectedUrl, currentUrl);
    }

    @Test
    public void click_new_car_img() {
        $(By.xpath("/html/body/div[2]/div[1]/div[1]/div/section[1]/div/div[2]/div[1]/a[1]/div/div[1]/img")).click();
        sleep(2000);
        String expectedUrl = "https://china-motors.org/products/paladin-2nk";
        String currentUrl = WebDriverRunner.url();
        assertEquals(expectedUrl, currentUrl);
    }

    @Test
    public void new_carBlock() {
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

    @Test
    public void testButton_slider_card_sedan() {
        Set<String> uniqueCardUrls = new HashSet<>();
        String mark = "sedan?page=";
        boolean allCardsHaveSlider = true;

        open("https://china-motors.org/all/catagory/sedan");

        collectUniqueCardUrls(uniqueCardUrls, mark);

        for (String cardUrl : uniqueCardUrls) {
            try {
                open(cardUrl);

                $(".slider-container").shouldBe(visible);

                if (!($(".swiper-button-prev").exists() && $(".swiper-button-next").exists())) {
                    allCardsHaveSlider = false;
                    System.out.println("Карточка товара " + cardUrl + " не содержит кнопок слайдера.");
                }

                back();
            } catch (TimeoutException e) {
                System.out.println("Таймаут произошел при загрузке карточки товара с маркой: " + cardUrl);
            }
        }

        assertTrue(allCardsHaveSlider);
    }

    @Test
    public void testButton_slider_card_hetchbek() {
        Set<String> uniqueCardUrls = new HashSet<>();
        String mark = "hetchbek?page=";
        boolean allCardsHaveSlider = true;

        open("https://china-motors.org/all/catagory/hetchbek");

        collectUniqueCardUrls(uniqueCardUrls, mark);

        for (String cardUrl : uniqueCardUrls) {
            try {
                open(cardUrl);

                $(".slider-container").shouldBe(visible);

                if (!($(".swiper-button-prev").exists() && $(".swiper-button-next").exists())) {
                    allCardsHaveSlider = false;
                    System.out.println("Карточка товара " + cardUrl + " не содержит кнопок слайдера.");
                }

                back();
            } catch (TimeoutException e) {
                System.out.println("Таймаут произошел при загрузке карточки товара с маркой: " + cardUrl);
            }
        }

        assertTrue(allCardsHaveSlider);
    }

    @Test
    public void testButton_slider_card_liftbek() {
        Set<String> uniqueCardUrls = new HashSet<>();
        String mark = "liftbek?page=";
        boolean allCardsHaveSlider = true;

        open("https://china-motors.org/all/catagory/liftbek");

        collectUniqueCardUrls(uniqueCardUrls, mark);

        for (String cardUrl : uniqueCardUrls) {
            try {
                open(cardUrl);

                $(".slider-container").shouldBe(visible);

                if (!($(".swiper-button-prev").exists() && $(".swiper-button-next").exists())) {
                    allCardsHaveSlider = false;
                    System.out.println("Карточка товара " + cardUrl + " не содержит кнопок слайдера.");
                }

                back();
            } catch (TimeoutException e) {
                System.out.println("Таймаут произошел при загрузке карточки товара с маркой: " + cardUrl);
            }
        }

        assertTrue(allCardsHaveSlider);
    }
    @Test
    public void testButton_slider_card_vnedorojnik() {
        Set<String> uniqueCardUrls = new HashSet<>();
        String mark = "vnedorojnik?page=";
        boolean allCardsHaveSlider = true;

        // Вывод марки с таймаутом
        System.out.println("Таймаут Selenide установлен в: " + Configuration.timeout + " миллисекунд");

        open("https://china-motors.org/all/catagory/vnedorojnik");

        collectUniqueCardUrls(uniqueCardUrls, mark);

        for (String cardUrl : uniqueCardUrls) {
            try {
                open(cardUrl);

                $(".slider-container").shouldBe(visible);

                if (!($(".swiper-button-prev").exists() && $(".swiper-button-next").exists())) {
                    allCardsHaveSlider = false;
                    System.out.println("Карточка товара " + cardUrl + " не содержит кнопок слайдера.");
                }

                back();
            } catch (TimeoutException e) {
                System.out.println("Таймаут произошел при загрузке карточки товара с маркой: " + cardUrl);
            }
        }

        assertTrue(allCardsHaveSlider);
    }

    @Test
    public void testButton_slider_card_mikroavtobus() {
        Set<String> uniqueCardUrls = new HashSet<>();
        String mark = "mikroavtobus?page=";
        boolean allCardsHaveSlider = true;

        open("https://china-motors.org/all/catagory/mikroavtobus");

        collectUniqueCardUrls(uniqueCardUrls, mark);

        for (String cardUrl : uniqueCardUrls) {
            try {
                open(cardUrl);

                $(".slider-container").shouldBe(visible);

                if (!($(".swiper-button-prev").exists() && $(".swiper-button-next").exists())) {
                    allCardsHaveSlider = false;
                    System.out.println("Карточка товара " + cardUrl + " не содержит кнопок слайдера.");
                }

                back();
            } catch (TimeoutException e) {
                System.out.println("Таймаут произошел при загрузке карточки товара с маркой: " + cardUrl);
            }
        }

        assertTrue(allCardsHaveSlider);
    }

    @Test
    public void testButton_slider_card_universal() {
        Set<String> uniqueCardUrls = new HashSet<>();
        String mark = "universal?page=";
        boolean allCardsHaveSlider = true;

        open("https://china-motors.org/all/catagory/universal");

        collectUniqueCardUrls(uniqueCardUrls, mark);

        for (String cardUrl : uniqueCardUrls) {
            try {
                open(cardUrl);

                $(".slider-container").shouldBe(visible);

                if (!($(".swiper-button-prev").exists() && $(".swiper-button-next").exists())) {
                    allCardsHaveSlider = false;
                    System.out.println("Карточка товара " + cardUrl + " не содержит кнопок слайдера.");
                }

                back();
            } catch (TimeoutException e) {
                System.out.println("Таймаут произошел при загрузке карточки товара с маркой: " + cardUrl);
            }
        }

        assertTrue(allCardsHaveSlider);
    }

    @Test
    public void testButton_slider_card_cupe() {
        Set<String> uniqueCardUrls = new HashSet<>();
        String mark = "cupe?page=";
        boolean allCardsHaveSlider = true;

        open("https://china-motors.org/all/catagory/cupe");

        collectUniqueCardUrls(uniqueCardUrls, mark);

        for (String cardUrl : uniqueCardUrls) {
            try {
                open(cardUrl);

                $(".slider-container").shouldBe(visible);

                if (!($(".swiper-button-prev").exists() && $(".swiper-button-next").exists())) {
                    allCardsHaveSlider = false;
                    System.out.println("Карточка товара " + cardUrl + " не содержит кнопок слайдера.");
                }

                back();
            } catch (TimeoutException e) {
                System.out.println("Таймаут произошел при загрузке карточки товара с маркой: " + cardUrl);
            }
        }

        assertTrue(allCardsHaveSlider);
    }


    private void collectUniqueCardUrls(Set<String> uniqueCardUrls, String mark) {
        int currentPage = 1;
        String currentPageUrl = "https://china-motors.org/all/catagory/" + mark + currentPage;

        open(currentPageUrl);
        boolean hasPagination = $(".pagination_newnli").exists();
        int cardsOnCurrentPage;
        if (hasPagination) {
            while (true) {
                cardsOnCurrentPage = collectCardUrls(uniqueCardUrls);
                System.out.println("Текущая страница: " + currentPage);
                System.out.println("Количество карточек: " + cardsOnCurrentPage / 4);
                System.out.println("Общее количество карточек: " + uniqueCardUrls.size());

                if (!isNextPageButtonActive(currentPageUrl)) {
                    break;
                }

                currentPage++;
                currentPageUrl = "https://china-motors.org/all/catagory/" + mark + currentPage;
                open(currentPageUrl);
            }
        } else {
            cardsOnCurrentPage = collectCardUrls(uniqueCardUrls);
            System.out.println("Текущая страница: " + currentPage);
            System.out.println("Количество карточек: " + cardsOnCurrentPage / 4);
            System.out.println("Общее количество карточек: " + uniqueCardUrls.size());
        }
    }


    private int collectCardUrls(Set<String> uniqueCardUrls) {
        ElementsCollection productCardLinks = $$(".content_info_list_a");
        for (SelenideElement element : productCardLinks) {
            uniqueCardUrls.add(element.getAttribute("href"));
        }
        return productCardLinks.size();
    }


    private boolean isNextPageButtonActive(String nextPageUrl) {
        open(nextPageUrl);
        $(".content_info_list_a").shouldBe(visible);
        return !$(".pagination_newnli.last-newli[aria-disabled='true']").exists();
    }

}