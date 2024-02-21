package com.wapss.digo360.interfaces;

import com.wapss.digo360.response.QuestionResponse;
import com.wapss.digo360.response.TopDiseaseResponse;

public interface HomeTopDiseaseListener {
    void onItemClickedItem(TopDiseaseResponse.Result item, int position);
}
