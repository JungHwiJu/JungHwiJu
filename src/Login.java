import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends BaseFrame{
	JTextField jt[] = {new JTextField(15), new JTextField(15) };
	String ln[] = "아이디,비밀번호".split(",");
	JLabel l = new JLabel("회원가입");
	
	public Login() {
		super("로그인", 350, 180);
		add(hylbl("기능마켓", 0, 25),"North");
		var s = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		var c = new JPanel(new GridLayout(0,1));
		add(c);
		add(s,"South");
		
		for (int i = 0; i < ln.length; i++) {
			var tmp = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5)); //!
			tmp.add(lbl(ln[i] + ":", 0)); //!
			tmp.add(jt[i]);
			c.add(tmp);
		}
		
		add(btn("로그인", a->{
			if(jt[0].getText().equals("") || jt[1].getText().equals("")) {
				wmsg("빈칸이 존재합니다.");
				return;
			}
			if(jt[0].getText().equals("admin") && jt[1].getText().equals("1234")) {
				imsg("관리자로 로그인 되었습니다.");
				dispose();
				new ItemManager().addWindowListener(new Before(this));
				return;
			}
			try {
				ResultSet rs = stmt.executeQuery("select * from user where u_id = '" + jt[0].getText() + "' and u_pw = '" + jt[1].getText() + "'");
				if(rs.next()) {
					u_name = rs.getString(4);
					u_no = rs.getInt(1);
					imsg(u_name + "님 환영합니다.");
					new Mainf().addWindowListener(new Before(Login.this));
				}else
					wmsg("일치하지 않다");
				return;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}),"East");
		
		l.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new Sign().addWindowListener(new Before(Login.this));
			}
			
		});
		s.add(l);
		
		((JPanel)getContentPane()).setBorder(new EmptyBorder(5,5,5,5));
		setVisible(true);
	}
	public static void main(String[] args) {
		new Login();
	}
}
