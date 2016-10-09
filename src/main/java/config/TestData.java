package config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import config.folders.CreateSet;
import config.sets.CreateRqSet;
import git.CreateRq;
import git.Repo;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

/**
 * class to store all test data
 * Created by MVostrikov on 30.07.2015.
 */
@XStreamAlias("testData")
public class TestData {

    protected static final String DEFAULTFILENAME = "testData";

    protected static final Logger logger = LoggerFactory.getLogger(TestData.class);

    static Map<String, TestData> testDataList;

    @Getter
    protected String testName;

    private static String getHostFormUrl(String url) {
        try {
            URL urlUrl = new URL(url);
            return urlUrl.getHost();
        } catch (MalformedURLException e) {
        }
        return "";
    }

    public static TestData getTestdata(String host, String testName) {
        host = getHostFormUrl(host);
        URL url;

        if (testDataList == null) testDataList = new HashMap<>();
        if(!testDataList.containsKey(testName)) testDataList.put(testName, null);
        if (testDataList.get(testName) == null) {
            try {
                File f = null;
                try {
                    String filename = DEFAULTFILENAME + "." + host + ".xml";
                    url = Thread.currentThread().getContextClassLoader().getResource(filename);
                    f = Paths.get(url.toURI()).toFile();
                    if (!f.exists()) throw new FileNotFoundException(f.getAbsolutePath());
                } catch (URISyntaxException | FileNotFoundException e) {
                    try {
                        url = Thread.currentThread().getContextClassLoader().getResource(DEFAULTFILENAME + ".xml");
                        f = Paths.get(url.toURI()).toFile();
                    } catch (URISyntaxException er) {
                        logger.error("Не найден файл конфигурации", er);
                    }
                }
                TestData newTestData = parseXml(f);
                newTestData.testName = testName;
                testDataList.put(testName, newTestData);
            } catch (FileNotFoundException e) {
                logger.error("Не найден файл конфигурации", e);
            }
        }
        return testDataList.get(testName);
    }

    public static TestData parseXml(String filename) throws FileNotFoundException, URISyntaxException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(filename);

        logger.debug("parseXml[String " + url.toString() + "]");
        return parseXml(Paths.get(url.toURI()).toFile());
    }

    public static TestData parseXml(File xml) throws FileNotFoundException {
        logger.debug("parseXml[File "+xml.getName()+"]");
        if (!xml.exists()) {
            logger.error("File "+xml.getAbsolutePath()+" don't exists");
            throw new FileNotFoundException(xml.getAbsolutePath());
        }

        FileReader fileReader = new FileReader(xml);
        XStream xStream = new XStream(new DomDriver());

        xStream.processAnnotations(TestData.class);

        return (TestData) xStream.fromXML(fileReader);
    }

    @XStreamAlias("login")
    @Getter
    @Setter
    private String login;

    @XStreamAlias("username")
    @Getter
    @Setter
    private String username;

    @XStreamAlias("password")
    @Getter
    @Setter
    private String password;

    Map<String, String> params = new HashMap<>();

    public String getParam(String paramName) {
        return params.get(paramName);
    }
    public void setParam(String paramName, String value) {
        params.put(paramName, value);
    }

    @XStreamAlias("createSet")
    private CreateSet createSet;

    public List<CreateRq> getCreateObjects() {
        return getCreateObjects(null);
    }
    public List<CreateRq> getCreateObjects(CreateSet.OutcomeSet outcomeSet) {
        List<CreateRq> ret = new ArrayList<>();
        for (CreateRqSet set : createSet.getOutcomeSet(outcomeSet)) {
            ret.add(set.convert());
        }
        return ret;
    }

    private static Queue<Repo> createdRepos;

    public Queue<Repo> getCreatedRepos() {
        if(createdRepos==null) createdRepos = new ArrayDeque<>();
        return createdRepos;
    }
}
