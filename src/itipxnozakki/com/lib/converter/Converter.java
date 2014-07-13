package itipxnozakki.com.lib.converter;

import itipxnozakki.com.lib.data.Issue;
import itipxnozakki.com.lib.data.Project;
import itipxnozakki.com.lib.data.parsedata.Leaf;
import itipxnozakki.com.lib.data.parsedata.ParseData;
import itipxnozakki.com.lib.data.parsedata.Tree;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static List<Project> convertProjects(ParseData data) {

        List<Project> projectList = new ArrayList<Project>();

        for (Tree tree : data.getDatas("project")) {
            Project project = new Project();
            for(Leaf leaf : tree.getLeaf()) {
                switch(leaf.getKey()) {
                    case "id":
                        project.setId(leaf.getData());
                    break;
                    case "name":
                        project.setName(leaf.getData());
                    break;
                    case "parent":
                        project.setParent(leaf.getAttr("id"));
                    break;
                }
            }

            projectList.add(project);

        }

        return projectList;
    }

    public static List<Issue> convertIssues(ParseData data) {

        List<Issue> issueList = new ArrayList<Issue>();
        for (Tree tree : data.getDatas("issue")) {
            Issue issue = new Issue();
            for(Leaf leaf : tree.getLeaf()) {
                switch(leaf.getKey()) {
                    case "id":
                        issue.setId(leaf.getData());
                    break;
                    case "subject":
                        issue.setSubject(leaf.getData());
                    break;
                    case "project":
                        issue.setProjectId(leaf.getAttr("id"));
                        issue.setProjectName(leaf.getAttr("name"));
                    break;
                    case "description" :
                        issue.setDescription(leaf.getData());
                    break;
                    case "start_date" :
                        issue.setStartDate(leaf.getData());
                    break;
                }
            }
            issueList.add(issue);
        }

        return issueList;
    }

}
