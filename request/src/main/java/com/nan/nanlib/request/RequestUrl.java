package com.nan.nanlib.request;

/**
 * @author liujiannan
 * @date 2018/7/24
 */
public class RequestUrl {


    // 清单相关
    public static class DailyList{
        /*获取前6个月的收费记录*/
        public static String CHARGE_RECORD_HALF_YEAR_POST_URL="rest/charge/month/amount.json";
        /*门诊收费记录*/
        public static String CHARGE_RECORD_LIST_POST_URL="rest/charge/record.json";
        /*门诊收费清单*/
        public static String CHARGE_RECORD_DETAIL_POST_URL="rest/charge/record/detail.json";
        /*获取清单列表*/
        public static final String DAY_LIST_POST_URL="rest/daylist/list.json";
        /*住院每日清单详细*/
        public static String ZhUYUAN_DAY_LIST_POST_URL="rest/hospitalized/daylist/detail.json";
    }

    // 医院相关
    public static class Hospital{
        // 获取医院列表
        public static final String HOSPITAL_POST_URL = "rest/hospital/query/all.json";
        // 获取指定医院信息
        public static final String QUERY_HOSPITAL_POST_URL = "rest/hospital/query/param.json";
        // 获取科室列表
        public static final String DEPARTMENT_INTRO_INIT_PAGE="rest/departmentintro/init.json";
        // 科室介绍详情
        public static final String GET_DEPARTMENT_INTRO="rest/departmentintro/department.json";
        // 获取科室诊疗项目信息
        public static final String GET_DEPARTMENT_CLINICITEM="rest/departmentintro/clinicitem.json";
        // 获取功能列表
        public static final String HOSPITALFUNCTION_POST_URL = "rest/hospital/function/query.json";
    }

    // app相关
    public static class AppInfo {
        public static final String UPDATE_APP="http://www.191cn.com/YlzAppUpdate/app/public/lastversion.json";
        public static final String GET_HOSPITAL_IMAGELIST_URL="rest/advimage/query.json";
    }

    // 用户登录相关
    public static class UserInfo {
        public static final String LOGIN_INIT_POST_URL =  "rest/user/login/init.json";
    }

    // 文章资讯相关
    public static class ArticalList{
        // 新闻公告TAB
        public static final String ARTICAL_LIST_TAB_URL =  "rest/api/news/getNewsType.json";
        // 新闻公告列表
        public static final String ARTICAL_LIST_URL =  "rest/api/news/queryPage.json";
        // 新闻公告详情
        public static final String ARTICAL_LIST_DETAIL_URL =  "rest/api/news/getNews.json";
        // 文章点击
        public static final String ARTICAL_DETAIL_CLICK = "rest/api/news/addCnView.json";

        // 停诊通知TAB
        public static final String ARTICAL_LIST_DIAGNOSIS_TAB_URL =  "rest/api/news/getDiagnosis.json";
        // 停诊通知列表
        public static final String ARTICAL_LIST_DIAGNOSIS_URL =  "rest/api/news/queryDiagnosis.json";

        // 医保政策TAB
        public static final String ARTICAL_LIST_MEDICAL_POLICY_TAB_URL =  "rest/api/news/getMedicalPolicy.json";
        // 医保政策列表
        public static final String ARTICAL_LIST_MEDICAL_POLICY_URL =  "rest/api/news/queryMedicalPolicy.json";

        // 特色科室TAB
        public static final String ARTICAL_LIST_FEATURE_DEPARTMENT_TAB_URL =  "rest/api/news/getColorDepartment.json";
        // 特色科室列表
        public static final String ARTICAL_LIST_FEATURE_DEPARTMENT_URL =  "rest/api/news/queryColorDepartment.json";

        // 医院资讯TAB
        public static final String ARTICAL_LIST_HOSPITAL_INFO_TAB_URL =  "rest/api/news/getHospitalInformation.json";
        // 医院信息列表
        public static final String ARTICAL_LIST_HOSPITAL_INFO_URL =  "rest/api/news/queryHospitalInformation.json";
    }

    // 订单相关
    public static class Order{
        // 龙岩人民医院获取订单号
        public static final String ORDER_GET_ORDER_NUMBER =  "rest/topup/getOrderFormNo.json";

        // 梧州医保结算查询医保结算结果
        public static final String SETTLEMENT_BY_HEALTHY_INSURANCE =  "rest/topup/queryMedicareInfo.json";

        // 梧州医保结算完成接口
        public static final String SETTLEMENT_BY_HEALTHY_FINISH =  "rest/chargepayoff/check.json";
    }
}
