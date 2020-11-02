package com.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/Parse")
public class ParseServlet extends HttpServlet {

    public ParseServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String nm = request.getParameter("url");

        //Connecting to the URL inserted
        Document doc = Jsoup.connect(nm).get();

        //Fetch Title of the pasted URL
        String title = doc.title().toUpperCase();
        out.println("<p style=\"background-color:tomato;\"><b>The title of the webpage is........</b></p>" + title);
        out.println("<br>");
        out.println("<br>");
        out.println("<p style=\"background-color:tomato;\"><b>NOW we have all the urls inside the url....WHAT???</b></p>");
        out.println("<br>");

        //Set of url inside the pasted URL
        //Used Set to remove duplicate URL address
        Set<String> linkinside = new HashSet<String>();
        Elements elements = doc.select("a[href]");
        for (Element element : elements) {
            linkinside.add(element.attr("href") + "<br>");
        }
        out.println(linkinside);
        out.println("<br>");
        out.println("<br>");

        //HTTP response status code of the pasted URL
        try {
            String url = request.getParameter("url");
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            int code = connection.getResponseCode();
            out.println("<p style=\"background-color:tomato;\"><b>The Http code of the URL is...............</b></p>" + code);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
