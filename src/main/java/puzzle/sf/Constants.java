package puzzle.sf;


import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String SESSION_USER = "CURRENT_USER";
    public static final String SESSION_ADMIN = "CURRENT_ADMIN";

    public static final String COOKIE_USER = "USERINFO";
    public static final String COOKIE_ADMIN = "ADMININFO";

    public static final String CONTEXT_PATH = "contextPath";

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final Integer DEFAULT_USER_TYPE = 2;

    public static final Map<Integer,Object> MAP_ACTION = new HashMap<Integer, Object>() {
        {
            put(1, "有效");
            put(2, "无效");
        }
    };

    public static final Map<Integer, String> MAP_USER_SEX = new HashMap<Integer, String>(){
        {
            put(1,"男");
            put(2,"女");
        }
    };

    public static final Map<Integer, String> MAP_LOG_TYPE = new HashMap<Integer, String>() {
        {
            put(1, "查看");
            put(2, "增加");
            put(3, "删除");
            put(4, "修改");
        }
    };

    public static final Map<Integer, String> MAP_ROLE_TYPE = new HashMap<Integer, String>(){
        {
            put(1,"系统角色");
            put(2,"自定义角色");
        }
    };

    //SystemUser Type
    public static final Integer USER_MAP_TARGET_TYPE_ROLE = 1;
    public static final Integer USER_MAP_TARGET_TYPE_GROUP = 2;
    public static final Integer USER_MAP_TARGET_TYPE_DEPT = 3;

    public static final Integer SYSTEM_AUTHORITY_TARGET_MENU = 1;
    public static final Integer SYSTEM_AUTHORITY_TARGET_ACTION = 2;

    public static final Map<String, Integer> MAP_ACTION_LOG_MAPPING = new HashMap<String, Integer>(){
        {
            put("SELECT",1);
            put("CREATE",2);
            put("DELETE",3);
            put("UPDATE",4);
        }
    };

<<<<<<< HEAD
    public static final Integer DEFAULT_ROLE_TYPE = 2;

    public static final Integer AUTO_AUTHORITY_TARGET_TYPE_MENU = 1;
    public static final Integer AUTO_AUTHORITY_TARGET_TYPE_ACTION = 2;
=======
    /**
     * 1=账户有效  2=账务无效
     */
    public static final Integer FCA_USER_STATUS_DISABLED = 1;
    public static final Integer FCA_USER_STATUS_NORMAL = 2;

    public static final Integer SYSTEM_AUTHORITY_TARGET_MENU = 1;
    public static final Integer SYSTEM_AUTHORITY_TARGET_ACTION = 2;

    public static final Map<Integer, String> MAP_AUTO_ARTICLE_STATUS = new HashMap<Integer, String>(){
        {
            put(1,"已保存");
            put(2,"已提交");
            put(3,"已审核");
        }
    };


    //endregion

    //region AUTO_CAR_TYPE
    public static final Integer AUTO_CAR_HAS_PARTS_NO = 1;
    public static final Integer AUTO_CAR_HAS_PARTS_YES = 2;
    public static final Map<Integer, String> MAP_AUTO_CAR_HAS_PARTS = new HashMap<Integer, String>(){
        {
            put(1,"没有配件");
            put(2,"有配件");
        }
    };
    //endregion



    public static final Integer DEFAULT_ROLE_TYPE = 2;

>>>>>>> 26373d77ab36e6a5ba4665b75eb361c769e39bdb

    //region System User Status
    public static final Integer SYSTEM_USER_STATUS_VALID = 1;
    public static final Integer SYSTEM_USER_STATUS_INVALID = 2;
    public static final Map<Integer, String> MAP_SYSTEM_USER_STATUS = new HashMap<Integer, String>(){
        {
            put(1,"有效");
            put(2,"无效");
        }
    };
    //endregion

    public static final Map<Integer, String> MAP_SF_ARTICLE_STATUS = new HashMap<Integer, String>(){
        {
            put(1,"已保存");
            put(2,"已提交");
            put(3,"已审核");
        }
    };

    public class UrlHelper{
        public static final String PARAM_RETURN_URL = "ReturnUrl";

        /**
         *  系统管理模块
         */

        public static final String ADMIN_SYSTEM_LOGIN = "admin/login";

        public static final String ADMIN_SYSTEM_MAIN = "admin/main";

        public static final String ADMIN_SYSTEM_INDEX = "admin/index";

        public static final String ADMIN_SYSTEM_MENU = "admin/system/menu/index";

        public static final String ADMIN_SYSTEM_LOG = "admin/system/log/index";

        public static final String ADMIN_SYSTEM_ROLE = "admin/system/role/index";

        public static final String ADMIN_SYSTEM_USER = "admin/system/user/index";

        public static final String ADMIN_SYSTEM_USER_GROUP = "admin/system/usergroup/index";

        public static final String ADMIN_SYSTEM_CONFIG = "admin/system/config/index";

        public static final String ADMIN_SYSTEM_DEPT = "admin/system/dept/index";

        public static final String ADMIN_SYSTEM_AUTHORITY = "admin/system/authority/index";

        public static final String ADMIN_SYSTEM_MENU_ACTION = "admin/system/menuaction/index";

        /**
         *  业务管理模块
         */

        public static final String ADMIN_SF_ARTICLE = "admin/sf/article/index";

<<<<<<< HEAD
        public static final String ADMIN_SF_ARTICLE_SHOW = "admin/sf/article/show";
=======
        public static final String ADMIN_FCA_USER_SHOW = "admin/auto/user/show";

        public static final String ADMIN_FCA_ARTICLE = "admin/auto/article/index";

        public static final String ADMIN_FCA_ARTICLE_SHOW = "admin/auto/article/show";

        public static final String ADMIN_FCA_ARTICLE_CAT = "admin/auto/articlecat/index";

        public static final String ADMIN_FCA_ARTICLE_TEMPLATE = "admin/auto/articletemplate/index";

        /**
         * 微信模块
         */
        public static final String WX_INDEX = "wx/index";

        public static final String WX_ARTICLE = "wx/article/index";

        public static final String WX_USER_LOGIN = "wx/user/login";
        public static final String WX_USER_FORGET = "wx/user/forget";
>>>>>>> 26373d77ab36e6a5ba4665b75eb361c769e39bdb
    }

    public class PageHelper{
        public static final String PAGE_NAME = "PAGE";

        public static final int PAGE_SIZE_COMMON = 20;

        public static final int PAGE_INDEX_FIRST = 1;

        public static final int PAGE_SIZE_MAX = Integer.MAX_VALUE;

        public static final String PAGE_ACTION_CREATE = "CREATE";

        public static final String PAGE_ACTION_UPDATE = "UPDATE";

        public static final String PAGE_ACTION_DELETE = "DELETE";

        public static final String PAGE_ACTION_SELECT = "SELECT";

        public static final String PAGE_ACTION_IMPORT = "IMPORT";

        public static final String PAGE_ACTION_EXPORT = "EXPORT";

        public static final String PAGE_ACTION_SEARCH = "SEARCH";

        public static final String PAGE_ACTION_SAVE = "SAVE";

        public static final String PAGE_ACTION_VIEW = "VIEW";
    }
}
