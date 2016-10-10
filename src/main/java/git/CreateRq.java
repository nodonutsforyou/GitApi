package git;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import utility.Rnd;

/**
 * object to create JSON objet from it
 * Created by MVostrikov on 07.10.2016.
 */
@Data
public class CreateRq {
    String name;
    String description;
    String homepage;
//    @SerializedName("private")
//    Boolean privateRepo; //TODO don't have account to check private repos
    @SerializedName("has_issues")
    Boolean hasIssues;
    @SerializedName("has_wiki")
    Boolean hasWiki;
    @SerializedName("has_downloads")
    Boolean hasDownloads;
    @SerializedName("team_id")
    Boolean teamId;
    @SerializedName("auto_init")
    Boolean autoInit;
    @SerializedName("gitignore_template")
    String gitignoreTemplate;
    @SerializedName("license_template")
    String licenseTemplate;

    public transient boolean positive; //expected result - positive or negative
    public transient int ExpectedCode; //expected status code

    /**
     * Create example object - with simple random name
     */
    static public CreateRq RANDOM() {
        CreateRq createRq = new CreateRq();
        createRq.setName(Rnd.generateString(10));
        return createRq;
    }

    /**
     * create repository object - expected result to check later it existence
     * @return
     */
    public Repo created() {
        return new Repo(this);
    }
}
