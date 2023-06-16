package at.jku.dke.swag.analysis_graphs.operations.operation_types;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.LevelMember;

public class DiceUtils {

    public boolean isLegalDiceNode(AnalysisSituation q, Dimension d, LevelMember m) {
        return !Utils.actual(q.getDiceLevels().get(d)).isStrictlyUnknown()
                && q.getMdGraph().isMemberOf(m, (Level) Utils.actual(q.getDiceLevels().get(d)));
    }

    public boolean isLegalDiceNodePair(AnalysisSituation q, Dimension d, LevelMember m) {
        return isLegalDiceNode(q, d, m)
                || m.isStrictlyUnknown();
    }
}
