import java.text.DecimalFormat;

// �л����� ������ ������ Ŭ����
public class StudentInfo implements Cloneable{
	
	// �ν��Ͻ� ����
	private int no;			// ��ȣ
	private String name;	// �̸�
	private int korScore;	// ���� ����
	private int engScore;	// ���� ����
	private int mathScore;	// ���� ����
	private int total;		// ����
	private double average;	// ���
	private int rank;		// ����

	// ���� �ν��Ͻ� ����
	private static int numOfStudent = 0;	// �л���
	private static int numOfSubject = 3;	// �����

	// ����Ʈ ������ �޼ҵ�
	public StudentInfo() {
		no = numOfStudent++;
	}
	
	// �Ű����� ������ �޼ҵ�
	public StudentInfo (String name, int korScore, int engScore, int mathScore) {
		no = numOfStudent++;
		this.name = name;
		this.korScore = korScore;
		this.engScore = engScore;
		this.mathScore = mathScore;
		total = korScore + engScore + mathScore;
		average = (double)total / numOfSubject;
	}
	
	// getter �޼ҵ�
	public int getNo() {
		return no;
	}
	
	public String getName() {
		return name;
	}

	public int getKorScore() {
		return korScore;
	}

	public int getEngScore() {
		return engScore;
	}

	public int getMathScore() {
		return mathScore;
	}

	public int getTotal() {
		return total;
	}	
	
	public double getAverage() {
		return average;
	}

	public int getRank() {
		return rank;
	}

	public static int getNumOfStudent() { 
		return numOfStudent;		
	}

	// setter �޼ҵ�
	public void setRank(int rank) {
		this.rank = rank;
	}

	protected StudentInfo clone() throws CloneNotSupportedException {
		return (StudentInfo)super.clone();
	}

	public String printScore() { 
		DecimalFormat form = new DecimalFormat("#.##");
		String avgFormat = form.format(average);
		return no+1 + "\t" + name + "\t" + korScore + "\t"+ engScore+ "\t" + mathScore+ "\t"  + total+ "\t" + avgFormat+ "\t" + rank;

	} // printScore()
	
}
