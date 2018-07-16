package io.github.saltytt.grouptracker.settings;

import io.github.saltytt.grouptracker.Main;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

import java.sql.*;

public class Database {

    public static Database standard;
    private static Connection con;
    private final String pathToDB = Main.FILE_PATH + "/db/database";
    private final String connectionString = String.format("jdbc:hsqldb:file:%s", pathToDB);


    public static void init() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        standard = new Database();
        standard.createTables();
    }

    private void createTables() {
        final String serverTable = "CREATE TABLE IF NOT EXISTS server_info (id BIGINT PRIMARY KEY, chan BIGINT);";
        performMutation(serverTable);
    }

    public String getChannelForGuild(Guild guild) {
        final String query = String.format("SELECT chan FROM server_info WHERE id = %s", guild.getId());
        ResultSet results = performQuery(query);
        try {
            if (results.next()) return results.getString("chan");
            else return "error";
        } catch (SQLException e) { e.printStackTrace(); }
        return "error";
    }

    public boolean setChannelForGuild(Guild guild, TextChannel channel) {
        final String mutation = String.format(" MERGE INTO server_info USING (VALUES(%s, %s))" +
                "   AS vals(x,y) ON server_info.id = vals.x" +
                "   WHEN MATCHED THEN UPDATE SET server_info.chan = vals.y" +
                "   WHEN NOT MATCHED THEN INSERT VALUES vals.x, vals.y", guild.getId(), channel.getId());
        return performMutation(mutation);
    }

    private boolean performMutation(String mutation) {
        boolean success = false;
        try {
            con = DriverManager.getConnection(connectionString, "SA", "");

            con.createStatement().executeUpdate(mutation);
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return success;
    }

    private ResultSet performQuery(String query) {
        try {
            con = DriverManager.getConnection(connectionString, "SA", "");
            return con.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

}
