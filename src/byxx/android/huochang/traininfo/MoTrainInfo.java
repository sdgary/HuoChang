package byxx.android.huochang.traininfo;

import java.util.List;

import byxx.android.huochang.MaStation;

public class MoTrainInfo {
	
	public List<CarDto> trainInfo = null;
	
	
	
	
	
	
	
	public List<CarDto> getTrainInfo() {
		return trainInfo;
	}







	public List<CarDto> getTrainInfo(String gdm){
		return MaStation.getInstance().getMaWebService().getGdxc(MaStation.stationCode, gdm);
	}
}
