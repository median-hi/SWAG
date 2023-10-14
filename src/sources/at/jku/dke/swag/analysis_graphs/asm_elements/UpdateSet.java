package at.jku.dke.swag.analysis_graphs.asm_elements;

import java.util.Set;
import java.util.stream.Collectors;

public final class UpdateSet {

    public static boolean verifyConsistent(Set<Update> updateSet) {
        return updateSet.size() == updateSet.stream().map(u -> u.getLocation()).collect(Collectors.toSet()).size();
    }
}
