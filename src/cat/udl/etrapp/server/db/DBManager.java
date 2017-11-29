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
            if (env.equals("production")) {
                dataSource = (DataSource) new InitialContext().lookup("java:/PostgresXADS");
            } else if (env.equals("development")) {
                // TODO: cambiar dataSource para atacar a DB local
                dataSource = (DataSource) new InitialContext().lookup("java:/PostgresXADS");
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
