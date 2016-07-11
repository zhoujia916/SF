package puzzle.sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import puzzle.sf.Constants;
import puzzle.sf.entity.SystemAuthority;
import puzzle.sf.entity.SystemLog;
import puzzle.sf.entity.SystemMenuAction;
import puzzle.sf.entity.SystemUser;
import puzzle.sf.service.ISystemLogService;
import puzzle.sf.service.ISystemMenuActionService;
import puzzle.sf.utils.CommonUtil;
import puzzle.sf.utils.ConvertUtil;
import puzzle.sf.utils.StringUtil;

import java.util.*;

public class ModuleController extends BaseController {

    @Autowired
    private ISystemLogService systemLogService;

    @Autowired
    private ISystemMenuActionService systemMenuActionService;

    public void insertLog(String action, String msg){
        try{
            int userId = 0;

            if(getCurrentUser() != null){
                userId = ((SystemUser)getCurrentUser()).getUserId();
            }
            SystemLog log = new SystemLog();
            log.setLogMsg(msg);
            log.setUserId(userId);
            log.setLogTime(ConvertUtil.toLong(new Date()));
            log.setLogType(Constants.MAP_ACTION_LOG_MAPPING.get(action));
            log.setLogIp(CommonUtil.getClientIp(request));
            systemLogService.insert(log);
        }
        catch (Exception e){

        }
    }

    public List<SystemMenuAction> getActions(){
        if(getCurrentUser() != null){
            SystemUser user = (SystemUser)getCurrentUser();
            if(user.getAuthorities() != null) {
                String url = request.getRequestURI().replace(request.getContextPath() + "/admin/", "");

                int menuId = 0;
                List<Integer> actionIds = new ArrayList<Integer>();
                for (SystemAuthority authority : user.getAuthorities()) {
                    if(StringUtil.isNotNullOrEmpty(authority.getMenuUrl())){
                        if(authority.getMenuUrl().equals(url) && authority.getTargetType() == Constants.SYSTEM_AUTHORITY_TARGET_MENU){
                            menuId = authority.getTargetId();
                            break;
                        }
                    }
                }
                for (SystemAuthority item : user.getAuthorities()) {
                    if(item.getTargetType() == Constants.SYSTEM_AUTHORITY_TARGET_ACTION){
                        actionIds.add(item.getTargetId());
                    }
                }
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("menuId", menuId);
                map.put("actionIds", StringUtil.concat(actionIds, ","));
                List<SystemMenuAction> actions = systemMenuActionService.queryList(map);
                return actions;
            }
        }
        return null;
    }
}
