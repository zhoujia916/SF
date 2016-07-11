package puzzle.sf.intercept;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import puzzle.sf.Constants;
import puzzle.sf.entity.SystemAuthority;
import puzzle.sf.entity.SystemMenu;
import puzzle.sf.entity.SystemUser;
import puzzle.sf.service.ISystemMenuActionService;
import puzzle.sf.service.ISystemMenuService;
import puzzle.sf.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AuthorityInterceptor extends HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI().replace(request.getContextPath(), "");
        if(isExcludePath(path)){
            return true;
        }
        HttpSession session = request.getSession();
        SystemUser user = (SystemUser)session.getAttribute(Constants.SESSION_ADMIN);
        if(user != null){
            path = path.replace("/admin/", "");
            boolean authorize = false;
            for(String url : user.getUrls()){
                if(path.matches(url)){
                    authorize = true;
                    break;
                }
            }
            if(!authorize){
                logger.debug("User [" + user.getUserName() + "] request url:" + path + " is deny...");
            }
            return authorize;
        }
        return true;
    }


}
