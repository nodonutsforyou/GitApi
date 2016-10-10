package config.folders;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import config.sets.CreateRqSet;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * holder of negative test cases
 * Created by MVostrikov on 07.10.2016.
 */
@XStreamAlias("negative")
public class Negative extends Positive {

    @XStreamImplicit(itemFieldName = "createRqSet")
    private List<CreateRqSet> setList;

    /**
     * returns negative test cases
     */
    @Override
    public List<CreateRqSet> getList() {
        if (setList == null )setList = new ArrayList<>(); //if there is no cases - we return empty list, not null
        for (CreateRqSet set : setList) set.positive = false; //for every test case we set it's outcome as negative
        return setList;
    }
}
