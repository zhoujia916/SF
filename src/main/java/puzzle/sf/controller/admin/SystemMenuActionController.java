package puzzle.sf.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import puzzle.sf.Constants;
import puzzle.sf.controller.ModuleController;
import puzzle.sf.entity.SystemMenuAction;
import puzzle.sf.service.ISystemMenuActionService;
import puzzle.sf.utils.ConvertUtil;
import puzzle.sf.utils.Result;
import puzzle.sf.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller(value="adminSystemMenuActionController")
@RequestMapping(value = {"/admin/systemmenuaction"})
public class SystemMenuActionController extends ModuleController {

    @Autowired
    private ISystemMenuActionService systemMenuActionService;


    @RequestMapping(value = {"/index/{menuId}"})
    public String index(@PathVariable int menuId){
        this.setModelAttribute("menuId", menuId);
        return Constants.UrlHelper.ADMIN_SYSTEM_MENU_ACTION;
    }

    @RequestMapping(value = { "/list.do" })
    @ResponseBody
    public Result list(){
        Result result = new Result();
        Map<String,Object> map = new HashMap<String, Object>();
        if(StringUtil.isNotNullOrEmpty(request.getParameter("menuId"))){
            map.put("menuId", ConvertUtil.toInt(request.getParameter("menuId")));
        }
        try {
            List<SystemMenuAction> actions = systemMenuActionService.queryList(map);
            if(actions != null && actions.size() > 0) {
                result.setData(actions);
            }
        }
        catch (Exception e){
            result.setCode(1);
            result.setMsg("获取菜单操作信息出错！");
            logger.error("获取菜单操作信息出错: " + e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = {"/action.do"})
    @ResponseBody
    public Result action(String action, SystemMenuAction menuAction){
        Result result = new Result();
        try {
            if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_CREATE)) {
                if (!systemMenuActionService.insert(menuAction)) {
                    result.setCode(1);
                    result.setMsg("保存菜单操作信息失败！");
                }
            }
            else if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_UPDATE)){
                if(!systemMenuActionService.update(menuAction)){
                    result.setCode(1);
                    result.setMsg("保存菜单操作信息失败！");
                }
            }
            else if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_DELETE)){
                Map<String,Object> map = new HashMap<String, Object>();
                if(StringUtil.isNotNullOrEmpty(request.getParameter("id"))){
                    map.put("actionId", request.getParameter("id"));
                }
                else if(StringUtil.isNotNullOrEmpty(request.getParameter("ids"))){
                    map.put("actionIds", request.getParameter("ids"));
                }
                if(!systemMenuActionService.delete(map)){
                    result.setCode(1);
                    result.setMsg("删除菜单操作信息失败！");
                }
            }
        }
        catch (Exception e){
            result.setCode(-1);
            result.setMsg("系统处理出错！");
            logger.error("系统处理出错: " + e.getMessage());
        }

        return result;
    }
}
