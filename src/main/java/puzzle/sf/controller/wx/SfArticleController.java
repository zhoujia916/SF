package puzzle.sf.controller.wx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import puzzle.sf.Constants;
import puzzle.sf.controller.ModuleController;
import puzzle.sf.entity.SfArticle;
import puzzle.sf.service.ISfArticleService;
import puzzle.sf.utils.ConvertUtil;

import java.util.HashMap;

@Controller(value = "wxSfArticleController")
@RequestMapping(value = "/wx/article")
public class SfArticleController extends ModuleController {

    @Autowired
    private ISfArticleService sfArticleService;

    @RequestMapping(value = {"/{articleId}"})
    public String show(@PathVariable Integer articleId) {
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

}