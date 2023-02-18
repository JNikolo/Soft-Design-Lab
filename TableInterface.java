import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

interface TableInterface{
    Connection getConnection(String url, String username, String password);
    
    static void createSchema(Connection connection, String nameSchema) throws SQLException{
        PreparedStatement psCreateTable = connection.prepareStatement("CREATE SCHEMA "+nameSchema);
        try{
            psCreateTable.executeUpdate();
        }
        catch(SQLException e){ System.out.println(e);}
    }

    static void dropTable(Connection connection, String nameTable) throws SQLException{
        PreparedStatement psDropTable = connection.prepareStatement("DROP TABLE IF EXISTS "+nameTable);
        try{
            psDropTable.executeUpdate();
        }
        catch(SQLException ex){System.out.println(ex);}
    }

    static void createTable(Connection connection, String ddlCreateTable) throws SQLException
    {
        PreparedStatement psCreateTable = connection.prepareStatement(ddlCreateTable);
        try
        {
            psCreateTable.executeUpdate();
        }
        catch(SQLException e){System.out.println(e);}
    }

    static String loadDataInFileTable(String nameFile, String nameTable)
    {
        return "LOAD DATA INFILE '" + nameFile + "' INTO TABLE " + nameTable +
        " FIELDS TERMINATED BY '\\t' LINES TERMINATED BY '\\n' "+
        "IGNORE 1 lines;";
    }
    static void populateTable(Connection connection, String ddlPopulateTable) throws SQLException{
        PreparedStatement psPopulateTable = connection.prepareStatement(ddlPopulateTable);
        try
        {
            psPopulateTable.executeUpdate();
        }
        catch(SQLException e){System.out.println(e);}
    }
}
