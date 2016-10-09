package config.folders;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import config.sets.CreateRqSet;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MVostrikov on 07.10.2016.
 */
@XStreamAlias("negative")
public class Negative extends Positive {

    @XStreamImplicit(itemFieldName = "createRqSet")
    private List<CreateRqSet> setList;

    @Override
    public List<CreateRqSet> getList() {
        if (setList == null )setList = new ArrayList<>();
        for (CreateRqSet set : setList) set.positive = false;
        return setList;
    }
}
