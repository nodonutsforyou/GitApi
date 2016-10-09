import com.google.common.truth.Truth;
import git.CreateRq;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Created by MVostrikov on 07.10.2016.
 */
public class Create extends BasicTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Test
    public void smoke() throws Exception {
        CreateRq createRq = CreateRq.RANDOM();
        HttpResponse rs = sendPostJson("/user/repos", createRq);

        String reply = parseReply(rs);

        logger.info(reply);

        Truth.assertThat(rs.getStatusLine().getStatusCode()).isEqualTo(201);

        testData.getCreatedRepos().add(createRq.created());
    }
}
