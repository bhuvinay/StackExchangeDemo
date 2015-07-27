package demo.stackexchange.com.stackexchangedemo.ui;

/**
 * Created by vinay.pratap on 26-07-2015.
 */

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import demo.stackexchange.com.stackexchangedemo.R;
import demo.stackexchange.com.stackexchangedemo.helper.DataBaseHelper;
import demo.stackexchange.com.stackexchangedemo.utils.Constants;
import demo.stackexchange.com.stackexchangedemo.utils.Utility;

public class SearchResultActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, View.OnFocusChangeListener, AdapterView.OnItemClickListener, Filterable {

    private ListView listView;
    private String[] dbValues;
    private DataBaseHelper dbHelp;

    ArrayAdapter<String> searchResultsHistory;
    ArrayList<String> filteredValues;
    private MyFilter myFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        dbHelp = DataBaseHelper.getInstance(this);
        listView = (ListView) findViewById(R.id.searchitems);
        listView.setOnItemClickListener(this);
       // ActionBar bar color set to #669944
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DDF68A1F")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_welcome_screen, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
       // searchView.setBackgroundColor(Color.parseColor("#FF669944"));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextFocusChangeListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(Constants.TAG, "onQuery " + newText);
        searchResultsHistory.getFilter().filter(newText);
        searchResultsHistory.notifyDataSetChanged();

        return true;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
           //search by filtering the user input
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final ArrayList<String> list = dbHelp.getDBSearchQueryList();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list.size() >= 0) {
                                ((TextView) findViewById(R.id.textitem)).setVisibility(View.GONE);
                            }
                            dbValues = (String[]) list.toArray(new String[list.size()]);
                            searchResultsHistory = new ArrayAdapter<>(SearchResultActivity.this, android.R.layout.simple_list_item_1, dbValues);
                            listView.setAdapter(searchResultsHistory);
                        }
                    });
                }
            }).start();


        } else {
            listView.setAdapter(null);
            ((TextView) findViewById(R.id.textitem)).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, WelcomeScreen.class);
        intent.putExtra("searchquery", ((TextView) view).getText().toString());
        startActivity(intent);
        Utility.hideKeyboard(view, SearchResultActivity.this);
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            Log.d(Constants.TAG, "performFiltering " + charSequence.toString());
            FilterResults filterResults = new FilterResults();
            ArrayList<String> tempList = new ArrayList<>();
            if (charSequence != null && charSequence.length() > 0) {
                for (String str : dbValues) {
                    if (str.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        tempList.add(str);
                    }
                }
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = Arrays.asList(dbValues).size();
                filterResults.values = Arrays.asList(dbValues);
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredValues = (ArrayList<String>) filterResults.values;
            searchResultsHistory.notifyDataSetChanged();
        }
    }
}
