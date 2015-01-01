public class MainPresenter {
	private MainView mainView;
	private TimerPresenter[] timerPresenters = new TimerPresenter[5];
	private int idCounter;

	MainPresenter() {
		idCounter = 1;
		mainView = new MainView(this);

		for (int i = 0; i < 5; i++) {
			timerPresenters[i] = new TimerPresenter(mainView, dispenseID());
		}
		mainView.constructAddPanels();
		
		
	}
	public TimerPresenter getTimerPres(int i) {
		return timerPresenters[i];
	}

	private int dispenseID() {
		int returnID = idCounter;
		idCounter++;
		return returnID;
	}
}
