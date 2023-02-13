package racingCar.domain;

import racingCar.util.NumberPickerUtil;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Cars {

    private static final int MIN_CAR_SIZE = 2;
    private static final int INIT_CAR_POSITION = 0;
    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 9;

    private final List<Car> cars;

    public Cars(List<String> names) {
        validateDuplicate(names);
        validateSize(names);

        this.cars = names.stream()
                .map(carName -> new Car(carName, INIT_CAR_POSITION))
                .collect(Collectors.toList());
    }

    private void validateDuplicate(List<String> carNames) {
        Set<String> distinctNames = new HashSet<>(carNames);
        if (carNames.size() != distinctNames.size()) {
            throw new IllegalArgumentException("자동차 이름에 중복이 있을 수 없습니다.");
        }
    }

    private void validateSize(List<String> carNames) {
        if (carNames.size() < MIN_CAR_SIZE) {
            throw new IllegalArgumentException("자동차의 개수는 2개 이상으로 입력해주세요.");
        }
    }

    public void move() {
        for (Car car : cars) {
            car.move(NumberPickerUtil.pickNumberInRange(MIN_NUMBER, MAX_NUMBER));
        }
    }

    public List<String> findWinners() {
        int maxPosition = findMaxPosition();
        return cars.stream()
                .filter(car -> car.isSamePosition(maxPosition))
                .map(Car::getName)
                .collect(Collectors.toList());
    }

    public int findMaxPosition() {
        return cars.stream()
                .mapToInt(Car::getPosition)
                .max()
                .orElse(INIT_CAR_POSITION);
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }
}
