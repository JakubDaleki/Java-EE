package org.example.user.servlet;

import org.example.managers.AvatarsManager;
import org.example.utils.ServletUtility;
import org.example.utils.UrlFactory;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.*;

/**
 * Servlet for operating on Users Avatars permanent storage.
 */
@WebServlet(urlPatterns = UsersAvatarServlet.URI.PATH + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class UsersAvatarServlet extends HttpServlet {

    private final Jsonb jsonb = JsonbBuilder.create();

    public static class URI {
        public static final String PATH = "/users/avatars";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ServletUtility.hasUrlArguments(req)) {
            getAvatar(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ServletUtility.hasUrlArguments(req)) {
            postAvatar(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ServletUtility.hasUrlArguments(req)) {
            putAvatar(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ServletUtility.hasUrlArguments(req)) {
            deleteAvatar(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uuid = ServletUtility.parseRequestPath(request).replaceAll("/", "");

        byte[] imageInByte = AvatarsManager.getAvatar(uuid);
        if (imageInByte.length > 0) {
            response.addHeader(HttpHeaders.CONTENT_TYPE, "image/jpg");
            response.setContentLength(imageInByte.length);
            response.getOutputStream().write(imageInByte);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void postAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Part portrait = request.getPart("avatar");

        if (AvatarsManager.saveAvatar(uuid, portrait)) {
            response.addHeader(HttpHeaders.LOCATION,
                    UrlFactory.createUrl(request, URI.PATH, uuid));
            response.setStatus(HttpServletResponse.SC_CREATED);
            return;
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void putAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Part portrait = request.getPart("avatar");

        if (AvatarsManager.overwriteAvatar(uuid, portrait)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uuid = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        if (AvatarsManager.removeAvatar(uuid)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
