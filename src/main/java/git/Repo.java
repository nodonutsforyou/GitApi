package git;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.apache.http.HttpResponse;
import utility.Rnd;

/**
 * object to store created data of new repository.
 * we can check created repository if we want to
 * Created by MVostrikov on 07.10.2016.
 */
@Data
public class Repo {
    String name;
    String description;
    String homepage;
    Boolean privateRepo;
    Boolean hasIssues;
    Boolean hasWiki;
    Boolean hasDownloads;
    Boolean teamId;
    Boolean autoInit;
    String gitignoreTemplate;
    String licenseTemplate;

    public Repo(CreateRq createRq) {
        this.name=createRq.getName();
        this.description=createRq.getDescription();
        this.homepage=createRq.getHomepage();
//        this.privateRepo=createRq.getPrivateRepo();  //TODO don't have account to check private repos
        this.hasIssues=createRq.getHasIssues();
        this.hasWiki=createRq.getHasWiki();
        this.hasDownloads=createRq.getHasDownloads();
        this.teamId=createRq.getTeamId();
        this.autoInit=createRq.getAutoInit();
        this.gitignoreTemplate=createRq.getGitignoreTemplate();
        this.licenseTemplate=createRq.getLicenseTemplate();
    }

}
