package Common.entities;

import Common.handlers.TextFormatter;

import java.time.LocalDate;
import java.util.LinkedList;

public class CollectionManager {

    private final LocalDate creationDate;
    public static LinkedList<SpaceMarine> spaceMarines;

    public CollectionManager() {
        spaceMarines = new LinkedList<>();
        creationDate = LocalDate.now();
    }

    public LinkedList<SpaceMarine> getSpaceMarines() {
        return spaceMarines;
    }

    public void addSpaceMarine(SpaceMarine sm) {
            sm.setId();
            if(sm.getCreationDate()==null) sm.setCreationDate();
            spaceMarines.add(sm);
    }

    public void clear() {
        spaceMarines.clear();
        if (this.getSpaceMarines().isEmpty()) {
            TextFormatter.printInfoMessage("Коллекция успешно очищена");
        } else {
            TextFormatter.printErrorMessage("Что-то пошло не так, попробуйте снова");
        }
    }

    public String removeById(int id) {
        try {
            SpaceMarine spaceMarine = getById(id);
            if (spaceMarine != null) {
                spaceMarines.remove(spaceMarine);
                System.out.println(spaceMarines.toString());
                return TextFormatter.colorInfoMessage("Корабль успешно удален");
            } else {
                return TextFormatter.colorErrorMessage("Корабль с данным ID не найден");
            }
        } catch (NumberFormatException e) {
            return TextFormatter.colorErrorMessage("ID имеет некорректный формат");
        }
    }

    public SpaceMarine getById(int id) {
        return spaceMarines.stream().filter(sm -> sm.getId() == id).findAny().orElse(null);
    }

    public String showInfo() {
        return TextFormatter.colorInfoMessage("Информация о коллекции: ")
                + TextFormatter.colorMessage("Тип коллекции: " + spaceMarines.getClass()
                + " дата инициализации: " + creationDate
                + " количество кораблей: " + spaceMarines.size());
    }

    public SpaceMarine getMaxByChapter() {
        return spaceMarines.stream().max(SpaceMarine::compareByChapter).get();
    }

    public SpaceMarine getMax() {
        return spaceMarines.stream().max(SpaceMarine::compareTo).get();
    }

    public SpaceMarine getMin() {
        return spaceMarines.stream().min(SpaceMarine::compareTo).get();
    }
}
