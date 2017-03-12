import java.util.*;
import java.io.*;

public class Operations{
	enum Menu {
		ADD, NUMBER, RANK, SEARCH, QUIT
	}
	

	private ArrayList<StudentInfo> student = new ArrayList<StudentInfo>();
	private Scanner sc = new Scanner(System.in);
	
	// ���� ����
	public void saveFile() {
		FileWriter fw = null;
		
		try {
			fw = new FileWriter("Resource/StudentInfo.txt");
			
			for(StudentInfo std: student) {
				
				//System.out.printf("��ȣ\t�̸�\t����\t����\t����\t����\t���\t����%n");
				String data = std.getNo() + ","
							+ std.getName() + ","
							+ std.getKorScore() + ","
							+ std.getEngScore() + ","
							+ std.getMathScore() + ","
							+ std.getTotal() + ","
							+ std.getAverage() + ","
							+ std.getRank() + "\r\n";
				
				fw.write(data);
			}
			
			System.out.println("����Ϸ�");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fw != null) fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// ���� �ҷ�����
	public void LoadFile() {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader("Resource/StudentInfo.txt"));
			String data = null;
			while ((data = br.readLine()) != null) {
				String[] str = new String(data).split(",");
				//student.add(new StudentInfo(name, korScore, engScore, mathScore));
				student.add(new StudentInfo(str[1], Integer.parseInt(str[2]), Integer.parseInt(str[3]), Integer.parseInt(str[4])));
				determineRanking();	// �ҷ������� ������ ������ ������ �����ǵ��� ���⼭ �������� ����.
			}
			
			System.out.println("�ҷ�����Ϸ�");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// �޴�����
	public void start() {
		
		LoadFile();
		
		while(true) {	// 5�� �Է¹ޱ� ������ ��� �����
			System.out.println("1. �л� ���� ���");
			System.out.println("2. ��ȣ�� ��ü �л� ���� ��ȸ");
	        System.out.println("3. ������ ��ü �л� ���� ��ȸ");
	        System.out.println("4. ���κ� �л� ���� ��ȸ");
	        System.out.println("  - �̸�");
	        System.out.println("  - ��ȣ");
	        System.out.println("5. ����");
	        System.out.print("�޴��� �����ϼ��� : ");
			
			Menu[] menus = Menu.values();
			int i = inputNum(1,5) - 1;
			if(i < 0) continue; // �� ���� �Է� ���� ���
			Menu menu = menus[i];
			
			System.out.println();

			switch(menu) {
				case ADD:
					// 1. ���
					AddStudent();
					break;
				case NUMBER:
					// 2. ��ȣ�� ��ȸ
					noSortedView();
					break;
				case RANK:
					// 3. ������ ��ȸ
					rankSortedView();
					break;
				case SEARCH:
					// 4. ���κ� ����(�̸� or ��ȣ�� ��ȸ)
					personalGradeCheck();
					break;
				case QUIT:
					// 5. ����
					System.out.println("���α׷��� �����մϴ�.");
					return ;
				default:
					break;
         } // switch
		 System.out.println();
      } // while
	}

	// ���ڸ� �Է¹޴� �޼ҵ�
	public int inputNum(int minScope, int maxScope) {

		int num = 0;
		while(true) {
			try {
				String input = sc.nextLine();
				if(input.equals("")) {
					return -1;
				} else if(input.charAt(0) >= '0' && input.charAt(0) <='9') {
					num = Integer.parseInt(input);
					if(num < minScope || num > maxScope) { 
						throw new WrongValueException("\n" + minScope + " ~ " + maxScope +"�� ���ڸ� �Է��ϼ���.");

					}
					break;
				} else { // ����, ���� ���� �ƿ�
					throw new WrongValueException("\n" + minScope + " ~ " + maxScope +"�� ���ڸ� �Է��ϼ���.\n");	
				}
	
			}
			catch (WrongValueException wve) {
				System.out.println(wve.getMessage());
				System.out.print("�ٽ� �Է� : ");

			} //try

		} //while	
		return num;
	} // inputNum()


	// ���ڸ� �Է¹޴� �޼ҵ�
	public String inputStr() {
		String str = "";
		while(true) {
			try {
				str = sc.nextLine();	
				if(str.equals("")) {	// �ƹ��Է¾��� ����������
					throw new WrongValueException("�̸��� �Է��ϼ���: ");
				} else {
					break;	// ���� �Է������� ��ȯ.
				}
			}
			catch (WrongValueException wve) {
				System.out.print(wve.getMessage());
			}	
			
		} // while	
		return str; 
	}


	// ��� �л��� ������ �����ϴ� �޼ҵ�
	public void determineRanking() {
		int rank;
		for(int i = 0; i < student.size(); i++) {
			rank = 1;
			for(int j = 0; j < student.size(); j++) {
				if(student.get(i).getTotal() < student.get(j).getTotal()) {
					rank++;
				}
			}
			student.get(i).setRank(rank);
		}
	}	// determineRanking()

	
	public void sortArray(ArrayList<StudentInfo> temp) {
								// Comparator ����
		Collections.sort(temp, new Comparator<StudentInfo>(){
		  public int compare(StudentInfo obj1, StudentInfo obj2) {
			return new Integer(obj1.getRank()).compareTo(new Integer(obj2.getRank()));
		  }
		});
	}

	// �л��� �߰��ϴ� �޼ҵ� (1)
	public void AddStudent() {
		// �л� ���� �Է�
		System.out.print("�̸� : ");
		String name = inputStr();
		int korScore;
		int engScore;
		int mathScore;
		do
		{
			System.out.print("���� ���� : ");
			korScore = inputNum(0,100);
		} while (korScore < 0);
		
		do
		{
			System.out.print("���� ���� : ");
			engScore = inputNum(0,100);
		} while (engScore < 0);

		do
		{
			System.out.print("���� ���� : ");
			mathScore = inputNum(0,100);
		} while (mathScore < 0);
	
		// �Է¹��� ���� arraylist�� ����.
		student.add(new StudentInfo(name, korScore, engScore, mathScore));

		System.out.println("��ϵǾ����ϴ�");
		
		determineRanking();	// �л����� ���� �缳��
		
		saveFile();
		
	} // AddStudent()


	// ��ȣ������ ��ü �л� ���� ��� (2)
	public void noSortedView () {
		
		// �л� �迭�� ��������� �޼ҵ� ����
		if(student.isEmpty()) {
			System.out.println("��ϵ� �л��� �����ϴ�");
			return;
		}

		System.out.printf("��ȣ\t�̸�\t����\t����\t����\t����\t���\t����%n");
		for(int i = 0 ;i < student.size(); i++) {
			System.out.println(student.get(i).printScore());
		}
	} // noSortedView()	

	// ���������� ��ü �л� ���� ��� (3)
	public void rankSortedView() {
		
		// �л� �迭�� ��������� �޼ҵ� ����
		if(student.isEmpty()) {
			System.out.println("��ϵ� �л��� �����ϴ�");
			return;
		}

		// ���� �迭�� �����ؼ� ���������� �� ������ �迭 ���
		ArrayList<StudentInfo> rankSortedArray = (ArrayList<StudentInfo>)student.clone();		// �츮�� 1���� ���⶧���� ������ ��. 0���� �ؾߵ�...
		
		// ��������
		sortArray(rankSortedArray);

		// ���
		System.out.printf("��ȣ\t�̸�\t����\t����\t����\t����\t���\t����%n");
		for(int i = 0 ;i < student.size(); i++) {
			System.out.println(rankSortedArray.get(i).printScore());
		}
	} // rankSortedView()




	// Ư�� �л��� ������ ����ϴ� �޼ҵ�(�̸� or ��ȣ �˻�) (4)
	public void personalGradeCheck() { 

		if(student.isEmpty()) {
			System.out.println("��ϵ� �л��� �����ϴ�");
			return;
		}

		System.out.printf("�޴��� �����ϼ���(1. �̸� �Է� 2. ��ȣ �Է�) : ");
		int a = inputNum(1,2);

		switch(a) {
			case 1:
				System.out.print("�̸� �Է� : ");
				String name =  inputStr();
				viewByName(name);
				break;
			case 2:
				System.out.print("��ȣ �Է� : ");
				int num = inputNum(1, student.size());
				viewByNum(num-1);		// �迭�� 0������ ����Ǳ� ������ -1 ������.
				break;
			default:
				break;
		}

	} // personalGradeCheck()

	public void viewByName(String _name) {

		boolean isSeek = false;	// �ش� �л��� �ִ��� ������ Ȯ���� ����
		//StudentInfo[] temp = new StudentInfo[StudentInfo.getNumOfStudent()+1];
		ArrayList<StudentInfo> temp = new ArrayList<StudentInfo>();

		for(int i = 0; i < student.size(); i++) {
			if( student.get(i).getName().equals(_name)) {
				temp.add(student.get(i));
				isSeek=true;
			}
		}

		if(!isSeek) {	// �迭�� �ش� �л��� ������
				System.out.println("�������� �ʴ� �л��Դϴ�.\n");
				return;
		}

		// ���
		System.out.printf("��ȣ\t�̸�\t����\t����\t����\t����\t���\t����%n");
		for(int i = 0 ;i < temp.size(); i++) {
			System.out.println(temp.get(i).printScore());
		}
	}

	public void viewByNum(int _num) {

		boolean isSeek = false;	// �ش� �л��� �ִ��� ������ Ȯ���� ����
		ArrayList<StudentInfo> temp = new ArrayList<StudentInfo>();

		for(int i = 0; i < student.size(); i++) {
			if( student.get(i).getNo() == _num) {
				temp.add(student.get(i));
				isSeek=true;
			}
		}

		if(!isSeek) {	// �迭�� �ش� �л��� ������
				System.out.println("�������� �ʴ� �л��Դϴ�.\n");
				return;
		}

		// ���
		System.out.printf("��ȣ\t�̸�\t����\t����\t����\t����\t���\t����%n");
		for(int i = 0 ;i < temp.size(); i++) {
			System.out.println(temp.get(i).printScore());
		}
	}
	

}
 
