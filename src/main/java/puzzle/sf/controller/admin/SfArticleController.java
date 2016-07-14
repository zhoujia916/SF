package puzzle.sf.controller.admin;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import puzzle.sf.Constants;
import puzzle.sf.controller.ModuleController;
import puzzle.sf.controller.plugin.uploader.PathFormatter;
import puzzle.sf.entity.SfArticle;
import puzzle.sf.entity.SystemMenuAction;
import puzzle.sf.entity.SystemUser;
import puzzle.sf.service.ISfArticleService;
import puzzle.sf.utils.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Controller(value = "adminSfArticleController")
@RequestMapping(value = "/admin/sfarticle")
public class SfArticleController extends ModuleController{

    @Autowired
    private ISfArticleService sfArticleService;

    @RequestMapping(value = {"/", "/index"})
    public String index(){
        List<SystemMenuAction> actions = getActions();
        this.setModelAttribute("actions", actions);
        return Constants.UrlHelper.ADMIN_SF_ARTICLE;
    }

    @RequestMapping("/add")
    public String add(){
        this.setModelAttribute("action",Constants.PageHelper.PAGE_ACTION_CREATE);
        return Constants.UrlHelper.ADMIN_SF_ARTICLE_SHOW;
    }

    @RequestMapping("/edit/{articleId}")
    public String edit(@PathVariable Integer articleId){
        if(articleId!=null && articleId>0){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("articleId",articleId);
            SfArticle article=sfArticleService.query(map);
            if(article!=null){
                this.setModelAttribute("article",article);
            }
        }
        this.setModelAttribute("action",Constants.PageHelper.PAGE_ACTION_UPDATE);
        return Constants.UrlHelper.ADMIN_SF_ARTICLE_SHOW;
    }

    @RequestMapping(value = "/view/{articleId}")
    public String view(@PathVariable Integer articleId){
        if(articleId!=null && articleId>0){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("articleId",articleId);
            SfArticle article=sfArticleService.query(map);
            if(article!=null){
                this.setModelAttribute("article",article);
            }
        }
        this.setModelAttribute("action",Constants.PageHelper.PAGE_ACTION_VIEW);
        return Constants.UrlHelper.ADMIN_SF_ARTICLE_SHOW;
    }

    @RequestMapping(value = "/list.do")
    @ResponseBody
    public Result list(SfArticle article,Page page){
        Result result=new Result();
        try{
            Map<String, Object> map=new HashMap<String, Object>();
            if(article!=null){
                if(StringUtil.isNotNullOrEmpty(article.getTitle())){
                    map.put("title",article.getTitle());
                }
                if(StringUtil.isNotNullOrEmpty(article.getAuth())){
                    map.put("auth",article.getAuth());
                }
                if(article.getStatus() != null && article.getStatus() > 0){
                    map.put("status",article.getStatus());
                }
                if(StringUtil.isNotNullOrEmpty(article.getStartDate())){
                    map.put("startDate", ConvertUtil.toLong(ConvertUtil.toDate(article.getStartDate())));
                }
                if(StringUtil.isNotNullOrEmpty(article.getEndDate())){
                    map.put("endDate", ConvertUtil.toLong(ConvertUtil.toDate(article.getEndDate())));
                }
            }
            List<SfArticle> list=sfArticleService.queryList(map,page);
            if(list!=null && list.size()>0){
                JSONArray array=new JSONArray();
                for(SfArticle sfArticle:list){
                    JSONObject object=JSONObject.fromObject(sfArticle);
                    object.put("addTime",ConvertUtil.toString(ConvertUtil.toDate(sfArticle.getAddTime())));
                    object.put("status",Constants.MAP_SF_ARTICLE_STATUS.get(sfArticle.getStatus()));
                    array.add(object);
                }
                result.setData(array);
                result.setTotal(page.getTotal());
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMsg("获取文章信息出错！");
            logger.error(result.getMsg()+e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/action.do")
    @ResponseBody
    public Result action(String action,SfArticle article){
        Result result=new Result();
        try{
            if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_CREATE)){
                article.setAddUserId(((SystemUser)getCurrentUser()).getUserId());
                article.setStatus(2);
                article.setAddTime(ConvertUtil.toLong(new Date()));
                String cover = saveCover();

                if(StringUtil.isNotNullOrEmpty(cover)){
                    article.setCover(cover);
                }
                if(!sfArticleService.insert(article)){
                    result.setCode(1);
                    result.setMsg("添加文章时出错");
                }else{
                    //添加日志
                    insertLog(Constants.PageHelper.PAGE_ACTION_CREATE,"添加文章信息");
                }
            }else if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_UPDATE)){
                String cover = saveCover();
                if(StringUtil.isNotNullOrEmpty(cover)){
                    article.setCover(cover);
                }
                else{
                    if(StringUtil.isNullOrEmpty(article.getCover())){
                        article.setCover("");
                    }
                }
                if(!sfArticleService.update(article)){
                    result.setCode(1);
                    result.setMsg("修改文章信息时出错");
                }else{
                    //添加日志
                    insertLog(Constants.PageHelper.PAGE_ACTION_UPDATE, "修改特定的文章信息");
                }
            }else if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_DELETE)){
                //删除
                String id =request.getParameter("id");
                String ids=request.getParameter("ids");
                if(StringUtil.isNotNullOrEmpty(id)){
                    map.put("articleId",ConvertUtil.toInt(id));
                }else if(StringUtil.isNotNullOrEmpty(ids)){
                    map.put("articleIds",ids);
                }
                if(!sfArticleService.delete(map)){
                    result.setCode(1);
                    result.setMsg("删除文章信息出错");
                }else{
                    //添加日志
                    insertLog(Constants.PageHelper.PAGE_ACTION_DELETE,"删除特定的文章信息");
                }
            }else{
                result.setCode(-1);
                result.setMsg("参数出错");
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMsg("操作文章信息出错！");
            logger.error(result.getMsg()+e.getMessage());
        }
        return result;
    }

    public String saveCover(){
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(session.getServletContext());
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                MultipartFile cover = multiRequest.getFile("file");
                if(cover != null) {
                    String saveName = session.getServletContext().getRealPath("") + "/upload/article/";

                    int min = (int)Math.pow(10, 5);
                    int max = (int)Math.pow(10, 6);
                    Random random = new Random(new Date().getTime());
                    int number = random.nextInt(max - min + 1) + min;

                    saveName += ConvertUtil.toString(new Date(), "yyMMdd/HHmmss") + number + "." + FileUtil.getFileExt(cover.getOriginalFilename());

                    return upload(cover, saveName);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
