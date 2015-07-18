package demo.stackexchange.com.stackexchangedemo.intface;

/**
 * Created by vinay.pratap on 18-07-2015.
 */

import java.util.ArrayList;


import demo.stackexchange.com.stackexchangedemo.utils.QsBean;

public interface JsonParserCallback {
		void setQuesListData(ArrayList<QsBean> mData);
}
