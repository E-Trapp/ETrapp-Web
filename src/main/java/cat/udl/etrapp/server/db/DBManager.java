package cat.udl.etrapp.server.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBManager {
	
	private static DataSource dataSource;

    static {
        try {
            final String env = System.getenv("environment");
            switch (env) {
                case "production":
                    dataSource = (DataSource) new InitialContext().lookup("java:/PostgresXADS");
                    break;
                case "development":
                    dataSource = (DataSource) new InitialContext().lookup("java:/PostgresXADS");
                    break;
                default:
                    dataSource = (DataSource) new InitialContext().lookup("java:/PostgresXADS");
                    break;
            }
        }
        catch (NamingException e) { 
            throw new ExceptionInInitializerError("'java:/PostgresXADS' not found in JNDI");
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
