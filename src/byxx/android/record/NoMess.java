package byxx.android.record;

import java.io.Serializable;

public class NoMess implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String mess = null;
	public long time;
    public int isrecord = 2 ; //2：默认记录；1：配置记录 ； 0：配置不记录  
     
}
