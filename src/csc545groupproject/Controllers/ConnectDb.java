/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc545groupproject.Controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import javax.swing.JOptionPane;

/**
 *
 * @author Jadon
 */
public class ConnectDb {
    public Connection setupConnection() {
        String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
        
        Properties dbProps = loadProperties("db.properties");
        
        String host = dbProps.getProperty("host");
        String port = dbProps.getProperty("port");
        String sid = dbProps.getProperty("sid");
        String username = dbProps.getProperty("username");
        String password = dbProps.getProperty("password");
        
        String jdbcUrl = String.format("jdbc:oracle:thin:@%s:%s:%s", host, port, sid);
        
        try {
            // Load jdbc driver.      
            Class.forName(jdbcDriver);
            
            // Connect to the Oracle database
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    
    private Properties loadProperties(String filename) {
        Properties props = new Properties();
        
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
            
            props.load(inputStream);
            return props;
        } catch (IOException e) {
            return null;
        }
    }
    
    public static void close(Connection conn) 
    {
        if(conn != null) 
        {
            try
            {
                conn.close();
            }
            catch(SQLException whatever)
            {}
        }
    }

    public static void close(OraclePreparedStatement st)
    {
        if(st != null)
        {
            try
            {
                st.close();
            }
            catch(SQLException whatever)
            {}
        }
    }

    public static void close(OracleResultSet rs)
    {
        if(rs != null)
        {
            try
            {
                rs.close();
            }
            catch(SQLException whatever)
            {}
        }
    }
}
