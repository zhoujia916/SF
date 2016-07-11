package puzzle.sf.controller.wx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import puzzle.sf.Constants;
import puzzle.sf.controller.BaseController;
import puzzle.sf.entity.FcaArticle;
import puzzle.sf.entity.FcaArticleCat;
import puzzle.sf.service.IFcaArticleCatService;
import puzzle.sf.service.IFcaArticleService;
import puzzle.sf.utils.ConvertUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller(value = "wxArticleController")
@RequestMapping(value = "/wx/article")
public class WxArticleController extends BaseController {

    @Autowired
    private IFcaArticleCatService fcaArticleCatService;

    @Autowired
    private IFcaArticleService fcaArticleService;

    /**
     * 查询微信article.htrml页面的文章全文
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/view/{articleId}")
    public String article(@PathVariable Integer articleId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("articleId",articleId);
        FcaArticle article=fcaArticleService.query(map);
        article.setAddTimeString(ConvertUtil.toString(ConvertUtil.toDate(
                article.getAddTime()
        ),Constants.DATE_FORMAT));
        List<FcaArticleCat> articleCatList=fcaArticleCatService.queryList(null);
        this.setModelAttribute("article",article);
        this.setModelAttribute("articleCatList",articleCatList);
        return Constants.UrlHelper.WX_ARTICLE;
    }
}
