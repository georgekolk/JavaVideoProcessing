import org.sqlite.JDBC;
import java.sql.*;

public class DbHandler {

    private static String CON_STR = null;
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance(String connectionString) throws SQLException {
        //CON_STR = connectionString;
        if (instance == null)
            instance = new DbHandler(connectionString);
        return instance;
    }

    private Connection connection;

    private DbHandler(String connectionString) throws SQLException {
        CON_STR = connectionString;
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    private String prepareYourAnus(String stringToPrepareYourAnus) {
        stringToPrepareYourAnus = stringToPrepareYourAnus.replace(".", "");
        stringToPrepareYourAnus = stringToPrepareYourAnus.replace("explore/tags/", "");
        return stringToPrepareYourAnus;
    }

    public String getTagsByFilename(String filename, String table) {
        String sql = "SELECT tags FROM " + table + " WHERE filenames LIKE '%" + filename + "%';";
        //System.out.println(sql.toString());
        String returnPlzThis = "";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                //System.out.println(filename + " " + rs.getString("tags"));
                returnPlzThis = rs.getString("tags");

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return returnPlzThis;
    }



}