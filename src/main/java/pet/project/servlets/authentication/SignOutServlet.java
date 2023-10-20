package pet.project.servlets.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import pet.project.dao.SessionDao;
import pet.project.exceptions.CookieNotFoundException;
import pet.project.exceptions.SessionExpiredException;
import pet.project.models.Session;
import pet.project.servlets.WeatherTrackerBaseServlet;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@WebServlet("/sign-out")
public class SignOutServlet extends WeatherTrackerBaseServlet {
    private final SessionDao sessionDao = new SessionDao();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Finding cookie with session id");
        Cookie[] cookies = req.getCookies();
        Cookie cookie = findCookieByName(cookies, "sessionId")
                .orElseThrow(() -> new CookieNotFoundException("Cookie with session id is not found"));

        UUID sessionId = UUID.fromString(cookie.getValue());

        log.info("Finding session: " + sessionId);
        Session session = sessionDao.findById(sessionId)
                .orElseThrow(() -> new SessionExpiredException("Session: " + sessionId + " has expired"));

        if (isSessionExpired(session)) {
            throw new SessionExpiredException("Session: " + sessionId + " has expired");
        }

        log.info("Deleting session: " + sessionId + " from database");
        sessionDao.delete(session);

        log.info("Deleting cookie from response");
        Cookie emptyCookie = new Cookie("sessionId", null);
        emptyCookie.setMaxAge(0);
        resp.addCookie(emptyCookie);

        log.info("Sign-out is successful: redirecting to the home page");
        resp.sendRedirect(req.getContextPath());
    }
}
