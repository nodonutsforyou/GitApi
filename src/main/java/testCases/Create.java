package testCases;

import com.google.common.truth.Truth;
import git.CreateRq;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * test to smoke-test create request
 * Created by MVostrikov on 07.10.2016.
 */
public class Create extends BasicTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Test
    public void smoke() throws Exception {
        CreateRq createRq = CreateRq.RANDOM(); //random name and empty other fileds
        HttpResponse rs = sendPostJson("/user/repos", createRq);

        String reply = parseReply(rs);

        logger.info(reply);

        Truth.assertThat(rs.getStatusLine().getStatusCode()).isEqualTo(201); //check status code

        testData.getCreatedRepos().add(createRq.created()); //added to list to delete it at test end
    }
}
