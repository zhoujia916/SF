package puzzle.sf.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import puzzle.sf.Constants;
import puzzle.sf.controller.ModuleController;
import puzzle.sf.entity.FcaArticleTemplate;
import puzzle.sf.entity.SystemMenuAction;
import puzzle.sf.service.IFcaArticleTemplateService;
import puzzle.sf.utils.ConvertUtil;
import puzzle.sf.utils.Page;
import puzzle.sf.utils.Result;
import puzzle.sf.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller (value = "adminFcaArticleTemplateController")
@RequestMapping (value = "/admin/fcaarticletemplate")
public class FcaArticleTemplateController extends ModuleController {

    @Autowired
    private IFcaArticleTemplateService fcaArticleTemplateService;

    @RequestMapping (value = {"/","/index"})
    public String index(){
        List<SystemMenuAction> actions = getActions();
        this.setModelAttribute("actions", actions);
        return Constants.UrlHelper.ADMIN_FCA_ARTICLE_TEMPLATE;
    }

    @RequestMapping (value = "/list.do")
    @ResponseBody
    public Result list(FcaArticleTemplate fcaArticleTemplate){
        Result result=new Result();
        try{
            Page page = new Page();
            if(StringUtil.isNotNullOrEmpty(request.getParameter("pageIndex")) && StringUtil.isNumber(request.getParameter("pageIndex"))){
                page.setPageIndex(ConvertUtil.toInt(request.getParameter("pageIndex")));
            }
            if(StringUtil.isNotNullOrEmpty(request.getParameter("pageSize")) && StringUtil.isNumber(request.getParameter("pageSize"))){
                page.setPageSize(ConvertUtil.toInt(request.getParameter("pageSize")));
            }
            Map<String,Object> map = new HashMap<String, Object>();
            if(fcaArticleTemplate!=null){
                if(StringUtil.isNotNullOrEmpty(fcaArticleTemplate.getName())){
                    map.put("name",fcaArticleTemplate.getName());
                }
            }
            List<FcaArticleTemplate> list = fcaArticleTemplateService.queryList(map, page);
            result.setData(list);
        }catch(Exception e){
            result.setCode(1);
            result.setMsg("获取文章类型信息出错");
            logger.error(result.getMsg()+e.getMessage());
        }
        return result;
    }

    @RequestMapping (value = "/action.do")
    @ResponseBody
    public Result action(String action,FcaArticleTemplate fcaArticleTemplate){
        Result result=new Result();
        Map<String, Object> map=new HashMap<String, Object>();
        try{
            if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_CREATE)){
                if(!fcaArticleTemplateService.insert(fcaArticleTemplate)){
                    result.setCode(1);
                    result.setMsg("添加文章类型信息时出错");
                }else{
                    //添加日志
                    insertLog(Constants.PageHelper.PAGE_ACTION_CREATE,"添加文章类型信息");
                }
            }else if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_UPDATE)){
                if(!fcaArticleTemplateService.update(fcaArticleTemplate)){
                    result.setCode(1);
                    result.setMsg("修改文章类型信息时出错");
                }else{
                    //添加日志
                    insertLog(Constants.PageHelper.PAGE_ACTION_UPDATE,"修改指定的文章类型信息");
                }
            }else if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_DELETE)){
                String id=request.getParameter("id");
                String ids=request.getParameter("ids");
                if(StringUtil.isNotNullOrEmpty(id)){
                    map.put("templateId", ConvertUtil.toInt(id));
                }else if(StringUtil.isNotNullOrEmpty(ids)){
                    map.put("templateIds",ids);
                }
                if(!fcaArticleTemplateService.delete(map)){
                    result.setCode(1);
                    result.setMsg("删除文章类型信息时出错");
                }else{
                    //添加日志
                    insertLog(Constants.PageHelper.PAGE_ACTION_DELETE,"删除指定的文章类型信息");
                }
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMsg("操作文章类型信息时出错!");
            logger.error(result.getMsg()+e.getMessage());
        }
        return result;
    }
}
