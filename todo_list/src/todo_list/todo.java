package todo_list;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;

public class todo extends JFrame {

    private JPanel contentPane;
    private JPanel listPanel;
    private JScrollPane scrollPane;
    private JButton addButton;
    private int yOffset = 10;
    private static final int ITEM_HEIGHT = 35;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> {
            try {
            	todo frame = new todo();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public todo() {
        setTitle("ToDoList");
        setLocation(1550, 600); // 위치 지정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(360, 400);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	
        	    saveTodoListToFile();  // 닫기 전에 저장
                new todo_main();
                
            }
        });
        
        setVisible(true);

       
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        listPanel = new JPanel(null);
        listPanel.setBackground(new Color(240, 240, 240));

        scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(5, 5, 334, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 3, true));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(scrollPane);

        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(5, 310, 334, 40);
        controlPanel.setBackground(Color.WHITE);
        contentPane.add(controlPanel);

        addButton = new JButton("Add");
        addButton.setBackground(Color.GRAY);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.putClientProperty("JComponent.arc", 999);
        controlPanel.add(addButton);

        addButton.addActionListener(e -> addCheckBox(" ",false));

        UIManager.put("CheckBox.icon.checkmarkColor", Color.GRAY);

        loadTodoListFromFile();
        
    }

    //불러오기
    private void loadTodoListFromFile() {
        String baseDir = System.getProperty("user.home") + "/ToDoList";
        String dateStr = todo_main.selectedDate.toString();
        File file = new File(baseDir + "/" + dateStr + "/todolist.txt");

        boolean added = false;

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.isBlank()) continue;
                    boolean isChecked = line.startsWith("V");
                    String todoText = line.substring(3).trim();  // "V - " 또는 "X - " 다음
                    addCheckBox(todoText, isChecked);
                    added = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!added) {
            addCheckBox("", false);
            
        }
    }



	private void addCheckBox(String text, boolean isChecked) {
        JPanel itemPanel = new JPanel(null);
        itemPanel.setBackground(Color.WHITE);
        
        itemPanel.setBounds(0, yOffset, 320, ITEM_HEIGHT);

        JCheckBox cb = new JCheckBox();
        cb.setBackground(Color.WHITE);
        cb.setBounds(10, 5, 20, 25);

        JTextField textField = new JTextField(text);
        textField.setFont(new Font("한컴산뜻돋움", Font.PLAIN, 13));
        textField.setBounds(35, 5, 200, 25);
        textField.setBorder(null);

       
        JLabel trashIcon = new JLabel( new ImageIcon(getClass().getResource("/icons/icons8-delete-trash-24.png")));
        trashIcon.setBounds(270, 5, 24, 24);
        trashIcon.setVisible(false);

        cb.setSelected(isChecked); //체크 상태 반영
        
        //취소선 처리
        Font baseFont = textField.getFont();
     
        if (isChecked) {
            Map<TextAttribute, Object> attributes = new HashMap<>(baseFont.getAttributes());
            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            textField.setFont(baseFont.deriveFont(attributes));
        }
           
        cb.addActionListener(e -> {
            if (cb.isSelected()) {
                Map<TextAttribute, Object> attributes = new HashMap<>(baseFont.getAttributes());
                attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
                textField.setFont(baseFont.deriveFont(attributes));
            } else {
                textField.setFont(baseFont);
            }
        });

        itemPanel.add(cb);
        itemPanel.add(textField);
        itemPanel.add(trashIcon);

        // 마우스 오버 시 삭제 아이콘 표시
        itemPanel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                trashIcon.setVisible(true);
            }
            public void mouseExited(MouseEvent e) {
                trashIcon.setVisible(false);
            }
        });

        // 삭제 이벤트
        trashIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                listPanel.remove(itemPanel);
                listPanel.revalidate();
                listPanel.repaint();
                repositionItems();
            }
        });

        listPanel.add(itemPanel);
        listPanel.revalidate();
        listPanel.repaint();
        yOffset += ITEM_HEIGHT;

        listPanel.setPreferredSize(new Dimension(300, yOffset));
        
        MouseAdapter showTrashHover = new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                trashIcon.setVisible(true);
                itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            }

            public void mouseExited(MouseEvent e) {
                // 마우스가 컴포넌트 위에 있는지 정확히 확인
                PointerInfo pi = MouseInfo.getPointerInfo();
                Point screenLoc = pi.getLocation();

                SwingUtilities.convertPointFromScreen(screenLoc, itemPanel.getParent()); // 기준을 panel의 부모 기준으로 변환
                Component deepest = SwingUtilities.getDeepestComponentAt(itemPanel.getParent(), screenLoc.x, screenLoc.y);

                // itemPanel 또는 그 자식이 아니면 테두리 제거
                if (deepest == null || !SwingUtilities.isDescendingFrom(deepest, itemPanel)) {
                    trashIcon.setVisible(false);
                    itemPanel.setBorder(null);
                }
            }
        };


        itemPanel.addMouseListener(showTrashHover);
        cb.addMouseListener(showTrashHover);
        textField.addMouseListener(showTrashHover);
        trashIcon.addMouseListener(showTrashHover);

    }

    private void repositionItems() {
        Component[] components = listPanel.getComponents();
        int y = 10;
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                comp.setBounds(0, y, 320, ITEM_HEIGHT);
                y += ITEM_HEIGHT;
            }
        }
        yOffset = y;
        listPanel.setPreferredSize(new Dimension(300, yOffset));
    }
    private void saveTodoListToFile() {
        String baseDir = System.getProperty("user.home") + "/ToDoList";
        String dateStr = todo_main.selectedDate.toString();
        File folder = new File(baseDir, dateStr);  // ToDoList/yyyy-MM-dd

        if (!folder.exists()) folder.mkdirs();

        File file = new File(folder, "todolist.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Component comp : listPanel.getComponents()) {
                if (comp instanceof JPanel itemPanel) {
                    JCheckBox cb = (JCheckBox) itemPanel.getComponent(0);
                    JTextField tf = (JTextField) itemPanel.getComponent(1);
                    String status = cb.isSelected() ? "V" : "X";
                    writer.write(status + " - " + tf.getText());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
