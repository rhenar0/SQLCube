package blueproject.sqlcube.sqlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MFuncSQLite {
    private static final String MOD_ID = "SQLCube";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static int CreateDB(String name) {
        Connection c;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
            LOGGER.info("[SQL] Opened database successfully");
            c.close();
            LOGGER.info("[SQL] Database created successfully");
            return 0;
        } catch ( Exception e ) {
            LOGGER.error("[SQL] Error creating database");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 1;
        }
    }

    public static int CreateTable(String name, String table, String[] columns, String[] types) {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
            LOGGER.info("[SQL] Opened database successfully");

            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder("CREATE TABLE " + table + " (");
            for (int i = 0; i < columns.length; i++) {
                sql.append(columns[i]).append(" ").append(types[i]).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1));
            sql.append(");");
            stmt.executeUpdate(sql.toString());
            stmt.close();
            c.close();
            LOGGER.info("[SQL] Table created successfully");
            return 0;
        } catch ( Exception e ) {
            LOGGER.error("[SQL] Error creating table");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 1;
        }
    }

    public static int Insert(String name, String table, String[] columns, String[] values) {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
            LOGGER.info("[SQL] Opened database successfully");

            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder("INSERT INTO " + table + " (");
            for (String column : columns) {
                sql.append(column).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1));
            sql.append(") VALUES (");
            for (String value : values) {
                sql.append(value).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1));
            sql.append(");");
            stmt.executeUpdate(sql.toString());
            stmt.close();
            c.close();
            LOGGER.info("[SQL] Inserted successfully");
            return 0;
        } catch ( Exception e ) {
            LOGGER.error("[SQL] Error inserting");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 1;
        }
    }

    public static int Update(String name, String table, String[] columns, String[] values, String[] where) {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
            LOGGER.info("[SQL] Opened database successfully");

            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder("UPDATE " + table + " SET ");
            for (int i = 0; i < columns.length; i++) {
                sql.append(columns[i]).append("=").append(values[i]).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1));
            sql.append(" WHERE ");
            SelectWhere(where, c, stmt, sql.toString());
            LOGGER.info("[SQL] Updated successfully");
            return 0;
        } catch ( Exception e ) {
            LOGGER.error("[SQL] Error updating");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 1;
        }
    }

    public static int Delete(String name, String table, String[] where) {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
            LOGGER.info("[SQL] Opened database successfully");

            stmt = c.createStatement();
            String sql = "DELETE FROM "+table+" WHERE ";
            SelectWhere(where, c, stmt, sql);
            LOGGER.info("[SQL] Deleted successfully");
            return 0;
        } catch ( Exception e ) {
            LOGGER.error("[SQL] Error deleting");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 1;
        }
    }

    public static String GetData(String name, String table, String[] columns, String[] where) {
        Connection c;
        Statement stmt;
        StringBuilder data = new StringBuilder();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
            LOGGER.info("[SQL] Opened database successfully");

            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder("SELECT ");
            for (String s : columns) {
                sql.append(s).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1));
            sql.append(" FROM ").append(table).append(" WHERE ");
            SelectWhere(where, c, stmt, sql.toString());
            ResultSet rs = stmt.executeQuery(sql.toString());
            while ( rs.next() ) {
                for (String column : columns) {
                    data.append(rs.getString(column)).append(" ");
                }
                data.append("\n");
            }
            rs.close();
            stmt.close();
            c.close();
            LOGGER.info("[SQL] Data selected successfully");
            return data.toString();
        } catch ( Exception e ) {
            LOGGER.error("[SQL] Error selecting data");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return data.toString();
        }
    }

    public static List<String> GetDataList(String name, String table, String[] columns, String[] where) {
        Connection c;
        Statement stmt;
        List<String> data = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
            LOGGER.info("[SQL] Opened database successfully");

            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder("SELECT ");
            for (String s : columns) {
                sql.append(s).append(",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1));
            sql.append(" FROM ").append(table).append(" WHERE ");
            SelectWhere(where, c, stmt, sql.toString());
            ResultSet rs = stmt.executeQuery(sql.toString());
            while ( rs.next() ) {
                for (String column : columns) {
                    data.add(rs.getString(column));
                }
            }
            rs.close();
            stmt.close();
            c.close();
            LOGGER.info("[SQL] Data selected successfully");
            return data;
        } catch ( Exception e ) {
            LOGGER.error("[SQL] Error selecting data");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return data;
        }
    }

    private static void SelectWhere(String[] where, Connection c, Statement stmt, String sql) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        for (String s : where) {
            sqlBuilder.append(s).append(" AND ");
        }
        sql = sqlBuilder.toString();
        sql = sql.substring(0, sql.length()-5);
        sql += ";";
        stmt.executeUpdate(sql);
        stmt.close();
        c.close();
    }

}
