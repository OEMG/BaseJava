package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(
                "<html>" +
                        "<head>" +
                        "   <meta charset=UTF-8>" +
                        "   <title>DB resume</title>" +
                        "<style>" +
                        "   table, th, td { border: 1px solid black; border-collapse: collapse;}" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "   <table>" +
                        "       <tr>" +
                        "           <th>UUID</th>" +
                        "           <th>Full Name</th>" +
                        "       </tr>");
        for (Resume resume : storage.getAllSorted()) {
            writer.write(
                    "<tr>" +
                            "<td>" + resume.getUuid() + "</td>" +
                            "<td>" + resume.getFullName() + "</td>" +
                         "</tr>\n");
        }
        writer.write("</table></body></html>");
    }
}

