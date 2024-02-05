package vasler.dddlecture.adapters.primary.htmx;

import org.springframework.context.annotation.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping( "/ui")
public class IndexController {
    @GetMapping("")
    @ResponseBody
    public String index() {
        return HtmlFragments.PAGE_HEADER +
            HtmlFragments.PAGE_FOOTER;
    }

    // TODO
    @GetMapping("/error")
    @ResponseBody
    public String error() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
            .anyMatch(r -> r.getAuthority().equals("ROLE_USER"));

        return HtmlFragments.PAGE_HEADER +

            """
            ERROR""" +

            HtmlFragments.PAGE_FOOTER;
    }
}
