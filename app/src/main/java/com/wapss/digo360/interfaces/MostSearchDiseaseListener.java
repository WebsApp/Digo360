package com.wapss.digo360.interfaces;

import com.wapss.digo360.response.SettingHomeResponse;
import com.wapss.digo360.response.TopDiseaseResponse;

public interface MostSearchDiseaseListener {
    void onItemClickedItem(SettingHomeResponse.Search item, int position);
}
