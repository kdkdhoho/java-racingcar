package racingCar.controller;

import racingCar.domain.Cars;
import racingCar.domain.TryCount;
import racingCar.view.InputView;
import racingCar.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

public class RacingCarController {

    public void run() {
        Cars cars = repeat(this::generateCars);
        TryCount tryCount = repeat(this::readTryCount);
        playRacing(cars, tryCount);
        OutputView.printWinners(cars.findWinners());
    }

    private Cars generateCars() {
        List<String> carNames = repeat(InputView::readCarNames);
        return new Cars(carNames);
    }

    private TryCount readTryCount() {
        int tryCount = repeat(InputView::readTryCount);
        return new TryCount(tryCount);
    }

    private void playRacing(Cars cars, TryCount tryCount) {
        for (int i = 0; i < tryCount.getTryCount(); i++) {
            cars.moveCars();
            OutputView.printCarPosition(cars.toDtos());
        }
    }

    private <T> T repeat(Supplier<T> reader) {
        try {
            return reader.get();
        } catch (Exception e) {
            OutputView.printError(e.getMessage());
            return repeat(reader);
        }
    }
}
