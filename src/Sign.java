import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Sign extends BaseFrame{
	String ln[] = "이름,아이디,비밀번호,비밀번호체크,전화번호,생년월일".split(",");
	JTextField jt[] = {new JTextField(15), new JTextField(15), new JTextField(15), new JTextField(15), new JTextField(15), new JTextField(15) };
	JButton btn[] = {new JButton("회원가입"), new JButton("취소") };
	String tel;
	boolean chk = false;
	int index = 0;
	
	public Sign() {
		super("회원가입",400,500);
		var c = new JPanel(new GridLayout(0,1,0,0));
		var s = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		add(c);
		add(s,"South");
		for (int i = 0; i < ln.length; i++) { //!
			var tmp = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
			var emtpy = new JPanel(new BorderLayout());
			emtpy.setPreferredSize(new Dimension(100,10));
			emtpy.add(lbl(ln[i] + ":", JLabel.LEFT));
			tmp.add(emtpy);
			tmp.add(jt[i]);
			if(i==1) {
				tmp.add(btn("중복확인", a->{
					if(jt[1].getText().equals("")) {
						wmsg("빈칸이 존재");
						return;
					}
					try {
						ResultSet rs = stmt.executeQuery("select * from user where u_id = '" + jt[1].getText() + "'");
						if(rs.next()) {
							wmsg("사용불가");
							jt[1].setText("");
							chk = false;
							return;
						}else
							imsg("ㄱㄴ");
						chk = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}));
			}
			c.add(tmp);
		}
		for(JButton bc : btn) {
			s.add(bc);
			bc.addActionListener(a->{
				if(a.getActionCommand().equals("회원가입")) {
					if(chk==false) {
						wmsg("중복확인을 눌러주세요");
						return;
					}
					if(!jt[2].getText().equals(jt[3].getText())) {
						wmsg("비번일치 X");
						return;
					}
					if(!(jt[2].getText().matches(".'[0-9].'") && jt[2].getText().matches(".'[a-zA-z].*") && jt[2].getText().matches(".*[!@@@^].*")) || jt[2].getText().length()<4) {
						wmsg("비번을 확인햐줘");
						return;
					}
					try {
						if(LocalDate.parse(jt[5].getText()).toEpochDay() > LocalDate.now().toEpochDay()) {
							wmsg("생년월일 확인바람");
							return;
						}
					} catch (Exception e2) {
						wmsg("생년월일 확인바람");
						return;
					}
					execute("insert into user values (0,'" + jt[1].getText()+ "','" + jt[2].getText() + "','" + jt[0].getText() + "','" + jt[4].getText() + "','" + jt[5].getText() + "'0,0)");
					imsg("회원가입됨");
					dispose();
					new Login().addWindowListener(new Before(this));
				}else
					dispose();
			});
		}
		jt[4].addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(jt[4].getText().matches(".*[^0-9 | -].*")) {
					wmsg("문자는사용불가");
					return;
				}
				if(jt[4].getText().length()>=13) {
					jt[4].setText(tel);
				}else
					tel = jt[4].getText();
				if(e.getKeyCode()==8) {
					return;
				}
				if(jt[4].getText().length()==3 || jt[4].getText().length()==8) {
					jt[4].setText(jt[4].getText() + "-");
				}
			}
			
		});
		
		for (int i = 0; i < jt.length; i++) {
			index = i;
			jt[i].addKeyListener(new KeyAdapter() {

				@Override
				public void keyTyped(KeyEvent e) {
					if(!jt[index].getText().equals("")) {
						btn[0].setEnabled(true);
					}else
						btn[0].setEnabled(false);
				}
				
			});
			btn[0].setEnabled(false);
			
			((JPanel)getContentPane()).setBorder(new EmptyBorder(5,5,5,5));
			setVisible(true);
		}
	}
	public static void main(String[] args) {
		new Sign();
	}
}
