package controller;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import model.Comment;
import model.Movie;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import service.CommentService;
import service.MovieService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HomeController implements IController {

    public void process(final HttpServletRequest request, final HttpServletResponse response, final ServletContext servletContext, final ITemplateEngine templateEngine) throws Exception {
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
//        List<Movie> list = new MovieService().getMoviesforHomePage();

        String url = "/?search=true";
        String by=null;
        String value=null;
        String text = null;

        if (request.getParameter("by") != null) {
            by = request.getParameter("by").trim();
            url = url + "&by=" + by;
        }
        if (request.getParameter("value") != null) {
            value = request.getParameter("value").trim();
            url = url + "&value=" + value;
        }
        if (request.getParameter("text") != null) {
            text = request.getParameter("text").trim();
            url = url + "&text=" + text;
        }
        ctx.setVariable("url", url);  //For paging

        boolean showCarousel = true;
        boolean showBreadcrumb = true;
        if (by != null || text != null) {  //Filter
            showCarousel = false;
            if (by != null)
                ctx.setVariable("breadCrumb", value);
            else if (text != null)
                ctx.setVariable("breadCrumb", "Search result for: <b>" + text + "</b>");
        } else { //Home
            showBreadcrumb = false;
        }
        ctx.setVariable("showCarousel", showCarousel);
        ctx.setVariable("showBreadcrumb", showBreadcrumb);

        int page = 1;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page").trim());

        ctx.setVariable("page", page);

        long totalPages = new MovieService().getTotalPages(by, value,text);
        ctx.setVariable("totalPages", totalPages);

        List<Movie> list = new MovieService().searchMovies(by, value, page,text);
        ctx.setVariable("list", list);

        templateEngine.process("index", ctx, response.getWriter());
    }
}