package Client.commandDispatcher;

import Common.TextFormatter;
import Common.entities.Coordinates;
import Common.entities.Chapter;
import Common.entities.SpaceMarine;
import Common.enums.AstartesCategory;
import Common.enums.WeaponType;

import java.util.Arrays;
import java.util.Scanner;

public class ArgumentsListener {

    private final Scanner scanner = new Scanner(System.in);

    public SpaceMarine inputSpaceMarine(String name, long health, String achievements) {
        SpaceMarine spaceMarine = new SpaceMarine();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        try {
            spaceMarine.setName(name);
            spaceMarine.setHealth(health);
            spaceMarine.setAchievements(achievements);
        } catch (NumberFormatException e) {
            TextFormatter.printErrorMessage("Аргументы имеют неверный формат");
            return null;
        }
        spaceMarine.setCoordinates(inputCoordinates());
        inputAstartesCategory(spaceMarine);
        inputWeaponType(spaceMarine);
        spaceMarine.setChapter(inputChapter());
        return spaceMarine;
    }

    public SpaceMarine inputSpaceMarineWithPrimitives() {
        SpaceMarine spaceMarine = new SpaceMarine();
        inputPrimitives(spaceMarine);
        spaceMarine.setCoordinates(inputCoordinates());
        inputAstartesCategory(spaceMarine);
        inputWeaponType(spaceMarine);
        spaceMarine.setChapter(inputChapter());
        return spaceMarine;
    }


    public void inputPrimitives(SpaceMarine spaceMarine) {
        TextFormatter.printMessage("Введите аргументы для корабля: имя, здоровье[>0], достижения[>0]");
        String[] inputArray = scanner.nextLine().split(" ");
        if (inputArray.length == 3) {
            try {
                spaceMarine.setName(inputArray[0]);
                spaceMarine.setHealth(Long.parseLong(inputArray[1]));
                spaceMarine.setAchievements(inputArray[2]);
            } catch (IllegalArgumentException e) {
                TextFormatter.printErrorMessage("Введены некорректные данные, верный формат: имя здоровье[>0] достижения[>0]");
                inputPrimitives(spaceMarine);
            }
        } else {
            TextFormatter.printErrorMessage("Введены некорректные данные, верный формат: имя здоровье[>0] достижения[>0]");
            inputPrimitives(spaceMarine);
        }
    }


    public Coordinates inputCoordinates() {
        TextFormatter.printInfoMessage("Введите координаты:");
        Coordinates newCoordinates = new Coordinates();
        inputX(newCoordinates);
        inputY(newCoordinates);
        return newCoordinates;
    }


    private void inputX(Coordinates coordinates) {
        TextFormatter.printInfoMessage("Введите координату x (целое число): ");
        try {
            int x = Integer.parseInt(scanner.nextLine());
            coordinates.setX(x);
        } catch (NumberFormatException e) {
            TextFormatter.printErrorMessage("Число имеет неверный формат");
            inputX(coordinates);
        } catch (IllegalArgumentException e) {
            TextFormatter.printErrorMessage("Число не входит в допустимый диапазон");
            inputX(coordinates);
        }
    }


    private void inputY(Coordinates coordinates) {
        TextFormatter.printInfoMessage("Введите Y(число с плавающей точкой): ");
        try {
            float y = Float.parseFloat(scanner.nextLine());
            coordinates.setY(y);
        } catch (NumberFormatException e) {
            TextFormatter.printErrorMessage("Ошибка ввода");
            inputY(coordinates);
        } catch (IllegalArgumentException e) {
            TextFormatter.printErrorMessage(e.getMessage());
            inputY(coordinates);
        }
    }

    public Chapter inputChapter() {
        TextFormatter.printInfoMessage("Введите данные о части:");
        Chapter chapter = new Chapter();
        inputName(chapter);
        inputParentLegion(chapter);
        inputMarinesCount(chapter);
        return chapter;
    }

    private void inputParentLegion(Chapter chapter) {
        TextFormatter.printInfoMessage("Введите имя родительского легиона: ");
        try {
            chapter.setParentLegion(scanner.nextLine());
        } catch (NumberFormatException e) {
            TextFormatter.printErrorMessage("Ошибка ввода");
            inputParentLegion(chapter);
        }
    }

    private void inputName(Chapter chapter) {
        TextFormatter.printInfoMessage("Введите имя части: ");
        try {
            chapter.setName(scanner.nextLine());
        } catch (NumberFormatException e) {
            TextFormatter.printErrorMessage("Ошибка ввода");
            inputName(chapter);
        } catch (IllegalArgumentException e) {
            TextFormatter.printErrorMessage(e.getMessage());
            inputName(chapter);
        }
    }

    private void inputMarinesCount(Chapter chapter) {
        TextFormatter.printInfoMessage("Введите кораблей в части: ");
        try {
            chapter.setMarinesCount(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException e) {
            TextFormatter.printErrorMessage("Ошибка ввода");
            inputMarinesCount(chapter);
        }
    }


    public void inputAstartesCategory(SpaceMarine spaceMarine) {
        TextFormatter.printInfoMessage("Введите категорию, доступные типы: " + Arrays.toString(AstartesCategory.values()) + ": ");
        try {
            spaceMarine.setAstartesCategory(AstartesCategory.valueOf(scanner.nextLine().toUpperCase()));
        } catch (IllegalArgumentException e) {
            TextFormatter.printErrorMessage("Ошибка ввода, такого типа оружия не существует");
            inputAstartesCategory(spaceMarine);
        }
    }

    public void inputWeaponType(SpaceMarine spaceMarine) {
        TextFormatter.printInfoMessage("Введите тип оружия, доступные типы: " + Arrays.toString(WeaponType.values()) + ": ");
        try {
            spaceMarine.setWeaponType(WeaponType.valueOf(scanner.nextLine().toUpperCase()));
        } catch (IllegalArgumentException e) {
            TextFormatter.printErrorMessage("Ошибка ввода, такого типа оружия не существует");
            inputWeaponType(spaceMarine);
        }
    }
}
