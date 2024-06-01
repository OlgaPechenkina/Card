package ru.netology;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardWithDiliveryTest {
    private String generateDate(String pattern){
        return LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    public void shouldCreateCardDelivery(){
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Магнитогорск");
        String planningDate = generateDate("dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Печенкина Ольга");
        $("[data-test-id='phone'] input").setValue("+79517701314");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }
}