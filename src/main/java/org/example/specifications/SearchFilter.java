package org.example.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFilter {
    private String[] key;
    private Object[] value;
    private String[] operation;
}
