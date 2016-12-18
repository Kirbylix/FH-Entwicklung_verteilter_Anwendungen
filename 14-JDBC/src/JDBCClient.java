import java.sql.*;
import java.util.Calendar;
import java.util.Random;

public class JDBCClient {

    public static void main(String[] args) {
        JDBCClient meinJDBC = new JDBCClient();
        Connection conn = meinJDBC.connect();

        if (conn != null){ // Verbindungsaufbau war erfolgreich
            try {
                meinJDBC.createTable(conn);
                meinJDBC.write(conn,"Kai","ABC","Schroedingers_Katze");
                meinJDBC.write(conn,"Kai","ABC2","Schroedingers_Katze2");
                meinJDBC.write(conn,"Kai","ABC3","Schroedingers_Katze3");
                meinJDBC.write(conn,"Kai","ABC4","Schroedingers_Katze4");
                meinJDBC.read(conn);
                meinJDBC.drop(conn);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        if (conn != null)  // Es existiert eine Verbindung
        {
            meinJDBC.disconnect(conn);
        }
    }

    public Connection connect() {

        Connection conn = null;

        String host = "jdbc:mysql://localhost:3306/";
        String dbName = "test";
        String username = "root";
        String password = "";

        String url = host + dbName
                + "?user=" + username
                + "?password=" + password;

        try {
            String treiber = "com.mysql.jdbc.Driver";
            Class.forName(treiber);
            System.out.println("Info: Treiber " + treiber + "[ok]");

            System.out.print("Info: Verbindung zu " + url);
            conn = DriverManager.getConnection(url);
            System.out.println("[ok]");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return conn;
    }

    public void createTable(Connection conn) throws Throwable {
        System.out.println("CREATE Table....");
        Statement stat = conn.createStatement();
        String sql = "CREATE TABLE eintrag(" +
                        " autor VARCHAR(250), " +
                        " titel VARCHAR(250), " +
                        " beschreibung VARCHAR(250), " +
                        " datum DATE, " +
                        " lfdnr INTEGER);";
        stat.executeUpdate(sql);
    }

    public void write(Connection conn, String autor, String titel, String beschreibung) throws Throwable {
        System.out.println("INSERT Statement....");
        Calendar calendar = Calendar.getInstance();
        java.sql.Date datum = new java.sql.Date(calendar.getTime().getTime());
        Random rn = new Random();

        String sql = "INSERT INTO `eintrag`(`autor`, `titel`, `beschreibung`, `datum`, `lfdnr`) VALUES (?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, autor);
        ps.setString(2, titel);
        ps.setString(3, beschreibung);
        ps.setDate(4, datum);
        ps.setInt(5, rn.nextInt(1000));

        ps.execute();
    }

    public void read(Connection conn) throws Throwable {
        System.out.println("SELECT Statement....");
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT  * FROM eintrag");

        while(rs.next()){
            String autor = rs.getString("autor");
            String title = rs.getString("titel");
            String beschreibung = rs.getString("beschreibung");
            Date datum = rs.getDate("datum");
            int ldfNr = rs.getInt("lfdnr");

            System.out.println("Autor: " + autor + ", Titel: " + title + ", Beschreibung: " + beschreibung + ", Datum: " + datum + ", LdfNr: " + ldfNr);
        }
        rs.close();
        stat.close();
    }

    public void drop(Connection conn)throws Throwable{
        Statement stat = conn.createStatement();
        try {
            stat.execute("DROP TABLE IF EXISTS eintrag");
            System.out.println("Info: Tabelle gelöscht - [ok]");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public Connection disconnect(Connection conn) {
        try {
            conn.close();
            conn = null;
            System.out.println("Info: Connection geschlossen" + "[ok]");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}