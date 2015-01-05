package database;

import java.sql.SQLException;

public interface IDataBase {
	boolean isNameFree(String name) throws SQLException;
	void register(String name, int passWd)throws SQLException;
	boolean loginOk(String name, int passWd) throws SQLException;
}
