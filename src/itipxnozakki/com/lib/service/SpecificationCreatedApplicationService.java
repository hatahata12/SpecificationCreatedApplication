package itipxnozakki.com.lib.service;

import itipxnozakki.com.lib.data.Issue;
import itipxnozakki.com.lib.data.OutputData;
import itipxnozakki.com.lib.data.Project;
import itipxnozakki.com.lib.data.Ticket;
import itipxnozakki.com.lib.output.DataOutputFactory;
import itipxnozakki.com.lib.output.DataOutputInterface;
import itipxnozakki.com.lib.parser.XmlDataParser;
import itipxnozakki.com.lib.reader.IssueReader;
import itipxnozakki.com.lib.reader.ProjectReader;
import itipxnozakki.com.lib.reader.TicketReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SpecificationCreatedApplicationService {

    private String URL;
    private String KEY;
    private String outputPath;
    private boolean httpsFlag;

    public SpecificationCreatedApplicationService() {
    	Properties prop = new Properties();
    	try {
			prop.load(new FileInputStream("setting.properties"));
			this.URL = prop.getProperty("url");
			this.KEY = "key=" + prop.getProperty("key");
			this.outputPath = prop.getProperty("output");
			this.httpsFlag = Boolean.valueOf(prop.getProperty("https"));
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }

    public List<Project> getProjects() {
        String url = this.URL + "projects.xml?" + KEY;
        ProjectReader reader = new ProjectReader(url, this.httpsFlag);
        reader.setParser(new XmlDataParser());
        return reader.get();
    }

    public Ticket getTicket(String url) {
        TicketReader reader = new TicketReader(url, this.httpsFlag);
        reader.setParser(new XmlDataParser());
        return reader.get();
    }

    public void createSpecification(String select, String selectName) {

        List<String> projectUrlList = new ArrayList<String>();
        // 指定されたプロジェクトの子プロジェクト取得
        List<Project> projects = this.getProjects();
        projectUrlList.add(URL + "issues.xml?project_id="+select+"&" + KEY);
//        for (Project p : projects) {
//            if (p.getParent() != null) {
//                if (p.getParent().equals(select)) {
//                    projectUrlList.add(URL + "issues.xml?project_id="+p.getId()+"&" + KEY);
//                }
//            }
//        }

        OutputData outputData = new OutputData();
        List<OutputData> outputDataList = new ArrayList<OutputData>();
        for (String url : projectUrlList) {
            List<Issue> issues = this.getIssues(url);
            List<Ticket> data = new ArrayList<Ticket>();
            for (Issue issue : issues) {
                String _url = URL + "issues/" + issue.getId() + ".xml?include=journals&" + KEY;
                Ticket ticket = null;
                if ((ticket = this.getTicket(_url)) != null) {
                	ticket.setDescription(issue.getDescription());
                	ticket.setStartDate(issue.getStartDate());
                	ticket.setProjectName(issue.getProjectName());
                	data.add(ticket);
                }
            }

            if (data.size() > 0) {
                outputData.add(data);
                outputDataList.add(outputData);
                outputData = new OutputData();
            }
        }

        DataOutputFactory factory = new DataOutputFactory(this.outputPath, outputDataList);
        DataOutputInterface outputer = factory.output("excel");
        outputer.outputting(selectName);

    }

    public List<Issue> getIssues(String url) {
        IssueReader reader = new IssueReader(url, this.httpsFlag);
        reader.setParser(new XmlDataParser());
        return reader.get();
    }

}
