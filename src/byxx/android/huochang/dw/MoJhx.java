package byxx.android.huochang.dw;

import java.util.ArrayList;
import java.util.List;



public class MoJhx {
	JzxDTO taskSelect;
	
	public MoJhx() {
		// TODO Auto-generated constructor stub
	}
	public JzxDTO getTaskSelect() {
		return taskSelect;
	}

	public void setTaskSelect(JzxDTO taskSelect) {
		this.taskSelect = taskSelect;
	}
	List<JzxDTO>  jzxdtos;
	
	
	
	public List<JzxDTO> getJzxdtos() {
		return jzxdtos;
	}

	List<JzxDTO> getJHX4web(int i){
		List<JzxDTO> list = testDates();
//		list = null;
		return list;
		
	}
	
	List<JzxDTO> testDates(){
		List<JzxDTO> list = new ArrayList<JzxDTO>();
		JzxDTO dto = new JzxDTO();
		dto.setCh("X123");
		dto.setCz("123");
		dto.setDdlx("123");
		dto.setJhh("1243");
		dto.setXh1("123");
		dto.setXx1("123");
		list.add(dto);
		return list;
		
	}
	
	
	public JzxDTO finddtobyid(String id){
		JzxDTO dto = null;
//		for(JzxDTO ndto :getJzxdtos()){
//			if(ndto.getCh().equals(id)){
//				dto = ndto;
//				break;
//			}
//		}
		for(int i = 0 ;i<getJzxdtos().size();i++){
			if(getJzxdtos().get(i).getCh().equals(id)){
				dto = getJzxdtos().get(i);
				break;
			}
		}
		return dto;
	}
	
	
}
