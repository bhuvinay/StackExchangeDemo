package demo.stackexchange.com.stackexchangedemo.utils;

/**
 * Created by vinay.pratap on 17-07-2015.
 */

public class Constants {

    public static final String TAG = "StackOverflow";
   //used for diiferentiating screen
    public static final int ANSWER = 1;
    public static final int QUESTION = 2;

    public static final String Site_Param_Value = "stackoverflow";
    public static final String Sort_Param_Value = "relevance";
    public static final String Sort_Ans_Param_Value = "votes";
    public static final String Order_Param_Value = "desc";
    public static final String Page_Size_Param_Value = "20";
    public static final String Order_Param_Filter = "!9YdnSM68i";

    //Http Query URL for Search [Questions] ..
    public static final String URL_SEARCH_Question = "https://api.stackexchange.com/2.2/search/advanced";
    public static final String URL_SEARCH_Param_Order = "order";
    public static final String URL_SEARCH_Param_Sort = "sort";
    public static final String URL_SEARCH_Page_Size = "pagesize";
    public static final String URL_SEARCH_Param_Intitle = "q"; // user inputted value ..
    public static final String URL_SEARCH_Param_Site = "site";

    public static final String URL_Search_Question_Query = URL_SEARCH_Question + "?" +
            URL_SEARCH_Page_Size + "=" + Page_Size_Param_Value + "&" +
            URL_SEARCH_Param_Order + "=" + Order_Param_Value + "&" +
            URL_SEARCH_Param_Sort + "=" + Sort_Param_Value + "&" +
            URL_SEARCH_Param_Site + "=" + Site_Param_Value + "&" +
            URL_SEARCH_Param_Intitle + "=";

    //Http Query URL for Search [Questions] ..
    public static final String URL_SEARCH_Answer = "http://api.stackexchange.com/2.2/questions/";
    public static final String URL_ANS_Param_filter = "filter";

    public static final String URL_Search_Answer_Query = "/answers" + "?" +
            URL_SEARCH_Param_Order + "=" + Order_Param_Value + "&" +
            URL_SEARCH_Param_Sort + "=" + Sort_Ans_Param_Value + "&" +
            URL_SEARCH_Param_Site + "=" + Site_Param_Value + "&" +
            URL_ANS_Param_filter + "=" + Order_Param_Filter;

    // Constants for Question qsBean ..

    public static final String Q_ITEM = "items";
    public static final String Q_OWNER = "owner";
    public static final String Q_OWNER_DISPLAY_NAME = "display_name";
    public static final String Q_TITLE = "title";
    public static final String Q_score = "score";
    public static final String Q_Id = "question_id";

    public static final String Q_HAS_MORE = "has_more";
    public static final String Q_MAX_QUOTA = "quota_max";
    public static final String Q_REMAIN = "quota_remaining";

    public static final String A_ITEM = "items";
    public static final String A_OWNER = "owner";
    public static final String A_OWNER_DISPLAY_NAME = "display_name";
    public static final String A_BODY = "body";
    public static final String A_score = "score";
    public static final String A_Id = "answer_id";


}
