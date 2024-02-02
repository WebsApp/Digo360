package com.wapss.digo360.interfaces;

import com.wapss.digo360.response.MostSearchResponse;
import com.wapss.digo360.response.TopDiseaseResponse;

public interface MostDiseaseClick {
    void onItemClickedItem(MostSearchResponse.Result item, int position);
}
