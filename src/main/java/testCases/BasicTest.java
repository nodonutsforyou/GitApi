package testCases;

import com.google.gson.Gson;
import config.TestData;
import git.Repo;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Basic test to parent all other tests
 * Created by MVostrikov on 07.10.2016.
 */
public class BasicTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Gson gson;
    protected HttpClient httpClient;
    protected String url;
    protected TestData testData;
    protected ITestContext testContext;
    UsernamePasswordCredentials credentials;

    /**
     * Initialization form TestNG
     * @param testContext provided by TestNG
     * @param url provided by maven
     */
    @Parameters({"url"})
    @BeforeClass(alwaysRun = true)
    public void init(final ITestContext testContext, String url) {

        gson = new Gson();
        httpClient = HttpClientBuilder.create().build();
        this.url = url;
        this.testContext = testContext;

        testData = TestData.getTestdata(url, testContext.getName());

        credentials = new UsernamePasswordCredentials(testData.getLogin(), testData.getPassword()); //encrypted header for auth
    }

    /**
     * runs after suite is ended
     */
    @AfterSuite
    public void afterSuite() {
        cleanAll();
    }

    /**
     * sends objects as Json in Post message
     */
    public HttpResponse sendPostJson(String url, Object obj) throws Exception {
        return sendPostJson(url, new StringEntity(gson.toJson(obj)));
    }
    /**
     * sends StringEntity in Post message
     */
    public HttpResponse sendPostJson(String url, StringEntity message) throws Exception {
        HttpPost rq = new HttpPost(this.url + url);
        rq.addHeader(new BasicScheme().authenticate(credentials, rq, null));
        rq.setEntity(message);

        HttpResponse rs = httpClient.execute(rq);

        return rs;
    }
    /**
     * sends empty Delete message
     */
    public HttpResponse sendDelete(String url) throws Exception {
        HttpDelete rq = new HttpDelete(this.url + url);
        rq.addHeader(new BasicScheme().authenticate(credentials, rq, null));

        HttpResponse rs = httpClient.execute(rq);

        return rs;
    }

    /**
     * deletes repository
     */
    public HttpResponse deleteRq(Repo repo)throws Exception {
        HttpResponse rs = sendDelete("/repos/"+testData.getUsername()+"/"+repo.getName());
        return rs;
    }

    /**
     * deletes all created repositories
     */
    public void cleanAll()  {
        while(testData.getCreatedRepos().size()>0) {
            Repo repo = testData.getCreatedRepos().poll();
            try {
                deleteRq(repo);
            } catch (Exception e) {
                logger.error("Error at deleting repository ["+repo.getName()+"]", e);
            }
        }
    }

    /**
     * parses reply to string
     */
    public String parseReply(HttpResponse rs) throws Exception {
        HttpEntity httpEntiny = rs.getEntity();
        String body;
        if (httpEntiny!=null) {
            body = EntityUtils.toString(httpEntiny);
        } else {
            body = null;
        }
        return body;
    }
}
