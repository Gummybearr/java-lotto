package step2.service;

import step2.domain.Judge;
import step2.domain.Money;
import step2.domain.lotto.LottoList;
import step2.domain.lotto.LottoMachine;
import step2.domain.lotto.WinningLotto;
import step2.dto.JudgeResponseDTO;
import step2.dto.LottoListDTO;

import java.util.ArrayList;
import java.util.List;

public class LottoService {

    private final LottoMachine lottoMachine = new LottoMachine();
    private final Judge judge = new Judge();

    private LottoList lottoList;

    public LottoListDTO buyLotto(int rawAmount, List<String> rawLottoList) {
        Money money = new Money(rawAmount);
        LottoList manualLottoList = lottoMachine.buyManualLotto(rawLottoList);
        LottoList autoLottoList = lottoMachine.buyAutoLotto(money);
        lottoList = new LottoList(manualLottoList, autoLottoList);
        return lottoList.dto();
    }

    public JudgeResponseDTO getLottoResult(String rawNumberString, int rawBonusNumber) {
        WinningLotto winningLotto = new WinningLotto(rawNumberString, rawBonusNumber);
        return judge.calculateResult(lottoList, winningLotto);
    }

    public void verifyLottoCount(int rawAmount, int rawManualLottoCount) {
        Money amount = new Money(rawAmount);
        lottoMachine.verifyCapacity(amount, rawManualLottoCount);
    }

    public void verifyLottoList(List<String> rawManualLottoList) {
        new LottoList((ArrayList<String>) rawManualLottoList);
    }

    public int change(int rawAmount, int rawManualLottoCount) {
        Money amount = new Money(rawAmount);
        return lottoMachine.change(amount, rawManualLottoCount);
    }
}