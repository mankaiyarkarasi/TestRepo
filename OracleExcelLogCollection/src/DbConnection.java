import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class DbConnection {
	static Connection connection = null;
	static Logger logger = Logger.getLogger(DbConnection.class);

	ArrayList<Employee> arrayList = new ArrayList<Employee>();
	Employee employee = new Employee();

	public Connection getConnection() {
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "system";
		String pass = "root";

		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			logger.info("Connecting to db");
			connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException sqlException) {
			logger.error(sqlException);
		}
		return connection;

	}

	void executeQuery(String sql) {

		try {
			Statement statement = connection.createStatement();
			logger.info("Executing Statement");

			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int sapId = resultSet.getInt("sapId");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				int age = resultSet.getInt("age");
				String location = resultSet.getString("location");

				logger.debug(sapId);
				logger.debug(firstName);
				logger.debug(lastName);
				logger.debug(age);
				logger.debug(location);

				employee.setSapId(resultSet.getInt("sapId"));
				employee.setFirstName(resultSet.getString("firstName"));
				employee.setLastName(resultSet.getString("lastName"));
				employee.setAge(resultSet.getInt("age"));
				employee.setLocation(resultSet.getString("location"));
				arrayList.add(employee);

			}

		} catch (SQLException sqlException) {
			logger.error(sqlException);
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		System.out.println("Total Objects in Employee table " + arrayList.size());
	}

	
}
