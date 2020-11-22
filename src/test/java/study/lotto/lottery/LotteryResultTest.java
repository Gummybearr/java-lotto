package study.lotto.lottery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.lotto.core.Lotto;
import study.lotto.core.LottoNumber;
import study.lotto.core.LottoRank;
import study.lotto.core.WinLottoNumbers;
import study.lotto.dispenser.Lottos;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LotteryResultTest {

    private static final LottoNumber bonusLottoNumber = LottoNumber.of(7);
    private Set<LottoNumber> lottoNumbers;

    @BeforeEach
    public void createLottoNumbers() {
        this.lottoNumbers = new HashSet<>(Arrays.asList(LottoNumber.of(1)
                , LottoNumber.of(2)
                , LottoNumber.of(3)
                , LottoNumber.of(4)
                , LottoNumber.of(5)
                , LottoNumber.of(6)));
    }

    @Test
    void test_LotteryResult() {
        // Given
        Lotto lotto = new Lotto(lottoNumbers);
        WinLottoNumbers.WinLottoNumbersBuilder builder = new WinLottoNumbers.WinLottoNumbersBuilder(lottoNumbers);
        builder.bonusLottoNumber(bonusLottoNumber);
        WinLottoNumbers winLottoNumbers = builder.build();
        Lottos lottos = new Lottos(Arrays.asList(lotto));
        Lottery lottery = new Lottery(winLottoNumbers, lottos);

        // When
        LotteryResult lotteryResult = lottery.checkLotto();

        // Then
        Long numberOfLottoRank = lotteryResult.getNumberOfLottoRank(LottoRank.FIRST);
        double totalReturnRatio = new Double(lotteryResult.getTotalReturnRatio());
        assertThat(numberOfLottoRank).isEqualTo(1L);
        assertThat(totalReturnRatio > 1.0).isTrue();
    }

    @Test
    @DisplayName("DecimalFormat 연습")
    void test_decimalFormat() {
        // Given
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double pi = 3.141592;
        double e = 2.7182818284;

        // When & Then
        System.out.println(decimalFormat.format(pi));
        System.out.println(decimalFormat.format(e));
    }

}