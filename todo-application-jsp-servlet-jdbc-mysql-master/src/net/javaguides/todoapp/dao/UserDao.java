package net.javaguides.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.javaguides.todoapp.model.User;
import net.javaguides.todoapp.utils.JDBCUtils;

public class UserDao {

	public int registerEmployee(User employee) throws ClassNotFoundException, SQLException {
		String INSERT_USERS_SQL = "INSERT INTO users" + "  (first_name, last_name, username, password) VALUES "
				+ " (?, ?, ?, ?);";

		int result = 0;
		if (checkUsernameExits(employee.getUsername())) {
			return result;
		} else {
			try (Connection connection = JDBCUtils.getConnection();
					// Step 2:Create a statement using connection object
					PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
				preparedStatement.setString(1, employee.getFirstName());
				preparedStatement.setString(2, employee.getLastName());
				preparedStatement.setString(3, employee.getUsername());
				preparedStatement.setString(4, employee.getPassword());

				System.out.println(preparedStatement);
				// Step 3: Execute the query or update query
				result = preparedStatement.executeUpdate();

			} catch (SQLException e) {
				// process sql exception
				JDBCUtils.printSQLException(e);
			}
		}
		return result;
	}

	public boolean checkUsernameExits(String username) throws SQLException {
		String CHECK_USERNAME_SQL = "select * from users where username = ?";
		boolean alreadyPresent = false;
		try (Connection connection = JDBCUtils.getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USERNAME_SQL)) {
			preparedStatement.setString(1, username);
			ResultSet result = preparedStatement.executeQuery();
			alreadyPresent = result.next();
		} catch (SQLException e) {
			// process sql exception
			JDBCUtils.printSQLException(e);
		}
		return alreadyPresent;
	}
}
