package org.example.specifications;

import java.util.ArrayList;
import java.util.List;

public class CriteriaArrayToList {

    public static List<SearchCriteria> getCriteriaList(SearchFilter criteria) {
        List<SearchCriteria> searchCriteria = new ArrayList<>();
        for (int i = 0; i < criteria.getKey().length; i++) {
            searchCriteria.add(new SearchCriteria(criteria.getKey()[i], criteria.getValue()[i], criteria.getOperation()[i]));
        }
        return searchCriteria;
    }
}
