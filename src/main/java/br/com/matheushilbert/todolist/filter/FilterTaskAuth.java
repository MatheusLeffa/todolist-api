package br.com.matheushilbert.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.matheushilbert.todolist.model.UserModel;
import br.com.matheushilbert.todolist.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public FilterTaskAuth(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")) {

            // Get the "Authorization" header from the request, and extract the LOGIN and PASSWORD to a String[].
            String[] credentials = getAuthorizationCredentials(request);
            String login = credentials[0];
            String password = credentials[1];

            // Authenticate user
            UserModel user = userRepository.findByLogin(login);
            if (user == null) {
                response.sendError(401, "invalid credentials!");
            } else {
                // Authenticate password
                BCrypt.Result passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    // Set idUser of the request
                    request.setAttribute("idUser", user.getId());
                    // Go to Controller
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "invalid credentials!");
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private static String[] getAuthorizationCredentials(HttpServletRequest request) {

        String authEncoded = request.getHeader("Authorization")
                .substring("Basic".length())
                .trim();

        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

        return new String(authDecoded).split(":");
    }
}
