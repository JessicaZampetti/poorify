package album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class AlbumDAO {


    private Connection connection;
    public AlbumDAO(Connection connection) {
        this.connection = connection;
    }

    public AlbumBean get(int id) throws SQLException {
        AlbumBean album = null;

        PreparedStatement stmt = connection.prepareStatement(" SELECT * FROM Album a WHERE a.id = ?");
        stmt.setInt(  1, id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            album = resultToBean(rs);
        rs.close(); stmt.close();
        return album;


    }


    public Collection<AlbumBean> getFromArtist(int id)  throws SQLException{
        Collection<AlbumBean> albums = new TreeSet<AlbumBean>((AlbumBean a, AlbumBean b) -> a.getYear() - b.getYear());

        PreparedStatement stmt = connection.prepareStatement("SELECT *FROM Album al, Artist ar " +
                    "WHERE al.artist = ar.id " +
                    "AND ar.id = ?");
        stmt.setInt(1,id);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            albums.add(resultToBean(rs));

        return  albums;

    }



    private AlbumBean resultToBean(ResultSet rs) throws SQLException {


        int id = rs.getInt("id");
        String title = rs.getString("title");
        int tracks = rs.getInt("tracks");
        int duration = rs.getInt("duration");
        int year = rs.getInt("year");

        return new AlbumBean(id, title, tracks, duration, year);

    }



}
