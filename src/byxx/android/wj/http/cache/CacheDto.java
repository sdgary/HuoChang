package byxx.android.wj.http.cache;

import java.util.UUID;

public class CacheDto {
	private String url;
	private Object dto;
	private String uuid;

	private long time;
	
	public CacheDto(){
		uuid = UUID.randomUUID().toString();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Object getDto() {
		return dto;
	}
	public void setDto(Object dto) {
		this.dto = dto;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
