package config.folders;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import config.sets.CreateRqSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MVostrikov on 07.10.2016.
 */
@XStreamAlias("createSet")
public class CreateSet {

    @XStreamAlias("positive")
    private Positive positiveCreateSet;

    @XStreamAlias("negative")
    private Negative negativeCreateSet;

    public enum OutcomeSet {POSITIVE, NEGATIVE};

    public List<CreateRqSet> getOutcomeSet() {
        return getOutcomeSet(null);
    }
    public List<CreateRqSet> getOutcomeSet(OutcomeSet outcomeSet) {
        List<CreateRqSet> set = new ArrayList<>();
        if (outcomeSet == null || outcomeSet == OutcomeSet.NEGATIVE) {
            set.addAll(negativeCreateSet.getList());
        }
        if (outcomeSet == null || outcomeSet == OutcomeSet.POSITIVE) {
            set.addAll(positiveCreateSet.getList());
        }
        return set;
    }
}
