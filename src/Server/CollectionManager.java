package Server;

import Common.TextFormatter;
import Common.entities.SpaceMarine;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CollectionManager {

    private final Date creationDate;
    private volatile HashSet<SpaceMarine> spaceMarines;
    private final Lock readLock;
    private final Lock writeLock;

    public CollectionManager() {
        spaceMarines = new HashSet<>();
        creationDate = new Date();
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    public HashSet<SpaceMarine> getSpaceMarines() {
        readLock.lock();
        try {
            return spaceMarines;
        } finally {
            readLock.unlock();
        }
    }

    public void addSpaceMarine(SpaceMarine spaceMarine) {
        writeLock.lock();
        spaceMarines.add(spaceMarine);
        writeLock.unlock();
    }

    public void clear(String username) {
        readLock.lock();
        spaceMarines.removeIf(SpaceMarine -> SpaceMarine.getAuthorName().equals(username));
        readLock.unlock();
    }

    public String removeById(long id) {
        writeLock.lock();
        try {
            SpaceMarine SpaceMarine = getById(id);
            if (SpaceMarine != null) {
                spaceMarines.remove(SpaceMarine);
                return TextFormatter.colorMessage("SpaceMarine successfully removed");
            } else {
                return TextFormatter.colorErrorMessage("SpaceMarine with that ID not found");
            }
        } catch (NumberFormatException e) {
            return TextFormatter.colorErrorMessage("ID имеет некорректный формат");
        } finally {
            writeLock.unlock();
        }
    }

    public SpaceMarine getById(Long id) {
        readLock.lock();
        try {
            return spaceMarines.stream().filter(sm -> sm.getId()==id).findAny().orElse(null);
        } finally {
            readLock.unlock();
        }
    }

    public String showInfo() {
        readLock.lock();
        try {
            return TextFormatter.colorInfoMessage("Information about collection: ")
                    + TextFormatter.colorMessage("Collection type: " + spaceMarines.getClass()
                    + " initialization date: " + creationDate
                    + " count of SpaceMarines: " + spaceMarines.size());
        } finally {
            readLock.unlock();
        }
    }

    public SpaceMarine getMaxByChapter(String username) {
        readLock.lock();
        try {
            return spaceMarines.stream().filter(((d) -> d.getAuthorName().equals(username))).max(SpaceMarine::compareByChapter).get();
        } finally {
            readLock.unlock();
        }
    }

    public SpaceMarine getMax() {
        readLock.lock();
        try {
            return spaceMarines.stream().max(SpaceMarine::compareTo).get();
        } finally {
            readLock.unlock();
        }
    }

    public SpaceMarine getMin() {
        readLock.lock();
        try {
            return spaceMarines.stream().min(SpaceMarine::compareTo).get();
        } finally {
            readLock.unlock();
        }
    }
}
