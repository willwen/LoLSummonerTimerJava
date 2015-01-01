import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;

import com.gtranslate.Audio;
import com.gtranslate.Language;


public class TimerSound implements Runnable{
	private Audio soundNotification;
	private InputStream soundStart;
	private InputStream soundFinish;
	private TimerModel timerModel;
	
	TimerSound(TimerModel timerModel){
		this.timerModel=timerModel;
		soundNotification = Audio.getInstance();

	}
	
	public Audio getAudio() {
		return soundNotification;
	}
	
	public void updateSoundClip(String championName){
		try {
			if (championName.equals("")){
				soundStart = soundNotification.getAudio("Started" + " champion "+ timerModel.getTimerPres().getId() + " Timer.", Language.ENGLISH);
				soundFinish  = soundNotification.getAudio("champion "+ timerModel.getTimerPres().getId() + " flash is back up.", Language.ENGLISH);
			}
			else{
				soundStart = soundNotification.getAudio("Started "+ championName + " Timer.", Language.ENGLISH);
				soundFinish  = soundNotification.getAudio(championName + "'s flash is back up.", Language.ENGLISH);
			}
		} catch (IOException e) {
			System.out.println("Setting sound to Champion failed");
		}
		
	}
	
	public void soundAlarmStart(){
		try {
			soundNotification.play(soundStart);
		} catch (JavaLayerException e) {
			System.out.println("Sound play failed");
			e.printStackTrace();
		}
	}
	
	public void soundAlarmFinish(){
		try {
			soundNotification.play(soundFinish);
		} catch (JavaLayerException e) {
			System.out.println("Sound play failed");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		soundAlarmFinish();
	}
}
