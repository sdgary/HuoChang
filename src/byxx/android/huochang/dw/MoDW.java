package byxx.android.huochang.dw;

import java.util.HashMap;
import java.util.List;

import byxx.android.huochang.traininfo.CarDto;

public class MoDW {
	List<CarDto> carDto4dws;
	CarDto dto4dw;
	
	
	static HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
	
	public List<CarDto> getCarDto4dws() {
		return carDto4dws;
	}
	
	public CarDto findbyid(String id){
		CarDto value= null;
		for(CarDto Dto :getCarDto4dws()){
			if(Dto.getCid().equals(id)){
				value = Dto;
				break;
			}
		}
		return value;
	}

	public CarDto getDto4dw() {
		return dto4dw;
	}

	public void setDto4dw(CarDto dto4dw) {
		this.dto4dw = dto4dw;
	}
	

}
