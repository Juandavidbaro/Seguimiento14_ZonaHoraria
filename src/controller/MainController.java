package controller;



import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class MainController implements Runnable {
		
	@FXML
	Label hourLabel;
	
	@FXML 
	Label dateLabel;	
	
	String hora, minutos, segundos, ampm;
	int dia, mes, año;
	int horaCronometro, minutoCronometro,segundoCronometro;
	Calendar calendario;
	Thread h1,h2;
	boolean iniciarHilo=true;
	boolean ejecutando=false;

	@FXML
	Label cronometro;
	@FXML
	Button reiniciar;
	@FXML
	Button iniciar;
	@FXML
	Button parar;
	@FXML
	Button vuelta;
	@FXML
	LocalTime comienzo;
	@FXML
	LocalTime actual;

	private Main main;
	
	public MainController() {
		h1=new Thread(this);
		h1.start();
		h2=new Thread(this);
		h2.start();
		
	}
	
	@Override
	public void run() {
		Thread ct=Thread.currentThread();
		
		while(ct==h1) {
			calculaHora();
			calculaFecha();
			//iniciarCronometro();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			Platform.runLater(()->{
				hourLabel.setText(hora + " : "+minutos+" : "+ segundos + " : " + ampm);
				dateLabel.setText(dia + " / "+ mes+ " / "+año );
				
				
			});
		}
	}

	private void calculaFecha() {
		Calendar calendario=new GregorianCalendar();
		Date fechaActual= new Date();		
		calendario.setTime(fechaActual);
		dia=calendario.get(Calendar.DAY_OF_MONTH);
		mes=calendario.get(Calendar.MONTH)+1;
		año=calendario.get(Calendar.YEAR);
	}

	private void calculaHora() {
		Calendar calendario=new GregorianCalendar();
		Date fechaHoraActual= new Date();		
		calendario.setTime(fechaHoraActual);
		ampm=calendario.get(Calendar.AM_PM)==Calendar.AM?"AM":"PM";
		
		if(ampm.equals("PM")) {
			int h=calendario.get(Calendar.HOUR_OF_DAY)-12;
			hora=h>9?""+h:"0"+h;
		}else {
			hora=calendario.get(Calendar.HOUR_OF_DAY)>9?""+calendario.get(Calendar.HOUR_OF_DAY):"0"+calendario.get(Calendar.HOUR_OF_DAY);
		}
		minutos=calendario.get(Calendar.MINUTE)>9?""+calendario.get(Calendar.MINUTE):"0"+calendario.get(Calendar.MINUTE);
		segundos=calendario.get(Calendar.SECOND)>9?""+calendario.get(Calendar.SECOND):"0"+calendario.get(Calendar.SECOND);
	}
	
	@FXML
	public void reiniciarCronometro() {
		
	}
	@FXML
	public void iniciarCronometro() {
		if(ejecutando==false) {
			iniciarHilo=true;
			ejecutando=true;
			iniciarHiloCronometro();
		}
	}
	private void iniciarHiloCronometro() {
		if(iniciarHilo==true) {
			if(segundoCronometro>59) {
				segundoCronometro=0;
				minutoCronometro++;
			}
			String textSeg="",textMin="",textHora="";
			textSeg+=segundoCronometro;
			
			if(segundoCronometro<10) {
				textSeg="0"+segundoCronometro;
			}else {
				textSeg=""+segundoCronometro;
			}
			if(minutoCronometro<10) {
				textSeg="0"+minutoCronometro;
			}else {
				textMin=""+minutoCronometro;
			}
			if(horaCronometro<10) {
				textHora="0"+horaCronometro;
			}else {
				textSeg=""+horaCronometro;
			}
			String reloj=textHora+" : "+ textMin+" : "+textSeg;
			
			cronometro.setText(reloj);
		}
		
	}

	@FXML
	public void pararCronometro() {
		ejecutando=false;
		iniciarHilo=false;
	}
	@FXML
	public void vueltaCronometro() {
		
	}
	
	public void setMain(Main main) {
		this.main=main;
		
	}
	

}
