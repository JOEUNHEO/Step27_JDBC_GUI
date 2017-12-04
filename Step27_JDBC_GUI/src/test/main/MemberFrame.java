package test.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import test.dao.MemberDao;
import test.dto.MemberDto;

public class MemberFrame extends JFrame implements ActionListener{
	//맴버필드 정의하기
	JTextField inputNum, inputName, inputAddr;
	JButton saveBtn, deleteBtn, updateBtn;
	//테이블 모델
	DefaultTableModel model;
	//
	JTable table;
		
	//생성자
	public MemberFrame() {
		initUI();
	}
	//UI 초기화 작업 메소드
	public void initUI() {
		//레이아웃 설정
		setLayout(new BorderLayout());
		//상단 페널 객체 생성
		JPanel topPanel=new JPanel();
				
		//레이블 객체 생성
		JLabel label1=new JLabel("번호");
		JLabel label2=new JLabel("이름");
		JLabel label3=new JLabel("주소");
				
		//텍스트 필드 객체 생성
		inputNum=new JTextField(10);
		inputName=new JTextField(10);
		inputAddr=new JTextField(10);
				
		saveBtn=new JButton("저장");
		deleteBtn=new JButton("삭제");
		updateBtn=new JButton("수정");
		
		//버튼에 ActionListener 등록
		saveBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		//버튼에 action command 등록		
		saveBtn.setActionCommand("save");
		deleteBtn.setActionCommand("delete");
		updateBtn.setActionCommand("update");
		
		//번호는 입력이나 수정을 할 수 없도록 설정
		inputNum.setEditable(false);
				
		//페널에 컴포넌트 추가 하기 
		topPanel.add(label1);
		topPanel.add(inputNum);
		topPanel.add(label2);
		topPanel.add(inputName);
		topPanel.add(label3);
		topPanel.add(inputAddr);
		topPanel.add(saveBtn);
		topPanel.add(deleteBtn);
		topPanel.add(updateBtn);
				
		//프레임의 상단에 페널 배치하기
		add(topPanel, BorderLayout.NORTH);
		
		//테이블 칼럼 명을 String[] 에 담는다.
		String[] colNames = {"번호","이름","주소"};
		//기본 테이블 모델 객체 생성
		model = new DefaultTableModel(colNames,0);
		//JTable 객체 생성
		table = new JTable();
		//테이블에 모델 연결
		table.setModel(model);
		
		//스크롤 가능한 페널 객체
		JScrollPane sPanel = new JScrollPane(table);
		//페널을 프레임의 가운데에 배치
		add(sPanel, BorderLayout.CENTER);
		
		//프레임의 위치와 크기 설정
		setBounds(200, 200, 800, 500);
		//보이도록 설정
		setVisible(true);
		//프레임을 닫았을 때 프로세스가 완전히 종료되도록 설정
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//회원정보 출력
		displayMember();
	}
	
	//메인 메소드
	public static void main(String[] args) {
		new MemberFrame();
	}
	//ActionListener 인터페이스 때문에 구현한 메소드
	@Override
	public void actionPerformed(ActionEvent ae) {
		String command = ae.getActionCommand();
		if(command.equals("save")) {
			//입력한 이름과 주소 읽어오기
			String name = inputName.getText();
			String addr = inputAddr.getText();
			//MemberDto 에 담는다.
			MemberDto dto = new MemberDto();
			dto.setName(name);
			dto.setAddr(addr);
			//MemberDao 를 이용해서 저장
			MemberDao dao = MemberDao.getInstance();
			boolean isSuccess = dao.insert(dto);
			if(isSuccess) {
				JOptionPane.showMessageDialog(this, "저장했습니다.");
			}else {
				JOptionPane.showMessageDialog(this, "저장 실패!");
			}
			//저장 후 입력한 이름과 주소 비우기
			inputName.setText("");
			inputAddr.setText("");
		}else if(command.equals("delete")) {
			//선택한 테이블의 행 위치를 읽는다.
			int row = table.getSelectedRow();
			if(row >= 0) {//테이블에 선택한 행이 있다면,
				//선택한 테이블 행 번호 읽어오기
				String value = table.getModel().getValueAt(row, 0).toString();
				int num = Integer.parseInt(value);
				
				//MemberDao 를 이용해서 삭제
				MemberDao dao = MemberDao.getInstance();
				boolean isSuccess = dao.delete(num);
				if(isSuccess) {
					JOptionPane.showMessageDialog(this, "삭제했습니다.");
				}else {
					JOptionPane.showMessageDialog(this, "삭제 실패!");
				}
				
				//저장 후 입력한 이름과 주소 비우기
				inputName.setText("");
				inputAddr.setText("");
			}
		}else if(command.equals("update")) {
			//선택한 테이블의 행 위치를 읽는다.
			int row = table.getSelectedRow();
			if(row >= 0) {//테이블에 선택한 행이 있다면,
				//선택한 테이블의 행 번호 읽어오기
				String value = model.getValueAt(row, 0).toString();
				int num = Integer.parseInt(value);
				//입력한 이름과 주소 읽어오기
				String name = inputName.getText();
				String addr = inputAddr.getText();
				//MemberDto 에 담는다.
				MemberDto dto = new MemberDto(num, name, addr);
				//MemberDao 를 이용해서 수정
				MemberDao dao = MemberDao.getInstance();
				boolean isSuccess = dao.update(dto);
				if(isSuccess) {
					JOptionPane.showMessageDialog(this, "수정했습니다.");
				}else {
					JOptionPane.showMessageDialog(this, "수정 실패!");
				}
				
				//저장 후 입력한 이름과 주소 비우기
				inputName.setText("");
				inputAddr.setText("");
			}
		}
		//회원 정보 다시 출력
		displayMember();
	}//actionPerformed()
	
	//DB 에 있는 회원 정보를 JTable 에 출력하는 메소드
	public void displayMember() {
		//회원 정보를 읽어온다.
		MemberDao dao = MemberDao.getInstance();
		List<MemberDto> list = dao.getList();
		//테이블의 내용을 지우고
		model.setRowCount(0);
		//다시 출력
		for(MemberDto tmp:list) {
			Object[] rowData = {tmp.getNum(), tmp.getName(), tmp.getAddr()};
			model.addRow(rowData);
		}
	}
}
