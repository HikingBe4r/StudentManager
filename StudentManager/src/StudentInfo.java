import java.text.DecimalFormat;

// 학생들의 정보를 저장할 클래스
public class StudentInfo implements Cloneable{
	
	// 인스턴스 변수
	private int no;			// 번호
	private String name;	// 이름
	private int korScore;	// 국어 점수
	private int engScore;	// 영어 점수
	private int mathScore;	// 수학 점수
	private int total;		// 총점
	private double average;	// 평균
	private int rank;		// 순위

	// 정적 인스턴스 변수
	private static int numOfStudent = 0;	// 학생수
	private static int numOfSubject = 3;	// 과목수

	// 디폴트 생성자 메소드
	public StudentInfo() {
		no = numOfStudent++;
	}
	
	// 매개변수 생성자 메소드
	public StudentInfo (String name, int korScore, int engScore, int mathScore) {
		no = numOfStudent++;
		this.name = name;
		this.korScore = korScore;
		this.engScore = engScore;
		this.mathScore = mathScore;
		total = korScore + engScore + mathScore;
		average = (double)total / numOfSubject;
	}
	
	// getter 메소드
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

	// setter 메소드
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
