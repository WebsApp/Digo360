package com.wapss.digo360.interfaces;

import com.wapss.digo360.response.QuestionResponse;
import com.wapss.digo360.response.SearchResponse;

public interface AnswerListener {
    void onItemClickedItem(QuestionResponse.DiseaseAnswer item, int position,String questionId);
}
