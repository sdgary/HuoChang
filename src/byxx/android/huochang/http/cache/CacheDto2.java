package byxx.android.huochang.http.cache;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

import java.io.Serializable;
import java.util.UUID;

@Table(name = "CacheDto")
public class CacheDto2 implements Serializable {

	@Id
	@Column(name = "uuid")
	private String uuid;
	@Column(name = "url")
	private String url;
	@Column(name = "dto")
	private Object dto;
	@Column(name = "time")
	private long time;
	
	public CacheDto2(){
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
