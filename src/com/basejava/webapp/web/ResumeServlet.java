package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.util.DateUtil;
import com.basejava.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        if (HtmlUtil.isEmpty(fullName)) {
            request.setAttribute("errorMessage", "Full name cannot be empty");
            Resume resume = (uuid == null || uuid.isEmpty()) ? Resume.EMPTY : storage.get(uuid);
            request.setAttribute("resume", resume);
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
            return;
        }

        final boolean isCreate = (uuid == null || uuid.isEmpty());
        Resume resume;
        if (isCreate) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.getContacts().remove(type);
            } else {
                resume.addContact(type, value);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && (values == null || values.length < 2)) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> {
                        resume.addSection(type, new TextSection(value));
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
//                            String joinedValues = String.join("\\n", values);
//                            resume.addSection(type, new ListSection(List.of(joinedValues.split("\\n"))));
                        resume.addSection(type, new ListSection(value.split("\\n")));
                    }
                    case EDUCATION, EXPERIENCE -> {
                        List<Company> org = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Period> periods = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        periods.add(new Period(titles[j], descriptions[j], DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j])));
                                    }
                                }
                                String url = (urls != null && urls.length > i) ? urls[i] : "";
                                org.add(new Company(name, url, periods));
                            }
                        }
                        resume.addSection(type, new CompanySection(org));
                    }
                }
            }
        }
        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view" -> resume = storage.get(uuid);
            case "add" -> resume = Resume.EMPTY;
            case "edit" -> {
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE, PERSONAL -> {
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                        }
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                        }
                        case EXPERIENCE, EDUCATION -> {
                            CompanySection orgSection = (CompanySection) section;
                            List<Company> emptyNewCompany = new ArrayList<>();
                            emptyNewCompany.add(Company.EMPTY);
                            if (orgSection != null) {
                                for (Company org : orgSection.getCompanies()) {
                                    List<Period> emptyNewPeriods = new ArrayList<>();
                                    emptyNewPeriods.add(Period.EMPTY);
                                    emptyNewPeriods.addAll(org.getPeriods());
                                    emptyNewCompany.add(new Company(org.getName(), org.getWebsite(), org.getPeriods()));
                                }
                            }
                            section = new CompanySection(emptyNewCompany);
                            resume.addSection(type, section);
                        }
                    }
                }
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}

