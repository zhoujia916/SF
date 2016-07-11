package puzzle.sf.controller.admin;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import puzzle.sf.entity.FcaUser;
import puzzle.sf.entity.SystemMenuAction;
import puzzle.sf.service.IFcaUserService;
import puzzle.sf.utils.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller(value = "adminFcaUserController")
@RequestMapping(value = "/admin/fcauser")
public class FcaUserController extends ModuleController {

    @Autowired
    private IFcaUserService fcaUserService;


    @RequestMapping (value = {"/","/index"})
    public String index(){
        List<SystemMenuAction> actions = getActions();
        this.setModelAttribute("actions", actions);
        return Constants.UrlHelper.ADMIN_FCA_USER;
    }

    @RequestMapping (value = {"/add"})
    public String add(){
        this.setModelAttribute("action", Constants.PageHelper.PAGE_ACTION_CREATE);
        return Constants.UrlHelper.ADMIN_FCA_USER_SHOW;
    }

    @RequestMapping (value = {"/edit/{userId}"})
    public String edit(@PathVariable Integer userId){
        if(userId != null && userId > 0){
            FcaUser user = fcaUserService.query(userId, null);
            if(user != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userId", userId);
                map.put("userId", userId);
                user.setBirthString(ConvertUtil.toString(ConvertUtil.toDate(user.getBirth()),Constants.DATE_FORMAT));
                this.setModelAttribute("user",user);
            }
        }
        this.setModelAttribute("action", Constants.PageHelper.PAGE_ACTION_UPDATE);
        return Constants.UrlHelper.ADMIN_FCA_USER_SHOW;
    }

    @RequestMapping (value = {"/view/{userId}"})
    public String view(@PathVariable Integer userId){
        if(userId != null && userId > 0){
            FcaUser user = fcaUserService.query(userId, null);
            if(user != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userId", userId);
                user.setBirthString(ConvertUtil.toString(ConvertUtil.toDate(user.getBirth()),Constants.DATE_FORMAT));
                this.setModelAttribute("user",user);
            }
        }
        this.setModelAttribute("action", Constants.PageHelper.PAGE_ACTION_VIEW);
        return Constants.UrlHelper.ADMIN_FCA_USER_SHOW;
    }

    /**
     * 获取会员信息
     * @param fcaUser
     * @return
     */
    @RequestMapping(value = "/list.do")
    @ResponseBody
    public Result list(FcaUser fcaUser, Page page){
        Result result = new Result();
        try{
            Map<String, Object> map=new HashMap<String, Object>();
            if(fcaUser != null) {
                if(StringUtil.isNotNullOrEmpty(fcaUser.getUserName())) {
                    map.put("userName", fcaUser.getUserName());
                }
                if(fcaUser.getStatus() != null && fcaUser.getStatus() > 0){
                    map.put("status",fcaUser.getStatus());
                }
            }
            List<FcaUser> list = fcaUserService.queryList(map,page);
            if(list != null && list.size() > 0){
                JSONArray array=new JSONArray();
                for(FcaUser user:list){
                    JSONObject jsonObject=JSONObject.fromObject(user);
                    array.add(jsonObject);
                }
                result.setData(array);
                result.setTotal(page.getTotal());
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMsg("获取会员信息出错");
            logger.error(result.getMsg()+e.getMessage());
        }
        return result;
    }

    /**
     * 操作会员信息
     * @param action
     * @param fcaUser
     * @return
     */
    @RequestMapping(value = "/action.do")
    @ResponseBody
    public Result action(String action,FcaUser fcaUser){
        Result result=new Result();
        Map<String, Object> map=new HashMap<String, Object>();
        try{
            if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_CREATE)){
                FcaUser find = fcaUserService.query(null, fcaUser.getUserName());
                if(find != null){
                    result.setCode(-1);
                    result.setMsg("该账户已存在！");
                    return result;
                }

                if(StringUtil.isNullOrEmpty(fcaUser.getPassword())){
                    fcaUser.setPassword("666666");
                }
                fcaUser.setPassword(EncryptUtil.MD5(fcaUser.getPassword()));

                String avatar = saveAvatar();
                if(StringUtil.isNotNullOrEmpty(avatar)){
                    fcaUser.setUserAvatar(avatar);
                }else{
                    fcaUser.setUserAvatar("/resource/admin/avatars/profile-pic.jpg");
                }
                fcaUser.setAddTime(ConvertUtil.toLong(new Date()));
                fcaUser.setStatus(Constants.FCA_USER_STATUS_DISABLED);
                fcaUser.setSortOrder(0);
                if(StringUtil.isNotNullOrEmpty(fcaUser.getBirthString())){
                    fcaUser.setBirth(ConvertUtil.toLong(ConvertUtil.toDate(fcaUser.getBirthString())));
                }
                if(!fcaUserService.insert(fcaUser)){
                    result.setCode(1);
                    result.setMsg("新建会员信息失败");
                }else{
                    //添加日志
                    insertLog(Constants.PageHelper.PAGE_ACTION_CREATE,"新建会员信息");
                }
            }else if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_UPDATE)){
                String avatar = saveAvatar();
                if(StringUtil.isNotNullOrEmpty(avatar)){
                    fcaUser.setUserAvatar(avatar);
                }else{
                    fcaUser.setUserAvatar("/resource/admin/avatars/profile-pic.jpg");
                }
                if(StringUtil.isNotNullOrEmpty(fcaUser.getBirthString())){
                    fcaUser.setBirth(ConvertUtil.toLong(ConvertUtil.toDate(fcaUser.getBirthString())));
                }
                if(!fcaUserService.update(fcaUser)){
                    result.setCode(1);
                    result.setMsg("更新会员信息失败");
                }else{
                    //添加日志
                    insertLog(Constants.PageHelper.PAGE_ACTION_UPDATE,"更新会员信息");
                }
            }else if(action.equalsIgnoreCase(Constants.PageHelper.PAGE_ACTION_DELETE)){
                String id = request.getParameter("id");
                String ids = request.getParameter("ids");
                if(StringUtil.isNotNullOrEmpty(id)){
                    map.put("userId",ConvertUtil.toInt(id));
                }else if(StringUtil.isNotNullOrEmpty(ids)){
                    map.put("userIds", ids);
                }
                if(!fcaUserService.delete(map)){
                    result.setCode(1);
                    result.setMsg("删除会员信息失败");
                }else{
                    //添加日志
                    insertLog(Constants.PageHelper.PAGE_ACTION_DELETE,"删除特定的会员信息");
                }
            }
        }catch(Exception e){
            result.setCode(1);
            result.setMsg("操作会员信息出错");
            logger.error(result.getMsg()+e.getMessage());
        }
        return result;
    }

    public String saveAvatar(){
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(session.getServletContext());
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                MultipartFile cover = multiRequest.getFile("file");

                String typePath = "avatar";
                String savePath = session.getServletContext().getRealPath("") + "/upload/" + typePath + "/";
                String relativeUrl = request.getContextPath() + "/upload/" + typePath + "/";
                String saveName = PathFormatter.format(cover.getOriginalFilename(), "{yy}{MM}{dd}/{hh}{mm}{rand:6}");
                String dirName = savePath + saveName.substring(0, saveName.lastIndexOf('/'));
                File dir = new File(dirName);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                FileOutputStream fos = new FileOutputStream(savePath + saveName);
                fos.write(cover.getBytes());
                fos.close();

                String url = request.getScheme() + "://" + request.getServerName();
                if (request.getServerPort() != 80) {
                    url += ":" + request.getServerPort();
                }
                url += relativeUrl + saveName;

                return url;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}