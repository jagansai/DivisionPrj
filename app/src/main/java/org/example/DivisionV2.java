package org.example;

import java.util.List;

public record DivisionV2(String name, List<String> depts) implements IDivision {

    @Override
    public List<String> getDepts() {
        return depts;
    }

    @Override
    public String getName() {
        return name;
    }
}
