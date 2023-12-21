package com.wapss.digo360.interfaces;

import com.wapss.digo360.response.TopDiseaseResponse;

public interface TopDiseaseListener {
    void onItemClickedItem(TopDiseaseResponse.Result item, int position);
}
