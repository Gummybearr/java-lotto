package step2.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import step2.dto.JudgeResponseDTO;
import step2.dto.LottoDTO;
import step2.dto.LottoListDTO;
import step2.dto.ShopResponseDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class JudgeTest {

    private Judge judge;

    @BeforeEach
    void setUp() {
        this.judge = new Judge();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,2,3,4,5,6 : 1,2,3,10,20,30 : 1,0,0,0 : 5",
            "1,2,3,4,5,6 : 1,2,3,4,10,20 : 0,1,0,0 : 50",
            "1,2,3,4,5,6 : 1,2,3,4,5,10 : 0,0,1,0 : 1500",
            "1,2,3,4,5,6 : 1,2,3,4,5,6 : 0,0,0,1 : 2000000"}, delimiter = ':')
    @DisplayName("당첨여부를 판단할 수 있다")
    void judgeCalculatesResult(String winningString, String boughtString, String expected, double expectedProfitIndex) {
        List<Integer> winningNumbers = parseIntegerListFromString(winningString);
        LottoDTO winningLotto = new LottoDTO(winningNumbers);
        ShopResponseDTO shopResponse = generateShopResponse(boughtString);
        List<Integer> expectedResult = parseIntegerListFromString(expected);

        JudgeResponseDTO judgeResponse = judge.calculateResult(shopResponse, winningLotto);
        assertThat(judgeResponse.getThreeMatch()).isEqualTo(expectedResult.get(0));
        assertThat(judgeResponse.getFourMatch()).isEqualTo(expectedResult.get(1));
        assertThat(judgeResponse.getFiveMatch()).isEqualTo(expectedResult.get(2));
        assertThat(judgeResponse.getSixMatch()).isEqualTo(expectedResult.get(3));
        assertThat(judgeResponse.getProfitIndex()).isEqualTo(expectedProfitIndex);
    }

    private ShopResponseDTO generateShopResponse(String boughtString) {
        List<Integer> myNumbers = parseIntegerListFromString(boughtString);
        LottoDTO myLotto = new LottoDTO(myNumbers);
        LottoListDTO myLottos = new LottoListDTO(myLotto);

        return new ShopResponseDTO(1000, 1, myLottos, 0);
    }

    private List<Integer> parseIntegerListFromString(String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

}
