package de.evonomy.evolution;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class BackDialogListeners {
	
	public static abstract class BaseListener implements OnClickListener {
		protected GameActivity callback;
		
		public BaseListener(GameActivity callback) {
			this.callback = callback;
		}
	}
	
	public static class SaveListener extends BaseListener {

		public SaveListener(GameActivity callback) {
			super(callback);
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			callback.save();
		}
	}
	
	public static class ExitListener extends BaseListener {

		public ExitListener(GameActivity callback) {
			super(callback);
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			callback.finish();
		}
	}
	
	public static class ExitAndSaveListener extends BaseListener {

		public ExitAndSaveListener(GameActivity callback) {
			super(callback);
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			callback.save();
			callback.finish();
		}
	}
	
	public static class ResumeListener extends BaseListener {
		public ResumeListener(GameActivity callback) {
			super(callback);
		}
		
		public void onClick(DialogInterface dialog, int which) {
			callback.resume();
		}
	}
}
