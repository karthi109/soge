import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBHandler {

	//Write the required business logic as expected in the question description
	public Connection establishConnection() {

		//fill the code
		try {
			FileInputStream fis = new FileInputStream("db.properties");
			Properties prop = new Properties();
			prop.load(fis);
			Class.forName(prop.getProperty("db.classname"));
			return DriverManager.getConnection(prop.getProperty("db.url"),prop.getProperty("db.username"),prop.getProperty("db.password"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
