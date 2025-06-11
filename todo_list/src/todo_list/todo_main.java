package todo_list;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class todo_main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	// 현재 선택된 날짜 변수
    static LocalDate selectedDate = LocalDate.now();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					todo_main frame = new todo_main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public todo_main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ToDoList");
		setSize(350, 350);
		setMinimumSize(new Dimension(350,300)); 
		setLocation(1550, 600);
	    setVisible(true);
	    
		// 배경 이미지 설정된 contentPane
        setContentPane(new BackgroundPanel(new ImageIcon(getClass().getResource("/icons/ai생성_곰.png")).getImage()));
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        
		JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		
		JLabel left = new JLabel("◀");
		left.setFont(new Font("굴림", Font.PLAIN, 14));
		left.setBounds(12, 10, 12, 15);
		datePanel.add(left);
		
		JLabel dateLabel  = new JLabel();
		dateLabel.setBounds(47, 2, 125, 30);
		datePanel.add(dateLabel);
		dateLabel.setFont(new Font("굴림", Font.BOLD, 14));
		
		dateLabel.setText(formatter.format(selectedDate));
		
		JLabel right = new JLabel("▶");
		right.setFont(new Font("굴림", Font.PLAIN, 14));
		right.setBounds(184, 10, 18, 15);
		datePanel.add(right);
		
		datePanel.setOpaque(false);
		getContentPane().add(datePanel, BorderLayout.NORTH);
		
		left.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        selectedDate = selectedDate.minusDays(1);
		        dateLabel.setText(formatter.format(selectedDate));
		    }
		});

		right.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        selectedDate = selectedDate.plusDays(1);
		        dateLabel.setText(formatter.format(selectedDate));
		    }
		});
		
		
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 10));

		
		JLabel folder = new JLabel(new ImageIcon(getClass().getResource("/icons/icons8-kawaii-folders-24.png")));
		folder.setBounds(12, 10, 24, 23);
		
		iconPanel.add(folder);
				
		folder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new list();
			}		  
		});
		
	
		
		JLabel checklist = new JLabel(new ImageIcon(getClass().getResource("/icons/icons8-checklist-24.png")));
		checklist.setBounds(119, 10, 32, 32);
	
		checklist.setBackground(new Color(0, 0, 0, 0));
		
		
		checklist.setOpaque(true);
		checklist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new todo();
			}
			
		  
		});
		iconPanel.add(checklist);
	
		JLabel bear = new JLabel(new ImageIcon(getClass().getResource("/icons/icons8-bear-24.png")));
		bear.setBounds(225, 10, 32, 32);		
		iconPanel.add(bear);
		bear.setToolTipText("할 일을 추가해보세요!");
		

		iconPanel.setOpaque(false);

	
		
		
		getContentPane().add(iconPanel, BorderLayout.SOUTH);
		
		
	}
	 // 배경 이미지 패널
    class BackgroundPanel extends JPanel {
        private Image background;

        public BackgroundPanel(Image background) {
            this.background = background;
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
