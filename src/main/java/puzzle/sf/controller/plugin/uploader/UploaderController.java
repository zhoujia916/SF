package puzzle.sf.controller.plugin.uploader;

import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import puzzle.sf.controller.BaseController;
import puzzle.sf.utils.FileUtil;
import puzzle.sf.utils.ImageUtil;
import puzzle.sf.utils.Result;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller(value = "pluginUploaderController")
@RequestMapping(value = {"/plugin"})
public class UploaderController extends BaseController {

    @ResponseBody
    @RequestMapping(value = {"/uploader"})
     public Result index(){
        Result result = new Result();

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(session.getServletContext());

        if(multipartResolver.isMultipart(request)) {

            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            Map<String, MultipartFile> files = multiRequest.getFileMap();

            try {
                List<String> urls = new ArrayList<String>();
                for (String key : files.keySet()) {
                    MultipartFile file = files.get(key);
                    String saveName = createName(file);
                    String url = upload(file, saveName);
                    urls.add(url);
                }
                if(urls.size() == 1){
                    result.setData(urls.get(0));
                }else{
                    result.setData(urls);
                }
            }
            catch (Exception e){
                result.setCode(1);
                result.setMsg("保存文件数据出错");
            }
        }
        return result;
    }

    private String createName(MultipartFile file){
        String savePath = session.getServletContext().getRealPath("") + "/upload/";
        String typePath = getParameter("type");
        if(typePath != null && typePath != ""){
            savePath += typePath + "/";
        }
        String saveName = savePath + PathFormatter.format(file.getName(), "{yy}{MM}{dd}/{hh}{mm}{rand:6}");
        File saveFile = new File(saveName);
        boolean exist = saveFile.exists();
        int index = 1;
        while (exist){
            saveName = FileUtil.getFileName(saveName, false) + "(" + index + ")." + FileUtil.getFileExt(saveName);
            exist = new File(saveName).exists();
            index++;
        }
        return saveName;
    }
}
