package todo_list;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTree;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class list extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					list frame = new list();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public list() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(1550, 600);
		
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	
                new todo_main();
                
            }
        });
        
	    setVisible(true);
		setSize(300, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	
		
        JTree tree = new JTree(buildTreeFromSavedData());
	    tree.setPreferredSize(new Dimension(250, 600)); // 충분한 크기 설정

        // 스크롤 가능하게 설정
        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setPreferredSize(new Dimension(260, 350));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		contentPane.add(scrollPane);
	}
	
	private DefaultMutableTreeNode buildTreeFromSavedData() {
	    File baseFolder = new File(System.getProperty("user.home") + "/ToDoList");
	    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Todo List");

	    if (baseFolder.exists() && baseFolder.isDirectory()) {
	        File[] dateFolders = baseFolder.listFiles(File::isDirectory);

	        if (dateFolders != null) {
	            Arrays.sort(dateFolders);  // 날짜 정렬
	            for (File dateFolder : dateFolders) {
	                DefaultMutableTreeNode dateNode = new DefaultMutableTreeNode(dateFolder.getName());
	                File todoFile = new File(dateFolder, "todolist.txt");

	                if (todoFile.exists()) {
	                    try (BufferedReader reader = new BufferedReader(new FileReader(todoFile))) {
	                        String line;
	                        while ((line = reader.readLine()) != null) {
	                            if (!line.isBlank()) {
	                                dateNode.add(new DefaultMutableTreeNode(line));
	                            }
	                        }
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }

	                root.add(dateNode);
	            }
	        }
	    }

	    return root;
	}

}
