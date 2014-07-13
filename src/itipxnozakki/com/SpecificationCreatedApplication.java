package itipxnozakki.com;

import itipxnozakki.com.lib.data.Project;
import itipxnozakki.com.lib.service.SpecificationCreatedApplicationService;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class SpecificationCreatedApplication extends JFrame implements ActionListener {

    protected DefaultListModel model;

    private static final long serialVersionUID = 1L;

    JList list;

    SpecificationCreatedApplication(String title){
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p1 = new JPanel();
        Container contentPane = getContentPane();
        contentPane.add(p1, BorderLayout.NORTH);
        init(p1);
      }

    public void init(JPanel panel) {
//        SpecificationCreatedApplicationService service = new SpecificationCreatedApplicationService();
        SpecificationCreatedApplicationService service = new SpecificationCreatedApplicationService();
        //service.getJournals(url + "issues/5.xml?include=journals&" + KEY).show();
        List<Project> prjects = service.getProjects();
        model = new DefaultListModel();
        for (Project p : prjects) {
            if (p.getParent() == null) {
                model.addElement(p.getId() + " : " + p.getName());
            }
        }
        list = new JList(model);
        panel.add(list);
        JButton addButton = new JButton("作成");
        addButton.addActionListener(this);
        addButton.setActionCommand("addButton");
        panel.add(addButton);

    }

    public static void main(String[] args) {

        SpecificationCreatedApplication frame = new SpecificationCreatedApplication("仕様書作成ツール くん ");
        frame.setSize(500, 280);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("addButton")){
            String select = ((String)list.getSelectedValue()).split(":")[0].trim();
            String selectName = ((String)list.getSelectedValue()).split(":")[1].trim();
            SpecificationCreatedApplicationService service = new SpecificationCreatedApplicationService();
            service.createSpecification(select, selectName);
            System.out.println("完了しました");
        }
    }

}
