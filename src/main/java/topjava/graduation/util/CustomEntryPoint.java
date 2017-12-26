package topjava.graduation.util;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//https://stackoverflow.com/questions/42358856/how-to-return-401-for-rest-api-call-in-spring-security

public class CustomEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";

    public CustomEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}