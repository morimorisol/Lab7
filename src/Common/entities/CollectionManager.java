package Common.entities;

import Common.handlers.TextFormatter;


import java.util.Date;
import java.util.HashSet;

/**
 * Класс коллекции, содержащий текущую коллекцию <b>dragons</b>,
 * отвечает за генерацию ID для новых элементов и за все действия,
 * связанные с коллекцией.
 */
public class CollectionManager {
    /**
     * Дата создания коллекции
     */
    private final Date creationDate;
    /**
     * Сет объектов класса Dragon, текущее содержимое коллекции
     */
    private HashSet<SpaceMarine> spaceMarines;

    /**
     * Конструктор объекта данного класса.
     * Устанавливает коллекцию и дату её создания
     */
    public CollectionManager() {
        spaceMarines = new HashSet<>();
        creationDate = new Date();
    }

    /**
     * Метод, возвращающий текущую коллекцию драконов в формате HashSet
     *
     * @return HashSet драконов, находящихся в коллекции
     */
    public HashSet<SpaceMarine> getDragons() {
        return spaceMarines;
    }

    /**
     * Метод, добавляющий полученного дракона в коллекцию
     *
     * @param dragon дракон, которого нужно добавить в коллекцию
     */
    public void addDragon(SpaceMarine dragon) {
        dragon.setId();
        spaceMarines.add(dragon);
    }

    /**
     * Метод, очищающий текущую коллекцию
     */
    public void clear() {
        spaceMarines.clear();
        if (this.getDragons().isEmpty()) {
            TextFormatter.printInfoMessage("Collection successful cleared");
        } else {
            TextFormatter.printErrorMessage("Something went wrong, try again.");
        }
    }

    /**
     * Метод, удаляющий дракона из коллекции по полученному ID, если таковой существует
     *
     * @param id id дракона, которого нужно удалить
     */
    public String removeById(long id) {
        try {
            SpaceMarine spaceMarine = getById(id);
            if (spaceMarine != null) {
                spaceMarines.remove(spaceMarine);
                return TextFormatter.colorInfoMessage("Dragon successfully removed");
            } else {
                return TextFormatter.colorErrorMessage("Dragon with that ID not found");
            }
        } catch (NumberFormatException e) {
            return TextFormatter.colorErrorMessage("ID имеет некорректный формат");
        }
    }

    public SpaceMarine getById(Long id) {
        return spaceMarines.stream().filter(dr -> dr.getId().equals(id)).findAny().orElse(null);
    }

    /**
     * Метод, выводящий пользователю информацию о коллекции
     */
    public String showInfo() {
        return TextFormatter.colorInfoMessage("Information about collection: ")
                + TextFormatter.colorMessage("Collection type: " + spaceMarines.getClass()
                + " initialization date: " + creationDate
                + " count of dragons: " + spaceMarines.size());
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
