package at.jku.dke.swag.analysis_graphs.operations.operation_types.dice_ops;

import at.jku.dke.swag.analysis_graphs.AnalysisSituation;
import at.jku.dke.swag.analysis_graphs.basic_elements.Parameter;
import at.jku.dke.swag.analysis_graphs.utils.Utils;
import at.jku.dke.swag.md_elements.Dimension;
import at.jku.dke.swag.md_elements.Level;
import at.jku.dke.swag.md_elements.LevelMember;

public class DiceUtils {

    public static boolean isLegalDiceNode(AnalysisSituation q, Dimension d, LevelMember m) {
        return !Utils.actual(q.getDiceLevels().get(d)).isUnknown()
                && q.getMdGraph().isMemberOf(m, (Level) Utils.actual(q.getDiceLevels().get(d)));
    }

    public static boolean isLegalDiceNodePair(AnalysisSituation q, Dimension d, LevelMember m) {
        return isLegalDiceNode(q, d, m)
                || m.isUnknown();
    }

    public static boolean isLegalDiceNodePair(AnalysisSituation q, Dimension d, Parameter p, LevelMember m) {
        return isLegalDiceNode(q, d, m)
                || m.isUnknown();
    }

    public static boolean isLegalDiceLevelPair(AnalysisSituation q, Dimension d, Level m) {
        return true;
    }

    public static boolean isLegalDiceLevelPair(AnalysisSituation q, Dimension d, Parameter p, Level m) {
        return true;
    }
}
