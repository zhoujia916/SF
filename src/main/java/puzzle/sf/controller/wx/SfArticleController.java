package puzzle.sf.controller.wx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import puzzle.sf.Constants;
import puzzle.sf.controller.BaseController;
import puzzle.sf.entity.SfArticle;
import puzzle.sf.init.InitConfig;
import puzzle.sf.service.ISfArticleService;
import puzzle.sf.utils.ConvertUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller(value = "wxSfArticleController")
@RequestMapping(value = "/wx/article")
public class SfArticleController extends BaseController {

    @Autowired
    private InitConfig initConfig;

    @Autowired
    private ISfArticleService sfArticleService;

    @RequestMapping(value = {"/{articleId}"})
    public String show(@PathVariable Integer articleId) {
        String url = request.getRequestURL().toString();
        if(request.getQueryString() != null)
            url += "?"+request.getQueryString();
        WxApi api = new WxApi();
        this.setModelAttribute("wxsign", api.sign(url));

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("articleId", articleId);
        SfArticle article = sfArticleService.query(map);
        if(article != null){
            article.setAddDate(ConvertUtil.toString(ConvertUtil.toDate(article.getAddTime()), Constants.DATE_FORMAT));
            this.setModelAttribute("article", article);

            return Constants.UrlHelper.WX_SF_ARTICLE;
        }
        return null;
    }

    public class WxApi{

        public Map<String, String> sign(String url) {
            String jsapi_ticket = "jsapi_ticket";
            return sign(jsapi_ticket, url);
        };

        public Map<String, String> sign(String jsapi_ticket, String url) {
            Map<String, String> ret = new HashMap<String, String>();
            String nonce_str = create_nonce_str();
            String timestamp = create_timestamp();
            String signature = "";

            //注意这里参数名必须全部小写，且必须有序
            String string1 = "jsapi_ticket=" + jsapi_ticket +
                             "&noncestr=" + nonce_str +
                             "&timestamp=" + timestamp +
                             "&url=" + url;

            try
            {
                MessageDigest crypt = MessageDigest.getInstance("SHA-1");
                crypt.reset();
                crypt.update(string1.getBytes("UTF-8"));
                signature = byteToHex(crypt.digest());
            }
            catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }

            ret.put("url", url);
            ret.put("jsapi_ticket", jsapi_ticket);
            ret.put("nonceStr", nonce_str);
            ret.put("timestamp", timestamp);
            ret.put("signature", signature);
            ret.put("appid", initConfig.getProperty("wx.AppID"));
            return ret;
        }

        private String byteToHex(final byte[] hash) {
            Formatter formatter = new Formatter();
            for (byte b : hash){
                formatter.format("%02x", b);
            }
            String result = formatter.toString();
            formatter.close();
            return result;
        }

        private String create_nonce_str() {
            return UUID.randomUUID().toString();
        }

        private String create_timestamp() {
            return Long.toString(System.currentTimeMillis() / 1000);
        }
    }
}