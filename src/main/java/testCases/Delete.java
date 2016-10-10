package testCases;

import git.Repo;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * test class to test delete method
 * Created by MVostrikov on 07.10.2016.
 */
public class Delete extends BasicTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Test
    public void smoke() throws Exception {
        Repo repo = null;
        if (testData.getCreatedRepos().size()==0) {
            Create create = new Create();
            create.init(testContext, url);
            create.smoke();
        }
        repo = testData.getCreatedRepos().poll();
        HttpResponse rs = deleteRq(repo);

        String reply = parseReply(rs);

        logger.info(reply);
    }
}
