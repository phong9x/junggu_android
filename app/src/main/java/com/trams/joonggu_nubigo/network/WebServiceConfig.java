package com.trams.joonggu_nubigo.network;

/**
 * Created by zin9x on 10/5/2015.
 */
public class WebServiceConfig {


    public static int NETWORK_TIME_OUT = 30000;
    public static int NETWORK_LONG_THREADSHOT = 30000;
    public static int NETWORK_LIMIT_SHUTDOWN_CONNECTION = 60000;
    //==================== DOMAIN =====================


    //    public static String BASE_IMAGE_URL = "http://192.168.170.159:9999/junggu";
    public static String BASE_IMAGE_URL = "http://14.63.215.19";

    public static String APP_DOMAIN = BASE_IMAGE_URL + "/";
//    public static String APP_DOMAIN = "http://192.168.170.159:8080/Junggu/";
//    public static String APP_DOMAIN = "http://tomcat.outsourcing-org.com";

    //=================== API==============
    public static final String API_SIGNUP = "rest/user/registor";
    public static final String API_LOGIN = "rest/user/login";
    public static final String API_NOTICE = "rest/items/notice";
    public static final String API_SETTING = "rest/items/accessibility";
    public static final String API_STORE = "rest/items/store";
    public static final String API_POST_COMMENT = "rest/api/comment";
    public static final String API_GET_LIST_COMMENT = "rest/api/comment";
    public static final String API_POST_REPORT = "rest/api/report";
    public static final String API_FIELD = "rest/items/field";
    public static final String API_CATEGORY = "rest/items/category";
    public static final String API_FACILITY = "rest/items/facility";
    public static final String API_SCRAP = "rest/items/store/user_comment";
    public static final String API_UPDATE_SCRAP_STATUS = "rest/items/store/scrap_status";


    //=================== URL ======================
    public static final String URL_SIGNUP = APP_DOMAIN + API_SIGNUP;
    public static final String URL_LOGIN = APP_DOMAIN + API_LOGIN;
    public static final String URL_UPDATE_NOTICE = APP_DOMAIN + API_NOTICE;
    public static final String URL_UPDATE_SETTING = APP_DOMAIN + API_SETTING;
    public static final String URL_UPDATE_STORE = APP_DOMAIN + API_STORE;
    public static final String URL_POST_COMMENT = APP_DOMAIN + API_POST_COMMENT;
    public static final String URL_GET_LIST_COMMENT = APP_DOMAIN + API_GET_LIST_COMMENT;
    public static final String URL_UPDATE_SCRAP_STATUS = APP_DOMAIN + API_UPDATE_SCRAP_STATUS;

    public static final String URL_POST_REPORT = APP_DOMAIN + API_POST_REPORT;

    public static final String URL_UPDATE_FIELD = APP_DOMAIN + API_FIELD;
    public static final String URL_UPDATE_CATEGORY = APP_DOMAIN + API_CATEGORY;
    public static final String URL_UPDATE_FACILITY = APP_DOMAIN + API_FACILITY;
    public static final String URL_UPDATE_SCRAP = APP_DOMAIN + API_SCRAP;

    public static final String SERVER_DB_URL = APP_DOMAIN + "db/db.zip";


    //=================== PARAMETER =========================
    //SIGNUP
    public static final String SIGNUP_ID = "id";
    public static final String SIGNUP_USERNAME = "username";
    public static final String SIGNUP_PASSWORD = "password";
    public static final String SIGNUP_NICKNAME = "nickname";
    public static final String SIGNUP_FULLNAME = "fullname";
    public static final String SIGNUP_ROLE = "role";
    public static final String SIGNUP_SEX = "sex";
    public static final String SIGNUP_PHONE = "phone";
    public static final String SIGNUP_EMAIL = "email";
    public static final String SIGNUP_AGE = "age";
    public static final String SINGNUP_UPDATE_DATE = "updatedDate";
    public static final String SIGNUP_CREATE_DATE = "createDate";

    //LOGIN
    public static final String LOGIN_USERNAME = "username";
    public static final String LOGIN_PASSWORD = "password";
    public static final String LOGIN_ROLE = "role";

    public static final String PARAM_NUMBER_PHONE = "phoneNumber";

    //KEY
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TAG = "tag";
    public static final String KEY_SERVICE_HOURS = "serviceHours";
    public static final String KEY_HOLIDAY = "holiday";
    public static final String KEY_BUILDING_FORM = "buildingForm";
    public static final String KEY_FLOOR = "floor";
    public static final String KEY_FACILITY_LIST = "facilityList";
    public static final String KEY_REPRESENTATIVE = "representative";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_MONITORING_DATE = "monitoringDate";
    public static final String KEY_MONITORING_MAN = "monitoringMan";
    public static final String KEY_MONITORING_MAN_PHONE = "monitoringManPhone";
    public static final String KEY_FIELD_LIST = "fieldList";
    public static final String KEY_IMAGE_BASE_ATTACH = "imageBaseAttach";
    public static final String KEY_IMAGE_EXTEND_ATTACH = "imageExtendAttach";
    public static final String KEY_GRADE = "grade";
    public static final String KEY_ACCESSIBILITYLIST = "accessibilityList";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_CREATE_DATE = "createDate";
    public static final String KEY_UPDATE_DATE = "updateDate";
    public static final String KEY_CAT_ID = "catId";
    public static final String KEY_USER_ID = "userId";

    public static final String KEY_DISTANCE = "distance";
    public static final String PARAM_DETAIL = "details";
    public static final String PARAM_STORE_ID = "storeId";
    public static final String PARAM_GRADE = "grade";
    public static final String PARAM_ID = "id";
    public static final String PARAM_USER_ID = "user_id";
    public static final String PARAM_SCRAP_STATUS = "scrap_status";
}
