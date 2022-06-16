package Common.entities;

import Common.enums.AstartesCategory;
import Common.enums.WeaponType;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {

    /**
     * Количество полей примитивного типа, которые необходимо передавать при инициализации
     * нового дракона в одной строчке с используемой командой
     */
    public static final int COUNT_OF_PRIMITIVE_ARGS = 3;
    /**
     * Счетчик id элементов, служит для обеспечения уникальности поля id у каждого элемента
     */
    private static long idCounter = 1;

    public int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    public String name; //Поле не может быть null, Строка не может быть пустой
    public Coordinates coordinates;//Поле не может быть null
    public static Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    public long health; //Поле не может быть null, Значение поля должно быть больше 0
    public String achievements; //Поле не может быть null
    public AstartesCategory category; //Поле может быть null
    public WeaponType weaponType; //Поле не может быть null
    public Chapter chapter; //Поле не может быть null

    public void setId() {
        this.id = (int) idCounter++;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        if (name == null || " ".equals(name)) {
            throw new IllegalArgumentException("Имя не может быть пустым или null, попробуйте снова");
        }
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Не переданы координаты или они некорректны, попробуйте снова");
        }
        this.coordinates = coordinates;
    }


    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void setHealth(long health) {
        if (health <= 0) {
            throw new IllegalArgumentException("Некорректный показатель здоровья, попробуйте снова");
        }
        this.health = health;
    }

    public long getHealth() {
        return this.health;
    }

    public void setAchievements(String achievements) {
        if (achievements == null) {
            throw new IllegalArgumentException("Некорректный формат достижений, попробуйте снова");
        }
        this.achievements = achievements;
    }

    public String getAchivements() {
        return this.achievements;
    }

    public void setAstartesCategory(AstartesCategory astartesCategory) {
        this.category= astartesCategory;
    }

    public AstartesCategory getAstartesCategory() {
        return this.category;
    }

    public void setWeaponType(WeaponType weaponType) {
        if (weaponType == null) {
            throw new IllegalArgumentException("Не передан тип оружия, попробуйте снова");
        }
        this.weaponType = weaponType;
    }

    public WeaponType getWeaponType() {
        return this.weaponType;
    }

    public void setChapter(Chapter chapter) {
        if (chapter == null) {
            throw new IllegalArgumentException("Не переданы данные о части или они некорректны, попробуйте снова");
        }
        this.chapter = chapter;
    }

    public Chapter getChapter() {
        return this.chapter;
    }


    public void setCreationDate() {
        this.creationDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int compareByChapter(SpaceMarine o) {
        if (o == null) {
            return 1;
        }
        return getChapter().compareTo(o.getChapter());
    }

    public int compareByName(SpaceMarine o) {
        if (o == null) {
            return 1;
        }
        return getName().compareTo(o.getName());
    }

    @Override
    public int compareTo(SpaceMarine o) {
        if (o == null) {
            return 1;
        }
        return Comparator.comparing(SpaceMarine::getHealth).thenComparing(SpaceMarine::getName).thenComparing(SpaceMarine::getAchivements).compare(this, o);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SpaceMarine spaceMarine = (SpaceMarine) obj;

        return getName().equals(spaceMarine.getName())
                && health == spaceMarine.health
                && coordinates.equals(spaceMarine.coordinates)
                && achievements == spaceMarine.achievements
                && chapter.equals(spaceMarine.chapter)
                && id == spaceMarine.id
                && category.equals(spaceMarine.category)
                && weaponType.equals(spaceMarine.weaponType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, chapter, category, weaponType, health, achievements, creationDate);
    }

    @Override
    public String toString() {
        return "\nКорабль #" + id + "\nимя: " + name
                + "\nдата создания: " + getCreationDate()
                + "\nздоровье: " + health + "\nдостижения: " + achievements
                + "\nкоординаты: " + coordinates.toString() + "\nкатегория: " + category
                + "\nтип оружия: " + weaponType + "\nчасть: " + chapter.toString() + "\n========================";
    }
}
