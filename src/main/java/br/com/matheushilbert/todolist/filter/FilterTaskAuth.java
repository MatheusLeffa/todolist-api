package br.com.matheushilbert.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.matheushilbert.todolist.user.model.UserModel;
import br.com.matheushilbert.todolist.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")) {

            // Pegar o header "Authorization" da request, e extrair o LOGIN e PASSWORD para um String[].
            String[] credencials = getAuthorizationCredentials(request);
            String login = credencials[0];
            String password = credencials[1];

            // Validar usuário
            UserModel user = userRepository.findByLogin(login);
            if (user == null) {
                response.sendError(401, "Usuário não existe!");
            } else {

                // Validar senha
                BCrypt.Result passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    // Set idUser of the request
                    request.setAttribute("idUser", user.getId());
                    // Segue para o Controller
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "Senha incorreta!");
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
