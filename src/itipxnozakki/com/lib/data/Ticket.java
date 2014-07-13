package itipxnozakki.com.lib.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ticket {

    private Journal journal;
    private Map<String, List<String[]>> tableMap = new HashMap<String, List<String[]>>();
    private String name;
    private String description;
    private String startDate;
    private String projectName;

    public Ticket(Journal journal, Map<String, List<String[]>> map) {
        this.journal = journal;
        this.tableMap = map;
    }

    public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Journal getData() {
        return journal;
    }

    public Map<String, List<String[]>> getTableMap() {
        return tableMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void show() {
        System.out.println(journal);
        System.out.println("----------------------------------------------------");
        for (Map.Entry<String, List<String[]>> list: this.tableMap.entrySet()) {
            System.out.println("[" + list.getKey() + "]");
            for (String[] values : list.getValue()) {
                for (String value : values) {
                    System.out.print(value + "\t");
                }
                System.out.println("");
            }
        }
    }

}
