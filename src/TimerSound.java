import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;

import com.gtranslate.Audio;
import com.gtranslate.Language;


public class TimerSound implements Runnable{
	private Audio soundNotification;
	private InputStream sound;
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
				sound  = soundNotification.getAudio("champion "+ timerModel.getTimerPres().getId() + " flash is back up.", Language.ENGLISH);
			}
			else{
				sound  = soundNotification.getAudio(championName + "'s flash is back up.", Language.ENGLISH);
			}
		} catch (IOException e) {
			System.out.println("Setting sound to Champion failed");
		}
		
	}

	public void soundAlarm(){
		try {
			soundNotification.play(sound);
		} catch (JavaLayerException e) {
			System.out.println("Sound play failed");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		soundAlarm();
	}
}
