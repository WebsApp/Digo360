package com.wapss.digo360.interfaces;

import com.wapss.digo360.response.SearchResponse;
import com.wapss.digo360.response.SettingHomeResponse;

public interface ListDiseaseListener {
    void onItemClickedItem(SearchResponse.Result item, int position);
}
