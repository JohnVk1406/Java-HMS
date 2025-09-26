import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
public class restaurant_chart {
    public static void main(String args[]){
  
            JFrame frame = new JFrame("Restaurant Chart");
            frame.setSize(600, 600);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            ImageIcon icon = new ImageIcon("HMSICON.png");
        frame.setIconImage(icon.getImage());

            JLabel label = new JLabel("Restaurant Chart ");
            label.setBounds(200, 30, 300, 30);
            frame.add(label);
            label.setFont(new Font("Arial", Font.BOLD, 24));

            JPanel panel = new JPanel();
            panel.setBounds(37, 90, 500, 430);
            panel.setLayout(null);
            panel.setBackground(new Color(220, 220, 220));
            frame.add(panel);
            JButton backButton = new JButton("Back");
            backButton.setBounds(10, 10, 80, 30);
            frame.add(backButton);
            backButton.addActionListener(e -> frame.dispose());
            

            String[] columnNames = {"Table Number", "Capacity", "Status"};
            Object[][] data = {
                {"1", "4", "Available"},
                {"2", "2", "Occupied"},
                {"3", "6", "Reserved"},
                {"4", "4", "Available"},
                {"5", "2", "Occupied"},
                {"6", "8", "Available"},
                {"7", "4", "Reserved"},
                {"8", "2", "Available"}
            };

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(20, 20, 460, 390);
            panel.add(scrollPane);

            frame.setVisible(true);
    }
}