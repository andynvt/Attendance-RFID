/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author chuna
 */
public class StringSearchable implements Searchable<String, String> {

    private List<String> terms = new ArrayList<>();

    public StringSearchable(List<String> terms) {
        this.terms.addAll(terms);
    }

    @Override
    public Collection<String> search(String value) {
        List<String> founds = new ArrayList<>();
        terms.forEach((s) -> {
            if (s.indexOf(value) == 0) {
                founds.add(s);
            }
        });
        return founds;

    }

}
