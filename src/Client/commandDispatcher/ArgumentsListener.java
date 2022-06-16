package Client.commandDispatcher;

import Common.entities.Coordinates;
import Common.entities.Chapter;
import Common.entities.SpaceMarine;
import Common.enums.AstartesCategory;
import Common.enums.WeaponType;
import Common.handlers.TextFormatter;


import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс, отвечающий за работу с пользователем во время
 * ввода данных о новом элементе коллекции
 */
public class ArgumentsListener {

    private final Scanner scanner = new Scanner(System.in);

    public SpaceMarine inputDragon(String name, String age, String wingspan) {
        SpaceMarine spaceMarine = new SpaceMarine();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        try {
            spaceMarine.setName(name);
            spaceMarine.setAge(Integer.parseInt(age));
            spaceMarine.setWingspan(Integer.parseInt(wingspan));
            boolean correctName = DragonValidator.validateField(spaceMarine, "name");
            boolean correctAge = DragonValidator.validateField(spaceMarine, "age");
            boolean correctWingspan = DragonValidator.validateField(spaceMarine, "wingspan");
            if (!correctName | !correctAge | !correctWingspan) {
                return null;
            }
        } catch (NumberFormatException e) {
            TextFormatter.printErrorMessage("Аргументы имеют неверный формат");
            return null;
        }
        spaceMarine.setCoordinates(inputCoordinates());
        inputColor(spaceMarine);
        inputCharacter(spaceMarine);
        spaceMarine.setCave(inputCave());
        return spaceMarine;
    }

    public SpaceMarine inputDragonWithPrimitives() {
        SpaceMarine spaceMarine = new SpaceMarine();
        inputPrimitives(spaceMarine);
        spaceMarine.setCoordinates(inputCoordinates());
        inputColor(spaceMarine);
        inputCharacter(spaceMarine);
        spaceMarine.setCave(inputCave());
        return spaceMarine;
    }

    /**
     * Метод обработки и инициализации данных примитивных типов для переданного
     * в аргументе дракона
     *
     * @param spaceMarine дракон, характеристики примитивных типов которого вводит пользователь
     */
    public void inputPrimitives(SpaceMarine spaceMarine) {
        TextFormatter.printMessage("Enter primitives args of dragon: name age[>0], wingspan[>0]");
        String[] inputArray = scanner.nextLine().split(" ");
        if (inputArray.length == 3) {
            try {
                spaceMarine.setName(inputArray[0]);
                spaceMarine.setAge(Integer.parseInt(inputArray[1]));
                spaceMarine.setWingspan(Integer.parseInt(inputArray[2]));
                boolean correctName = DragonValidator.validateField(spaceMarine, "name");
                boolean correctAge = DragonValidator.validateField(spaceMarine, "age");
                boolean correctWingspan = DragonValidator.validateField(spaceMarine, "wingspan");
                if (!correctName | !correctAge | !correctWingspan) {
                    TextFormatter.printErrorMessage("Введенные данные некорректны");
                    inputPrimitives(spaceMarine);
                }
            } catch (IllegalArgumentException e) {
                TextFormatter.printErrorMessage("Введены некорректные данные, верный формат: name age[>0] wingspan[>0]");
                inputPrimitives(spaceMarine);
            }
        } else {
            TextFormatter.printErrorMessage("Incorrect count of primitive args. Needed: name age[>0], wingspan[>0]");
            inputPrimitives(spaceMarine);
        }
    }

    /**
     * Метод обработки и инициализации координат (не устанавливает данные
     * в поля объекта коллекции)
     *
     * @return объект координат, данные которых ввёл пользователь
     */
    public Coordinates inputCoordinates() {
        TextFormatter.printInfoMessage("Введите координаты:");
        Coordinates newCoordinates = new Coordinates();
        inputX(newCoordinates);
        inputY(newCoordinates);
        return newCoordinates;
    }

    /**
     * Метод обработки и инициализации координаты Х и присваивание ее объекту координат
     *
     * @param coordinates объект координат, х которых вводит пользователь
     */
    private void inputX(Coordinates coordinates) {
        TextFormatter.printInfoMessage("Введите координату x (целое число): ");
        try {
            int x = Integer.parseInt(scanner.nextLine());
            coordinates.setX(x);
            boolean correctField = DragonValidator.validateField(coordinates, "x");
            if (!correctField) {
                TextFormatter.printErrorMessage("Значение поля некорректно, попробуйте еще раз");
                inputX(coordinates);
            }
        } catch (NumberFormatException e) {
            TextFormatter.printErrorMessage("Число имеет неверный формат");
            inputX(coordinates);
        } catch (IllegalArgumentException e) {
            TextFormatter.printErrorMessage("Число не входит в допустимый диапазон");
            inputX(coordinates);
        }
    }

    /**
     * Метод обработки и инициализации координаты У и присваивание ее объекту координат
     *
     * @param coordinates объект координат, у которых вводит пользователь
     */
    private void inputY(Coordinates coordinates) {
        TextFormatter.printInfoMessage("Введите Y(число с плавающей точкой): ");
        try {
            float y = Float.parseFloat(scanner.nextLine());
            coordinates.setY(y);
            boolean correctField = DragonValidator.validateField(coordinates, "y");
            if (!correctField) {
                inputY(coordinates);
            }
        } catch (NumberFormatException e) {
            TextFormatter.printErrorMessage("Ошибка ввода");
            inputY(coordinates);
        } catch (IllegalArgumentException e) {
            TextFormatter.printErrorMessage(e.getMessage());
            inputY(coordinates);
        }
    }

    /**
     * Метод обработки и инициализации пещеры (не устанавливает данные
     * в поля объекта коллекции)
     *
     * @return пещера, данные о которой ввёл пользователь
     */
    public DragonCave inputCave() {
        TextFormatter.printInfoMessage("Введите данные о пещере:");
        DragonCave cave = new DragonCave();
        inputDepth(cave);
        inputNumOfTreasures(cave);
        return cave;
    }

    /**
     * Метод обработки и инициализации глубины пещеры и присваивание ее объекту пещеры
     *
     * @param cave пещера, глубина которой запрашивается у пользователя
     */
    private void inputDepth(DragonCave cave) {
        TextFormatter.printInfoMessage("Введите глубину пещеры (число с плавающей точкой): ");
        try {
            cave.setDepth(Double.parseDouble(scanner.nextLine()));
        } catch (NumberFormatException e) {
            TextFormatter.printErrorMessage("Ошибка ввода");
            inputDepth(cave);
        }
    }

    /**
     * Метод обработки и инициализации количества сокровищ в пещере и присваивание этого количества объекту пещеры
     *
     * @param cave пещера, количество сокровищ в которой запрашивается у пользователя
     */
    private void inputNumOfTreasures(DragonCave cave) {
        TextFormatter.printInfoMessage("Введите количество сокровищ (целое число, большее 0): ");
        try {
            cave.setNumberOfTreasures(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException e) {
            TextFormatter.printErrorMessage("Ошибка ввода");
            inputNumOfTreasures(cave);
        } catch (IllegalArgumentException e) {
            TextFormatter.printErrorMessage(e.getMessage());
            inputNumOfTreasures(cave);
        }
    }

    /**
     * Метод обработки цвета дракона, полученного от пользователя
     *
     * @param spaceMarine дракон, цвет которого запрашивается у пользователя
     */
    public void inputColor(SpaceMarine spaceMarine) {
        TextFormatter.printInfoMessage("Введите цвет дракона, доступные цвета: " + Arrays.toString(AstartesCategory.values()) + ", если у дракона нет цвета, нажмите Enter: ");
        try {
            String color = scanner.nextLine().toUpperCase();
            if ("".equals(color)) {
                spaceMarine.setAstartesCategory(null);
            } else {
                spaceMarine.setAstartesCategory(AstartesCategory.valueOf(color));
            }
        } catch (IllegalArgumentException e) {
            TextFormatter.printErrorMessage("Ошибка ввода, такого цвета не существует");
            inputColor(spaceMarine);
        }
    }

    /**
     * Метод обработки характера дракона, полученного от пользователя
     *
     * @param spaceMarine дракон, характер которого запрашивается у пользователя
     */
    public void inputCharacter(SpaceMarine spaceMarine) {
        TextFormatter.printInfoMessage("Введите настроение дракона, доступные настроения: " + Arrays.toString(WeaponType.values()) + ": ");
        try {
            spaceMarine.setWeaponType(WeaponType.valueOf(scanner.nextLine().toUpperCase()));
        } catch (IllegalArgumentException e) {
            TextFormatter.printErrorMessage("Ошибка ввода, такого настроения не существует");
            inputCharacter(spaceMarine);
        }
    }
}
