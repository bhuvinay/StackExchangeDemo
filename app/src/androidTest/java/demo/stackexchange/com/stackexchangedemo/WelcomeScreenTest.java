package demo.stackexchange.com.stackexchangedemo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import junit.framework.TestCase;

import demo.stackexchange.com.stackexchangedemo.ui.WelcomeScreen;

/**
 * Created by vinay.pratap on 27-07-2015.
 */
public class WelcomeScreenTest extends ActivityInstrumentationTestCase2<WelcomeScreen> {

    WelcomeScreen welcomeScreen;

    public WelcomeScreenTest() {
        super(WelcomeScreen.class);
        welcomeScreen = getActivity();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testListViewNotNull() {
        ListView lv = (ListView) welcomeScreen.findViewById(R.id.listView1);
        assertNotNull(lv);
    }

}
