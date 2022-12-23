package playlist;

import netscape.javascript.JSObject;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddTrackToPlaylist", value = "/AddTrackToPlaylist")
public class AddTrackToPlaylist extends HttpServlet {
    private PlaylistDAO playlistDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        playlistDAO = (PlaylistDAO) super.getServletContext().getAttribute("PlaylistDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        int enduser = (int) request.getSession().getAttribute("enduser");
        int track = Integer.parseInt(request.getParameter("track"));
        int playlist = Integer.parseInt(request.getParameter("playlist"));

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("outcome",playlistDAO.addTrackToPlaylist(enduser,track,playlist));
            response.getWriter().print(jsonObject);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
