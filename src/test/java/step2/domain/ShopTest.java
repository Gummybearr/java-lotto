package step2.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import step2.dto.MoneyDTO;
import step2.dto.ShopResponseDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShopTest {

    private Shop shop;

    @BeforeEach
    void setUp() {
        this.shop = new Shop();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1000:1:1",
            "1500:1:1",
            "2600:2:2"}, delimiter = ':')
    @DisplayName("살 수 있는 만큼의 로또를 살 수 있다")
    void canBuyLotto(int moneyAmount, int capacity, int expected) {
        MoneyDTO money = new MoneyDTO(moneyAmount);
        ShopResponseDTO shopResponse = shop.buyLotto(money, capacity);
        assertThat(shopResponse.getLottoCount()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1000:2",
            "1500:3",
            "2600:4"}, delimiter = ':')
    @DisplayName("살 수 없는 만큼의 로또를 사려 하면 예외를 던진다")
    void buyingInvalidNumberOfLottoThrowsException(int moneyAmount, int capacity) {
        MoneyDTO money = new MoneyDTO(moneyAmount);
        assertThrows(IllegalArgumentException.class, () -> shop.buyLotto(money, capacity));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1100:1:0:false",
            "1500:1:0:false",
            "2600:2:0:false"}, delimiter = ':')
    @DisplayName("원금과 로또를 모두 환불한다고 가정했을 때의 돈이 같지 않으면 예외를 던진다")
    void shopResponseThrowsExceptionOnInvalidInput(int moneyAmount, int capacity, int change, boolean expected) {
        ShopResponseDTO shopResponse = new ShopResponseDTO(moneyAmount, capacity, null, change);
        assertThat(shop.isShopResponseValid(shopResponse)).isEqualTo(expected);
    }

}
