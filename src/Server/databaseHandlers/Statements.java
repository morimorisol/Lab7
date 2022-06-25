package Server.databaseHandlers;

public enum Statements {
    addSpaceMarine("INSERT INTO s337017SpaceMarines " +
            "(id, name, creationDate, health, achievements, xCoord, yCoord, astartesCategory, weaponType, chapterName, chapterLegion, chapterMarinesCount, author" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),

    checkUserInData("SELECT * FROM s337017Users WHERE (name=? AND password=?);"),

    addUserToData("INSERT INTO s337017Users (name, password) VALUES(?, ?)"),

    clearByUser("DELETE FROM s337017SpaceMarines WHERE (author=?) RETURNING s337017SpaceMarines.id;"),

    getAllForUser("SELECT * FROM s337017SpaceMarines WHERE author=?;"),

    removeById("DELETE FROM s337017SpaceMarines WHERE (author=? AND id=?) RETURNING s337017SpaceMarines.id;"),

    updateById("UPDATE s337017SpaceMarines " +
            "SET name = ?, " +
            "creationDate = ?, " +
            "health = ?, " +
            "achievements = ?, " +
            "xCoord = ?, " +
            "yCoord = ?, " +
            "astartesCategory = ?, " +
            "weapoType = ?, " +
            "chapterName = ?, " +
            "chapterLegion = ?, " +
            "chapterMarinesCount = ? " +
            "WHERE (id=? AND author=?) RETURNING s337017SpaceMarines.id;"),

    getNextId("SELECT nextval('ids')"),

    getAll("SELECT * FROM s337017SpaceMarines;");

    private final String statement;

    Statements(String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }
}
