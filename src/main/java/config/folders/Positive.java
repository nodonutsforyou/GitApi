package config.folders;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import config.sets.CreateRqSet;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * holder of positive test cases
 * Created by MVostrikov on 07.10.2016.
 */
@XStreamAlias("positive")
public class Positive {

    @XStreamImplicit(itemFieldName = "createRqSet")
    @Getter
    private List<CreateRqSet> setList;

    /**
     * returns positive test cases
     */
    public List<CreateRqSet> getList() {
        if (setList == null )setList = new ArrayList<>(); //if there is no cases - we return empty list, not null
        for (CreateRqSet set : setList) set.positive = true; //for every test case we set it's outcome as positive
        return setList;
    }
}
