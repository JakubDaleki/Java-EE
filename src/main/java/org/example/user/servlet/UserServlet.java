package org.example.user.servlet;

import org.example.managers.UsersManager;
import org.example.utils.ServletUtility;
import org.example.utils.UrlFactory;
import org.example.user.request.UserRequest;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

/**
 * Servlet for operating on User entities in operating storage.
 */
@WebServlet(urlPatterns = UserServlet.URI.PATH + "/*")
public class UserServlet extends HttpServlet {

    private final Jsonb jsonb = JsonbBuilder.create();

    public static class URI {
        public static final String PATH = "/users";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ServletUtility.hasUrlArguments(req)) {
            getUser(req, resp);
        } else {
            getUsers(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!ServletUtility.hasUrlArguments(req)) {
            postUser(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ServletUtility.hasUrlArguments(req)) {
            putUser(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ServletUtility.hasUrlArguments(req)) {
            deleteUser(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uuid = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        var user = UsersManager.getUser(uuid);
        if (user.isPresent()) {
            response.getWriter().write(jsonb.toJson(user.get()));
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var users = UsersManager.getUsers();
        if (users.isEmpty()) {
            response.getWriter().write("{}");
        } else {
            response.getWriter().write(jsonb.toJson(users));
        }
    }

    private void postUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserRequest requestBody = jsonb.fromJson(request.getInputStream(), UserRequest.class);

        var uuid = UsersManager.postUser(requestBody);
        if (!uuid.equals("")) {
            response.addHeader(HttpHeaders.LOCATION,
                    UrlFactory.createUrl(request, URI.PATH, uuid));
            response.setStatus(HttpServletResponse.SC_CREATED);
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);

    }

    private void putUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uuid = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        UserRequest requestBody = jsonb.fromJson(request.getInputStream(), UserRequest.class);

        try {
            var result = UsersManager.putUser(uuid, requestBody);
            if (result) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                return;
            }

        } catch (NullPointerException | IllegalArgumentException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);

    }


    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uuid = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        if (UsersManager.deleteUser(uuid)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
