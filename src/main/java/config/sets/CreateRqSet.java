package config.sets;

import com.google.gson.annotations.SerializedName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import git.CreateRq;
import lombok.Data;
import utility.Rnd;

/**
 * Class to hold description of test case for xml configuration
 * Created by MVostrikov on 07.10.2016.
 */
@Data
@XStreamAlias("createRqSet")
public class CreateRqSet {

    @XStreamAlias("name")
    @XStreamAsAttribute
    String name;

    @XStreamAlias("description")
    @XStreamAsAttribute
    String description;

    @XStreamAlias("homepage")
    @XStreamAsAttribute
    String homepage;

//    @XStreamAlias("privateRepo")
//    @XStreamAsAttribute
//    String privateRepo; //TODO don't have account to check private repos

    @XStreamAlias("hasIssues")
    @XStreamAsAttribute
    String hasIssues;

    @XStreamAlias("hasWiki")
    @XStreamAsAttribute
    String hasWiki;

    @XStreamAlias("hasDownloads")
    @XStreamAsAttribute
    String hasDownloads;

    @XStreamAlias("teamId")
    @XStreamAsAttribute
    String teamId;

    @XStreamAlias("autoInit")
    @XStreamAsAttribute
    String autoInit;

    @XStreamAlias("gitignoreTemplate")
    @XStreamAsAttribute
    String gitignoreTemplate;

    @XStreamAlias("licenseTemplate")
    @XStreamAsAttribute
    String licenseTemplate;

    @XStreamAlias("expectedErrorCode")
    @XStreamAsAttribute
    String expectedErrorCode;

    public boolean positive;

    /**
     * convert this object to object, which can be translated to JSON
     */
    public CreateRq convert() {
        CreateRq ret = new CreateRq();

        if (name == null) {//name is not null by default
            ret.setName(Rnd.generateString(10));
        } else {
            switch (name.toLowerCase()) {
                case "empty": ret.setName("");
                    break;
                case "min": ret.setName(Rnd.generateString(1));
                    break;
                case "middle": ret.setName(Rnd.generateString(10));
                    break;
                case "large": ret.setName(Rnd.generateString(100));
                    break;
                case "null": ret.setName(null);
                    break;
                case "too large": ret.setName(Rnd.generateString(1000));
                    break;
                default: ret.setName(Rnd.generateString(10));
            }
        }
        ret.setDescription(switchStringValues(description));
        ret.setHomepage(switchStringValues(homepage));
//        ret.setPrivateRepo(switchBooleanValues(privateRepo));  //TODO don't have account to check private repos
        ret.setHasIssues(switchBooleanValues(hasIssues));
        ret.setHasWiki(switchBooleanValues(hasWiki));
        ret.setHasDownloads(switchBooleanValues(hasDownloads));
        ret.setTeamId(switchBooleanValues(teamId));
        ret.setAutoInit(switchBooleanValues(autoInit));

        ret.setGitignoreTemplate(switchStringValues(gitignoreTemplate));
        ret.setLicenseTemplate(switchStringValues(licenseTemplate));

        ret.positive = this.positive;
        int expectedCode;
        try {
            expectedCode = Integer.parseInt(expectedErrorCode);
        } catch (Exception e) {
            expectedCode = 201;
        }
        ret.setExpectedCode(expectedCode);

        return ret;
    }

    private static String switchStringValues(String value) {
        String ret=null;
        if (value == null) {
            return null;
        }
        switch (value.toLowerCase()) {
            case "min": ret=Rnd.generateString(1);
                break;
            case "middle": ret=Rnd.generateString(10);
                break;
            case "large": ret=Rnd.generateString(100);
                break;
            case "null": ret=null;
                break;
            case "too large": ret=Rnd.generateString(665380);
                break;
            case "too large text": ret=Rnd.generateString(665360);
                break;
            default: ret=null;
        }
        return ret;
    }

    private static Boolean switchBooleanValues(String value) {
        Boolean ret=null;
        if (value == null) {
            return null;
        }
        switch (value.toLowerCase()) {
            case "true": ret=true;
                break;
            case "false": ret=false;
                break;
            default: ret=null;
        }
        return ret;
    }
}
