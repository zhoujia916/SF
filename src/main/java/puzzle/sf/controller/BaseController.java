package puzzle.sf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import puzzle.sf.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import puzzle.sf.init.InitConfig;
import puzzle.sf.utils.FileUtil;
import puzzle.sf.utils.StringUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URLEncoder;

public class BaseController {

    protected static Logger logger;

    protected HttpServletRequest request;

    protected HttpSession session;

    protected HttpServletResponse response;

    protected ModelMap map;

    @Autowired
    protected InitConfig initConfig;


    public BaseController(){
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @ModelAttribute
    public void initialize(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap map){
        this.request = request;
        this.response = response;
        this.session = session;
        this.map = map;
        this.map.put(Constants.CONTEXT_PATH, session.getServletContext().getContextPath());
    }


    public String redirect(String url){
        String contextPath = session.getServletContext().getContextPath();
        if (StringUtil.isNullOrEmpty(contextPath) && !url.startsWith("/")) {
            url = "/" + url;
        }
        else if(StringUtil.isNotNullOrEmpty(contextPath) && !url.startsWith(contextPath)){
            url = contextPath + (url.startsWith("/") ? "" : "/") + url;
        }
        try {
            this.response.sendRedirect(url);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
//        return "redirect:" + url;
    }

    public String getParameter(String name){
        return request.getParameter(name);
    }

    public void setCookie(String name, String value){
       setCookie(name, value, 0, "/");
    }

    public void setCookie(String name, String value, int expiry){
        setCookie(name, value, expiry, "/");
    }

    public void setCookie(String name, String value, int expiry, String path){
        Cookie cookie = new Cookie(name, value);
        if(expiry > 0) {
            cookie.setMaxAge(expiry);
        }
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    public void setCookie(Cookie cookie){
        response.addCookie(cookie);
    }

    public void removeCookie(String name){
        Cookie[] cookies = request.getCookies();
        if(cookies != null)
            for(Cookie cookie:cookies){
                if(cookie.getName().equals(name)){
                    cookie.setMaxAge(-1);
                    cookie.setValue("");
                    response.addCookie(cookie);
                    break;
                }
            }
    }

    public String getCookie(String name){
        Cookie[] cookies = request.getCookies();
        if(cookies == null)
            return null;
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(name)){
                return cookie.getValue();
            }
        }
        return null;
    }

    public void setCurrentUser(Object user){
        if(session != null) {
            if (user != null) {
                this.session.setAttribute(Constants.SESSION_ADMIN, user);

            } else {
                this.session.removeAttribute(Constants.SESSION_ADMIN);
            }
        }
    }

    public Object getCurrentUser(){
        if(this.session != null && this.session.getAttribute(Constants.SESSION_ADMIN) != null){
            return this.session.getAttribute(Constants.SESSION_ADMIN);
        }
        return  null;
    }

    public void setModelAttribute(String name, Object value){
        if(this.map != null){
            this.map.addAttribute(name, value);
        }
    }

    public Object getRequestAttribute(String name){
        if(this.request != null){
            return this.request.getAttribute(name);
        }
        return null;
    }

    public void writeJson(Object object){
        write("application/json;charset=utf-8", JSONObject.fromObject(object).toString());
    }

    public void writeXml(String xml) throws IOException{
        write("text/xml;charset=utf-8", xml);
    }

    public void writeText(String text){
        write("text/plain;charset=utf-8", text);
    }

    protected void write(String contentType, String content){
        try {
            response.addHeader("Content-Type", contentType);
            response.getWriter().write(content);
        }
        catch (Exception e){
            logger.error("write error:" + e.getMessage());
        }
    }

    protected String upload(MultipartFile file, String filePath) throws Exception{
        FileUtil.checkFileDir(filePath);
        String rootPath = session.getServletContext().getRealPath("");
        String host = request.getScheme() + "://" + request.getServerName();
        if(request.getServerPort() != 80){
            host += ":" + request.getServerPort();
        }
        host += request.getContextPath();
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(file.getBytes());
        fos.close();

        return filePath.replace(rootPath, host);
    }

    protected void download(String filePath) throws Exception{
        OutputStream os = response.getOutputStream();
        response.reset();
        String fileName = FileUtil.getFileName(filePath);
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream; charset=utf-8");
        os.write(FileUtil.readFileByte(filePath));
        os.flush();
    }
}
