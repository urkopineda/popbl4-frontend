package model;

public class Duration {
	int sec;
	
	public Duration (int sec) {
		this.sec = sec;
	}
	
	public void addSecond() {
		this.sec++;
	}
	
	public void setDuration(int s) {
		this.sec = s;
	}
	
	public int enSegundos() {
		return sec;
	}
	
	public int enMilisegundos() {
		return sec*1000;
	}
	
	public int enMinutos() {
		return sec/60;
	}
	
	public String toString() {
		return (this.sec/60)+":"+((this.sec%60<10)?"0"+this.sec%60:this.sec%60);
	}
}
