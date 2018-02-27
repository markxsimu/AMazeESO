package falstad;

import android.os.Handler;
import android.os.Message;



import falstad.Constants.StateGUI;


/**
 * Implements the screens that are displayed whenever the game is not in 
 * the playing state. The screens shown are the title screen, 
 * the generating screen with the progress bar during maze generation,
 * and the final screen when the game finishes.
 * @author pk
 *
 */
public class MazeView extends DefaultViewer {

	// need to know the maze model to check the state 
	// and to provide progress information in the generating state
	private MazeController controller ;
	Handler handler;

	
	public MazeView(MazeController c) {
		super() ;
		controller = c ;
	}
	



			


	@Override
	public void redraw(MazePanel panel, StateGUI state, int px, int py, int view_dx,
			int view_dy, int walk_step, int view_offset, RangeSet rset, int ang) {



		switch (state) {

		case STATE_TITLE:
			break;
		case STATE_GENERATING:
			break;
		case STATE_PLAY:
			// skip this one
			break;
		case STATE_FINISH:
			handler.sendMessage(Message.obtain(handler, Constants.STATE_FINISH));
			break;
		case STATE_LOSE:
			handler.sendMessage(Message.obtain(handler, Constants.STATE_LOSE));


		}
	}
	
	private void dbg(String str) {
		System.out.println("MazeView:" + str);
	}
	
	private void setHandler(Handler handler){
		this.handler = handler;
	}
	


}
