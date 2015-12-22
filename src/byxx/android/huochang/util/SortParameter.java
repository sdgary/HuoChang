package byxx.android.huochang.util;

public class SortParameter {
	
	private String description;
	
	private Class cl;

	private String[] fields;

	private Integer[] sortTypes;


	/**
	 * @param description 自定义的描述
	 * @param cl	集合里存放的对象类型
	 * @param fields 根据哪些属性排序(按传入的顺序进行排序)
	 * @param sortTypes 属性排序方式(CSort.SORT_DESC 降序 CSort.SORT_ACS 升序)
	 */
	public SortParameter(String description, Class cl, String[] fields, Integer[] sortTypes) {
		super();
		this.description = description;
		this.cl = cl;
		this.fields = fields;
		this.sortTypes = sortTypes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Class getCl() {
		return cl;
	}

	public void setCl(Class cl) {
		this.cl = cl;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public Integer[] getSortTypes() {
		return sortTypes;
	}

	public void setSortTypes(Integer[] sortTypes) {
		this.sortTypes = sortTypes;
	}

}
