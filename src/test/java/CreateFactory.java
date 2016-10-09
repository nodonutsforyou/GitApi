import com.google.common.truth.Truth;
import config.TestData;
import git.CreateRq;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by MVostrikov on 07.10.2016.
 */
public class CreateFactory {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected TestData testData;

    @Parameters({"url"})
    @Factory
    public Object[] createApiFactory(final ITestContext testContext, String url) {

        testData = TestData.getTestdata(url, testContext.getName());

        List<CreateRq> list = testData.getCreateObjects();
        Object[] objects = new Object[list.size()];
        for(int i=0; i<list.size(); i++) {
            objects[i] = list.get(i).positive?new PosivePostCreateRqTest(list.get(i)):new NegativePostCreateRqTest(list.get(i));
        }
        return objects;
    }

    public class PosivePostCreateRqTest extends BasicTest {

        protected Logger logger = LoggerFactory.getLogger(this.getClass());

        private CreateRq createRq;

        public PosivePostCreateRqTest(CreateRq createRq) {
            this.createRq = createRq;
        }

        @Test
        public void test() throws Exception {
            HttpResponse rs = sendPostJson("/user/repos", createRq);

            String reply = parseReply(rs);

            logger.info(reply);

            Truth.assertThat(rs.getStatusLine().getStatusCode()).isEqualTo(201);

            testData.getCreatedRepos().add(createRq.created());
        }
    }

    public class NegativePostCreateRqTest extends BasicTest {

        protected Logger logger = LoggerFactory.getLogger(this.getClass());

        private CreateRq createRq;

        public NegativePostCreateRqTest(CreateRq createRq) {
            this.createRq = createRq;
        }

        @Test
        public void test() throws Exception {
            HttpResponse rs = sendPostJson("/user/repos", createRq);

            String reply = parseReply(rs);

            logger.info(reply);

            Truth.assertThat(rs.getStatusLine().getStatusCode()).isEqualTo(createRq.ExpectedCode);
        }
    }
}
