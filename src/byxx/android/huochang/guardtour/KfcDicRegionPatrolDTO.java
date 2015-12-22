/**
 * 
 */
package byxx.android.huochang.guardtour;

import java.io.Serializable;

/**
 * @author 【刘孝华】
 * 
 */
public class KfcDicRegionPatrolDTO implements Serializable {
	private static final long serialVersionUID = -3084729475800052715L;
	private String stationCode;
	private String region;
	private String patrolDot;
	private String direction;
	private String details;

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPatrolDot() {
		return patrolDot;
	}

	public void setPatrolDot(String patrolDot) {
		this.patrolDot = patrolDot;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
		
	}
}
