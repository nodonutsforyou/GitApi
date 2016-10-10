package testCases;

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
 * test factory to test cases in configuration xml
 * Created by MVostrikov on 07.10.2016.
 */
public class CreateFactory {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected TestData testData;

    /**
     * TestNG test factory
     */
    @Parameters({"url"})
    @Factory
    public Object[] createApiFactory(final ITestContext testContext, String url) {

        testData = TestData.getTestdata(url, testContext.getName());

        List<CreateRq> list = testData.getCreateObjects(); //get list of all test cases
        Object[] objects = new Object[list.size()]; //crate array for TestNG Objects
        for(int i=0; i<list.size(); i++) {
            //each one is granted with there own test case
            objects[i] = list.get(i).positive?new PosivePostCreateRqTest(list.get(i)):new NegativePostCreateRqTest(list.get(i));
        }
        return objects;
    }

    /**
     * positive test
     */
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

            Truth.assertThat(rs.getStatusLine().getStatusCode()).isEqualTo(201); //check status

            testData.getCreatedRepos().add(createRq.created());//add to list to delete repository at the end of test.
        }
    }

    /**
     * Test negative test cases
     */
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

            Truth.assertThat(rs.getStatusLine().getStatusCode()).isEqualTo(createRq.ExpectedCode); //status mast be as expected. //TODO status code of error is not actualy important, and maybe it is not an issue to check it at all
        }
    }
}
