package puzzle.sf.controller.wx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import puzzle.sf.Constants;
import puzzle.sf.controller.BaseController;
import puzzle.sf.entity.FcaUser;
import puzzle.sf.service.IFcaUserService;
import puzzle.sf.utils.EncryptUtil;
import puzzle.sf.utils.Result;
import puzzle.sf.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

@Controller(value = "wxUserController")
@RequestMapping(value = "/wx/user")
public class WxUserController extends BaseController {

    @Autowired
    private IFcaUserService fcaUserService;

    /**
     * 进入微信登录页面
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(){
        return Constants.UrlHelper.WX_USER_LOGIN;
    }

    /**
     * 验证登录
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login.do")
    @ResponseBody
    public Result sign(String userName,String password){
        Result result=new Result();
        try{
            if(StringUtil.isNullOrEmpty(userName) || StringUtil.isNullOrEmpty(password)){
                result.setCode(-1);
                result.setMsg("用户名或密码不能为空！");
                return result;
            }
            userName = userName.trim();
            password = password.trim();

            if(!StringUtil.isRangeLength(userName, 3, 20) || !StringUtil.isRangeLength(password, 6, 20)){
                result.setCode(-1);
                result.setMsg("用户名或密码不正确！");
                return result;
            }
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("userName",userName);
            map.put("passWord", EncryptUtil.MD5(password));
            FcaUser user=fcaUserService.query(map);
            if(user == null){
                result.setCode(1);
                result.setMsg("用户名或密码不正确！");
                return result;
            }
            if(user.getStatus() == Constants.FCA_USER_STATUS_DISABLED){
                result.setCode(1);
                result.setMsg("该账户无效，不能登录！");
                return result;
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMsg("登录验证出错！");
            logger.error(result.getMsg()+e.getMessage());
        }
        return result;
    }
}
