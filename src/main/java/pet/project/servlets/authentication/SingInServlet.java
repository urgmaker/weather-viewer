package pet.project.servlets.authentication;

import com.password4j.Password;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import pet.project.dao.SessionDao;
import pet.project.dao.UserDao;
import pet.project.exceptions.InvalidParameterException;
import pet.project.exceptions.authentication.UserNotFoundException;
import pet.project.exceptions.authentication.WrongPasswordException;
import pet.project.models.Session;
import pet.project.models.User;
import pet.project.servlets.WeatherTrackerBaseServlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@WebServlet("/sign-in")
public class SingInServlet extends WeatherTrackerBaseServlet {
    private final UserDao userDao = new UserDao();
    private final SessionDao sessionDao = new SessionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Processing sign-in page");
        templateEngine.process("sign-in", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvalidParameterException, UserNotFoundException, WrongPasswordException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || login.isBlank()) {
            throw new InvalidParameterException("Parameter login is invalid");
        }
        if (password == null || password.isBlank()) {
            throw new InvalidParameterException("Parameter password is invalid");
        }

        User user = userDao.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User: " + login + " is not found"));

        String actualPassword = user.getPassword();

        if (!Password.check(password, actualPassword).withBcrypt()) {
            throw new WrongPasswordException("Authentication failed, wrong password. User: " + user.getId());
        }

        log.info("Creating new session");
        Session session = new Session(UUID.randomUUID(), user, LocalDateTime.now().plusHours(24));
        sessionDao.save(session);

        log.info("Adding cookie with the session: " + session.getId() + " to the response");
        Cookie cookie = new Cookie("sessionId", session.getId().toString());
        resp.addCookie(cookie);

        log.info("Authentication is successful: redirecting to the home page");
        resp.sendRedirect(req.getContextPath());
    }
}
