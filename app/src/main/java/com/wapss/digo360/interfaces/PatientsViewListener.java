package com.wapss.digo360.interfaces;

import com.wapss.digo360.response.PatientsDetailsViewResponse;
import com.wapss.digo360.response.TopDiseaseResponse;

public interface PatientsViewListener {
    void onItemClickedItem(PatientsDetailsViewResponse.Result item, int position);
}
