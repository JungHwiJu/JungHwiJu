import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class BaseFrame extends JFrame{
	static Connection con = DB.con;
	static Statement stmt = DB.stmt;
	static String u_name, p_name, c_name;
	static int u_no, p_no, c_no, sel;
	static boolean ADmind = false;
	JTable t;
	DefaultTableCellRenderer cell = new DefaultTableCellRenderer();
	
	static{
		try {
			stmt.execute("use 2021지방_1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BaseFrame(String title, int x, int y) {
		super(title);
		setSize(x,y);
		setDefaultCloseOperation(2);
		setLocationRelativeTo(null);
	}
	
	JLabel lbl(String t, int a) {
		JLabel l = new JLabel(t,a);
		return l;
	}
	
	JLabel hylbl(String t, int a, int s) {
		JLabel l = new JLabel(t,a);
		l.setFont(new Font("HY헤드라인M",1,s));
		return l;
	}
	
	JLabel lbl(String t, int a, int s) {
		JLabel l = new JLabel(t,a);
		l.setFont(new Font("",1,s));
		return l;
	}
	
	JButton btn(String t, ActionListener a) {
		JButton b = new JButton(t);
		b.addActionListener(a);
		return b;
	}
	
	int toInt(Object path) {
		if(path.toString().equals("")) {
			path = "0";
		}
		return Integer.parseInt(path.toString());
	}
	
	DefaultTableModel model(String col[]) {
		DefaultTableModel m = new DefaultTableModel(null,col) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};
		return m;
	}
	
	
	
	void execute(String sql) {
		try {
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void imsg(String msg) {
		JOptionPane.showMessageDialog(null, msg,"정보",JOptionPane.INFORMATION_MESSAGE);
	}
	
	void wmsg(String msg) {
		JOptionPane.showMessageDialog(null, msg,"경고",JOptionPane.ERROR_MESSAGE);
	}
	
	JTable tablel(DefaultTableModel m) {
		cell.setHorizontalAlignment(SwingConstants.LEFT);
		JTable t = new JTable(m);
		t.getTableHeader().setReorderingAllowed(false);
		t.getTableHeader().setResizingAllowed(false);
		for (int i = 0; i < t.getColumnCount(); i++) {
			t.getColumnModel().getColumn(i).setCellRenderer(cell);
		}
		return t;
	}
	
	JTable tablec(DefaultTableModel m) {
		cell.setHorizontalAlignment(SwingConstants.CENTER);
		JTable t = new JTable(m);
		t.getTableHeader().setReorderingAllowed(false);
		t.getTableHeader().setResizingAllowed(false);
		for (int i = 0; i < t.getColumnCount(); i++) {
			t.getColumnModel().getColumn(i).setCellRenderer(cell);
		}
		return t;
	}
	
	JTable yellowTable(DefaultTableModel m) {
		cell.setHorizontalAlignment(SwingConstants.CENTER);
		t = new JTable(m) {

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				JComponent jc = (JComponent) super.prepareRenderer(renderer, row, column);
				if(row==t.getSelectedRow()) {
					jc.setBackground(Color.yellow);
				}
				else
					jc.setBackground(Color.white);
				return jc;
			}
		};
		t.getTableHeader().setReorderingAllowed(false);
		t.getTableHeader().setResizingAllowed(false);
		for (int i = 0; i < t.getColumnCount(); i++) {
			t.getColumnModel().getColumn(i).setCellRenderer(cell);
		}
		return t;
	}
	
	void addrow(String sql, DefaultTableModel m) {
		m.setRowCount(0);
		try {
			ResultSet rs = stmt.executeQuery(sql);
			Object row[] = new Object[m.getColumnCount()];
			while(rs.next()) {
				for (int i = 0; i < row.length; i++) {
					row[i] = rs.getString(i+1);
				}
				m.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class Before extends WindowAdapter{
		BaseFrame b;
		
		public Before(BaseFrame b) {
			this.b =b;
			setVisible(false);
		}

		@Override
		public void windowClosed(WindowEvent e) {
			setVisible(true);
		}
		
		
	}
}
